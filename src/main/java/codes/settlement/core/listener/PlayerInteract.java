package codes.settlement.core.listener;

import codes.settlement.core.util.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public final class PlayerInteract implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        // Handle inventory click
        ItemStack hand = event.getItem();
        if (hand == null || hand.getType() == null) return;
        int slot = player.getInventory().getHeldItemSlot();
        boolean cancel = false;
        switch (slot) {
            case 5:
                // Backpack
                cancel = true;
                player.sendMessage(Utils.color("&aBackpack comming soon!"));
                break;
            case 6:
                // Watch
                player.sendMessage(Utils.color("&aWatch comming soon!"));
                break;
            case 7:
                // Autograph Book
                if (!event.getAction().equals(Action.PHYSICAL)) {
                    cancel = true;
                    player.sendMessage(Utils.color("&aAutograph Book comming soon!"));
                }
                break;
            case 8:
                // MagicBand
                player.sendMessage(Utils.color("&aMagicBand comming soon!"));
                break;
        }
        if (cancel) event.setCancelled(true);
    }
}
