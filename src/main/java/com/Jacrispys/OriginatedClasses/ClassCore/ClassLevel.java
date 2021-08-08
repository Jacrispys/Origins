package com.Jacrispys.OriginatedClasses.ClassCore;

import com.Jacrispys.OriginatedClasses.Files.ClassData;
import com.Jacrispys.OriginatedClasses.Utils.ClassLeveling;
import org.apache.commons.lang.Validate;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_16_R3.boss.CraftBossBar;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Objects;

import static com.Jacrispys.OriginatedClasses.Utils.Chat.chat;

public class ClassLevel implements ClassLeveling {

    private final Player p;

    private final FileConfiguration data;

    private final String xpGains;

    private CraftBossBar ExpBar;

    public ClassLevel(Player player) throws NullPointerException {

        this.p = player;
        this.data = ClassData.getClassStorage();
        this.xpGains = chat("&f&l" + data.get(p.getUniqueId() + ".EXP") + "&b&lXP &7&l/ &f&l" + getLevelEXP() + "&b&lXP");


    }

    protected double scalingEXP(int n) throws NumberFormatException {
        return Math.round(((Math.pow(n, n + 1)) * 100) / ((Math.pow(n, n - 1)) / n));
    }

    @Override @Nonnull
    public String getLevel() {
        try {
            data.get(p.getUniqueId() + ".Level");
            return Objects.requireNonNull(data.get(p.getUniqueId() + ".Level")).toString();

        } catch (NullPointerException e1) {
            if(data.get(p.getUniqueId() + ".Class") != "null") {
                data.set(p.getUniqueId() + ".Level", "1");
                Validate.notNull(data.get(p.getUniqueId() + ".Level"));
                return Objects.requireNonNull(data.get(p.getUniqueId() + ".Level")).toString();
            }
            data.set(p.getUniqueId() + ".Level", "0");
            return Objects.requireNonNull(data.get(p.getUniqueId() + ".Level")).toString();
        }
    }

    @Override
    public void setLevel(int Level, boolean keepEXP) {
        @Nullable Object level =  data.get(p.getUniqueId() + ".Level");
        @Nullable Object EXP =  data.get(p.getUniqueId() + ".EXP");
        if(level == "0" || level == null) {
            throw new NullPointerException("Player cannot have a level of 0 or have no Class!");
        } else {
            data.set(p.getUniqueId() + ".Level", level);
        }
        if(EXP == null) {
            throw new NullPointerException("EXP cannot be Null!");
        } else if(keepEXP) {
            data.set(p.getUniqueId() + ".EXP", EXP);
        } else { data.set(p.getUniqueId() + ".EXP", "0"); }
    }

    @Override
    public void addLevel(int AMT, boolean keepEXP) throws NumberFormatException {
        int level =  Integer.parseInt(String.valueOf(data.get(p.getUniqueId() + ".Level")));
        int EXP =  Integer.parseInt(String.valueOf(data.get(p.getUniqueId() + ".EXP")));
        if(level == 0 || data.get(p.getUniqueId() + ".Level") == null) {
            throw new NullPointerException("Player cannot have a level of 0 or have no Class!");
        } else {
            data.set(p.getUniqueId() + ".Level", level + AMT);
        }
        if(data.get(p.getUniqueId() + ".EXP") == null) {
            throw new NullPointerException("EXP cannot be Null!");
        } else if(keepEXP) {
            data.set(p.getUniqueId() + ".EXP", EXP);
        } else { data.set(p.getUniqueId() + ".EXP", "0"); }

    }

    @Override
    public void removeLevel(int AMT, boolean keepEXP) {
        int level =  Integer.parseInt(String.valueOf(data.get(p.getUniqueId() + ".Level")));
        int EXP =  Integer.parseInt(String.valueOf(data.get(p.getUniqueId() + ".EXP")));
        if(level == 0 || data.get(p.getUniqueId() + ".Level") == null) {
            throw new NullPointerException("Player cannot have a level of 0 or have no Class!");
        } else {
            if((level - AMT) < 1 ) {
                throw new NullPointerException("Level cannot be less than 1");
            }
            data.set(p.getUniqueId() + ".Level", level - AMT);
        }
        if(data.get(p.getUniqueId() + ".EXP") == null) {
            throw new NullPointerException("EXP cannot be Null!");
        } else if(keepEXP) {
            data.set(p.getUniqueId() + ".EXP", EXP);
        } else { data.set(p.getUniqueId() + ".EXP", "0"); }
    }

    @Override
    public double getRemainingEXP() {
        int EXP =  Integer.parseInt(String.valueOf(data.get(p.getUniqueId() + ".EXP")));
        if(data.get(p.getUniqueId() + ".EXP") == null) {
            throw new NullPointerException("EXP cannot be Null!");
        } else {
            return getLevelEXP() - EXP;
        }
    }

    @Override
    public void setRemainingEXP(int i) {
        if(data.get(p.getUniqueId() + ".EXP") == null) {
            throw new NullPointerException("EXP cannot be Null!");
        } else {
            data.set(p.getUniqueId() + ".EXP", i);
        }
    }

    @Override
    public int getEXP() {
        int EXP =  Integer.parseInt(String.valueOf(data.get(p.getUniqueId() + ".EXP")));
        if(data.get(p.getUniqueId() + ".EXP") == null) {
            throw new NullPointerException("EXP cannot be Null!");
        } else {
            return EXP;
        }

    }

    @Override
    public void setEXP(int EXP) {
        if(data.get(p.getUniqueId() + ".EXP") == null) {
            throw new NullPointerException("EXP cannot be Null!");
        } else {
            data.set(p.getUniqueId() + ".EXP", EXP);
        }
    }

    @Override
    public double getLevelEXP() {
        int level =  Integer.parseInt(String.valueOf(data.get(p.getUniqueId() + ".Level")));
        return scalingEXP(level);
    }

    @Override
    public void bossBarEXP(boolean enabled) {
        if(enabled) {
            double remainingPercent =  1 - (getRemainingEXP() / getLevelEXP());
            this.ExpBar = new CraftBossBar(xpGains, BarColor.GREEN, BarStyle.SOLID);
            ExpBar.addPlayer(p);
            ExpBar.setVisible(true);
            ExpBar.setProgress(remainingPercent);

        }
    }

    @Override
    public void vanillaBarEXP(boolean enabled) {

    }

    @Override
    public boolean SidebarEXP() {
        return true;
    }

    @Override
    public void tabListEXP(boolean enabled) {

    }

    @Override
    public void update() {
        if(this.ExpBar != null) {
            double remainingPercent =  1 - (getRemainingEXP() / getLevelEXP());
            ExpBar.setProgress(remainingPercent);
            ExpBar.setTitle(xpGains);
        }

    }
}
