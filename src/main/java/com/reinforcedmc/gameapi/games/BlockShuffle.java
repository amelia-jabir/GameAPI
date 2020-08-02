package com.reinforcedmc.gameapi.games;

import com.reinforcedmc.gameapi.Game;
import org.bukkit.ChatColor;

import java.util.Arrays;

public class BlockShuffle extends Game {

    public BlockShuffle() {
        super("BlockShuffle", ChatColor.GREEN + "" + ChatColor.BOLD + "Block" + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "Shuffle", Arrays.asList(
                "Compete against other players by",
                "setting up big brain traps before it",
                "too late!"
        ), 2, 8, false, null);
    }

}
