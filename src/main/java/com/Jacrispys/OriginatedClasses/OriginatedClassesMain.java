package com.Jacrispys.OriginatedClasses;

import com.Jacrispys.OriginatedClasses.Classes.Enderian;
import com.Jacrispys.OriginatedClasses.Classes.Merling;
import com.Jacrispys.OriginatedClasses.Classes.Shade;
import com.Jacrispys.OriginatedClasses.Commands.BearCommand;
import com.Jacrispys.OriginatedClasses.Files.ClassData;
import com.Jacrispys.OriginatedClasses.ClassCore.ClassSelection;
import com.Jacrispys.OriginatedClasses.Rewards.BasicRewardOpen;
import com.Jacrispys.OriginatedClasses.Utils.ClassUtils.InfoMOTD;
import com.Jacrispys.OriginatedClasses.Utils.Commands.LagCommand;
import com.Jacrispys.OriginatedClasses.Utils.Commands.LoopCommand;
import com.Jacrispys.OriginatedClasses.Utils.TabCreation;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
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
import java.util.UUID;
import java.util.logging.Level;

import static com.Jacrispys.OriginatedClasses.Utils.Chat.ColorChat.color;

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
        new InfoMOTD(this);
        new BasicRewardOpen(this);
        new LoopCommand(this);
        new LagCommand(this);
        this.saveDefaultConfig();
        ClassData.setup();
        ClassData.saveClassStorage();
        ClassData.getClassStorage().addDefault("##########################", null);
        ClassData.getClassStorage().addDefault("#PLAYER_DATA DO NOT TOUCH#", null);
        ClassData.getClassStorage().addDefault("##########################", null);
        ClassData.getClassStorage().options().copyDefaults(true);
        ClassData.saveClassStorage();

        if(!(ClassData.getClassStorage().contains("Players"))) {
            ClassData.getClassStorage().createSection("Players");
            ClassData.saveClassStorage();
        }


    }

    @Override
    public void onLoad() {
        Bukkit.getConsoleSender().sendMessage(color(" "));
        Bukkit.getConsoleSender().sendMessage(color("   &1__   &9__"));
        Bukkit.getConsoleSender().sendMessage(color("  &1|  | &9|     &eOriginatedClasses &av" + pluginInfo.getVersion()));
        Bukkit.getConsoleSender().sendMessage(color("  &1|__| &9|__   &8Running on " + this.getServer().getName() + " &8- " + this.getServer().getBukkitVersion()));
        Bukkit.getConsoleSender().sendMessage(color(" "));
        getLogger().info(color("Loading LuckPerms Dependency..."));
        if(Bukkit.getServer().getPluginManager().getPlugin("LuckPerms") == null) {
            Bukkit.getLogger().log(Level.SEVERE, "[OriginatedClasses] Core Dependency 'LuckPerms' not available within server scope!");
            Bukkit.getLogger().log(Level.SEVERE, "[OriginatedClasses] Without Dependency plugin will now disable!");
            Bukkit.getConsoleSender().sendMessage(color("&c[OriginatedClasses] Core Dependency 'LuckPerms' not available within server scope!"));
            Bukkit.getConsoleSender().sendMessage(color("&c[OriginatedClasses] Without Dependency plugin will now disable!"));
            this.getPluginLoader().disablePlugin(this);
        } else {
            getLogger().info(color("LuckPerms Hook: Successful!"));
        }

        getConfig().addDefault("vanillaXP-Override", false);
        getConfig().addDefault("sideBar-EXP", true);
        getConfig().addDefault("bossBar-EXP", true);
    }


    @EventHandler(priority = EventPriority.LOWEST)
    public void handleNewPlayers(PlayerJoinEvent e) {


        if(!(ClassData.getClassStorage().contains("Players." + e.getPlayer().getUniqueId()))) {

            UUID uuid = e.getPlayer().getUniqueId();


            ClassData.getClassStorage().createSection("Players." + uuid);
            ClassData.saveClassStorage();

            ConfigurationSection playerSection = ClassData.getClassStorage().getConfigurationSection("Players." + uuid);



            if(playerSection != null) {
                playerSection.set(".Class", "null");
                playerSection.set(".Level", 0);
                playerSection.set(".EXP", 0);
            }

            ClassData.saveClassStorage();
        }
    }

    @EventHandler
    public void ipAuthChecker(AsyncPlayerPreLoginEvent e) {
        try {
            LuckPerms lp = LuckPermsProvider.get();
            if(!(e.getAddress().getHostName().equalsIgnoreCase(Objects.requireNonNull(ClassData.getClassStorage().get("Players." + e.getUniqueId() + ".Address")).toString()))) {
                @NonNull String playerGroup = Objects.requireNonNull(lp.getUserManager().getUser(e.getUniqueId())).getPrimaryGroup();
                if(Objects.requireNonNull(lp.getGroupManager().getGroup(playerGroup)).getCachedData().getPermissionData().checkPermission("2fa.true").asBoolean()) {
                    e.setKickMessage(color(" &cYou are permanently banned from this server! \n" +
                        "&7Reason&f: Compromised Admin Account Detected! \n" +
                        "&7Current IP&f: " +  e.getAddress().getHostName()) + "/n" +
                            "&7Info&f: If this was a mistake or your IP has changed, please contact server administrators.");
                e.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_BANNED);
            }
        }
        }catch(NullPointerException e1) {
            return;
        }
    }

    @Override
    public void onDisable() {
        ClassData.saveClassStorage();
    }




}
