package codes.settlement.core.manager;

import codes.settlement.core.item.HotbarItem;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HotbarManager {
    private final Map<String, HotbarItem> items = new HashMap<>();
    private final Map<Integer, String> layout = new HashMap<>();
    private final JavaPlugin plugin;

    private static HotbarManager instance;

    public HotbarManager(JavaPlugin plugin) {
        this.plugin = plugin;
        instance = this;
        loadItems();
        loadLayout();
    }

    public void loadItems() {
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "hotbar-items.yml"));
        for (String key : config.getKeys(false)) {
            String materialStr = config.getString(key + ".material");
            String name = config.getString(key + ".name");
            List<String> lore = config.getStringList(key + ".lore");

            List<String> actions = parseActions(config.getList(key + ".actions"));

            Material material = Material.valueOf(materialStr);
            items.put(key, new HotbarItem(key, material, name, lore, actions));
        }
    }

    public void loadLayout() {
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "hotbar-layout.yml"));
        if (config.contains("layout")) {
            for (String slotStr : config.getConfigurationSection("layout").getKeys(false)) {
                layout.put(Integer.parseInt(slotStr), config.getString("layout." + slotStr));
            }
        }
    }

    public static void reloadFiles() {
        if (instance != null) {
            instance.loadItems();
            instance.loadLayout();
        }
    }

    private List<String> parseActions(List<?> rawActions) {
        List<String> actions = new ArrayList<>();
        if (rawActions == null) return actions;

        for (Object entry : rawActions) {
            if (entry instanceof String) {
                actions.add((String) entry);
            } else if (entry instanceof Map) {
                Map<String, Object> map = (Map<String, Object>) entry;
                for (Map.Entry<String, Object> e : map.entrySet()) {
                    // Convert map entry to string "key:value"
                    actions.add(e.getKey() + ":" + e.getValue().toString());
                }
            }
        }
        return actions;
    }

    public void applyHotbar(Player player) {
        if (player.getGameMode().name().equals("CREATIVE")) return; // no hotbar in creative

        PlayerInventory inv = player.getInventory();
        for (Map.Entry<Integer, String> entry : layout.entrySet()) {
            HotbarItem item = items.get(entry.getValue());
            if (item != null) {
                inv.setItem(entry.getKey(), item.toItemStack());
            }
        }
    }

    public HotbarItem getItemFromStack(ItemStack stack) {
        if (stack == null || !stack.hasItemMeta()) return null;
        ItemMeta meta = stack.getItemMeta();
        if (meta == null) return null;
        String id = meta.getPersistentDataContainer().get(
                new org.bukkit.NamespacedKey(plugin, "hotbar_id"),
                PersistentDataType.STRING
        );
        return id != null ? items.get(id) : null;
    }

    public Map<Integer, String> getLayout() {
        return layout;
    }
}
