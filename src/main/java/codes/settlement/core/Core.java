package codes.settlement.core;

import codes.settlement.core.listeners.PlayerLoad;
import codes.settlement.core.util.Utils;
import codes.settlement.core.util.config.Config;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Core extends JavaPlugin {
    public static Core instance;

    public Config config;

    public String tabHeader = "";
    public String tabFooter = "";

    @Override
    public void onEnable() {
        instance = this;

        config = new Config("config.yml");

        tabHeader = Utils.color(config.getString("tab-header"));
        tabFooter = Utils.color(config.getString("tab-footer"));

        // Register listeners
        registerListeners();
    }

    @Override
    public void onDisable() {

    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new PlayerLoad(), getInstance());
    }

    public void setTabHeader(String tabHeader) {
        this.tabHeader = tabHeader;
    }

    public void setTabFooter(String tabFooter) {
        this.tabFooter = tabFooter;
    }

    public static Core getInstance() {
        return instance;
    }
}

/**
 * @Getter @Setter public String tabHeader = "";
 * @Getter @Setter public String tabFooter = "";
 * <p>
 * / Load config utility
 * // soon
 * <p>
 * // Load tab from config
 * tabHeader = Utils.color("&k&lsdfsdfsdfe");
 * tabFooter = ChatColor.translateAlternateColorCodes('&', "&aplay.test.com");
 */
