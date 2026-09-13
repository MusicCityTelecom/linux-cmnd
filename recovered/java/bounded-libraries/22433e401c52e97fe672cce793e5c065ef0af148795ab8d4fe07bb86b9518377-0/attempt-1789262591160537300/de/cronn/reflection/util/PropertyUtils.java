/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.jetbrains.annotations.VisibleForTesting
 *  org.objenesis.ObjenesisHelper
 */
package de.cronn.reflection.util;

import de.cronn.reflection.util.Assert;
import de.cronn.reflection.util.ClassUtils;
import de.cronn.reflection.util.ClassValues;
import de.cronn.reflection.util.MethodCaptor;
import de.cronn.reflection.util.PropertyDescriptorCache;
import de.cronn.reflection.util.RecordSupport;
import de.cronn.reflection.util.ReflectionRuntimeException;
import de.cronn.reflection.util.TypedPropertyGetter;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.VisibleForTesting;
import org.objenesis.ObjenesisHelper;

public final class PropertyUtils {
    private static final ClassValue<PropertyDescriptorCache<?>> cache = ClassValues.create(PropertyDescriptorCache::new);

    private PropertyUtils() {
    }

    @Nullable
    public static PropertyDescriptor getPropertyDescriptorByName(Object bean, String propertyName) {
        return PropertyUtils.getPropertyDescriptorByName(ClassUtils.getRealClass(bean), propertyName);
    }

    @Nullable
    public static PropertyDescriptor getPropertyDescriptorByName(Class<?> beanClass, String propertyName) {
        PropertyDescriptorCache<?> propertyDescriptorCache = PropertyUtils.getCache(beanClass);
        return propertyDescriptorCache.getDescriptorByName(propertyName);
    }

    @NotNull
    public static PropertyDescriptor getPropertyDescriptorByNameOrThrow(Object bean, String propertyName) {
        Class<Object> beanClass = ClassUtils.getRealClass(bean);
        return PropertyUtils.getPropertyDescriptorByNameOrThrow(beanClass, propertyName);
    }

    @NotNull
    public static PropertyDescriptor getPropertyDescriptorByNameOrThrow(Class<?> beanClass, String propertyName) {
        PropertyDescriptor propertyDescriptor = PropertyUtils.getPropertyDescriptorByName(beanClass, propertyName);
        Assert.notNull(propertyDescriptor, () -> String.format("Property '%s' not found for '%s'", propertyName, beanClass.getSimpleName()));
        return propertyDescriptor;
    }

    public static Collection<PropertyDescriptor> getPropertyDescriptors(Class<?> type) {
        PropertyDescriptorCache<?> propertyDescriptorCache = PropertyUtils.getCache(type);
        return propertyDescriptorCache.getDescriptors();
    }

    public static Collection<PropertyDescriptor> getPropertyDescriptors(Object object) {
        return PropertyUtils.getPropertyDescriptors(ClassUtils.getRealClass(object));
    }

    public static <A extends Annotation> Map<PropertyDescriptor, A> getPropertyDescriptorsWithAnnotation(Object object, Class<A> annotationClass) {
        Class<Object> objectClass = ClassUtils.getRealClass(object);
        return PropertyUtils.getPropertyDescriptorsWithAnnotation(objectClass, annotationClass);
    }

    public static <A extends Annotation> Map<PropertyDescriptor, A> getPropertyDescriptorsWithAnnotation(Class<?> type, Class<A> annotationClass) {
        PropertyDescriptorCache<?> propertyDescriptorCache = PropertyUtils.getCache(type);
        return propertyDescriptorCache.getDescriptorsForAnnotation(annotationClass);
    }

    static <T> PropertyDescriptorCache<T> getCache(Class<T> type) {
        return cache.get(type);
    }

    public static <T> T copyNonDefaultValues(T source, T destination) {
        return PropertyUtils.copyNonDefaultValues(source, destination, Collections.emptySet());
    }

    public static <T> T copyNonDefaultValues(T source, T destination, PropertyDescriptor ... excludedProperties) {
        return PropertyUtils.copyNonDefaultValues(source, destination, Stream.of(excludedProperties).collect(Collectors.toSet()));
    }

    public static <T> T copyNonDefaultValues(T source, T destination, Collection<PropertyDescriptor> excludedProperties) {
        PropertyUtils.getPropertyDescriptors(source).stream().filter(property -> !excludedProperties.contains(property)).filter(PropertyUtils::isFullyAccessible).filter(propertyDescriptor -> !PropertyUtils.hasDefaultValue(source, propertyDescriptor)).forEach(propertyDescriptor -> PropertyUtils.copyValue(source, destination, propertyDescriptor));
        return destination;
    }

    public static <T> Object copyValue(T source, T destination, PropertyDescriptor propertyDescriptor) {
        T value = PropertyUtils.read(source, propertyDescriptor);
        PropertyUtils.write(destination, propertyDescriptor, value);
        return value;
    }

    public static <T> boolean hasDefaultValue(T bean, PropertyDescriptor propertyDescriptor) {
        T value = PropertyUtils.read(bean, propertyDescriptor);
        Class<T> beanClass = ClassUtils.getRealClass(bean);
        return PropertyUtils.isDefaultValue(beanClass, propertyDescriptor, value);
    }

    public static <T> boolean hasSameValue(T a, T b, PropertyDescriptor propertyDescriptor) {
        T valueFromA = PropertyUtils.read(a, propertyDescriptor);
        T valueFromB = PropertyUtils.read(b, propertyDescriptor);
        return Objects.equals(valueFromA, valueFromB);
    }

    public static <T> boolean hasDifferentValue(T a, T b, PropertyDescriptor propertyDescriptor) {
        return !PropertyUtils.hasSameValue(a, b, propertyDescriptor);
    }

    public static <T> boolean isDefaultValue(Class<T> objectClass, TypedPropertyGetter<T, ?> propertyGetter, Object value) {
        return PropertyUtils.isDefaultValue(objectClass, PropertyUtils.getPropertyDescriptor(objectClass, propertyGetter), value);
    }

    public static <T> boolean isDefaultValue(Class<T> objectClass, PropertyDescriptor propertyDescriptor, Object value) {
        Object defaultValue = PropertyUtils.getDefaultValue(objectClass, propertyDescriptor);
        if (defaultValue instanceof Float && value instanceof Float) {
            return ((Float)defaultValue).floatValue() == ((Float)value).floatValue();
        }
        if (defaultValue instanceof Double && value instanceof Double) {
            return ((Double)defaultValue).doubleValue() == ((Double)value).doubleValue();
        }
        return Objects.equals(value, defaultValue);
    }

    public static <T> Object getDefaultValue(Class<T> objectClass, PropertyDescriptor propertyDescriptor) {
        return PropertyUtils.getCache(objectClass).getDefaultValue(propertyDescriptor);
    }

    public static void write(Object destination, String propertyName, Object value) {
        PropertyDescriptor propertyDescriptor = PropertyUtils.getPropertyDescriptorByNameOrThrow(destination, propertyName);
        PropertyUtils.write(destination, propertyDescriptor, value);
    }

    public static <T> void writeIfPropertyExists(Object destination, String propertyName, Supplier<T> valueSupplier) {
        PropertyDescriptor property = PropertyUtils.getPropertyDescriptorByName(destination, propertyName);
        if (property != null) {
            T value = valueSupplier.get();
            PropertyUtils.write(destination, property, value);
        }
    }

    public static void write(Object destination, PropertyDescriptor propertyDescriptor, Object value) {
        PropertyUtils.write(destination, propertyDescriptor, value, false);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static void write(Object destination, PropertyDescriptor propertyDescriptor, Object value, boolean force) {
        try {
            if (!PropertyUtils.isWritable(propertyDescriptor)) {
                if (!force) throw new IllegalArgumentException(propertyDescriptor.getName() + " is not writable");
                PropertyUtils.writeDirectly(destination, propertyDescriptor, value);
                return;
            } else {
                Object[] args = new Object[]{value};
                Method writeMethod = propertyDescriptor.getWriteMethod();
                PropertyUtils.withAccessibleObject(writeMethod, method -> method.invoke(destination, args), force);
            }
            return;
        }
        catch (ReflectiveOperationException | RuntimeException e) {
            throw new ReflectionRuntimeException("Failed to write " + PropertyUtils.getQualifiedPropertyName(destination, propertyDescriptor), e);
        }
    }

    public static void writeDirectly(Object destination, PropertyDescriptor propertyDescriptor, Object value) {
        PropertyUtils.writeDirectly(destination, propertyDescriptor.getName(), value);
    }

    public static void writeDirectly(Object destination, String propertyName, Object value) {
        try {
            Field field = PropertyUtils.findField(destination.getClass(), propertyName);
            PropertyUtils.writeDirectly(destination, field, value);
        }
        catch (NoSuchFieldException e) {
            throw new ReflectionRuntimeException("Failed to write " + PropertyUtils.getQualifiedPropertyName(destination, propertyName), e);
        }
    }

    public static void writeDirectly(Object destination, Field field, Object value) {
        Assert.notNull(destination, () -> "Destination must not be null");
        try {
            PropertyUtils.withAccessibleObject(field, f -> f.set(destination, value));
        }
        catch (ReflectiveOperationException e) {
            throw new ReflectionRuntimeException("Failed to write " + PropertyUtils.getQualifiedPropertyName(destination, field), e);
        }
    }

    private static Field findField(Class<?> objectClass, String propertyName) throws NoSuchFieldException {
        try {
            return objectClass.getDeclaredField(propertyName);
        }
        catch (NoSuchFieldException e) {
            Class<?> superclass = objectClass.getSuperclass();
            if (!superclass.equals(Object.class)) {
                return PropertyUtils.findField(superclass, propertyName);
            }
            throw e;
        }
    }

    public static <T> T readDirectly(Object object, PropertyDescriptor propertyDescriptor) {
        return PropertyUtils.readDirectly(object, propertyDescriptor.getName());
    }

    public static <T> T readDirectly(Object object, String propertyName) {
        try {
            Field field = PropertyUtils.findField(object.getClass(), propertyName);
            return PropertyUtils.readDirectly(object, field);
        }
        catch (NoSuchFieldException e) {
            throw new ReflectionRuntimeException("Failed to read " + PropertyUtils.getQualifiedPropertyName(object, propertyName), e);
        }
    }

    public static <T> T readDirectly(Object object, Field field) {
        try {
            return (T)PropertyUtils.withAccessibleObject(field, f -> {
                Object value = field.get(object);
                return value;
            }, true);
        }
        catch (ReflectiveOperationException e) {
            throw new ReflectionRuntimeException("Failed to read " + PropertyUtils.getQualifiedPropertyName(object, field), e);
        }
    }

    public static <T> T read(Object source, PropertyDescriptor propertyDescriptor) {
        return PropertyUtils.read(source, propertyDescriptor, false);
    }

    public static <T> T read(Object source, PropertyDescriptor propertyDescriptor, boolean force) {
        Object result;
        try {
            if (!PropertyUtils.isReadable(propertyDescriptor)) {
                if (force) {
                    return PropertyUtils.readDirectly(source, propertyDescriptor);
                }
                throw new IllegalArgumentException(String.format("%s must be readable", propertyDescriptor.getName()));
            }
            Method readMethod = propertyDescriptor.getReadMethod();
            result = PropertyUtils.withAccessibleObject(readMethod, method -> readMethod.invoke(source, new Object[0]), force);
        }
        catch (ReflectiveOperationException | RuntimeException e) {
            throw new ReflectionRuntimeException("Failed to read " + PropertyUtils.getQualifiedPropertyName(source, propertyDescriptor), e);
        }
        Object castedResult = result;
        return (T)castedResult;
    }

    public static <T> T readIfPropertyExists(Object source, String propertyName) {
        PropertyDescriptor property = PropertyUtils.getPropertyDescriptorByName(source, propertyName);
        if (property != null) {
            return PropertyUtils.read(source, property);
        }
        return null;
    }

    public static <T> T readProperty(Object entity, PropertyDescriptor propertyDescriptor, Class<T> expectedType) {
        Class<Object> clazz = ClassUtils.getRealClass(entity);
        String propertyName = propertyDescriptor.getName();
        Class<?> propertyType = propertyDescriptor.getPropertyType();
        if (!expectedType.isAssignableFrom(propertyType)) {
            throw new IllegalArgumentException(String.format("%s.%s is of type %s but %s is expected", clazz, propertyName, propertyType, expectedType));
        }
        return PropertyUtils.read(entity, propertyDescriptor);
    }

    @NotNull
    public static <T> PropertyDescriptor getPropertyDescriptor(T bean, TypedPropertyGetter<T, ?> propertyGetter) {
        Class<T> beanClass = ClassUtils.getRealClass(bean);
        return PropertyUtils.getPropertyDescriptor(beanClass, propertyGetter);
    }

    @NotNull
    public static <T> PropertyDescriptor getPropertyDescriptor(Class<T> beanClass, TypedPropertyGetter<T, ?> propertyGetter) {
        Method method = PropertyUtils.getMethod(beanClass, propertyGetter);
        PropertyDescriptor propertyDescriptor = PropertyUtils.getPropertyDescriptorByMethod(beanClass, method);
        Assert.notNull(propertyDescriptor, () -> String.format("Found no property for %s on %s", method, beanClass));
        return propertyDescriptor;
    }

    @NotNull
    public static <T> String getPropertyName(Class<T> beanClass, TypedPropertyGetter<T, ?> propertyGetter) {
        PropertyDescriptor propertyDescriptor = PropertyUtils.getPropertyDescriptor(beanClass, propertyGetter);
        return propertyDescriptor.getName();
    }

    @NotNull
    public static <T> String getPropertyName(T bean, TypedPropertyGetter<T, ?> propertyGetter) {
        Class<T> beanClass = ClassUtils.getRealClass(bean);
        return PropertyUtils.getPropertyName(beanClass, propertyGetter);
    }

    @Nullable
    public static <T> PropertyDescriptor getPropertyDescriptorByMethod(Class<T> beanClass, Method method) {
        PropertyDescriptorCache<T> propertyDescriptorCache = PropertyUtils.getCache(beanClass);
        return propertyDescriptorCache.getDescriptorByMethod(method);
    }

    @Nullable
    public static <T> PropertyDescriptor getPropertyDescriptorByField(Class<T> beanClass, Field field) {
        PropertyDescriptorCache<T> propertyDescriptorCache = PropertyUtils.getCache(beanClass);
        return propertyDescriptorCache.getDescriptorByField(field);
    }

    @NotNull
    public static <T> Method getMethod(Class<T> beanClass, TypedPropertyGetter<T, ?> propertyGetter) {
        PropertyDescriptorCache<T> cache = PropertyUtils.getCache(beanClass);
        return cache.getMethod(propertyGetter);
    }

    public static <T> Method findMethodByGetter(Class<T> beanClass, TypedPropertyGetter<T, ?> propertyGetter) {
        if (RecordSupport.isRecord(beanClass)) {
            return RecordSupport.findMethod(beanClass, propertyGetter);
        }
        MethodCaptor methodCaptor = new MethodCaptor();
        T proxy = PropertyUtils.createProxy(beanClass, methodCaptor);
        propertyGetter.get(proxy);
        return methodCaptor.getCapturedMethod();
    }

    private static <T> T createProxy(Class<T> beanClass, MethodCaptor methodCaptor) {
        Class<T> proxyClass = PropertyUtils.getCache(beanClass).getMethodCapturingProxy();
        try {
            Object proxyInstance = ObjenesisHelper.newInstance(proxyClass);
            PropertyUtils.writeDirectly(proxyInstance, "$methodCaptor", (Object)methodCaptor);
            return (T)proxyInstance;
        }
        catch (IllegalAccessError e) {
            throw new ReflectionRuntimeException("Failed to create proxy on " + beanClass, e);
        }
    }

    public static boolean hasAnnotationOfProperty(Class<?> entityType, PropertyDescriptor descriptor, Class<? extends Annotation> annotationClass) {
        return PropertyUtils.getAnnotationOfProperty(entityType, descriptor, annotationClass) != null;
    }

    public static <T, A extends Annotation> A getAnnotationOfProperty(Class<T> entityType, TypedPropertyGetter<T, ?> propertyGetter, Class<A> annotationClass) {
        PropertyDescriptor propertyDescriptor = PropertyUtils.getPropertyDescriptor(entityType, propertyGetter);
        return PropertyUtils.getAnnotationOfProperty(entityType, propertyDescriptor, annotationClass);
    }

    public static <A extends Annotation> A getAnnotationOfProperty(Object object, PropertyDescriptor descriptor, Class<A> annotationClass) {
        Class<Object> objectClass = ClassUtils.getRealClass(object);
        return PropertyUtils.getAnnotationOfProperty(objectClass, descriptor, annotationClass);
    }

    public static <A extends Annotation> A getAnnotationOfProperty(Class<?> entityType, PropertyDescriptor descriptor, Class<A> annotationClass) {
        PropertyDescriptorCache<?> cache = PropertyUtils.getCache(entityType);
        Map<PropertyDescriptor, A> descriptorsForAnnotation = cache.getDescriptorsForAnnotation(annotationClass);
        return (A)((Annotation)descriptorsForAnnotation.get(descriptor));
    }

    public static boolean isFullyAccessible(PropertyDescriptor descriptor) {
        return PropertyUtils.isReadable(descriptor) && PropertyUtils.isWritable(descriptor);
    }

    public static boolean isWritable(PropertyDescriptor descriptor) {
        return descriptor.getWriteMethod() != null;
    }

    public static boolean isReadable(PropertyDescriptor descriptor) {
        return descriptor.getReadMethod() != null;
    }

    public static boolean isDeclaredInClass(PropertyDescriptor propertyDescriptor, Class<?> entityClass) {
        Method readMethod = propertyDescriptor.getReadMethod();
        return readMethod != null && Objects.equals(readMethod.getDeclaringClass(), entityClass);
    }

    public static boolean hasProperty(Object bean, String propertyName) {
        return PropertyUtils.getPropertyDescriptorByName(bean, propertyName) != null;
    }

    public static boolean hasProperty(Class<?> beanClass, String propertyName) {
        return PropertyUtils.getPropertyDescriptorByName(beanClass, propertyName) != null;
    }

    public static Object getDefaultValueObject(Class<?> type) {
        if (type.isPrimitive()) {
            if (type.equals(Byte.TYPE)) {
                return (byte)0;
            }
            if (type.equals(Character.TYPE)) {
                return Character.valueOf('\u0000');
            }
            if (type.equals(Short.TYPE)) {
                return (short)0;
            }
            if (type.equals(Integer.TYPE)) {
                return 0;
            }
            if (type.equals(Long.TYPE)) {
                return 0L;
            }
            if (type.equals(Float.TYPE)) {
                return Float.valueOf(0.0f);
            }
            if (type.equals(Double.TYPE)) {
                return 0.0;
            }
            if (type.equals(Boolean.TYPE)) {
                return false;
            }
            if (type.equals(Void.TYPE)) {
                return null;
            }
            throw new IllegalArgumentException("Unhandled primitive type: " + type);
        }
        return null;
    }

    public static <T> String getQualifiedPropertyName(T bean, TypedPropertyGetter<T, ?> propertyGetter) {
        Class<T> beanClass = ClassUtils.getRealClass(bean);
        return PropertyUtils.getQualifiedPropertyName(beanClass, propertyGetter);
    }

    public static <T> String getQualifiedPropertyName(Class<T> type, TypedPropertyGetter<T, ?> propertyGetter) {
        PropertyDescriptor propertyDescriptor = PropertyUtils.getPropertyDescriptor(type, propertyGetter);
        return PropertyUtils.getQualifiedPropertyName(type, propertyDescriptor);
    }

    public static String getQualifiedPropertyName(Object bean, PropertyDescriptor propertyDescriptor) {
        return PropertyUtils.getQualifiedPropertyName(ClassUtils.getRealClass(bean), propertyDescriptor);
    }

    public static String getQualifiedPropertyName(Class<?> type, PropertyDescriptor propertyDescriptor) {
        return PropertyUtils.getQualifiedPropertyName(type, propertyDescriptor.getName());
    }

    public static String getQualifiedPropertyName(Class<?> type, String name) {
        return type.getSimpleName() + "." + name;
    }

    static String getQualifiedPropertyName(Object bean, Field field) {
        return PropertyUtils.getQualifiedPropertyName(bean, field.getName());
    }

    private static String getQualifiedPropertyName(Object bean, String name) {
        return PropertyUtils.getQualifiedPropertyName(ClassUtils.getRealClass(bean), name);
    }

    public static boolean isCollectionType(PropertyDescriptor propertyDescriptor) {
        return Collection.class.isAssignableFrom(propertyDescriptor.getPropertyType());
    }

    public static boolean isNotCollectionType(PropertyDescriptor propertyDescriptor) {
        return !PropertyUtils.isCollectionType(propertyDescriptor);
    }

    private static <T extends AccessibleObject> void withAccessibleObject(T accessibleObject, AccessibleObjectConsumer<T> accessibleObjectConsumer) throws ReflectiveOperationException {
        PropertyUtils.withAccessibleObject(accessibleObject, obj -> {
            accessibleObjectConsumer.access(obj);
            return null;
        }, true);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static <T extends AccessibleObject, R> R withAccessibleObject(T accessibleObject, AccessibleObjectFunction<T, R> function, boolean force) throws ReflectiveOperationException {
        boolean accessible = accessibleObject.isAccessible();
        try {
            if (force && !accessible) {
                accessibleObject.setAccessible(true);
            }
            R r = function.access(accessibleObject);
            return r;
        }
        finally {
            if (force && !accessible) {
                accessibleObject.setAccessible(false);
            }
        }
    }

    @VisibleForTesting
    static void removeClassFromCache(Class<?> type) {
        cache.remove(type);
    }

    private static interface AccessibleObjectFunction<T extends AccessibleObject, R> {
        public R access(T var1) throws ReflectiveOperationException;
    }

    private static interface AccessibleObjectConsumer<T extends AccessibleObject> {
        public void access(T var1) throws ReflectiveOperationException;
    }
}

