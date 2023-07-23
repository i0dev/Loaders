package com.i0dev.loaders.cmd;

import com.i0dev.loaders.LoadersPlugin;
import com.i0dev.loaders.entity.MConf;
import com.i0dev.loaders.entity.MLang;
import com.i0dev.loaders.integration.LoaderTrait;
import com.i0dev.loaders.task.TaskSpawnParticle;
import com.i0dev.loaders.util.Utils;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;

import java.util.concurrent.atomic.AtomicInteger;

public class CmdLoaderView extends LoadersCommand {

    private static CmdLoaderView i = new CmdLoaderView();

    public static CmdLoaderView get() {
        return i;
    }

    @Override
    public void perform() {

        if (TaskSpawnParticle.get().contains(me)) {
            msg(Utils.prefixAndColor(MLang.get().alreadyViewOpen));
            return;
        }

        String playerFactionID = MPlayer.get(me).getFaction().getId();
        AtomicInteger count = new AtomicInteger(0);
        LoadersPlugin.get().getNpcRegistry().iterator().forEachRemaining(npc -> {
            Faction faction = Faction.get(npc.getTraitNullable(LoaderTrait.class).getFactionID());
            if (faction == null) return;
            if (count.get() > MConf.get().maxLoadersView) return;
            if (!faction.getId().equals(playerFactionID)) return;
            TaskSpawnParticle.get().addView(me, npc, System.currentTimeMillis() + (MConf.get().spawnViewTime * 1000L));
            count.getAndIncrement();
        });

        msg(Utils.prefixAndColor(MLang.get().spawnViewOpened));
    }

}
