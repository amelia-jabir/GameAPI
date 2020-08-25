package com.reinforcedmc.gameapi.events;

import com.reinforcedmc.gameapi.GameAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class WorldChangeEvent implements Listener {

    @EventHandler
    public void onPortal(PlayerPortalEvent e) {
        if(GameAPI.getInstance().currentGame.getGameFlags().isAllowNether()) {
            if(e.getTo().getWorld().getEnvironment() == World.Environment.NETHER) {
                e.setTo(new Location(Bukkit.getWorld(GameAPI.getInstance().currentGame.worldname + "_nether"), e.getTo().getX(), e.getTo().getY(), e.getTo().getZ()));
            }
            if(e.getTo().getWorld().getEnvironment() == World.Environment.NORMAL) {
                e.setTo(new Location(Bukkit.getWorld(GameAPI.getInstance().currentGame.worldname), e.getTo().getX(), e.getTo().getY(), e.getTo().getZ()));
            }
            return;
        }

        e.setCancelled(true);
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e) {
        if(GameAPI.getInstance().currentGame.getGameFlags().isAllowEnd()) {
            if(e.getTo().getWorld().getEnvironment() == World.Environment.THE_END) {
                e.setTo(new Location(Bukkit.getWorld(GameAPI.getInstance().currentGame.worldname + "_the_end"), e.getTo().getX(), e.getTo().getY(), e.getTo().getZ()));
            }
            return;
        }

        if (e.getCause().equals(PlayerTeleportEvent.TeleportCause.END_PORTAL))
            e.setCancelled(true);
        if (e.getCause().equals(PlayerTeleportEvent.TeleportCause.END_GATEWAY))
            e.setCancelled(true);

    }

}
