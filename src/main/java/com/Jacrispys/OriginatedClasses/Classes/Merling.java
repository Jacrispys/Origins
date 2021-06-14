package com.Jacrispys.OriginatedClasses.Classes;

import com.Jacrispys.OriginatedClasses.Files.ClassData;
import com.Jacrispys.OriginatedClasses.OriginatedClassesMain;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityAirChangeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;

public class Merling implements Listener {

    private OriginatedClassesMain plugin;

    public Merling(OriginatedClassesMain plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
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
    public void onPlayerMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (Objects.requireNonNull(ClassData.getClassStorage().get(p.getUniqueId() + " Class")).equals("atlantian")) {
            if (p.isSwimming()) {
                p.setWalkSpeed(2);
            } else if((p.isSwimming())) {
                p.setWalkSpeed(1);
            }
        }
    }
}
