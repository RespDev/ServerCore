package codes.settlement.core.listener.park;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public final class PlayerDropItem implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPermission("core.mod")) {
            // Non-mods can't drop items
            event.setCancelled(true);
            return;
        }

        // Check if it is a parks hotbar item
        int slot = player.getInventory().getHeldItemSlot();
        if (slot >= 4 && slot <= 8) {
            event.setCancelled(true);
        }
    }
}
