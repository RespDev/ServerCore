package codes.settlement.core.util.config;

import codes.settlement.core.Core;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public final class Config extends YamlConfiguration {
    private final Core plugin = Core.getInstance();
    private final File file;

    public Config(File parent, String name) {
        this.file = new File(parent, name);
        if (!this.file.exists()) {
            (this.options()).copyDefaults(true);
            this.plugin.saveResource(name, false);
        }
        this.load();
    }

    public Config(String name) {
        this(Core.getInstance().getDataFolder(), name);
    }

    public void load() {
        try {
            super.load(this.file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            super.save(this.file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}