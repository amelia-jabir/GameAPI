package com.reinforcedmc.gameapi.gameserver;

import com.reinforcedmc.gameapi.game.Game;
import com.reinforcedmc.gameapi.game.GameStatus;

public class GameServer {

    private String servername;
    private Game game;
    private GameStatus status;
    private int playercount = 0;

    public GameServer(String servername, Game game, GameStatus gameStatus, int playercount) {
        this.servername = servername;
        this.game = game;
        this.status = gameStatus;
        this.playercount = playercount;
    }

    public void setServername(String servername) {
        this.servername = servername;
    }

    public void setPlayercount(int playercount) {
        this.playercount = playercount;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public String getServername() {
        return servername;
    }

    public int getPlayercount() {
        return playercount;
    }

    public Game getGame() {
        return game;
    }

    public GameStatus getStatus() {
        return status;
    }
}
