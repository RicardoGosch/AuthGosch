package net.fycraft.events;

import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import net.fycraft.AuthGosch;

public class EvtPlayerLeave implements Listener {

	AuthGosch plugin;
	
	public EvtPlayerLeave(AuthGosch plugin) {
		this.plugin = plugin;
	}
	
	public void onPlayerLeave(PlayerQuitEvent e){
		AuthGosch.getLogin().removePlayer(e.getPlayer());
		e.setQuitMessage(null);
	}
	
}
