package com.reinforcedmc.gameapi.api;

import com.reinforcedmc.gameapi.game.Game;
import com.reinforcedmc.gameapi.GameAPI;
import com.reinforcedmc.gameapi.game.GameStatus;
import com.reinforcedmc.gameapi.events.api.GameSetupEvent;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.UUID;

public class API {

    public void putInSpectator(Player p) {
        p.setGameMode(GameMode.ADVENTURE);
        p.setAllowFlight(true);
        p.setFlying(true);
        for(UUID uuid : GameAPI.getInstance().ingame) {
            Player pp = Bukkit.getPlayer(uuid);
            pp.hidePlayer(p);
        }
    }

    public void putInNormal(Player p) {
        p.setGameMode(GameMode.SURVIVAL);
        p.setAllowFlight(false);
        p.setFlying(false);
        for(UUID uuid : GameAPI.getInstance().ingame) {
            Player pp = Bukkit.getPlayer(uuid);
            pp.showPlayer(p);
        }
    }

    public void endGame(Player winner) {

        if(GameAPI.getInstance().status != GameStatus.INGAME) {
            return;
        }

        GameAPI.getInstance().status = GameStatus.ENDING;

        for(UUID uuid : GameAPI.getInstance().ingame) {
            Player p = Bukkit.getPlayer(uuid);
            for(Player pp : Bukkit.getOnlinePlayers()) {
                pp.showPlayer(p);
            }
        }

        if(winner != null) {

            for(UUID uuid : GameAPI.getInstance().ingame) {
                Player p = Bukkit.getPlayer(uuid);
                if(uuid != winner.getUniqueId())
                    p.teleport(winner);
            }

            Bukkit.broadcastMessage(ChatColor.AQUA + "The game has ended. " + ChatColor.DARK_AQUA + ChatColor.BOLD + winner.getName() + " won!");

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
            Bukkit.broadcastMessage(ChatColor.AQUA + "The game has ended. Nobody wins!");
        }

        new BukkitRunnable() {
            int cd = 8;
            @Override
            public void run() {
                if(cd > 0) {
                    cd--;
                } else {
                    Bukkit.getOnlinePlayers().forEach((p) -> p.teleport(GameAPI.getInstance().getLocationsConfig().lobby));

                    for(UUID uuid : GameAPI.getInstance().ingame) {
                        Player p = Bukkit.getPlayer(uuid);
                        GameAPI.getInstance().getGameUtils().resetPlayer(p);
                    }

                    this.cancel();
                    if(!GameAPI.getInstance().getBungeeUtils().enabled) {
                        Bukkit.getOnlinePlayers().forEach((p) -> p.kickPlayer("Constructing New Game..."));
                    } else {
                        GameAPI.getInstance().getBungeeUtils().sendAllToHub();
                    }
                    GameAPI.getInstance().status = GameStatus.SETUP;
                    Bukkit.getServer().getPluginManager().callEvent(new GameSetupEvent(GameAPI.getInstance().currentGame));
                }
            }
        }.runTaskTimer(GameAPI.getInstance(), 0, 20L);

    }

    public ArrayList<Game> getGames() {
        return GameAPI.getInstance().getGameManager().games;
    }

}
