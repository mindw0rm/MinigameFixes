package net.kaoztribe.minigamefixes.bedwars;

import net.kaoztribe.minigamefixes.MinigameFixes;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.screamingsandals.bedwars.api.BedwarsAPI;

public class BedWars implements Listener {

  private final BedwarsAPI api = BedwarsAPI.getInstance();

  public BedWars(MinigameFixes plugin) {
    Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
  }

  @EventHandler
  public void onInventoryClick(InventoryClickEvent e) {
    HumanEntity he = e.getWhoClicked();

    if (he instanceof Player && e.getClickedInventory() != null) {
      Player player = (Player)he;

      if (api.isPlayerPlayingAnyGame(player)) {
        switch (e.getClickedInventory().getType()) {
          case CRAFTING:
          case PLAYER:
            int raw = e.getRawSlot();

            if (!player.isOnGround() || raw >= 1 && raw <= 4)
              e.setCancelled(true);
        }
      }
    }
  }

  @EventHandler
  public void onPlayerSpawn(PlayerRespawnEvent e) {
    Player player = e.getPlayer();

    if (api.isPlayerPlayingAnyGame(player)) {
      player.getInventory().clear();
    }
  }

}
