package codes.settlement.core.listener;

import codes.settlement.core.Core;
import codes.settlement.core.menu.Button;
import codes.settlement.core.menu.Menu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class MenuListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        int slot = event.getSlot();

        if (event.getClickedInventory().equals(player.getInventory())) return;

        if (player.hasMetadata("ServerCoreMenu")) {
            Menu menu = (Menu) player.getMetadata("ServerCoreMenu").get(0).value();

            event.setCancelled(true);

            for (Button button : menu.getButtons())
                if (button.getSlot() == slot)
                    button.onClick(player);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();

        if (player.hasMetadata("ServerCoreMenu")) {
            player.removeMetadata("ServerCoreMenu", Core.getInstance());
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (player.hasMetadata("ServerCoreMenu")) {
            player.getMetadata("ServerCoreMenu").clear();
        }
    }
}
