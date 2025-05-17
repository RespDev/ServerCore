package codes.settlement.core.util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class ItemUtil {

    /**
     * Create item stack.
     *
     * @param type the type
     * @return the item stack
     */
    public static ItemStack create(Material type) {
        return create(type, 1);
    }

    /**
     * Create item stack.
     *
     * @param type   the type
     * @param amount the amount
     * @return the item stack
     */
    public static ItemStack create(Material type, int amount) {
        return new ItemStack(type, amount);
    }

    /**
     * Create item stack.
     *
     * @param type   the type
     * @param amount the amount
     * @param damage the damage
     * @return the item stack
     */
    public static ItemStack create(Material type, int amount, int damage) {
        ItemStack item = create(type, amount);
        item.setDurability((short) damage);
//        ItemMeta meta = item.getItemMeta();
//        ((Damageable) meta).setDamage(damage);
//        item.setItemMeta(meta);
        return item;
    }

    /**
     * Create item stack.
     *
     * @param type the type
     * @param name the name
     * @return the item stack
     */
    public static ItemStack create(Material type, String name) {
        return create(type, Utils.color(name), new ArrayList<>());
    }

    /**
     * Create item stack.
     *
     * @param type   the type
     * @param name   the name
     * @param damage the damage
     * @return the item stack
     */
    public static ItemStack create(Material type, String name, int damage) {
        ItemStack item = create(type, name);
        item.setDurability((short) damage);
//        ItemMeta meta = item.getItemMeta();
//        ((Damageable) meta).setDamage(damage);
//        item.setItemMeta(meta);
        return item;
    }

    /**
     * Create item stack.
     *
     * @param type the type
     * @param name the name
     * @param lore the lore
     * @return the item stack
     */
    public static ItemStack create(Material type, String name, List<String> lore) {
        return create(type, 1, Utils.color(name), lore);
    }

    /**
     * Create item stack.
     *
     * @param type   the type
     * @param amount the amount
     * @param name   the name
     * @param lore   the lore
     * @return the item stack
     */
    public static ItemStack create(Material type, int amount, String name, List<String> lore) {
        ItemStack item = create(type, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.color(name));
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS);
        item.setItemMeta(meta);
        return item;
    }

    /**
     * Create item stack.
     *
     * @param type   the type
     * @param amount the amount
     * @param damage the damage
     * @param name   the name
     * @param lore   the lore
     * @return the item stack
     */
    public static ItemStack create(Material type, int amount, int damage, String name, List<String> lore) {
        ItemStack item = create(type, amount);
        item.setDurability((short) damage);
        ItemMeta meta = item.getItemMeta();
//        ((Damageable) meta).setDamage(damage);
        meta.setDisplayName(Utils.color(name));
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS);
        item.setItemMeta(meta);
        return item;
    }

    /**
     * Create item stack.
     *
     * @param type the type
     * @param name the name
     * @param lore the lore (varargs)
     * @return the item stack
     */
    public static ItemStack create(Material type, String name, String... lore) {
        List<String> loreList = new ArrayList<>();
        for (String line : lore) {
            loreList.add(Utils.color(line));
        }
        return create(type, 1, name, loreList);
    }

    /**
     * Create item stack.
     *
     * @param type   the type
     * @param amount the amount
     * @param name   the name
     * @param lore   the lore (varargs)
     * @return the item stack
     */
    public static ItemStack create(Material type, int amount, String name, String... lore) {
        List<String> loreList = new ArrayList<>();
        for (String line : lore) {
            loreList.add(Utils.color(line));
        }
        return create(type, amount, Utils.color(name), loreList);
    }

    public static ItemStack createSkull(String playerName, String displayName, String... lore) {
        // Create the skull item
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        if (meta != null) {
            // Set the owning player (skin)
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerName);
            meta.setOwningPlayer(offlinePlayer);
            // Set display name and lore
            meta.setDisplayName(Utils.color(displayName));
            if (lore != null && lore.length > 0) {
                meta.setLore(Arrays.stream(lore)
                        .map(Utils::color)
                        .toList());
            }
            skull.setItemMeta(meta);
        }
        return skull;
    }
}
