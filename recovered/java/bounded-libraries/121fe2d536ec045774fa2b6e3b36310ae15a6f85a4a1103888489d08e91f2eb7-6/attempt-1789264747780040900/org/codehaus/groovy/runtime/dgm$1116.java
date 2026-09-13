/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.runtime;

import java.io.File;
import org.codehaus.groovy.reflection.CachedClass;
import org.codehaus.groovy.reflection.GeneratedMetaMethod;
import org.codehaus.groovy.runtime.ResourceGroovyMethods;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;

public class dgm$1116
extends GeneratedMetaMethod {
    public dgm$1116(String string, CachedClass cachedClass, Class clazz, Class[] classArray) {
        super(string, cachedClass, clazz, classArray);
    }

    @Override
    public Object invoke(Object object, Object[] objectArray) {
        return ResourceGroovyMethods.newWriter((File)object, (String)objectArray[0], DefaultTypeTransformation.booleanUnbox(objectArray[1]), DefaultTypeTransformation.booleanUnbox(objectArray[2]));
    }

    @Override
    public final Object doMethodInvoke(Object object, Object[] objectArray) {
        objectArray = this.coerceArgumentsToClasses(objectArray);
        return ResourceGroovyMethods.newWriter((File)object, (String)objectArray[0], DefaultTypeTransformation.booleanUnbox(objectArray[1]), DefaultTypeTransformation.booleanUnbox(objectArray[2]));
    }
}

