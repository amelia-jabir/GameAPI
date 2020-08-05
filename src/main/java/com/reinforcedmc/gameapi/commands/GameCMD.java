package com.reinforcedmc.gameapi.commands;

import com.reinforcedmc.gameapi.Game;
import com.reinforcedmc.gameapi.GameAPI;
import com.reinforcedmc.gameapi.GameManager;
import com.reinforcedmc.gameapi.GameStatus;
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

        if(args[0].equalsIgnoreCase("setlobby")) {
            GameAPI.getInstance().getLocationsConfig().setLocation("lobby", p.getLocation());
            p.sendMessage(GameAPI.getInstance().prefix + ChatColor.GRAY + "Lobby has been set!");
        }

        if(args[0].equalsIgnoreCase("setgame")) {
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
                GameAPI.getInstance().preCountDown.cancel();
            }

            Game g = GameAPI.getInstance().getGameManager().getGameByName(args[1]);
            GameAPI.getInstance().currentGame = g;
            GameAPI.getInstance().getGameConfig().set("game", args[1]);
            p.sendMessage(ChatColor.GRAY + "The game has been set to " + g.getPrefix() + "!");

            GameAPI.getInstance().tryStarting();

        }

        return true;
    }
}
