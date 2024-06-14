package nl.miel.penthouses.Managers;

import org.bukkit.*;
import java.io.*;
import java.util.List;

import org.bukkit.configuration.file.*;

public class DataManager {
    private static File file;
    private static FileConfiguration customFile;

    public static void setup() {
        DataManager.file = new File(Bukkit.getServer().getPluginManager().getPlugin("Penthouses").getDataFolder(), "data.yml");
        if (!DataManager.file.exists()) {
            try {
                DataManager.file.createNewFile();
            }
            catch (IOException ex) {}
        }
        DataManager.customFile = (FileConfiguration)YamlConfiguration.loadConfiguration(DataManager.file);
    }

    public static FileConfiguration get() {
        return DataManager.customFile;
    }

    public static void save() {
        try {
            DataManager.customFile.save(DataManager.file);
        }
        catch (IOException e) {
            System.out.println("Couldn't save file");
        }
    }



    //check if building exists
    public static boolean buildingExists(String name) {
        return DataManager.customFile.contains("Data." + name);
    }

    //check if building exists
    public static boolean penthouseExists(String building, String penthouse) {
        boolean exists = DataManager.get().contains("Data." + building + ".Rooms." + penthouse);
        return exists;
    }

    public static String getBuildingByPenthouse(String penthouse) {
        String building = null;
        Boolean done = false;
        for (String str : DataManager.get().getConfigurationSection("Data.").getKeys(true)) {
            if (str.contains(penthouse) && done == false) {
                String[] split = str.split("\\.");
                building = split[0];
                done = true;
            }
        }

        return building;

    }

    public static String getBuildingByBlock(int x, int y, int z) {
        String building = null;
        Boolean done = false;
        for (String str : DataManager.get().getConfigurationSection("Data.").getKeys(true)) {
            if (str.contains("PC-Location") && done == false) {
                String[] split = str.split("\\.");
                int x2 = DataManager.get().getInt("Data." + split[0] + ".PC-Location.X");
                int y2 = DataManager.get().getInt("Data." + split[0] + ".PC-Location.Y");
                int z2 = DataManager.get().getInt("Data." + split[0] + ".PC-Location.Z");
                if(x2 == x && y2 == y && z2 == z) {
                    building = split[0];
                    done = true;
                }

            }
        }

        return building;

    }


    public static Location getPenthouseSpawn(World world, String building, String penthouse) {
        return new Location(
                world,
                DataManager.get().getDouble("Data." + building + ".Rooms." + penthouse + ".Spawn-Location.X"),
                DataManager.get().getDouble("Data." + building + ".Rooms." + penthouse + ".Spawn-Location.Y"),
                DataManager.get().getDouble("Data." + building + ".Rooms." + penthouse + ".Spawn-Location.Z"),
                (float) DataManager.get().getDouble("Data." + building + ".Rooms." + penthouse + ".Spawn-Location.Yaw"),
                (float) DataManager.get().getDouble("Data." + building + ".Rooms." + penthouse + ".Spawn-Location.Pitch")
        );
    }
    //get owner from penthouse
    public static String getPenthouseOwner(String building, String penthouse) {
        return DataManager.get().getString("Data." + building + ".Rooms." + penthouse + ".Owner");
    }




    //get members from penthouse
    public static List getMembers(String building, String penthouse) {
        return DataManager.get().getStringList("Data." + building + ".Rooms." + penthouse + ".Members");
    }

    // add member to penthouse
    public static void addMember(String building, String penthouse, String member) {
        List members = DataManager.get().getStringList("Data." + building + ".Rooms." + penthouse + ".Members");
        members.add(member);
        DataManager.get().set("Data." + building + ".Rooms." + penthouse + ".Members", members);
        DataManager.save();
    }

    public static void removeMember(String building, String penthouse, String member) {
        List members = DataManager.get().getStringList("Data." + building + ".Rooms." + penthouse + ".Members");
        members.remove(member);
        DataManager.get().set("Data." + building + ".Rooms." + penthouse + ".Members", members);
        DataManager.save();
    }

    public static void clearPenthouse(String building, String penthouse) {
        DataManager.get().set("Data." + building + ".Rooms." + penthouse + ".Members", "");
        DataManager.get().set("Data." + building + ".Rooms." + penthouse + ".Owner", "");
        DataManager.save();
    }
    public static void transferPlot(String building, String penthouse, String member) {
        clearPenthouse(building, penthouse);
        DataManager.get().set("Data." + building + ".Rooms." + penthouse + ".Owner", member);
        DataManager.save();
    }

    //get price from penthouse
    public static int getPrice(String building, String penthouse) {
        return DataManager.get().getInt("Data." + building + ".Rooms." + penthouse + ".Price");
    }

    //get building spawn location
    public static Location getBuildingSpawn(World world, String building) {
        return new Location(
                world,
                DataManager.get().getDouble("Data." + building + ".Exit-Location.X"),
                DataManager.get().getDouble("Data." + building + ".Exit-Location.Y"),
                DataManager.get().getDouble("Data." + building + ".Exit-Location.Z"),
                (float) DataManager.get().getDouble("Data." + building + ".Exit-Location.Yaw"),
                (float) DataManager.get().getDouble("Data." + building + ".Exit-Location.Pitch")
        );
    }

    public static void setBuildingSpawn(String building, Location spawn) {
        DataManager.get().set("Data." + building + ".Exit-Location.X", spawn.getX());
        DataManager.get().set("Data." + building + ".Exit-Location.Y", spawn.getY());
        DataManager.get().set("Data." + building + ".Exit-Location.Z", spawn.getZ());
        DataManager.get().set("Data." + building + ".Exit-Location.Yaw", spawn.getYaw());
        DataManager.get().set("Data." + building + ".Exit-Location.Pitch", spawn.getPitch());
        DataManager.save();
        return;
    }

    public static void createBuilding(String building, Location spawn) {
        DataManager.get().set("Data." + building + ".Exit-Location.X", spawn.getX());
        DataManager.get().set("Data." + building + ".Exit-Location.Y", spawn.getY());
        DataManager.get().set("Data." + building + ".Exit-Location.Z", spawn.getZ());
        DataManager.get().set("Data." + building + ".Exit-Location.Yaw", spawn.getYaw());
        DataManager.get().set("Data." + building + ".Exit-Location.Pitch", spawn.getPitch());
        DataManager.save();
        return;
    }

    //create new penthouse
    public static void createPenthouse(String building, String penthouse, double price, Location spawn) {
        DataManager.get().set("Data." + building + ".Rooms." + penthouse + ".Spawn-Location.X", spawn.getX());
        DataManager.get().set("Data." + building + ".Rooms." + penthouse + ".Spawn-Location.Y", spawn.getY());
        DataManager.get().set("Data." + building + ".Rooms." + penthouse + ".Spawn-Location.Z", spawn.getZ());
        DataManager.get().set("Data." + building + ".Rooms." + penthouse + ".Spawn-Location.Yaw", spawn.getYaw());
        DataManager.get().set("Data." + building + ".Rooms." + penthouse + ".Spawn-Location.Pitch", spawn.getPitch());
        DataManager.get().set("Data." + building + ".Rooms." + penthouse + ".Price", price);
        DataManager.get().set("Data." + building + ".Rooms." + penthouse + ".Owner", "");
        DataManager.get().set("Data." + building + ".Rooms." + penthouse + ".Members", "");
        DataManager.save();
        return;
    }

}
