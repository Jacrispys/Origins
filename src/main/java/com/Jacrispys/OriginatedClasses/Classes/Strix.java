package com.Jacrispys.OriginatedClasses.Classes;

import com.Jacrispys.OriginatedClasses.Files.ClassData;
import com.Jacrispys.OriginatedClasses.OriginatedClassesMain;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class Strix implements CommandExecutor, Listener {

    private final Plugin plugin;

    public Strix(OriginatedClassesMain main) {
        plugin = main;

        main.getCommand("launch").setExecutor(this);
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    private final Map<UUID, Long> flyTime = new HashMap<>();
    private final Map<UUID, Long> startTime = new HashMap<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        boolean permission = false;
        if (!(sender instanceof Player)) {
            return false;
        }
        Player p = (Player) sender;

        ConfigurationSection playerConfig = ClassData.getClassStorage().getConfigurationSection("Players." + p.getUniqueId());

        if (p.hasPermission("origins.launch")) {
            permission = true;
        }
        if (playerConfig.get(".Class").toString().equalsIgnoreCase("strix")) {
            permission = true;
        }
        if (!(permission)) {
            return false;
        }

        if (cmd.getName().equalsIgnoreCase("launch")) {
            Random rand = new Random();
            long seed = rand.nextLong();
            rand.setSeed(seed);
            int max = 100;
            int min = -100;
            int range = max - min;
            Vector vector = new Vector(0, 0, 0);
            vector.setX(rand.nextInt(range) + min);
            vector.setY(rand.nextInt(range) + min);
            vector.setZ(rand.nextInt(range) + min);
            if (args.length == 0) {
                p.setVelocity(vector);
                Location loc = p.getLocation();
                p.playSound(loc, Sound.ENTITY_GENERIC_EXPLODE, 100, 0.9F);
                p.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, loc, 5);
            }
        }

        return false;
    }

    @EventHandler
    public void onToggleFly(PlayerToggleFlightEvent e) {
        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();
        ConfigurationSection playerConfig = ClassData.getClassStorage().getConfigurationSection("Players." + p.getUniqueId());
        if (playerConfig.get(".Class").toString().equalsIgnoreCase("strix")) {
            if (flyTime.get(uuid) == null && playerConfig.get(".fly") == null) {
                flyTime.put(uuid, 600000L);
            } else {
                if (e.isFlying()) {

                } else {

                }
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        Player p = (Player) e.getEntity();
        ConfigurationSection playerConfig = ClassData.getClassStorage().getConfigurationSection("Players." + p.getUniqueId());
        if (playerConfig.get(".Class").toString().equalsIgnoreCase("strix")) {
            if (e.getCause() == EntityDamageEvent.DamageCause.SUFFOCATION) {
                e.setCancelled(true);

            }
        }

    }
}
