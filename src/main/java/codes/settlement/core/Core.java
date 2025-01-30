package codes.settlement.core;

import codes.settlement.core.listener.Chat;
import codes.settlement.core.listener.Join;
import codes.settlement.core.listener.Leave;
import codes.settlement.core.util.LoggingUtil;
import codes.settlement.core.util.SqlUtil;
import codes.settlement.core.util.config.Config;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Core extends JavaPlugin {

    private static Core instance;
    private String version = "1.0.0";
    private Config config;
    private SqlUtil sqlUtil;

    @Override
    public void onEnable() {
        instance = this;

        LoggingUtil.logMessage("Core", "Attempting to load Core version " + version + "!");

        // Load configuration
        config = new Config("config.yml");

        // Utils
        sqlUtil = new SqlUtil();

        // Create Tables
        sqlUtil.createBackpackTable();

        // Load Commands & Listeners
        registerListeners();
        registerCommands();

        LoggingUtil.logMessage("Core", "Core is now running!");
    }

    @Override
    public void onDisable() {
        LoggingUtil.logMessage("Core", "Core is now disabled!");
    }

    private void registerListeners() {
        LoggingUtil.logMessage("Core", "Starting to register listeners!");

        Bukkit.getPluginManager().registerEvents(new Join(), getInstance());
        Bukkit.getPluginManager().registerEvents(new Leave(), getInstance());
        Bukkit.getPluginManager().registerEvents(new Chat(), getInstance());

        LoggingUtil.logMessage("Core", "All listeners have been registered!");
    }

    private void registerCommands() {
        LoggingUtil.logMessage("Core", "Starting to register commands!");

        // TODO: Code commands

        LoggingUtil.logMessage("Core", "All commands have been registered!");
    }

    public static void registerListener(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, getInstance());
    }

    public static int runTask(Plugin plugin, Runnable task) {
        return Bukkit.getScheduler().runTask(plugin, task).getTaskId();
    }

    public Config getConfiguration() {
        return config;
    }

    public static Core getInstance() {
        return instance;
    }
}