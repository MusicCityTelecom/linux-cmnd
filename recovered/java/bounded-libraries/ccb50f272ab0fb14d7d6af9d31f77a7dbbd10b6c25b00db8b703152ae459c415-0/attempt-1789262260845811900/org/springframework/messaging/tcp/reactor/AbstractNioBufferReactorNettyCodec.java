/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 */
package org.springframework.messaging.tcp.reactor;

import io.netty.buffer.ByteBuf;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.List;
import org.springframework.messaging.Message;
import org.springframework.messaging.tcp.reactor.ReactorNettyCodec;

public abstract class AbstractNioBufferReactorNettyCodec<P>
implements ReactorNettyCodec<P> {
    @Override
    public Collection<Message<P>> decode(ByteBuf inputBuffer) {
        ByteBuffer nioBuffer = inputBuffer.nioBuffer();
        int start = nioBuffer.position();
        List<Message<P>> messages = this.decodeInternal(nioBuffer);
        inputBuffer.skipBytes(nioBuffer.position() - start);
        return messages;
    }

    @Override
    public void encode(Message<P> message, ByteBuf outputBuffer) {
        outputBuffer.writeBytes(this.encodeInternal(message));
    }

    protected abstract List<Message<P>> decodeInternal(ByteBuffer var1);

    protected abstract ByteBuffer encodeInternal(Message<P> var1);
}

