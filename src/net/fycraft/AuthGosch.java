package net.fycraft;

import java.io.File;
import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.fycraft.cmd.CmdAuth;
import net.fycraft.cmd.CmdLogin;
import net.fycraft.cmd.CmdRegister;
import net.fycraft.events.EvtPlayerChat;
import net.fycraft.events.EvtPlayerInventory;
import net.fycraft.events.EvtPlayerJoin;
import net.fycraft.events.EvtPlayerLeave;
import net.fycraft.events.EvtPlayerMove;
import net.fycraft.helper.PlayerLoginList;

public class AuthGosch extends JavaPlugin {
	private static PlayerLoginList playerLogged;
	private FileConfiguration messages;
	private File fileMessages;

	@Override
	public void onEnable() {
		// TODO Ativação do plugin

		// Instancia do objeto da lista de jogadores logados ou não
		playerLogged = new PlayerLoginList();

		// Handlers
		registerEvents();
		registerCommands();
		loadFile();

		// Kicka todos os jogadores que estiverem online
		Collection<? extends Player> players = Bukkit.getOnlinePlayers();
		for (Player player : players)
			player.kickPlayer("§cO servidor está reiniciando\n§6Entre novamente em alguns instantes.");

	}

	@Override
	public void onDisable() {
		// TODO Desativação do plugin

	}

	public PlayerLoginList getLogin() {
		return playerLogged;
	}

	private void registerEvents() {
		// TODO Registra todos os eventos do plugin

		// Player Move
		Bukkit.getPluginManager().registerEvents(new EvtPlayerMove(this), this);

		// Player Join
		Bukkit.getPluginManager().registerEvents(new EvtPlayerJoin(this), this);

		// Player Leave
		Bukkit.getPluginManager().registerEvents(new EvtPlayerLeave(this), this);

		// Player Chat
		Bukkit.getPluginManager().registerEvents(new EvtPlayerChat(this), this);

		// Player Inventory
		Bukkit.getPluginManager().registerEvents(new EvtPlayerInventory(this), this);
	}

	private void registerCommands() {
		// TODO Registra todos os comandos do plugin
		getCommand("login").setExecutor(new CmdLogin(this));
		getCommand("register").setExecutor(new CmdRegister(this));
		getCommand("auth").setExecutor(new CmdAuth(this));
	}

	private void loadFile() {
		if (!getDataFolder().exists()) {
			getDataFolder().mkdirs();
		}

		if (!fileMessages.exists())
			saveResource("messages.yml", false);
		messages = YamlConfiguration.loadConfiguration(fileMessages);
	}

	public FileConfiguration getMessages() {
		return messages;
	}

}
