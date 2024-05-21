package com.i0dev.loaders.entity;

import com.i0dev.loaders.entity.object.ConfigItem;
import com.massivecraft.massivecore.command.editor.annotation.EditorName;
import com.massivecraft.massivecore.store.Entity;
import com.massivecraft.massivecore.util.MUtil;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;

import java.util.List;

@EditorName("config")
public class MConf extends Entity<MConf> {

    protected static transient MConf i;

    public static MConf get() {
        return i;
    }

    public List<String> aliases = MUtil.list("loaders");
    public Sound placeLoaderSound = Sound.BLOCK_NOTE_BLOCK_BELL;

    public long spawnViewTime = 30L;
    public int maxLoadersView = 5;

    public EntityType loaderEntityType = EntityType.ALLAY;

    public int manageLoaderGuiSize = 27;
    public String manageLoaderTitle = "&aManage Loader";
    public ConfigItem borderItem = new ConfigItem(
            Material.BLACK_STAINED_GLASS_PANE,
            "&f",
            MUtil.list(),
            true
    );

    public int infoSlot = 4;
    public ConfigItem infoItem = new ConfigItem(
            Material.PAPER,
            "&f&lINFO",
            MUtil.list(
                    "",
                    "&cThis loader is owned by %faction%",
                    "&cIt will load all spawners within 16 blocks",
                    "&cIt will load all chunks within %radius% chunks",
                    "",
                    "&cClick the barrier to remove this loader, and get the spawn egg back",
                    "&cClick the eye to view the spawner range",
                    "&cClick the elytra to toggle gravity"
            ),
            true
    );

    public int toggleGravitySlot = 11;
    public String enabledState = "&aENABLED";
    public String disabledState = "&cDISABLED";
    public ConfigItem toggleGravityItem = new ConfigItem(
            Material.ELYTRA,
            "&f&lTOGGLE GRAVITY",
            MUtil.list(
                    "",
                    "&cClick to toggle gravity",
                    "&cCurrent state: %state%"
            ),
            true
    );

    public int viewRangeSlot = 13;
    public ConfigItem viewSpawnerRangeItem = new ConfigItem(
            Material.ENDER_EYE,
            "&f&lVIEW SPAWNER RANGE",
            MUtil.list(
                    "",
                    "&cClick to view the spawner range",
                    "&cThis will open a view for %time% seconds",
                    "&cYou can only have one view open at a time",
                    "&cYou can only view loaders in your own land"
            ),
            true
    );

    public int removedLoaderSlot = 15;
    public ConfigItem removeLoaderItem = new ConfigItem(
            Material.VILLAGER_SPAWN_EGG,
            "&f&lREMOVE LOADER",
            MUtil.list(
                    "",
                    "&cClick to remove this loader",
                    "&cYou will receive the spawn egg back in your inventory",
                    "&cYou can only remove loaders in your own land",
                    "&cYou can't remove loaders in system factions"
            ),
            true
    );

    public String commandToGiveLeaderLoadersOnDisband = "inboxes give %player% loader-voucher %amount%";


    @Override
    public MConf load(MConf that) {
        super.load(that);
        return this;
    }

}
