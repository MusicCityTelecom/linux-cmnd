/*
 * Decompiled with CFR 0.152.
 */
package com.fasterxml.jackson.jaxrs.cfg;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.jaxrs.cfg.EndpointConfigBase;
import java.io.IOException;
import javax.ws.rs.core.MultivaluedMap;

public abstract class ObjectReaderModifier {
    public abstract ObjectReader modify(EndpointConfigBase<?> var1, MultivaluedMap<String, String> var2, JavaType var3, ObjectReader var4, JsonParser var5) throws IOException;
}

