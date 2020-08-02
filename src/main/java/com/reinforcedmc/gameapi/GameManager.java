package com.reinforcedmc.gameapi;

import com.reinforcedmc.gameapi.games.BlockShuffle;
import com.reinforcedmc.gameapi.games.DeathSwap;

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
