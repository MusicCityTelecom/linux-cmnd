/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.bytebuddy.ByteBuddy
 *  net.bytebuddy.description.modifier.ModifierContributor$ForField
 *  net.bytebuddy.description.modifier.Visibility
 *  net.bytebuddy.dynamic.scaffold.subclass.ConstructorStrategy
 *  net.bytebuddy.dynamic.scaffold.subclass.ConstructorStrategy$Default
 *  net.bytebuddy.implementation.Implementation
 *  net.bytebuddy.implementation.MethodDelegation
 *  net.bytebuddy.implementation.bind.annotation.FieldValue
 *  net.bytebuddy.implementation.bind.annotation.Origin
 *  net.bytebuddy.implementation.bind.annotation.RuntimeType
 *  net.bytebuddy.matcher.ElementMatcher
 *  net.bytebuddy.matcher.ElementMatchers
 */
package de.cronn.reflection.util;

import de.cronn.reflection.util.Assert;
import de.cronn.reflection.util.PropertyUtils;
import de.cronn.reflection.util.ReflectionRuntimeException;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicReference;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.modifier.ModifierContributor;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.scaffold.subclass.ConstructorStrategy;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;

public class MethodCaptor {
    static final String FIELD_NAME = "$methodCaptor";
    private final AtomicReference<Method> capturedMethod = new AtomicReference();

    void capture(Method method) {
        Method existing = this.capturedMethod.getAndSet(method);
        Assert.isNull(existing, () -> String.format("Method already captured: %s called twice?", existing));
    }

    Method getCapturedMethod() {
        Method method = this.capturedMethod.get();
        Assert.notNull(method, () -> "Method could not be captured. This can happen when no method was invoked or the method is final or non-public.");
        return method;
    }

    @RuntimeType
    public static Object intercept(@Origin Method method, @FieldValue(value="$methodCaptor") MethodCaptor methodCaptor) {
        methodCaptor.capture(method);
        return PropertyUtils.getDefaultValueObject(method.getReturnType());
    }

    static <T> Class<? extends T> createProxyClass(Class<T> beanClass) {
        try {
            return new ByteBuddy().subclass(beanClass, (ConstructorStrategy)ConstructorStrategy.Default.NO_CONSTRUCTORS).defineField(FIELD_NAME, MethodCaptor.class, new ModifierContributor.ForField[]{Visibility.PRIVATE}).method((ElementMatcher)ElementMatchers.isMethod().and((ElementMatcher)ElementMatchers.takesArguments((int)0)).and((ElementMatcher)ElementMatchers.not((ElementMatcher)ElementMatchers.isDeclaredBy(Object.class)))).intercept((Implementation)MethodDelegation.to(MethodCaptor.class)).make().load(PropertyUtils.class.getClassLoader()).getLoaded();
        }
        catch (IllegalAccessError e) {
            throw new ReflectionRuntimeException("Failed to create proxy on " + beanClass, e);
        }
    }
}

