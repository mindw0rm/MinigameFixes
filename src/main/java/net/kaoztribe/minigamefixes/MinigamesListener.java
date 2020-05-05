package net.kaoztribe.minigamefixes;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;

public class MinigamesListener implements Listener {

  Permission keepInvPerm;

  MinigamesListener(Plugin plugin) {
    Bukkit.getPluginManager().registerEvents(this, plugin);
    keepInvPerm = Bukkit.getPluginManager().getPermission("minigames.keepInventory");
  }

  @EventHandler
  public void onJoin(PlayerJoinEvent e) {
    Player player = e.getPlayer();

    if (!player.hasPermission(keepInvPerm)) {
      player.getInventory().clear();
    }
  }
}
