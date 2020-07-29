package com.reinforcedmc.gameapi.api;

import com.reinforcedmc.gameapi.Game;
import com.reinforcedmc.gameapi.GameAPI;
import com.reinforcedmc.gameapi.GameStatus;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class API {

    public void revivePlayer(Player p, GameMode gameMode, Location reviveLocation) {
        if(!GameAPI.getInstance().ingame.contains(p.getUniqueId())) {
            GameAPI.getInstance().ingame.add(p.getUniqueId());
            p.setGameMode(gameMode);
            if(reviveLocation != null) p.teleport(reviveLocation);
        }
    }

    public void killPlayer(Player p, Location spectateLocation) {
        if(GameAPI.getInstance().ingame.contains(p.getUniqueId())) {
            GameAPI.getInstance().ingame.remove(p.getUniqueId());
            p.setGameMode(GameMode.CREATIVE);
            if(spectateLocation != null) p.teleport(spectateLocation);
        }
    }

    public void endGame(Player winner) {

        if(GameAPI.getInstance().status != GameStatus.INGAME) {
            return;
        }

        if(winner != null) {

            for(Player p : Bukkit.getOnlinePlayers()) {
                if(!p.getUniqueId().equals(winner.getUniqueId())) {
                    if(p.getLocation().distance(winner.getLocation()) >= 16) {
                        p.teleport(winner);
                    }
                }
                p.playSound(p.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1F, 1F);
            }

            Bukkit.broadcastMessage(GameAPI.prefix + "The game has ended. " + ChatColor.GOLD + winner.getName() + " won!");

            new BukkitRunnable() {
                int i = 8;

                @Override
                public void run() {
                    if (i > 0) {
                        Firework f = (Firework) winner.getWorld().spawnEntity(winner.getLocation(), EntityType.FIREWORK);
                        FireworkMeta fm = f.getFireworkMeta();
                        fm.setPower(1);
                        f.setFireworkMeta(fm);

                        i--;
                    } else {
                        this.cancel();
                    }
                }
            }.runTaskTimer(GameAPI.getInstance(), 0, 20);
        } else {
            Bukkit.broadcastMessage(GameAPI.prefix + "The game has ended. Nobody wins!");
        }

        GameAPI.getInstance().status = GameStatus.ENDING;

        new BukkitRunnable() {
            int cd = 8;
            @Override
            public void run() {
                if(cd > 0) {
                    cd--;
                } else {
                    Bukkit.getOnlinePlayers().forEach((p) -> p.teleport(GameAPI.getInstance().getLocationsConfig().lobby));
                    Bukkit.getOnlinePlayers().forEach((p) -> p.setGameMode(GameMode.SURVIVAL));
                    Bukkit.getOnlinePlayers().forEach((p) -> p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1F, 1.5F));
                    GameAPI.getInstance().status = GameStatus.LOBBY;
                    this.cancel();
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            GameAPI.getInstance().tryStarting();
                        }
                    }.runTaskLater(GameAPI.getInstance(), 100L);
                }
            }
        }.runTaskTimer(GameAPI.getInstance(), 0, 20L);

    }

    public ArrayList<Game> getGames() {
        return GameAPI.getInstance().getGameManager().games;
    }

}
