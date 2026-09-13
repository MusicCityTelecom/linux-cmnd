/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib.jdwp;

import com.android.ddmlib.JdwpPacket;
import com.android.ddmlib.jdwp.JdwpAgent;
import com.android.ddmlib.jdwp.JdwpInterceptor;
import com.android.ddmlib.jdwp.packets.IdSizesReply;
import com.google.common.base.Charsets;
import java.nio.ByteBuffer;

public class JdwpProtocol {
    private IdSizesReply mIdSizes;

    public long readObjectId(ByteBuffer buffer) {
        assert (this.mIdSizes != null);
        return this.readId(buffer, this.mIdSizes.objectIDSize);
    }

    public long readRefTypeId(ByteBuffer buffer) {
        assert (this.mIdSizes != null);
        return this.readId(buffer, this.mIdSizes.refTypeIDSize);
    }

    public long readMethodId(ByteBuffer buffer) {
        assert (this.mIdSizes != null);
        return this.readId(buffer, this.mIdSizes.methodIDSize);
    }

    public long readFieldId(ByteBuffer buffer) {
        assert (this.mIdSizes != null);
        return this.readId(buffer, this.mIdSizes.fieldIDSize);
    }

    private long readId(ByteBuffer buffer, int size) {
        switch (size) {
            case 1: {
                return buffer.get();
            }
            case 2: {
                return buffer.getShort();
            }
            case 4: {
                return buffer.getInt();
            }
            case 8: {
                return buffer.getLong();
            }
        }
        throw new IllegalArgumentException("Unsupported Id size: " + size);
    }

    public String readString(ByteBuffer buffer) {
        int len = buffer.getInt();
        byte[] utf8 = new byte[len];
        buffer.get(utf8);
        return new String(utf8, Charsets.UTF_8);
    }

    public void incoming(JdwpPacket packet, JdwpAgent target) {
        if (packet.is(1, 7)) {
            target.addReplyInterceptor(packet.getId(), new JdwpInterceptor(){

                @Override
                public JdwpPacket intercept(JdwpAgent agent, JdwpPacket packet) {
                    JdwpProtocol.this.mIdSizes = new IdSizesReply();
                    JdwpProtocol.this.mIdSizes.parse(packet.getPayload(), JdwpProtocol.this);
                    return packet;
                }
            });
        }
    }
}

