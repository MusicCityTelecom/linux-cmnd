/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.esotericsoftware.kryo.Kryo
 *  com.esotericsoftware.kryo.io.Input
 *  org.springframework.messaging.MessageHeaders
 */
package org.springframework.integration.codec.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import java.util.HashMap;
import java.util.Map;
import org.springframework.integration.codec.kryo.MessageHeadersSerializer;
import org.springframework.integration.support.MutableMessageHeaders;
import org.springframework.messaging.MessageHeaders;

class MutableMessageHeadersSerializer
extends MessageHeadersSerializer {
    MutableMessageHeadersSerializer() {
    }

    @Override
    public MessageHeaders read(Kryo kryo, Input input, Class<MessageHeaders> type) {
        Map headers = (Map)kryo.readObject(input, HashMap.class);
        return new MutableMessageHeaders(headers);
    }
}

