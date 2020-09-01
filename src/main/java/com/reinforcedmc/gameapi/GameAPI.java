package com.reinforcedmc.gameapi;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.reinforcedmc.gameapi.api.API;
import com.reinforcedmc.gameapi.commands.GameCMD;
import com.reinforcedmc.gameapi.config.GameConfig;
import com.reinforcedmc.gameapi.config.LocationsConfig;
import com.reinforcedmc.gameapi.events.*;
import com.reinforcedmc.gameapi.events.api.GameSetupEvent;
import com.reinforcedmc.gameapi.game.*;
import com.reinforcedmc.gameapi.utils.BungeeUtils;
import com.reinforcedmc.gameapi.utils.GameUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.UUID;

public class GameAPI extends JavaPlugin implements Listener, PluginMessageListener {

    private static GameAPI instance;
    private API api;
    private GameManager gameManager;
    private GameConfig gameConfig;
    private LocationsConfig locationsConfig;
    private GameUtils gameUtils;
    private BungeeUtils bungeeUtils;

    public String serverName;
    public boolean gameServer = false;

    public ArrayList<UUID> ingame = new ArrayList<>();

    public Game currentGame;
    public GameStatus status = GameStatus.SETUP;
    public GameType gameType = GameType.DEFAULT;

    public static String prefix;

    public GamePreCountDown preCountDown;

    @Override
    public void onEnable() {

        instance = this;

        gameUtils = new GameUtils(this);
        bungeeUtils = new BungeeUtils();

        locationsConfig = new LocationsConfig();
        gameManager = new GameManager();
        gameConfig = new GameConfig();
        api = new API();

        prefix = currentGame.getPrefix() + ChatColor.DARK_GRAY + ChatColor.BOLD + "> " + ChatColor.RESET + ChatColor.AQUA;

        if(bungeeUtils.enabled) {
            this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);
            this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

            new BukkitRunnable() {
                @Override
                public void run() {
                    bungeeUtils.getServerPlayerCounts();
                }
            }.runTaskTimerAsynchronously(this, 0L, 100L);
        }

        if(gameServer) {
            this.getCommand("game").setExecutor(new GameCMD());

            Bukkit.getServer().getPluginManager().registerEvents(new PlayerJoinQuitEvent(), this);
            Bukkit.getServer().getPluginManager().registerEvents(new ScoreboardUpdateEvent(), this);
            Bukkit.getServer().getPluginManager().registerEvents(new DamageEvent(), this);
            Bukkit.getServer().getPluginManager().registerEvents(new WorldChangeEvent(), this);
            Bukkit.getServer().getPluginManager().registerEvents(new BuildEvent(), this);
        }

        Bukkit.getServer().getPluginManager().callEvent(new GameSetupEvent(currentGame));

    }

    public API getAPI() {
        return api;
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public LocationsConfig getLocationsConfig() {
        return locationsConfig;
    }

    public GameConfig getGameConfig() {
        return gameConfig;
    }

    public BungeeUtils getBungeeUtils() {
        return bungeeUtils;
    }

    public GameUtils getGameUtils() {
        return gameUtils;
    }

    public static GameAPI getInstance() {
        return instance;
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        ByteArrayDataInput in = ByteStreams.newDataInput(message);

        String server = in.readUTF();
        int playercount = in.readInt();
        bungeeUtils.hubs.replace(server, playercount);

    }
}
