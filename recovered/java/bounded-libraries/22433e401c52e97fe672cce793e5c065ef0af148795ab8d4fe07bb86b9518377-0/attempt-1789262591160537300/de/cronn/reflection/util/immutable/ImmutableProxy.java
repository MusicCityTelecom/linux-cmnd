/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.bytebuddy.ByteBuddy
 *  net.bytebuddy.description.method.MethodDescription
 *  net.bytebuddy.description.method.MethodDescription$SignatureToken
 *  net.bytebuddy.description.method.MethodList
 *  net.bytebuddy.description.modifier.ModifierContributor$ForField
 *  net.bytebuddy.description.type.TypeDefinition
 *  net.bytebuddy.description.type.TypeDescription
 *  net.bytebuddy.description.type.TypeDescription$Generic
 *  net.bytebuddy.description.type.TypeList$Generic
 *  net.bytebuddy.implementation.ExceptionMethod
 *  net.bytebuddy.implementation.Implementation
 *  net.bytebuddy.implementation.MethodDelegation
 *  net.bytebuddy.matcher.ElementMatcher
 *  net.bytebuddy.matcher.ElementMatcher$Junction
 *  net.bytebuddy.matcher.ElementMatchers
 *  org.jetbrains.annotations.UnmodifiableView
 *  org.jetbrains.annotations.VisibleForTesting
 *  org.objenesis.ObjenesisHelper
 */
package de.cronn.reflection.util.immutable;

import de.cronn.reflection.util.ClassUtils;
import de.cronn.reflection.util.ClassValues;
import de.cronn.reflection.util.PropertyUtils;
import de.cronn.reflection.util.RecordUtils;
import de.cronn.reflection.util.immutable.GenericImmutableProxyForwarder;
import de.cronn.reflection.util.immutable.Immutable;
import de.cronn.reflection.util.immutable.ImmutableProxyForwarderBoolean;
import de.cronn.reflection.util.immutable.ImmutableProxyForwarderInteger;
import de.cronn.reflection.util.immutable.ImmutableProxyForwarderLong;
import de.cronn.reflection.util.immutable.ImmutableProxyForwarderString;
import de.cronn.reflection.util.immutable.ImmutableProxyOption;
import de.cronn.reflection.util.immutable.ReadOnly;
import de.cronn.reflection.util.immutable.collection.DeepImmutableCollection;
import de.cronn.reflection.util.immutable.collection.DeepImmutableList;
import de.cronn.reflection.util.immutable.collection.DeepImmutableMap;
import de.cronn.reflection.util.immutable.collection.DeepImmutableSet;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.net.URI;
import java.nio.file.Path;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.method.MethodList;
import net.bytebuddy.description.modifier.ModifierContributor;
import net.bytebuddy.description.type.TypeDefinition;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.description.type.TypeList;
import net.bytebuddy.implementation.ExceptionMethod;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;
import org.jetbrains.annotations.UnmodifiableView;
import org.jetbrains.annotations.VisibleForTesting;
import org.objenesis.ObjenesisHelper;

public final class ImmutableProxy {
    static final String DELEGATE_FIELD_NAME = "$delegate";
    static final String OPTIONS = "$options";
    private static final ClassValue<Class<?>> immutableProxyClassCache = ClassValues.create(ImmutableProxy::createProxyClass);

    private ImmutableProxy() {
    }

    public static <T> T create(T instance, ImmutableProxyOption ... options) {
        if (ImmutableProxy.isImmutable(instance)) {
            return instance;
        }
        if (instance instanceof List) {
            List<T> immutableList = ImmutableProxy.create((List)instance);
            return (T)immutableList;
        }
        if (instance instanceof Set) {
            Set<T> immutableSet = ImmutableProxy.create((Set)instance);
            return (T)immutableSet;
        }
        if (instance instanceof Map) {
            Map immutableMap = ImmutableProxy.create((Map)instance);
            return (T)immutableMap;
        }
        if (ClassUtils.isRecord(instance)) {
            if (ImmutableProxy.isOptionEnabled(options, ImmutableProxyOption.ALLOW_CLONING_RECORDS)) {
                return RecordUtils.cloneRecord(instance, x$0 -> ImmutableProxy.create(x$0, new ImmutableProxyOption[0]));
            }
            throw new IllegalArgumentException(instance.getClass() + " is a record that potentially contains mutable components. Consider using ImmutableProxy.create(bean, " + ImmutableProxyOption.class.getSimpleName() + "." + (Object)((Object)ImmutableProxyOption.ALLOW_CLONING_RECORDS) + ") to enable cloning of such records.");
        }
        Class<T> proxyClass = ImmutableProxy.getOrCreateProxyClass(instance);
        Object proxy = ObjenesisHelper.newInstance(proxyClass);
        PropertyUtils.writeDirectly(proxy, DELEGATE_FIELD_NAME, instance);
        if (options != null && options.length > 0) {
            PropertyUtils.writeDirectly(proxy, OPTIONS, (Object)options);
        }
        return (T)proxy;
    }

    public static <T> @UnmodifiableView Collection<T> create(Collection<T> collection) {
        return new DeepImmutableCollection<T>(collection);
    }

    public static <T> @UnmodifiableView List<T> create(List<T> list) {
        return new DeepImmutableList<T>(list);
    }

    public static <T> @UnmodifiableView Set<T> create(Set<T> set) {
        return new DeepImmutableSet<T>(set);
    }

    public static <K, V> @UnmodifiableView Map<K, V> create(Map<K, V> map) {
        return new DeepImmutableMap<K, V>(map);
    }

    public static <T> T unwrap(T immutableProxy) {
        if (!ImmutableProxy.isImmutableProxy(immutableProxy)) {
            return immutableProxy;
        }
        return PropertyUtils.readDirectly(immutableProxy, DELEGATE_FIELD_NAME);
    }

    static boolean isImmutable(Object value) {
        if (value == null) {
            return true;
        }
        return ImmutableProxy.isImmutable(value.getClass());
    }

    public static boolean isImmutable(Class<?> type) {
        if (ImmutableProxy.isImmutableProxyClass(type)) {
            return true;
        }
        if (String.class.isAssignableFrom(type)) {
            return true;
        }
        if (Byte.class.isAssignableFrom(type) || Byte.TYPE.isAssignableFrom(type)) {
            return true;
        }
        if (Short.class.isAssignableFrom(type) || Short.TYPE.isAssignableFrom(type)) {
            return true;
        }
        if (Integer.class.isAssignableFrom(type) || Integer.TYPE.isAssignableFrom(type)) {
            return true;
        }
        if (Long.class.isAssignableFrom(type) || Long.TYPE.isAssignableFrom(type)) {
            return true;
        }
        if (Float.class.isAssignableFrom(type) || Float.TYPE.isAssignableFrom(type)) {
            return true;
        }
        if (Double.class.isAssignableFrom(type) || Double.TYPE.isAssignableFrom(type)) {
            return true;
        }
        if (Boolean.class.isAssignableFrom(type) || Boolean.TYPE.isAssignableFrom(type)) {
            return true;
        }
        if (Character.class.isAssignableFrom(type) || Character.TYPE.isAssignableFrom(type)) {
            return true;
        }
        if (BigDecimal.class.isAssignableFrom(type)) {
            return true;
        }
        if (Temporal.class.isAssignableFrom(type)) {
            return true;
        }
        if (TemporalAmount.class.isAssignableFrom(type)) {
            return true;
        }
        if (UUID.class.isAssignableFrom(type)) {
            return true;
        }
        if (File.class.isAssignableFrom(type)) {
            return true;
        }
        if (Path.class.isAssignableFrom(type)) {
            return true;
        }
        if (URI.class.isAssignableFrom(type)) {
            return true;
        }
        if (ImmutableProxy.isEnumType(type)) {
            return true;
        }
        if (ClassUtils.isRecord(type)) {
            return RecordUtils.hasOnlyImmutableRecordComponents(type);
        }
        return false;
    }

    private static boolean isEnumType(Class<?> type) {
        return type.isEnum() || type.getSuperclass() != null && type.getSuperclass().isEnum();
    }

    private static <T> Class<? extends T> getOrCreateProxyClass(T instance) {
        Class<T> realClass = ClassUtils.getRealClass(instance);
        return immutableProxyClassCache.get(realClass);
    }

    private static <T> Class<? extends T> createProxyClass(Class<T> clazz) {
        ImmutableProxy.assertPublicMethodsAreNotFinal(clazz);
        return new ByteBuddy().subclass(clazz).implement(new Type[]{Immutable.class}).defineField(DELEGATE_FIELD_NAME, clazz, new ModifierContributor.ForField[0]).defineField(OPTIONS, ImmutableProxyOption[].class, new ModifierContributor.ForField[0]).method((ElementMatcher)ElementMatchers.any()).intercept(ExceptionMethod.throwing(UnsupportedOperationException.class, (String)("This instance is immutable. Annotate the method with @" + ReadOnly.class.getSimpleName() + " if this is a false-positive."))).method(ImmutableProxy.isReadOnlyMethod()).intercept((Implementation)MethodDelegation.to(GenericImmutableProxyForwarder.class)).method((ElementMatcher)ImmutableProxy.isReadOnlyMethod().and((ElementMatcher)ElementMatchers.returns(Long.class).or((ElementMatcher)ElementMatchers.returns(Long.TYPE)))).intercept((Implementation)MethodDelegation.to(ImmutableProxyForwarderLong.class)).method((ElementMatcher)ImmutableProxy.isReadOnlyMethod().and((ElementMatcher)ElementMatchers.returns(Integer.class).or((ElementMatcher)ElementMatchers.returns(Integer.TYPE)))).intercept((Implementation)MethodDelegation.to(ImmutableProxyForwarderInteger.class)).method((ElementMatcher)ImmutableProxy.isReadOnlyMethod().and((ElementMatcher)ElementMatchers.returns(Boolean.class).or((ElementMatcher)ElementMatchers.returns(Boolean.TYPE)))).intercept((Implementation)MethodDelegation.to(ImmutableProxyForwarderBoolean.class)).method((ElementMatcher)ImmutableProxy.isReadOnlyMethod().and((ElementMatcher)ElementMatchers.returns(String.class))).intercept((Implementation)MethodDelegation.to(ImmutableProxyForwarderString.class)).make().load(ImmutableProxy.class.getClassLoader()).getLoaded();
    }

    private static <T> void assertPublicMethodsAreNotFinal(Class<T> clazz) {
        for (Method method : clazz.getMethods()) {
            if (!Modifier.isFinal(method.getModifiers()) || method.getDeclaringClass().equals(Object.class) || ImmutableProxy.isHashCodeMethod(method) || ImmutableProxy.isEqualsMethod(method) || ImmutableProxy.isToStringMethod(method) || ImmutableProxy.isCloneMethod(method) || method.getDeclaredAnnotation(ReadOnly.class) != null) continue;
            throw new IllegalArgumentException("Cannot create an immutable proxy for " + clazz + ". Method " + method + " is final.");
        }
    }

    private static boolean isHashCodeMethod(Method method) {
        return method.getName().equals("hashCode") && method.getReturnType().equals(Integer.TYPE) && method.getParameterCount() == 0;
    }

    private static boolean isEqualsMethod(Method method) {
        return method.getName().equals("equals") && method.getReturnType().equals(Boolean.TYPE) && method.getParameterCount() == 1 && method.getParameterTypes()[0].equals(Object.class);
    }

    static boolean isToStringMethod(Method method) {
        return method.getName().equals("toString") && method.getReturnType().equals(String.class) && method.getParameterCount() == 0;
    }

    static boolean isCloneMethod(Method method) {
        return method.getName().equals("clone") && method.getParameterCount() == 0;
    }

    private static ElementMatcher.Junction<MethodDescription> isReadOnlyMethod() {
        return ElementMatchers.not((ElementMatcher)ElementMatchers.isSetter()).and((ElementMatcher)ElementMatchers.isGetter().or((ElementMatcher)ElementMatchers.isHashCode()).or((ElementMatcher)ElementMatchers.isEquals()).or((ElementMatcher)ElementMatchers.isToString()).or((ElementMatcher)ElementMatchers.isClone()).or((ElementMatcher)ElementMatchers.isDeclaredBy(Object.class)).or(ImmutableProxy.isAnnotatedWith(ReadOnly.class)));
    }

    private static ElementMatcher<MethodDescription> isAnnotatedWith(Class<? extends Annotation> annotation) {
        return target -> {
            TypeDefinition type = target.getDeclaringType();
            MethodDescription.SignatureToken methodSignature = target.asSignatureToken();
            return ImmutableProxy.isAnnotatedWith(methodSignature, type, annotation);
        };
    }

    private static boolean isAnnotatedWith(MethodDescription.SignatureToken methodSignature, TypeDefinition type, Class<? extends Annotation> annotation) {
        if (type == null || type.equals(TypeDescription.OBJECT)) {
            return false;
        }
        if (ImmutableProxy.hasMethodAnnotatedWith(methodSignature, type, annotation)) {
            return true;
        }
        TypeList.Generic interfaces = type.getInterfaces();
        for (TypeDefinition interfaceType : interfaces) {
            if (ImmutableProxy.hasMethodAnnotatedWith(methodSignature, interfaceType, annotation)) {
                return true;
            }
            for (TypeDescription.Generic interfaceSuperclass : interfaceType.getInterfaces()) {
                if (!ImmutableProxy.isAnnotatedWith(methodSignature, (TypeDefinition)interfaceSuperclass, annotation)) continue;
                return true;
            }
        }
        return ImmutableProxy.isAnnotatedWith(methodSignature, (TypeDefinition)type.getSuperClass(), annotation);
    }

    private static boolean hasMethodAnnotatedWith(MethodDescription.SignatureToken methodSignature, TypeDefinition type, Class<? extends Annotation> annotation) {
        return !((MethodList)type.getDeclaredMethods().filter((ElementMatcher)ElementMatchers.hasMethodName((String)methodSignature.getName()).and((ElementMatcher)ElementMatchers.takesArguments((Iterable)methodSignature.getParameterTypes())).and((ElementMatcher)ElementMatchers.isAnnotatedWith(annotation)))).isEmpty();
    }

    public static boolean isImmutableProxy(Object object) {
        if (object == null) {
            return false;
        }
        return ImmutableProxy.isImmutableProxyClass(object.getClass());
    }

    public static boolean isImmutableProxyClass(Class<?> beanClass) {
        return Immutable.class.isAssignableFrom(beanClass);
    }

    @VisibleForTesting
    static void removeClassFromCache(Class<?> type) {
        immutableProxyClassCache.remove(type);
    }

    private static boolean isOptionEnabled(ImmutableProxyOption[] options, ImmutableProxyOption optionToTest) {
        if (options == null) {
            return false;
        }
        for (ImmutableProxyOption option : options) {
            if (option != optionToTest) continue;
            return true;
        }
        return false;
    }
}

