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

    @Override
    public MLang load(MLang that) {
        super.load(that);
        return this;
    }
}
