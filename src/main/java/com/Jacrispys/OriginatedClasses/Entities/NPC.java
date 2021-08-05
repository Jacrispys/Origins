package com.Jacrispys.OriginatedClasses.Entities;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.EntityHuman;
import net.minecraft.server.v1_16_R3.World;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class NPC extends EntityHuman implements SpawnEntity {
    public NPC(World world, BlockPosition blockposition, float f, GameProfile gameprofile) {
        super(world, blockposition, f, gameprofile);
    }

    @Override
    public void spawn() {
        org.bukkit.World world = this.getBukkitEntity().getWorld();
        ((CraftWorld)world).getHandle().addEntity(this, CreatureSpawnEvent.SpawnReason.CUSTOM);

    }

    @Override
    public void EntitySpawning() {

    }


    @Override
    public boolean isSpectator() {
        return false;
    }

    @Override
    public boolean isCreative() {
        return false;
    }
}
