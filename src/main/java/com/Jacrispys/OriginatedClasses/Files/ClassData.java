package com.Jacrispys.OriginatedClasses.Files;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ClassData {

    private static File file;
    private static FileConfiguration ClassStorage;


    //finds or gens file
    public static void setup() {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("OriginatedClasses").getDataFolder(), "ClassStorage.yml");

        if (!file.exists()) {
            try {
                file.createNewFile();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ClassStorage = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration getClassStorage() {
        return ClassStorage;
    }

    public static void saveClassStorage() {
        try {
            ClassStorage.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void reloadClassStorage() {
        ClassStorage = YamlConfiguration.loadConfiguration(file);
    }

}
