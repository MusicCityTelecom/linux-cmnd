/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.zip;

public enum DataDescriptorType {
    NO_DATA_DESCRIPTOR(0),
    DATA_DESCRIPTOR_WITHOUT_SIGNATURE(12),
    DATA_DESCRIPTOR_WITH_SIGNATURE(16);

    public int size;

    private DataDescriptorType(int size) {
        this.size = size;
    }
}

