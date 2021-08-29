package com.Jacrispys.OriginatedClasses.Classes;

import com.Jacrispys.OriginatedClasses.Files.ClassData;
import com.Jacrispys.OriginatedClasses.OriginatedClassesMain;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityAirChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static com.Jacrispys.OriginatedClasses.Utils.Chat.ColorChat.color;

public class Merling implements Listener {

    private final OriginatedClassesMain plugin;

    public Merling(OriginatedClassesMain plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    private final Map<UUID, ItemStack> SwimmingBoots = new HashMap<>();

    private ItemStack swimmingBoots() {
        ItemStack tempBoots = new ItemStack(Material.CHAINMAIL_BOOTS, 1);
        ItemMeta tempBootMeta = tempBoots.getItemMeta();
        tempBoots.addUnsafeEnchantment(Enchantment.DEPTH_STRIDER, 6);
        tempBootMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        tempBootMeta.setUnbreakable(true);
        tempBootMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        tempBootMeta.setDisplayName(color("&bSwimmin Boots"));
        tempBoots.setItemMeta(tempBootMeta);
        return tempBoots;
    }

    private ItemStack InfiniteBucket() {
        ItemStack bucket = new ItemStack(Material.WATER_BUCKET, 1);
        ItemMeta bucketMeta = bucket.getItemMeta();
        bucketMeta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        bucketMeta.setDisplayName(color("&b&lInfini&f-&9&lBucket"));
        bucket.setItemMeta(bucketMeta);
        return bucket;
    }

    @EventHandler
    public void WaterBreathe(EntityAirChangeEvent e) {
        if(e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();

            ConfigurationSection playerConfig = ClassData.getClassStorage().getConfigurationSection("Players." + p.getUniqueId());

            if (playerConfig != null) {
                if (playerConfig.get(".Class").toString().equalsIgnoreCase("atlantian")) {

                    p.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 20, 1), true);

                } else return;
            }
        } else return;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        ConfigurationSection playerConfig = ClassData.getClassStorage().getConfigurationSection("Players." + p.getUniqueId());

        if (playerConfig != null) {
            if (playerConfig.get(".Class").toString().equalsIgnoreCase("atlantian")) {
                if(!(p.getInventory().contains(InfiniteBucket()) && !(p.getInventory().firstEmpty() == -1))) {
                    p.getInventory().addItem(InfiniteBucket());
                }
                if(p.getInventory().getItem(EquipmentSlot.FEET) != null) {
                    SwimmingBoots.put(p.getUniqueId(), p.getInventory().getItem(EquipmentSlot.FEET));
                }

                // Decrease Health

                Objects.requireNonNull(p.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(14);

                //AirDrowning

                HashMap<UUID, Long> startTime = new HashMap<>();
                startTime.put(p.getUniqueId(), System.currentTimeMillis());

                BukkitRunnable remainingBreath = new BukkitRunnable() {
                    @Override
                    public void run() {
                        String actionBar = null;
                        long currentTime = System.currentTimeMillis();
                        if(((CraftPlayer)p).getHandle().isInWater()) {
                            startTime.put(p.getUniqueId(), System.currentTimeMillis());
                        }
                        long timeElapsed = currentTime - startTime.get(p.getUniqueId());
                        if(timeElapsed < 30000) {
                            actionBar = (" &9▇ ▇ ▇ ▇ ▇ ▇ ▇ ▇ ▇ ▇");
                        } else if(timeElapsed < 60000) {
                            actionBar = (" &9▇ ▇ ▇ ▇ ▇ ▇ ▇ ▇ ▇ &7▇");
                        } else if(timeElapsed < 90000) {
                            actionBar = (" &9▇ ▇ ▇ ▇ ▇ ▇ ▇ ▇ &7▇ ▇");
                        } else if(timeElapsed < 120000) {
                            actionBar = (" &9▇ ▇ ▇ ▇ ▇ ▇ ▇ &7▇ ▇ ▇");
                        } else if(timeElapsed < 150000) {
                            actionBar = (" &9▇ ▇ ▇ ▇ ▇ ▇ &7▇ ▇ ▇ ▇");
                        } else if(timeElapsed < 180000) {
                            actionBar = (" &9▇ ▇ ▇ ▇ ▇ &7▇ ▇ ▇ ▇ ▇");
                        } else if (timeElapsed < 210000) {
                            actionBar = (" &9▇ ▇ ▇ ▇ &7▇ ▇ ▇ ▇ ▇ ▇");
                        } else if ( timeElapsed < 240000) {
                            actionBar = (" &9▇ ▇ ▇ &7▇ ▇ ▇ ▇ ▇ ▇ ▇");
                        } else if (timeElapsed < 270000) {
                            actionBar = (" &9▇ ▇ &7▇ ▇ ▇ ▇ ▇ ▇ ▇ ▇");
                        } else if (timeElapsed < 300000) {
                            actionBar = (" &9▇ ▇ &7▇ ▇ ▇ ▇ ▇ ▇ ▇ ▇");
                        } else if (timeElapsed < 330000) {
                            actionBar = (" &9▇ &7▇ ▇ ▇ ▇ ▇ ▇ ▇ ▇ ▇");
                        } else if(timeElapsed < 360000) {
                            p.damage(1);
                            actionBar = (" &c▇ ▇ ▇ ▇ ▇ ▇ ▇ ▇ ▇ ▇");
                        }
                        try {
                            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(color(actionBar)));
                        } catch (IllegalArgumentException exception) {}
                        if (!(playerConfig.get(".Class").toString().equalsIgnoreCase("atlantian"))) {
                            this.cancel();
                        }
                    }
                };
                remainingBreath.runTaskTimer(plugin, 1L,1L);

            } else return;
        }
    }

    @EventHandler
    public void cancelBootClick(InventoryClickEvent e) {
        try {
            if (e.getCurrentItem().isSimilar(swimmingBoots())) {
                e.setCancelled(true);
            }
        } catch(NullPointerException exception) { return; }
    }

    @EventHandler
    public void bucketUse(PlayerBucketEmptyEvent e) {
        Player p = e.getPlayer();

        ConfigurationSection playerConfig = ClassData.getClassStorage().getConfigurationSection("Players." + p.getUniqueId());

        if (playerConfig != null && playerConfig.get(".Class").toString().equalsIgnoreCase("atlantian")) {

            e.setCancelled(true);
            if (p.getItemInHand().isSimilar(InfiniteBucket())) {
                e.setCancelled(true);
                e.getBlock().setType(Material.WATER);
            } else {
                e.setCancelled(false);
            }
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();

        ConfigurationSection playerConfig = ClassData.getClassStorage().getConfigurationSection("Players." + p.getUniqueId());

        try {
            if (playerConfig != null && playerConfig.get(".Class").toString().equalsIgnoreCase("atlantian")) {

                if (p.isSwimming()) {
                    if (p.getInventory().getItem(EquipmentSlot.FEET) != null) {
                        SwimmingBoots.put(p.getUniqueId(), p.getInventory().getItem(EquipmentSlot.FEET));
                        SwimmingBoots.get(p.getUniqueId()).addUnsafeEnchantment(Enchantment.DEPTH_STRIDER, 6);
                    } else {
                        p.getInventory().setBoots(swimmingBoots());

                    }
                } else if (!(p.isSwimming())) {
                    if (p.getInventory().getBoots().isSimilar(swimmingBoots())) {
                        p.getInventory().setBoots(null);
                        return;
                    }
                    if (SwimmingBoots.get(p.getUniqueId()).getEnchantments().containsKey(Enchantment.DEPTH_STRIDER)) {
                        SwimmingBoots.get(p.getUniqueId()).removeEnchantment(Enchantment.DEPTH_STRIDER);
                    }
                }
            }
        } catch (NullPointerException exception) { return; }
    }
}
