package br.com.splgenerator.dao.memory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
		funcionario1.setDataNascimento(subtractYearsFromNow(20));
		funcionario1.setDataAdmissao(subtractYearsFromNow(3));
		funcionario1.setEmail("jose@email.com");
		funcionario1.setBanco("Itau");
		funcionario1.setAgencia(1234);
		funcionario1.setConta(123456);
		
		
		Funcionario funcionario2 = new Funcionario();
		funcionario2.setId(2);
		funcionario2.setNome("Maria Paula (MEMORIA)");
		funcionario2.setIdade(45);
		funcionario2.setCpf("99988877712");
		funcionario2.setEndereco("Avenida Dois");
		funcionario2.setDataNascimento(subtractYearsFromNow(30));
		funcionario2.setDataAdmissao(subtractYearsFromNow(1));
		funcionario2.setEmail("maria@email.com");
		funcionario2.setBanco("Bradesco");
		funcionario2.setAgencia(4321);
		funcionario2.setConta(654321);
		
		Funcionario funcionario3 = new Funcionario();
		funcionario3.setId(3);
		funcionario3.setNome("Carlos Fonseca (MEMORIA)");
		funcionario3.setIdade(50);
		funcionario3.setCpf("33322211189");
		funcionario3.setEndereco("Avenida Tres");
		funcionario3.setDataNascimento(subtractYearsFromNow(40));
		funcionario3.setDataAdmissao(subtractYearsFromNow(2));
		funcionario3.setEmail("carlos@email.com");
		funcionario3.setBanco("Bradesco");
		funcionario3.setAgencia(4321);
		funcionario3.setConta(111222);
		
		listaFuncionario.add(funcionario1);
		listaFuncionario.add(funcionario2);
		listaFuncionario.add(funcionario3);
		
	}
	
	private static Date subtractYearsFromNow(int years) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.YEAR, -years);
		return cal.getTime();
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
	public List<Funcionario> getFuncionariosPeriodoAdmissao(Date dataAdmissaoInicial, Date dataAdmissaoFinal) {
		List<Funcionario> l = new ArrayList<Funcionario>();
		for (Funcionario f : listaFuncionario) {
			Date admissao = f.getDataAdmissao();
			if (admissao.after(dataAdmissaoInicial) && admissao.before(dataAdmissaoFinal)) {
				l.add(f);
			}
		}
		return l;
	}

	@Override
	public List<Funcionario> getFuncionariosAniversariantes(int mesAniversario) {
		List<Funcionario> l = new ArrayList<Funcionario>();
		for (Funcionario f : listaFuncionario) {
			Date aniversario = f.getDataNascimento();
			Calendar cal = Calendar.getInstance();
			cal.setTime(aniversario);
			int month = cal.get(Calendar.MONTH);
			if (month == (mesAniversario - 1)) {
				l.add(f);
			}
		}
		return l;
	}

	@Override
	public List<String> getBancos() {
		Set<String> bancos = new HashSet<String>();
		for (Funcionario f : listaFuncionario) {
			bancos.add(f.getBanco());
		}
		return new ArrayList<String>(bancos);
	}

	@Override
	public List<Funcionario> getFuncionariosBanco(String selectedBancosFuncionarios) {
		List<Funcionario> l = new ArrayList<Funcionario>();
		for (Funcionario f : listaFuncionario) {
			if (selectedBancosFuncionarios.equals(f.getBanco())) {
				l.add(f);
			}
		}
		return l;
	}

}
