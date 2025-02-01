package codes.settlement.core.menu.impl;

import codes.settlement.core.menu.Button;
import codes.settlement.core.menu.Menu;
import codes.settlement.core.util.ItemUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TestMenu extends Menu {

    public TestMenu() {

        this.setTitle("Test Menu");

        this.addButton(new Button(9 + 4) {
            @Override
            public ItemStack getItem() {
                return ItemUtil.create(Material.ARROW,
                        "&aOpen sub menu",
                        "",
                        "&aexample button");
            }

            @Override
            public void onClick(Player player) {
                new TestSubMenu().displayTo(player);
            }
        });
    }

    private class TestSubMenu extends Menu {

        public TestSubMenu() {

            this.setTitle("Test Sub-Menu");

            this.addButton(new Button(9 + 4) {
                @Override
                public ItemStack getItem() {
                    return ItemUtil.create(Material.ARROW,
                            "&cBack",
                            "",
                            "&aexample button");
                }

                @Override
                public void onClick(Player player) {
                    new TestMenu().displayTo(player);
                }
            });
        }
    }
}
