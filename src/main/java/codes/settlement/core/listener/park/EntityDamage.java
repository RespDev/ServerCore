package codes.settlement.core.listener.park;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public final class EntityDamage implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamage(EntityDamageEvent event) {
        boolean cancel = false;
        if (event.getEntityType().equals(EntityType.PLAYER)) {
            // Prevent all player damage
            cancel = true;
        } else if (event.getCause().equals(EntityDamageEvent.DamageCause.PROJECTILE)) {
            // Prevent damage caused by projectiles
            cancel = true;
        }
        if (cancel) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onTntExplosionEvent(BlockExplodeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityExplosionEvent(EntityExplodeEvent event) {
        event.blockList().clear();
    }
}
