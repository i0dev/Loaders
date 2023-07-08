package com.i0dev.loaders.entity;

import com.massivecraft.massivecore.store.Coll;

public class LoaderColl extends Coll<Loader> {

    private static final LoaderColl i = new LoaderColl();

    public static LoaderColl get() {
        return i;
    }

    @Override
    public void onTick() {
        super.onTick();
    }

    @Override
    public void setActive(boolean active) {
        super.setActive(active);
    }
}