package com.Jacrispys.OriginatedClasses;

import com.Jacrispys.OriginatedClasses.Classes.Enderian;
import com.Jacrispys.OriginatedClasses.Classes.Merling;
import com.Jacrispys.OriginatedClasses.Classes.Shade;
import com.Jacrispys.OriginatedClasses.Commands.BearCommand;
import com.Jacrispys.OriginatedClasses.Files.ClassData;
import com.Jacrispys.OriginatedClasses.ClassCore.ClassSelection;
import com.Jacrispys.OriginatedClasses.Utils.TabCreation;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Objects;
import java.util.logging.Level;

import static com.Jacrispys.OriginatedClasses.Utils.Chat.chat;

public class OriginatedClassesMain extends JavaPlugin implements Listener {

    private static Plugin plugin;

    public static Plugin getPlugin() {
        return plugin;
    }

    public static ProtocolManager protocolManager;

    private final PluginDescriptionFile pluginInfo = this.getDescription();



    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        protocolManager = ProtocolLibrary.getProtocolManager();
        plugin = this;
        new ClassSelection(this);
        new Enderian(this);
        new Merling(this);
        new TabCreation(this);
        new Shade(this);
        new BearCommand(this);
        this.saveDefaultConfig();
        ClassData.setup();
        ClassData.saveClassStorage();
        ClassData.getClassStorage().addDefault("##########################", null);
        ClassData.getClassStorage().addDefault("#PLAYER_DATA DO NOT TOUCH#", null);
        ClassData.getClassStorage().addDefault("##########################", null);
        ClassData.saveClassStorage();
        ClassData.getClassStorage().options().copyDefaults(true);


    }

    @Override
    public void onLoad() {
        Bukkit.getConsoleSender().sendMessage(chat(" "));
        Bukkit.getConsoleSender().sendMessage(chat("   &1__   &9__"));
        Bukkit.getConsoleSender().sendMessage(chat("  &1|  | &9|     &eOriginatedClasses &av" + pluginInfo.getVersion()));
        Bukkit.getConsoleSender().sendMessage(chat("  &1|__| &9|__   &8Running on " + this.getServer().getName() + " &8- " + this.getServer().getBukkitVersion()));
        Bukkit.getConsoleSender().sendMessage(chat(" "));
        getLogger().info(chat("Loading LuckPerms Dependency..."));
        if(Bukkit.getServer().getPluginManager().getPlugin("LuckPerms") == null) {
            Bukkit.getLogger().log(Level.SEVERE, "[OriginatedClasses] Core Dependency 'LuckPerms' not avalible within server scope!");
            Bukkit.getLogger().log(Level.SEVERE, "[OriginatedClasses] Without Dependency plugin will now disable!");
            Bukkit.getConsoleSender().sendMessage(chat("&c[OriginatedClasses] Core Dependency 'LuckPerms' not avalible within server scope!"));
            Bukkit.getConsoleSender().sendMessage(chat("&c[OriginatedClasses] Without Dependency plugin will now disable!"));
            this.getPluginLoader().disablePlugin(this);
        } else {
            getLogger().info(chat("LuckPerms Hook: Successful!"));
        }
    }


    @EventHandler(priority = EventPriority.LOW)
    public void handleNewPlayers(PlayerJoinEvent e) {
        if(ClassData.getClassStorage().get(e.getPlayer().getUniqueId().toString()) == null) {
            ClassData.getClassStorage().set(e.getPlayer().getUniqueId().toString(), null);
            ClassData.getClassStorage().set(e.getPlayer().getUniqueId() + ".Class", "null");
            ClassData.getClassStorage().set(e.getPlayer().getUniqueId() + ".Level", 0);
            ClassData.getClassStorage().set(e.getPlayer().getUniqueId() + ".EXP", 0);
            ClassData.saveClassStorage();
            ClassData.getClassStorage().options().copyDefaults(true);
        }
    }

    @EventHandler
    public void ipAuthChecker(AsyncPlayerPreLoginEvent e) {
        try {
            LuckPerms lp = LuckPermsProvider.get();
            if(!(e.getAddress().getHostName().equalsIgnoreCase(Objects.requireNonNull(ClassData.getClassStorage().get(e.getUniqueId() + ".Address")).toString()))) {
                @NonNull String playerGroup = Objects.requireNonNull(lp.getUserManager().getUser(e.getUniqueId())).getPrimaryGroup();
                if(Objects.requireNonNull(lp.getGroupManager().getGroup(playerGroup)).getCachedData().getPermissionData().checkPermission("2fa.true").asBoolean()) {
                    e.setKickMessage(chat(" &cYou are permanently banned from this server! \n" +
                        "&7Reason&f: Compromised Admin Account Detected! \n" +
                        "&7Current IP&f: " +  e.getAddress().getHostName()) + "/n" +
                            "&7Info&f: If this was a mistake or your IP has changed, please contact server administrators.");
                e.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_BANNED);
            }
        }
            return;
        }catch(NullPointerException e1) {
            return;
        }
    }




}
