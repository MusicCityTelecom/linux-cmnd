/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.Version
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.Module$SetupContext
 *  com.fasterxml.jackson.databind.deser.Deserializers
 *  com.fasterxml.jackson.databind.ser.BeanSerializerModifier
 *  com.fasterxml.jackson.databind.ser.Serializers
 *  com.fasterxml.jackson.databind.type.TypeModifier
 *  com.google.common.base.Preconditions
 *  com.google.common.collect.BoundType
 */
package com.fasterxml.jackson.datatype.guava;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.fasterxml.jackson.databind.type.TypeModifier;
import com.fasterxml.jackson.datatype.guava.GuavaDeserializers;
import com.fasterxml.jackson.datatype.guava.GuavaSerializers;
import com.fasterxml.jackson.datatype.guava.GuavaTypeModifier;
import com.fasterxml.jackson.datatype.guava.PackageVersion;
import com.fasterxml.jackson.datatype.guava.ser.GuavaBeanSerializerModifier;
import com.google.common.base.Preconditions;
import com.google.common.collect.BoundType;

public class GuavaModule
extends Module {
    private final String NAME = "GuavaModule";
    protected boolean _cfgHandleAbsentAsNull = true;
    protected BoundType _defaultBoundType;

    public String getModuleName() {
        return "GuavaModule";
    }

    public Version version() {
        return PackageVersion.VERSION;
    }

    public void setupModule(Module.SetupContext context) {
        context.addDeserializers((Deserializers)new GuavaDeserializers(this._defaultBoundType));
        context.addSerializers((Serializers)new GuavaSerializers());
        context.addTypeModifier((TypeModifier)new GuavaTypeModifier());
        if (this._cfgHandleAbsentAsNull) {
            context.addBeanSerializerModifier((BeanSerializerModifier)new GuavaBeanSerializerModifier());
        }
    }

    public GuavaModule configureAbsentsAsNulls(boolean state) {
        this._cfgHandleAbsentAsNull = state;
        return this;
    }

    public GuavaModule defaultBoundType(BoundType boundType) {
        Preconditions.checkNotNull((Object)boundType);
        this._defaultBoundType = boundType;
        return this;
    }

    public int hashCode() {
        return "GuavaModule".hashCode();
    }

    public boolean equals(Object o) {
        return this == o;
    }
}

