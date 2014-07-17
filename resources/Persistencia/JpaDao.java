package br.com.splgenerator.dao.jpa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.transaction.UserTransaction;

import br.com.splgenerator.dao.ModRhDao;
import br.com.splgenerator.model.cadastro.Funcionario;

@ManagedBean(name = "jpaDao")
@SessionScoped
public class JpaDao implements ModRhDao {

	private static final long serialVersionUID = 1L;

	private static final String PERSISTENCE_UNIT_NAME = "modrh-pu";

	@PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
	private EntityManager em;

	@Resource
	private UserTransaction ut;
	
	private void rollback() {
		try {
			ut.rollback();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Funcionario> getFuncionarios() {
		Query q = em.createQuery("select f from Funcionario f");
		return q.getResultList();
	}

	@Override
	public void salvarFuncionario(Funcionario funcionario) {
		try {
			ut.begin();
			em.persist(funcionario);
			ut.commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
		}
	}

	@Override
	public void atualizarFuncionario(Funcionario funcionario) {
		try {
			ut.begin();
			em.merge(funcionario);
			ut.commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
		}
	}

	@Override
	public void removerFuncionario(Funcionario funcionario) {
		try {
			ut.begin();
			em.remove(em.merge(funcionario));
			ut.commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Funcionario> getFuncionariosPeriodoAdmissao(Date dataAdmissaoInicial, Date dataAdmissaoFinal) {
		Query q = em.createQuery("select f from Funcionario f where f.dataAdmissao between :dataAdmissaoInicial and :dataAdmissaoFinal");
		q.setParameter("dataAdmissaoInicial", dataAdmissaoInicial, TemporalType.DATE);
		q.setParameter("dataAdmissaoFinal", dataAdmissaoFinal, TemporalType.DATE);
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Funcionario> getFuncionariosAniversariantes(int mesAniversario) {
		Query q = em.createNativeQuery("select f.* from Funcionario f where month(f.data_nascimento) = ?", Funcionario.class);
		q.setParameter(1, mesAniversario);
		return q.getResultList();
	}

	@Override
	public List<String> getBancos() {
		List<Funcionario> funcionarios = getFuncionarios();
		Set<String> bancos = new TreeSet<String>();
		for (Funcionario f : funcionarios) {
			bancos.add(f.getBanco());
		}
		return new ArrayList<String>(bancos);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Funcionario> getFuncionariosBanco(String selectedBancosFuncionarios) {
		Query q = em.createQuery("select f from Funcionario f where f.banco = :banco");
		q.setParameter("banco", selectedBancosFuncionarios);
		return q.getResultList();
	}

}
