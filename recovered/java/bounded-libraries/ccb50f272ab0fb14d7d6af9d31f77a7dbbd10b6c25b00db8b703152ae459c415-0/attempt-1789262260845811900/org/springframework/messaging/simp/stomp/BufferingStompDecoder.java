/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 *  org.springframework.util.LinkedMultiValueMap
 *  org.springframework.util.MultiValueMap
 */
package org.springframework.messaging.simp.stomp;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompConversionException;
import org.springframework.messaging.simp.stomp.StompDecoder;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class BufferingStompDecoder {
    private final StompDecoder stompDecoder;
    private final int bufferSizeLimit;
    private final Queue<ByteBuffer> chunks = new LinkedBlockingQueue<ByteBuffer>();
    @Nullable
    private volatile Integer expectedContentLength;

    public BufferingStompDecoder(StompDecoder stompDecoder, int bufferSizeLimit) {
        Assert.notNull((Object)stompDecoder, (String)"StompDecoder is required");
        Assert.isTrue((bufferSizeLimit > 0 ? 1 : 0) != 0, (String)"Buffer size limit must be greater than 0");
        this.stompDecoder = stompDecoder;
        this.bufferSizeLimit = bufferSizeLimit;
    }

    public final StompDecoder getStompDecoder() {
        return this.stompDecoder;
    }

    public final int getBufferSizeLimit() {
        return this.bufferSizeLimit;
    }

    public List<Message<byte[]>> decode(ByteBuffer newBuffer) {
        this.chunks.add(newBuffer);
        this.checkBufferLimits();
        Integer contentLength = this.expectedContentLength;
        if (contentLength != null && this.getBufferSize() < contentLength) {
            return Collections.emptyList();
        }
        ByteBuffer bufferToDecode = this.assembleChunksAndReset();
        LinkedMultiValueMap headers = new LinkedMultiValueMap();
        List<Message<byte[]>> messages = this.stompDecoder.decode(bufferToDecode, (MultiValueMap<String, String>)headers);
        if (bufferToDecode.hasRemaining()) {
            this.chunks.add(bufferToDecode);
            this.expectedContentLength = StompHeaderAccessor.getContentLength((Map<String, List<String>>)headers);
        }
        return messages;
    }

    private ByteBuffer assembleChunksAndReset() {
        ByteBuffer result;
        if (this.chunks.size() == 1) {
            result = this.chunks.remove();
        } else {
            result = ByteBuffer.allocate(this.getBufferSize());
            for (ByteBuffer partial : this.chunks) {
                result.put(partial);
            }
            result.flip();
        }
        this.chunks.clear();
        this.expectedContentLength = null;
        return result;
    }

    private void checkBufferLimits() {
        Integer contentLength = this.expectedContentLength;
        if (contentLength != null && contentLength > this.bufferSizeLimit) {
            throw new StompConversionException("STOMP 'content-length' header value " + this.expectedContentLength + "  exceeds configured buffer size limit " + this.bufferSizeLimit);
        }
        if (this.getBufferSize() > this.bufferSizeLimit) {
            throw new StompConversionException("The configured STOMP buffer size limit of " + this.bufferSizeLimit + " bytes has been exceeded");
        }
    }

    public int getBufferSize() {
        int size = 0;
        for (ByteBuffer buffer : this.chunks) {
            size += buffer.remaining();
        }
        return size;
    }

    @Nullable
    public Integer getExpectedContentLength() {
        return this.expectedContentLength;
    }
}

