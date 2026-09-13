/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.runtime;

import java.math.BigDecimal;
import org.codehaus.groovy.reflection.CachedClass;
import org.codehaus.groovy.reflection.GeneratedMetaMethod;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;

public class dgm$448
extends GeneratedMetaMethod {
    public dgm$448(String string, CachedClass cachedClass, Class clazz, Class[] classArray) {
        super(string, cachedClass, clazz, classArray);
    }

    @Override
    public Object invoke(Object object, Object[] objectArray) {
        return DefaultGroovyMethods.isAtLeast((BigDecimal)object, (BigDecimal)objectArray[0]);
    }

    @Override
    public final Object doMethodInvoke(Object object, Object[] objectArray) {
        return DefaultGroovyMethods.isAtLeast((BigDecimal)object, (BigDecimal)this.getParameterTypes()[0].coerceArgument(objectArray[0]));
    }

    @Override
    public boolean isValidMethod(Class[] classArray) {
        return classArray == null || this.getParameterTypes()[0].isAssignableFrom(classArray[0]);
    }
}

