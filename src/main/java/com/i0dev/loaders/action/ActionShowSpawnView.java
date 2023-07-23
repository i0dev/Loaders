package com.i0dev.loaders.action;

import com.i0dev.loaders.entity.MConf;
import com.i0dev.loaders.entity.MLang;
import com.i0dev.loaders.task.TaskSpawnParticle;
import com.i0dev.loaders.util.Utils;
import com.massivecraft.massivecore.chestgui.ChestAction;
import lombok.AllArgsConstructor;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

@AllArgsConstructor
public class ActionShowSpawnView implements ChestAction {

    NPC npc;

    @Override
    public boolean onClick(InventoryClickEvent e) {
        e.getWhoClicked().closeInventory();

        if (TaskSpawnParticle.get().contains((Player) e.getWhoClicked())) {
            e.getWhoClicked().sendMessage(Utils.prefixAndColor(MLang.get().alreadyViewOpen));
            return true;
        }

        e.getWhoClicked().sendMessage(Utils.prefixAndColor(MLang.get().spawnViewOpened));

        TaskSpawnParticle.get().addView((Player) e.getWhoClicked(), npc, System.currentTimeMillis() + (MConf.get().spawnViewTime * 1000L));
        return true;
    }

}
