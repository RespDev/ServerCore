package codes.settlement.core.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

public class InventoryListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() != null && event.getClickedInventory().getType() == InventoryType.PLAYER) {
            int slot = event.getSlot();

            if (slot >= 4 && slot <= 8) {
                // Cancel inventory clicks on hotbar items
                event.setCancelled(true);
            }
        }
    }
}
