package net.fycraft.helper;

import java.util.HashMap;

import org.bukkit.entity.Player;

public class PlayerLoginList {
	
	private HashMap<Player, Boolean> isLogged = new HashMap<>();

	
	public void addPlayer(Player player){
		isLogged.put(player, false);
	}
	
	public void setLogged(Player player){
		isLogged.replace(player, true);
	}
	
	public void setUnlogged(Player player){
		isLogged.replace(player, false);
	}
	
	public void removePlayer(Player player){
		isLogged.remove(player);
	}
	
	public boolean isLogged(Player player){
		return isLogged.get(player);
	}
}
