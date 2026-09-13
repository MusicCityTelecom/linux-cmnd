/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.runtime;

import java.util.Comparator;
import java.util.List;
import org.codehaus.groovy.reflection.CachedClass;
import org.codehaus.groovy.reflection.GeneratedMetaMethod;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;

public class dgm$853
extends GeneratedMetaMethod {
    public dgm$853(String string, CachedClass cachedClass, Class clazz, Class[] classArray) {
        super(string, cachedClass, clazz, classArray);
    }

    @Override
    public Object invoke(Object object, Object[] objectArray) {
        return DefaultGroovyMethods.toUnique((List)object, (Comparator)objectArray[0]);
    }

    @Override
    public final Object doMethodInvoke(Object object, Object[] objectArray) {
        objectArray = this.coerceArgumentsToClasses(objectArray);
        return DefaultGroovyMethods.toUnique((List)object, (Comparator)objectArray[0]);
    }
}

