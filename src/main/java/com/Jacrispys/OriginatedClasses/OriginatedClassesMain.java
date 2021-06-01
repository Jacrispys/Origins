package com.Jacrispys.OriginatedClasses;

import com.Jacrispys.OriginatedClasses.Files.ClassData;
import com.Jacrispys.OriginatedClasses.FirstJoin.ClassSelection;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class OriginatedClassesMain extends JavaPlugin {

    @Override
    public void onEnable() {
        new ClassSelection(this);
        this.saveDefaultConfig();
        ClassData.setup();
        ClassData.getClassStorage().addDefault("#PLAYER_DATA DO NOT TOUCH#", " ");
        ClassData.getClassStorage().options().copyDefaults(true);
        ClassData.saveClassStorage();
    }
}
