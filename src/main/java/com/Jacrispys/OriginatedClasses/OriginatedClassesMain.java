package com.Jacrispys.OriginatedClasses;

import com.Jacrispys.OriginatedClasses.Classes.Enderian;
import com.Jacrispys.OriginatedClasses.Classes.Merling;
import com.Jacrispys.OriginatedClasses.Files.ClassData;
import com.Jacrispys.OriginatedClasses.FirstJoin.ClassSelection;
import com.Jacrispys.OriginatedClasses.Utils.TabCreation;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class OriginatedClassesMain extends JavaPlugin {

    private static Plugin plugin;

    public static Plugin getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;
        new ClassSelection(this);
        new Enderian(this);
        new Merling(this);
        new TabCreation(this);
        this.saveDefaultConfig();
        ClassData.setup();
        ClassData.getClassStorage().addDefault("#PLAYER_DATA DO NOT TOUCH#", " ");
        ClassData.getClassStorage().options().copyDefaults(true);
        ClassData.saveClassStorage();
    }

}
