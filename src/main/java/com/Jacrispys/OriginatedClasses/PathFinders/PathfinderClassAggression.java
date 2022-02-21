package com.Jacrispys.OriginatedClasses.PathFinders;

import com.Jacrispys.OriginatedClasses.Files.ClassData;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.PolarBear;
import org.bukkit.event.entity.EntityTargetEvent;

import java.lang.reflect.Field;
import java.util.EnumSet;
import java.util.Objects;

public class PathfinderClassAggression extends PathfinderGoalTarget {


    private final EntityInsentient a; //bear
    private EntityLiving b; // target

    private double c; //x
    private double d; //y
    private double e; //z

    private boolean classCheck;
    private final boolean f; //flag
    private final boolean g; //flag1
    private int h; //health?

    private double i;
    private double j;
    private int k;
    private double attackCooldown;

    private double l; //follow distance

    private final double s;


    public PathfinderClassAggression(EntityInsentient a, boolean flag, boolean flag1, double speed, double followDistance) {
        super(a, flag, flag1);
        this.a = a;
        this.f = flag;
        this.g = flag1;
        this.h = 60;
        this.s = speed;
        this.l = followDistance;
        Location loc = this.a.getBukkitEntity().getLocation();
        this.a(EnumSet.of(Type.TARGET));

    }


    protected void g() {
        for (Entity entity : a.getWorld().getWorld().getNearbyEntities(a.getBukkitEntity().getLocation(), 10, 10, 10)) {
            if (entity instanceof Player) {
                Player target = (Player) entity;
                EntityLiving el = ((CraftPlayer) target).getHandle();
                if (ClassData.getClassStorage().get("Players." + target.getUniqueId() + ".Class").toString().equalsIgnoreCase("shade")) {
                    this.b = el;
                    this.classCheck = true;
                } else this.classCheck = false;
            }
        }
    }

    @Override
    public boolean b() {
        // runs after C checks if C should continue
        if (this.b.isInvulnerable()) {
            return false;
        }
        return this.a.getGoalTarget() != null;
    }

    @Override
    public void d() {
        this.b = null;
    }

    @Override
    public double k() {
        return this.l;
    }

    @Override
    public boolean a() {
        //starts pathfinders if true, runs every tick

        g();

        if (this.b instanceof Player) {
            Player p = (Player) this.b;
            if (p.getGameMode().equals(GameMode.CREATIVE) || p.getGameMode().equals(GameMode.SPECTATOR)) {
                return false;
            }
        }


        return (this.classCheck);

    }


    @Override
    public void c() {
        this.c = this.b.locX();
        this.d = this.b.locY();
        this.e = this.b.locZ();
        this.a.getNavigation().a(this.c, this.d, this.e, this.s);
        this.k = 0;
        this.h = 0;
        this.a.setGoalTarget(this.b, EntityTargetEvent.TargetReason.TEMPT, true);

    }

    @Override
    public void e() {
        EntityLiving var0 = this.a.getGoalTarget();
        this.a.getControllerLook().a(var0, 30.0F, 30.0F);
        double var1 = this.a.h(var0.locX(), var0.locY(), var0.locZ());
        this.h = Math.max(this.h - 1, 0);

        if ((this.f || this.a.getEntitySenses().a(var0)) &&
                this.h <= 0 && ((
                this.e == 0.0D && this.i == 0.0D && this.j == 0.0D) || var0.h(this.e, this.i, this.j) >= 1.0D || this.a.getRandom().nextFloat() < 0.05F)) {
            this.c = var0.locX();
            this.d = var0.locY();
            this.e = var0.locZ();
            this.h = 4 + this.a.getRandom().nextInt(7);

            if (var1 > 1024.0D) {
                this.h += 10;
            } else if (var1 > 256.0D) {
                this.h += 5;
            }

            if (!this.a.getNavigation().a(var0, this.s)) {
                this.h += 15;
            }
        }


        this.k = Math.max(this.k - 1, 0);
        this.attackCooldown = Math.max(this.attackCooldown - 1, 0);
        double var3 = reach(this.a);
        if (var1 <= var3 && this.attackCooldown <= 0) {
            this.attackCooldown = 20;
            this.a.swingHand(EnumHand.MAIN_HAND);
            if (this.a.getEntityType() == EntityTypes.POLAR_BEAR) {

                try {
                    Field stand = EntityPolarBear.class.getDeclaredField("bo");
                    stand.setAccessible(true);
                    @SuppressWarnings("unchecked")
                    DataWatcherObject<Boolean> dataWatcherObject = (DataWatcherObject<Boolean>) stand.get(null);
                    this.a.getDataWatcher().set(dataWatcherObject, true);
                    this.a.attackEntity(this.b);
                } catch (IllegalAccessException | NoSuchFieldException | NullPointerException exception) {
                    exception.printStackTrace();
                }
            } else {
                this.a.attackEntity(this.b);
            }

            a(var0, PathfinderTargetCondition.a);

        }
    }


    protected double reach(EntityLiving var0) {
        return (this.a.getWidth() * 2.0F * this.a.getWidth() * 2.0F + var0.getWidth());
    }
}
