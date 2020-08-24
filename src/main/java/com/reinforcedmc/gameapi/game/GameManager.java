package com.reinforcedmc.gameapi.game;

import com.reinforcedmc.gameapi.minigames.BlockShuffle;
import com.reinforcedmc.gameapi.minigames.DeathSwap;

import java.util.ArrayList;

public class GameManager {

    public ArrayList<Game> games = new ArrayList<>();

    public GameManager() {
        games.add(new DeathSwap());
        games.add(new BlockShuffle());
    }

    public Game getGameByName(String gameName) {
        for(Game g : games) {
            if(g.getName().equalsIgnoreCase(gameName)) {
                return g;
            }
        }
        return null;
    }

}
