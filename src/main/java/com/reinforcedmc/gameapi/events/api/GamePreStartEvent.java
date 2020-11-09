package com.reinforcedmc.gameapi.events.api;

import com.reinforcedmc.gameapi.game.Game;
import com.reinforcedmc.gameapi.GameAPI;
import com.reinforcedmc.gameapi.game.GameStatus;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public class GamePreStartEvent extends Event {

    private Game game;

    private static final HandlerList HANDLERS = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public GamePreStartEvent(Game game) {
        this.game = game;

        GameAPI.getInstance().status = GameStatus.POSTCOUNTDOWN;
        Bukkit.getOnlinePlayers().forEach((p) -> GameAPI.getInstance().getGameUtils().resetPlayer(p));
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "difficulty " + game.getGameFlags().getDifficulty().name().toLowerCase());
        Bukkit.getOnlinePlayers().forEach((p) -> p.playSound(p.getLocation(), Sound.UI_CARTOGRAPHY_TABLE_TAKE_RESULT, 1F, 0.8F));
        GameAPI.getInstance().getJedisManager().updateGameServer();

    }


}
