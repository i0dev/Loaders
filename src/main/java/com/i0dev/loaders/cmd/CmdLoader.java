package com.i0dev.loaders.cmd;

import com.i0dev.loaders.Perm;
import com.i0dev.loaders.LoadersPlugin;
import com.i0dev.loaders.entity.MConf;
import com.massivecraft.massivecore.command.MassiveCommandVersion;
import com.massivecraft.massivecore.command.requirement.RequirementHasPerm;

import java.util.List;

public class CmdGlobalCurrency extends GlobalCurrencyCommand {

    private static CmdGlobalCurrency i = new CmdGlobalCurrency();

    public Cmd
    public MassiveCommandVersion cmdFactionsVersion = new MassiveCommandVersion(LoadersPlugin.get()).setAliases("v", "version").addRequirements(RequirementHasPerm.get(Perm.VERSION));

    public static CmdGlobalCurrency get() {
        return i;
    }

    @Override
    public List<String> getAliases() {
        return MConf.get().aliases;
    }

}
