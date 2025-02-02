package com.i0dev.loaders.cmd;

import com.i0dev.loaders.cmd.type.TypeLoader;
import com.i0dev.loaders.entity.Loader;
import com.i0dev.loaders.entity.MLang;
import com.i0dev.loaders.util.Pair;
import com.i0dev.loaders.util.Utils;
import com.massivecraft.factions.util.MiscUtil;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.primitive.TypeInteger;
import com.massivecraft.massivecore.command.type.sender.TypePlayer;
import org.bukkit.entity.Player;

public class CmdLoaderGive extends LoadersCommand {

    public CmdLoaderGive() {
        this.addParameter(TypePlayer.get(), "player");
        this.addParameter(TypeLoader.get(), "loader");
        this.addParameter(TypeInteger.get(), "amount", "1");
    }


    @Override
    public void perform() throws MassiveException {
        Player player = this.readArg();
        Loader loader = this.readArg();
        int amount = this.readArg(1);

        player.getInventory().addItem(loader.getItemStack(amount));

        msg(Utils.prefixAndColor(MLang.get().gaveLoader,
                new Pair<>("%player%", player.getName()),
                new Pair<>("%amount%", String.valueOf(amount)),
                new Pair<>("%loader%", loader.getDisplayName())
        ));

    }
}
