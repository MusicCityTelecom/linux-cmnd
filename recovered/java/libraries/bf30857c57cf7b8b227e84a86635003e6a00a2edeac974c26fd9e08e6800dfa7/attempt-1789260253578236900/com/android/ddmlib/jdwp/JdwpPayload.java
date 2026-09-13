/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib.jdwp;

import com.android.ddmlib.jdwp.JdwpProtocol;
import java.nio.ByteBuffer;

public abstract class JdwpPayload {
    public abstract void parse(ByteBuffer var1, JdwpProtocol var2);
}

