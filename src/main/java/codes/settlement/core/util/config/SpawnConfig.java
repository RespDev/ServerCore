package codes.settlement.core.util.config;

import codes.settlement.core.Core;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public final class SpawnConfig extends YamlConfiguration {
    private final Core plugin = Core.getInstance();
    private final File file;

    public SpawnConfig() {
        this.file = new File(plugin.getDataFolder(), "data.yml");
        if (!this.file.exists()) {
            try {
                this.file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        load();
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

    public void setSpawnLocation(Location location) {
        this.set("spawn.world", location.getWorld().getName());
        this.set("spawn.x", location.getX());
        this.set("spawn.y", location.getY());
        this.set("spawn.z", location.getZ());
        this.set("spawn.yaw", location.getYaw());
        this.set("spawn.pitch", location.getPitch());
        save();
    }

    public Location getSpawnLocation() {
        if (!this.contains("spawn.world")) return null;

        World world = Bukkit.getWorld(this.getString("spawn.world"));
        if (world == null) return null;

        double x = this.getDouble("spawn.x");
        double y = this.getDouble("spawn.y");
        double z = this.getDouble("spawn.z");
        float yaw = (float) this.getDouble("spawn.yaw");
        float pitch = (float) this.getDouble("spawn.pitch");

        return new Location(world, x, y, z, yaw, pitch);
    }
}
