package codes.settlement.core.menu.impl;

import codes.settlement.core.constant.Item;
import codes.settlement.core.menu.Button;
import codes.settlement.core.menu.Menu;
import codes.settlement.core.util.ItemUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MagicBandMenu extends Menu {

    public MagicBandMenu(Player player) {
        this.setTitle("&9MagicBand");

        // Profile Button (Slot 4)
        this.addButton(new Button(4) {
            @Override
            public ItemStack getItem() {
                return ItemUtil.createSkull(player.getName(),
                        "&e" + player.getName() + "'s Profile",
                        "&7Rank: &f<rank>",
                        "&7Money: &a$<money>",
                        "&7Tokens: &a<tokens>");
            }

            @Override
            public void onClick(Player player) {
                // Open profile menu
            }
        });

        // Food Button (Slot 10)
        this.addButton(new Button(10) {
            @Override
            public ItemStack getItem() {
                return ItemUtil.create(Material.COOKED_BEEF,
                        "&eFind Food",
                        "&7Craving something tasty?",
                        "&fDiscover nearby restaurants",
                        "&fand dining experiences.");
            }

            @Override
            public void onClick(Player player) {
                // Open food menu
            }
        });

        // Hotel Button (Slot 11)
        this.addButton(new Button(11) {
            @Override
            public ItemStack getItem() {
                return ItemUtil.create(Material.RED_BED,
                        "&eFind Hotel",
                        "&7Need to recharge?",
                        "&fBrowse cozy resorts and",
                        "&fbook a magical night’s stay.");
            }

            @Override
            public void onClick(Player player) {
                // Open hotel menu
            }
        });

        // Rides & Attractions Button (Slot 12)
        this.addButton(new Button(12) {
            @Override
            public ItemStack getItem() {
                return ItemUtil.create(Material.MINECART,
                        "&eRides & Attractions",
                        "&7Choose from a ton of thrilling rides!",
                        "&fExperience heart-pounding excitement,",
                        "&ffast-paced fun, and unforgettable adventures.");
            }

            @Override
            public void onClick(Player player) {
                // Open show schedule
            }
        });

        // Park Menu Button (Slot 13)
        this.addButton(new Button(13) {
            @Override
            public ItemStack getItem() {
                return ItemUtil.create(Material.MAP,
                        "&ePark Menu",
                        "&7Pick your adventure!",
                        "&fChoose from thrilling parks",
                        "&fand explore their magic.");
            }

            @Override
            public void onClick(Player player) {
                new ParkSubMenu().displayTo(player);
            }
        });

        // Shop Button (Slot 14)
        this.addButton(new Button(14) {
            @Override
            public ItemStack getItem() {
                return ItemUtil.create(Material.EMERALD,
                        "&eShop",
                        "&7Looking for souvenirs?",
                        "&fBrowse exclusive items,",
                        "&ffrom gear to goodies.");
            }

            @Override
            public void onClick(Player player) {
                // Open shop menu
            }
        });

        // Wardrobe Button (Slot 15)
        this.addButton(new Button(15) {
            @Override
            public ItemStack getItem() {
                return ItemUtil.create(Material.LEATHER_CHESTPLATE,
                        "&eWardrobe",
                        "&7Style it your way!",
                        "&fCustomize your outfit and",
                        "&fswitch between looks.");
            }

            @Override
            public void onClick(Player player) {
                // Open wardrobe menu
            }
        });

        // Show Schedule Button (Slot 16)
        this.addButton(new Button(16) {
            @Override
            public ItemStack getItem() {
                return ItemUtil.create(Material.CLOCK,
                        "&eUpcoming Shows",
                        "&7Don’t miss a moment!",
                        "&fView times for live shows,",
                        "&fparades, and events.");
            }

            @Override
            public void onClick(Player player) {
                // Open show schedule
            }
        });

        // Close Button (Slot 22)
        this.addButton(new Button(22) {
            @Override
            public ItemStack getItem() {
                return Item.closeButton;
            }

            @Override
            public void onClick(Player player) {
                player.closeInventory();
            }
        });
    }

    private class ParkSubMenu extends Menu {

        public ParkSubMenu() {

            this.setTitle("&9Parks");

            this.setSize(9 * 2);

            this.addButton(new Button(2) {
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
                    return ItemUtil.create(Material.RED_BED,
                            "&aResorts",
                            "&7Choose a place to stay in",
                            "&7one of the many resorts.");
                }

                @Override
                public void onClick(Player player) {
                    // Warp to DL
                }
            });

            this.addButton(new Button(13) {
                @Override
                public ItemStack getItem() {
                    return Item.backButton;
                }

                @Override
                public void onClick(Player player) {
                    new MagicBandMenu(player).displayTo(player);
                }
            });
        }
    }
}
