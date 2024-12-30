package codes.settlement.core;

import codes.settlement.core.listeners.Chat;
import codes.settlement.core.listeners.PlayerLoad;
import codes.settlement.core.util.LoggingUtil;
import codes.settlement.core.util.SqlUtil;
import codes.settlement.core.util.Utils;
import codes.settlement.core.util.config.Config;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public class Core extends JavaPlugin {
    public static Core instance;

    public Config config;

    public String tabHeader = "";
    public String tabFooter = "";
    private String version = "0.1";

    @Override
    public void onEnable() {
        instance = this;
        LoggingUtil.logMessage("Core", "&fAttempting to load Core version " + version + "!");

        config = new Config("config.yml");

        tabHeader = Utils.color(config.getString("tab-header"));
        tabFooter = Utils.color(config.getString("tab-footer"));

        // Connect to MySQL database
        try {
            new SqlUtil().connect();
            LoggingUtil.logMessage("Database", "&aSUCCESS &fnow connected to the database!");
        } catch (Exception e) {
            e.printStackTrace();
            LoggingUtil.logMessage("Database", "&cFAILED &fto connect to the database!");
        }

        // Register listeners
        registerListeners();

        LoggingUtil.logMessage("Core", "&aSUCCESS &fCore is now running!");
    }

    @Override
    public void onDisable() {
        new SqlUtil().disconnect();

        LoggingUtil.logMessage("Core", "&aSUCCESS &fCore is now disabled!");
    }

    private void registerListeners() {
        LoggingUtil.logMessage("Core", "&fAttempting to register listeners!");
        Bukkit.getPluginManager().registerEvents(new PlayerLoad(), getInstance());
        Bukkit.getPluginManager().registerEvents(new Chat(), getInstance());
        LoggingUtil.logMessage("Core", "&fAll listeners have been registered!");
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