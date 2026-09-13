/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.jackson.internal.jackson.jaxrs.cfg;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.IOException;
import javax.ws.rs.core.MultivaluedMap;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.cfg.EndpointConfigBase;

public abstract class ObjectWriterModifier {
    public abstract ObjectWriter modify(EndpointConfigBase<?> var1, MultivaluedMap<String, Object> var2, Object var3, ObjectWriter var4, JsonGenerator var5) throws IOException;
}

