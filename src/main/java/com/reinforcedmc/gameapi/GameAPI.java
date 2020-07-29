package com.reinforcedmc.gameapi;

import com.reinforcedmc.gameapi.api.API;
import com.reinforcedmc.gameapi.commands.GameCMD;
import com.reinforcedmc.gameapi.config.GameConfig;
import com.reinforcedmc.gameapi.config.LocationsConfig;
import com.reinforcedmc.gameapi.events.GameStartEvent;
import com.reinforcedmc.gameapi.scoreboard.CustomScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.ArrayList;
import java.util.UUID;

public class GameAPI extends JavaPlugin implements Listener {

    static GameAPI instance;
    static API api;
    static GameManager gameManager;
    static GameConfig gameConfig;
    static LocationsConfig locationsConfig;

    public static boolean gameServer = false;

    public ArrayList<UUID> ingame = new ArrayList<>();
    public GameStatus status = GameStatus.LOBBY;

    public Game currentGame;
    public static String prefix;

    public GamePreCountDown preCountDown;

    @Override
    public void onEnable() {

        instance = this;

        locationsConfig = new LocationsConfig();
        gameManager = new GameManager();
        gameConfig = new GameConfig();
        api = new API();

        prefix = currentGame.getPrefix() + ChatColor.DARK_GRAY + ChatColor.BOLD + "> " + ChatColor.RESET + ChatColor.GRAY;

        if(gameServer) {
            Bukkit.getServer().getPluginCommand("game").setExecutor(new GameCMD());
            Bukkit.getServer().getPluginManager().registerEvents(this, this);
        }

    }

    @Override
    public void onDisable() {
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.setJoinMessage(ChatColor.GREEN + "[+] " + e.getPlayer().getName());

        tryStarting();

        new CustomScoreboard(e.getPlayer(), "GameAPI", "lmao1", "lol2").init();

        if(status == GameStatus.LOBBY || status == GameStatus.PRECOUNTDOWN) {
            if(locationsConfig.lobby != null) {
                e.getPlayer().teleport(locationsConfig.lobby, PlayerTeleportEvent.TeleportCause.PLUGIN);
            } else {
                e.getPlayer().sendMessage(prefix + ChatColor.RED + "WARNING: You need to set a lobby spawn!");
            }
        }

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        e.setQuitMessage(ChatColor.RED + "[-] " + e.getPlayer().getName());
    }

    public void tryStarting() {
        if(currentGame == null) {
            Bukkit.broadcastMessage(prefix + ChatColor.RED + "ERROR: No game was specified in config.");
            return;
        }

        if(Bukkit.getOnlinePlayers().size() < currentGame.getMinPlayers()) {
            Bukkit.broadcastMessage(prefix + ChatColor.RED + "" + (currentGame.getMinPlayers() - Bukkit.getOnlinePlayers().size()) + " more players are needed to start the game!");
            return;
        }

        if(status != GameStatus.LOBBY) return;

        preCountDown = new GamePreCountDown();
        preCountDown.start();
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

    public static GameAPI getInstance() {
        return instance;
    }

}
