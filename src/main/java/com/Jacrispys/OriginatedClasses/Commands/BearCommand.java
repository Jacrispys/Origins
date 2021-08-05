package com.Jacrispys.OriginatedClasses.Commands;

import com.Jacrispys.OriginatedClasses.Entities.MinaciousBear;
import com.Jacrispys.OriginatedClasses.OriginatedClassesMain;
import net.minecraft.server.v1_16_R3.EntityTypes;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class BearCommand implements CommandExecutor {

    private Plugin plugin = OriginatedClassesMain.getPlugin();

    public BearCommand(OriginatedClassesMain plugin) {
        this.plugin = plugin;

        plugin.getCommand("bear").setExecutor(this);


    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        Player p = (Player) commandSender;
        if(command.getName().equalsIgnoreCase("bear")) {
            p.sendMessage("spawn");
            MinaciousBear bear = new MinaciousBear(EntityTypes.POLAR_BEAR, p.getLocation());
            p.sendMessage("spawn");
            bear.spawn();
            return true;

        }
        return false;

    }
}
