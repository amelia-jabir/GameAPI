package com.reinforcedmc.gameapi.api;

import com.reinforcedmc.gameapi.GameAPI;
import org.apache.commons.io.FileUtils;
import org.bukkit.*;
import org.bukkit.block.Block;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GameWorld {

    public World world;
    public Location spawn;
    public long maxRadius;

    public ArrayList<Location> tpLocations = new ArrayList<>();

    public GameWorld(String worldname, long maxRadius) {
        if(Bukkit.getWorld(worldname) != null) {
            Bukkit.unloadWorld(worldname, false);
        }
        File folder = new File(Bukkit.getWorldContainer() + "/" + worldname);
        try {
            FileUtils.deleteDirectory(folder);
        } catch (IOException e) {
            e.printStackTrace();
        }

        WorldCreator creator = new WorldCreator(worldname);
        creator.environment(World.Environment.NORMAL);
        creator.generateStructures(true);
        world = creator.createWorld();

        spawn = new Location(world, 0, 0, 0);

        for (int i = 0; i<GameAPI.getInstance().currentGame.getMaxPlayers(); i++) {

            boolean notocean = false;

            Location location = Bukkit.getWorld(worldname).getSpawnLocation();

            while(!notocean) {
                location = new Location(world, 0, 0, 0); // New Location in the right World you want
                location.setX(spawn.getX() + Math.random() * maxRadius * 2 - maxRadius); // This get a Random with a MaxRange
                location.setZ(spawn.getZ() + Math.random() * maxRadius * 2 - maxRadius);

                Block highest = world.getHighestBlockAt(location.getBlockX(), location.getBlockZ());

                if(highest.isLiquid()) {
                    maxRadius += 100;
                    continue;
                }

                notocean = true;
                location.setY(highest.getY() + 1); // Get the Highest Block of the Location for Save Spawn.
            }

            for(int cx=-8; cx<=8; cx++) {
                for(int cz=-8; cz<=8; cz++) {
                    Location loc = new Location(location.getWorld(), location.getX() + cx * 16, location.getY(), location.getZ() + cz * 16);
                    Chunk chunk = world.getChunkAt(loc);
                    chunk.load();
                }
            }

            tpLocations.add(location);
        }
    }

}
