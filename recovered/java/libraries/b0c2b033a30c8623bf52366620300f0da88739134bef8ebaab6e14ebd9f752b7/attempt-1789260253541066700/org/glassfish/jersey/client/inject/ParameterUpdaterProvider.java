/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.client.inject;

import org.glassfish.jersey.client.inject.ParameterUpdater;
import org.glassfish.jersey.model.Parameter;

public interface ParameterUpdaterProvider {
    public ParameterUpdater<?, ?> get(Parameter var1);
}

