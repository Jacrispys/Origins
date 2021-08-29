package com.Jacrispys.OriginatedClasses.Utils.ClassUtils;

import com.Jacrispys.OriginatedClasses.Files.ClassData;
import com.Jacrispys.OriginatedClasses.OriginatedClassesMain;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.Plugin;

import java.util.Objects;

import static com.Jacrispys.OriginatedClasses.Utils.Chat.ColorChat.color;

public class InfoMOTD implements Listener {



    @SuppressWarnings("unused")
    private static final Plugin plugin = OriginatedClassesMain.getPlugin();
    @SuppressWarnings("unused")
    private static final ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();


    public InfoMOTD(OriginatedClassesMain plugin) {

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPing(ServerListPingEvent e) {
        try {
            for (String key : ClassData.getClassStorage().getConfigurationSection("Players").getKeys(false)) {
                if (ClassData.getClassStorage().getConfigurationSection("Players." + key).contains(".Address")) {
                    String address = String.valueOf(Objects.requireNonNull(ClassData.getClassStorage().get("Players." + key + ".Address")));
                    if (address.equalsIgnoreCase(e.getAddress().getHostName())) {

                        String ign = String.valueOf(ClassData.getClassStorage().get("Players." + key + ".Name"));
                        String playerClass = String.valueOf(ClassData.getClassStorage().get("Players." + key + ".Class"));

                        e.setMotd(color("&e&LWelcome back, &a&l" + ign + "\n" +
                                "&3&lClass: &b" + playerClass));
                        return;
                    }
                } else e.setMotd("no address");
            }
        }catch(NullPointerException e1) {
            e.setMotd("not present");
        }
    }


    /*
    public static void listenForMotd() {
        protocolManager.addPacketListener(new PacketAdapter(PacketAdapter.params(plugin, PacketType.Status.Server.SERVER_INFO).optionAsync()) {
            @Override
            public void onPacketSending(PacketEvent event) {
                PacketContainer eventPacket = event.getPacket();
                if(eventPacket.getType() == PacketType.Status.Server.SERVER_INFO) {

                }
            }
        });
    }

     */


}
