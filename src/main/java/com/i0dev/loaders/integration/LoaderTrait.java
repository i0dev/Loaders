package com.i0dev.loaders.integration;

import com.i0dev.loaders.action.ActionDeleteLoader;
import com.i0dev.loaders.action.ActionShowSpawnView;
import com.i0dev.loaders.action.ActionToggleGravity;
import com.i0dev.loaders.engine.EngineLoader;
import com.i0dev.loaders.entity.Loader;
import com.i0dev.loaders.util.ItemBuilder;
import com.i0dev.loaders.util.Utils;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.chestgui.ChestGui;
import com.massivecraft.massivecore.ps.PS;
import com.massivecraft.massivecore.util.MUtil;
import lombok.Getter;
import lombok.Setter;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.persistence.Persist;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.trait.TraitName;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.inventory.Inventory;

@TraitName("loader")
public class LoaderTrait extends Trait {

    public LoaderTrait() {
        super("loader");
    }

    @Getter
    @Persist
    String factionID = null;

    @Setter
    @Getter
    @Persist
    String loaderID = null;

    public void setIDS(String factionID, String loaderID) {
        this.factionID = factionID;
        this.loaderID = loaderID;
    }

    @Override
    public void onRemove() {
        EngineLoader.get().unForceLoadChunks(npc.getStoredLocation(), Loader.get(loaderID).getChunkLoadRadius());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onNPCRightClick(NPCRightClickEvent e) {
        if (e.getNPC() != this.getNPC()) return;
        Faction factionAtLocation = BoardColl.get().getFactionAt(PS.valueOf(npc.getStoredLocation()));
        if (!factionAtLocation.isNone()) {
            if (!factionAtLocation.getId().equals(MPlayer.get(e.getClicker()).getFaction().getId())) {
                e.getClicker().sendMessage(Utils.color("&cYou can't manage this loader because it's not in your factions land!"));
                return;
            }
        }

        e.getClicker().openInventory(getLoaderInventory());
    }


    private Inventory getLoaderInventory() {
        Inventory inventory = Bukkit.getServer().createInventory(null, 27, Utils.color("&cManage " + Faction.get(factionID).getName() + "'s Loader"));
        ChestGui chestGui = ChestGui.getCreative(inventory);

        chestGui.setAutoclosing(false);
        chestGui.setAutoremoving(true);
        chestGui.setSoundOpen(null);
        chestGui.setSoundClose(null);

        for (int i = 0; i < 27; i++) {
            chestGui.getInventory().setItem(i, new com.massivecraft.factions.util.ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).addGlow(true).name("&7"));
        }

        chestGui.getInventory().setItem(4, new ItemBuilder(Material.PAPER)
                .name("&aLoader Information")
                .lore(MUtil.list(
                        "",
                        "&cThis loader is owned by " + Faction.get(factionID).getName(),
                        "&cIt will load all spawners within 16 blocks",
                        "&cIt will load all chunks within " + Loader.get(loaderID).getChunkLoadRadius() + " blocks",
                        "",
                        "&cClick the barrier to remove this loader, and get the spawn egg back",
                        "&cClick the eye to view the spawner range",
                        "&cClick the elytra to toggle gravity"
                ))
        );

        chestGui.getInventory().setItem(11, new ItemBuilder(Material.ELYTRA).name("&cToggle Gravity"));
        chestGui.setAction(11, new ActionToggleGravity(this.getNPC()));

        chestGui.getInventory().setItem(13, new ItemBuilder(Material.ENDER_EYE).name("&aView spawner range"));
        chestGui.setAction(13, new ActionShowSpawnView(this.getNPC()));

        chestGui.getInventory().setItem(15, new ItemBuilder(Material.BARRIER).name("&cRemove Loader"));
        chestGui.setAction(15, new ActionDeleteLoader(this.getNPC()));

        return inventory;
    }

}
