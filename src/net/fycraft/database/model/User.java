package net.fycraft.database.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.GregorianCalendar;

import org.bukkit.entity.Player;

import net.fycraft.database.Conector;
import net.fycraft.helper.Encryption;

public class User {

	public static boolean exists(Player player) {
		Connection conn = Conector.getConnection();
		String sql = "SELECT * FROM user WHERE user_name=?;";
		try {
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, player.getName());
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {
				Conector.close(conn, pstm, rs);
				return true;
			}
			Conector.close(conn, pstm, rs);
			return false;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return false;
		}
	}

	public static boolean register(Player player, String password) {
		// Caso o usuário já estiver castrado, será cadastrado mais uma senha
		if (!exists(player))
			if (!registerUser(player))
				return false;
		
		// Cadastrando uma senha
		if (!registerPassword(player, password))
			return false;
		
		return true;

	}

	public static boolean auth(Player player, String password) {
		// Verifica se o player está cadastrado
		if (!exists(player))
			return false;

		Connection conn = Conector.getConnection();
		String sql = "SELECT password_encrypted FROM password WHERE user_name=? AND password_use=1;";
		try {
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, player.getName());
			ResultSet rs = pstm.executeQuery();
			if (!rs.next()) {
				Conector.close(conn, pstm, rs);
				return false;
			}
			if (Encryption.compare(rs.getString("password_encrypted"), password)) {
				Conector.close(conn, pstm, rs);
				return true;
			}
			Conector.close(conn, pstm, rs);
			return false;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return false;
		}
	}

	public static boolean changePassword(Player player, String newPass) {
		// Verifica se o player está cadastrado
		if (!exists(player))
			return false;

		return registerPassword(player, newPass);
	}

	private static boolean registerUser(Player player) {
		// Inserindo um novo usuário no banco de dados
		Connection conn = Conector.getConnection();
		String sql = "INSERT INTO user VALUES(?, ?);";
		try {
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, player.getName());
			pstm.setDate(2, new Date(new GregorianCalendar().getTimeInMillis()));
			Boolean result = pstm.execute();
			Conector.close(conn, pstm);
			return !result;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			Conector.close(conn);
			return false;
		}
	}

	private static boolean registerPassword(Player player, String password) {
		try {
			Connection conn = Conector.getConnection();
			
			// Deixando todas as outras senhas do usuário desabilitadas
			String sql = "UPDATE password SET password_use=? WHERE user_name=?;";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1, 0);
			pstm.setString(2, player.getName());
			if (pstm.execute())
				return false;
			Conector.close(conn, pstm);

			// Cadastrando a nova senha
			conn = Conector.getConnection();
			sql = "INSERT INTO password VALUES(null, ?, ?, ?, ?);";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, player.getName());
			pstm.setString(2, Encryption.encrypt(password));
			pstm.setDate(3, new Date(new GregorianCalendar().getTimeInMillis()));
			pstm.setInt(4, 1);
			Boolean result = pstm.execute();
			Conector.close(conn, pstm);
			return !result;

		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return false;
		}

	}

}
