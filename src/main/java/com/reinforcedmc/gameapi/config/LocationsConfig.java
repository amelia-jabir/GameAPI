package com.reinforcedmc.gameapi.config;

import com.reinforcedmc.gameapi.GameAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class LocationsConfig {

    private File file;
    private FileConfiguration config;

    public Location lobby;

    public LocationsConfig() {

        if (GameAPI.getInstance().getDataFolder() == null || !GameAPI.getInstance().getDataFolder().exists()) {
            GameAPI.getInstance().getDataFolder().mkdirs();
        }

        file = new File(GameAPI.getInstance().getDataFolder(), "locations.yml");

        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            config = YamlConfiguration.loadConfiguration(file);

            config.set("lobby", "null");
            save();

        } else {
            config = YamlConfiguration.loadConfiguration(file);
        }

        getLocation("lobby");
        save();

    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setLocation(String name, Location loc) {
        if(name.equalsIgnoreCase("lobby")) {
            config.set("lobby", "valid");

            config.set("lobby.world", loc.getWorld().getName());
            config.set("lobby.x", loc.getX());
            config.set("lobby.y", loc.getY());
            config.set("lobby.z", loc.getZ());
            config.set("lobby.yaw", loc.getYaw());
            config.set("lobby.pitch", loc.getPitch());

            save();

            getLocation("lobby");
        }
    }

    public void getLocation(String name) {

        if(name.equalsIgnoreCase("lobby")) {
            if(config.getString("lobby").equalsIgnoreCase("null") == false) {
                lobby = new Location(Bukkit.getWorld(
                        config.getString("lobby.world")),
                        config.getDouble("lobby.x"),
                        config.getDouble("lobby.y"),
                        config.getDouble("lobby.z"),
                        (float) config.getDouble("lobby.yaw"),
                        (float) config.getDouble("lobby.pitch")
                );
            }
        }

    }

}
