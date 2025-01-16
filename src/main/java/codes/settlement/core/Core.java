package codes.settlement.core;

import codes.settlement.core.command.disabled.TabCompletion;
import codes.settlement.core.command.general.*;
import codes.settlement.core.command.park.BuildmodeCommand;
import codes.settlement.core.listener.general.Chat;
import codes.settlement.core.listener.general.Leave;
import codes.settlement.core.listener.general.PlayerLoad;
import codes.settlement.core.listener.park.*;
import codes.settlement.core.manager.TpaRequestManager;
import codes.settlement.core.model.CustomCommand;
import codes.settlement.core.model.PlayerScoreboard;
import codes.settlement.core.util.LoggingUtil;
import codes.settlement.core.util.Utils;
import codes.settlement.core.util.config.Config;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

public final class Core extends JavaPlugin {

    private static Core instance;
    private String version = "1.0.0";
    private Config config;
    private String dashboardUrl;
    private Boolean isPark;
    private BukkitTask scoreboardTask;

    private static final String NO_PERMISSION_MESSAGE = "&cSorry, but you don't have permission to perform this command.";
    private final Map<String, CustomCommand> commands = new HashMap<>();

    @Override
    public void onEnable() {
        LoggingUtil.logMessage("Core", "Attempting to load Core version " + version + "!");

        // Register getter
        instance = this;

        // Load configuration
        config = new Config("config.yml");
        dashboardUrl = config.getString("dashboard-url");
        isPark = config.getBoolean("park");

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

        commands.clear();
        TpaRequestManager.clearAllRequests();

        LoggingUtil.logMessage("Core", "Core is now disabled!");
    }

    private void registerListeners() {
        // Normal listeners
        LoggingUtil.logMessage("Core", "Starting to register normal listeners!");
        Bukkit.getPluginManager().registerEvents(new PlayerLoad(), getInstance());
        Bukkit.getPluginManager().registerEvents(new Leave(), getInstance());
        Bukkit.getPluginManager().registerEvents(new Chat(), getInstance());
        Bukkit.getPluginManager().registerEvents(new EntityDamage(), getInstance());
        Bukkit.getPluginManager().registerEvents(new TabCompletion(), getInstance());

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
        LoggingUtil.logMessage("Core", "Starting to register general commands!");

        // TODO: Convert to custom command system!
        this.getCommand("fly").setExecutor(new FlyCommand());
        this.getCommand("vanish").setExecutor(new VanishCommand());

        registerCommand("tpa", new TpaCommand());
        registerCommand("tpaccept", new TpAcceptCommand());
        registerCommand("tpdeny", new TpDenyCommand());
        registerCommand("tphere", new TpHereCommand());

        // Park specific listeners
        if (isPark) {
            LoggingUtil.logMessage("Core", "Park commands being registered!");
            this.getCommand("buildmode").setExecutor(new BuildmodeCommand());
        }

        LoggingUtil.logMessage("Core", "All commands have been registered!");
    }

    private void registerCommand(String name, CustomCommand command) {
        commands.put(name, command);
        this.getCommand(name).setExecutor((sender, cmd, label, args) -> {
            if (command.getPermission() != null && !sender.hasPermission(command.getPermission())) {
                sender.sendMessage(Utils.color(NO_PERMISSION_MESSAGE));
                return true;
            }
            return command.onCommand(sender, cmd, label, args);
        });
    }

    public Config getConfiguration() {
        return config;
    }

    public String getDashboardUrl() {
        return dashboardUrl;
    }

    public Boolean isPark() {
        return isPark;
    }

    public static Core getInstance() {
        return instance;
    }
}