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
 */
package com.fasterxml.jackson.datatype.jdk8;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.fasterxml.jackson.databind.type.TypeModifier;
import com.fasterxml.jackson.datatype.jdk8.Jdk8BeanSerializerModifier;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Deserializers;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Serializers;
import com.fasterxml.jackson.datatype.jdk8.Jdk8TypeModifier;
import com.fasterxml.jackson.datatype.jdk8.PackageVersion;

public class Jdk8Module
extends Module {
    protected boolean _cfgHandleAbsentAsNull = false;

    public void setupModule(Module.SetupContext context) {
        context.addSerializers((Serializers)new Jdk8Serializers());
        context.addDeserializers((Deserializers)new Jdk8Deserializers());
        context.addTypeModifier((TypeModifier)new Jdk8TypeModifier());
        if (this._cfgHandleAbsentAsNull) {
            context.addBeanSerializerModifier((BeanSerializerModifier)new Jdk8BeanSerializerModifier());
        }
    }

    public Version version() {
        return PackageVersion.VERSION;
    }

    @Deprecated
    public Jdk8Module configureAbsentsAsNulls(boolean state) {
        this._cfgHandleAbsentAsNull = state;
        return this;
    }

    public int hashCode() {
        return ((Object)((Object)this)).getClass().hashCode();
    }

    public boolean equals(Object o) {
        return this == o;
    }

    public String getModuleName() {
        return "Jdk8Module";
    }
}

