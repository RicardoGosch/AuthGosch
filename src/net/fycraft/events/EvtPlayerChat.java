package net.fycraft.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import net.fycraft.AuthGosch;

@SuppressWarnings("deprecation")
public class EvtPlayerChat implements Listener {

	AuthGosch plugin;

	public EvtPlayerChat(AuthGosch plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerChat(PlayerChatEvent e) {
		if (plugin.getLogin().isLogged(e.getPlayer())) {
			return;
		}
		e.setCancelled(true);
		e.getPlayer().sendMessage("[AuthGosch] §cErro: §6Para utilizar o chat é necessário que esteja autenticado.");
	}

	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent e) {
		if (plugin.getLogin().isLogged(e.getPlayer())) {
			return;
		}
		if (e.getMessage().toLowerCase().startsWith("/login ") || e.getMessage().toLowerCase().startsWith("/register ")
				|| e.getMessage().toLowerCase().startsWith("/registrar ")
				|| e.getMessage().toLowerCase().startsWith("/logar ")
				|| e.getMessage().toLowerCase().startsWith("/cadastrar ")
				|| e.getMessage().toLowerCase().startsWith("/entrar ")) {
			return;
		}
		e.getPlayer().sendMessage("[AuthGosch] §cErro: §6Para acionar comandos é necessário estar autenticado.");
		e.setCancelled(true);

	}
}
