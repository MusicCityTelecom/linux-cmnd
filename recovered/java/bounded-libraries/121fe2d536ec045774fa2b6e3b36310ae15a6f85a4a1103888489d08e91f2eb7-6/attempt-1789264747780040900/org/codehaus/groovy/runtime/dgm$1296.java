/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.runtime;

import groovy.lang.IntRange;
import org.codehaus.groovy.reflection.CachedClass;
import org.codehaus.groovy.reflection.GeneratedMetaMethod;
import org.codehaus.groovy.runtime.StringGroovyMethods;

public class dgm$1296
extends GeneratedMetaMethod {
    public dgm$1296(String string, CachedClass cachedClass, Class clazz, Class[] classArray) {
        super(string, cachedClass, clazz, classArray);
    }

    @Override
    public Object invoke(Object object, Object[] objectArray) {
        StringGroovyMethods.putAt((StringBuffer)object, (IntRange)objectArray[0], objectArray[1]);
        return null;
    }

    @Override
    public final Object doMethodInvoke(Object object, Object[] objectArray) {
        objectArray = this.coerceArgumentsToClasses(objectArray);
        StringGroovyMethods.putAt((StringBuffer)object, (IntRange)objectArray[0], objectArray[1]);
        return null;
    }
}

