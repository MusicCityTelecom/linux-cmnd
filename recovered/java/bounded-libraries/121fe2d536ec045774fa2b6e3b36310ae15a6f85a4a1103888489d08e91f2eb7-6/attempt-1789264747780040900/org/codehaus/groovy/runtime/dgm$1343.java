/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.runtime;

import org.codehaus.groovy.reflection.CachedClass;
import org.codehaus.groovy.reflection.GeneratedMetaMethod;
import org.codehaus.groovy.runtime.StringGroovyMethods;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;

public class dgm$1343
extends GeneratedMetaMethod {
    public dgm$1343(String string, CachedClass cachedClass, Class clazz, Class[] classArray) {
        super(string, cachedClass, clazz, classArray);
    }

    @Override
    public Object invoke(Object object, Object[] objectArray) {
        return StringGroovyMethods.takeBetween((String)object, (CharSequence)objectArray[0], (CharSequence)objectArray[1], DefaultTypeTransformation.intUnbox(objectArray[2]));
    }

    @Override
    public final Object doMethodInvoke(Object object, Object[] objectArray) {
        objectArray = this.coerceArgumentsToClasses(objectArray);
        return StringGroovyMethods.takeBetween((String)object, (CharSequence)objectArray[0], (CharSequence)objectArray[1], DefaultTypeTransformation.intUnbox(objectArray[2]));
    }
}

