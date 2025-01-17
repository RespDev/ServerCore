package codes.settlement.core.util;

import org.bukkit.ChatColor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public final class Utils {

    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String getCurrentTimeInEST() {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");
        sdf.setTimeZone(TimeZone.getTimeZone("EST"));
        String time = sdf.format(new Date());
        return time;
    }
}
