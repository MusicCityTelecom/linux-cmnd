/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  io.netty.buffer.Unpooled
 *  io.rsocket.Payload
 *  io.rsocket.util.ByteBufPayload
 *  io.rsocket.util.DefaultPayload
 *  org.springframework.core.io.buffer.DataBuffer
 *  org.springframework.core.io.buffer.DataBufferFactory
 *  org.springframework.core.io.buffer.DefaultDataBuffer
 *  org.springframework.core.io.buffer.NettyDataBuffer
 *  org.springframework.core.io.buffer.NettyDataBufferFactory
 */
package org.springframework.messaging.rsocket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.rsocket.Payload;
import io.rsocket.util.ByteBufPayload;
import io.rsocket.util.DefaultPayload;
import java.nio.ByteBuffer;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DefaultDataBuffer;
import org.springframework.core.io.buffer.NettyDataBuffer;
import org.springframework.core.io.buffer.NettyDataBufferFactory;

public abstract class PayloadUtils {
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static DataBuffer retainDataAndReleasePayload(Payload payload, DataBufferFactory bufferFactory) {
        try {
            if (bufferFactory instanceof NettyDataBufferFactory) {
                ByteBuf byteBuf = payload.sliceData().retain();
                NettyDataBuffer nettyDataBuffer = ((NettyDataBufferFactory)bufferFactory).wrap(byteBuf);
                return nettyDataBuffer;
            }
            DataBuffer dataBuffer = bufferFactory.wrap(payload.getData());
            return dataBuffer;
        }
        finally {
            if (payload.refCnt() > 0) {
                payload.release();
            }
        }
    }

    public static Payload createPayload(DataBuffer data, DataBuffer metadata) {
        return data instanceof NettyDataBuffer || metadata instanceof NettyDataBuffer ? ByteBufPayload.create((ByteBuf)PayloadUtils.asByteBuf(data), (ByteBuf)PayloadUtils.asByteBuf(metadata)) : DefaultPayload.create((ByteBuffer)PayloadUtils.asByteBuffer(data), (ByteBuffer)PayloadUtils.asByteBuffer(metadata));
    }

    public static Payload createPayload(DataBuffer data) {
        return data instanceof NettyDataBuffer ? ByteBufPayload.create((ByteBuf)PayloadUtils.asByteBuf(data)) : DefaultPayload.create((ByteBuffer)PayloadUtils.asByteBuffer(data));
    }

    static ByteBuf asByteBuf(DataBuffer buffer) {
        return buffer instanceof NettyDataBuffer ? ((NettyDataBuffer)buffer).getNativeBuffer() : Unpooled.wrappedBuffer((ByteBuffer)buffer.asByteBuffer());
    }

    private static ByteBuffer asByteBuffer(DataBuffer buffer) {
        return buffer instanceof DefaultDataBuffer ? ((DefaultDataBuffer)buffer).getNativeBuffer() : buffer.asByteBuffer();
    }
}

