/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.aopalliance.intercept.MethodInterceptor
 *  org.aopalliance.intercept.MethodInvocation
 *  org.springframework.aop.ProxyMethodInvocation
 *  org.springframework.lang.Nullable
 *  org.springframework.util.ConcurrentReferenceHashMap
 *  org.springframework.util.ConcurrentReferenceHashMap$ReferenceType
 *  org.springframework.util.ReflectionUtils
 */
package org.springframework.integration.gateway;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.function.Supplier;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.ProxyMethodInvocation;
import org.springframework.lang.Nullable;
import org.springframework.util.ConcurrentReferenceHashMap;
import org.springframework.util.ReflectionUtils;

class DefaultMethodInvokingMethodInterceptor
implements MethodInterceptor {
    private final MethodHandleLookup methodHandleLookup = MethodHandleLookup.getMethodHandleLookup();
    private final Map<Method, MethodHandle> methodHandleCache = new ConcurrentReferenceHashMap(10, ConcurrentReferenceHashMap.ReferenceType.WEAK);

    DefaultMethodInvokingMethodInterceptor() {
    }

    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        if (!method.isDefault()) {
            return invocation.proceed();
        }
        Object[] arguments = invocation.getArguments();
        Object proxy = ((ProxyMethodInvocation)invocation).getProxy();
        return this.getMethodHandle(method).bindTo(proxy).invokeWithArguments(arguments);
    }

    private MethodHandle getMethodHandle(Method method) {
        return this.methodHandleCache.computeIfAbsent(method, key -> {
            try {
                return this.methodHandleLookup.lookup((Method)key);
            }
            catch (ReflectiveOperationException ex) {
                throw new IllegalStateException(ex);
            }
        });
    }

    static enum MethodHandleLookup {
        ENCAPSULATED{
            @Nullable
            private final transient Method privateLookupIn = ReflectionUtils.findMethod(MethodHandles.class, (String)"privateLookupIn", (Class[])new Class[]{Class.class, MethodHandles.Lookup.class});

            @Override
            MethodHandle lookup(Method method) throws ReflectiveOperationException {
                if (this.privateLookupIn == null) {
                    throw new IllegalStateException("Could not obtain MethodHandles.privateLookupIn!");
                }
                return MethodHandleLookup.doLookup(method, this.getLookup(method.getDeclaringClass(), this.privateLookupIn));
            }

            @Override
            boolean isAvailable() {
                return this.privateLookupIn != null;
            }

            private MethodHandles.Lookup getLookup(Class<?> declaringClass, Method privateLookupIn) {
                MethodHandles.Lookup lookup = MethodHandles.lookup();
                try {
                    return (MethodHandles.Lookup)privateLookupIn.invoke(MethodHandles.class, declaringClass, lookup);
                }
                catch (ReflectiveOperationException e) {
                    return lookup;
                }
            }
        }
        ,
        OPEN{
            private volatile boolean constructorResolved;
            private Constructor<MethodHandles.Lookup> constructor;
            private final Supplier<Constructor<MethodHandles.Lookup>> constructorSupplier = () -> {
                if (!this.constructorResolved) {
                    Constructor ctor;
                    block3: {
                        ctor = null;
                        try {
                            ctor = MethodHandles.Lookup.class.getDeclaredConstructor(Class.class);
                            ReflectionUtils.makeAccessible(ctor);
                        }
                        catch (Exception ex) {
                            if (ex.getClass().getName().equals("java.lang.reflect.InaccessibleObjectException")) break block3;
                            throw new IllegalStateException(ex);
                        }
                    }
                    this.constructor = ctor;
                    this.constructorResolved = true;
                }
                return this.constructor;
            };

            @Override
            MethodHandle lookup(Method method) throws ReflectiveOperationException {
                Constructor<MethodHandles.Lookup> lookupConstructor = this.constructorSupplier.get();
                if (lookupConstructor != null) {
                    return lookupConstructor.newInstance(method.getDeclaringClass()).unreflectSpecial(method, method.getDeclaringClass());
                }
                throw new IllegalStateException("Could not obtain MethodHandles.lookup constructor!");
            }

            @Override
            boolean isAvailable() {
                return this.constructorSupplier.get() != null;
            }
        }
        ,
        FALLBACK{

            @Override
            MethodHandle lookup(Method method) throws ReflectiveOperationException {
                return MethodHandleLookup.doLookup(method, MethodHandles.lookup());
            }

            @Override
            boolean isAvailable() {
                return true;
            }
        };


        private static MethodHandle doLookup(Method method, MethodHandles.Lookup lookup) throws ReflectiveOperationException {
            MethodType methodType = MethodType.methodType(method.getReturnType(), method.getParameterTypes());
            return lookup.findSpecial(method.getDeclaringClass(), method.getName(), methodType, method.getDeclaringClass());
        }

        abstract MethodHandle lookup(Method var1) throws ReflectiveOperationException;

        abstract boolean isAvailable();

        static MethodHandleLookup getMethodHandleLookup() {
            for (MethodHandleLookup it : MethodHandleLookup.values()) {
                if (!it.isAvailable()) continue;
                return it;
            }
            throw new IllegalStateException("No MethodHandleLookup available!");
        }
    }
}

