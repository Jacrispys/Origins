package com.Jacrispys.OriginatedClasses.Utils;


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

    void bossBarEXP(boolean enabled);

    void vanillaBarEXP(boolean enabled);

    boolean SidebarEXP();

    void tabListEXP(boolean enabled);

    void update();




}
