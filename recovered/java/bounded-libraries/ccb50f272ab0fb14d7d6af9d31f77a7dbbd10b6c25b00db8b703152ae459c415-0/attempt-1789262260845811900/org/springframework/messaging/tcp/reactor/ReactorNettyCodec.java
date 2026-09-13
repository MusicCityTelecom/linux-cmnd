/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 */
package org.springframework.messaging.tcp.reactor;

import io.netty.buffer.ByteBuf;
import java.util.Collection;
import org.springframework.messaging.Message;

public interface ReactorNettyCodec<P> {
    public Collection<Message<P>> decode(ByteBuf var1);

    public void encode(Message<P> var1, ByteBuf var2);
}

