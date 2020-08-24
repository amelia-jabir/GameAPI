package com.reinforcedmc.gameapi.events;

import com.reinforcedmc.gameapi.GameAPI;
import com.reinforcedmc.gameapi.game.GameStatus;
import com.reinforcedmc.gameapi.scoreboard.CustomScoreboard;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PlayerJoinQuitEvent implements Listener {

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        if(GameAPI.getInstance().status == GameStatus.SETUP) {
            e.setKickMessage(ChatColor.RED + "Game is still being constructed...");
            e.setResult(PlayerLoginEvent.Result.KICK_OTHER);
            return;
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.setJoinMessage(ChatColor.GREEN + "[+] " + e.getPlayer().getName());

        GameAPI.getInstance().getGameUtils().resetPlayer(e.getPlayer());

        if(!GameAPI.getInstance().ingame.contains(e.getPlayer().getUniqueId()))
            GameAPI.getInstance().ingame.add(e.getPlayer().getUniqueId());

        CustomScoreboard sb = new CustomScoreboard(e.getPlayer(), " " + GameAPI.getInstance().currentGame.getPrefix() + " ");
        sb.init();

        GameAPI.getInstance().getGameUtils().tryStarting();

        if(GameAPI.getInstance().status == GameStatus.LOBBY || GameAPI.getInstance().status == GameStatus.PRECOUNTDOWN) {
            GameAPI.getInstance().getAPI().putInNormal(e.getPlayer());
            if(GameAPI.getInstance().getLocationsConfig().lobby != null) {
                e.getPlayer().teleport(GameAPI.getInstance().getLocationsConfig().lobby, PlayerTeleportEvent.TeleportCause.PLUGIN);
            }
        } else {
            GameAPI.getInstance().getAPI().putInSpectator(e.getPlayer());
        }

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        e.setQuitMessage(ChatColor.RED + "[-] " + e.getPlayer().getName());

        if(GameAPI.getInstance().ingame.contains(e.getPlayer().getUniqueId()))
            GameAPI.getInstance().ingame.remove(e.getPlayer().getUniqueId());

        CustomScoreboard.clearPlayer(e.getPlayer());
    }

}
