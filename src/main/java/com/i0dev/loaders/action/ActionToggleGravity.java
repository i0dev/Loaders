package com.i0dev.loaders.action;

import com.massivecraft.massivecore.chestgui.ChestAction;
import lombok.AllArgsConstructor;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.Gravity;
import org.bukkit.event.inventory.InventoryClickEvent;

@AllArgsConstructor
public class ActionToggleGravity implements ChestAction {

    NPC npc;

    @Override
    public boolean onClick(InventoryClickEvent inventoryClickEvent) {
        if (npc.getTraitNullable(Gravity.class).hasGravity()) {
            npc.removeTrait(Gravity.class);
            npc.getOrAddTrait(Gravity.class).gravitate(true);
        } else {
            npc.removeTrait(Gravity.class);
            npc.getOrAddTrait(Gravity.class).gravitate(false);
        }
        inventoryClickEvent.getWhoClicked().closeInventory();
        inventoryClickEvent.getWhoClicked().sendMessage("§aGravity toggled to " + npc.getTraitNullable(Gravity.class).hasGravity() + "!");
        return true;
    }
}
