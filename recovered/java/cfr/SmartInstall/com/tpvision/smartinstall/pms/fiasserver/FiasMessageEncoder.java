/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.pms.fiasserver;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class FiasMessageEncoder
extends MessageToByteEncoder<String> {
    @Override
    protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out) throws Exception {
        out.writeByte(2);
        out.writeBytes(msg.getBytes());
        out.writeByte(3);
    }
}

