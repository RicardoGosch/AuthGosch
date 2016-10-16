package net.fycraft.cmd;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.fycraft.AuthGosch;
import net.fycraft.database.model.User;
import net.fycraft.eventhandler.PlayerLoginEvent;

public class CmdLogin implements CommandExecutor {

	AuthGosch plugin;

	public CmdLogin(AuthGosch plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player;
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Comando habilitado somente para players!");
		}
		player = (Player) sender;

		// Verifica se o player já está autenticado
		if (plugin.getPlayerLogged().isLogged(player)) {
			player.sendMessage("[FyCraft] §9Você já está autenticado!");
			return true;
		}

		// Verifica se o player está cadastrado
		if (!User.exists(player)) {
			player.sendMessage("[FyCraft] §cUse: §6/registrar [senha] [senha]");
		}
		
		// Verifica se o player inseriu mais de 1 argumento
		if (args.length != 1) {
			player.sendMessage("[FyCraft] §cUse: §6/entrar [senha]");
			return true;
		}
		
		// Verifica se o login está correto
		if (User.auth(player, args[0])) {
			player.sendMessage("[FyCraft] §9Autenticado com sucesso!");
			plugin.getPlayerLogged().setLogged(player);
			Bukkit.getServer().getPluginManager().callEvent(new PlayerLoginEvent(player, false));
			return true;
		}
		
		// Caso chegue até aqui, significa que a senha está incorreta
		player.sendMessage("[FyCraft] §cSenha incorreta!");

		plugin.getPlayerLogged().setUnlogged(player);
		return false;
	}
}
