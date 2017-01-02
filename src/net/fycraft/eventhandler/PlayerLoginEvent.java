package net.fycraft.eventhandler;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerLoginEvent extends Event {
	private static final HandlerList handlers = new HandlerList();
	private Player player;
	private boolean isFirstTime;
		
	
	public PlayerLoginEvent(Player player, Boolean isFirstTime) {
		this.player = player;
		this.isFirstTime = isFirstTime;
	}
	
	// Método obrigatório
	public HandlerList getHandlers() {
		return handlers;
	}
	
	// Método obrigatório
	public static HandlerList getHandlerList(){
		return handlers;
	}
	
	public Player getPlayer(){
		return this.player;
	}
	
	public Boolean isFirstTime(){
		return this.isFirstTime;
	}
}