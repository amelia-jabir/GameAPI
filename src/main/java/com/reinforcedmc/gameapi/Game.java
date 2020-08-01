package com.reinforcedmc.gameapi;

import org.bukkit.ChatColor;

import java.util.List;

public class Game {

    private String name;
    private String prefix;
    private List<String> description;

    private int minPlayers;
    private int maxPlayers;

    private boolean allowMixed;

    private String[] titles;

    public Game(String name, String prefix, List<String> description, int minPlayers, int maxPlayers, boolean allowMixed, String[] titles) {
        this.name = name;
        this.prefix = prefix;
        this.description = description;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.allowMixed = allowMixed;
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

    public int getMinPlayers() {
        return minPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public boolean mixAllowed() {
        return allowMixed;
    }

    public String[] getScoreboardTitles() {
        return titles;
    }

}
