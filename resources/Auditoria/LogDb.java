package br.com.splgenerator.audit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

@ManagedBean(name="logDB")
@SessionScoped
public class LogDb implements ILog {
	
	private DataSource ds;

	public LogDb() throws NamingException {
		InitialContext ctx = new InitialContext();
		ds = (DataSource) ctx.lookup("jdbc/reembolso");
	}
	
	protected void close(Connection connection, PreparedStatement stmt) {
		closeConnection(connection);

		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	protected void closeConnection(Connection connection) {
		try {
			if (connection != null) {
				System.out.println("fechando : " + connection);
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected Connection getConnection() {
		try {
			Connection connection = ds.getConnection();
			System.out.println("aberto : " + connection);
			return connection;
		} catch (SQLException e) {
			throw new IllegalArgumentException("Erro ao recuperar a conexao com o banco de dados!", e);
		}
	}
	
	@Override
	public void log(String level, String msg) {
		System.out.println("Inserindo log no banco. level: [" + level + "] | msg : [" + msg + "]");
		
		Connection connection = null;
		PreparedStatement stmt = null;
		
		try {
			connection = getConnection();
			
			String query = "insert into AUDIT_LOG (LEVEL, MSG) values (?,?)";
			stmt = connection.prepareStatement(query);
			stmt.setString(1, level);
			stmt.setString(2, msg);
			int ret = stmt.executeUpdate();
			System.out.println("Log inserido no banco: " + ret);
			
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			close(connection, stmt);
		}
		
	}

}
