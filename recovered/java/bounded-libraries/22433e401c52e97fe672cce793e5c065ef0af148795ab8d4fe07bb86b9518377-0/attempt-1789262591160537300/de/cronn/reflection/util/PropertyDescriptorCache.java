/*
 * Decompiled with CFR 0.152.
 */
package de.cronn.reflection.util;

import de.cronn.reflection.util.Assert;
import de.cronn.reflection.util.ClassUtils;
import de.cronn.reflection.util.MethodCaptor;
import de.cronn.reflection.util.PropertyGetter;
import de.cronn.reflection.util.PropertyUtils;
import de.cronn.reflection.util.RecordSupport;
import de.cronn.reflection.util.ReflectionRuntimeException;
import de.cronn.reflection.util.TypedPropertyGetter;
import de.cronn.reflection.util.VoidMethod;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

class PropertyDescriptorCache<T> {
    private final Class<T> originalClass;
    private final AtomicReference<Class<? extends T>> methodCapturingProxy = new AtomicReference();
    private final Map<String, PropertyDescriptor> propertyDescriptorsByName = new LinkedHashMap<String, PropertyDescriptor>();
    private final Map<Field, PropertyDescriptor> propertyDescriptorsByField = new LinkedHashMap<Field, PropertyDescriptor>();
    private final Map<Method, PropertyDescriptor> propertyDescriptorsByMethod = new LinkedHashMap<Method, PropertyDescriptor>();
    private final Map<Class<? extends Annotation>, Map<PropertyDescriptor, Annotation>> propertyDescriptorsByAnnotation = new LinkedHashMap<Class<? extends Annotation>, Map<PropertyDescriptor, Annotation>>();
    private final Map<TypedPropertyGetter<T, ?>, Method> methodByPropertyGetterCache = new ConcurrentHashMap();
    private final Map<VoidMethod<T>, Method> methodByVoidMethodCache = new ConcurrentHashMap<VoidMethod<T>, Method>();
    private final Map<PropertyDescriptor, Object> defaultValues = new ConcurrentHashMap<PropertyDescriptor, Object>();

    PropertyDescriptorCache(Class<T> originalClass) {
        this.originalClass = originalClass;
        for (PropertyDescriptor propertyDescriptor : this.getAllPropertyDescriptors()) {
            Method writeMethod;
            PropertyDescriptor existing = this.propertyDescriptorsByName.putIfAbsent(propertyDescriptor.getName(), propertyDescriptor);
            Assert.isNull(existing, () -> "PropertyDescriptor for name " + propertyDescriptor.getName() + " already exists: " + existing);
            Method readMethod = propertyDescriptor.getReadMethod();
            if (readMethod != null) {
                this.propertyDescriptorsByMethod.put(readMethod, propertyDescriptor);
                this.putAnnotations(propertyDescriptor, readMethod.getAnnotations());
            }
            if ((writeMethod = propertyDescriptor.getWriteMethod()) == null) continue;
            this.propertyDescriptorsByMethod.put(writeMethod, propertyDescriptor);
            this.putAnnotations(propertyDescriptor, writeMethod.getAnnotations());
        }
        for (Field field : this.getFields()) {
            PropertyDescriptor propertyDescriptor = this.propertyDescriptorsByName.get(field.getName());
            if (propertyDescriptor == null) continue;
            PropertyDescriptor existing = this.propertyDescriptorsByField.putIfAbsent(field, propertyDescriptor);
            Assert.isNull(existing, () -> "Property descriptor for " + field + " already exists: " + existing);
            this.putAnnotations(propertyDescriptor, field.getAnnotations());
        }
    }

    Class<? extends T> getMethodCapturingProxy() {
        return this.methodCapturingProxy.updateAndGet(proxyClass -> {
            if (proxyClass == null) {
                return MethodCaptor.createProxyClass(this.originalClass);
            }
            return proxyClass;
        });
    }

    private Set<Field> getFields() {
        ArrayList<Field> allFields = new ArrayList<Field>();
        PropertyDescriptorCache.collectFields(this.originalClass, allFields);
        allFields.sort(Comparator.comparing(Field::getName));
        return new LinkedHashSet<Field>(allFields);
    }

    private static void collectFields(Class<?> type, Collection<Field> collectedFields) {
        Class<?> superclass;
        collectedFields.addAll(Arrays.asList(type.getFields()));
        collectedFields.addAll(Arrays.asList(type.getDeclaredFields()));
        if (!type.equals(Object.class) && (superclass = type.getSuperclass()) != null) {
            PropertyDescriptorCache.collectFields(superclass, collectedFields);
        }
    }

    private void putAnnotations(PropertyDescriptor propertyDescriptor, Annotation[] annotations) {
        for (Annotation annotation : annotations) {
            this.propertyDescriptorsByAnnotation.computeIfAbsent(annotation.annotationType(), k -> new LinkedHashMap()).putIfAbsent(propertyDescriptor, annotation);
        }
    }

    private static Collection<PropertyDescriptor> collectAllPropertyDescriptors(Class<?> type) {
        try {
            TreeMap<String, PropertyDescriptor> propertyDescriptors = new TreeMap<String, PropertyDescriptor>();
            for (PropertyDescriptor propertyDescriptor : PropertyDescriptorCache.collectPropertyDescriptorsOfClass(type)) {
                propertyDescriptors.put(propertyDescriptor.getName(), propertyDescriptor);
            }
            PropertyDescriptorCache.collectPropertyDescriptorsOfInterfaces(type, propertyDescriptors);
            return propertyDescriptors.values();
        }
        catch (IntrospectionException e) {
            throw new ReflectionRuntimeException(e);
        }
    }

    private static Collection<PropertyDescriptor> collectPropertyDescriptorsOfClass(Class<?> type) throws IntrospectionException {
        if (RecordSupport.isRecord(type)) {
            return RecordSupport.collectPropertyDescriptorsOfRecord(type);
        }
        BeanInfo beanInfo = Introspector.getBeanInfo(type);
        return Arrays.asList(beanInfo.getPropertyDescriptors());
    }

    private static void collectPropertyDescriptorsOfInterfaces(Class<?> type, Map<String, PropertyDescriptor> propertyDescriptors) throws IntrospectionException {
        if (type == null || type.equals(Object.class)) {
            return;
        }
        for (Class<?> typeInterface : type.getInterfaces()) {
            BeanInfo beanInfo = Introspector.getBeanInfo(typeInterface);
            for (PropertyDescriptor propertyDescriptor : beanInfo.getPropertyDescriptors()) {
                propertyDescriptors.putIfAbsent(propertyDescriptor.getName(), propertyDescriptor);
            }
            PropertyDescriptorCache.collectPropertyDescriptorsOfInterfaces(typeInterface, propertyDescriptors);
        }
        PropertyDescriptorCache.collectPropertyDescriptorsOfInterfaces(type.getSuperclass(), propertyDescriptors);
    }

    private Collection<PropertyDescriptor> getAllPropertyDescriptors() {
        return PropertyDescriptorCache.collectAllPropertyDescriptors(this.originalClass);
    }

    Collection<PropertyDescriptor> getDescriptors() {
        return Collections.unmodifiableCollection(this.propertyDescriptorsByName.values());
    }

    PropertyDescriptor getDescriptorByMethod(Method method) {
        return this.propertyDescriptorsByMethod.get(method);
    }

    PropertyDescriptor getDescriptorByField(Field field) {
        return this.propertyDescriptorsByField.get(field);
    }

    <A extends Annotation> Map<PropertyDescriptor, A> getDescriptorsForAnnotation(Class<A> annotationClass) {
        Map descriptors = this.propertyDescriptorsByAnnotation.getOrDefault(annotationClass, Collections.emptyMap());
        return Collections.unmodifiableMap(descriptors);
    }

    PropertyDescriptor getDescriptorByName(String propertyName) {
        return this.propertyDescriptorsByName.get(propertyName);
    }

    Object getDefaultValue(PropertyDescriptor propertyDescriptor) {
        return this.defaultValues.computeIfAbsent(propertyDescriptor, this::determineDefaultValue);
    }

    private Object determineDefaultValue(PropertyDescriptor propertyDescriptor) {
        try {
            T defaultObject = ClassUtils.createNewInstance(this.originalClass);
            return PropertyUtils.read(defaultObject, propertyDescriptor);
        }
        catch (RuntimeException e) {
            throw new ReflectionRuntimeException("Failed to determine default value for " + PropertyUtils.getQualifiedPropertyName(this.originalClass, propertyDescriptor), e);
        }
    }

    Method getMethod(TypedPropertyGetter<T, ?> propertyGetter) {
        PropertyDescriptorCache.assertHasNoDeclaredFields(propertyGetter);
        return this.methodByPropertyGetterCache.computeIfAbsent(propertyGetter, getter -> PropertyUtils.findMethodByGetter(this.originalClass, getter));
    }

    Method getMethod(VoidMethod<T> voidMethod) {
        PropertyDescriptorCache.assertHasNoDeclaredFields(voidMethod);
        return this.methodByVoidMethodCache.computeIfAbsent(voidMethod, m -> PropertyUtils.findMethodByGetter(this.originalClass, PropertyDescriptorCache.toPropertyGetter(m)));
    }

    private static void assertHasNoDeclaredFields(Object lambda) {
        if (PropertyDescriptorCache.hasDeclaredFields(lambda)) {
            throw new IllegalArgumentException(lambda + " is call site specific");
        }
    }

    private static boolean hasDeclaredFields(Object lambda) {
        return lambda.getClass().getDeclaredFields().length > 0;
    }

    private static <T> PropertyGetter<T> toPropertyGetter(VoidMethod<T> voidMethod) {
        return bean -> {
            try {
                voidMethod.invoke(bean);
            }
            catch (Exception e) {
                throw new ReflectionRuntimeException(e);
            }
            return null;
        };
    }
}

