package net.fycraft.eventhandler;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerLoginEvent extends Event {
	private Player player;
	private boolean isFirstTime;
	
	private static final HandlerList handlers = new HandlerList();
		
	
	public PlayerLoginEvent(Player player, Boolean isFirstTime) {
		this.player = player;
		this.isFirstTime = isFirstTime;
	}
	
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
        return handlers;
    }
	
	public Player getPlayer(){
		return player;
	}
	
	public Boolean isFirstTime(){
		return isFirstTime;
	}
}