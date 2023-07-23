package com.i0dev.loaders.action;

import com.i0dev.loaders.entity.MLang;
import com.i0dev.loaders.util.Utils;
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
            inventoryClickEvent.getWhoClicked().sendMessage(Utils.prefixAndColor(MLang.get().gravityToggledOff));
        } else {
            npc.removeTrait(Gravity.class);
            npc.getOrAddTrait(Gravity.class).gravitate(false);
            inventoryClickEvent.getWhoClicked().sendMessage(Utils.prefixAndColor(MLang.get().gravityToggledOn));
        }
        inventoryClickEvent.getWhoClicked().closeInventory();
        return true;
    }
}
