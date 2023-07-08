package org.jdgames.koth.utils;

import org.bukkit.Location;
import org.bukkit.World;

//Simple, bare-bones cuboid class. Original idea from bergerkiller on the bukkit forumns (http://forums.bukkit.org/threads/region-general-api-for-creating-cuboids.34644/).
public class Cuboid {

    public int xMin, xMax, yMin, yMax, zMin, zMax;
    public World world;

    public Cuboid(Location point1, Location point2) {
        xMin = Math.min(point1.getBlockX(), point2.getBlockX());
        xMax = Math.max(point1.getBlockX(), point2.getBlockX());
        yMin = Math.min(point1.getBlockY(), point2.getBlockY());
        yMax = Math.max(point1.getBlockY(), point2.getBlockY());
        zMin = Math.min(point1.getBlockZ(), point2.getBlockZ());
        zMax = Math.max(point1.getBlockZ(), point2.getBlockZ());
        world = point1.getWorld();
    }

    public Cuboid(int xMin, int xMax, int yMin, int yMax, int zMin, int zMax, World world) {
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
        this.zMin = zMin;
        this.zMax = zMax;
        this.world = world;
    }

    public boolean isInRegion(Location loc) {
        if (loc.getWorld() != this.world) return false;
        if (loc.getBlockX() < xMin) return false;
        if (loc.getBlockX() > xMax) return false;
        if (loc.getBlockY() < yMin) return false;
        if (loc.getBlockY() > yMax) return false;
        if (loc.getBlockZ() < zMin) return false;
        return loc.getBlockZ() <= zMax;
    }

    public String serialize() {
        return "" + xMin + "," + zMin + "," + yMin + ":" + xMax + "," + zMax + "," + yMax + "@" + world.getName();
    }

    @Override
    public String toString() {
        return serialize();
    }

    public int getXWidth() {
        return xMax - xMin;
    }

    public int getZWidth() {
        return zMax - zMin;
    }

    public int getHeight() {
        return yMax - yMin;
    }

    public int getArea() {
        return getHeight() * getXWidth() * getZWidth();
    }
}