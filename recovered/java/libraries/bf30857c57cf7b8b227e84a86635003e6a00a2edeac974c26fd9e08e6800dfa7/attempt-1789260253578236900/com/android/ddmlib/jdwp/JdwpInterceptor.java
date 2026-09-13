/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib.jdwp;

import com.android.ddmlib.JdwpPacket;
import com.android.ddmlib.jdwp.JdwpAgent;

public abstract class JdwpInterceptor {
    public abstract JdwpPacket intercept(JdwpAgent var1, JdwpPacket var2);
}

