/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.reflection;

import org.apache.groovy.util.SystemUtil;
import org.codehaus.groovy.reflection.GroovyClassValue;
import org.codehaus.groovy.reflection.GroovyClassValuePreJava7;
import org.codehaus.groovy.reflection.v7.GroovyClassValueJava7;

class GroovyClassValueFactory {
    private static final boolean USE_CLASSVALUE = Boolean.parseBoolean(SystemUtil.getSystemPropertySafe("groovy.use.classvalue", "true"));

    GroovyClassValueFactory() {
    }

    public static <T> GroovyClassValue<T> createGroovyClassValue(GroovyClassValue.ComputeValue<T> computeValue) {
        return USE_CLASSVALUE ? new GroovyClassValueJava7<T>(computeValue) : new GroovyClassValuePreJava7<T>(computeValue);
    }
}

