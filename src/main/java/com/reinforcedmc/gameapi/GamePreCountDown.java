package com.reinforcedmc.gameapi;

import com.reinforcedmc.gameapi.events.GameSetupEvent;
import com.reinforcedmc.gameapi.events.GameStartEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

public class GamePreCountDown extends BukkitRunnable {

    int cooldown = 30;
    public int currentCD = cooldown;

    public void start() {
        GameAPI.getInstance().status = GameStatus.PRECOUNTDOWN;
        Bukkit.getOnlinePlayers().forEach((p) -> p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1F, 1F));
        Bukkit.broadcastMessage(GameAPI.getInstance().prefix + "Game starts in " + ChatColor.GOLD + ChatColor.BOLD + currentCD + " seconds.");
        this.runTaskTimer(GameAPI.getInstance(), 0, 20);
    }

    @Override
    public void run() {

        if(Bukkit.getOnlinePlayers().size() < GameAPI.getInstance().currentGame.getMinPlayers()) {
            Bukkit.broadcastMessage(GameAPI.prefix + ChatColor.RED + "" + (GameAPI.getInstance().currentGame.getMinPlayers() - Bukkit.getOnlinePlayers().size()) + " more players are needed to start the game!");
            GameAPI.getInstance().status = GameStatus.LOBBY;
            Bukkit.getOnlinePlayers().forEach((p) -> p.setLevel(0));
            this.cancel();
            return;
        }

        if(currentCD > 0) {
            Bukkit.getOnlinePlayers().forEach((p) -> p.setLevel(currentCD));

            if(currentCD < 5 || currentCD == 15) {
                Bukkit.getOnlinePlayers().forEach((p) -> p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1F, 1F));
                Bukkit.broadcastMessage(GameAPI.getInstance().prefix + "Game starts in " + ChatColor.GOLD + ChatColor.BOLD + currentCD + " seconds.");
            }

            currentCD--;
        } else {
            Bukkit.getServer().getPluginManager().callEvent(new GameSetupEvent(GameAPI.getInstance().currentGame));
            new GamePostCountDown().start();
            GameAPI.getInstance().preCountDown = null;
            this.cancel();
        }

    }

}
