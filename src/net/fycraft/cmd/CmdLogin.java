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
		/*
		 * TODO Respons�vel pela capta��o do comando de login dos jogadores.
		 * USE: /<command> [pass]
		 *
		 * <command> = [login, entrar, logar]
		 *
		 */

		// Verifica se � o console usando o comando
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Comando habilitado somente para players!");
			return true;
		}
		Player player = (Player) sender;

		// Verifica se o player j� est� autenticado
		if (plugin.getLogin().isLogged(player)) {
			player.sendMessage("[FyCraft] �6Voc� j� est� autenticado!");
			return true;
		}

		// Verifica se o player est� cadastrado
		if (!User.exists(player)) {
			player.sendMessage("[FyCraft] �cUse: �6/registrar [senha] [senha]");
		}

		// Verifica se o player inseriu mais de 1 argumento
		if (args.length != 1) {
			player.sendMessage("[FyCraft] �cUse: �6/entrar [senha]");
			return true;
		}

		// Verifica se o login est� correto
		if (User.auth(player, args[0])) {
			player.sendMessage("[FyCraft] �6Autenticado com sucesso!");

			// Seta o player como logado na lista
			plugin.getLogin().setLogged(player);

			// Lan�a o evento
			Bukkit.getServer().getPluginManager().callEvent(new PlayerLoginEvent(player, false));
			return true;
		}

		// Caso chegue at� aqui, significa que a senha est� incorreta
		player.sendMessage("[FyCraft] �cErro: �6A senha informada est� incorreta!");

		// Por seguran�a, for�a na lista que o player continue n�o autenticado
		plugin.getLogin().setUnlogged(player);
		return true;
	}
}
