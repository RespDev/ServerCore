package codes.settlement.core.item;

import codes.settlement.core.menu.Menu;
import codes.settlement.core.util.Utils;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.util.List;

public class ItemActionExecutor {

    public static void execute(List<String> actions, Player player) {
        if (actions == null) return;

        for (String action : actions) {
            if (action.startsWith("openMenu:")) {
                String className = action.substring("openMenu:".length());
                String fullClassName = "codes.settlement.core.menu.impl." + className;

                try {
                    Class<?> clazz = Class.forName(fullClassName);

                    if (Menu.class.isAssignableFrom(clazz)) {
                        Menu menu;

                        // Try to get a constructor that takes a Player
                        try {
                            Constructor<?> constructor = clazz.getDeclaredConstructor(Player.class);
                            menu = (Menu) constructor.newInstance(player);
                        } catch (NoSuchMethodException e) {
                            // Fall back to a no-arg constructor if the Player one isn't found
                            Constructor<?> constructor = clazz.getDeclaredConstructor();
                            menu = (Menu) constructor.newInstance();
                        }
                        menu.displayTo(player);
                    } else {
                        System.err.println("Class " + fullClassName + " does not implement Menu interface.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (action.startsWith("message:")) {
                player.sendMessage(Utils.color(action.substring("message:".length())));
            }
        }
    }
}
