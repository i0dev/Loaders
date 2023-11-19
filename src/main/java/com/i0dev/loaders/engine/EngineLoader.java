package com.i0dev.loaders.engine;

import com.i0dev.loaders.LoadersPlugin;
import com.i0dev.loaders.entity.Loader;
import com.i0dev.loaders.entity.MConf;
import com.i0dev.loaders.entity.MLang;
import com.i0dev.loaders.integration.LoaderTrait;
import com.i0dev.loaders.util.ItemBuilder;
import com.i0dev.loaders.util.Utils;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.ps.PS;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.trait.Gravity;
import net.citizensnpcs.trait.SkinTrait;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class EngineLoader extends Engine {

    private static EngineLoader i = new EngineLoader();

    public static EngineLoader get() {
        return i;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        ItemStack itemInHand = e.getItem();
        Action action = e.getAction();
        Player player = e.getPlayer();
        if (action.equals(Action.LEFT_CLICK_BLOCK)) return;
        if (action.equals(Action.LEFT_CLICK_AIR)) return;
        if (action.equals(Action.RIGHT_CLICK_AIR)) return;
        Block clickedBlock = e.getClickedBlock();
        if (clickedBlock == null) return;
        if (itemInHand == null) return;

        Loader loader = Loader.get(ItemBuilder.getPDCValue(itemInHand, "loader-id"));
        if (loader == null) return;
        e.setCancelled(true);
        if (!e.getBlockFace().equals(BlockFace.UP)) {
            player.sendMessage(Utils.prefixAndColor(MLang.get().onlyPlaceLoadersOnTopOfBlocks));
            return;
        }
        Location location = clickedBlock.getLocation().clone().add(.5, 1, .5);

        Faction faction = MPlayer.get(player).getFaction();
        if (faction.isSystemFaction()) {
            player.sendMessage(Utils.prefixAndColor(MLang.get().cantPlaceLoadersInSystemFactions));
            return;
        }

        Faction factionAtLocation = BoardColl.get().getFactionAt(PS.valueOf(location));
        if (!factionAtLocation.getId().equals(faction.getId())) {
            player.sendMessage(Utils.prefixAndColor(MLang.get().canOnlyPlaceLoadersInYourOwnLand));

            return;
        }

        // TODO add mperm check ? ? ?

        NPCRegistry registry = LoadersPlugin.get().getNpcRegistry();
        NPC npc = registry.createNPC(EntityType.PLAYER, "&2" + faction.getName() + "'s Loader");
        npc.data().setPersistent(NPC.Metadata.REMOVE_FROM_PLAYERLIST, false);
        npc.getOrAddTrait(Gravity.class).gravitate(true);
        SkinTrait trait = npc.getOrAddTrait(SkinTrait.class);
        trait.setFetchDefaultSkin(false);
        trait.setShouldUpdateSkins(true);
        trait.setSkinName(faction.isSystemFaction() ? "steve" : faction.getLeader().getName());

        npc.getOrAddTrait(LoaderTrait.class).setIDS(faction.getId(), loader.getId());
        npc.spawn(location);
        registry.saveToStore();

        forceLoadChunks(location, loader.chunkLoadRadius);

        player.playSound(player.getLocation(), MConf.get().placeLoaderSound, 1, 1);

        itemInHand.setAmount(itemInHand.getAmount() - 1);
        e.getPlayer().updateInventory();
    }

    public void forceLoadChunks(Location location, int radius) {
        int chunkX = location.getChunk().getX();
        int chunkZ = location.getChunk().getZ();
        for (int x = chunkX - radius; x <= chunkX + radius; x++) {
            for (int z = chunkZ - radius; z <= chunkZ + radius; z++) {
                location.getWorld().getChunkAt(x, z).setForceLoaded(true);
            }
        }
    }

    public void unForceLoadChunks(Location location, int radius) {
        int chunkX = location.getChunk().getX();
        int chunkZ = location.getChunk().getZ();
        for (int x = chunkX - radius; x <= chunkX + radius; x++) {
            for (int z = chunkZ - radius; z <= chunkZ + radius; z++) {
                location.getWorld().getChunkAt(x, z).setForceLoaded(false);
            }
        }
    }

}
