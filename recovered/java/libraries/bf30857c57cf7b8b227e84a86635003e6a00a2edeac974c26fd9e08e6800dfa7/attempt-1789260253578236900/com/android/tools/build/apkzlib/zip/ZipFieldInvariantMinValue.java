/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.zip;

import com.android.tools.build.apkzlib.zip.ZipFieldInvariant;

class ZipFieldInvariantMinValue
implements ZipFieldInvariant {
    private final long min;

    ZipFieldInvariantMinValue(long min2) {
        this.min = min2;
    }

    @Override
    public boolean isValid(long value) {
        return value >= this.min;
    }

    @Override
    public String getName() {
        return "Min value " + this.min;
    }
}

