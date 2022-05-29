package net.momirealms.customtotem.utils;

public class CorePos {

    private final int x;
    private final int y;
    private final int z;

    public CorePos(int x, int z, int y) {
        this.x = x;
        this.z = z;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getZ() {
        return this.z;
    }

}
