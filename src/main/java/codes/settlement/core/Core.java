package codes.settlement.core;

import codes.settlement.core.command.*;
import codes.settlement.core.command.disabled.OPCommand;
import codes.settlement.core.listener.*;
import codes.settlement.core.manager.HotbarManager;
import codes.settlement.core.manager.ScoreboardManager;
import codes.settlement.core.util.LoggingUtil;
import codes.settlement.core.util.SqlUtil;
import codes.settlement.core.util.TpaUtil;
import codes.settlement.core.util.config.Config;
import codes.settlement.core.util.config.SpawnConfig;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public final class Core extends JavaPlugin {

    private static Core instance;
    private String version = "1.0.0";
    private BukkitTask scoreboardTask;
    private Config config;
    private SpawnConfig spawnConfig;
    private SqlUtil sqlUtil;
    private HotbarManager hotbarManager;

    @Override
    public void onEnable() {
        instance = this;

        LoggingUtil.logMessage("Core", "Attempting to load Core version " + version + "!");

        // Load configuration
        config = new Config("config.yml");
        spawnConfig = new SpawnConfig();

        saveResource("hotbar-items.yml", false);
        saveResource("hotbar-layout.yml", false);

        if (config.getBoolean("default-scoreboard"))
            scoreboardTask = getServer().getScheduler().runTaskTimer(instance, new ScoreboardManager(), 0, 1);

        // Utils
        if (config.getBoolean("production")) {
            sqlUtil = new SqlUtil();

            // Create Tables
            sqlUtil.createBackpackTable();
        }
        hotbarManager = new HotbarManager(this);

        // Load Commands & Listeners
        registerListeners();
        registerCommands();

        LoggingUtil.logMessage("Core", "Core is now running!");
    }

    @Override
    public void onDisable() {
        if (scoreboardTask != null && !scoreboardTask.isCancelled())
            scoreboardTask.cancel();

        TpaUtil.clearAllRequests();

        LoggingUtil.logMessage("Core", "Core is now disabled!");
    }

    private void registerListeners() {
        LoggingUtil.logMessage("Core", "Starting to register listeners!");

        registerListener(new Join());
        registerListener(new Leave());
        registerListener(new Chat());
        registerListener(new MenuListener());
        registerListener(new HotbarListener(hotbarManager));
        registerListener(new PlayerInteract(hotbarManager));

        LoggingUtil.logMessage("Core", "All listeners have been registered!");
    }

    private void registerCommands() {
        LoggingUtil.logMessage("Core", "Starting to register commands!");

        new FlyCommand();
        new VanishCommand();
        new InvseeCommand();
        new GamemodeCommand();
        new GMCCommand();
        new GMSCommand();
        new GMACommand();
        new GMSPCommand();
        new HealCommand();
        new HatCommand();
        new HelpOpCommand();
        new OPCommand();
        new TpCommand();
        new TpHereCommand();
        new TpAllCommand();
        new TpaCommand();
        new TpaHereCommand();
        new TpaCancelCommand();
        new TpaAcceptCommand();
        new TpaDenyCommand();
        new RulesCommand();
        new DiscordCommand();
        new SpawnCommand();

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

    public SpawnConfig getSpawnConfig() {
        return spawnConfig;
    }

    public static Core getInstance() {
        return instance;
    }

    public static int runTaskTimer(Plugin plugin, Runnable task, long delay, long period) {
        return Bukkit.getScheduler().runTaskTimer(plugin, task, delay, period).getTaskId();
    }
}