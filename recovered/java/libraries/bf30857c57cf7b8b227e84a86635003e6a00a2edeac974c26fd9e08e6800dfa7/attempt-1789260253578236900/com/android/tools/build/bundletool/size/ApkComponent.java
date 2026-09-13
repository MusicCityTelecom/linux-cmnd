/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.size;

public enum ApkComponent {
    DEX,
    RESOURCES,
    ASSETS,
    NATIVE_LIBS,
    OTHER;


    public static ApkComponent fromEntryName(String entryName) {
        if (entryName.startsWith("res/") || entryName.equals("resources.arsc")) {
            return RESOURCES;
        }
        if (entryName.startsWith("lib/")) {
            return NATIVE_LIBS;
        }
        if (entryName.endsWith(".dex")) {
            return DEX;
        }
        if (entryName.startsWith("assets/")) {
            return ASSETS;
        }
        return OTHER;
    }
}

