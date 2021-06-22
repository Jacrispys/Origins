package com.Jacrispys.OriginatedClasses.API;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.*;

import static com.Jacrispys.OriginatedClasses.Utils.Chat.chat;
import static com.Jacrispys.OriginatedClasses.Utils.TabCreation.logout;
import static com.Jacrispys.OriginatedClasses.Utils.TabCreation.removablePlayerList;

public class TabAPI {


    public void removePlayer(Player receiver) {
        CraftPlayer receiverNMS = ((CraftPlayer) receiver);
        PacketPlayOutPlayerInfo removePacket = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, receiverNMS.getHandle());
        receiverNMS.getHandle().playerConnection.sendPacket(removePacket);
    }

    public void removeAllPlayers(Player receiver) {
        Collection<? extends Player> playersBukkit = Bukkit.getOnlinePlayers();
        EntityPlayer[] playersNMS = new EntityPlayer[playersBukkit.size()];
        int current = 0;
        for (Player player : playersBukkit) {
            playersNMS[current] = ((CraftPlayer) player).getHandle();
            current++;
        }
        PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, playersNMS);
        ((CraftPlayer) receiver).getHandle().playerConnection.sendPacket(packet);
    }

    public EntityPlayer createPlayers(String name, String listName, String texture, String sig) {
        //get server info
        MinecraftServer server = ((CraftServer)Bukkit.getServer()).getServer();
        WorldServer worldServer = server.getWorldServer(World.OVERWORLD);
        PlayerInteractManager playerInteractManager = new PlayerInteractManager(worldServer);
        //create profile
        GameProfile profile = new GameProfile(UUID.randomUUID(), name);
        profile.getProperties().put("textures", new Property("textures", texture, sig));
        //send profile
        EntityPlayer entityPlayer = new EntityPlayer(server, worldServer, profile, playerInteractManager);
        entityPlayer.listName = new ChatComponentText(listName);
        return entityPlayer;
    }

    public void addPlayers(Player receiver, EntityPlayer... createdPlayers) {
        PacketPlayOutPlayerInfo infoPacket = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, createdPlayers);
        ((CraftPlayer) receiver).getHandle().playerConnection.sendPacket(infoPacket);
    }

    public void headerText(String header, String footer, Player receiver) {
        IChatBaseComponent headerComponent = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + header + "\"}");
        IChatBaseComponent footerComponent = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + footer + "\"}");

        PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();

        Field field;
        Field field1;
        try {
            field1 = packet.getClass().getDeclaredField("header");
            field1.setAccessible(true);
            field1.set(packet, headerComponent);
            field = packet.getClass().getDeclaredField("footer");
            field.setAccessible(true);
            field.set(packet, footerComponent);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }

        ((CraftPlayer) receiver).getHandle().playerConnection.sendPacket(packet);
    }

    public void newPlayerList(Player receiver) {
        int i = 1;
        for (Player players : Bukkit.getOnlinePlayers()) {
            MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
            WorldServer worldServer = server.getWorldServer(World.OVERWORLD);
            PlayerInteractManager playerInteractManager = new PlayerInteractManager(worldServer);
            EntityPlayer playerNMS = ((CraftPlayer) players.getPlayer()).getHandle();
            GameProfile profile = playerNMS.getProfile();
            Property property = profile.getProperties().get("textures").iterator().next();
            String texture = property.getValue();
            String signature = property.getSignature();
            if (Bukkit.getOnlinePlayers().size() < 10) {
                // player list arraylist
                // newPlayerList & updatePlayerList
                GameProfile changedProfile = new GameProfile(UUID.randomUUID(), "00" + i);
                changedProfile.getProperties().put("textures", new Property("textures", texture, signature));
                EntityPlayer entityPlayer = new EntityPlayer(server, worldServer, changedProfile, playerInteractManager);
                entityPlayer.listName = new ChatComponentText(chat("&c" + players.getPlayer().getName()));
                PacketPlayOutPlayerInfo removePacket = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, removablePlayerList.get(receiver.getUniqueId() + "00" + i));
                PacketPlayOutPlayerInfo addPacket = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, entityPlayer);
                ((CraftPlayer) receiver).getHandle().playerConnection.sendPacket(removePacket);
                ((CraftPlayer) receiver).getHandle().playerConnection.sendPacket(addPacket);
            } else if (Bukkit.getOnlinePlayers().size() < 21) {
                GameProfile changedProfile = new GameProfile(UUID.randomUUID(), "0" + i);
                changedProfile.getProperties().put("textures", new Property("textures", texture, signature));
                EntityPlayer entityPlayer = new EntityPlayer(server, worldServer, changedProfile, playerInteractManager);
                entityPlayer.listName = new ChatComponentText(chat("&c" + players.getPlayer().getName()));
                PacketPlayOutPlayerInfo removePacket = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, removablePlayerList.get(receiver.getUniqueId() + "0" + i));
                PacketPlayOutPlayerInfo addPacket = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, entityPlayer);
                ((CraftPlayer) receiver).getHandle().playerConnection.sendPacket(removePacket);
                ((CraftPlayer) receiver).getHandle().playerConnection.sendPacket(addPacket);
            }
            i++;
        }
    }

    public void updatedPlayerList(Player newAddition, Boolean login) {
        int i = Bukkit.getOnlinePlayers().size();
        for(Player players : Bukkit.getOnlinePlayers()) {
            if(players != newAddition) {
                if(login) {
                    MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
                    WorldServer worldServer = server.getWorldServer(World.OVERWORLD);
                    PlayerInteractManager playerInteractManager = new PlayerInteractManager(worldServer);
                    EntityPlayer playerNMS = ((CraftPlayer) newAddition.getPlayer()).getHandle();
                    GameProfile profile = playerNMS.getProfile();
                    Property property = profile.getProperties().get("textures").iterator().next();
                    String texture = property.getValue();
                    String signature = property.getSignature();
                    if (Bukkit.getOnlinePlayers().size() < 10) {
                        // player list arraylist
                        // newPlayerList & updatePlayerList
                        GameProfile changedProfile = new GameProfile(UUID.randomUUID(), "00" + Bukkit.getOnlinePlayers().size());
                        changedProfile.getProperties().put("textures", new Property("textures", texture, signature));
                        EntityPlayer entityPlayer = new EntityPlayer(server, worldServer, changedProfile, playerInteractManager);
                        entityPlayer.listName = new ChatComponentText(chat("&c" + newAddition.getPlayer().getName()));
                        PacketPlayOutPlayerInfo removePacket = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, removablePlayerList.get(players.getUniqueId() + "00" + i));
                        PacketPlayOutPlayerInfo addPacket = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, entityPlayer);
                        ((CraftPlayer) players).getHandle().playerConnection.sendPacket(removePacket);
                        ((CraftPlayer) players).getHandle().playerConnection.sendPacket(addPacket);
                        logout.put((newAddition.getUniqueId()), entityPlayer);
                    } else if (Bukkit.getOnlinePlayers().size() < 20) {
                        GameProfile changedProfile = new GameProfile(UUID.randomUUID(), "0" + Bukkit.getOnlinePlayers().size());
                        changedProfile.getProperties().put("textures", new Property("textures", texture, signature));
                        EntityPlayer entityPlayer = new EntityPlayer(server, worldServer, changedProfile, playerInteractManager);
                        entityPlayer.listName = new ChatComponentText(chat("&c" + newAddition.getPlayer().getName()));
                        PacketPlayOutPlayerInfo removePacket = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, removablePlayerList.get(players.getUniqueId() + "0" + i));
                        PacketPlayOutPlayerInfo addPacket = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, entityPlayer);
                        ((CraftPlayer) players).getHandle().playerConnection.sendPacket(removePacket);
                        ((CraftPlayer) players).getHandle().playerConnection.sendPacket(addPacket);
                        logout.put((newAddition.getUniqueId()), entityPlayer);
                    }
                } else {
                  if(Bukkit.getOnlinePlayers().size() < 10) {
                      PacketPlayOutPlayerInfo removePacket = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, logout.get(newAddition.getUniqueId()));
                      PacketPlayOutPlayerInfo addPacket = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, removablePlayerList.get(players.getUniqueId() + "00" + Bukkit.getOnlinePlayers().size()));
                      ((CraftPlayer) players).getHandle().playerConnection.sendPacket(removePacket);
                      ((CraftPlayer) players).getHandle().playerConnection.sendPacket(addPacket);
                  } else if(Bukkit.getOnlinePlayers().size() < 20) {
                      PacketPlayOutPlayerInfo removePacket = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, logout.get(newAddition.getUniqueId()));
                      PacketPlayOutPlayerInfo addPacket = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, removablePlayerList.get(players.getUniqueId() + "0" + Bukkit.getOnlinePlayers().size()));
                      ((CraftPlayer) players).getHandle().playerConnection.sendPacket(removePacket);
                      ((CraftPlayer) players).getHandle().playerConnection.sendPacket(addPacket);
                  }
                }
            } else return;
        }
    }

}
