package com.i0dev.loaders.cmd;

import com.i0dev.loaders.cmd.type.TypeLoader;
import com.i0dev.loaders.entity.Loader;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.primitive.TypeInteger;
import com.massivecraft.massivecore.command.type.sender.TypePlayer;
import org.bukkit.entity.Player;

public class CmdLoadersGive extends LoadersCommand {

    public CmdLoadersGive() {
        this.addParameter(TypePlayer.get(), "player");
        this.addParameter(TypeLoader.get(), "loader");
        this.addParameter(TypeInteger.get(), "amount");
    }


    @Override
    public void perform() throws MassiveException {
        Player player = this.readArg();
        Loader loader = this.readArg();
        int amount = this.readArg();


        player.getInventory().addItem(loader.getItemStack(amount));
        player.sendMessage("You have been given " + amount + " " + loader.getDisplayName() + "!");

    }
}
