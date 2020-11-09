package com.reinforcedmc.gameapi.database;

import com.reinforcedmc.gameapi.GameAPI;
import com.reinforcedmc.gameapi.game.Game;
import com.reinforcedmc.gameapi.game.GameStatus;
import com.reinforcedmc.gameapi.gameserver.GameServer;
import org.bukkit.scheduler.BukkitRunnable;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JedisManager {

    JedisPool jedispool;

    public JedisManager() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMinIdle(8);
        jedisPoolConfig.setMaxIdle(32);

        jedispool = new JedisPool();

        Jedis jedis = jedispool.getResource();

        System.out.println("Pinging jedis..");
        System.out.println(jedis.ping("Connection successful!"));

    }

    public void updateGameServer() {

        if(!GameAPI.getInstance().gameServer) return;

        new BukkitRunnable() {
            @Override
            public void run() {
                Jedis jedis = jedispool.getResource();
                Pipeline pipeline = jedis.pipelined();

                String servername = GameAPI.getInstance().serverName;
                Game game = GameAPI.getInstance().currentGame;
                GameStatus status = GameAPI.getInstance().status;
                int playercount = GameAPI.getInstance().ingame.size();

                Map<String, String> data = new HashMap<>();
                data.put("game", game.getName());
                data.put("status", status.name());
                data.put("playercount", playercount + "");

                pipeline.hmset("gameserver." + servername, data);
                pipeline.sync();
            }
        }.runTaskAsynchronously(GameAPI.getInstance());

    }

    public ArrayList<GameServer> getGameServers() {

        ArrayList<GameServer> gameServers = new ArrayList<>();

        Jedis jedis = jedispool.getResource();
        Pipeline pipeline = jedis.pipelined();
        for(String servername : pipeline.keys("").get()) {

            if(!servername.contains("gameserver.")) continue;

            Map<String, String> data = pipeline.hgetAll(servername).get();

            Game game = GameAPI.getInstance().getGameManager().getGameByName(data.get("game"));
            GameStatus status = GameStatus.valueOf(data.get("status"));
            int playercount = Integer.parseInt(data.get("playercount"));

            gameServers.add(new GameServer(servername, game, status, playercount));
        }
        pipeline.sync();

        return gameServers;
    }

}
