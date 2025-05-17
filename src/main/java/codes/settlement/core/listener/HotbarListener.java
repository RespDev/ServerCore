package codes.settlement.core.listener;

import codes.settlement.core.Core;
import codes.settlement.core.manager.HotbarManager;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class HotbarListener implements Listener {
    private final HotbarManager hotbarManager;

    public HotbarListener(HotbarManager hotbarManager) {
        this.hotbarManager = hotbarManager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        new BukkitRunnable() {
            @Override
            public void run() {
                if (player.isOnline() && player.getGameMode() != GameMode.CREATIVE) {
                    hotbarManager.applyHotbar(player);
                } else {
                    for (int slot : hotbarManager.getLayout().keySet()) {
                        player.getInventory().setItem(slot, null);
                    }
                }
            }
        }.runTaskLater(Core.getInstance(), 5L);
    }

    @EventHandler
    public void onGameModeChange(PlayerGameModeChangeEvent event) {
        Player player = event.getPlayer();
        GameMode newMode = event.getNewGameMode();

        if (newMode == GameMode.CREATIVE) {
            for (int slot : hotbarManager.getLayout().keySet()) {
                ItemStack item = player.getInventory().getItem(slot);
                if (hotbarManager.getItemFromStack(item) != null) {
                    player.getInventory().setItem(slot, null);
                }
            }
        } else {
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (player.isOnline()) {
                        hotbarManager.applyHotbar(player);
                    }
                }
            }.runTaskLater(Core.getInstance(), 5L);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;
        ItemStack current = event.getCurrentItem();
        if (hotbarManager.getItemFromStack(current) != null) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (hotbarManager.getItemFromStack(event.getItemDrop().getItemStack()) != null) {
            event.setCancelled(true);
        }
    }
}
