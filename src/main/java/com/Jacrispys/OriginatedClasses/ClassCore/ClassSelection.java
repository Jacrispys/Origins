package com.Jacrispys.OriginatedClasses.ClassCore;

import com.Jacrispys.OriginatedClasses.Files.ClassData;
import com.Jacrispys.OriginatedClasses.OriginatedClassesMain;
import com.Jacrispys.OriginatedClasses.Utils.TabCreation;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_16_R3.ChatComponentText;
import net.minecraft.server.v1_16_R3.EntityPlayer;
import net.minecraft.server.v1_16_R3.PacketPlayOutPlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.craftbukkit.v1_16_R3.boss.CraftBossBar;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.tags.ItemTagType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static com.Jacrispys.OriginatedClasses.Utils.Chat.ColorChat.color;

public class ClassSelection implements Listener, CommandExecutor {

    private Plugin plugin = OriginatedClassesMain.getPlugin();

    public ClassSelection(OriginatedClassesMain plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
        Objects.requireNonNull(plugin.getCommand("origins")).setExecutor(this);
    }

    @SuppressWarnings("deprecated")
    private final Inventory classSelector = Bukkit.createInventory(null, 27, color("&3&lSelect Your Starter Class!"));

    @SuppressWarnings("deprecated")
    private final Inventory classConfirm = Bukkit.createInventory(null, 27, color("&c&lAre you sure?"));


    private final Map<UUID, ItemStack> saveConfirm = new HashMap<>();

    NamespacedKey Class = new NamespacedKey(plugin, "Class");

    @SuppressWarnings("deprecated")
    private void classSelector(Player p) {

        ConfigurationSection playerConfig = ClassData.getClassStorage().getConfigurationSection("Players." + p.getUniqueId());

        ItemStack Enderian = new ItemStack(Material.ENDER_PEARL);
        ItemMeta EnderianMeta = Enderian.getItemMeta();
        List<String> Enderianlore = new ArrayList<>();
        EnderianMeta.getCustomTagContainer().setCustomTag(Class, ItemTagType.STRING, "enderhusk");
        Enderianlore.add((ChatColor.of("#431BE6") + color("Enderhusk Perks Include:")));
        Enderianlore.add(color("&a+ &7Teleports after holding" + ChatColor.of("#1FEE96") + " SHIFT &7for 3 seconds!"));
        Enderianlore.add(color("&a+ &7Has &a&lDouble &cHealth"));
        Enderianlore.add(color("&c- &9Water &7damages you!"));
        Enderianlore.add(color("&c- &7Cannot pick up" + ChatColor.of("#E6961B") + " Pumpkins!"));
        EnderianMeta.setDisplayName(ChatColor.of("#1FEE96") + color("&lEnderhusk"));
        EnderianMeta.setLore(Enderianlore);
        Enderian.setItemMeta(EnderianMeta);

        ItemStack Merling = new ItemStack(Material.SEA_LANTERN);
        ItemMeta MerlingMeta = Merling.getItemMeta();
        List<String> Merlinglore = new ArrayList<>();
        MerlingMeta.getCustomTagContainer().setCustomTag(Class, ItemTagType.STRING, "atlantian");
        Merlinglore.add((ChatColor.of("#431BE6") + color("Atlantian Perks Include:")));
        Merlinglore.add(color("&a+ &7Can &bBreathe &7Under Water"));
        Merlinglore.add(color("&a+ &7Natural Depth Strider"));
        Merlinglore.add(color("&a+ &7Has a &1Waterbucket &7with &c∞ &7uses!"));
        Merlinglore.add(color("&c- &7Has Reversed drowning"));
        Merlinglore.add(color("&c- &7Has Decreased health"));
        MerlingMeta.setDisplayName(ChatColor.of("#50D4BC") + color("&lAtlantian"));
        MerlingMeta.setLore(Merlinglore);
        Merling.setItemMeta(MerlingMeta);

        ItemStack Phantom = new ItemStack(Material.PHANTOM_MEMBRANE);
        ItemMeta PhantomMeta = Phantom.getItemMeta();
        List<String> Phantomlore = new ArrayList<>();
        PhantomMeta.getCustomTagContainer().setCustomTag(Class, ItemTagType.STRING, "shade");
        Phantomlore.add((ChatColor.of("#431BE6") + color("Shade Perks Include:")));
        Phantomlore.add(color("&a+ &7Can Go invisible while shifting!"));
        Phantomlore.add(color("&a+ &7Takes no fall damage"));
        Phantomlore.add(color("&a+ &7Access to /roam command"));
        Phantomlore.add(color("&c- &7Takes extra damage from projectiles."));
        Phantomlore.add(color("&f&l☯ &7Loses health during the day, but gains health during the night"));
        PhantomMeta.setDisplayName(ChatColor.of("#DCDCCA") + color("&lShade"));
        PhantomMeta.setLore(Phantomlore);
        Phantom.setItemMeta(PhantomMeta);

        ItemStack Elytrian = new ItemStack(Material.ELYTRA);
        ItemMeta ElytrianMeta = Elytrian.getItemMeta();
        List<String> Elytrianlore = new ArrayList<>();
        ElytrianMeta.getCustomTagContainer().setCustomTag(Class, ItemTagType.STRING, "strix");
        Elytrianlore.add((ChatColor.of("#431BE6") + color("Strix Perks Include:")));
        Elytrianlore.add(color("&a+ &7Can fly for a short amount of time"));
        Elytrianlore.add(color("&a+ &7Access to /launch command (results may vary)"));
        Elytrianlore.add(color("&a+ &7Immune to suffocation"));
        Elytrianlore.add(color("&c- &7Has weakness based off flying time"));
        Elytrianlore.add(color("&c- &7Takes double fall damage"));
        ElytrianMeta.setDisplayName(ChatColor.of("#A1EEED") + color("&lStrix"));
        ElytrianMeta.setLore(Elytrianlore);
        Elytrian.setItemMeta(ElytrianMeta);

        ItemStack Blazeborn = new ItemStack(Material.BLAZE_ROD);
        ItemMeta BlazebornMeta = Blazeborn.getItemMeta();
        List<String> Blazebornlore = new ArrayList<>();
        BlazebornMeta.getCustomTagContainer().setCustomTag(Class, ItemTagType.STRING, "ignerian");
        Blazebornlore.add((ChatColor.of("#431BE6") + color("Ignerian Perks Include:")));
        Blazebornlore.add(color("&a+ &7Is immune to fire damage"));
        Blazebornlore.add(color("&a+ &7Can float while shifting!"));
        Blazebornlore.add(color("&a+ &7Takes no fall damage!"));
        Blazebornlore.add(color("&a+ &7 Gains &4Regeneration &7while burning!"));
        Blazebornlore.add(color("&c- &7While in 'float' mode takes 1.25x damage!"));
        Blazebornlore.add(color("&c- &7While in 'float' mode takes damage from rain"));
        Blazebornlore.add(color("&c- &7Takes damage while in water"));
        BlazebornMeta.setDisplayName(ChatColor.of("#E77D3D") + color("&lIgnerian"));
        BlazebornMeta.setLore(Blazebornlore);
        Blazeborn.setItemMeta(BlazebornMeta);

        ItemStack Avian = new ItemStack(Material.FEATHER);
        ItemMeta AvianMeta = Avian.getItemMeta();
        AvianMeta.setDisplayName(ChatColor.of("#713DE7") + color("&lGargoyle"));
        List<String> Avainlore = new ArrayList<>();
        AvianMeta.getCustomTagContainer().setCustomTag(Class, ItemTagType.STRING, "Gargoyle");
        Avainlore.add((ChatColor.of("#431BE6") + color("Gargoyle Perks Include:")));
        Avainlore.add(color("&a+ &7Become stone and gain &8RESISTANCE &7 after not moving for 3+ seconds!"));
        Avainlore.add(color("&a+ &7Your fist deals damage scaling to your xp!"));
        Avainlore.add(color("&d??? &7Drops feathers and stone on death"));
        Avainlore.add(color("&c- &7Deals 50% less damage with swords/axes"));
        Avainlore.add(color("&c- &7Gains slowness for a short time after becoming stone"));
        AvianMeta.setLore(Avainlore);
        Avian.setItemMeta(AvianMeta);

        ItemStack Arachnid = new ItemStack(Material.COBWEB);
        ItemMeta ArachnidMeta = Arachnid.getItemMeta();
        ArachnidMeta.setDisplayName(ChatColor.of("#491D81") + color("&lArachne"));
        List<String> Arachnidlore = new ArrayList<>();
        ArachnidMeta.getCustomTagContainer().setCustomTag(Class, ItemTagType.STRING, "arachne");
        Arachnidlore.add((ChatColor.of("#431BE6") + color("Arachne Perks Include:")));
        Arachnidlore.add(color("&a+ &7Can Climb walls (kinda)"));
        Arachnidlore.add(color("&a+ &7Traps Enemies in webs!"));
        Arachnidlore.add(color("&c- &7Moves slightly slower"));
        ArachnidMeta.setLore(Arachnidlore);
        Arachnid.setItemMeta(ArachnidMeta);

        ItemStack Shulk = new ItemStack(Material.SHULKER_BOX);
        ItemMeta ShulkMeta = Shulk.getItemMeta();
        ShulkMeta.setDisplayName(ChatColor.of("#BB6CDA") + color("&lScatola"));
        List<String> Shulklore = new ArrayList<>();
        ShulkMeta.getCustomTagContainer().setCustomTag(Class, ItemTagType.STRING, "scatola");
        Shulklore.add((ChatColor.of("#431BE6") + color("Scatola Perks Include:")));
        Shulklore.add(color("&a+ &7Gain an extra inventory!"));
        Shulklore.add(color("&A+ &bABILITY: &7activate levitating touch!"));
        Shulklore.add(color("&a+ &7Gains extra health while in the end dimension!"));
        Shulklore.add(color("&c- &7Your extra inventory drops into a chest on death!"));
        Shulklore.add(color("&c- &7Becomes immobile for a short duration after using ExtraInventory"));
        Shulklore.add(color("&c- &7Has a phobia of the nether!"));
        ShulkMeta.setLore(Shulklore);
        Shulk.setItemMeta(ShulkMeta);

        ItemStack Feline = new ItemStack(Material.OCELOT_SPAWN_EGG);
        ItemMeta FelineMeta = Feline.getItemMeta();
        List<String> Felinelore = new ArrayList<>();
        FelineMeta.getCustomTagContainer().setCustomTag(Class, ItemTagType.STRING, "Khajiit");
        Felinelore.add((ChatColor.of("#431BE6") + color("Khajiit Perks Include:")));
        Felinelore.add(color("&a+ &7Is naturally faster than its prey.")); //1.5x
        Felinelore.add(color("&a+ &7Increased jumping ability."));
        Felinelore.add(color("&a+ &7Can double jump!"));
        Felinelore.add(color("&c- &7Takes &CDOUBLE &7damage from explosions"));
        Felinelore.add(color("&c- &7Extremely slow in the water!"));
        Felinelore.add(color("&c- &7Is nauseous while wet."));
        FelineMeta.setDisplayName(ChatColor.of("#E6651B") + color("&lKhajiit"));
        FelineMeta.setLore(Felinelore);
        Feline.setItemMeta(FelineMeta);

        classSelector.setItem(3, Enderian);
        classSelector.setItem(4, Merling);
        classSelector.setItem(5, Phantom);
        classSelector.setItem(12, Elytrian);
        classSelector.setItem(13, Blazeborn);
        classSelector.setItem(14, Avian);
        classSelector.setItem(21, Arachnid);
        classSelector.setItem(22, Shulk);
        classSelector.setItem(23, Feline);

        for (int i = 0; i < 27; i++) {
            ItemStack blank = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
            ItemMeta blankm = blank.getItemMeta();
            blankm.setDisplayName(" ");
            blank.setItemMeta(blankm);
            if (classSelector.getItem(i) == null) {
                classSelector.setItem(i, blank);
            }
        }

        p.openInventory(classSelector);
    }

    private final ItemStack CONFIRM = new ItemStack(Material.GREEN_WOOL);
    private final ItemStack DENY = new ItemStack(Material.RED_WOOL);

    private void classConfirmed(Player p) {


        ItemMeta CONFIRMmeta = CONFIRM.getItemMeta();
        CONFIRMmeta.setDisplayName(color("&aCONFIRM"));
        CONFIRM.setItemMeta(CONFIRMmeta);
        classConfirm.setItem(12, CONFIRM);

        ItemMeta DENYmeta = DENY.getItemMeta();
        DENYmeta.setDisplayName(color("&cDENY"));
        DENY.setItemMeta(DENYmeta);
        classConfirm.setItem(14, DENY);

        for (int i = 0; i < 27; i++) {
            ItemStack blank = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
            ItemMeta blankm = blank.getItemMeta();
            blankm.setDisplayName(" ");
            blank.setItemMeta(blankm);
            if (classConfirm.getItem(i) == null) {
                classConfirm.setItem(i, blank);
            }

        }

        if (saveConfirm.get(p.getUniqueId()) != null) {
            ItemStack choose = saveConfirm.get(p.getUniqueId()).clone();
            choose.addUnsafeEnchantment(Enchantment.DURABILITY, 0);
            choose.getItemMeta().addItemFlags(ItemFlag.HIDE_ENCHANTS);
            classConfirm.setItem(13, choose);

        } else return;

        p.openInventory(classConfirm);
    }


    @EventHandler(priority = EventPriority.HIGH)
    public void classSelection(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        ConfigurationSection playerConfig = ClassData.getClassStorage().getConfigurationSection("Players." + p.getUniqueId());

        if (playerConfig != null) {
            if ((playerConfig.get(".Class") == "null")) {
                classSelector(p);
            } else if (!(playerConfig.get(".Class") == "null")) {
                p.sendMessage(color("&e&lWelcome &a&l" + p.getName() + "&e&l, your current class is: " + playerConfig.get(".Class")));
                ClassLevel level = new ClassLevel(p);
                if (level.bossBarEXP()) {
                    String xpGains = color("&f&l" + playerConfig.get(".EXP") + "&b&lXP &7&l/ &f&l" + level.getLevelEXP() + "&b&lXP");
                    double remainingPercent = 1 - (level.getRemainingEXP() / level.getLevelEXP());
                    CraftBossBar expBar = new CraftBossBar(xpGains, BarColor.GREEN, BarStyle.SOLID);
                    expBar.addPlayer(p);
                    expBar.setVisible(true);
                    expBar.setProgress(remainingPercent);

                }
            }
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("origins")) {
            if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
                sender.sendMessage(color("&cReloading all configs, please stand bye."));
                long startTime = System.currentTimeMillis();
                ClassData.reloadClassStorage();
                long totalTime = System.currentTimeMillis() - startTime;
                sender.sendMessage(color("&aReload completed in: " + totalTime + "ms!"));
                return true;
            }
            if (sender instanceof Player) {
                Player p = (Player) sender;
                ConfigurationSection playerConfig = ClassData.getClassStorage().getConfigurationSection("Players." + p.getUniqueId());
                if (playerConfig != null && playerConfig.get(".Class") != "null") {
                    if (!(p.hasPermission("oc.class.change"))) {
                        p.sendMessage(color("&cError class already selected as: " + playerConfig.get(".Class")));
                        return true;
                    }
                }
                classSelector(p);
                return false;
            }
        }
        return false;
    }


    @EventHandler(priority = EventPriority.NORMAL)
    public void noClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        ConfigurationSection playerConfig = ClassData.getClassStorage().getConfigurationSection("Players." + p.getUniqueId());

        try {
            if (e.getInventory() == classSelector) {
                e.setCancelled(true);
                if (e.getCurrentItem() != null || e.getCurrentItem().getType() != Material.BLACK_STAINED_GLASS_PANE) {
                    saveConfirm.put(p.getUniqueId(), e.getCurrentItem());
                    p.sendMessage(saveConfirm.get(p.getUniqueId()).getItemMeta().getDisplayName());
                    classConfirmed(p);
                }
            } else if (e.getClickedInventory() == classConfirm) {
                e.setCancelled(true);
                if (e.getCurrentItem().isSimilar(CONFIRM)) {
                    //logic
                    e.getWhoClicked().closeInventory();
                    if (playerConfig != null) {
                        if (playerConfig.get(".Class") != "null") {
                            if (p.hasPermission("oc.class.change")) {
                                ItemMeta save = saveConfirm.get(p.getUniqueId()).getItemMeta();
                                String tagContainer = save.getCustomTagContainer().getCustomTag(Class, ItemTagType.STRING);
                                playerConfig.set(".Class", tagContainer);
                                ClassData.saveClassStorage();
                                ClassData.getClassStorage().options().copyDefaults(true);
                                p.sendMessage(color("&e&lClass &a&lSuccessfully &e&lChanged to: &a" + tagContainer));
                                EntityPlayer tabClass = TabCreation.classPlayer;
                                tabClass.listName = new ChatComponentText(color("     &3&lSelected Class: &b" + playerConfig.get(".Class")));
                                PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.UPDATE_DISPLAY_NAME, tabClass.getBukkitEntity().getHandle());
                                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
                            } else {
                                p.closeInventory();
                                return;
                            }
                        } else if (playerConfig.get(".Class") == "null") {
                            ItemMeta save = saveConfirm.get(p.getUniqueId()).getItemMeta();
                            String tagContainer = save.getCustomTagContainer().getCustomTag(Class, ItemTagType.STRING);
                            String playerIP = Objects.requireNonNull(p.getAddress()).getHostName();

                            ConfigurationSection playerData = ClassData.getClassStorage().getConfigurationSection("Players." + p.getUniqueId());

                            ClassData.getClassStorage().set(String.valueOf(playerData), null);

                            if (playerData != null) {
                                playerData.set(".Class", tagContainer);
                                playerData.set(".Name", p.getName());
                                playerData.set(".Address", playerIP);
                                playerData.set(".Level", 1);
                                playerData.set(".EXP", 0);
                            }

                            ClassData.saveClassStorage();
                            ClassData.getClassStorage().options().copyDefaults(true);

                        } else return;
                    }
                } else if (e.getCurrentItem().isSimilar(DENY)) {
                    //logic
                    e.getWhoClicked().closeInventory();
                    e.getWhoClicked().openInventory(classSelector);
                }
            }
        } catch (NullPointerException e1) {
            return;
        }
    }

    @EventHandler
    public void nonNullClass(PlayerMoveEvent e) {
        if ((ClassData.getClassStorage().get("Players." + e.getPlayer().getUniqueId() + ".Class") == "null")) {
            e.setCancelled(true);
            Bukkit.dispatchCommand(e.getPlayer(), "origins");
        }
    }


}
