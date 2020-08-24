package com.reinforcedmc.gameapi.game;

public class GameFlags {

    boolean allowNether = false;
    boolean allowEnd = false;
    boolean pvp = false;
    boolean blockbreak = true;
    boolean blockplace = true;

    public GameFlags() {

    }

    public boolean isAllowNether() {
        return allowNether;
    }

    public GameFlags setAllowNether(boolean allowNether) {
        this.allowNether = allowNether;
        return this;
    }

    public boolean isAllowEnd() {
        return allowEnd;
    }

    public GameFlags setAllowEnd(boolean allowEnd) {
        this.allowEnd = allowEnd;
        return this;
    }

    public boolean isPvp() {
        return pvp;
    }

    public GameFlags setPvp(boolean pvp) {
        this.pvp = pvp;
        return this;
    }

    public boolean isBlockbreak() {
        return blockbreak;
    }

    public GameFlags setBlockbreak(boolean blockbreak) {
        this.blockbreak = blockbreak;
        return this;
    }

    public boolean isBlockplace() {
        return blockplace;
    }

    public GameFlags setBlockplace(boolean blockplace) {
        this.blockplace = blockplace;
        return this;
    }
}
