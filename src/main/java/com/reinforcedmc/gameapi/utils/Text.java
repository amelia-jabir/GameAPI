package com.reinforcedmc.gameapi.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import net.md_5.bungee.api.ChatColor;

public class Text {
	
	public static String format(String prefix, String message) {
		message = Text.color(message);
		prefix = Text.color(String.format("&9%s »&r", prefix));
		return (prefix + " " + message);
	}
	
	public static String color(String text) {
		return ChatColor.translateAlternateColorCodes('&', text.replaceAll("&s", "&e").replaceAll("&r", "&7").replaceAll("&q", "&c&l").replaceAll("&w", "&a&l"));
	}
	
    public static void log(Plugin plugin, String msg) {
        msg = ChatColor.translateAlternateColorCodes('&', "&d[" + plugin.getName() + "]&r " + msg);
        Bukkit.getConsoleSender().sendMessage(msg);
    }
    
    public static void echo(String prefix, String msg){
    	Bukkit.getConsoleSender().sendMessage(String.format("[%s] %s", prefix.toUpperCase(), msg));
    }

    public static void debug(Plugin plugin, String msg) {
        log(plugin, "&7[&eDEBUG&7]&r" + msg);
    }
    
    public static String formatName(String text) {
		String[] name = text.replaceAll("_", " ").toLowerCase().split(" ");
		StringBuilder message = new StringBuilder();
		for(String s : name) {
			s = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
			message.append(s).append(" ");
		}
		return message.toString().trim();
    }
    
    public static String capitalize(String text) {
    	if(text == null || text.isEmpty())
    		return text;
    	
    	return text.substring(0, 1).toUpperCase() + text.substring(1);
    }
    
    public static boolean isAlphanumeric(String str)
    {
        char[] charArray = str.toCharArray();
        for(char c:charArray)
        {
            if (!Character.isLetterOrDigit(c))
                return false;
        }
        
        String[] charrArray2 = {"»", "»", "»", "»", "»"};
        for(String s : charrArray2) {
        	if(str.contains(s))
        		return false;
        }
        return true;
    }

}
