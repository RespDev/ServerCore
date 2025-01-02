package codes.settlement.core.util;

import org.bukkit.ChatColor;

public final class Utils {

    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
