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
import net.fycraft.eventhandler.PlayerRegisterEvent;

public class CmdRegister implements CommandExecutor {

	AuthGosch plugin;

	public CmdRegister(AuthGosch plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player;
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Comando habilitado somente para players!");
			return false;
		}
		player = (Player) sender;

		if (plugin.getPlayerLogged().isLogged(player)) {
			player.sendMessage("[FyCraft] §9Você já está autenticado!");
			return true;
		}

		if (User.exists(player)) {
			player.sendMessage("[FyCraft] §cUse: §6/entrar [senha]");
			return true;
		}

		if (args.length != 2 || !args[0].equals(args[1])) {
			player.sendMessage("[FyCraft] §cUse: §6/registrar [senha] [senha]");
			return true;
		}

		if (args[0].length() < 5) {
			player.sendMessage("[FyCraft] §cCadastre uma senha mais forte!");
			return true;
		}

		if (User.register(player, args[0])) {
			plugin.getPlayerLogged().setLogged(player);
			Bukkit.getServer().getPluginManager().callEvent(new PlayerRegisterEvent(player));
			Bukkit.getServer().getPluginManager().callEvent(new PlayerLoginEvent(player, true));
			player.sendMessage("[FyCraft] §9Cadastrado com sucesso!");
			return true;
		}

		player.sendMessage("[FyCraft] §cAlgo deu errado!");
		return false;
	}

}
