package com.i0dev.loaders.task;

import com.massivecraft.massivecore.ModuloRepeatTask;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TaskSpawnParticle extends ModuloRepeatTask {

    private static final TaskSpawnParticle i = new TaskSpawnParticle();

    public static TaskSpawnParticle get() {
        return i;
    }

    public void addView(Player observer, NPC npc, long time) {
        views.add(new View(observer, npc, time));
    }

    public boolean contains(Player observer) {
        for (View view : views) {
            if (view.observer().equals(observer)) return true;
        }
        return false;
    }

    List<View> views = new ArrayList<>();

    @Override
    public long getDelayMillis() {
        return 1000L;
    }

    @Override
    public void invoke(long l) {
        List<View> toRemove = new ArrayList<>();
        for (View view : views) {
            if (view.npc() == null || !view.npc.isSpawned() || view.stopTime() < System.currentTimeMillis()) {
                toRemove.add(view);
                continue;
            }
            showOutline(view.observer(), view.npc().getStoredLocation());
        }
        views.removeAll(toRemove);
    }


    public void showOutline(Player observer, Location center) {
        Location min = new Location(center.getWorld(), center.getBlockX() - 16, center.getBlockY() - 16, center.getBlockZ() - 16);
        Location max = new Location(center.getWorld(), center.getBlockX() + 16, center.getBlockY() + 16, center.getBlockZ() + 16);

        for (int x = min.getBlockX(); x <= max.getBlockX(); x += 2) {
            for (int y = min.getBlockY(); y <= max.getBlockY(); y += 2) {
                for (int z = min.getBlockZ(); z <= max.getBlockZ(); z += 2) {
                    // if block is on the outside edge of the cuboid
                    if (x == min.getBlockX() || x == max.getBlockX() || y == min.getBlockY() || y == max.getBlockY() || z == min.getBlockZ() || z == max.getBlockZ()) {
                        // show the block
                        observer.spawnParticle(Particle.REDSTONE, x, y, z, 1, new Particle.DustOptions(org.bukkit.Color.RED, 1));
                    }
                }
            }
        }
    }

    public record View(Player observer, NPC npc, long stopTime) {
    }

}
