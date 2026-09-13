/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.api;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;
import org.aopalliance.intercept.ConstructorInterceptor;
import org.aopalliance.intercept.MethodInterceptor;
import org.glassfish.hk2.api.Filter;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface InterceptionService {
    public Filter getDescriptorFilter();

    public List<MethodInterceptor> getMethodInterceptors(Method var1);

    public List<ConstructorInterceptor> getConstructorInterceptors(Constructor<?> var1);
}

