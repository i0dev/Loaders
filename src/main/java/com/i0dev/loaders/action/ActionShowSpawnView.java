package com.i0dev.loaders.action;

import com.i0dev.loaders.task.TaskSpawnParticle;
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
            e.getWhoClicked().sendMessage("§cYou already have a spawn view opened!");
            return true;
        }

        e.getWhoClicked().sendMessage("§aSpawn view opened for 30s!");

        TaskSpawnParticle.get().addView((Player) e.getWhoClicked(), npc, System.currentTimeMillis() + 30000L);
        return true;
    }

}
