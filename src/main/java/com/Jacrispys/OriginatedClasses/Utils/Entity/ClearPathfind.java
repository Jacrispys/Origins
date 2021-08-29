package com.Jacrispys.OriginatedClasses.Utils.Entity;

import java.lang.reflect.Field;

public class ClearPathfind {

    public static Object getPrivateField(String fieldName, Class clazz, Object object) {
        Field field;
        Object o = null;


        try {
            field = clazz.getDeclaredField(fieldName);

            field.setAccessible(true);

            o = field.get(object);

        } catch(NoSuchFieldException | IllegalAccessException exception) {
            exception.printStackTrace();}

        return o;
    }
}
