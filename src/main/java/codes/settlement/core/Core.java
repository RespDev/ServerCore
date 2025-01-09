package codes.settlement.core;

import codes.settlement.core.listener.Chat;
import codes.settlement.core.listener.Leave;
import codes.settlement.core.listener.PlayerLoad;
import codes.settlement.core.model.PlayerScoreboard;
import codes.settlement.core.util.LoggingUtil;
import codes.settlement.core.util.config.Config;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public final class Core extends JavaPlugin {

    private static Core instance;
    private String version = "1.0.0";
    private Config config;
    private BukkitTask scoreboardTask;

    @Override
    public void onEnable() {
        LoggingUtil.logMessage("Core", "Attempting to load Core version " + version + "!");

        // Register getter
        instance = this;

        // Load configuration
        config = new Config("config.yml");

        // Load scoreboard
        if (config.getBoolean("default-scoreboard"))
            scoreboardTask = getServer().getScheduler().runTaskTimer(instance, new PlayerScoreboard(), 0, 1);

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
        LoggingUtil.logMessage("Core", "Starting to register listeners!");
        Bukkit.getPluginManager().registerEvents(new PlayerLoad(), getInstance());
        Bukkit.getPluginManager().registerEvents(new Leave(), getInstance());
        Bukkit.getPluginManager().registerEvents(new Chat(), getInstance());
        LoggingUtil.logMessage("Core", "All listeners have been registered!");
    }

    private void registerCommands() {
        // Normal commands
        LoggingUtil.logMessage("Core", "Starting to register commands!");
        //this.getCommand("example").setExecutor(new ExampleCommand());
        LoggingUtil.logMessage("Core", "All commands have been registered!");
    }

    public Config getConfiguration() {
        return config;
    }

    public static Core getInstance() {
        return instance;
    }
}