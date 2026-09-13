/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib.jdwp.packets;

import com.android.ddmlib.jdwp.JdwpPayload;
import com.android.ddmlib.jdwp.JdwpProtocol;
import java.nio.ByteBuffer;

public class IdSizesReply
extends JdwpPayload {
    public int fieldIDSize;
    public int methodIDSize;
    public int objectIDSize;
    public int refTypeIDSize;
    public int frameIDSize;

    @Override
    public void parse(ByteBuffer buffer, JdwpProtocol protocol) {
        this.fieldIDSize = buffer.getInt();
        this.methodIDSize = buffer.getInt();
        this.objectIDSize = buffer.getInt();
        this.refTypeIDSize = buffer.getInt();
        this.frameIDSize = buffer.getInt();
    }
}

