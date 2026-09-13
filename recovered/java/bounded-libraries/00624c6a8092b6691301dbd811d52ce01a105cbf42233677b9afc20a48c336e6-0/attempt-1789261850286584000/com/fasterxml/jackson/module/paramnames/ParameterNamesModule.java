/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator$Mode
 *  com.fasterxml.jackson.databind.AnnotationIntrospector
 *  com.fasterxml.jackson.databind.Module$SetupContext
 *  com.fasterxml.jackson.databind.module.SimpleModule
 */
package com.fasterxml.jackson.module.paramnames;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.module.paramnames.PackageVersion;
import com.fasterxml.jackson.module.paramnames.ParameterExtractor;
import com.fasterxml.jackson.module.paramnames.ParameterNamesAnnotationIntrospector;

public class ParameterNamesModule
extends SimpleModule {
    private static final long serialVersionUID = 1L;
    private final JsonCreator.Mode creatorBinding;

    public ParameterNamesModule(JsonCreator.Mode creatorBinding) {
        super(PackageVersion.VERSION);
        this.creatorBinding = creatorBinding;
    }

    public ParameterNamesModule() {
        super(PackageVersion.VERSION);
        this.creatorBinding = null;
    }

    public void setupModule(Module.SetupContext context) {
        super.setupModule(context);
        context.insertAnnotationIntrospector((AnnotationIntrospector)new ParameterNamesAnnotationIntrospector(this.creatorBinding, new ParameterExtractor()));
    }

    public int hashCode() {
        return ((Object)((Object)this)).getClass().hashCode();
    }

    public boolean equals(Object o) {
        return this == o;
    }
}

