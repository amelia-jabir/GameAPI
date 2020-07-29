package com.reinforcedmc.gameapi.events;

import com.reinforcedmc.gameapi.Game;
import com.reinforcedmc.gameapi.GameAPI;
import com.reinforcedmc.gameapi.GameStatus;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GameSetupEvent extends Event {

    private Game game;

    private static final HandlerList HANDLERS = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public GameSetupEvent(Game game) {
        this.game = game;

        GameAPI.getInstance().status = GameStatus.POSTCOUNTDOWN;
        Bukkit.getOnlinePlayers().forEach((p) -> p.setLevel(0));
        Bukkit.getOnlinePlayers().forEach((p) -> p.setGameMode(GameMode.SURVIVAL));
        Bukkit.getOnlinePlayers().forEach((p) -> p.playSound(p.getLocation(), Sound.UI_CARTOGRAPHY_TABLE_TAKE_RESULT, 1F, 0.8F));
    }


}
