package com.reinforcedmc.gameapi.utils;

import com.reinforcedmc.gameapi.GameAPI;
import com.reinforcedmc.gameapi.game.GamePreCountDown;
import com.reinforcedmc.gameapi.game.GameStatus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

public class GameUtils {

    GameAPI gameAPI;
    
    public GameUtils(GameAPI gameAPI) {
        this.gameAPI = gameAPI;
    }
    
    public void tryStarting() {

        boolean cancel = false;

        if(gameAPI.getLocationsConfig().lobby == null) {
            cancel = true;
            Bukkit.broadcastMessage(gameAPI.prefix + ChatColor.RED + "ERROR: You need to set a lobby spawn before playing!");
        }

        if(gameAPI.currentGame == null) {
            cancel = true;
            Bukkit.broadcastMessage(gameAPI.prefix + ChatColor.RED + "ERROR: No game was specified in config.");
        }

        if(gameAPI.getBungeeUtils().enabled && gameAPI.getBungeeUtils().hubs.isEmpty()) {
            cancel = true;
            Bukkit.broadcastMessage(gameAPI.prefix + ChatColor.RED + "ERROR: You haven't specified any servers in config!");
        }

        if(cancel) return;

        if(gameAPI.ingame.size() < gameAPI.currentGame.getMinPlayers()) {
            Bukkit.broadcastMessage(gameAPI.prefix + ChatColor.RED + "" + (gameAPI.currentGame.getMinPlayers() - gameAPI.ingame.size()) + " more players are needed to start the game!");
            return;
        }

        if(gameAPI.status != GameStatus.LOBBY) return;

        gameAPI.preCountDown = new GamePreCountDown();
        gameAPI.preCountDown.start();
    }

    public void resetPlayer(Player p) {
        p.getInventory().clear();
        p.getInventory().setArmorContents(null);
        p.setGameMode(GameMode.SURVIVAL);
        p.setHealth(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
        p.setFoodLevel(20);
        p.setLevel(0);
        p.setExp(0);
        p.setFoodLevel(20);
        p.setFireTicks(0);
        p.getActivePotionEffects().clear();
    }

}
