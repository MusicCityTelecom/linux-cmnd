/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.annotation.JsonCreator$Mode
 *  com.fasterxml.jackson.databind.cfg.MapperConfig
 *  com.fasterxml.jackson.databind.introspect.Annotated
 *  com.fasterxml.jackson.databind.introspect.AnnotatedConstructor
 *  com.fasterxml.jackson.databind.introspect.AnnotatedMember
 *  com.fasterxml.jackson.databind.introspect.AnnotatedMethod
 *  com.fasterxml.jackson.databind.introspect.AnnotatedParameter
 *  com.fasterxml.jackson.databind.introspect.AnnotatedWithParams
 *  com.fasterxml.jackson.databind.introspect.NopAnnotationIntrospector
 */
package com.fasterxml.jackson.module.paramnames;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedConstructor;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.AnnotatedParameter;
import com.fasterxml.jackson.databind.introspect.AnnotatedWithParams;
import com.fasterxml.jackson.databind.introspect.NopAnnotationIntrospector;
import com.fasterxml.jackson.module.paramnames.ParameterExtractor;
import java.lang.reflect.MalformedParametersException;
import java.lang.reflect.Parameter;

public class ParameterNamesAnnotationIntrospector
extends NopAnnotationIntrospector {
    private static final long serialVersionUID = 1L;
    private final JsonCreator.Mode creatorBinding;
    private final ParameterExtractor parameterExtractor;

    ParameterNamesAnnotationIntrospector(JsonCreator.Mode creatorBinding, ParameterExtractor parameterExtractor) {
        this.creatorBinding = creatorBinding;
        this.parameterExtractor = parameterExtractor;
    }

    public String findImplicitPropertyName(AnnotatedMember m) {
        if (m instanceof AnnotatedParameter) {
            return this.findParameterName((AnnotatedParameter)m);
        }
        return null;
    }

    private String findParameterName(AnnotatedParameter annotatedParameter) {
        Parameter[] params;
        try {
            params = this.getParameters(annotatedParameter.getOwner());
        }
        catch (MalformedParametersException e) {
            return null;
        }
        Parameter p = params[annotatedParameter.getIndex()];
        return p.isNamePresent() ? p.getName() : null;
    }

    private Parameter[] getParameters(AnnotatedWithParams owner) {
        if (owner instanceof AnnotatedConstructor) {
            return this.parameterExtractor.getParameters(((AnnotatedConstructor)owner).getAnnotated());
        }
        if (owner instanceof AnnotatedMethod) {
            return this.parameterExtractor.getParameters(((AnnotatedMethod)owner).getAnnotated());
        }
        return null;
    }

    public JsonCreator.Mode findCreatorAnnotation(MapperConfig<?> config, Annotated a) {
        JsonCreator ann = (JsonCreator)this._findAnnotation(a, JsonCreator.class);
        if (ann != null) {
            JsonCreator.Mode mode = ann.mode();
            if (this.creatorBinding != null && mode == JsonCreator.Mode.DEFAULT) {
                mode = this.creatorBinding;
            }
            return mode;
        }
        return null;
    }
}

