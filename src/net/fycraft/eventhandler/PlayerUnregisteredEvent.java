package net.fycraft.eventhandler;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerUnregisteredEvent extends Event {

	private static final HandlerList handlers = new HandlerList();
	private Player player;

	public PlayerUnregisteredEvent(Player player) {
		this.player = player;
	}

	// Método obrigatório
	public HandlerList getHandlers() {
		return handlers;
	}

	// Método obrigatório
	public static HandlerList getHandlerList() {
		return handlers;
	}

	public Player getPlayer() {
		return this.player;
	}
}
