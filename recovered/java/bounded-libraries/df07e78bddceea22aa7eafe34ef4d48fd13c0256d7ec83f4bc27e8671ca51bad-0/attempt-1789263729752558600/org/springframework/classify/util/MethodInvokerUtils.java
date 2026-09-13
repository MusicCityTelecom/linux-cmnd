/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.aop.framework.Advised
 *  org.springframework.core.annotation.AnnotationUtils
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.ObjectUtils
 *  org.springframework.util.ReflectionUtils
 *  org.springframework.util.ReflectionUtils$MethodCallback
 */
package org.springframework.classify.util;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicReference;
import org.springframework.aop.framework.Advised;
import org.springframework.classify.util.MethodInvoker;
import org.springframework.classify.util.SimpleMethodInvoker;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;

public class MethodInvokerUtils {
    public static MethodInvoker getMethodInvokerByName(Object object, String methodName, boolean paramsRequired, Class<?> ... paramTypes) {
        Assert.notNull((Object)object, (String)"Object to invoke must not be null");
        Method method = ClassUtils.getMethodIfAvailable(object.getClass(), (String)methodName, (Class[])paramTypes);
        if (method == null) {
            String errorMsg = "no method found with name [" + methodName + "] on class [" + object.getClass().getSimpleName() + "] compatable with the signature [" + MethodInvokerUtils.getParamTypesString(paramTypes) + "].";
            Assert.isTrue((!paramsRequired ? 1 : 0) != 0, (String)errorMsg);
            method = ClassUtils.getMethodIfAvailable(object.getClass(), (String)methodName, (Class[])new Class[0]);
            Assert.notNull((Object)method, (String)errorMsg);
        }
        return new SimpleMethodInvoker(object, method);
    }

    public static String getParamTypesString(Class<?> ... paramTypes) {
        StringBuilder paramTypesList = new StringBuilder("(");
        for (int i = 0; i < paramTypes.length; ++i) {
            paramTypesList.append(paramTypes[i].getSimpleName());
            if (i + 1 >= paramTypes.length) continue;
            paramTypesList.append(", ");
        }
        return paramTypesList.append(")").toString();
    }

    public static MethodInvoker getMethodInvokerForInterface(Class<?> cls, String methodName, Object object, Class<?> ... paramTypes) {
        if (cls.isAssignableFrom(object.getClass())) {
            return MethodInvokerUtils.getMethodInvokerByName(object, methodName, true, paramTypes);
        }
        return null;
    }

    public static MethodInvoker getMethodInvokerByAnnotation(final Class<? extends Annotation> annotationType, Object target, final Class<?> ... expectedParamTypes) {
        Class targetClass;
        MethodInvoker mi = MethodInvokerUtils.getMethodInvokerByAnnotation(annotationType, target);
        Class clazz = targetClass = target instanceof Advised ? ((Advised)target).getTargetSource().getTargetClass() : target.getClass();
        if (mi != null) {
            ReflectionUtils.doWithMethods((Class)targetClass, (ReflectionUtils.MethodCallback)new ReflectionUtils.MethodCallback(){

                public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {
                    Class<?>[] paramTypes;
                    Annotation annotation = AnnotationUtils.findAnnotation((Method)method, (Class)annotationType);
                    if (annotation != null && (paramTypes = method.getParameterTypes()).length > 0) {
                        String errorMsg = "The method [" + method.getName() + "] on target class [" + targetClass.getSimpleName() + "] is incompatable with the signature [" + MethodInvokerUtils.getParamTypesString(expectedParamTypes) + "] expected for the annotation [" + annotationType.getSimpleName() + "].";
                        Assert.isTrue((paramTypes.length == expectedParamTypes.length ? 1 : 0) != 0, (String)errorMsg);
                        for (int i = 0; i < paramTypes.length; ++i) {
                            Assert.isTrue((boolean)expectedParamTypes[i].isAssignableFrom(paramTypes[i]), (String)errorMsg);
                        }
                    }
                }
            });
        }
        return mi;
    }

    public static MethodInvoker getMethodInvokerByAnnotation(final Class<? extends Annotation> annotationType, Object target) {
        Class targetClass;
        Assert.notNull((Object)target, (String)"Target must not be null");
        Assert.notNull(annotationType, (String)"AnnotationType must not be null");
        Assert.isTrue((boolean)ObjectUtils.containsElement((Object[])annotationType.getAnnotation(Target.class).value(), (Object)((Object)ElementType.METHOD)), (String)("Annotation [" + annotationType + "] is not a Method-level annotation."));
        Class clazz = targetClass = target instanceof Advised ? ((Advised)target).getTargetSource().getTargetClass() : target.getClass();
        if (targetClass == null) {
            return null;
        }
        final AtomicReference annotatedMethod = new AtomicReference();
        ReflectionUtils.doWithMethods((Class)targetClass, (ReflectionUtils.MethodCallback)new ReflectionUtils.MethodCallback(){

            public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {
                Annotation annotation = AnnotationUtils.findAnnotation((Method)method, (Class)annotationType);
                if (annotation != null) {
                    Assert.isNull(annotatedMethod.get(), (String)("found more than one method on target class [" + targetClass.getSimpleName() + "] with the annotation type [" + annotationType.getSimpleName() + "]."));
                    annotatedMethod.set(method);
                }
            }
        });
        Method method = (Method)annotatedMethod.get();
        if (method == null) {
            return null;
        }
        return new SimpleMethodInvoker(target, (Method)annotatedMethod.get());
    }

    public static <C, T> MethodInvoker getMethodInvokerForSingleArgument(Object target) {
        final AtomicReference methodHolder = new AtomicReference();
        ReflectionUtils.doWithMethods(target.getClass(), (ReflectionUtils.MethodCallback)new ReflectionUtils.MethodCallback(){

            public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {
                if ((method.getModifiers() & 1) == 0 || method.isBridge()) {
                    return;
                }
                if (method.getParameterTypes() == null || method.getParameterTypes().length != 1) {
                    return;
                }
                if (method.getReturnType().equals(Void.TYPE) || ReflectionUtils.isEqualsMethod((Method)method)) {
                    return;
                }
                Assert.state((methodHolder.get() == null ? 1 : 0) != 0, (String)"More than one non-void public method detected with single argument.");
                methodHolder.set(method);
            }
        });
        Method method = (Method)methodHolder.get();
        return new SimpleMethodInvoker(target, method);
    }
}

