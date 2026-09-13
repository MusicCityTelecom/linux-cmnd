/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.runtime;

import groovy.lang.EmptyRange;
import groovy.lang.ListWithDefault;
import org.codehaus.groovy.reflection.CachedClass;
import org.codehaus.groovy.reflection.GeneratedMetaMethod;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;

public class dgm$353
extends GeneratedMetaMethod {
    public dgm$353(String string, CachedClass cachedClass, Class clazz, Class[] classArray) {
        super(string, cachedClass, clazz, classArray);
    }

    @Override
    public Object invoke(Object object, Object[] objectArray) {
        return DefaultGroovyMethods.getAt((ListWithDefault)object, (EmptyRange)objectArray[0]);
    }

    @Override
    public final Object doMethodInvoke(Object object, Object[] objectArray) {
        objectArray = this.coerceArgumentsToClasses(objectArray);
        return DefaultGroovyMethods.getAt((ListWithDefault)object, (EmptyRange)objectArray[0]);
    }
}

