package codes.settlement.core.listeners;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;
import java.util.UUID;

public class PlayerBuild implements Listener {
    private static ArrayList<UUID> allowBuild = new ArrayList<UUID>();

    // Build Mode System
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (allowBuild.contains(event.getPlayer().getUniqueId())) return;
        if (player.hasPermission("core.mod")) player.sendMessage(ChatColor.RED + "You must be in Build Mode to break blocks!");
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (allowBuild.contains(event.getPlayer().getUniqueId())) return;
        if (player.hasPermission("core.mod")) player.sendMessage(ChatColor.RED + "You must be in Build Mode to place blocks!");
        event.setCancelled(true);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        if (allowBuild.contains(event.getPlayer().getUniqueId())) allowBuild.remove(event.getPlayer().getUniqueId());
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        Entity entity = event.getEntity();

        switch (entity.getType()) {
            case MINECART:
            case CHEST_MINECART:
            case COMMAND_BLOCK_MINECART:
            case FURNACE_MINECART:
            case HOPPER_MINECART:
            case SPAWNER_MINECART:
            case TNT_MINECART: {
                if (damager instanceof Player) {
                    Player player = (Player) damager;
                    if (!player.hasPermission("core.trainee")) {
                        event.setCancelled(true);
                    }
                } else if (damager instanceof Arrow) {
                    event.setCancelled(true);
                }
                break;
            }
            case ITEM_FRAME: {
                if (handleItemFrameDamage(entity, damager)) {
                    event.setCancelled(true);
                }
                break;
            }
            case GLOW_ITEM_FRAME: {
                if (handleItemFrameDamage(entity, damager)) {
                    event.setCancelled(true);
                }
                break;
            }
            case ARMOR_STAND: {
                if (damager instanceof Player) {
                    Player player = (Player) damager;
                    if (player.hasPermission("core.mod")) {
                        if (!allowBuild.contains(player.getUniqueId())) {
                            event.setCancelled(true);
                            player.sendMessage(ChatColor.RED + "You must be in Build Mode to break entities!");
                        }
                    } else {
                        event.setCancelled(true);
                        player.sendMessage(ChatColor.RED + "You are not allowed to break this!");
                    }
                }
                break;
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
        Entity entity = event.getRightClicked();
        Player player = event.getPlayer();

        switch (entity.getType()) {
            case ARMOR_STAND:
            case ITEM_FRAME: {
                if (player.hasPermission("core.mod")) {
                    if (!allowBuild.contains(player.getUniqueId())) {
                        event.setCancelled(true);
                        player.sendMessage(ChatColor.RED + "You must be in Build Mode to edit entities!");
                    }
                } else {
                    event.setCancelled(true);
                    player.sendMessage(ChatColor.RED + "You are not allowed to edit this!");
                }
            }
            case GLOW_ITEM_FRAME: {
                if (player.hasPermission("core.mod")) {
                    if (!allowBuild.contains(player.getUniqueId())) {
                        event.setCancelled(true);
                        player.sendMessage(ChatColor.RED + "You must be in Build Mode to edit entities!");
                    }
                } else {
                    event.setCancelled(true);
                    player.sendMessage(ChatColor.RED + "You are not allowed to edit this!");
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityInteract(PlayerInteractEntityEvent event) {
        Entity clicked = event.getRightClicked();
        Player player = event.getPlayer();

        switch (clicked.getType()) {
            case ARMOR_STAND:
            case ITEM_FRAME: {
                if (!player.hasPermission("core.mod")) {
                    event.setCancelled(true);
                    player.sendMessage(ChatColor.RED + "You are not allowed to place item frames!");
                } else if (!allowBuild.contains(player.getUniqueId())) {
                    event.setCancelled(true);
                    player.sendMessage(ChatColor.RED + "You must be in Build Mode to place item frames!");
                }
                break;
            }
            case GLOW_ITEM_FRAME: {
                if (!player.hasPermission("core.mod")) {
                    event.setCancelled(true);
                    player.sendMessage(ChatColor.RED + "You are not allowed to place item frames!");
                } else if (!allowBuild.contains(player.getUniqueId())) {
                    event.setCancelled(true);
                    player.sendMessage(ChatColor.RED + "You must be in Build Mode to place item frames!");
                }
                break;
            }
            case PAINTING: {
                if (!player.hasPermission("core.mod")) {
                    event.setCancelled(true);
                    player.sendMessage(ChatColor.RED + "You are not allowed to edit this!");
                } else if (!allowBuild.contains(player.getUniqueId())) {
                    event.setCancelled(true);
                    player.sendMessage(ChatColor.RED + "You must be in Build Mode to edit entities!");
                }
                break;
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onHangingBreak(HangingBreakByEntityEvent event) {
        Entity remover = event.getRemover();
        Entity entity = event.getEntity();

        switch (entity.getType()) {
            case ITEM_FRAME:
            case GLOW_ITEM_FRAME:
            case PAINTING: {
                if (handleItemFrameDamage(entity, remover)) {
                    event.setCancelled(true);
                }
                break;
            }
        }
    }

    private boolean handleItemFrameDamage(Entity entity, Entity damager) {
        if (damager instanceof Player) {
            Player player = (Player) damager;

            if (player.hasPermission("core.mod")) {
                if (!allowBuild.contains(player.getUniqueId())) {
                    player.sendMessage(ChatColor.RED + "You must be in Build Mode to break entities!");
                    return true;
                } else if (entity instanceof ItemFrame) {
                    ItemFrame frame = (ItemFrame) entity;
                    if (frame.getItem() != null) {
                        frame.setItem(new ItemStack(Material.AIR)); // Clear the item to prevent duplication bugs
                    }
                }
            } else {
                player.sendMessage(ChatColor.RED + "You are not allowed to break this!");
                return true;
            }
        }
        return false;
    }

    @EventHandler
    public void onGameModeChange(PlayerGameModeChangeEvent event) {
        Player player = event.getPlayer();
        GameMode gameMode = event.getNewGameMode();
        if (allowBuild.contains(event.getPlayer().getUniqueId())) {
            if (!gameMode.equals(GameMode.CREATIVE) && !gameMode.equals(GameMode.SPECTATOR)) {
                event.setCancelled(true);
                if (player.hasPermission("core.mod")) player.sendMessage(ChatColor.RED + "You must exit Build Mode to go into Survival or Adventure mode!");
            }
        } else {
            if (!gameMode.equals(GameMode.ADVENTURE) && !gameMode.equals(GameMode.SURVIVAL)) {
                event.setCancelled(true);
                if (player.hasPermission("core.mod")) player.sendMessage(ChatColor.RED + "You must be in Build Mode to go into Creative or Spectator mode!");
            }
        }
    }

    public static void toggleBuild(Player player) {
        UUID uuid = player.getUniqueId();

        // Quick permission check
        if (!player.hasPermission("core.mod")) return;

        if (allowBuild.contains(uuid)) {
            allowBuild.remove(uuid);
            player.setGameMode(GameMode.ADVENTURE);
            player.sendMessage(ChatColor.RED + "Build Mode disabled.");
        } else {
            allowBuild.add(uuid);
            player.setGameMode(GameMode.CREATIVE);
            player.sendMessage(ChatColor.GREEN + "Build Mode enabled.");
        }
    }
}