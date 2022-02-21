package com.Jacrispys.OriginatedClasses.Utils.Commands;

import com.Jacrispys.OriginatedClasses.OriginatedClassesMain;
import net.minecraft.server.v1_16_R3.EntityBoat;
import net.minecraft.server.v1_16_R3.PacketPlayOutSpawnEntity;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftBoat;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import static com.Jacrispys.OriginatedClasses.Utils.Chat.ColorChat.color;

public class LagCommand implements CommandExecutor {


    @SuppressWarnings("unused")
    private final Plugin plugin;

    public LagCommand(OriginatedClassesMain main) {
        plugin = main;

        main.getCommand("lag").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(color("&CSender must be a Player!"));
            return false;
        }
        Player p = (Player) sender;

        if (!(args.length == 2)) {
            sender.sendMessage(color("&cLag Usage: /lag <Player> <Amount>"));
            return false;
        } else for (Player player : Bukkit.getOnlinePlayers()) {
            if (args[0].equalsIgnoreCase(player.getName())) {
                int amt;
                try {
                    amt = Integer.parseInt(args[1]);
                } catch (NullPointerException | NumberFormatException e) {
                    sender.sendMessage(color("Error: Amount of lag invalid!"));
                    return false;

                }


                Location loc = player.getLocation();
                CraftPlayer cp = (CraftPlayer) player;
                CraftServer server = cp.getHandle().world.getServer();

                long lagStart = System.currentTimeMillis();
                float rot = 0.0F;
                for (int i = 0; i < amt; i++) {
                    CraftBoat boat = new CraftBoat(server, new EntityBoat(cp.getHandle().getWorld(), loc.getX(), loc.getY(), loc.getZ()));
                    boat.setGravity(true);
                    boat.setRotation(rot, rot);
                    PacketPlayOutSpawnEntity packet = new PacketPlayOutSpawnEntity(boat.getHandle());
                    cp.getHandle().playerConnection.sendPacket(packet);
                    rot = rot + 0.1F;
                }
                long lagStop = System.currentTimeMillis();
                long lagFinal = lagStop - lagStart;
                sender.sendMessage(color("&aLag Complete! (" + lagFinal + "ms)"));
                return false;
            }
        }
        sender.sendMessage(color("&cError: Player '" + args[0] + "' was not found."));
        return false;
    }

}
