/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.zip.compress;

import java.io.IOException;

public class Zip64NotSupportedException
extends IOException {
    public Zip64NotSupportedException(String message) {
        super(message);
    }
}

