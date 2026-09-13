/*
 * Decompiled with CFR 0.152.
 */
package com.fasterxml.jackson.jaxrs.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.cfg.Annotations;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.ext.Provider;

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

