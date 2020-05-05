package net.kaoztribe.minigamefixes;

import net.kaoztribe.minigamefixes.bedwars.BedWars;
import net.kaoztribe.minigamefixes.villagedefense.VillageDefense;
import org.bukkit.plugin.java.JavaPlugin;

public class MinigameFixes extends JavaPlugin {

  @Override
  public void onEnable() {

    //general stuff
    new MinigamesListener(this);

    // Bedwars
    new BedWars(this);
    // Village DEfense
    new VillageDefense(this);
  }
  
}
