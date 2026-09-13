/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.model.internal.spi;

import java.util.Map;
import org.glassfish.jersey.model.Parameter;

public interface ParameterServiceProvider {
    public Map<Class, Parameter.ParamAnnotationHelper> getParameterAnnotationHelperMap();

    public Parameter.ParamCreationFactory<? extends Parameter> getParameterCreationFactory();
}

