package net.fycraft.eventhandler;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerRegisterEvent extends Event {
	private static final HandlerList handlers = new HandlerList();
	private Player player;
	
	
	
	public PlayerRegisterEvent(Player player) {
		this.player = player;
	}
	
	
	// M�todo obrigat�rio
	public HandlerList getHandlers() {
		return handlers;
	}
	
	// M�todo obrigat�rio
	public static HandlerList getHandlerList(){
		return handlers;
	}
	
	public Player getPlayer(){
		return this.player;
	}
	
}
