package net.kaoztribe.minigamefixes.parkour;

import net.kaoztribe.minigamefixes.MinigameFixes;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@SuppressWarnings("unused")
public class Parkour implements Listener {

  private final Method isPlaying;

  public Parkour(MinigameFixes plugin) throws ClassNotFoundException, NoSuchMethodException {
    /*
     * for some weird reason the class path to PlayerMethods start with uppercase,
     * but those are not found during runtime!
     * so circumvent this problem by dynamically loading the class
     */
    Class<?> pmClass = getClass().getClassLoader().loadClass("me.A5H73Y.parkour.player.PlayerMethods");

    isPlaying = pmClass.getMethod("isPlaying", String.class);

    Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
  }

  @EventHandler
  public void onInteract(PlayerInteractEvent e) throws InvocationTargetException, IllegalAccessException {
    Player p = e.getPlayer();
    Block  b = e.getClickedBlock();

    // give blocks
    if (e.getAction() == Action.RIGHT_CLICK_BLOCK  // handle click on block
            && b != null
            && e.getHand() == EquipmentSlot.HAND   // only main hand
            && inParkour(p)) { // and only if player is in Parkour course
      if (b.getType() == Material.ENCHANTING_TABLE) {
          // put elytra in CHEST slot
          ItemStack elytra = new ItemStack(Material.ELYTRA);

          elytra.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
          p.getInventory().setChestplate(elytra);
          e.setCancelled(true);
      }
    }
  }

  @EventHandler
  public void onPlayerMove(PlayerMoveEvent e) throws InvocationTargetException, IllegalAccessException {
    Player p = e.getPlayer();

    if (inParkour(p)) {
      if (p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.RED_NETHER_BRICKS) {
        p.getInventory().setChestplate(null);
      }
    }
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onInventoryClick(InventoryClickEvent e) throws InvocationTargetException, IllegalAccessException {
    HumanEntity h = e.getWhoClicked();

    if (h instanceof Player && inParkour((Player)h)) {
      ItemStack item = e.getCurrentItem();

      // instead of checking slot index, just cancel everything that does something with elytra
      if (item != null && item.getType() == Material.ELYTRA) {
        e.setCancelled(true);
      }
    }
  }

  private boolean inParkour(Player p) throws InvocationTargetException, IllegalAccessException {
    return (Boolean)isPlaying.invoke(null, p.getName());
  }
}
