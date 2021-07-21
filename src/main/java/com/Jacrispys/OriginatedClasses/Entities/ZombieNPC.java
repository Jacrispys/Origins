package com.Jacrispys.OriginatedClasses.Entities;

import net.minecraft.server.v1_16_R3.EntityZombie;
import net.minecraft.server.v1_16_R3.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_16_R3.PacketPlayOutPlayerInfo;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class ZombieNPC extends EntityZombie implements SpawnEntity {




    public ZombieNPC(Location loc) {
        super(((CraftWorld)loc.getWorld()).getHandle());
        this.setPosition(loc.getX(), loc.getY(), loc.getZ());

    }


    @Override
    public void spawn() {
        World world = this.getBukkitEntity().getWorld();
        ((CraftWorld)world).getHandle().addEntity(this, CreatureSpawnEvent.SpawnReason.CUSTOM);
        
    }
}
