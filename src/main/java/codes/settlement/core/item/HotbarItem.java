package codes.settlement.core.item;

import codes.settlement.core.Core;
import codes.settlement.core.util.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;
import java.util.stream.Collectors;

public class HotbarItem {
    private final String id;
    private final Material material;
    private final String name;
    private final List<String> lore;
    private final List<String> actions;

    public HotbarItem(String id, Material material, String name, List<String> lore, List<String> actions) {
        this.id = id;
        this.material = material;
        this.name = name;
        this.lore = lore;
        this.actions = actions;
    }

    public ItemStack toItemStack() {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return item;

        if (name != null) meta.setDisplayName(Utils.color(name));
        if (lore != null && !lore.isEmpty()) {
            meta.setLore(lore.stream()
                    .map(Utils::color)
                    .collect(Collectors.toList()));
        }

        if (material == Material.BOOK) {
            meta.setUnbreakable(true);
            meta.addEnchant(Enchantment.LOYALTY, 1, false);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
        }

        meta.getPersistentDataContainer().set(new org.bukkit.NamespacedKey(Core.getInstance(), "hotbar_id"), PersistentDataType.STRING, id);
        item.setItemMeta(meta);
        return item;
    }

    public void execute(org.bukkit.entity.Player player) {
        ItemActionExecutor.execute(actions, player);
    }
}
