package com.Jacrispys.OriginatedClasses.Entities;

import com.Jacrispys.OriginatedClasses.PathFinders.PathfinderClassAggression;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.Objects;

public class MinaciousBear extends EntityPolarBear implements SpawnEntity {


    public MinaciousBear(EntityTypes<? extends EntityPolarBear> var0, Location loc) {
        super(var0, ((CraftWorld)loc.getWorld()).getHandle());
        this.setPosition(loc.getX(), loc.getY(), loc.getZ());
        initAttribute();


    }

    @Override
    public void initPathfinder() {
        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(4, new PathfinderGoalFollowParent(this, 1.25D));
        this.goalSelector.a(5, new PathfinderGoalRandomStroll(this, 1.0D));
        this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
        this.goalSelector.a(7, new PathfinderGoalRandomLookaround(this));


        this.targetSelector.a(5, new PathfinderGoalUniversalAngerReset<>(this, true));
        this.targetSelector.a(1, new PathfinderClassAggression(this, true, false, 2.8D, 10D));

    }

    protected void initAttribute() {
        try {
            Objects.requireNonNull(this.craftAttributes.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE)).setBaseValue(8.0D);
            Objects.requireNonNull(this.craftAttributes.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(1.0D);
        }catch (NullPointerException exception) { //Null value
             }
    }



    @Override
    public void spawn() {
        world.addEntity(this, CreatureSpawnEvent.SpawnReason.CUSTOM);
    }

    @Override
    public void EntitySpawning() {

    }

    @Override
    public void c(NBTTagCompound nbttagcompound) {
        super.c(nbttagcompound);
    }

    @Override
    public void a(WorldServer worldserver, NBTTagCompound nbttagcompound) {
        super.a(worldserver, nbttagcompound);
    }

    @Override
    public void a(WorldServer worldserver, boolean flag) {
        super.a(worldserver, flag);
    }

    @Override
    public boolean a_(EntityLiving entityliving) {
        return super.a_(entityliving);
    }

    @Override
    public boolean a(World world) {
        return super.a(world);
    }

    @Override
    public boolean isAngry() {
        return super.isAngry();
    }



    @Override
    public void b(EntityHuman entityhuman) {
        super.b(entityhuman);
    }

    @Override
    public void I_() {
        super.I_();
    }

    @Override
    public void pacify() {
        super.pacify();
    }
}
