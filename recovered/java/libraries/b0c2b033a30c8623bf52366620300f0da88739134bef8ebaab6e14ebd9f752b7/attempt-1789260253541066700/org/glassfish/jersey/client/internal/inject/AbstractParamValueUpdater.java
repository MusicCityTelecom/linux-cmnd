/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.client.internal.inject;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.ParamConverter;
import org.glassfish.jersey.internal.inject.UpdaterException;
import org.glassfish.jersey.internal.util.collection.UnsafeValue;
import org.glassfish.jersey.internal.util.collection.Values;

abstract class AbstractParamValueUpdater<T> {
    private final ParamConverter<T> paramConverter;
    private final String parameterName;
    private final String defaultValue;
    private final UnsafeValue<String, RuntimeException> convertedDefaultValue;

    protected AbstractParamValueUpdater(ParamConverter<T> converter, String parameterName, final String defaultValue) {
        this.paramConverter = converter;
        this.parameterName = parameterName;
        this.defaultValue = defaultValue;
        if (defaultValue != null) {
            this.convertedDefaultValue = Values.lazy(new UnsafeValue<String, RuntimeException>(){

                @Override
                public String get() throws RuntimeException {
                    return defaultValue;
                }
            });
            if (!converter.getClass().isAnnotationPresent(ParamConverter.Lazy.class)) {
                this.convertedDefaultValue.get();
            }
        } else {
            this.convertedDefaultValue = null;
        }
    }

    public String getName() {
        return this.parameterName;
    }

    public String getDefaultValueString() {
        return this.defaultValue;
    }

    protected final String toString(T value) {
        String result = this.convert(value);
        if (result == null) {
            return this.defaultValue();
        }
        return result;
    }

    private String convert(T value) {
        try {
            return this.paramConverter.toString(value);
        }
        catch (IllegalArgumentException | WebApplicationException ex) {
            throw ex;
        }
        catch (Exception ex) {
            throw new UpdaterException(ex);
        }
    }

    protected final boolean isDefaultValueRegistered() {
        return this.defaultValue != null;
    }

    protected final String defaultValue() {
        if (!this.isDefaultValueRegistered()) {
            return null;
        }
        return this.convertedDefaultValue.get();
    }
}

