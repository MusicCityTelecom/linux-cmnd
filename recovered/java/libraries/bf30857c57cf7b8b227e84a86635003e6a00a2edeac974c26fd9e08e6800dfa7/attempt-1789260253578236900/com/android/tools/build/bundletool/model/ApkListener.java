/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.android.bundle.Commands;

public class ApkListener {
    public static final ApkListener NO_OP = new ApkListener(){};

    public void onApkFinalized(Commands.ApkDescription apkDesc) {
    }
}

