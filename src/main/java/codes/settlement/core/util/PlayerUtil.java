package codes.settlement.core.util;

import codes.settlement.core.Core;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public final class PlayerUtil {
    public static void refreshPlayer(Player player) {
        if (Core.getInstance().isPark()) refreshParkItems(player);
    }

    private static void refreshParkItems(Player player) {
        // Item definition
        ItemStack rideItem = new ItemStack(Material.GLASS_PANE);
        ItemMeta rideMeta = rideItem.getItemMeta();
        rideMeta.setDisplayName(Utils.color("&aThis Slot is Reserved for &7(Ride Items)"));
        rideItem.setItemMeta(rideMeta);

        ItemStack backpackItem = new ItemStack(Material.TRAPPED_CHEST);
        ItemMeta backpackMeta = backpackItem.getItemMeta();
        backpackMeta.setDisplayName(Utils.color("&aBackpack &7(Right-Click)"));
        backpackItem.setItemMeta(backpackMeta);

        ItemStack clockItem = new ItemStack(Material.CLOCK);
        ItemMeta clockMeta = clockItem.getItemMeta();
        clockMeta.setDisplayName(Utils.color("&aClock &7(Right-Click)"));
        clockItem.setItemMeta(clockMeta);

        ItemStack autographBook = new ItemStack(Material.BOOK);
        ItemMeta autographMeta = autographBook.getItemMeta();
        autographMeta.setDisplayName(Utils.color("&3Autograph Book"));
        autographMeta.setUnbreakable(true);
        autographMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        autographMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        autographMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        autographMeta.addEnchant(Enchantment.LOYALTY, 1, false);
        autographBook.setItemMeta(autographMeta);

        ItemStack magicbandItem = new ItemStack(Material.GRAY_DYE);
        ItemMeta magicbandMeta = magicbandItem.getItemMeta();
        magicbandMeta.setDisplayName(Utils.color("&aMagicBand &7(Right-Click)"));
        magicbandItem.setItemMeta(magicbandMeta);

        // Set Inventory slots
        player.getInventory().setItem(4, rideItem);
        player.getInventory().setItem(5, backpackItem);
        player.getInventory().setItem(6, clockItem);
        player.getInventory().setItem(7, autographBook);
        player.getInventory().setItem(8, magicbandItem);
    }
}
