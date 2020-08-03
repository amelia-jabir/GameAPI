package com.reinforcedmc.gameapi.utils;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.reinforcedmc.gameapi.GameAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class BungeeUtils {

    public boolean enabled = false;
    public HashMap<String, Integer> hubs = new HashMap<>();

    public void getServerPlayerCounts() {

        ArrayList<Player> players = new ArrayList<>();
        for(Player p : Bukkit.getOnlinePlayers()) {
            players.add(p);
        }

        if(players.isEmpty()) return;

        Player randomplayer = players.get(new Random().nextInt(players.size()));

        for(String server : hubs.keySet())
            getPlayerCount(randomplayer, server);
    }

    private void getPlayerCount(Player p, String server) {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("PlayerCount");
        output.writeUTF(server);
        p.sendPluginMessage(GameAPI.getInstance(), "BungeeCord", output.toByteArray());
    }

    private String getServerWithLowestPlayerCount() {
        String lastserver = "";
        for(String s : hubs.keySet()) {
            if (lastserver == "") {
                lastserver = s;
                continue;
            }

            if(hubs.get(lastserver) > hubs.get(s)) {
                lastserver = s;
            }
        }
        return lastserver;
    }

    public void sendToHub(Player p) {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("Connect");
        output.writeUTF(getServerWithLowestPlayerCount());
        p.sendPluginMessage(GameAPI.getInstance(), "BungeeCord", output.toByteArray());
    }


    public void sendAllToHub() {
        for(Player p : Bukkit.getOnlinePlayers()) {
            ByteArrayDataOutput output = ByteStreams.newDataOutput();
            output.writeUTF("Connect");
            output.writeUTF(getServerWithLowestPlayerCount());
            p.sendPluginMessage(GameAPI.getInstance(), "BungeeCord", output.toByteArray());
        }
    }

}
