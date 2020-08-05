package com.reinforcedmc.gameapi.config;

import com.reinforcedmc.gameapi.GameAPI;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class GameConfig {

    private File file;
    private FileConfiguration config;

    public GameConfig() {

        if (!GameAPI.getInstance().getDataFolder().exists()) {
            GameAPI.getInstance().getDataFolder().mkdirs();
        }

        file = new File(GameAPI.getInstance().getDataFolder(), "config.yml");

        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            config = YamlConfiguration.loadConfiguration(file);

            config.set("isGameServer", false);
            config.set("serverName", "CHANGE NAME");
            config.set("game", "DeathSwap");
            config.set("bungeecord", false);
            config.set("hubs", Arrays.asList("lobby"));

        } else {
            config = YamlConfiguration.loadConfiguration(file);
        }

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        GameAPI gameAPI = GameAPI.getInstance();
        gameAPI.gameServer = config.getBoolean("isGameServer");
        gameAPI.serverName = config.getString("serverName");
        gameAPI.currentGame = gameAPI.getGameManager().getGameByName(config.getString("game"));
        gameAPI.getBungeeUtils().enabled = config.getBoolean("bungeecord");

        for(String hub : config.getStringList("hubs")) {
            gameAPI.getBungeeUtils().hubs.put(hub, 0);
        }

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void set(String path, String value) {
        config.set(path, value);

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
