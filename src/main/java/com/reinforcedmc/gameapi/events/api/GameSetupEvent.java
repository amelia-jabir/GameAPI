package com.reinforcedmc.gameapi.events.api;

import com.reinforcedmc.gameapi.game.Game;
import com.reinforcedmc.gameapi.GameAPI;
import com.reinforcedmc.gameapi.game.GameStatus;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
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
        GameAPI.getInstance().status = GameStatus.SETUP;
    }

    public void openServer() {
        GameAPI.getInstance().status = GameStatus.LOBBY;
    }

}
