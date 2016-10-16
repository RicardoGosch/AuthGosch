package net.fycraft.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.fycraft.AuthGosch;
import net.fycraft.database.model.User;

public class EvtPlayerJoin implements Listener {
	
	AuthGosch plugin;
	
	public EvtPlayerJoin(AuthGosch plugin) {
		this.plugin = plugin;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e){
		Player player = e.getPlayer();
		player.resetTitle();
		System.out.println(player.getGameMode().toString());

		for(int a = 0; a<15; a++) player.sendMessage("");
		if(User.exists(player)){
			player.sendMessage("  §6Você ou alguém já entrou neste servidor\n"
							 + "  com esta conta.\n");
			player.sendMessage("  §6Para aproveitar tudo que temos a oferecer\n"
						 	 + "  precisamos que autentique-se.\n\n");
			player.sendMessage("");
			player.sendMessage("  §cUse: §6/entrar [senha]");
		} else {
			player.sendMessage("  §6Para garantir que ninguém utilize sua conta\n"
							 + "  precisamos que cadastre uma senha única.\n");
			player.sendMessage("");
			player.sendMessage("  §6Toda vez que voltar, será necessário\n"
							 + "  utilizá-la. Por isso memorize-a bem.\n\n");
			player.sendMessage("");
			player.sendMessage("  §cUse: §6/registrar [senha] [senha]");
		}
		for(int a = 0; a<2; a++) player.sendMessage("");
		
		
		plugin.getPlayerLogged().addPlayer(player);
		e.getPlayer().teleport(new Location(Bukkit.getWorld("world_spawn"), 561, 6, 1205));
		if(player.isFlying())
			player.setFlying(false);
		
		e.setJoinMessage(null);
	}
}