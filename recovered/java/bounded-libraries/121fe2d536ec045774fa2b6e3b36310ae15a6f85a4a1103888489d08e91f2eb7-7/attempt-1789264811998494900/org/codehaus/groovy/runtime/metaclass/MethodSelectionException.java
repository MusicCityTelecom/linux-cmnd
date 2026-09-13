/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.runtime.metaclass;

import groovy.lang.GroovyRuntimeException;
import groovy.lang.MetaMethod;
import java.lang.reflect.Modifier;
import org.codehaus.groovy.reflection.CachedConstructor;
import org.codehaus.groovy.util.FastArray;

public class MethodSelectionException
extends GroovyRuntimeException {
    private static final long serialVersionUID = 8126246630023758333L;
    private final String methodName;
    private final FastArray methods;
    private final Class[] arguments;

    public MethodSelectionException(String methodName, FastArray methods, Class[] arguments) {
        super(methodName);
        this.methodName = methodName;
        this.arguments = arguments;
        this.methods = methods;
    }

    @Override
    public String getMessage() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("Could not find which method ").append(this.methodName);
        MethodSelectionException.appendClassNames(buffer, this.arguments);
        buffer.append(" to invoke from this list:");
        this.appendMethods(buffer);
        return buffer.toString();
    }

    private static void appendClassNames(StringBuilder argBuf, Class[] classes) {
        argBuf.append("(");
        for (int i = 0; i < classes.length; ++i) {
            Class clazz;
            if (i > 0) {
                argBuf.append(", ");
            }
            String name = (clazz = classes[i]) == null ? "null" : clazz.getName();
            argBuf.append(name);
        }
        argBuf.append(")");
    }

    private void appendMethods(StringBuilder buffer) {
        for (int i = 0; i < this.methods.size; ++i) {
            buffer.append("\n  ");
            Object methodOrConstructor = this.methods.get(i);
            if (methodOrConstructor instanceof MetaMethod) {
                MetaMethod method = (MetaMethod)methodOrConstructor;
                buffer.append(Modifier.toString(method.getModifiers()));
                buffer.append(" ").append(method.getReturnType().getName());
                buffer.append(" ").append(method.getDeclaringClass().getName());
                buffer.append("#");
                buffer.append(method.getName());
                MethodSelectionException.appendClassNames(buffer, method.getNativeParameterTypes());
                continue;
            }
            CachedConstructor constructor = (CachedConstructor)methodOrConstructor;
            buffer.append(Modifier.toString(constructor.getModifiers()));
            buffer.append(" ").append(constructor.getDeclaringClass().getName());
            buffer.append("#<init>");
            MethodSelectionException.appendClassNames(buffer, constructor.getNativeParameterTypes());
        }
    }
}

