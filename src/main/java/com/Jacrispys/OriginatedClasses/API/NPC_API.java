package com.Jacrispys.OriginatedClasses.API;


import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.UUID;


public class NPC_API {
    public static EntityPlayer createNPC(ResourceKey<World> dimension, UUID playerUUID, Player player, Location loc) {
        MinecraftServer serverNMS = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer worldNMS = serverNMS.getWorldServer(dimension);
        GameProfile profile = new GameProfile(playerUUID, player.getName());
        EntityPlayer npc = new EntityPlayer(serverNMS, worldNMS, profile, new PlayerInteractManager(worldNMS));




        npc.setNoGravity(false);
        npc.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        return npc;
    }

    public static void spawnNPCPacket(EntityPlayer npc, Player player) {
        PlayerConnection connection = ((CraftPlayer)player).getHandle().playerConnection;
        connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
        connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
        connection.sendPacket(new PacketPlayOutEntityHeadRotation(npc, (byte) (npc.yaw * 256/360)));
    }

    public static void removeNPCPacket(Entity npc, Player player) {
        PlayerConnection connection = ((CraftPlayer)player).getHandle().playerConnection;
        connection.sendPacket(new PacketPlayOutEntityDestroy(npc.getId()));
    }

    /*
    Found from:
     */

    public static void sendSetNPCSkinPacket(EntityPlayer npc, Player player, String username) { // The username is the name for the player that has the skin.
        removeNPCPacket(npc, player);

        try {
            HttpsURLConnection connection = (HttpsURLConnection) new URL(String.format("https://api.ashcon.app/mojang/v2/user/%s", username)).openConnection();
            if (connection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                ArrayList<String> lines = new ArrayList<>();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                reader.lines().forEach(lines::add);

                String reply = String.join(" ",lines);
                int indexOfValue = reply.indexOf("\"value\": \"");
                int indexOfSignature = reply.indexOf("\"signature\": \"");
                String skin = reply.substring(indexOfValue + 10, reply.indexOf("\"", indexOfValue + 10));
                String signature = reply.substring(indexOfSignature + 14, reply.indexOf("\"", indexOfSignature + 14));
                GameProfile profile = new GameProfile(player.getUniqueId(), player.getName());
                profile.getProperties().put("textures", new Property("textures", skin, signature));
            }

            else {
                Bukkit.getConsoleSender().sendMessage("Connection could not be opened when fetching player skin (Response code " + connection.getResponseCode() + ", " + connection.getResponseMessage() + ")");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // The client settings.
        DataWatcher watcher = npc.getDataWatcher();
        int byteAsInt = 79;
        watcher.set(new DataWatcherObject<>(16, DataWatcherRegistry.a), (byte) byteAsInt);
        PacketPlayOutEntityMetadata packet = new PacketPlayOutEntityMetadata(npc.getId(), watcher, true);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);

        spawnNPCPacket(npc, player);
    }
}
