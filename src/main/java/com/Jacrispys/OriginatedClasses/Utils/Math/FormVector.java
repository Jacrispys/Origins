package com.Jacrispys.OriginatedClasses.Utils.Math;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class FormVector {

    public static Vector genVec(Location a, Location b, boolean normalize) {
        double dX = a.getX() - b.getX();
        double dY = a.getY() - b.getY();
        double dZ = a.getZ() - b.getZ();
        double yaw = Math.atan2(dZ, dX);
        double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;
        double x = Math.sin(pitch) * Math.cos(yaw);
        double y = Math.sin(pitch) * Math.sin(yaw);
        double z = Math.cos(pitch);

        Vector finalVector = new Vector(x ,y ,z);

        if(normalize) {
            finalVector.normalize();
        }


        return finalVector;
    }
}
