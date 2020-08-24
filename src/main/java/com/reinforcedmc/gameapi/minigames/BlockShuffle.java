package com.reinforcedmc.gameapi.minigames;

import com.reinforcedmc.gameapi.game.Game;
import com.reinforcedmc.gameapi.game.GameFlags;
import org.bukkit.ChatColor;

import java.util.Arrays;

public class BlockShuffle extends Game {

    public BlockShuffle() {
        super("BlockShuffle", ChatColor.GREEN + "" + ChatColor.BOLD + "Block" + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "Shuffle", Arrays.asList(
                "Compete against other players by",
                "setting up big brain traps before it",
                "too late!"
        ), new GameFlags().setAllowNether(true), 2, 8,  null);
    }

}
