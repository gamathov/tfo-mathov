package ar.com.codoacodo.repository;

import java.sql.Connection;
import java.sql.DriverManager;

public class AdministradorDeConexiones {
	
	public static Connection getConnection() {
		String host = "localhost";
		String username = "root";
		String password = "Admin00";
		String port = "3306";
		String database = "integrador_cac";
		String dbUrl = "jdbc:mysql://" + host + ":" + port + "/" + database + "?allowPublicKeyRetrieval=true&serverTimeZone=UTC&useSSL=false";		
		String driver = "com.mysql.cj.jdbc.Driver";
		
		Connection con = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(dbUrl, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return con;
		
	}

}
