/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.Lifecycle
 *  org.springframework.core.annotation.AnnotationFilter
 *  org.springframework.core.annotation.MergedAnnotations
 *  org.springframework.core.annotation.MergedAnnotations$SearchStrategy
 *  org.springframework.core.annotation.RepeatableContainers
 *  org.springframework.expression.MethodFilter
 *  org.springframework.jmx.export.annotation.ManagedAttribute
 *  org.springframework.jmx.export.annotation.ManagedOperation
 *  org.springframework.util.CustomizableThreadCreator
 *  org.springframework.util.ReflectionUtils
 */
package org.springframework.integration.expression;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.Lifecycle;
import org.springframework.core.annotation.AnnotationFilter;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.core.annotation.RepeatableContainers;
import org.springframework.expression.MethodFilter;
import org.springframework.integration.core.Pausable;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.util.CustomizableThreadCreator;
import org.springframework.util.ReflectionUtils;

public class ControlBusMethodFilter
implements MethodFilter {
    public List<Method> filter(List<Method> methods) {
        ArrayList<Method> supportedMethods = new ArrayList<Method>();
        for (Method method : methods) {
            if (!this.accept(method)) continue;
            supportedMethods.add(method);
        }
        return supportedMethods;
    }

    private boolean accept(Method method) {
        Class<?> declaringClass = method.getDeclaringClass();
        String methodName = method.getName();
        if ((Pausable.class.isAssignableFrom(declaringClass) || Lifecycle.class.isAssignableFrom(declaringClass)) && ReflectionUtils.findMethod(Pausable.class, (String)methodName, (Class[])method.getParameterTypes()) != null) {
            return true;
        }
        if (CustomizableThreadCreator.class.isAssignableFrom(declaringClass) && (methodName.startsWith("get") || methodName.startsWith("set") || methodName.startsWith("shutdown"))) {
            return true;
        }
        MergedAnnotations mergedAnnotations = MergedAnnotations.from((AnnotatedElement)method, (MergedAnnotations.SearchStrategy)MergedAnnotations.SearchStrategy.TYPE_HIERARCHY, (RepeatableContainers)RepeatableContainers.none(), (AnnotationFilter)AnnotationFilter.PLAIN);
        return mergedAnnotations.get(ManagedAttribute.class).isPresent() || mergedAnnotations.get(ManagedOperation.class).isPresent();
    }
}

