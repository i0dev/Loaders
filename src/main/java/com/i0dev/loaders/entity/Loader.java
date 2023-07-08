package com.i0dev.vouchers.entity;

import com.i0dev.vouchers.util.ItemBuilder;
import com.massivecraft.massivecore.store.Entity;
import com.massivecraft.massivecore.util.MUtil;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Getter
@Setter
public class Voucher extends Entity<Voucher> {

    public static Voucher get(Object oid) {
        return VoucherColl.get().get(oid);
    }

    public String displayName;
    public Material material;
    public List<String> lore;
    public boolean glow;
    public Sound sound;
    public String refuseUseIfHasPermission;
    public List<String> commands;

    @Override
    public Voucher load(@NotNull Voucher that) {
        super.load(that);
        this.displayName = that.displayName;
        this.material = that.material;
        this.lore = that.lore;
        this.glow = that.glow;
        this.refuseUseIfHasPermission = that.refuseUseIfHasPermission;
        this.commands = that.commands;
        return this;
    }

    public ItemStack getItemStack(int amount) {
        return new ItemBuilder(material)
                .name(displayName)
                .lore(lore)
                .amount(amount)
                .addPDCValue("voucher-id", id)
                .addGlow(glow);
    }


    public static void example() {
        if (VoucherColl.get().containsId("example")) return;
        Voucher voucher = VoucherColl.get().create("example");
        voucher.setDisplayName("&aExample Voucher");
        voucher.setMaterial(Material.PAPER);
        voucher.setSound(Sound.ENTITY_GENERIC_EXPLODE);
        voucher.setLore(MUtil.list("&7This is an example voucher", "&7You can use it to get a reward"));
        voucher.setGlow(true);
        voucher.setRefuseUseIfHasPermission("example.permission");
        voucher.setCommands(MUtil.list("give %player% diamond 1"));
    }

}
