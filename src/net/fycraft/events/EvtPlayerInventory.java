package net.fycraft.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import net.fycraft.AuthGosch;

public class EvtPlayerInventory implements Listener {
	
	AuthGosch plugin;
	
	public EvtPlayerInventory(AuthGosch plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerInventory(InventoryClickEvent e){
		if(plugin.getPlayerLogged().isLogged((Player)e.getWhoClicked())){
			return;
		}
		e.setCancelled(true);
		
	}
	
	@EventHandler
	public void onPlayerDropInventory(PlayerDropItemEvent e){
		if(plugin.getPlayerLogged().isLogged(e.getPlayer())){
			return;
		}
		e.setCancelled(true);
		
	}
	
	@EventHandler
	public void onPlayerDropInventory(PlayerPickupItemEvent e){
		if(plugin.getPlayerLogged().isLogged(e.getPlayer())){
			return;
		}
		e.setCancelled(true);
		
	}
}
