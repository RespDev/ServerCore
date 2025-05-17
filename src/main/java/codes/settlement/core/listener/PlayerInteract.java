package codes.settlement.core.listener;

import codes.settlement.core.item.HotbarItem;
import codes.settlement.core.item.ItemActionExecutor;
import codes.settlement.core.manager.HotbarManager;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class PlayerInteract implements Listener {

    private final HotbarManager hotbarManager;

    public PlayerInteract(HotbarManager hotbarManager) {
        this.hotbarManager = hotbarManager;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        
        if (player.getGameMode() == GameMode.CREATIVE) return;

        ItemStack itemInHand = event.getItem();
        if (itemInHand == null) return;

        HotbarItem hotbarItem = hotbarManager.getItemFromStack(itemInHand);
        if (hotbarItem == null) return;

        if (event.getAction() != Action.PHYSICAL) {
            event.setCancelled(true);
        }

        hotbarItem.execute(player);
    }
}