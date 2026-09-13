/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.client.internal.inject;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.ParamConverter;
import org.glassfish.jersey.client.inject.ParameterUpdater;
import org.glassfish.jersey.client.internal.inject.AbstractParamValueUpdater;
import org.glassfish.jersey.internal.inject.UpdaterException;

final class SingleValueUpdater<T>
extends AbstractParamValueUpdater<T>
implements ParameterUpdater<T, String> {
    public SingleValueUpdater(ParamConverter<T> converter, String parameterName, String defaultValue) {
        super(converter, parameterName, defaultValue);
    }

    @Override
    public String update(T value) {
        try {
            if (value == null && this.isDefaultValueRegistered()) {
                return this.getDefaultValueString();
            }
            return this.toString(value);
        }
        catch (ProcessingException | WebApplicationException ex) {
            throw ex;
        }
        catch (IllegalArgumentException ex) {
            return this.defaultValue();
        }
        catch (Exception ex) {
            throw new UpdaterException(ex);
        }
    }
}

