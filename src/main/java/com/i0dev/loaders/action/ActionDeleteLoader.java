package com.i0dev.loaders.action;

import com.i0dev.loaders.LoadersPlugin;
import com.i0dev.loaders.entity.Loader;
import com.i0dev.loaders.integration.LoaderTrait;
import com.massivecraft.massivecore.chestgui.ChestAction;
import lombok.AllArgsConstructor;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.Gravity;
import org.bukkit.event.inventory.InventoryClickEvent;

@AllArgsConstructor
public class ActionDeleteLoader implements ChestAction {

    NPC npc;

    @Override
    public boolean onClick(InventoryClickEvent inventoryClickEvent) {
        Loader loader = Loader.get(npc.getTraitNullable(LoaderTrait.class).getLoaderID());

        inventoryClickEvent.getWhoClicked().closeInventory();
        inventoryClickEvent.getWhoClicked().getInventory().addItem(loader.getItemStack(1));

        npc.destroy();
        LoadersPlugin.get().getNpcRegistry().saveToStore();

        return true;
    }
}
