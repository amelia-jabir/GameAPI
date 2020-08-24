package com.reinforcedmc.gameapi.events;

import com.reinforcedmc.gameapi.GameAPI;
import com.reinforcedmc.gameapi.game.Game;
import com.reinforcedmc.gameapi.game.GameStatus;
import com.reinforcedmc.gameapi.scoreboard.UpdateScoreboardEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ScoreboardUpdateEvent implements Listener {

    @EventHandler
    public void onSBUpdate(UpdateScoreboardEvent e) {

        if(GameAPI.getInstance().status == GameStatus.LOBBY || GameAPI.getInstance().status == GameStatus.PRECOUNTDOWN) {

            Game game = GameAPI.getInstance().currentGame;

            String[] lines = {
                    "&7" + GameAPI.getInstance().serverName,
                    "",
                    "Players: " + "&b" + GameAPI.getInstance().ingame.size() + "/" + game.getMaxPlayers(),
                    "",
                    "&bplay.reinforced.com"
            };

            if(GameAPI.getInstance().currentGame.getScoreboardTitles() != null) {
                e.getScoreboard().setTitles(GameAPI.getInstance().currentGame.getScoreboardTitles());
            } else {
                e.getScoreboard().setTitles(GameAPI.getInstance().currentGame.getPrefix());
            }
            e.getScoreboard().setLines(lines);
        }

    }

}
