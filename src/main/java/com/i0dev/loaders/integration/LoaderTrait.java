package com.i0dev.loaders.integration;

import com.i0dev.loaders.LoadersPlugin;
import com.i0dev.loaders.action.ActionDeleteLoader;
import com.i0dev.loaders.action.ActionShowSpawnView;
import com.i0dev.loaders.action.ActionToggleGravity;
import com.i0dev.loaders.engine.EngineLoader;
import com.i0dev.loaders.entity.Loader;
import com.i0dev.loaders.entity.MConf;
import com.i0dev.loaders.entity.MLang;
import com.i0dev.loaders.util.Pair;
import com.i0dev.loaders.util.Utils;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.chestgui.ChestGui;
import com.massivecraft.massivecore.ps.PS;
import lombok.Getter;
import lombok.Setter;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.api.persistence.Persist;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.trait.TraitName;
import net.citizensnpcs.trait.Gravity;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

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

    @Getter
    @Setter
    @Persist
    UUID entangledLoaderID = null;

    @Getter
    @Setter
    @Persist
    String displayName = null;

    public NPC getEntangledLoader() {
        return LoadersPlugin.get().getNpcRegistry().getByUniqueId(entangledLoaderID);
    }

    public void setIDS(String factionID, String loaderID, String name) {
        this.factionID = factionID;
        this.loaderID = loaderID;
        this.displayName = name;
    }

    @Override
    public void onRemove() {
        EngineLoader.get().unForceLoadChunks(npc.getStoredLocation(), Loader.get(loaderID).getChunkLoadRadius());
        if (entangledLoaderID != null)
            LoadersPlugin.get().getNpcRegistry().getByUniqueId(entangledLoaderID).destroy();
    }

    @Override
    public void onSpawn() {
        npc.getEntity().setInvulnerable(true);
        ((LivingEntity) npc.getEntity()).setInvisible(true);

        if (entangledLoaderID == null) {
            NPCRegistry registry = LoadersPlugin.get().getNpcRegistry();
            NPC entangledAllay = registry.createNPC(EntityType.ALLAY, displayName);
            entangledAllay.setUseMinecraftAI(false);
            entangledAllay.spawn(npc.getStoredLocation());
            entangledAllay.getEntity().setInvulnerable(true);
            entangledAllay.getOrAddTrait(Gravity.class).gravitate(true);
            this.setEntangledLoaderID(entangledAllay.getUniqueId());
            registry.saveToStore();
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onNPCRightClick(NPCRightClickEvent e) {
        if (e.getNPC() != this.getNPC()) return;
        Faction factionAtLocation = BoardColl.get().getFactionAt(PS.valueOf(npc.getStoredLocation()));
        if (!factionAtLocation.isNone()) {
            if (!factionAtLocation.getId().equals(MPlayer.get(e.getClicker()).getFaction().getId())) {
                e.getClicker().sendMessage(Utils.prefixAndColor(MLang.get().cantManageLoadersInNotYourOwnLand));
                return;
            }
        }

        e.getClicker().openInventory(getLoaderInventory());
    }


    private Inventory getLoaderInventory() {
        Faction faction = Faction.get(factionID) == null ? Faction.get("none") : Faction.get(factionID);

        Inventory inventory = Bukkit.getServer().createInventory(null, MConf.get().manageLoaderGuiSize, Utils.color(MConf.get().manageLoaderTitle));
        ChestGui chestGui = ChestGui.getCreative(inventory);

        chestGui.setAutoclosing(false);
        chestGui.setAutoremoving(true);
        chestGui.setSoundOpen(null);
        chestGui.setSoundClose(null);

        for (int i = 0; i < MConf.get().manageLoaderGuiSize; i++) {
            chestGui.getInventory().setItem(i, MConf.get().borderItem.getItemStack());
        }

        chestGui.getInventory().setItem(MConf.get().infoSlot, MConf.get().infoItem.getItemStack(
                new Pair<>("%faction%", faction.getName()),
                new Pair<>("%radius%", String.valueOf(Loader.get(loaderID).getChunkLoadRadius()))
        ));

        chestGui.getInventory().setItem(MConf.get().toggleGravitySlot, MConf.get().toggleGravityItem.getItemStack(
                new Pair<>("%state%", npc.getTrait(Gravity.class).hasGravity() ? MConf.get().enabledState : MConf.get().disabledState)
        ));
        chestGui.setAction(MConf.get().toggleGravitySlot, new ActionToggleGravity(this.getNPC()));

        chestGui.getInventory().setItem(MConf.get().viewRangeSlot, MConf.get().viewSpawnerRangeItem.getItemStack(
                new Pair<>("%time%", String.valueOf(MConf.get().spawnViewTime))
        ));
        chestGui.setAction(MConf.get().viewRangeSlot, new ActionShowSpawnView(this.getNPC()));

        chestGui.getInventory().setItem(MConf.get().removedLoaderSlot, MConf.get().removeLoaderItem.getItemStack());
        chestGui.setAction(MConf.get().removedLoaderSlot, new ActionDeleteLoader(this.getNPC()));

        return inventory;
    }

}
