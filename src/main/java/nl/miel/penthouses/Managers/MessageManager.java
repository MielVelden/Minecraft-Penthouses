package nl.miel.penthouses.Managers;

import nl.miel.penthouses.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.IOException;

public class MessageManager {
    static Main plugin;
    private static FileConfiguration config;
    private static File file;
    private static FileConfiguration customFile;
    public static void setupConfig(Main main) {
        MessageManager.config = main.getConfig();
        main.saveDefaultConfig();
    }
    public static void setup() {
        MessageManager.file = new File(Bukkit.getServer().getPluginManager().getPlugin("Penthouses").getDataFolder(), "messages.yml");
        if (!MessageManager.file.exists()) {
            MessageManager.plugin.saveResource("messages.yml", false);
        }
        MessageManager.customFile = (FileConfiguration) YamlConfiguration.loadConfiguration(MessageManager.file);
    }

    public static FileConfiguration get() {
        return MessageManager.customFile;
    }

    public static void save() {
        try {
            MessageManager.customFile.save(MessageManager.file);
        }
        catch (IOException e) {
            System.out.println("Couldn't save file");
        }
    }



    public static String getMessage(String value) {
        return MessageManager.get().getString(value);
    }

    public static String getResponse(String value) {
        String prefix = MessageManager.get().getString("MessagePrefix");
        String message = MessageManager.getMessage(value);
        String response = prefix + message;
        return response;
    }

    static {
        MessageManager.plugin = (Main)Main.getPlugin((Class)Main.class);
    }
}
