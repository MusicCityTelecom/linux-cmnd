/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.ws.rs.RuntimeType;
import org.glassfish.jersey.internal.BootstrapConfigurator;
import org.glassfish.jersey.internal.ServiceFinder;
import org.glassfish.jersey.internal.util.PropertiesHelper;

public abstract class AbstractServiceFinderConfigurator<T>
implements BootstrapConfigurator {
    private final Class<T> contract;
    private final RuntimeType runtimeType;

    protected AbstractServiceFinderConfigurator(Class<T> contract, RuntimeType runtimeType) {
        this.contract = contract;
        this.runtimeType = runtimeType;
    }

    protected List<Class<T>> loadImplementations(Map<String, Object> applicationProperties) {
        if (PropertiesHelper.isMetaInfServicesEnabled(applicationProperties, this.runtimeType)) {
            return Stream.of(ServiceFinder.find(this.contract, true).toClassArray()).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}

