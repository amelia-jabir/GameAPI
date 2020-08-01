package com.reinforcedmc.gameapi.events;

import com.reinforcedmc.gameapi.Game;
import com.reinforcedmc.gameapi.GameAPI;
import com.reinforcedmc.gameapi.GameStatus;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

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