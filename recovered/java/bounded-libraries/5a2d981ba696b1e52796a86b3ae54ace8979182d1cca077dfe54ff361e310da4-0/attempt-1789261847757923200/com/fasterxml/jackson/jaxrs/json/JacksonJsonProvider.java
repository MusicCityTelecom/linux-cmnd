/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.Version
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.ObjectReader
 *  com.fasterxml.jackson.databind.ObjectWriter
 *  com.fasterxml.jackson.jaxrs.base.ProviderBase
 *  com.fasterxml.jackson.jaxrs.cfg.Annotations
 *  com.fasterxml.jackson.jaxrs.cfg.MapperConfiguratorBase
 *  javax.ws.rs.Consumes
 *  javax.ws.rs.Produces
 *  javax.ws.rs.core.Context
 *  javax.ws.rs.core.MediaType
 *  javax.ws.rs.ext.ContextResolver
 *  javax.ws.rs.ext.Provider
 *  javax.ws.rs.ext.Providers
 */
package com.fasterxml.jackson.jaxrs.json;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.jaxrs.base.ProviderBase;
import com.fasterxml.jackson.jaxrs.cfg.Annotations;
import com.fasterxml.jackson.jaxrs.cfg.MapperConfiguratorBase;
import com.fasterxml.jackson.jaxrs.json.JsonEndpointConfig;
import com.fasterxml.jackson.jaxrs.json.JsonMapperConfigurator;
import com.fasterxml.jackson.jaxrs.json.PackageVersion;
import java.lang.annotation.Annotation;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.Providers;

@Provider
@Consumes(value={"*/*"})
@Produces(value={"*/*"})
public class JacksonJsonProvider
extends ProviderBase<JacksonJsonProvider, ObjectMapper, JsonEndpointConfig, JsonMapperConfigurator> {
    public static final String MIME_JAVASCRIPT = "application/javascript";
    public static final String MIME_JAVASCRIPT_MS = "application/x-javascript";
    public static final Annotations[] BASIC_ANNOTATIONS = new Annotations[]{Annotations.JACKSON};
    protected String _jsonpFunctionName;
    @Context
    protected Providers _providers;

    public JacksonJsonProvider() {
        this((ObjectMapper)null, BASIC_ANNOTATIONS);
    }

    public JacksonJsonProvider(Annotations ... annotationsToUse) {
        this((ObjectMapper)null, annotationsToUse);
    }

    public JacksonJsonProvider(ObjectMapper mapper) {
        this(mapper, BASIC_ANNOTATIONS);
    }

    public JacksonJsonProvider(ObjectMapper mapper, Annotations[] annotationsToUse) {
        super((MapperConfiguratorBase)new JsonMapperConfigurator(mapper, annotationsToUse));
    }

    public Version version() {
        return PackageVersion.VERSION;
    }

    public void setJSONPFunctionName(String fname) {
        this._jsonpFunctionName = fname;
    }

    protected boolean hasMatchingMediaType(MediaType mediaType) {
        if (mediaType != null) {
            String subtype = mediaType.getSubtype();
            return "json".equalsIgnoreCase(subtype) || subtype.endsWith("+json") || "javascript".equals(subtype) || "x-javascript".equals(subtype) || "x-json".equals(subtype);
        }
        return true;
    }

    protected ObjectMapper _locateMapperViaProvider(Class<?> type, MediaType mediaType) {
        if (this._providers != null) {
            ContextResolver resolver = this._providers.getContextResolver(ObjectMapper.class, mediaType);
            if (resolver == null) {
                resolver = this._providers.getContextResolver(ObjectMapper.class, null);
            }
            if (resolver != null) {
                return (ObjectMapper)resolver.getContext(type);
            }
        }
        return null;
    }

    protected JsonEndpointConfig _configForReading(ObjectReader reader, Annotation[] annotations) {
        return JsonEndpointConfig.forReading(reader, annotations);
    }

    protected JsonEndpointConfig _configForWriting(ObjectWriter writer, Annotation[] annotations) {
        return JsonEndpointConfig.forWriting(writer, annotations, this._jsonpFunctionName);
    }
}

