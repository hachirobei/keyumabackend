package com.keyuma.managementsystem.helper;

public class DebugUtil {
    public static void dd(Object obj) {
        System.out.println(obj);
        throw new RuntimeException("Debugging");
    }
}