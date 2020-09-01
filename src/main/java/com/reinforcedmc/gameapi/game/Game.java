package com.reinforcedmc.gameapi.game;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private String name;
    private String prefix;
    private List<String> description;

    private ArrayList<GameType> types;

    private GameFlags gameFlags;

    private int minPlayers;
    private int maxPlayers;

    private String[] titles;

    public String worldname;

    public Game(String name, String prefix, List<String> description, ArrayList<GameType> types, GameFlags gameFlags, int minPlayers, int maxPlayers, String[] titles) {
        this.name = name;
        this.prefix = prefix;
        this.description = description;

        this.types = types;

        this.gameFlags = gameFlags;

        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;

        this.titles = titles;
    }

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }

    public List<String> getDescription() {
        return description;
    }

    public GameFlags getGameFlags() {
        return gameFlags;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public String[] getScoreboardTitles() {
        return titles;
    }

    public ArrayList<GameType> getTypes() {
        return types;
    }
}
