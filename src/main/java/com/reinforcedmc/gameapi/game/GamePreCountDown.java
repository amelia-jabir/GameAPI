package com.reinforcedmc.gameapi.game;

import com.reinforcedmc.gameapi.GameAPI;
import com.reinforcedmc.gameapi.events.api.GamePreStartEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

public class GamePreCountDown extends BukkitRunnable {

    public final int cooldown = 30;
    public int currentCD = cooldown;

    public void start() {
        GameAPI.getInstance().status = GameStatus.PRECOUNTDOWN;
        Bukkit.getOnlinePlayers().forEach((p) -> p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1F, 1F));
        Bukkit.broadcastMessage(ChatColor.AQUA + "The game is starting in " + ChatColor.DARK_AQUA + ChatColor.BOLD + currentCD + ChatColor.AQUA + " seconds.");
        this.runTaskTimer(GameAPI.getInstance(), 0, 20);
    }

    @Override
    public void run() {

        if(GameAPI.getInstance().ingame.size() < GameAPI.getInstance().currentGame.getMinPlayers()) {
            GameAPI.getInstance().status = GameStatus.LOBBY;
            Bukkit.getOnlinePlayers().forEach((p) -> p.setLevel(0));
            this.cancel();
            return;
        }

        if(currentCD > 0) {
            Bukkit.getOnlinePlayers().forEach((p) -> p.setLevel(currentCD));

            if(currentCD < 5 || currentCD == 15) {
                Bukkit.getOnlinePlayers().forEach((p) -> p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1F, 1F));
                Bukkit.broadcastMessage(ChatColor.AQUA + "The game is starting in " + ChatColor.DARK_AQUA + ChatColor.BOLD + currentCD + ChatColor.AQUA + " seconds.");
            }

            currentCD--;
        } else {
            Bukkit.getServer().getPluginManager().callEvent(new GamePreStartEvent(GameAPI.getInstance().currentGame));
            this.cancel();
        }

    }

}
