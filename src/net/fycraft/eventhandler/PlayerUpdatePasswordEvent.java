package net.fycraft.eventhandler;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerUpdatePasswordEvent extends Event {

	private static final HandlerList handlers = new HandlerList();
	private Player player;
	private String oldPass;
	private String newPass;

	public PlayerUpdatePasswordEvent(Player player, String oldPass, String newPass) {
		this.player = player;
		this.oldPass = oldPass;
		this.newPass = newPass;
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

	public String getOldPass() {
		return oldPass;
	}

	public String getNewPass() {
		return newPass;
	}
}
