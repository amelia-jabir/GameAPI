package com.reinforcedmc.gameapi.events;

import com.reinforcedmc.gameapi.GameAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class WorldChangeEvent implements Listener {

    @EventHandler
    public void onPortal(PlayerPortalEvent e) {
        if(GameAPI.getInstance().currentGame.getGameFlags().isAllowNether()) return;

        e.setCancelled(true);
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e) {
        if(GameAPI.getInstance().currentGame.getGameFlags().isAllowEnd()) return;

        if (e.getCause().equals(PlayerTeleportEvent.TeleportCause.END_PORTAL))
            e.setCancelled(true);
        if (e.getCause().equals(PlayerTeleportEvent.TeleportCause.END_GATEWAY))
            e.setCancelled(true);

    }

}
