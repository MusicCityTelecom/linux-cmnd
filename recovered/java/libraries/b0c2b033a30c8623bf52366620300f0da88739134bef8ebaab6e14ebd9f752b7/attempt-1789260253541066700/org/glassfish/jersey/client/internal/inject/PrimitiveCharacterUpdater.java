/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.client.internal.inject;

import org.glassfish.jersey.client.inject.ParameterUpdater;

class PrimitiveCharacterUpdater
implements ParameterUpdater<Character, String> {
    private final String parameter;
    private final String defaultValue;
    private final Object defaultPrimitiveTypeValue;

    public PrimitiveCharacterUpdater(String parameter, String defaultValue, Object defaultPrimitiveTypeValue) {
        this.parameter = parameter;
        this.defaultValue = defaultValue;
        this.defaultPrimitiveTypeValue = defaultPrimitiveTypeValue;
    }

    @Override
    public String getName() {
        return this.parameter;
    }

    @Override
    public String getDefaultValueString() {
        return this.defaultValue;
    }

    @Override
    public String update(Character value) {
        if (value != null) {
            return value.toString();
        }
        if (this.defaultValue != null) {
            return this.defaultValue;
        }
        return this.defaultPrimitiveTypeValue.toString();
    }
}

