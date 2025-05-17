package codes.settlement.core.menu.impl;

import codes.settlement.core.menu.Button;
import codes.settlement.core.menu.Menu;
import codes.settlement.core.util.ItemUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class NavigationMenu extends Menu {

    public NavigationMenu() {

        this.setTitle("&9Navigation");

        this.setSize(9);

        this.addButton(new Button(2) {
            @Override
            public ItemStack getItem() {
                return ItemUtil.create(Material.GRASS_BLOCK,
                        "&aCreative",
                        "&7Take your imagination to a whole new",
                        "&7level building whatever you desire to.");
            }

            @Override
            public void onClick(Player player) {
                // Warp to Creative
            }
        });

        this.addButton(new Button(4) {
            @Override
            public ItemStack getItem() {
                return ItemUtil.create(Material.DIAMOND_SWORD,
                        "&aWalt Disney World",
                        "&7Experience the magic of the Disney parks.",
                        "&7Rides, shows, memories, and much more.");
            }

            @Override
            public void onClick(Player player) {
                // Warp to WDW
            }
        });

        this.addButton(new Button(6) {
            @Override
            public ItemStack getItem() {
                return ItemUtil.create(Material.WOODEN_SWORD,
                        "&aDisney Land",
                        "&7Experience the magic of the Disney parks.",
                        "&7Rides, shows, memories, and much more.");
            }

            @Override
            public void onClick(Player player) {
                // Warp to DL
            }
        });
    }
}
