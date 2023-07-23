package com.i0dev.loaders.entity.object;

import com.i0dev.loaders.util.ItemBuilder;
import com.i0dev.loaders.util.Pair;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class ConfigItem {

    Material material;
    String name;
    List<String> lore;
    boolean glow;

    @SafeVarargs
    public final ItemStack getItemStack(Pair<String, String>... loreReplacements) {
        List<String> newLore = new ArrayList<>();
        for (String s : lore) {
            for (Pair<String, String> loreReplacement : loreReplacements) {
                s = s.replace(loreReplacement.getKey(), loreReplacement.getValue());
            }
            newLore.add(s);
        }

        return new ItemBuilder(material)
                .name(name)
                .lore(newLore)
                .addGlow(glow);
    }
}
