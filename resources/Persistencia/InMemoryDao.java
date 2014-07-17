package br.com.splgenerator.dao.memory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.splgenerator.dao.ModRhDao;
import br.com.splgenerator.model.cadastro.Funcionario;



@ManagedBean(name="inMemoryDao")
@SessionScoped
public class InMemoryDao implements ModRhDao {

	private static final long serialVersionUID = 1L;
	
	private static List<Funcionario> listaFuncionario = new ArrayList<Funcionario>();
	
	static {
		Funcionario funcionario1 = new Funcionario();
		funcionario1.setId(1);
		funcionario1.setNome("Jose da Silva (MEMORIA)");
		funcionario1.setIdade(30);
		funcionario1.setCpf("11122233396");
		funcionario1.setEndereco("Rua Um");
		
		Funcionario funcionario2 = new Funcionario();
		funcionario2.setId(2);
		funcionario2.setNome("Maria Paula (MEMORIA)");
		funcionario2.setIdade(45);
		funcionario2.setCpf("99988877712");
		funcionario2.setEndereco("Avenida Dois");
		
		listaFuncionario.add(funcionario1);
		listaFuncionario.add(funcionario2);
		
	}

	@Override
	public List<Funcionario> getFuncionarios() {
		return listaFuncionario;
	}

	@Override
	public void salvarFuncionario(Funcionario funcionario) {
		listaFuncionario.add(funcionario);
	}

	@Override
	public void removerFuncionario(Funcionario funcionario) {
		listaFuncionario.remove(funcionario);
	}

	@Override
	public void atualizarFuncionario(Funcionario funcionario) {
		Funcionario tempFuncionario = null;
		for (Funcionario f : listaFuncionario) {
			if (f.equals(funcionario)) {
				tempFuncionario = f;
				break;
			}
		}
		listaFuncionario.remove(tempFuncionario);
		salvarFuncionario(funcionario);
	}

	@Override
	public List<Funcionario> getFuncionariosPeriodoAdmissao(
			Date dataAdmissaoInicial, Date dataAdmissaoFinal) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Funcionario> getFuncionariosAniversariantes(int mesAniversario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getBancos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Funcionario> getFuncionariosBanco(
			String selectedBancosFuncionarios) {
		// TODO Auto-generated method stub
		return null;
	}

}
