package com.i0dev.vouchers.cmd.type;

import com.i0dev.vouchers.entity.Voucher;
import com.i0dev.vouchers.entity.VoucherColl;
import com.massivecraft.massivecore.command.type.TypeAbstractChoice;
import org.bukkit.command.CommandSender;

import java.util.Collection;

public class TypeVoucher extends TypeAbstractChoice<Voucher> {

    private static final TypeVoucher i = new TypeVoucher();

    public static TypeVoucher get() {
        return i;
    }

    public TypeVoucher() {
        super(Voucher.class);
    }

    public String getName() {
        return "text";
    }

    public Voucher read(String arg, CommandSender sender) {
        return Voucher.get(arg);
    }

    public Collection<String> getTabList(CommandSender sender, String arg) {
        return VoucherColl.get().getIds();
    }
}

