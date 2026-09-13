/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.deser.std.FromStringDeserializer
 *  com.google.common.net.InternetDomainName
 */
package com.fasterxml.jackson.datatype.guava.deser;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer;
import com.google.common.net.InternetDomainName;
import java.io.IOException;

public class InternetDomainNameDeserializer
extends FromStringDeserializer<InternetDomainName> {
    private static final long serialVersionUID = 1L;
    public static final InternetDomainNameDeserializer std = new InternetDomainNameDeserializer();

    public InternetDomainNameDeserializer() {
        super(InternetDomainName.class);
    }

    protected InternetDomainName _deserialize(String value, DeserializationContext ctxt) throws IOException {
        return InternetDomainName.from((String)value);
    }
}

