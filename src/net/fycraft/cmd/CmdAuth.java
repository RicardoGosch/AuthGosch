package net.fycraft.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.fycraft.AuthGosch;
import net.fycraft.database.model.User;

public class CmdAuth implements CommandExecutor {

	private AuthGosch plugin;

	public CmdAuth(AuthGosch plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		/*
		 * TODO Responsável pela captação do comando de modificação de dados dos
		 * jogadores.
		 * 
		 * USE: /<command> [controller] [args...]
		 *
		 * <command> = [auth]
		 *
		 */

		// Começamos filtrando pela quantia de argumentos
		switch (args.length) {
		case 0:
			// Mostra todas as funções do comando
			// Planos para um menu GUI
			break;
		case 1:
			//

			break;
		case 2:

			break;
		case 3:
			String controller = args[0].toLowerCase();

			// Controler de modificar a senha
			if (controller.equals("trocarsenha") || controller.equals("mudarsenha")) {
				// O player trocando a própria senha
				// /<command> [controller] [lastPass] [newPass]
				if (sender instanceof Player)
					changePass((Player) sender, args);
			}

			break;

		default:

			break;
		}
		return false;
	}

	private void changePass(Player player, String[] args) {
		/*
		 * TODO Responsável pela alteração da senha do player. Será utilizado
		 * somente para o player trocar sua própria senha
		 */

		String lastPass = args[1];
		String newPass = args[2];

		// Verifica se o player está logado
		if (!plugin.getLogin().isLogged(player))
			return;

		// Verifica se o player existe no banco
		if (!User.exists(player)) {
			player.sendMessage("[FyCraft] §cErro: §6A senha não foi alterada por um problema interno!");
			return;
		}

		// Verifica se a senha antiga confere com a do Banco
		if (!User.auth(player, lastPass)) {
			player.sendMessage("[FyCraft] §cErro: §6A senha não corresponde!");
			return;
		}

		// Modifica a senha no banco
		if (!User.changePassword(player, newPass)) {
			player.sendMessage("[FyCraft] §cErro: §6A senha não foi alterada por um problema interno!");
			return;
		}

		player.sendMessage("[FyCraft] §6Senha alterada com sucesso!");

	}

}
