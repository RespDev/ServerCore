package codes.settlement.core;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Core extends JavaPlugin {
    public static Core instance;

    @Getter @Setter public String tabHeader = "";
    @Getter @Setter public String tabFooter = "";

    @Override
    public void onEnable() {
        instance = this;

        // Load config utility
        // soon

        // Load tab from config
        tabHeader = ChatColor.translateAlternateColorCodes('&', "&b&lTEST NETWORK");
        tabFooter = ChatColor.translateAlternateColorCodes('&', "&aplay.test.com");

        // Register listeners
        registerListeners();
    }

    @Override
    public void onDisable() {

    }

    private void registerListeners() {

    }

    public static Core getInstance(){
        return instance;
    }
}
