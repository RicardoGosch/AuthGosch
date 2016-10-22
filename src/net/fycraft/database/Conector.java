package net.fycraft.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Conector {
	
	private final static String DRIVER = "com.mysql.jdbc.Driver";
	private final static String URL = "jdbc:mysql://localhost:3306/fycraft_survival";
	private final static String USER = "root";
	private final static String PASS = "";
	
	
	public static Connection getConnection(){
		try {
			Class.forName(DRIVER);
			return DriverManager.getConnection(URL, USER, PASS);

		} catch (ClassNotFoundException | SQLException e) {
			return null;
		}
	}
	
	public static void close(Connection conn){
		if(conn == null) return;
		try {
			conn.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public static void close(Connection conn, PreparedStatement pstm){
		close(conn);
		if(pstm == null) return;
		try {
			pstm.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public static void close(Connection conn, PreparedStatement pstm, ResultSet rs){
		close(conn, pstm);
		if(rs == null) return;
		try {
			rs.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

}
