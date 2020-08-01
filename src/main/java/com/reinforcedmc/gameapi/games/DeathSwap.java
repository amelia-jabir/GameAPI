package com.reinforcedmc.gameapi.games;

import com.reinforcedmc.gameapi.Game;
import org.bukkit.ChatColor;

import java.util.Arrays;

public class DeathSwap extends Game {

    public DeathSwap() {
        super("DeathSwap", ChatColor.GOLD + "" + ChatColor.BOLD + "Death" + ChatColor.YELLOW + ChatColor.BOLD + "Swap", Arrays.asList(
                "Compete against other players by",
                "setting up big brain traps before it",
                "too late!"
        ), 2, 8, false, new String[]{
                "&6&lDeath&e&lSwap",
                "&6&lDeath&e&lSwap",
                "&6&lDeath&e&lSwap",
                "&6&lDeath&e&lSwap",
                "&6&lDeath&e&lSwap",
                "&6&lDeath&e&lSwap",
                "&6&lDeath&e&lSwap",
                "&6&lDeath&e&lSwap",
                "&6&lDeath&e&lSwap",
                "&e&lSwap&6&lDeath",
                "&e&lSwap&6&lDeath",
                "&e&lSwap&6&lDeath",
                "&e&lSwap&6&lDeath",
                "&e&lSwap&6&lDeath",
                "&e&lSwap&6&lDeath",
                "&e&lSwap&6&lDeath",
                "&e&lSwap&6&lDeath",
                "&e&lSwap&6&lDeath"
                });
    }

}
