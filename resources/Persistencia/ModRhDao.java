package br.com.splgenerator.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import br.com.splgenerator.model.cadastro.Funcionario;

public interface ModRhDao extends Serializable {
	
	//Funcionario
	List<Funcionario> getFuncionarios();
	void salvarFuncionario(Funcionario funcionario);	
	void atualizarFuncionario(Funcionario funcionario);
	void removerFuncionario(Funcionario funcionario);
	
	//Relatorios
	List<Funcionario> getFuncionariosPeriodoAdmissao(Date dataAdmissaoInicial, Date dataAdmissaoFinal);
	List<Funcionario> getFuncionariosAniversariantes(int mesAniversario);
	List<String> getBancos();
	List<Funcionario> getFuncionariosBanco(String selectedBancosFuncionarios);
	
}
