package com.i0dev.loaders.entity;

import com.massivecraft.massivecore.command.editor.annotation.EditorName;
import com.massivecraft.massivecore.store.Entity;

@EditorName("config")
public class MLang extends Entity<MLang> {

    protected static transient MLang i;

    public static MLang get() {
        return i;
    }

    public String prefix = "&8[&5Loaders&8]&7";

    public String deletedLoader = "%prefix% &cYou have removed this loader, and received it in your inventory.";
    public String alreadyViewOpen = "%prefix% &cYou already have a view open.";
    public String spawnViewOpened = "%prefix% &aSpawn view opened for 30s!";
    public String gravityToggledOff = "%prefix% &cGravity toggled off.";
    public String gravityToggledOn = "%prefix% &aGravity toggled on.";

    public String gaveLoader = "%prefix% &aYou have given %player% %amount% %loader%!";
    public String onlyPlaceLoadersOnTopOfBlocks = "%prefix% &cYou can only place loaders on top of blocks.";
    public String cantPlaceLoadersInSystemFactions = "%prefix% &cYou can't place loaders in system factions.";
    public String canOnlyPlaceLoadersInYourOwnLand = "%prefix% &cYou can only place loaders in your own land.";
    public String cantManageLoadersInNotYourOwnLand = "%prefix% &cYou can't manage loaders in not your own land. (or wilderness)";

    @Override
    public MLang load(MLang that) {
        super.load(that);
        return this;
    }
}
