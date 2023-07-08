package com.i0dev.loaders.cmd.type;

import com.i0dev.loaders.entity.Loader;
import com.i0dev.loaders.entity.LoaderColl;
import com.massivecraft.massivecore.command.type.TypeAbstractChoice;
import org.bukkit.command.CommandSender;

import java.util.Collection;

public class TypeLoader extends TypeAbstractChoice<Loader> {

    private static final TypeLoader i = new TypeLoader();

    public static TypeLoader get() {
        return i;
    }

    public TypeLoader() {
        super(Loader.class);
    }

    public String getName() {
        return "text";
    }

    public Loader read(String arg, CommandSender sender) {
        return Loader.get(arg);
    }

    public Collection<String> getTabList(CommandSender sender, String arg) {
        return LoaderColl.get().getIds();
    }
}

