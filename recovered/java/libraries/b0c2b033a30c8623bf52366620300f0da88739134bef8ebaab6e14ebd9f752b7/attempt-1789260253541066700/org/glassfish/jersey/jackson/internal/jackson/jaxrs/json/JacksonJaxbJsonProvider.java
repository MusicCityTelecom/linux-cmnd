/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.jackson.internal.jackson.jaxrs.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.ext.Provider;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.cfg.Annotations;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJsonProvider;

@Provider
@Consumes(value={"*/*"})
@Produces(value={"*/*"})
public class JacksonJaxbJsonProvider
extends JacksonJsonProvider {
    public static final Annotations[] DEFAULT_ANNOTATIONS = new Annotations[]{Annotations.JACKSON, Annotations.JAXB};

    public JacksonJaxbJsonProvider() {
        this((ObjectMapper)null, DEFAULT_ANNOTATIONS);
    }

    public JacksonJaxbJsonProvider(Annotations ... annotationsToUse) {
        this((ObjectMapper)null, annotationsToUse);
    }

    public JacksonJaxbJsonProvider(ObjectMapper mapper, Annotations[] annotationsToUse) {
        super(mapper, annotationsToUse);
    }
}

