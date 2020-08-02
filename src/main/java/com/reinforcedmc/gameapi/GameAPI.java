package com.reinforcedmc.gameapi;

import com.reinforcedmc.gameapi.api.API;
import com.reinforcedmc.gameapi.commands.GameCMD;
import com.reinforcedmc.gameapi.config.GameConfig;
import com.reinforcedmc.gameapi.config.LocationsConfig;
import com.reinforcedmc.gameapi.events.GameSetupEvent;
import com.reinforcedmc.gameapi.events.GameStartEvent;
import com.reinforcedmc.gameapi.scoreboard.CustomScoreboard;
import com.reinforcedmc.gameapi.scoreboard.UpdateScoreboardEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
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

    public String serverName;

    public boolean gameServer = false;

    public ArrayList<UUID> ingame = new ArrayList<>();
    public GameStatus status = GameStatus.SETUP;

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
            this.getCommand("game").setExecutor(new GameCMD());
            Bukkit.getServer().getPluginManager().registerEvents(this, this);
        }

        Bukkit.getServer().getPluginManager().callEvent(new GameSetupEvent(currentGame));

    }

    @Override
    public void onDisable() {
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        if(status == GameStatus.SETUP) {
            e.setKickMessage(ChatColor.RED + "Game is still being constructed...");
            e.setResult(PlayerLoginEvent.Result.KICK_OTHER);
            return;
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.setJoinMessage(ChatColor.GREEN + "[+] " + e.getPlayer().getName());

        resetPlayer(e.getPlayer());

        if(!ingame.contains(e.getPlayer().getUniqueId()))
            ingame.add(e.getPlayer().getUniqueId());

        CustomScoreboard sb = new CustomScoreboard(e.getPlayer(), " " + currentGame.getPrefix() + " ");
        sb.init();
        sb.addLine("&9Text");

        tryStarting();

        if(status == GameStatus.LOBBY || status == GameStatus.PRECOUNTDOWN) {
            api.putInNormal(e.getPlayer());
            if(locationsConfig.lobby != null) {
                e.getPlayer().teleport(locationsConfig.lobby, PlayerTeleportEvent.TeleportCause.PLUGIN);
            } else {
                e.getPlayer().sendMessage(prefix + ChatColor.RED + "WARNING: You need to set a lobby spawn!");
            }
        } else {
            api.putInSpectator(e.getPlayer());
        }

    }

    public void resetPlayer(Player p) {
        p.getInventory().clear();
        p.getInventory().setArmorContents(null);
        p.setGameMode(GameMode.SURVIVAL);
        p.setHealth(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
        p.setFireTicks(0);
        p.getActivePotionEffects().clear();
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        e.setQuitMessage(ChatColor.RED + "[-] " + e.getPlayer().getName());

        if(ingame.contains(e.getPlayer().getUniqueId()))
            ingame.remove(e.getPlayer().getUniqueId());

        CustomScoreboard.clearPlayer(e.getPlayer());
    }

    public void tryStarting() {
        if(currentGame == null) {
            Bukkit.broadcastMessage(prefix + ChatColor.RED + "ERROR: No game was specified in config.");
            return;
        }

        if(ingame.size() < currentGame.getMinPlayers()) {
            Bukkit.broadcastMessage(prefix + ChatColor.RED + "" + (currentGame.getMinPlayers() - ingame.size()) + " more players are needed to start the game!");
            return;
        }

        if(status != GameStatus.LOBBY) return;

        preCountDown = new GamePreCountDown();
        preCountDown.start();
    }

    @EventHandler
    public void onSBUpdate(UpdateScoreboardEvent e) {

        if(status == GameStatus.LOBBY || status == GameStatus.PRECOUNTDOWN) {

            Game game = GameAPI.getInstance().currentGame;

            String[] lines = {
                    "&7" + serverName,
                    "",
                    "Players: " + "&b" + ingame.size() + "/" + game.getMaxPlayers(),
                    "",
                    "&3play.reinforced.com"
            };

            if(currentGame.getScoreboardTitles() != null) {
                e.getScoreboard().setTitles(currentGame.getScoreboardTitles());
            } else {
                e.getScoreboard().setTitles(currentGame.getPrefix());
            }
            e.getScoreboard().setLines(lines);
        }

    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if(e.getEntityType() == EntityType.PLAYER) {
            if(status != GameStatus.INGAME) {
                e.setCancelled(true);
            }
        }
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
