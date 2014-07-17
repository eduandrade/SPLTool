package br.com.splgenerator.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import br.com.splgenerator.dao.ModRhDao;

public abstract class JdbcBaseDao implements ModRhDao {
	
	private static final long serialVersionUID = 1L;

	private DataSource ds;
	
	private Context initalContext;

	public JdbcBaseDao() throws NamingException {
		initalContext = new InitialContext();
		Context ctx = (Context) initalContext.lookup("java:comp/env");
		//InitialContext ctx = new InitialContext();
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
	
}
