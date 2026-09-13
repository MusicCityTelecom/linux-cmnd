/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.api;

import org.glassfish.hk2.api.Injectee;
import org.glassfish.hk2.api.ServiceHandle;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface InjectionResolver<T> {
    public static final String SYSTEM_RESOLVER_NAME = "SystemInjectResolver";

    public Object resolve(Injectee var1, ServiceHandle<?> var2);

    public boolean isConstructorParameterIndicator();

    public boolean isMethodParameterIndicator();
}

