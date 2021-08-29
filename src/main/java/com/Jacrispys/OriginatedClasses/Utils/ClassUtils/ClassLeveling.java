package com.Jacrispys.OriginatedClasses.Utils.ClassUtils;


import org.bukkit.entity.Player;

public interface ClassLeveling  {

    String getLevel();

    void setLevel(int level, boolean keepExp);

    void addLevel(int AMT, boolean keepEXP);

    void removeLevel(int AMT, boolean keepEXP);

    double getRemainingEXP();

    void setRemainingEXP(int i);

    int getEXP();

    void setEXP(int EXP);

    double getLevelEXP();

    boolean bossBarEXP();

    boolean vanillaBarEXP();

    boolean SidebarEXP();

    void tabListEXP(boolean enabled);


    void update(Player player);
}
