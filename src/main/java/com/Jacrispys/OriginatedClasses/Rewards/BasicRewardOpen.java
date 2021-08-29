package com.Jacrispys.OriginatedClasses.Rewards;

import com.Jacrispys.OriginatedClasses.OriginatedClassesMain;
import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.datafixers.util.Pair;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.*;

import static com.Jacrispys.OriginatedClasses.Utils.Chat.ColorChat.color;
import static com.Jacrispys.OriginatedClasses.Utils.Math.locationDirection.genVec;
import static java.lang.Math.abs;

public class BasicRewardOpen implements CommandExecutor, Listener {

    //  8/28/2021 make base interface for all method reference, this is base class for all rewards.

    private final Plugin plugin;


    public BasicRewardOpen(OriginatedClassesMain main) {

        plugin = main;


        Objects.requireNonNull(main.getCommand("openreward")).setExecutor(this);
        Bukkit.getPluginManager().registerEvents(this, plugin);

    }

    private final Map<UUID, List<Integer>> playerRewards = new HashMap<>();
    private final List<Integer> rewardEntityList = new ArrayList<>();
    private final Map<Integer, Location> originalLocation = new HashMap<>();
    private final Map<Integer, Location> currentLocation = new HashMap<>();


    @SuppressWarnings("unused, reflection util")
    protected void setValue(Class<?> newClass, String field, Object value1, Object value2) {
        try {
            newClass.getDeclaredField(field).set(value1, value2);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    protected ItemStack customSkull(String texture, String sig) {
        ItemStack im = new ItemStack(Material.PLAYER_HEAD, 1);

        SkullMeta sm = (SkullMeta) im.getItemMeta();

        if(plugin.getServer().getName().toLowerCase().contains("paper") || plugin.getServer().getName().toLowerCase().contains("waterfall")) {
            PlayerProfile playerProfile;
            playerProfile = Bukkit.getServer().createProfile(UUID.randomUUID());

            playerProfile.setProperty(new ProfileProperty("textures", texture, sig));
            sm.setPlayerProfile(playerProfile);
        } else {
            try {
                GameProfile playerProfile = new GameProfile(UUID.randomUUID(), null);

                playerProfile.getProperties().put("textures", new Property("textures", texture, sig));

                Field profileField;
                profileField = sm.getClass().getDeclaredField("profile");
                profileField.setAccessible(true);
                profileField.set(sm, playerProfile);
                profileField.setAccessible(false);
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        im.setItemMeta(sm);

        return im;
    }


    protected EntityArmorStand rewardArmorStand(String name, World world, Location loc, Player opening, ItemStack skull) {
        EntityArmorStand armorStand = new EntityArmorStand(world, loc.getX(), loc.getY(), loc.getZ());

        DataWatcher watcher = new DataWatcher(null);

        IChatBaseComponent nameComponent = new ChatComponentText(color((name)));


        armorStand.setCustomName(nameComponent);
        armorStand.setCustomNameVisible(true);

        armorStand.setInvulnerable(true);
        armorStand.setInvisible(true);


        armorStand.setArms(false);
        armorStand.setBasePlate(false);
        armorStand.setNoGravity(true);

        net.minecraft.server.v1_16_R3.ItemStack im = CraftItemStack.asNMSCopy(skull);
        armorStand.setSlot(EnumItemSlot.HEAD, im);
        armorStand.getEquipment(EnumItemSlot.HEAD).setItem(im.getItem());

        Location headLoc = armorStand.getBukkitEntity().getLocation();
        headLoc.setY(armorStand.getHeadY());

        originalLocation.put(armorStand.getBukkitEntity().getEntityId(), headLoc);
        currentLocation.put(armorStand.getBukkitEntity().getEntityId(), headLoc);


        return armorStand;
    }

    protected void newReward(Player p) {
        String texture = "ewogICJ0aW1lc3RhbXAiIDogMTYyOTMyODg3NjU0NiwKICAicHJvZmlsZUlkIiA6ICJiNzVjZDRmMThkZjg0MmNlYjJhY2MxNTU5MTNiMjA0YiIsCiAgInByb2ZpbGVOYW1lIiA6ICJLcmlzdGlqb25hczEzIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2RkMzBlZTMwYTRmOTg1YjhhN2Y0MjY1Nzk0NmFiNWU3MmNjYzRkMzllOWNiMDlhYTEyNTdiNjAyMTliY2RjNiIKICAgIH0KICB9Cn0=";
        String sig = "HUEUwUkRssYK6O1Er3/qfbZ77pyxHyBWjDO/ftSss/OiC7Hc6R/AsDI0wJe7YhcCXz/z6lQSrB2hGYqsrm9bDP+G42gHjLLCPkzQKevowtdaQNpLBTGvLTWAfCMqo3QcJibolXAgGarzb3pYKiEj6cuiFFvBaa0gtCWxBqCR2G2XUJYCFWJoM0zObR5TzR7bNNAY6HyVMqHM1HpWXRwxkhvJrNbu30URkzSJ0/8Nz1z+XBC8QJz/k++j8Ka1G8kC8MOE8pS7vPYD03f1Nd0ePxlUGheyh3wKyu4RdG7E9b8QIZ9wZN3sQjqRwLSwvJlGxT2NXiVcDuxS782yN3XE5mAeZBH0la1GIWFpe9z+TbHhVagV5GUhf5tUsa8HGJq3hHGGM8lInKMLPb1JYFCtlqbEQGxkYavEID6rp9DtZzFrMBMCnd5wp2TxBjn9+hOw6m/GCrSjQ9hOUPCwxtb3Xy79nsSyNPhCnJ1HeqOeCJQs1MbCwAqnaz+9X5yniIVzalNvs3hufbcD/pfws6BhlTnsrNhnnKUDHm1bg52Zci3ng6gvjIyXmfqhStQILE3ill5YehHwR/xPHtTRi5rKWuZLLjlVVje/baQK1BGhrcv5MGju9Rm48qMfEdb9rRLYrcGaca9/Ga+7IowwE4wVqR6lyjiPFI8UgjsT2P23W04=";


        ItemStack customSkull = customSkull(texture, sig);

        EntityArmorStand am = rewardArmorStand("test", ((CraftWorld) p.getWorld()).getHandle(), p.getLocation(), p, customSkull);

        CraftPlayer craft = (CraftPlayer) p;
        PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(am);
        PacketPlayOutEntityMetadata armorStandData = new PacketPlayOutEntityMetadata(am.getBukkitEntity().getEntityId(), am.getDataWatcher(), true);

        final List<Pair<EnumItemSlot, net.minecraft.server.v1_16_R3.ItemStack>> equipmentList = new ArrayList<>();
        equipmentList.add(new Pair<>(EnumItemSlot.HEAD, am.getEquipment(EnumItemSlot.HEAD)));

        PacketPlayOutEntityEquipment armorStandArmor = new PacketPlayOutEntityEquipment(am.getBukkitEntity().getEntityId(), equipmentList);


        craft.getHandle().playerConnection.sendPacket(packet);
        craft.getHandle().playerConnection.sendPacket(armorStandData);
        craft.getHandle().playerConnection.sendPacket(armorStandArmor);
        rewardEntityList.add(am.getBukkitEntity().getEntityId());
        playerRewards.put(p.getUniqueId(), rewardEntityList);

    }

    private Integer getLookingAt(Player player) {
        int range = 5;
        Location eye = player.getEyeLocation();
        Integer lookingAt = null;
        if(playerRewards.get(player.getUniqueId()) != null) {
            for (Integer entityID : playerRewards.get(player.getUniqueId())) {
                Location entityLoc = currentLocation.get(entityID);
                Vector vector = entityLoc.toVector().subtract(eye.toVector());
                if(!(player.getLocation().distance(entityLoc) > range)) {
                    if (vector.normalize().dot(eye.getDirection()) > 0.9D) {
                        lookingAt = entityID;
                    }
                }
            }
        } else return 0;
        return lookingAt;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(!(cmd.getName().equalsIgnoreCase("openreward"))) { return false; }
        if(!(sender instanceof Player)) { return false; }
        Player p = (Player) sender;
        if(!(args.length == 0)) {
            if(args.length == 1) {
                if(!(Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[0])))) {
                    p = Bukkit.getPlayer(args[0]);
                    newReward(p);
                } else {
                    switch (args[0]) {
                        case ("help"):
                            sender.sendMessage(color("help msg"));
                            return false;
                        case("admin"):
                            return false;
                    }
                }
            }
        } else { newReward(p); }

            return false;
    }

    @EventHandler
    public void onArmorStandHover(PlayerMoveEvent e) {
        if(playerRewards.get(e.getPlayer().getUniqueId()) == null) { return;}
        if(playerRewards.get(e.getPlayer().getUniqueId()).contains(getLookingAt(e.getPlayer()))) {
            Player p = e.getPlayer();
            int entityID = getLookingAt(p);
            if(originalLocation.get(entityID) != currentLocation.get(entityID)) { return;}
            Location loc = originalLocation.get(entityID);
            Vector vec = p.getLocation().subtract(loc).toVector().normalize().multiply(5);
            Location updated = loc.add(vec);
            CraftPlayer cp = ((CraftPlayer) p);
            short newX = (short) ((short) (((updated.getX()) * 32) - ((loc.getX()) * 32)) * 128);
            short newY = (short) ((short) (((updated.getY()) * 32) - ((loc.getY()) * 32)) * 128);
            short newZ = (short) ((short) ((updated.getZ() * 32) - (loc.getZ() * 32)) * 128);
            //p.sendMessage("x move: " + newX + ", y move: " + newY + ". z move: " + newZ);
            //p.sendMessage("new Y" + updated.getY() + ", old Y" + loc.getY() + " = " + newY);
            PacketPlayOutEntity.PacketPlayOutRelEntityMove packet = new PacketPlayOutEntity.PacketPlayOutRelEntityMove(entityID, newX, newY, newZ, true);
            cp.getHandle().playerConnection.sendPacket(packet);
            cp.playSound(cp.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 1.0F);
            currentLocation.put(entityID, updated);
            p.sendMessage("old loc = " + loc.getX() );

        } else for(Integer allStands : playerRewards.get(e.getPlayer().getUniqueId())) {
            if(originalLocation.get(allStands) != currentLocation.get(allStands) && !(allStands.equals(getLookingAt(e.getPlayer())))) {
                Location loc = currentLocation.get(allStands);
                CraftPlayer cp = ((CraftPlayer) e.getPlayer());
                Location updated = originalLocation.get(allStands);
                short newX = (short) ((short) (((updated.getX()) * 32) - ((loc.getX()) * 32)) * 128);
                short newY = (short) ((short) (((updated.getY()) * 32) - ((loc.getY()) * 32)) * 128);
                short newZ = (short) ((short) ((updated.getZ() * 32) - (loc.getZ() * 32)) * 128);
                PacketPlayOutEntity.PacketPlayOutRelEntityMove packet = new PacketPlayOutEntity.PacketPlayOutRelEntityMove(allStands, newX, newY, newZ, true);
                cp.getHandle().playerConnection.sendPacket(packet);
                currentLocation.put(allStands, originalLocation.get(allStands));
            }
        }
    }





}
