package net.kaoztribe.minigamefixes;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.DoubleFlag;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import lombok.Getter;
import net.kaoztribe.minigamefixes.parkour.Parkour;
import net.kaoztribe.minigamefixes.villagedefense.VillageDefense;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class MinigameFixes extends JavaPlugin {

  @Override
  public void onEnable() {

    Logger l = getLogger();

    try {
      saveDefaultConfig();
      getConfig().load(new File(getDataFolder(), "config.yml"));
    } catch (IOException e) {
      l.warning("Cannot load config.yml - default values are used!");
      e.printStackTrace();
    } catch (InvalidConfigurationException e) {
      e.printStackTrace();
    }

    l.info("### Initializing ...");
    //general stuff
    l.info("### - adding general fixes");
    new MinigamesListener(this);

    // Village Defense
    if (Bukkit.getPluginManager().isPluginEnabled("VillageDefense")) {
      l.info("### - adding VillageDefense fixes");
      new VillageDefense(this);
    }
    // Parkour
    if (Bukkit.getPluginManager().isPluginEnabled("BedWars")) {
      l.info("### - adding Parkour enhancements");
      try {
        new Parkour(this);
      } catch (Exception e) {
        l.severe("ERROR! Parkour enhancements not available!");
        e.printStackTrace();
      }
    }
  }

  @Getter
  private static DoubleFlag tntRadiusFlag = null;

  private static final String TNT_RADIUS_FLAG_NAME = "tnt-radius";

  @Override
  public void onLoad() {
    super.onLoad();

    FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();
    try {
      // create a flag with the name "my-custom-flag", defaulting to true
      DoubleFlag flag = new DoubleFlag(TNT_RADIUS_FLAG_NAME);
      registry.register(flag);
      tntRadiusFlag = flag; // only set our field if there was no error
    } catch (FlagConflictException e) {
      // some other plugin registered a flag by the same name already.
      // you can use the existing flag, but this may cause conflicts - be sure to check type
      Flag<?> existing = registry.get(TNT_RADIUS_FLAG_NAME);
      if (existing instanceof DoubleFlag) {
        tntRadiusFlag = (DoubleFlag) existing;
      } else {
        getLogger().severe("Cannot add worldguard flag " + TNT_RADIUS_FLAG_NAME + ": a flag with this name but different type already exists!");
      }
    }
  }
}
