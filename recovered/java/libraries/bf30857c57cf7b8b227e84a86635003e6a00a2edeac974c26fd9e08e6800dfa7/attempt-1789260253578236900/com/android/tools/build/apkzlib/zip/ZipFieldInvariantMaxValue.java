/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.zip;

import com.android.tools.build.apkzlib.zip.ZipFieldInvariant;

class ZipFieldInvariantMaxValue
implements ZipFieldInvariant {
    private final long max;

    ZipFieldInvariantMaxValue(long max) {
        this.max = max;
    }

    @Override
    public boolean isValid(long value) {
        return value <= this.max;
    }

    @Override
    public String getName() {
        return "Maximum value " + this.max;
    }
}

