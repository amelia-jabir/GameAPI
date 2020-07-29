package com.reinforcedmc.gameapi;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.UUID;

public class GameAPI extends JavaPlugin implements Listener {

    ArrayList<UUID> ingame = new ArrayList<>();

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if(!ingame.contains(e.getPlayer().getUniqueId())) {
            ingame.add(e.getPlayer().getUniqueId());
        }
        e.setJoinMessage(ChatColor.GREEN + "+ " + e.getPlayer().getName());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        if(ingame.contains(e.getPlayer().getUniqueId())) {
            ingame.remove(e.getPlayer().getUniqueId());
        }
        e.setQuitMessage(ChatColor.RED + "- " + e.getPlayer().getName());
    }

}
