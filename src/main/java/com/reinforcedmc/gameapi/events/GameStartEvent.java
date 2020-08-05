package com.reinforcedmc.gameapi.events;

import com.reinforcedmc.gameapi.Game;
import com.reinforcedmc.gameapi.GameAPI;
import com.reinforcedmc.gameapi.GameStatus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GameStartEvent extends Event {

    private Game game;

    private static final HandlerList HANDLERS = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public GameStartEvent(Game game) {
        this.game = game;
        GameAPI.getInstance().status = GameStatus.INGAME;
        Bukkit.getOnlinePlayers().forEach(p -> GameAPI.getInstance().resetPlayer(p));
    }

}
