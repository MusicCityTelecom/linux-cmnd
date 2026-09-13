/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.zip;

import com.android.tools.build.apkzlib.zip.ZipFieldInvariant;

class ZipFieldInvariantNonNegative
implements ZipFieldInvariant {
    ZipFieldInvariantNonNegative() {
    }

    @Override
    public boolean isValid(long value) {
        return value >= 0L;
    }

    @Override
    public String getName() {
        return "Is positive";
    }
}

