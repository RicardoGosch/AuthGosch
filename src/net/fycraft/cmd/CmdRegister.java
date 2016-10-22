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
		/*
		 * TODO Respons�vel pela capta��o do comando de registro dos jogadores.
		 * USE: /<command> [pass] [pass]
		 *
		 * <command> = [register, registrar, cadastrar]
		 *
		 */

		// Verifica se � o console executando o comando
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Comando habilitado somente para players!");
			return false;
		}
		Player player = (Player) sender;

		// Verifica se o player j� est� autenticado
		if (plugin.getLogin().isLogged(player)) {
			player.sendMessage("[FyCraft] �6Voc� j� est� autenticado!");
			return true;
		}

		// Verifica se o jogador j� n�o est� cadastrado
		if (User.exists(player)) {
			player.sendMessage("[FyCraft] �cUse: �6/entrar [senha]");
			return true;
		}

		// Verifica se a sintaxe est� correta (/<command> [pass] [pass])
		if (args.length != 2) {
			player.sendMessage("[FyCraft] �cUse: �6/registrar [senha] [senha]");
			return true;
		}

		// Verifica se as duas senhas conferem
		if (!args[0].equals(args[1])) {
			player.sendMessage("[FyCraft] �cErro: �6As duas senhas n�o conferem.");
			return true;
		}

		// Registra o player no banco
		if (User.register(player, args[0])) {
			plugin.getLogin().setLogged(player);
			Bukkit.getServer().getPluginManager().callEvent(new PlayerRegisterEvent(player));
			Bukkit.getServer().getPluginManager().callEvent(new PlayerLoginEvent(player, true));
			player.sendMessage("[FyCraft] �6Que �timo! Voc� foi cadastrado com sucesso!");
			return true;
		}

		// Se chegar at� aqui � por houve um erro na hora de cadastrar a senha
		// do player
		player.sendMessage("[FyCraft] �cErro: �6Enquanto estavamos cadastrando voc�,"
				+ "tivemos algum problema. Recomendamos que contate o suporte " + "para que possamos corrigir isto.");
		return false;
	}

}
