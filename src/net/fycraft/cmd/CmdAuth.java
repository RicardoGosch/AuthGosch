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
		switch (args.length) {
		case 0:
			// Mostra todas as funções do comando
			
			break;
		case 1:
			// 			
			
			break;
		case 2:
			
			break;
		case 3:
			// Trocar senha
			if(sender instanceof Player) changePass((Player)sender, args);
			
			break;

		default:
			
			break;
		}
		return false;
	}


	private void changePass(Player player, String[] args) {
		String controller = args[0];
		String lastPass = args[1];
		String newPass = args[2];
		System.out.println("Aqui 00");
		
		if(!controller.equalsIgnoreCase("trocarsenha") || !controller.equalsIgnoreCase("mudarsenha")){
			System.out.println("Aqui 01");
			return;
		}
		if(!plugin.getPlayerLogged().isLogged(player)){
			System.out.println("Aqui 02");
			return;
		}
		if(!User.exists(player)){
			System.out.println("Aqui 03");
			return;
		}
		if(!User.auth(player, lastPass)){
			System.out.println("Aqui 04");
			return;
		}
		if(!User.changePassword(player, lastPass, newPass)){
			System.out.println("Aqui 05");
			return;
		}
		player.sendMessage("[AuthGosch] §6Senha alterada com sucesso!");
		
		
	}
	
}
