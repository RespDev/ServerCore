package codes.settlement.core;

import codes.settlement.core.commands.BuildmodeCommand;
import codes.settlement.core.listeners.*;
import codes.settlement.core.util.LoggingUtil;
import codes.settlement.core.util.config.Config;
import codes.settlement.core.util.manager.PlayerScoreboardManager;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class Core extends JavaPlugin {
    public static Core instance;

    public Config config;
    private String version = "1.0.0";
    private BukkitTask scoreboardTask;
    public String dashboardUrl;
    private Boolean isPark;

    @Override
    public void onEnable() {
        instance = this;
        LoggingUtil.logMessage("Core", "Attempting to load Core version " + version + "!");

        // Load configuration
        config = new Config("config.yml");

        dashboardUrl = config.getString("dashboard-url");
        isPark = config.getBoolean("park");

        if (config.getBoolean("default-scoreboard"))
            scoreboardTask = getServer().getScheduler().runTaskTimer(instance, new PlayerScoreboardManager(), 0, 1);

        // Load luckperms api
        RegisteredServiceProvider<LuckPerms> lpProvider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (lpProvider != null) {
            LuckPerms api = lpProvider.getProvider();
        }

        // Load listeners
        registerListeners();

        // Load commands
        registerCommands();

        LoggingUtil.logMessage("Core", "Core is now running!");
    }

    @Override
    public void onDisable() {
        if (scoreboardTask != null && !scoreboardTask.isCancelled())
            scoreboardTask.cancel();

        LoggingUtil.logMessage("Core", "Core is now disabled!");
    }

    private void registerListeners() {
        // Normal listeners
        LoggingUtil.logMessage("Core", "Starting to register normal listeners!");
        Bukkit.getPluginManager().registerEvents(new PlayerLoad(), getInstance());
        Bukkit.getPluginManager().registerEvents(new Chat(), getInstance());
        Bukkit.getPluginManager().registerEvents(new EntityDamage(), getInstance());

        // Park specific listeners
        if (isPark) {
            LoggingUtil.logMessage("Core", "Park listeners being registered also!");
            Bukkit.getPluginManager().registerEvents(new PlayerBuild(), getInstance());
            Bukkit.getPluginManager().registerEvents(new InventoryListener(), getInstance());
            Bukkit.getPluginManager().registerEvents(new PlayerDropItem(), getInstance());
            Bukkit.getPluginManager().registerEvents(new PlayerInteract(), getInstance());
        }
        LoggingUtil.logMessage("Core", "All listeners have been registered!");
    }

    private void registerCommands() {
        // Normal commands
        LoggingUtil.logMessage("Core", "Starting to register normal commands!");

        // Park specific listeners
        if (isPark) {
            LoggingUtil.logMessage("Core", "Park commands being registered also!");
            this.getCommand("buildmode").setExecutor(new BuildmodeCommand());
        }
        LoggingUtil.logMessage("Core", "All commands have been registered!");
    }

    public Boolean isPark() {
        return isPark;
    }

    public static Core getInstance() {
        return instance;
    }
}