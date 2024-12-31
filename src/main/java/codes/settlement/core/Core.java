package codes.settlement.core;

import codes.settlement.core.listeners.Chat;
import codes.settlement.core.listeners.PlayerLoad;
import codes.settlement.core.util.LoggingUtil;
import codes.settlement.core.util.Utils;
import codes.settlement.core.util.config.Config;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Core extends JavaPlugin {
    public static Core instance;

    public Config config;

    public String tabHeader = "";
    public String tabFooter = "";
    private String version = "0.0.1";

    @Override
    public void onEnable() {
        instance = this;
        LoggingUtil.logMessage("Core", "Attempting to load Core version " + version + "!");

        // Load configuration
        config = new Config("config.yml");

        tabHeader = Utils.color(config.getString("tab-header"));
        tabFooter = Utils.color(config.getString("tab-footer"));

        // Load luckperms api
        RegisteredServiceProvider<LuckPerms> lpProvider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (lpProvider != null) {
            LuckPerms api = lpProvider.getProvider();
        }

        // Load listeners
        registerListeners();

        LoggingUtil.logMessage("Core", "Core is now running!");
    }

    @Override
    public void onDisable() {
        LoggingUtil.logMessage("Core", "Core is now disabled!");
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new PlayerLoad(), getInstance());
        Bukkit.getPluginManager().registerEvents(new Chat(), getInstance());
        LoggingUtil.logMessage("Core", "All listeners have been registered!");
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