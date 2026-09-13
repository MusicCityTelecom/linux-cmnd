/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.deser.std.FromStringDeserializer
 *  com.google.common.hash.HashCode
 */
package com.fasterxml.jackson.datatype.guava.deser;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer;
import com.google.common.hash.HashCode;
import java.io.IOException;
import java.util.Locale;

public class HashCodeDeserializer
extends FromStringDeserializer<HashCode> {
    private static final long serialVersionUID = 1L;
    public static final HashCodeDeserializer std = new HashCodeDeserializer();

    public HashCodeDeserializer() {
        super(HashCode.class);
    }

    protected HashCode _deserialize(String value, DeserializationContext ctxt) throws IOException {
        return HashCode.fromString((String)value.toLowerCase(Locale.ENGLISH));
    }
}

