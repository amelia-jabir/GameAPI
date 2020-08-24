package com.reinforcedmc.gameapi.events;

import com.reinforcedmc.gameapi.GameAPI;
import com.reinforcedmc.gameapi.game.GameStatus;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BuildEvent implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent e) {

        if(GameAPI.getInstance().status == GameStatus.POSTCOUNTDOWN || GameAPI.getInstance().status == GameStatus.ENDING) {
            if(GameAPI.getInstance().ingame.contains(e.getPlayer().getUniqueId())) {
                e.setCancelled(true);
                return;
            }
        }

        if(GameAPI.getInstance().currentGame.getGameFlags().isBlockbreak()) return;

        if(GameAPI.getInstance().status == GameStatus.INGAME) {
            e.setCancelled(true);
        }

    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {

        if(GameAPI.getInstance().status == GameStatus.POSTCOUNTDOWN || GameAPI.getInstance().status == GameStatus.ENDING) {
            if(GameAPI.getInstance().ingame.contains(e.getPlayer().getUniqueId())) {
                e.setCancelled(true);
                return;
            }
        }

        if(GameAPI.getInstance().currentGame.getGameFlags().isBlockplace()) return;

        if(GameAPI.getInstance().status == GameStatus.INGAME) {
            e.setCancelled(true);
        }

    }

}
