package br.com.splgenerator.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.naming.NamingException;

import br.com.splgenerator.model.cadastro.Funcionario;

@ManagedBean(name = "jdbcDao")
@SessionScoped
public class JdbcDao extends JdbcBaseDao {
	
	private static final long serialVersionUID = 1L;

	public JdbcDao() throws NamingException {
		super();
	}

	@Override
	public List<Funcionario> getFuncionarios() {
		List<Funcionario> funcionarios = new ArrayList<Funcionario>();
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = getConnection();

			//String query = "select f.*, df.id as ID_DADOS_FUNC, df.*, cf.id as ID_CURSO_FUNCIONARIO, cf.*, pc.* from funcionario f, dados_funcionario df, curso_funcionario cf, parcela_curso pc where f.id = df.id_funcionario and f.id = cf.id_funcionario and cf.id_parcela = pc.id_parcela";
			String query = "select * from FUNCIONARIO";
			stmt = connection.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Funcionario funcionario = new Funcionario();
				funcionario.setId(rs.getInt("ID"));
				funcionario.setNome(rs.getString("NOME"));
				funcionario.setIdade(rs.getInt("IDADE"));
				funcionario.setCpf(rs.getString("CPF"));
				funcionario.setEndereco(rs.getString("ENDERECO"));
				funcionario.setDataAdmissao(rs.getDate("DATA_ADMISSAO"));
				funcionario.setDataNascimento(rs.getDate("DATA_NASCIMENTO"));
				funcionario.setEmail(rs.getString("EMAIL"));
				funcionario.setBanco(rs.getString("BANCO"));
				funcionario.setAgencia(rs.getInt("AGENCIA"));
				funcionario.setConta(rs.getInt("CONTA"));
				funcionario.setCargo(rs.getString("CARGO"));
				funcionario.setSalario(rs.getInt("SALARIO"));
				funcionario.setAfastamentoInicio(rs.getDate("AFASTAMENTO_INICIO"));
				funcionario.setAfastamentoDias(rs.getInt("AFASTAMENTO_DIAS"));
				funcionario.setDataDesligamento(rs.getDate("DATA_DESLIGAMENTO"));
				funcionario.setTipoPagamento(rs.getString("TIPO_PAGAMENTO"));
				funcionario.setBonus(rs.getInt("BONUS"));
				funcionario.setReembolso(rs.getInt("REEMBOLSO"));
				
				funcionarios.add(funcionario);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(connection, stmt);
		}
		return funcionarios;
	}

	//TODO SALVAR, ATUALIZAR E REMOVER CURSO FUNCIONARIO
	@Override
	public void salvarFuncionario(Funcionario funcionario) {
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = getConnection();
			connection.setAutoCommit(false);

			String query = "insert into FUNCIONARIO (NOME, CPF, ENDERECO, IDADE, DATA_NASCIMENTO, DATA_ADMISSAO, EMAIL, BANCO, AGENCIA, CONTA) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			stmt = connection.prepareStatement(query, new String[] { "ID"} );
			stmt.setString(1, funcionario.getNome());
			stmt.setString(2, funcionario.getCpf());
			stmt.setString(3, funcionario.getEndereco());
			stmt.setInt(4, funcionario.getIdade());
			stmt.setDate(5, new java.sql.Date(funcionario.getDataNascimento().getTime()));
			stmt.setDate(6, new java.sql.Date(funcionario.getDataAdmissao().getTime()));
			stmt.setString(7, funcionario.getEmail());
			stmt.setString(8, funcionario.getBanco());
			stmt.setInt(9, funcionario.getAgencia());
			stmt.setInt(10, funcionario.getConta());
			
			int ret = stmt.executeUpdate();
			System.out.println("ret = " + ret);
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				funcionario.setId(rs.getLong(1));
			}
			
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new RuntimeException(e.getMessage(), e);
			}
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			close(connection, stmt);
		}
	}

	@Override
	public void atualizarFuncionario(Funcionario funcionario) {
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = getConnection();
			connection.setAutoCommit(false);
			
			String query = "UPDATE FUNCIONARIO SET NOME = ?, " +
												   "CPF = ?, " +
												   "ENDERECO = ?, " +
												   "IDADE = ?, " +
												   "DATA_NASCIMENTO = ?, " +
												   "DATA_ADMISSAO = ?, " +
												   "EMAIL = ?, " +
												   "BANCO = ?, " +
												   "AGENCIA = ?, " +
												   "CONTA = ?, " +
												   "CARGO = ?, " +
												   "SALARIO = ?, " +
												   "AFASTAMENTO_INICIO = ?, " +
												   "AFASTAMENTO_DIAS = ?, " +
												   "DATA_DESLIGAMENTO = ?, " +
												   "TIPO_PAGAMENTO = ?, " +
												   "BONUS = ?, " +
												   "REEMBOLSO = ? " +
												   "WHERE ID = ?";
			stmt = connection.prepareStatement(query);
			stmt.setString(1, funcionario.getNome());
			stmt.setString(2, funcionario.getCpf());
			stmt.setString(3, funcionario.getEndereco());
			stmt.setInt(4, funcionario.getIdade());
			stmt.setDate(5, new java.sql.Date(funcionario.getDataNascimento().getTime()));
			stmt.setDate(6, new java.sql.Date(funcionario.getDataAdmissao().getTime()));
			stmt.setString(7, funcionario.getEmail());
			stmt.setString(8, funcionario.getBanco());
			stmt.setInt(9, funcionario.getAgencia());
			stmt.setInt(10, funcionario.getConta());
			stmt.setString(11, funcionario.getCargo());
			stmt.setInt(12, funcionario.getSalario());
			
			if (funcionario.getAfastamentoInicio() == null) {
				stmt.setNull(13, java.sql.Types.DATE); 
			} else {
				stmt.setDate(13, new java.sql.Date(funcionario.getAfastamentoInicio().getTime()));
			}
			
			stmt.setInt(14, funcionario.getAfastamentoDias());
			
			if (funcionario.getDataDesligamento() == null) {
				stmt.setNull(15, java.sql.Types.DATE); 
			} else {
				stmt.setDate(15, new java.sql.Date(funcionario.getDataDesligamento().getTime()));
			}
			
			stmt.setString(16, funcionario.getTipoPagamento());
			stmt.setInt(17, funcionario.getBonus());
			stmt.setInt(18, funcionario.getReembolso());
			
			stmt.setLong(19, funcionario.getId());
			int ret = stmt.executeUpdate();
			System.out.println("ret = " + ret);
			
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new RuntimeException(e.getMessage(), e);
			}
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			close(connection, stmt);
		}
	}

	@Override
	public void removerFuncionario(Funcionario funcionario) {
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = getConnection();
			connection.setAutoCommit(false);

			String query = "DELETE from FUNCIONARIO WHERE id = ?";
			stmt = connection.prepareStatement(query);
			stmt.setLong(1, funcionario.getId());
			int ret = stmt.executeUpdate();
			System.out.println("ret = " + ret);
			
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new RuntimeException(e.getMessage(), e);
			}
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			close(connection, stmt);
		}
	}

	@Override
	public List<Funcionario> getFuncionariosPeriodoAdmissao(Date dataAdmissaoInicial, Date dataAdmissaoFinal) {
		List<Funcionario> funcionarios = new ArrayList<Funcionario>();
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = getConnection();

			String query = "select * from funcionario where data_admissao between ? and ?";
			stmt = connection.prepareStatement(query);
			stmt.setDate(1, new java.sql.Date(dataAdmissaoInicial.getTime()));
			stmt.setDate(2, new java.sql.Date(dataAdmissaoFinal.getTime()));
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Funcionario funcionario = new Funcionario();
				funcionario.setId(rs.getInt("ID"));
				funcionario.setNome(rs.getString("NOME"));
				funcionario.setIdade(rs.getInt("IDADE"));
				funcionario.setCpf(rs.getString("CPF"));
				funcionario.setEndereco(rs.getString("ENDERECO"));
				funcionario.setDataAdmissao(rs.getDate("DATA_ADMISSAO"));
				funcionario.setDataNascimento(rs.getDate("DATA_NASCIMENTO"));
				funcionario.setEmail(rs.getString("EMAIL"));
				funcionario.setBanco(rs.getString("BANCO"));
				funcionario.setAgencia(rs.getInt("AGENCIA"));
				funcionario.setConta(rs.getInt("CONTA"));
				
				funcionarios.add(funcionario);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(connection, stmt);
		}
		return funcionarios;
	}

	@Override
	public List<Funcionario> getFuncionariosAniversariantes(int mesAniversario) {
		List<Funcionario> funcionarios = new ArrayList<Funcionario>();
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = getConnection();

			String query = "select * from funcionario where month(data_nascimento) = ?";
			stmt = connection.prepareStatement(query);
			stmt.setInt(1, mesAniversario);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Funcionario funcionario = new Funcionario();
				funcionario.setId(rs.getInt("ID"));
				funcionario.setNome(rs.getString("NOME"));
				funcionario.setIdade(rs.getInt("IDADE"));
				funcionario.setCpf(rs.getString("CPF"));
				funcionario.setEndereco(rs.getString("ENDERECO"));
				funcionario.setDataAdmissao(rs.getDate("DATA_ADMISSAO"));
				funcionario.setDataNascimento(rs.getDate("DATA_NASCIMENTO"));
				funcionario.setEmail(rs.getString("EMAIL"));
				funcionario.setBanco(rs.getString("BANCO"));
				funcionario.setAgencia(rs.getInt("AGENCIA"));
				funcionario.setConta(rs.getInt("CONTA"));
				
				funcionarios.add(funcionario);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(connection, stmt);
		}
		return funcionarios;
	}

	@Override
	public List<String> getBancos() {
		List<String> bancos = new ArrayList<String>();
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = getConnection();

			String query = "select distinct(banco) from funcionario";
			stmt = connection.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				bancos.add(rs.getString("BANCO"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(connection, stmt);
		}
		return bancos;
	}

	@Override
	public List<Funcionario> getFuncionariosBanco(String selectedBancosFuncionarios) {
		List<Funcionario> funcionarios = new ArrayList<Funcionario>();
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = getConnection();

			String query = "select * from funcionario where banco = ?";
			stmt = connection.prepareStatement(query);
			stmt.setString(1, selectedBancosFuncionarios);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Funcionario funcionario = new Funcionario();
				funcionario.setId(rs.getInt("ID"));
				funcionario.setNome(rs.getString("NOME"));
				funcionario.setIdade(rs.getInt("IDADE"));
				funcionario.setCpf(rs.getString("CPF"));
				funcionario.setEndereco(rs.getString("ENDERECO"));
				funcionario.setDataAdmissao(rs.getDate("DATA_ADMISSAO"));
				funcionario.setDataNascimento(rs.getDate("DATA_NASCIMENTO"));
				funcionario.setEmail(rs.getString("EMAIL"));
				funcionario.setBanco(rs.getString("BANCO"));
				funcionario.setAgencia(rs.getInt("AGENCIA"));
				funcionario.setConta(rs.getInt("CONTA"));
				
				funcionarios.add(funcionario);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(connection, stmt);
		}
		return funcionarios;
	}

}
