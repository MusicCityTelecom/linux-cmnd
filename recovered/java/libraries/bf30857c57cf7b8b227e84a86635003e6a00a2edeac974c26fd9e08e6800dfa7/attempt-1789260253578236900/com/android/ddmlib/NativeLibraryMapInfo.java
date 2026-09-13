/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib;

public final class NativeLibraryMapInfo {
    private long mStartAddr;
    private long mEndAddr;
    private String mLibrary;

    NativeLibraryMapInfo(long startAddr, long endAddr, String library) {
        this.mStartAddr = startAddr;
        this.mEndAddr = endAddr;
        this.mLibrary = library;
    }

    public String getLibraryName() {
        return this.mLibrary;
    }

    public long getStartAddress() {
        return this.mStartAddr;
    }

    public long getEndAddress() {
        return this.mEndAddr;
    }

    public boolean isWithinLibrary(long address) {
        return address >= this.mStartAddr && address <= this.mEndAddr;
    }
}

