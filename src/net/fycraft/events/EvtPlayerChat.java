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
		System.out.println("aqui");
		if (plugin.getPlayerLogged().isLogged(e.getPlayer())) {
			return;
		}
		e.setCancelled(true);
		e.getPlayer().sendMessage("Você deve autenticar-se antes!");
	}

	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent e) {
		if (plugin.getPlayerLogged().isLogged(e.getPlayer())) {
			return;
		}
		if (e.getMessage().toLowerCase().startsWith("/login ") || e.getMessage().toLowerCase().startsWith("/register ")
				|| e.getMessage().toLowerCase().startsWith("/registrar ")
				|| e.getMessage().toLowerCase().startsWith("/logar ")
				|| e.getMessage().toLowerCase().startsWith("/cadastrar ")
				|| e.getMessage().toLowerCase().startsWith("/entrar ")) {
			return;
		}
		e.setCancelled(true);

	}
}
