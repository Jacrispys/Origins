package com.Jacrispys.OriginatedClasses.Classes;

import com.Jacrispys.OriginatedClasses.Files.ClassData;
import com.Jacrispys.OriginatedClasses.OriginatedClassesMain;
import org.bukkit.Bukkit;
import org.bukkit.Material;
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

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

import static com.Jacrispys.OriginatedClasses.Utils.Chat.chat;

public class Merling implements Listener {

    private OriginatedClassesMain plugin;

    public Merling(OriginatedClassesMain plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    private final HashMap<UUID, ItemStack> SwimmingBoots = new HashMap<>();

    private ItemStack swimmingBoots() {
        ItemStack tempBoots = new ItemStack(Material.CHAINMAIL_BOOTS, 1);
        ItemMeta tempBootMeta = tempBoots.getItemMeta();
        tempBoots.addUnsafeEnchantment(Enchantment.DEPTH_STRIDER, 6);
        tempBootMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        tempBootMeta.setUnbreakable(true);
        tempBootMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        tempBootMeta.setDisplayName(chat("&bSwimmin Boots"));
        tempBoots.setItemMeta(tempBootMeta);
        return tempBoots;
    }

    private ItemStack InfiniteBucket() {
        ItemStack bucket = new ItemStack(Material.WATER_BUCKET, 1);
        ItemMeta bucketMeta = bucket.getItemMeta();
        bucketMeta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        bucketMeta.setDisplayName(chat("&b&lInfini&f-&9&lBucket"));
        bucket.setItemMeta(bucketMeta);
        return bucket;
    }

    @EventHandler
    public void WaterBreathe(EntityAirChangeEvent e) {
        if(e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (Objects.requireNonNull(ClassData.getClassStorage().get(p.getUniqueId() + " Class")).equals("atlantian")) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 20, 1), true);

            } else return;
        } else return;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (Objects.requireNonNull(ClassData.getClassStorage().get(p.getUniqueId() + " Class")).equals("atlantian")) {
            if(!(p.getInventory().contains(InfiniteBucket()) && !(p.getInventory().firstEmpty() == -1))) {
                p.getInventory().addItem(InfiniteBucket());
            }
            if(p.getInventory().getItem(EquipmentSlot.FEET) != null) {
                SwimmingBoots.put(p.getUniqueId(), p.getInventory().getItem(EquipmentSlot.FEET));
            }
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
        if (Objects.requireNonNull(ClassData.getClassStorage().get(p.getUniqueId() + " Class")).equals("atlantian")) {
            e.setCancelled(true);
            if(p.getItemInHand().isSimilar(InfiniteBucket())) {
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
        try {
            if (Objects.requireNonNull(ClassData.getClassStorage().get(p.getUniqueId() + " Class")).equals("atlantian")) {
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
