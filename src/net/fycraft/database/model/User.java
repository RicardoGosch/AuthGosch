package net.fycraft.database.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.GregorianCalendar;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.fycraft.database.DAOConector;
import net.fycraft.eventhandler.PlayerLoginEvent;
import net.fycraft.helper.Encryption;

public class User {

	public static boolean exists(Player player) {
		Connection conn = DAOConector.getConnection();
		String sql = "SELECT * FROM user WHERE user_name=?;";
		try {
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, player.getName());
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {
				DAOConector.close(conn, pstm, rs);
				return true;
			}
			DAOConector.close(conn, pstm, rs);
			return false;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return false;
		}
	}

	public static boolean register(Player player, String password) {
		if (exists(player))
			return false;

		if(registerUser(player)) return false;
		if(registerPassword(player, password)) return false;
		
		Bukkit.getServer().getPluginManager().callEvent(new PlayerLoginEvent(player, true));
		return true;

	}

	public static boolean auth(Player player, String password) {
		if (!exists(player))
			return false;

		Connection conn = DAOConector.getConnection();
		String sql = "SELECT password_encrypted FROM password WHERE user_name=? AND password_use=1;";
		try {
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, player.getName());
			ResultSet rs = pstm.executeQuery();
			if (!rs.next()) {
				DAOConector.close(conn, pstm, rs);
				return false;
			}
			if (Encryption.compare(rs.getString("password_encrypted"), password)) {
				DAOConector.close(conn, pstm, rs);
				return true;
			}
			DAOConector.close(conn, pstm, rs);
			return false;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return false;
		}
	}

	public static boolean changePassword(Player player, String lastPass, String newPass) {
		if (!exists(player))
			return false;
		
		return !registerPassword(player, newPass);
	}

	private static boolean registerUser(Player player) {
		Connection conn = DAOConector.getConnection();
		String sql = "INSERT INTO user VALUES(?, ?);";
		try {
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, player.getName());
			pstm.setDate(2, new Date(new GregorianCalendar().getTimeInMillis()));
			Boolean value = pstm.execute();
			DAOConector.close(conn, pstm);
			return value;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			DAOConector.close(conn);
			return false;
		}
	}

	private static boolean registerPassword(Player player, String password) {
		try {
			Connection conn = DAOConector.getConnection();
			// Deixando todas as outras senhas do usuário desabilitadas;
			String sql = "UPDATE password SET password_use=? WHERE user_name=?;";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1, 0);
			pstm.setString(2, player.getName());
			pstm.execute();
			DAOConector.close(conn, pstm);

			conn = DAOConector.getConnection();
		
		
			sql = "INSERT INTO password VALUES(null, ?, ?, ?, ?);";
		
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, player.getName());
			pstm.setString(2, Encryption.encrypt(password));
			pstm.setDate(3, new Date(new GregorianCalendar().getTimeInMillis()));
			pstm.setInt(4, 1);
			Boolean result = pstm.execute();
			DAOConector.close(conn, pstm);
			return result;

		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return false;
		}

	}

}
