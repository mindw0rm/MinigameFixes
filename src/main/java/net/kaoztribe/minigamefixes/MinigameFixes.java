package net.kaoztribe.minigamefixes;

import net.kaoztribe.minigamefixes.bedwars.BedWars;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.stream.Collectors;

public class MinigameFixes extends JavaPlugin {

  @Override
  public void onEnable() {

    //general stuff
    new MinigamesListener(this);

    // Bedwars
    new BedWars(this);

    int[] newarray = new int[66];

    Arrays.stream(newarray).mapToObj(i -> "" + i).collect(Collectors.joining(", "));
  }
  
}
