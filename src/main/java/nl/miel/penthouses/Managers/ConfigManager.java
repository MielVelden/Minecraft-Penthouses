package nl.miel.penthouses.Managers;

import nl.miel.penthouses.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager {
    private static FileConfiguration config;
    public static void setupConfig(Main main) {
        ConfigManager.config = main.getConfig();
        main.saveDefaultConfig();
    }
    private static File file;
    private static FileConfiguration customFile;
    public static void setup() {
        ConfigManager.file = new File(Bukkit.getServer().getPluginManager().getPlugin("Penthouses").getDataFolder(), "config.yml");
        if (!ConfigManager.file.exists()) {
            try {
                ConfigManager.file.createNewFile();
            }
            catch (IOException ex) {}
        }
        ConfigManager.customFile = (FileConfiguration) YamlConfiguration.loadConfiguration(ConfigManager.file);
    }
    private static Main main;
    public ConfigManager(Main main) {
        this.main = main;
    }
    public static String getMessage(String value) {
        return config.getString("Messages." + value);
    }
    public static double getPercentage() {
        return config.getDouble("MoneyBackPercentage");
    }

    public static FileConfiguration get() {
        return ConfigManager.customFile;
    }
}