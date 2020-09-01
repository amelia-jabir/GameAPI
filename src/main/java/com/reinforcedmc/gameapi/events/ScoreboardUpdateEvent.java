package com.reinforcedmc.gameapi.events;

import com.reinforcedmc.gameapi.GameAPI;
import com.reinforcedmc.gameapi.game.Game;
import com.reinforcedmc.gameapi.game.GameStatus;
import com.reinforcedmc.gameapi.game.GameType;
import com.reinforcedmc.gameapi.scoreboard.UpdateScoreboardEvent;
import org.bukkit.ChatColor;
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

            String gametype = "";

            if(GameAPI.getInstance().gameType == GameType.CLASSIC) gametype = ChatColor.RESET + " " + ChatColor.GRAY + "Classic";

            if(GameAPI.getInstance().currentGame.getScoreboardTitles() != null) {
                String[] titles = GameAPI.getInstance().currentGame.getScoreboardTitles().clone();

                if(gametype != "") {
                    for (int i = 0; i < GameAPI.getInstance().currentGame.getScoreboardTitles().length; i++) {
                        String s = titles[i];
                        titles[i] = s + gametype;
                    }
                }

                e.getScoreboard().setTitles(titles);
            } else {
                e.getScoreboard().setTitles(GameAPI.getInstance().currentGame.getPrefix() + gametype);
            }
            e.getScoreboard().setLines(lines);
        }

    }

}
