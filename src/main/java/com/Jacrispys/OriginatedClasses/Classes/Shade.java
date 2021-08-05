package com.Jacrispys.OriginatedClasses.Classes;

import com.Jacrispys.OriginatedClasses.API.NPC_API;
import com.Jacrispys.OriginatedClasses.API.TabAPI;
import com.Jacrispys.OriginatedClasses.Entities.MinaciousBear;
import com.Jacrispys.OriginatedClasses.Entities.NPC;
import com.Jacrispys.OriginatedClasses.Entities.ZombieNPC;
import com.Jacrispys.OriginatedClasses.Files.ClassData;
import com.Jacrispys.OriginatedClasses.OriginatedClassesMain;
import com.mojang.authlib.GameProfile;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class Shade extends TabAPI implements Listener, CommandExecutor {

    private final OriginatedClassesMain plugin;

    public Shade(OriginatedClassesMain plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
        plugin.getCommand("roam").setExecutor(this);
    }

    private final HashMap<UUID, Long> invisibleCooldown = new HashMap<>();
    private final HashMap<UUID, Boolean> isInRoam = new HashMap<>();
    private final HashMap<UUID, EntityPlayer> roamingNPC = new HashMap<>();

    @EventHandler
    public void onShift(PlayerToggleSneakEvent e) {
        Player p = e.getPlayer();
        if (Objects.requireNonNull(ClassData.getClassStorage().get(p.getUniqueId() + ".Class")).equals("shade")) {
            if(e.isSneaking()) {
                invisibleCooldown.put(p.getUniqueId(), System.currentTimeMillis());
                for(Player hidden : Bukkit.getOnlinePlayers()) {
                    if (Objects.requireNonNull(ClassData.getClassStorage().get(p.getUniqueId() + ".Class")).equals("shade") && ClassData.getClassStorage().contains(p.getUniqueId().toString()) && hidden != p) {

                        hidden.showPlayer(plugin, p);
                    }  else {
                        hidden.hidePlayer(plugin, p);
                        addPlayers(hidden, ((CraftPlayer)p).getHandle());

                    }
                }
            } else if(!(e.isSneaking())) {
                for(Player hidden : Bukkit.getOnlinePlayers()) {
                    if (Objects.requireNonNull(ClassData.getClassStorage().get(p.getUniqueId() + ".Class")).equals("shade") && ClassData.getClassStorage().contains(p.getUniqueId().toString()) && hidden != p) {

                        hidden.showPlayer(plugin, p);
                    }  else {
                        hidden.showPlayer(plugin, p);

                    }
                }
            }
        }
    }
    @EventHandler
    public void noFallDamage(EntityDamageEvent e) {
        if(e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (Objects.requireNonNull(ClassData.getClassStorage().get(p.getUniqueId() + ".Class")).equals("atlantian") && ClassData.getClassStorage().contains(p.getUniqueId().toString())) {

                if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
                    e.setCancelled(true);
                } else return;
            }
        } else return;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (Objects.requireNonNull(ClassData.getClassStorage().get(e.getPlayer().getUniqueId() + ".Class")).equals("shade") && ClassData.getClassStorage().get(e.getPlayer().getUniqueId().toString()) != null) {

            isInRoam.put(e.getPlayer().getUniqueId(), false);
        } else return;
    }

    private final HashMap<UUID, GameMode> previousGameMode = new HashMap<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            CraftPlayer craftPlayer = ((CraftPlayer) p);
            if (Objects.requireNonNull(ClassData.getClassStorage().get(p.getUniqueId() + ".Class")).equals("shade") && ClassData.getClassStorage().contains(p.getUniqueId().toString())) {

                if(cmd.getName().equalsIgnoreCase("roam")) {
                    CraftWorld craftWorld = (CraftWorld) craftPlayer.getWorld();
                    World world = craftWorld.getHandle();
                    if(!isInRoam.get(p.getUniqueId())) {
                        EntityPlayer roamNPC = NPC_API.createNPC(world.getDimensionKey(), p.getUniqueId(), p, p.getLocation());
                        NPC_API.spawnNPCPacket(roamNPC, p);
                        NPC_API.sendSetNPCSkinPacket(roamNPC, p, p.getName());
                        previousGameMode.put(p.getUniqueId(), p.getGameMode());
                        p.setGameMode(GameMode.SPECTATOR);
                        roamingNPC.put(p.getUniqueId(), roamNPC);
                        isInRoam.put(p.getUniqueId(), true);
                    } else if(isInRoam.get(p.getUniqueId())) {
                        NPC_API.removeNPCPacket(roamingNPC.get(p.getUniqueId()), p);
                        if(previousGameMode.get(p.getUniqueId()) == null) {
                            p.setGameMode(GameMode.SURVIVAL);
                        } else {
                            p.setGameMode(previousGameMode.get(p.getUniqueId()));
                        }
                        isInRoam.put(p.getUniqueId(), false);
                    }
                }
            }

        } else return false;

        return false;
    }
}
