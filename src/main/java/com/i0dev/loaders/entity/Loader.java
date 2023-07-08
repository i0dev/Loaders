package com.i0dev.loaders.entity;

import com.i0dev.loaders.util.ItemBuilder;
import com.massivecraft.massivecore.store.Entity;
import com.massivecraft.massivecore.util.MUtil;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter
@Setter
public class Loader extends Entity<Loader> {

    public static Loader get(Object oid) {
        return LoaderColl.get().get(oid);
    }

    public String displayName;
    public Material material;
    public List<String> lore;
    public boolean glow;

    public int chunkLoadRadius = 3;

    @Override
    public Loader load(Loader that) {
        super.load(that);
        this.displayName = that.displayName;
        this.material = that.material;
        this.lore = that.lore;
        this.glow = that.glow;
        this.chunkLoadRadius = that.chunkLoadRadius;
        return this;
    }

    public ItemStack getItemStack(int amount) {
        return new ItemBuilder(material)
                .name(displayName)
                .lore(lore)
                .amount(amount)
                .addPDCValue("loader-id", id)
                .addGlow(glow);
    }


    public static void example() {
        if (LoaderColl.get().containsId("example")) return;
        Loader loader = LoaderColl.get().create("example");
        loader.setDisplayName("&aExample Loader");
        loader.setMaterial(Material.VILLAGER_SPAWN_EGG);
        loader.setLore(MUtil.list("&7This is an example loader", "&7It will load all spawners within 16 blocks, and chunks within 3"));
        loader.setGlow(true);
        loader.setChunkLoadRadius(3);
    }

}
