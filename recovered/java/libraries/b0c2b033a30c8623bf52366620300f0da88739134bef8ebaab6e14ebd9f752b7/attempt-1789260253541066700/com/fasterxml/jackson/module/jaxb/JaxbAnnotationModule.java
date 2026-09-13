/*
 * Decompiled with CFR 0.152.
 */
package com.fasterxml.jackson.module.jaxb;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;
import com.fasterxml.jackson.module.jaxb.PackageVersion;

public class JaxbAnnotationModule
extends Module {
    protected Priority _priority = Priority.PRIMARY;
    protected JaxbAnnotationIntrospector _introspector;
    protected JsonInclude.Include _nonNillableInclusion;

    public JaxbAnnotationModule() {
    }

    public JaxbAnnotationModule(JaxbAnnotationIntrospector intr) {
        this._introspector = intr;
    }

    @Override
    public String getModuleName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public Version version() {
        return PackageVersion.VERSION;
    }

    @Override
    public void setupModule(Module.SetupContext context) {
        JaxbAnnotationIntrospector intr = this._introspector;
        if (intr == null) {
            intr = new JaxbAnnotationIntrospector(context.getTypeFactory());
            if (this._nonNillableInclusion != null) {
                intr.setNonNillableInclusion(this._nonNillableInclusion);
            }
        }
        switch (this._priority) {
            case PRIMARY: {
                context.insertAnnotationIntrospector(intr);
                break;
            }
            case SECONDARY: {
                context.appendAnnotationIntrospector(intr);
            }
        }
    }

    public JaxbAnnotationModule setPriority(Priority p) {
        this._priority = p;
        return this;
    }

    public Priority getPriority() {
        return this._priority;
    }

    public JaxbAnnotationModule setNonNillableInclusion(JsonInclude.Include incl) {
        this._nonNillableInclusion = incl;
        if (this._introspector != null) {
            this._introspector.setNonNillableInclusion(incl);
        }
        return this;
    }

    public JsonInclude.Include getNonNillableInclusion() {
        return this._nonNillableInclusion;
    }

    public static enum Priority {
        PRIMARY,
        SECONDARY;

    }
}

