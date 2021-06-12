package com.Jacrispys.OriginatedClasses.FirstJoin;

import com.Jacrispys.OriginatedClasses.Files.ClassData;
import com.Jacrispys.OriginatedClasses.OriginatedClassesMain;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.tags.ItemTagType;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static com.Jacrispys.OriginatedClasses.Utils.Chat.chat;

public class ClassSelection implements Listener, CommandExecutor {

    private Plugin plugin = OriginatedClassesMain.getPlugin();

    public ClassSelection(OriginatedClassesMain plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
        plugin.getCommand("origins").setExecutor(this);
    }

    private final Inventory classSelector = Bukkit.createInventory(null, 27, chat("&3&lSelect Your Starter Class!"));

    private final Inventory classConfirm = Bukkit.createInventory(null, 27, chat("&c&lAre you sure?"));


    private final HashMap<UUID, ItemStack> saveConfirm = new HashMap<>();

    NamespacedKey Class = new NamespacedKey(plugin, "Class");

    private void classSelector(Player p) {
        ItemStack Enderian = new ItemStack(Material.ENDER_PEARL);
        ItemMeta EnderianMeta = Enderian.getItemMeta();
        List<String> Enderianlore = new ArrayList<>();
        EnderianMeta.getCustomTagContainer().setCustomTag(Class, ItemTagType.STRING, "enderhusk");
        Enderianlore.add((ChatColor.of("#431BE6") + chat("Enderhusk Perks Include:")));
        Enderianlore.add(chat("&a+ &7Teleports after holding" + ChatColor.of("#1FEE96") + " SHIFT &7for 3 seconds!"));
        Enderianlore.add(chat("&a+ &7Has &a&lDouble &cHealth"));
        Enderianlore.add(chat("&c- &9Water &7damages you!"));
        Enderianlore.add(chat("&c- &7Cannot pick up" + ChatColor.of("#E6961B") +  " Pumpkins!"));
        EnderianMeta.setDisplayName(ChatColor.of("#1FEE96") + chat("&lEnderhusk"));
        EnderianMeta.setLore(Enderianlore);
        Enderian.setItemMeta(EnderianMeta);

        ItemStack Merling = new ItemStack(Material.SEA_LANTERN);
        ItemMeta MerlingMeta = Merling.getItemMeta();
        List<String> Merlinglore = new ArrayList<>();
        MerlingMeta.getCustomTagContainer().setCustomTag(Class, ItemTagType.STRING, "atlantian");
        Merlinglore.add((ChatColor.of("#431BE6") + chat("Atlantian Perks Include:")));
        Merlinglore.add(chat("&a+ &7Can &bBreathe &7Under Water"));
        Merlinglore.add(chat("&a+ &7Natural Depth Strider"));
        Merlinglore.add(chat("&a+ &7Has a &1Waterbucket &7with &c∞ &7uses!"));
        Merlinglore.add(chat("&c- &7Has Reversed drowning"));
        Merlinglore.add(chat("&c- &7Has Decreased health"));
        MerlingMeta.setDisplayName(ChatColor.of("#50D4BC") + chat("&lAtlantian"));
        MerlingMeta.setLore(Merlinglore);
        Merling.setItemMeta(MerlingMeta);

        ItemStack Phantom = new ItemStack(Material.PHANTOM_MEMBRANE);
        ItemMeta PhantomMeta = Phantom.getItemMeta();
        List<String> Phantomlore = new ArrayList<>();
        PhantomMeta.getCustomTagContainer().setCustomTag(Class, ItemTagType.STRING, "shade");
        Phantomlore.add((ChatColor.of("#431BE6") + chat("Shade Perks Include:")));
        Phantomlore.add(chat("&a+ &7Can Go invisible while shifting!"));
        Phantomlore.add(chat("&a+ &7Takes no fall damage"));
        Phantomlore.add(chat("&a+ &7Access to /roam command"));
        Phantomlore.add(chat("&c- &7Randomly loses hunger"));
        Phantomlore.add(chat("&f&l☯ &7Loses health during the day, but gains health during the night"));
        PhantomMeta.setDisplayName(ChatColor.of("#DCDCCA") + chat("&lShade"));
        PhantomMeta.setLore(Phantomlore);
        Phantom.setItemMeta(PhantomMeta);

        ItemStack Elytrian = new ItemStack(Material.ELYTRA);
        ItemMeta ElytrianMeta = Elytrian.getItemMeta();
        List<String> Elytrianlore = new ArrayList<>();
        ElytrianMeta.getCustomTagContainer().setCustomTag(Class, ItemTagType.STRING, "strix");
        Elytrianlore.add((ChatColor.of("#431BE6") + chat("Strix Perks Include:")));
        Elytrianlore.add(chat("&a+ &7Can fly for a short amount of time"));
        Elytrianlore.add(chat("&a+ &7Access to /launch command (results may vary)"));
        Elytrianlore.add(chat("&a+ &7Immune to suffocation"));
        Elytrianlore.add(chat("&c- &7Has weakness based off flying time"));
        Elytrianlore.add(chat("&c- &7Takes double fall damage"));
        ElytrianMeta.setDisplayName(ChatColor.of("#A1EEED") + chat("&lStrix"));
        ElytrianMeta.setLore(Elytrianlore);
        Elytrian.setItemMeta(ElytrianMeta);

        ItemStack Blazeborn = new ItemStack(Material.BLAZE_ROD);
        ItemMeta BlazebornMeta = Blazeborn.getItemMeta();
        List<String> Blazebornlore = new ArrayList<>();
        BlazebornMeta.getCustomTagContainer().setCustomTag(Class, ItemTagType.STRING, "ignerian");
        Blazebornlore.add((ChatColor.of("#431BE6") + chat("Ignerian Perks Include:")));
        Blazebornlore.add(chat("&a+ &7Is immune to fire damage"));
        Blazebornlore.add(chat("&a+ &7Can float while shifting!"));
        Blazebornlore.add(chat("&a+ &7Takes no fall damage!"));
        Blazebornlore.add(chat("&a+ &7 Gains &4Regeneration &7while burning!"));
        Blazebornlore.add(chat("&c- &7While in 'float' mode takes 1.25x damage!"));
        Blazebornlore.add(chat("&c- &7While in 'float' mode takes damage from rain"));
        Blazebornlore.add(chat("&c- &7Takes damage while in water"));
        BlazebornMeta.setDisplayName(ChatColor.of("#E77D3D") + chat("&lIgnerian"));
        BlazebornMeta.setLore(Blazebornlore);
        Blazeborn.setItemMeta(BlazebornMeta);

        ItemStack Avian = new ItemStack(Material.FEATHER);
        ItemMeta AvianMeta = Avian.getItemMeta();
        AvianMeta.setDisplayName(ChatColor.of("#713DE7") + chat("&lGargoyle"));
        List<String> Avainlore = new ArrayList<>();
        AvianMeta.getCustomTagContainer().setCustomTag(Class, ItemTagType.STRING, "Gargoyle");
        Avainlore.add((ChatColor.of("#431BE6") + chat("Gargoyle Perks Include:")));
        Avainlore.add(chat("&a+ &7Become stone and gain &8RESISTANCE &7 after not moving for 3+ seconds!"));
        Avainlore.add(chat("&a+ &7Your fist deals damage scaling to your xp!"));
        Avainlore.add(chat("&d??? &7Drops feathers and stone on death"));
        Avainlore.add(chat("&c- &7Deals 50% less damage with swords/axes"));
        Avainlore.add(chat("&c- &7Gains slowness for a short time after becoming stone"));
        AvianMeta.setLore(Avainlore);
        Avian.setItemMeta(AvianMeta);

        ItemStack Arachnid = new ItemStack(Material.COBWEB);
        ItemMeta ArachnidMeta = Arachnid.getItemMeta();
        ArachnidMeta.setDisplayName(ChatColor.of("#491D81") + chat("&lArachne"));
        List<String> Arachnidlore = new ArrayList<>();
        ArachnidMeta.getCustomTagContainer().setCustomTag(Class, ItemTagType.STRING, "arachne");
        Arachnidlore.add((ChatColor.of("#431BE6") + chat("Arachne Perks Include:")));
        Arachnidlore.add(chat("&a+ &7Can Climb walls (kinda)"));
        Arachnidlore.add(chat("&a+ &7Traps Enemies in webs!"));
        Arachnidlore.add(chat("&c- &7Moves slightly slower"));
        ArachnidMeta.setLore(Arachnidlore);
        Arachnid.setItemMeta(ArachnidMeta);

        ItemStack Shulk = new ItemStack(Material.SHULKER_BOX);
        ItemMeta ShulkMeta = Shulk.getItemMeta();
        ShulkMeta.setDisplayName(ChatColor.of("#BB6CDA") + chat("&lScatola"));
        List<String> Shulklore = new ArrayList<>();
        ShulkMeta.getCustomTagContainer().setCustomTag(Class, ItemTagType.STRING, "scatola");
        Shulklore.add((ChatColor.of("#431BE6") + chat("Scatola Perks Include:")));
        Shulklore.add(chat("&a+ &7Gain an extra inventory!"));
        Shulklore.add(chat("&A+ &bABILITY: &7activate levitating touch!"));
        Shulklore.add(chat("&a+ &7Gains extra health while in the end dimension!"));
        Shulklore.add(chat("&c- &7Your extra inventory drops into a chest on death!"));
        Shulklore.add(chat("&c- &7Becomes immobile for a short duration after using ExtraInventory"));
        Shulklore.add(chat("&c- &7Has a phobia of the nether!"));
        ShulkMeta.setLore(Shulklore);
        Shulk.setItemMeta(ShulkMeta);

        ItemStack Feline = new ItemStack(Material.OCELOT_SPAWN_EGG);
        ItemMeta FelineMeta = Feline.getItemMeta();
        List<String> Felinelore = new ArrayList<>();
        FelineMeta.getCustomTagContainer().setCustomTag(Class, ItemTagType.STRING, "Khajiit");
        Felinelore.add((ChatColor.of("#431BE6") + chat("Khajiit Perks Include:")));
        Felinelore.add(chat("&a+ &7Is naturally faster than its prey.")); //1.5x
        Felinelore.add(chat("&a+ &7Increased jumping ability."));
        Felinelore.add(chat("&a+ &7Can double jump!"));
        Felinelore.add(chat("&c- &7Takes &CDOUBLE &7damage from explosions"));
        Felinelore.add(chat("&c- &7Extremely slow in the water!"));
        Felinelore.add(chat("&c- &7Is nauseous while wet."));
        FelineMeta.setDisplayName(ChatColor.of("#E6651B") + chat("&lKhajiit"));
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

        for(int i = 0; i < 27; i++) {
            ItemStack blank = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
            ItemMeta blankm = blank.getItemMeta();
            blankm.setDisplayName(" ");
            blank.setItemMeta(blankm);
            if(classSelector.getItem(i) == null) {
                classSelector.setItem(i, blank);
            }
        }

            p.openInventory(classSelector);
    }

    private ItemStack CONFIRM = new ItemStack(Material.GREEN_WOOL);
    private ItemStack DENY = new ItemStack(Material.RED_WOOL);
    private void classConfirmed (Player p) {
        ItemMeta CONFIRMmeta = CONFIRM.getItemMeta();
        CONFIRMmeta.setDisplayName(chat("&aCONFIRM"));
        CONFIRM.setItemMeta(CONFIRMmeta);
        classConfirm.setItem(12, CONFIRM);

        ItemMeta DENYmeta = DENY.getItemMeta();
        DENYmeta.setDisplayName(chat("&cDENY"));
        DENY.setItemMeta(DENYmeta);
        classConfirm.setItem(14, DENY);

        for(int i = 0; i < 27; i++) {
            ItemStack blank = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
            ItemMeta blankm = blank.getItemMeta();
            blankm.setDisplayName(" ");
            blank.setItemMeta(blankm);
            if(classConfirm.getItem(i) == null) {
                classConfirm.setItem(i, blank);
            }

        }

        if(saveConfirm.get(p.getUniqueId()) != null) {
            ItemStack choose = saveConfirm.get(p.getUniqueId()).clone();
            choose.addUnsafeEnchantment(Enchantment.DURABILITY, 0);
            choose.getItemMeta().addItemFlags(ItemFlag.HIDE_ENCHANTS);
            classConfirm.setItem(13, choose);

        } else return;

        p.openInventory(classConfirm);
    }



    @EventHandler
    public void classSelection (PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if(!(p.hasPlayedBefore()) || !(ClassData.getClassStorage().contains(p.getUniqueId() + " Class"))) {
            classSelector(p);
        } else if(p.hasPlayedBefore() && ClassData.getClassStorage().contains(p.getUniqueId() + " Class")) {
            p.sendMessage(chat("&e&lWelcome &a&l" + p.getName() + "&e&l, your current class is: " + ClassData.getClassStorage().get(p.getUniqueId() + " Class")));
        }
    }

    @Override
    public boolean onCommand( CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        if(ClassData.getClassStorage().contains(p.getUniqueId() + " Class")) {
            if(!(p.hasPermission("oc.class.change"))) {
                p.sendMessage(chat("&cError class already selected as: " + ClassData.getClassStorage().get(p.getUniqueId() + " Class")));
                return true;
            }
        }
        classSelector(p);
        return false;
    }


    @EventHandler
    public void noClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if(e.getInventory() == classSelector) {
            e.setCancelled(true);
            if(e.getCurrentItem() != null || e.getCurrentItem().getType() != Material.BLACK_STAINED_GLASS_PANE) {
                saveConfirm.put(p.getUniqueId(), e.getCurrentItem());
                p.sendMessage(saveConfirm.get(p.getUniqueId()).getItemMeta().getDisplayName());
                classConfirmed(p);
            }
        } else if(e.getClickedInventory() == classConfirm) {
            e.setCancelled(true);
            if(e.getCurrentItem().isSimilar(CONFIRM)) {
                //logic
                e.getWhoClicked().closeInventory();
                if(ClassData.getClassStorage().get(String.valueOf(p.getUniqueId())) != null) {
                    if(p.hasPermission("oc.class.change")) {
                        ItemMeta save = saveConfirm.get(p.getUniqueId()).getItemMeta();
                        String tagContainer = save.getCustomTagContainer().getCustomTag(Class, ItemTagType.STRING);
                        ClassData.getClassStorage().set(p.getUniqueId() + " Class", tagContainer );
                        ClassData.saveClassStorage();
                        ClassData.getClassStorage().options().copyDefaults(true);
                    } else {
                        p.closeInventory();
                        return;
                    }
                } else if(ClassData.getClassStorage().get(String.valueOf(p.getUniqueId())) == null) {
                    ItemMeta save = saveConfirm.get(p.getUniqueId()).getItemMeta();
                    String tagContainer = save.getCustomTagContainer().getCustomTag(Class, ItemTagType.STRING);
                    ClassData.getClassStorage().addDefault(p.getUniqueId() + " Class", tagContainer);
                    ClassData.saveClassStorage();
                    ClassData.getClassStorage().options().copyDefaults(true);
                } else return;
            } else if(e.getCurrentItem().isSimilar(DENY)) {
                //logic
                e.getWhoClicked().closeInventory();
                e.getWhoClicked().openInventory(classSelector);
            }
        }
    }
}
