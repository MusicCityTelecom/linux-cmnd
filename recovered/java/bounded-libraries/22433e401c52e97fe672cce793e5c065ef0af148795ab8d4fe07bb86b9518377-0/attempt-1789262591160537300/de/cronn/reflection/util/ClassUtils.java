/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package de.cronn.reflection.util;

import de.cronn.reflection.util.Assert;
import de.cronn.reflection.util.ClassValues;
import de.cronn.reflection.util.MethodSignature;
import de.cronn.reflection.util.PropertyDescriptorCache;
import de.cronn.reflection.util.PropertyGetter;
import de.cronn.reflection.util.PropertyUtils;
import de.cronn.reflection.util.RecordSupport;
import de.cronn.reflection.util.ReflectionRuntimeException;
import de.cronn.reflection.util.VoidMethod;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;

public final class ClassUtils {
    private static final String CGLIB_JAVASSIST_CLASS_SEPARATOR = "$$";
    private static final String BYTE_BUDDY_CLASS_SEPARATOR = "$ByteBuddy$";
    private static final String HIBERNATE_PROXY_CLASS_SEPARATOR = "$HibernateProxy$";
    private static final ClassValue<Set<MethodSignature>> methodsSignaturesCache = ClassValues.create(ClassUtils::getAllDeclaredMethodSignatures);

    private ClassUtils() {
    }

    public static <T> Class<T> getRealClass(T object) {
        if (object instanceof Class) {
            throw new IllegalArgumentException("The provided object is already a class: " + object + ". You probably want to call ClassUtils.getRealClass(Class) instead.");
        }
        Class<?> entityClass = object.getClass();
        return ClassUtils.getRealClass(entityClass);
    }

    public static <T> Class<T> getRealClass(Class<T> clazz) {
        if (ClassUtils.isProxyClass(clazz)) {
            if (Proxy.isProxyClass(clazz)) {
                Class[] interfaces = clazz.getInterfaces();
                Assert.isTrue(interfaces.length == 1, () -> "Unexpected number of interfaces: " + interfaces.length);
                Class proxiedInterface = interfaces[0];
                return proxiedInterface;
            }
            Class<T> superclass = clazz.getSuperclass();
            return ClassUtils.getRealClass(superclass);
        }
        return clazz;
    }

    public static <T> T createNewInstanceLike(T source) {
        if (source == null) {
            return null;
        }
        Class<T> sourceClass = ClassUtils.getRealClass(source);
        return ClassUtils.createNewInstance(sourceClass);
    }

    public static boolean isFromPackage(Class<?> clazz, String packageName) {
        Package aPackage = clazz.getPackage();
        return aPackage != null && aPackage.getName().equals(packageName);
    }

    public static boolean isRecord(Object bean) {
        return RecordSupport.isRecord(bean);
    }

    public static boolean isRecord(Class<?> beanType) {
        return RecordSupport.isRecord(beanType);
    }

    public static <T> T createNewInstance(Class<T> sourceClass) {
        try {
            Constructor<T> constructor = sourceClass.getDeclaredConstructor(new Class[0]);
            return ClassUtils.createInstance(constructor, new Object[0]);
        }
        catch (ReflectiveOperationException e) {
            throw new ReflectionRuntimeException("Failed to construct an instance of " + sourceClass, e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static <T> T createInstance(Constructor<T> constructor, Object ... initArgs) throws ReflectiveOperationException {
        boolean accessible = constructor.isAccessible();
        try {
            if (!accessible) {
                constructor.setAccessible(true);
            }
            T t = constructor.newInstance(initArgs);
            return t;
        }
        finally {
            if (!accessible) {
                constructor.setAccessible(false);
            }
        }
    }

    @NotNull
    public static <T> String getVoidMethodName(T bean, VoidMethod<T> voidMethod) {
        Class<T> beanClass = ClassUtils.getRealClass(bean);
        return ClassUtils.getVoidMethodName(beanClass, voidMethod);
    }

    @NotNull
    public static <T> String getVoidMethodName(Class<T> beanClass, VoidMethod<T> voidMethod) {
        Method method = ClassUtils.getVoidMethod(beanClass, voidMethod);
        return method.getName();
    }

    @NotNull
    public static <T> Method getVoidMethod(Class<T> beanClass, VoidMethod<T> voidMethod) {
        PropertyDescriptorCache<T> cache = PropertyUtils.getCache(beanClass);
        return cache.getMethod(voidMethod);
    }

    @NotNull
    public static <T> Method getMethod(Class<T> beanClass, PropertyGetter<T> getterMethod) {
        PropertyDescriptorCache<T> cache = PropertyUtils.getCache(beanClass);
        return cache.getMethod(getterMethod);
    }

    @NotNull
    public static <T> Method getMethod(T bean, PropertyGetter<T> getterMethod) {
        Class<T> beanClass = ClassUtils.getRealClass(bean);
        return ClassUtils.getMethod(beanClass, getterMethod);
    }

    @NotNull
    public static <T> String getMethodName(T bean, PropertyGetter<T> getterMethod) {
        Class<T> beanClass = ClassUtils.getRealClass(bean);
        return ClassUtils.getMethodName(beanClass, getterMethod);
    }

    @NotNull
    public static <T> String getMethodName(Class<T> beanClass, PropertyGetter<T> getterMethod) {
        Method method = ClassUtils.getMethod(beanClass, getterMethod);
        return method.getName();
    }

    @NotNull
    public static <T> Method getMethod(Class<T> beanClass, VoidMethod<T> voidMethod) {
        return ClassUtils.getVoidMethod(beanClass, voidMethod);
    }

    @NotNull
    public static <T> String getMethodName(T bean, VoidMethod<T> voidMethod) {
        return ClassUtils.getVoidMethodName(bean, voidMethod);
    }

    @NotNull
    public static <T> String getMethodName(Class<T> beanClass, VoidMethod<T> voidMethod) {
        return ClassUtils.getVoidMethodName(beanClass, voidMethod);
    }

    public static boolean isProxy(Object object) {
        return object != null && ClassUtils.isProxyClass(object.getClass());
    }

    public static boolean isProxyClass(Class<?> clazz) {
        if (clazz == null) {
            return false;
        }
        if (Proxy.isProxyClass(clazz)) {
            return true;
        }
        return ClassUtils.matchesWellKnownProxyClassNamePattern(clazz.getName());
    }

    static boolean matchesWellKnownProxyClassNamePattern(String className) {
        return className.contains(BYTE_BUDDY_CLASS_SEPARATOR) || className.contains(CGLIB_JAVASSIST_CLASS_SEPARATOR) || className.contains(HIBERNATE_PROXY_CLASS_SEPARATOR);
    }

    public static boolean haveSameSignature(Method oneMethod, Method otherMethod) {
        return new MethodSignature(oneMethod).equals(new MethodSignature(otherMethod));
    }

    public static List<Method> findMethodsByArgumentTypes(Class<?> classToSearchIn, Class<?> ... argumentTypes) {
        return Stream.of(classToSearchIn.getMethods()).filter(method -> Arrays.equals(method.getParameterTypes(), argumentTypes)).collect(Collectors.toList());
    }

    public static boolean hasMethodWithSameSignature(Class<?> clazz, Method method) {
        Set<MethodSignature> methods = methodsSignaturesCache.get(clazz);
        return methods.contains(new MethodSignature(method));
    }

    public static Set<Method> getAllDeclaredMethods(Class<?> clazz) {
        LinkedHashSet<Method> methods = new LinkedHashSet<Method>(Arrays.asList(clazz.getDeclaredMethods()));
        if (clazz.getSuperclass() != null && !clazz.getSuperclass().equals(Object.class)) {
            methods.addAll(ClassUtils.getAllDeclaredMethods(clazz.getSuperclass()));
        }
        for (Class<?> interfaceClass : clazz.getInterfaces()) {
            methods.addAll(ClassUtils.getAllDeclaredMethods(interfaceClass));
        }
        return Collections.unmodifiableSet(methods);
    }

    public static Set<MethodSignature> getAllDeclaredMethodSignatures(Class<?> clazz) {
        return ClassUtils.getAllDeclaredMethods(clazz).stream().map(MethodSignature::new).collect(Collectors.toCollection(TreeSet::new));
    }

    public static <A extends Annotation> A findAnnotation(Method method, Class<A> annotationType) {
        A annotation = method.getAnnotation(annotationType);
        if (annotation != null) {
            return annotation;
        }
        return ClassUtils.findAnnotation(method.getDeclaringClass(), method, annotationType);
    }

    private static <A extends Annotation> A findAnnotation(Class<?> declaringClass, Method method, Class<A> annotationType) {
        if (declaringClass == null || declaringClass.equals(Object.class)) {
            return null;
        }
        if (declaringClass.getSuperclass() != null) {
            for (GenericDeclaration genericDeclaration : declaringClass.getSuperclass().getMethods()) {
                A a;
                if (!ClassUtils.isOverride(method, (Method)genericDeclaration) || (a = ClassUtils.findAnnotation((Method)genericDeclaration, annotationType)) == null) continue;
                return a;
            }
        }
        for (GenericDeclaration genericDeclaration : declaringClass.getInterfaces()) {
            for (Method methodCandidate : ((Class)genericDeclaration).getDeclaredMethods()) {
                A annotation2;
                if (!ClassUtils.isOverride(method, methodCandidate) || (annotation2 = ClassUtils.findAnnotation(methodCandidate, annotationType)) == null) continue;
                return annotation2;
            }
            A a = ClassUtils.findAnnotation(genericDeclaration, method, annotationType);
            if (a == null) continue;
            return a;
        }
        return ClassUtils.findAnnotation(declaringClass.getSuperclass(), method, annotationType);
    }

    private static boolean isOverride(Method method, Method candidate) {
        return method.getName().equals(candidate.getName()) && candidate.getParameterCount() == method.getParameterCount() && Arrays.equals(candidate.getParameterTypes(), method.getParameterTypes());
    }
}

