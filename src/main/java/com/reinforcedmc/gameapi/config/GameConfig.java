package com.reinforcedmc.gameapi.config;

import com.reinforcedmc.gameapi.GameAPI;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

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

            config.set("game", "DeathSwap");
            config.set("isGameServer", false);

        } else {
            config = YamlConfiguration.loadConfiguration(file);
        }

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        GameAPI gameAPI = GameAPI.getInstance();
        gameAPI.currentGame = gameAPI.getGameManager().getGameByName(config.getString("game"));
        GameAPI.gameServer = config.getBoolean("isGameServer");

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
