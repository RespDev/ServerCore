package codes.settlement.core.listener.park;

import codes.settlement.core.Core;
import codes.settlement.core.util.Utils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

public class TimeDisplayListener implements Listener {

    public TimeDisplayListener() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (player.getInventory().getHeldItemSlot() == 6) {
                        String timeInEST = Utils.getCurrentTimeInEST();
                        String actionBarText = ChatColor.YELLOW + "" + ChatColor.BOLD + "Current Time In EST: "
                                + ChatColor.GREEN + "" + ChatColor.BOLD + timeInEST;
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(actionBarText));
                    }
                }
            }
        }.runTaskTimer(Core.getInstance(), 0L, 20L);
    }
}
