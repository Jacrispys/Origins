package com.Jacrispys.OriginatedClasses.API;

import com.Jacrispys.OriginatedClasses.OriginatedClassesMain;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;
import java.util.*;

import static com.Jacrispys.OriginatedClasses.Utils.TabCreation.removablePlayerList;

public class TabAPI {

    Plugin plugin = OriginatedClassesMain.getPlugin();


    public void removePlayer(Player receiver, Player removePlayer) {
        CraftPlayer receiverNMS = ((CraftPlayer) receiver);
        CraftPlayer removeNMS = ((CraftPlayer) removePlayer);
        PacketPlayOutPlayerInfo removePacket = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, removeNMS.getHandle());
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
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer worldServer = server.getWorldServer(World.OVERWORLD);
        PlayerInteractManager playerInteractManager = new PlayerInteractManager(worldServer);
        //create profile
        GameProfile profile = new GameProfile(UUID.randomUUID(), name);
        profile.getProperties().put("textures", new Property("textures", texture, sig));
        //send profile
        assert worldServer != null;
        EntityPlayer entityPlayer = new EntityPlayer(server, worldServer, profile, playerInteractManager);
        entityPlayer.listName = new ChatComponentText(listName);
        /*
        Use Reflection to remove Tab completion
        Does not Currently Work
         */

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
        for (int i = 1; i < Bukkit.getOnlinePlayers().size(); i++) {
            PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, removablePlayerList.get(receiver.getUniqueId() + String.valueOf(i)));
            ((CraftPlayer) receiver).getHandle().playerConnection.sendPacket(packet);

        }
    }

    public void updatedPlayerList(Boolean login) {
        for (Player players : Bukkit.getOnlinePlayers()) {
            if (login) {
                PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, removablePlayerList.get(players.getUniqueId() + String.valueOf(Bukkit.getOnlinePlayers().size())));
                ((CraftPlayer) players).getHandle().playerConnection.sendPacket(packet);
            } else {
                PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, removablePlayerList.get(players.getUniqueId() + String.valueOf(Bukkit.getOnlinePlayers().size())));
                ((CraftPlayer) players).getHandle().playerConnection.sendPacket(packet);
            }
        }
    }


}

