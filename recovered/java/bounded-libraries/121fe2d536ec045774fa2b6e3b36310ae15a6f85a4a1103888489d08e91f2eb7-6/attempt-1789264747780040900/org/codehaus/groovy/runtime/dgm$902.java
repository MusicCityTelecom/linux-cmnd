/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.runtime;

import groovy.lang.Closure;
import java.util.Map;
import org.codehaus.groovy.reflection.CachedClass;
import org.codehaus.groovy.reflection.GeneratedMetaMethod;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;

public class dgm$902
extends GeneratedMetaMethod {
    public dgm$902(String string, CachedClass cachedClass, Class clazz, Class[] classArray) {
        super(string, cachedClass, clazz, classArray);
    }

    @Override
    public Object invoke(Object object, Object[] objectArray) {
        return DefaultGroovyMethods.withDefault((Map)object, DefaultTypeTransformation.booleanUnbox(objectArray[0]), DefaultTypeTransformation.booleanUnbox(objectArray[1]), (Closure)objectArray[2]);
    }

    @Override
    public final Object doMethodInvoke(Object object, Object[] objectArray) {
        objectArray = this.coerceArgumentsToClasses(objectArray);
        return DefaultGroovyMethods.withDefault((Map)object, DefaultTypeTransformation.booleanUnbox(objectArray[0]), DefaultTypeTransformation.booleanUnbox(objectArray[1]), (Closure)objectArray[2]);
    }
}

