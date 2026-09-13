/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonParser
 *  com.fasterxml.jackson.core.JsonToken
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.deser.std.FromStringDeserializer
 *  com.google.common.net.HostAndPort
 */
package com.fasterxml.jackson.datatype.guava.deser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer;
import com.google.common.net.HostAndPort;
import java.io.IOException;

public class HostAndPortDeserializer
extends FromStringDeserializer<HostAndPort> {
    private static final long serialVersionUID = 1L;
    public static final HostAndPortDeserializer std = new HostAndPortDeserializer();

    public HostAndPortDeserializer() {
        super(HostAndPort.class);
    }

    public HostAndPort deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.hasToken(JsonToken.START_OBJECT)) {
            JsonNode root = (JsonNode)p.readValueAsTree();
            JsonNode hostNode = root.get("host");
            String host = hostNode == null ? root.path("hostText").asText() : hostNode.textValue();
            JsonNode n = root.get("port");
            if (n == null) {
                return HostAndPort.fromString((String)host);
            }
            return HostAndPort.fromParts((String)host, (int)n.asInt());
        }
        return (HostAndPort)super.deserialize(p, ctxt);
    }

    protected HostAndPort _deserialize(String value, DeserializationContext ctxt) throws IOException {
        return HostAndPort.fromString((String)value);
    }
}

