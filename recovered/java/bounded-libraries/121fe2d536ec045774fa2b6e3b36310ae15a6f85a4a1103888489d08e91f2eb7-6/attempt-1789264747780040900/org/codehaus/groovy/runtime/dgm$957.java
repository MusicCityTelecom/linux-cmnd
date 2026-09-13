/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.runtime;

import java.io.OutputStream;
import org.codehaus.groovy.reflection.CachedClass;
import org.codehaus.groovy.reflection.GeneratedMetaMethod;
import org.codehaus.groovy.runtime.IOGroovyMethods;

public class dgm$957
extends GeneratedMetaMethod {
    public dgm$957(String string, CachedClass cachedClass, Class clazz, Class[] classArray) {
        super(string, cachedClass, clazz, classArray);
    }

    @Override
    public Object invoke(Object object, Object[] objectArray) {
        return IOGroovyMethods.leftShift((OutputStream)object, objectArray[0]);
    }

    @Override
    public final Object doMethodInvoke(Object object, Object[] objectArray) {
        objectArray = this.coerceArgumentsToClasses(objectArray);
        return IOGroovyMethods.leftShift((OutputStream)object, objectArray[0]);
    }
}

