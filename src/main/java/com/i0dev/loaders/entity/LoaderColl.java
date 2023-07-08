package com.i0dev.vouchers.entity;

import com.massivecraft.massivecore.store.Coll;

public class VoucherColl extends Coll<Voucher> {

    private static final VoucherColl i = new VoucherColl();

    public static VoucherColl get() {
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