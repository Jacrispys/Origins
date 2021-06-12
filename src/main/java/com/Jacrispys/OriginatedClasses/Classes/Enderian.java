package com.Jacrispys.OriginatedClasses.Classes;

import com.Jacrispys.OriginatedClasses.Files.ClassData;
import com.Jacrispys.OriginatedClasses.OriginatedClassesMain;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.UUID;

import static com.Jacrispys.OriginatedClasses.Utils.Chat.chat;

public class Enderian implements Listener {

    private final OriginatedClassesMain plugin;

    public Enderian(OriginatedClassesMain plugin) {
        this.plugin = plugin;


        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    private HashMap<UUID, Long> tpTimerStart = new HashMap<UUID, Long>();

    @EventHandler
    public void teleport(PlayerToggleSneakEvent e) {
        Player p = e.getPlayer();
        if(ClassData.getClassStorage().contains(e.getPlayer().getUniqueId() + " Class")) {
            if(ClassData.getClassStorage().get(e.getPlayer().getUniqueId() + " Class").equals("enderhusk")) {
                tpTimerStart.put(p.getUniqueId(), System.currentTimeMillis()/1000);
                if(e.isSneaking()) {
                    BukkitRunnable tpTime = new BukkitRunnable() {
                        @Override
                        public void run() {
                            if(p.isSneaking()) {
                                if((System.currentTimeMillis()/1000 - tpTimerStart.get(p.getUniqueId())) >= 3.0) {
                                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(chat("&e&lTeleport: &aSuccessful!")));
                                    int teleportRange = 8;
                                    for(int i = 1; i <= teleportRange; i++) {
                                        Vector facing = p.getLocation().getDirection().normalize();
                                        facing.multiply(i);
                                        Location reach = p.getEyeLocation().add(facing);
                                        if(!(reach.getBlock().getType() == Material.AIR) || !(reach.add(0, 1, 0).getBlock().getType() == Material.AIR)) {
                                            p.teleport(p.getEyeLocation().add(p.getLocation().getDirection().normalize().multiply(i - 2)));
                                            i = teleportRange;
                                            this.cancel();
                                            return;
                                        }
                                        if(i == teleportRange) {
                                           p.teleport(reach);
                                           this.cancel();
                                           return;
                                        }
                                    }
                                    this.cancel();
                                } else {
                                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(chat("&aTeleporting in: " + (3 - (System.currentTimeMillis()/1000 - tpTimerStart.get(p.getUniqueId()))))));
                                }
                        } else {
                                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(chat("&cTeleport Failed! Reason: Stopped Shifting")));
                               this.cancel();
                            }
                    }
                };
                tpTime.runTaskTimer(plugin, 0L, 1L);

                } else return;
            } else e.getPlayer().sendMessage(chat("&cNot Enderian"));
        } else e.getPlayer().sendMessage(chat("&cNo class"));
    }
}
