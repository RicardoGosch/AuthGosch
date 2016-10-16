package net.fycraft.events;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import net.fycraft.AuthGosch;

public class EvtPlayerMove implements Listener {
	HashMap<Player, Boolean> isLogged = new HashMap<>();
	AuthGosch plugin;
	
	public EvtPlayerMove(AuthGosch plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void playerMove(PlayerMoveEvent e){
		if(plugin.getPlayerLogged().isLogged(e.getPlayer())){
			return;
		}
		
		Integer x = (e.getFrom().getBlockX() - e.getTo().getBlockX());
		if(x < 0) x =  x * -1;
		
		Integer y = (e.getFrom().getBlockY() - e.getTo().getBlockY());
		if(y < 0) y =  y * -1;

		Integer z = (e.getFrom().getBlockZ() - e.getTo().getBlockZ());
		if(z < 0) z =  z * -1;

		if((x > 0) || (z > 0)){
			e.getPlayer().teleport(e.getFrom());
		}
		
	}
	
}
