package com.Jacrispys.OriginatedClasses.API;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.v1_16_R3.*;

public class ServerNPC extends EntityHuman {


    public ServerNPC(World world, BlockPosition blockposition, float f, GameProfile gameprofile) {
        super(world, blockposition, f, gameprofile);


    }


    @Override
    public boolean isSpectator() {
        return false;
    }

    @Override
    public boolean isCreative() {
        return false;
    }
}
