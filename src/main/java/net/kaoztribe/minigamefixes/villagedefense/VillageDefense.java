package net.kaoztribe.minigamefixes.villagedefense;

import net.kaoztribe.minigamefixes.MinigameFixes;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.screamingsandals.bedwars.api.BedwarsAPI;
import pl.plajer.villagedefense.arena.ArenaRegistry;

public class VillageDefense implements Listener {

    private final BedwarsAPI api = BedwarsAPI.getInstance();

  public VillageDefense(MinigameFixes plugin) {
      Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent e) {
      HumanEntity player = e.getPlayer();

      if (player instanceof Player && ArenaRegistry.getArena((Player)player) != null) {
        switch (e.getInventory().getType()) {
          case CHEST:
          case DISPENSER:
          case DROPPER:
          case FURNACE:
          case BREWING:
          case HOPPER:
          case SHULKER_BOX:
          case BARREL:
          case BLAST_FURNACE:
          case LECTERN:
          case SMOKER:
          case LOOM:
            e.setCancelled(true);
        }
      }
    }

  }
