package com.reinforcedmc.gameapi.minigames;

import com.reinforcedmc.gameapi.game.Game;
import com.reinforcedmc.gameapi.game.GameFlags;
import com.reinforcedmc.gameapi.game.GameType;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.Arrays;

public class BlockShuffle extends Game {

    public BlockShuffle() {
        super("BlockShuffle", ChatColor.GREEN + "" + ChatColor.BOLD + "Block" + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "Shuffle", Arrays.asList(
                "Find and craft specific blocks",
                "before everyone else to become",
                "the ultimate block master!"
        ), Arrays.asList(GameType.CLASSIC), new GameFlags().setAllowNether(true), 2, 16,  null);
    }

}
