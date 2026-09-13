/*
 * Decompiled with CFR 0.152.
 */
package com.android.apksig.apk;

import com.android.apksig.apk.MinSdkVersionException;

public class CodenameMinSdkVersionException
extends MinSdkVersionException {
    private static final long serialVersionUID = 1L;
    private final String mCodename;

    public CodenameMinSdkVersionException(String message, String codename) {
        super(message);
        this.mCodename = codename;
    }

    public String getCodename() {
        return this.mCodename;
    }
}

