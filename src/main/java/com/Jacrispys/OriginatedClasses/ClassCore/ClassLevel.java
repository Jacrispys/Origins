package com.Jacrispys.OriginatedClasses.ClassCore;

import com.Jacrispys.OriginatedClasses.Files.ClassData;
import com.Jacrispys.OriginatedClasses.OriginatedClassesMain;
import com.Jacrispys.OriginatedClasses.Utils.ClassUtils.ClassLeveling;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.boss.KeyedBossBar;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.Objects;

public class ClassLevel implements ClassLeveling {

    private final Player p;

    private final ConfigurationSection data;



    private final Plugin plugin;

    public ClassLevel(Player player) throws NullPointerException {

        this.p = player;
        this.data = ClassData.getClassStorage().getConfigurationSection("Players." + p.getUniqueId());

        this.plugin = OriginatedClassesMain.getPlugin();

    }

    protected double scalingEXP(int n) throws NumberFormatException {
        return Math.round(((Math.pow(n, n + 1)) * 100) / ((Math.pow(n, n - 1)) / n));
    }

    @Override @Nonnull
    public String getLevel() {
        try {
            data.get(".Level");
            return Objects.requireNonNull(data.get(".Level")).toString();

        } catch (NullPointerException e1) {
            if(data.get(".Class") != "null") {
                data.set(".Level", "1");
                Validate.notNull(data.get(".Level"));
                return Objects.requireNonNull(data.get(".Level")).toString();
            }
            data.set(".Level", "0");
            return Objects.requireNonNull(data.get(".Level")).toString();
        }
    }

    @Override
    public void setLevel(int Level, boolean keepEXP) {
        @Nullable Object level =  data.get(".Level");
        @Nullable Object EXP =  data.get(".EXP");
        if(level == "0" || level == null) {
            throw new NullPointerException("Player cannot have a level of 0 or have no Class!");
        } else {
            data.set(".Level", level);
        }
        if(EXP == null) {
            throw new NullPointerException("EXP cannot be Null!");
        } else if(keepEXP) {
            data.set(".EXP", EXP);
        } else { data.set(".EXP", "0"); }
    }

    @Override
    public void addLevel(int AMT, boolean keepEXP) throws NumberFormatException {
        int level =  Integer.parseInt(String.valueOf(data.get(".Level")));
        int EXP =  Integer.parseInt(String.valueOf(data.get(".EXP")));
        if(level == 0 || data.get(".Level") == null) {
            throw new NullPointerException("Player cannot have a level of 0 or have no Class!");
        } else {
            data.set(".Level", level + AMT);
        }
        if(data.get(".EXP") == null) {
            throw new NullPointerException("EXP cannot be Null!");
        } else if(keepEXP) {
            data.set(".EXP", EXP);
        } else { data.set(".EXP", "0"); }

    }

    @Override
    public void removeLevel(int AMT, boolean keepEXP) {
        int level =  Integer.parseInt(String.valueOf(data.get(".Level")));
        int EXP =  Integer.parseInt(String.valueOf(data.get(".EXP")));
        if(level == 0 || data.get(".Level") == null) {
            throw new NullPointerException("Player cannot have a level of 0 or have no Class!");
        } else {
            if((level - AMT) < 1 ) {
                throw new NullPointerException("Level cannot be less than 1");
            }
            data.set(".Level", level - AMT);
        }
        if(data.get( ".EXP") == null) {
            throw new NullPointerException("EXP cannot be Null!");
        } else if(keepEXP) {
            data.set( ".EXP", EXP);
        } else { data.set(".EXP", "0"); }
    }

    @Override
    public double getRemainingEXP() {
        int EXP =  Integer.parseInt(String.valueOf(data.get(".EXP")));
        if(data.get(".EXP") == null) {
            throw new NullPointerException("EXP cannot be Null!");
        } else {
            return getLevelEXP() - EXP;
        }
    }

    @Override
    public void setRemainingEXP(int i) {
        if(data.get(".EXP") == null) {
            throw new NullPointerException("EXP cannot be Null!");
        } else {
            data.set(".EXP", i);
        }
    }

    @Override
    public int getEXP() {
        int EXP =  Integer.parseInt(String.valueOf(data.get(".EXP")));
        if(data.get(".EXP") == null) {
            throw new NullPointerException("EXP cannot be Null!");
        } else {
            return EXP;
        }

    }

    @Override
    public void setEXP(int EXP) {
        if(data.get(".EXP") == null) {
            throw new NullPointerException("EXP cannot be Null!");
        } else {
            data.set(".EXP", EXP);
        }
    }

    @Override
    public double getLevelEXP() {
        int level =  Integer.parseInt(String.valueOf(data.get(".Level")));
        return scalingEXP(level);
    }

    @Override
    public boolean bossBarEXP() {
        return  Objects.equals(plugin.getConfig().get("bossBar-EXP"), true);
    }

    @Override
    public boolean vanillaBarEXP() {
        return Objects.equals(plugin.getConfig().get("vanillaXP-Override"), true);
    }

    @Override
    public boolean SidebarEXP() {
        return Objects.equals(plugin.getConfig().get("sideBar-EXP"), true);
    }

    @Override
    public void tabListEXP(boolean enabled) {

    }

    @Override
    public void update(Player player) {
        if(bossBarEXP()) {
            for (@NotNull Iterator<KeyedBossBar> it = Bukkit.getBossBars(); it.hasNext(); ) {
                KeyedBossBar bossBar = it.next();
                if(bossBar.getPlayers().contains(player)) {
                    bossBar.setTitle(bossBar.getTitle());
                }
            }
        }

    }
}
