package net.kaoztribe.minigamefixes;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class MinigamesListener implements Listener {

  final Permission keepInvPerm;

  final String lobbyWorld;
  final int    lobbyMinX, lobbyMaxX, lobbyMinZ, lobbyMaxZ;

  MinigamesListener(Plugin plugin) {
    Bukkit.getPluginManager().registerEvents(this, plugin);
    keepInvPerm = Bukkit.getPluginManager().getPermission("minigames.keepInventory");
    lobbyMinX   = plugin.getConfig().getInt("lobby.minX");
    lobbyMaxX   = plugin.getConfig().getInt("lobby.maxX");
    lobbyMinZ   = plugin.getConfig().getInt("lobby.minZ");
    lobbyMaxZ   = plugin.getConfig().getInt("lobby.maxZ");
    lobbyWorld  = plugin.getConfig().getString("lobby.world");
  }

  @EventHandler
  public void onJoin(PlayerJoinEvent e) {
    Player player = e.getPlayer();

    if (!player.hasPermission(keepInvPerm)) {
      player.getInventory().clear();
    }
  }

  /**
   * This is for Paintball, but might be useful enough for other games that I' put it here
   * (also to prevent having to use yet another class :P)
   *
   * @param e the event
   */
  @EventHandler
  public void onPlayerMove(PlayerMoveEvent e) {
    Location to = e.getTo();

    if (to != null && to.getY() < 1 && e.getPlayer().getGameMode() == GameMode.SPECTATOR) {
      to.setY(1);
    }
  }

  /**
   * prevent teleporting for spectators, since this leads to bugs
   * (unchecked Survival modus in other Games)
   */
  @EventHandler
  public void OnPlayerTeleport(PlayerTeleportEvent e) {
    if (e.getCause() == PlayerTeleportEvent.TeleportCause.SPECTATE)
      e.setCancelled(true);
    else {
      Player   player = e.getPlayer();
      Location to     = e.getTo();

      if (to != null && isInLobby(to) && !isInLobby(e.getFrom()) && !player.hasPermission(keepInvPerm)) {
        player.getInventory().clear();
      }
    }
  }

  private boolean isInLobby(@NotNull Location l) {
    int x = l.getBlockX(), z = l.getBlockZ();

    return l.getWorld() != null && l.getWorld().getName().equals(lobbyWorld)
            && x >= lobbyMinX && x <= lobbyMaxX && z >= lobbyMinZ && z <= lobbyMaxZ;
  }

  @EventHandler
  public void onExplosionPrime(ExplosionPrimeEvent e) {
    // flag might be null if there was some init error
    // onlyx use this for TNT!
    if (MinigameFixes.getTntRadiusFlag() != null && e.getEntityType() == EntityType.PRIMED_TNT) {
      RegionContainer     container = WorldGuard.getInstance().getPlatform().getRegionContainer();
      RegionQuery         query = container.createQuery();
      ApplicableRegionSet set = query.getApplicableRegions(BukkitAdapter.adapt(e.getEntity().getLocation()));
      Double              radius = set.queryValue(null, MinigameFixes.getTntRadiusFlag());

      if (radius != null)
        e.setRadius(radius.floatValue());
    }
  }
}
