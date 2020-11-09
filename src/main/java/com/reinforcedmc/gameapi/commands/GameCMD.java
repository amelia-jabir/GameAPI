package com.reinforcedmc.gameapi.commands;

import com.reinforcedmc.gameapi.*;
import com.reinforcedmc.gameapi.game.Game;
import com.reinforcedmc.gameapi.game.GamePreCountDown;
import com.reinforcedmc.gameapi.game.GameStatus;
import com.reinforcedmc.gameapi.game.GameType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GameCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        if(!(sender instanceof Player)) {
            sender.sendMessage("Only for players!");
            return true;
        }

        Player p = (Player) sender;

        if(!p.hasPermission("reinforced.game.admin")) {
            p.sendMessage(ChatColor.RED + "No permission!");
            return true;
        }

        if(args[0].equalsIgnoreCase("forcestart") || args[0].equals("fs")) {
            GameAPI gameAPI = GameAPI.getInstance();
            if(gameAPI.preCountDown != null && gameAPI.status == GameStatus.PRECOUNTDOWN) {
                gameAPI.preCountDown.currentCD -= 10;
                if(gameAPI.preCountDown.currentCD > 0) {
                    Bukkit.getOnlinePlayers().forEach((pp) -> pp.setLevel(gameAPI.preCountDown.currentCD));
                } else {
                    Bukkit.getOnlinePlayers().forEach((pp) -> pp.setLevel(0));
                }
                p.playSound(p.getLocation(), Sound.ENTITY_PUFFER_FISH_BLOW_UP, 1F, 0.8F);
            }
        }

        if(args[0].equalsIgnoreCase("start")) {
            GameAPI.getInstance().getGameUtils().tryStarting();
        }

        if(args[0].equalsIgnoreCase("stop")) {
            GamePreCountDown gamePreCountDown = GameAPI.getInstance().preCountDown;
            if(gamePreCountDown != null) {
                gamePreCountDown.cancel();
                gamePreCountDown.currentCD = gamePreCountDown.cooldown;
                GameAPI.getInstance().status = GameStatus.LOBBY;
                GameAPI.getInstance().getJedisManager().updateGameServer();
                p.sendMessage(ChatColor.AQUA + "You have stopped the game from starting.");
            }
        }

        if(args[0].equalsIgnoreCase("end")) {
            if(GameAPI.getInstance().status != GameStatus.INGAME) {
                p.sendMessage(ChatColor.AQUA + "There is currently no game running!");
                return true;
            }

            GameAPI.getInstance().getAPI().endGame(null);
        }

        if(args[0].equalsIgnoreCase("setlobby")) {
            GameAPI.getInstance().getLocationsConfig().setLocation("lobby", p.getLocation());
            p.sendMessage(ChatColor.AQUA + "You have set the lobby!");
        }

        if(args[0].equalsIgnoreCase("settype")) {
            GameType gameType = null;

            try {
                gameType = GameType.valueOf(args[1].toUpperCase());
            } catch(IllegalArgumentException e) {
                p.sendMessage(ChatColor.RED + "ERROR: That game-type does not exist!");
                return true;
            }

            if(gameType == null) {
                p.sendMessage(ChatColor.RED + "ERROR: That game-type does not exist!");
                return true;
            }

            if(gameType != GameType.DEFAULT) {
                if (!GameAPI.getInstance().currentGame.getTypes().contains(gameType)) {
                    p.sendMessage(ChatColor.RED + "ERROR: The current game does not support this game type!");
                    return true;
                }
            }

            GameAPI.getInstance().gameType = gameType;
            GameAPI.getInstance().getGameConfig().set("type", args[1]);
            p.sendMessage(ChatColor.AQUA + "You have set the game-type to " + ChatColor.DARK_AQUA + ChatColor.BOLD + gameType.toString() + "!");
        }

        if(args[0].equalsIgnoreCase("setgame") || args[0].equalsIgnoreCase("set")) {
            if(GameAPI.getInstance().getGameManager().getGameByName(args[1]) == null) {
                p.sendMessage(ChatColor.RED + "ERROR: That game does not exist!");
                return true;
            }

            if(GameAPI.getInstance().status != GameStatus.LOBBY && GameAPI.getInstance().status != GameStatus.PRECOUNTDOWN) {
                p.sendMessage(ChatColor.RED + "ERROR: You need to wait for the game to finish!");
                return true;
            }

            if(GameAPI.getInstance().status == GameStatus.PRECOUNTDOWN) {
                GameAPI.getInstance().status = GameStatus.LOBBY;
                GameAPI.getInstance().getJedisManager().updateGameServer();
                GameAPI.getInstance().preCountDown.cancel();
            }

            Game g = GameAPI.getInstance().getGameManager().getGameByName(args[1]);
            GameAPI.getInstance().currentGame = g;
            GameAPI.getInstance().getGameConfig().set("game", args[1]);
            GameAPI.prefix = g.getPrefix() + ChatColor.DARK_GRAY + ChatColor.BOLD + "> " + ChatColor.RESET + ChatColor.GRAY;
            p.sendMessage(ChatColor.AQUA + "The game has been set to " + g.getPrefix() + "!");

            GameAPI.getInstance().getGameUtils().tryStarting();

            GameAPI.getInstance().getJedisManager().updateGameServer();

        }

        return true;
    }
}
