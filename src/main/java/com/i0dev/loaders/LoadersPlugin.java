package com.i0dev.loaders;

import com.i0dev.loaders.engine.EngineLoader;
import com.i0dev.loaders.entity.Loader;
import com.i0dev.loaders.entity.LoaderColl;
import com.i0dev.loaders.entity.MConfColl;
import com.i0dev.loaders.entity.MLangColl;
import com.i0dev.loaders.integration.LoaderTrait;
import com.massivecraft.massivecore.MassivePlugin;
import com.massivecraft.massivecore.collections.MassiveList;
import lombok.Getter;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPCDataStore;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.api.npc.SimpleNPCDataStore;
import net.citizensnpcs.api.trait.TraitInfo;
import net.citizensnpcs.api.util.Storage;
import net.citizensnpcs.api.util.YamlStorage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LoadersPlugin extends MassivePlugin {

    private static LoadersPlugin i;

    public LoadersPlugin() {
        LoadersPlugin.i = this;
    }

    public static LoadersPlugin get() {
        return i;
    }

    @Override
    public void onEnableInner() {
        this.activateAuto();
    }


    @Getter
    NPCRegistry npcRegistry;

    @Override
    public void onEnablePost() {
        super.onEnablePost();
        CitizensAPI.getTraitFactory().registerTrait(TraitInfo.create(LoaderTrait.class));
        Loader.example();
        if (!this.getDataFolder().exists())
            this.getDataFolder().mkdir();

        Storage storage = new YamlStorage(new File(this.getDataFolder().getPath() + "/npcRegistry.yml"), "NPC Registry");
        NPCDataStore store = SimpleNPCDataStore.create(storage);
        store.reloadFromSource();
        npcRegistry = CitizensAPI.createNamedNPCRegistry("loaders", store);
        store.loadInto(npcRegistry);

        LoadersPlugin.get().getNpcRegistry().sorted().forEach(npc -> {
            System.out.println("Loading NPC " + npc.getId() + " from " + npc.getStoredLocation().getWorld().getName());
            if (!npc.isSpawned()) {
                System.out.println("NPC " + npc.getId() + " is not spawned, spawning...");
                npc.spawn(npc.getStoredLocation());
            }
            EngineLoader.get().forceLoadChunks(npc.getStoredLocation(), Loader.get(npc.getTraitNullable(LoaderTrait.class).getLoaderID()).getChunkLoadRadius());
        });


    }

    @Override
    public List<Class<?>> getClassesActiveColls() {
        return new MassiveList<>(
                MConfColl.class,
                MLangColl.class,

                LoaderColl.class
        );
    }

}