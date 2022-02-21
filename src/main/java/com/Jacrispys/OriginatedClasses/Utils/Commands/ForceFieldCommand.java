package com.Jacrispys.OriginatedClasses.Utils.Commands;

import com.Jacrispys.OriginatedClasses.OriginatedClassesMain;
import com.Jacrispys.OriginatedClasses.Utils.Math.FormVector;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

import static com.Jacrispys.OriginatedClasses.Utils.Chat.ColorChat.color;

public class ForceFieldCommand implements CommandExecutor {
    private final Plugin plugin;

    public ForceFieldCommand(OriginatedClassesMain main) {
        plugin = main;
        main.getCommand("forcefield").setExecutor(this);
    }

    private final Map<UUID, Boolean> isEnabled = new HashMap<>();

    protected void enableForceField(Player target) {
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                if(isEnabled.get(target.getUniqueId())) {
                    Location targetLoc = target.getLocation();
                    if(!target.isOnline()) {
                        targetLoc = null;
                    }
                    for(Entity ent : target.getNearbyEntities(5, 5, 5)) {
                        Location entLoc = ent.getLocation();
                        Vector direction = FormVector.genVec(targetLoc, entLoc, true);
                        ent.setVelocity(direction);
                        if(ent instanceof Player) {
                            ((Player) ent).playSound(entLoc, Sound.ENTITY_CHICKEN_EGG, 1.3F, 100F);
                        }
                    }
                } else {
                    this.cancel();
                }
            }
        };
        runnable.runTaskTimer(plugin, 0, 1);

    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {

        if(!cmd.getName().equalsIgnoreCase("forcefield")) {
            return false;
        }

        if(!(sender instanceof Player)) {
            Bukkit.getLogger().log(Level.SEVERE, "Console cannot execute this command!");
            return false;
        }
        Player p = (Player) sender;
        if(isEnabled.containsKey(p.getUniqueId()) && isEnabled.get(p.getUniqueId())) {
            isEnabled.put(p.getUniqueId(), false);
            p.sendMessage(color("&9ForceField is now: &cDisabled!"));
            return false;

        } else if(!isEnabled.containsKey(p.getUniqueId()) || !isEnabled.get(p.getUniqueId())) {
            isEnabled.put(p.getUniqueId(), true);
            p.sendMessage(color("&9ForceField is now: &aEnabled!"));
            enableForceField(p);
        }

        return false;
    }
}
