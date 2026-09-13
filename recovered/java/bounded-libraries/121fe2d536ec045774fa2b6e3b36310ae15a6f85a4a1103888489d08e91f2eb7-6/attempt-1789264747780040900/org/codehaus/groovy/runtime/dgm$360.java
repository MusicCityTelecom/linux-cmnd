/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.runtime;

import java.lang.reflect.AnnotatedElement;
import org.codehaus.groovy.reflection.CachedClass;
import org.codehaus.groovy.reflection.GeneratedMetaMethod;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;

public class dgm$360
extends GeneratedMetaMethod {
    public dgm$360(String string, CachedClass cachedClass, Class clazz, Class[] classArray) {
        super(string, cachedClass, clazz, classArray);
    }

    @Override
    public Object invoke(Object object, Object[] objectArray) {
        return DefaultGroovyMethods.getGroovydoc((AnnotatedElement)object);
    }

    @Override
    public final Object doMethodInvoke(Object object, Object[] objectArray) {
        objectArray = this.coerceArgumentsToClasses(objectArray);
        return DefaultGroovyMethods.getGroovydoc((AnnotatedElement)object);
    }
}

