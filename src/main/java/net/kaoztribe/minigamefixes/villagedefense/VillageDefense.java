package net.kaoztribe.minigamefixes.villagedefense;

import net.kaoztribe.minigamefixes.MinigameFixes;
import org.bukkit.Bukkit;
import org.bukkit.block.Container;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import pl.plajer.villagedefense.arena.ArenaRegistry;

public class VillageDefense implements Listener {

  public VillageDefense(MinigameFixes plugin) {
      Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
      // since both the kit selector and the villager shop use inventory type CHEST, precenting the
      // opening of real chests is difficult to detect in onInventoryOpen
      // => use interact
      if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)
              && e.getClickedBlock().getState() instanceof Container
              && ArenaRegistry.getArena(e.getPlayer()) != null) {
        e.setCancelled(true);
      }
    }
 }
