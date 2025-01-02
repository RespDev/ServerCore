package codes.settlement.core.util;

import org.bukkit.Bukkit;

public final class LoggingUtil {

    public static void logMessage(String name, String message) {
        Bukkit.getConsoleSender().sendMessage(Utils.color("&9" + name + "> &f" + message));
    }
}