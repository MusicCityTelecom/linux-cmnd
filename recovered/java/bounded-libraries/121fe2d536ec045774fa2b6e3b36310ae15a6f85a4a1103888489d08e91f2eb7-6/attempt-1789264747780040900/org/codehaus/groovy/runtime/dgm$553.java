/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.runtime;

import java.math.BigDecimal;
import org.codehaus.groovy.reflection.CachedClass;
import org.codehaus.groovy.reflection.GeneratedMetaMethod;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;

public class dgm$553
extends GeneratedMetaMethod {
    public dgm$553(String string, CachedClass cachedClass, Class clazz, Class[] classArray) {
        super(string, cachedClass, clazz, classArray);
    }

    @Override
    public Object invoke(Object object, Object[] objectArray) {
        return DefaultGroovyMethods.multiply((BigDecimal)object, (Double)objectArray[0]);
    }

    @Override
    public final Object doMethodInvoke(Object object, Object[] objectArray) {
        return DefaultGroovyMethods.multiply((BigDecimal)object, (Double)this.getParameterTypes()[0].coerceArgument(objectArray[0]));
    }

    @Override
    public boolean isValidMethod(Class[] classArray) {
        return classArray == null || this.getParameterTypes()[0].isAssignableFrom(classArray[0]);
    }
}

