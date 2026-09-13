/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.AnnotationIntrospector
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector
 *  com.fasterxml.jackson.jaxrs.cfg.Annotations
 *  com.fasterxml.jackson.jaxrs.cfg.MapperConfiguratorBase
 *  com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector
 */
package com.fasterxml.jackson.jaxrs.json;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.jaxrs.cfg.Annotations;
import com.fasterxml.jackson.jaxrs.cfg.MapperConfiguratorBase;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;
import java.util.ArrayList;

public class JsonMapperConfigurator
extends MapperConfiguratorBase<JsonMapperConfigurator, ObjectMapper> {
    public JsonMapperConfigurator(ObjectMapper mapper, Annotations[] defAnnotations) {
        super(mapper, defAnnotations);
    }

    public synchronized ObjectMapper getConfiguredMapper() {
        return this._mapper;
    }

    public synchronized ObjectMapper getDefaultMapper() {
        if (this._defaultMapper == null) {
            this._defaultMapper = new ObjectMapper();
            this._setAnnotations(this._defaultMapper, this._defaultAnnotationsToUse);
        }
        return this._defaultMapper;
    }

    protected ObjectMapper mapper() {
        if (this._mapper == null) {
            this._mapper = new ObjectMapper();
            this._setAnnotations(this._mapper, this._defaultAnnotationsToUse);
        }
        return this._mapper;
    }

    protected AnnotationIntrospector _resolveIntrospectors(Annotations[] annotationsToUse) {
        ArrayList<AnnotationIntrospector> intr = new ArrayList<AnnotationIntrospector>();
        for (Annotations a : annotationsToUse) {
            if (a == null) continue;
            intr.add(this._resolveIntrospector(a));
        }
        int count = intr.size();
        if (count == 0) {
            return AnnotationIntrospector.nopInstance();
        }
        AnnotationIntrospector curr = (AnnotationIntrospector)intr.get(0);
        int len = intr.size();
        for (int i = 1; i < len; ++i) {
            curr = AnnotationIntrospector.pair((AnnotationIntrospector)curr, (AnnotationIntrospector)((AnnotationIntrospector)intr.get(i)));
        }
        return curr;
    }

    protected AnnotationIntrospector _resolveIntrospector(Annotations ann) {
        switch (ann) {
            case JACKSON: {
                return new JacksonAnnotationIntrospector();
            }
            case JAXB: {
                try {
                    if (this._jaxbIntrospectorClass == null) {
                        this._jaxbIntrospectorClass = JaxbAnnotationIntrospector.class;
                    }
                    return (AnnotationIntrospector)this._jaxbIntrospectorClass.newInstance();
                }
                catch (Exception e) {
                    throw new IllegalStateException("Failed to instantiate JaxbAnnotationIntrospector: " + e.getMessage(), e);
                }
            }
        }
        throw new IllegalStateException();
    }
}

