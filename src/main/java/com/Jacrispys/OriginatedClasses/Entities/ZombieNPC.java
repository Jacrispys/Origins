package com.Jacrispys.OriginatedClasses.Entities;

import com.Jacrispys.OriginatedClasses.OriginatedClassesMain;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.plugin.Plugin;


public class ZombieNPC extends EntityZombie implements SpawnEntity {

    private final Plugin plugin = OriginatedClassesMain.getPlugin();

    public ZombieNPC(Location loc) {
        super(((CraftWorld) loc.getWorld()).getHandle());
        this.setPosition(loc.getX(), loc.getY(), loc.getZ());

    }


    @Override
    public void spawn() {
        World world = this.getBukkitEntity().getWorld();
        ((CraftWorld) world).getHandle().addEntity(this, CreatureSpawnEvent.SpawnReason.CUSTOM);

    }

    @Override
    public void EntitySpawning() {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        protocolManager.addPacketListener(
                new PacketAdapter(plugin, PacketType.Play.Server.SPAWN_ENTITY_LIVING) {
                    @Override
                    public void onPacketSending(PacketEvent event) {
                        PacketContainer packet = event.getPacket();
                        if (packet.getType() == PacketType.Play.Server.SPAWN_ENTITY_LIVING) {
                            CraftEntity entity = ((CraftEntity) packet.getEntityModifier(event.getPlayer().getWorld()).read(0));
                            if (entity.getHandle() instanceof ZombieNPC) {
                            } else Bukkit.broadcastMessage(entity.getHandle().toString());
                        }
                    }
                }
        );
    }
}
