package com.Jacrispys.OriginatedClasses.Classes;

import com.Jacrispys.OriginatedClasses.Files.ClassData;
import com.Jacrispys.OriginatedClasses.OriginatedClassesMain;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

import static com.Jacrispys.OriginatedClasses.Utils.Chat.chat;

public class Enderian implements Listener {

    private final OriginatedClassesMain plugin;

    public Enderian(OriginatedClassesMain plugin) {
        this.plugin = plugin;


        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    private final HashMap<UUID, Long> tpTimerStart = new HashMap<>();

    @EventHandler
    public void teleport(PlayerToggleSneakEvent e) {
        Player p = e.getPlayer();
        if (ClassData.getClassStorage().contains(e.getPlayer().getUniqueId() + " Class")) {
            if (ClassData.getClassStorage().get(e.getPlayer().getUniqueId() + " Class").equals("enderhusk")) {
                tpTimerStart.put(p.getUniqueId(), System.currentTimeMillis() / 1000);
                if (e.isSneaking()) {
                    BukkitRunnable tpTime = new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (p.isSneaking()) {
                                if ((System.currentTimeMillis() / 1000 - tpTimerStart.get(p.getUniqueId())) >= 3.0) {
                                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(chat("&e&lTeleport: &aSuccessful!")));
                                    int teleportRange = 8;
                                    for (int i = 1; i <= teleportRange; i++) {
                                        Vector facing = p.getLocation().getDirection().normalize();
                                        facing.multiply(i);
                                        Location reach = p.getEyeLocation().add(facing);
                                        if (!(reach.getBlock().getType() == Material.AIR) || !(reach.add(0, 1, 0).getBlock().getType() == Material.AIR)) {
                                            p.teleport(p.getEyeLocation().add(p.getLocation().getDirection().normalize().multiply(i - 1.25)));
                                            i = teleportRange;
                                            this.cancel();
                                            return;
                                        }
                                        if (i == teleportRange) {
                                            p.teleport(reach);
                                            this.cancel();
                                            return;
                                        }
                                    }
                                    this.cancel();
                                } else {
                                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(chat("&aTeleporting in: " + (3 - (System.currentTimeMillis() / 1000 - tpTimerStart.get(p.getUniqueId()))))));
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

    @EventHandler
    public void EnderHealth(PlayerJoinEvent e) {
        if (Objects.requireNonNull(ClassData.getClassStorage().get(e.getPlayer().getUniqueId() + " Class")).equals("enderhusk")) {
            Player p = e.getPlayer();
            p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(40D);
            p.setHealth(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
            for(int i = 0; i < 36; i++) {
                try {
                    if (p.getInventory().getItem(i).getType().toString().toLowerCase().contains("pumpkin")) {
                        p.getInventory().removeItem(Objects.requireNonNull(p.getInventory().getItem(i)));
                    }
                } catch(NullPointerException error) {}

            }
            if(p.getWorld().hasStorm()) {
                BukkitRunnable rainDamage = new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (p.getWorld().hasStorm()) {

                            int blockLocation = p.getLocation().getWorld().getHighestBlockYAt(p.getLocation());
                            if (blockLocation <= p.getLocation().getY()) {
                                p.damage(0.5);
                            }
                        } else this.cancel();
                    }

                };
                rainDamage.runTaskTimer(plugin, 0L, 20);

            }
        } else {
            Objects.requireNonNull(e.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(20);
        }
    }

    @EventHandler
    public void RainDamage(WeatherChangeEvent e) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (Objects.requireNonNull(ClassData.getClassStorage().get(p.getUniqueId() + " Class")).equals("enderhusk")) {
                BukkitRunnable rainDamage = new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (p.getWorld().hasStorm()) {

                            int blockLocation = p.getLocation().getWorld().getHighestBlockYAt(p.getLocation());
                            if (blockLocation <= p.getLocation().getY()) {
                                p.damage(0.5);
                            }
                        } else this.cancel();
                    }

                };
                rainDamage.runTaskTimer(plugin, 0L, 20);
            }
        }
    }

    @EventHandler
    public void PumpkinPickup(PlayerPickupItemEvent e) {
        Player p = e.getPlayer();
        if (Objects.requireNonNull(ClassData.getClassStorage().get(p.getUniqueId() + " Class")).equals("enderhusk")) {
            if(e.getItem().getItemStack().getType().equals(Material.PUMPKIN) || e.getItem().getItemStack().getType().equals(Material.PUMPKIN_PIE) || e.getItem().getItemStack().getType().equals(Material.PUMPKIN_SEEDS) || e.getItem().getItemStack().getType().equals(Material.PUMPKIN_STEM) || e.getItem().getItemStack().getType().equals(Material.CARVED_PUMPKIN) || e.getItem().getItemStack().getType().equals(Material.ATTACHED_PUMPKIN_STEM)) {
                e.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void PumpkinChest(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (Objects.requireNonNull(ClassData.getClassStorage().get(p.getUniqueId() + " Class")).equals("enderhusk")) {
            try {
                if (e.getCurrentItem().getType().toString().toLowerCase().contains("pumpkin") && e.getCurrentItem() != null) {
                    e.setCancelled(true);
                }
            }catch(NullPointerException error) {return;}
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (Objects.requireNonNull(ClassData.getClassStorage().get(p.getUniqueId() + " Class")).equals("enderhusk")) {
            BukkitRunnable rainDamage = new BukkitRunnable() {
                @Override
                public void run() {
                    Material m = e.getPlayer().getLocation().getBlock().getType();
                    if (m == Material.LEGACY_STATIONARY_WATER || m == Material.WATER) {
                        e.getPlayer().damage(0.5);
                    } else this.cancel();
                }

            };
            rainDamage.runTaskTimer(plugin, 0L, 20);
        } else return;
    }
}