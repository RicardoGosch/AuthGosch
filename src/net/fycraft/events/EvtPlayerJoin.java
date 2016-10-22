package net.fycraft.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.connorlinfoot.titleapi.TitleAPI;

import net.fycraft.AuthGosch;
import net.fycraft.database.model.User;

public class EvtPlayerJoin implements Listener {

	AuthGosch plugin;

	public EvtPlayerJoin(AuthGosch plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		Boolean isRegistered = User.exists(player);

		for (int a = 0; a < 15; a++)
			player.sendMessage("");
		if (isRegistered) {
			player.sendMessage("  §6Você ou alguém já entrou neste servidor\n" + "  com esta conta.\n");
			player.sendMessage(
					"  §6Para aproveitar tudo que temos a oferecer\n" + "  precisamos que autentique-se.\n\n");
			player.sendMessage("");
			player.sendMessage("  §cUse: §6/entrar [senha]");
		} else {
			player.sendMessage("  §6Para garantir que ninguém utilize sua conta\n"
					+ "  precisamos que cadastre uma senha única.\n");
			player.sendMessage("");
			player.sendMessage(
					"  §6Toda vez que voltar, será necessário\n" + "  utilizá-la. Por isso memorize-a bem.\n\n");
			player.sendMessage("");
			player.sendMessage("  §cUse: §6/registrar [senha] [senha]");
		}
		for (int a = 0; a < 2; a++)
			player.sendMessage("");

		plugin.getLogin().addPlayer(player);
		
		// Repeater
		new BukkitRunnable() {
			Integer timeOut = 0;

			@Override
			public void run() {
				timeOut++;
				if (isRegistered) {
					TitleAPI.sendTitle(player, 1, 20, 1, "§6§lFyCraft", "§c/entrar [senha]");
				} else {
					TitleAPI.sendTitle(player, 0, 40, 0, "§6§lFyCraft", "§c/registrar [senha] [senha]");
				}
				if (plugin.getLogin().isLogged(player)) {
					TitleAPI.clearTitle(player);
					this.cancel();
					return;
				}
				if (!player.isOnline()) {
					this.cancel();
					return;
				}
				if (timeOut >= 35) {
					if(isRegistered){
						player.kickPlayer("§cTempo de autenticação esgotado!\n§6Use: /entrar [senha]");
						this.cancel();
					} else {
						player.kickPlayer("§cTempo de autenticação esgotado!\n§6Use: /registrar [senha] [senha]");
						this.cancel();
					}
					player.kickPlayer("§cTempo de autenticação esgotado!");
					this.cancel();
					return;
				}

			}
		}.runTaskTimer(plugin, 0L, 30L);
		e.getPlayer().teleport(new Location(Bukkit.getWorld("world_spawn"), 561, 6, 1205));
		if (player.isFlying())
			player.setFlying(false);

		e.setJoinMessage(null);
	}
}