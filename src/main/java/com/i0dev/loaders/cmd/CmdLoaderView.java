package com.i0dev.loaders.cmd;

import com.i0dev.loaders.LoadersPlugin;
import com.i0dev.loaders.integration.LoaderTrait;
import com.i0dev.loaders.task.TaskSpawnParticle;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

import java.util.concurrent.atomic.AtomicInteger;

public class CmdLoaderView extends LoadersCommand {

    private static CmdLoaderView i = new CmdLoaderView();

    public static CmdLoaderView get() {
        return i;
    }

    @Override
    public void perform() {

        if (TaskSpawnParticle.get().contains(me)) {
            msg("§cYou already have a spawn view opened!");
            return;
        }

        msg("§aShowing all loaders spawner load range for your faction!");
        msg("§aThese particles will disappear in 30 seconds");

        String playerFactionID = MPlayer.get(me).getFaction().getId();
        LoadersPlugin.get().getNpcRegistry().iterator().forEachRemaining(npc -> {
            Faction faction = Faction.get(npc.getTraitNullable(LoaderTrait.class).getFactionID());
            if (faction == null) return;
            if (!faction.getId().equals(playerFactionID)) return;
            TaskSpawnParticle.get().addView(me, npc, System.currentTimeMillis() + 30000L);
        });

    }

}
