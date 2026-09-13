/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.esotericsoftware.kryo.Kryo
 *  com.esotericsoftware.kryo.Serializer
 *  com.esotericsoftware.kryo.io.Input
 *  com.esotericsoftware.kryo.io.Output
 *  org.springframework.messaging.MessageHeaders
 */
package org.springframework.integration.codec.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import java.util.HashMap;
import java.util.Map;
import org.springframework.messaging.MessageHeaders;

class MessageHeadersSerializer
extends Serializer<MessageHeaders> {
    MessageHeadersSerializer() {
    }

    public void write(Kryo kryo, Output output, MessageHeaders headers) {
        HashMap map = new HashMap();
        for (Map.Entry entry : headers.entrySet()) {
            if (entry.getValue() == null) continue;
            map.put((String)entry.getKey(), entry.getValue());
        }
        kryo.writeObject(output, map);
    }

    public MessageHeaders read(Kryo kryo, Input input, Class<MessageHeaders> type) {
        Map headers = (Map)kryo.readObject(input, HashMap.class);
        return new MessageHeaders(headers);
    }
}

