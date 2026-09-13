/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.bytebuddy.ByteBuddy
 *  org.jetbrains.annotations.VisibleForTesting
 *  org.objenesis.ObjenesisHelper
 */
package de.cronn.reflection.util;

import de.cronn.reflection.util.Assert;
import de.cronn.reflection.util.ClassUtils;
import de.cronn.reflection.util.ClassValues;
import de.cronn.reflection.util.ReflectionRuntimeException;
import de.cronn.reflection.util.TypedPropertyGetter;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.bytebuddy.ByteBuddy;
import org.jetbrains.annotations.VisibleForTesting;
import org.objenesis.ObjenesisHelper;

final class RecordSupport {
    private static volatile boolean currentJvmIsKnownNotToSupportRecords = false;
    private static WeakReference<Class<?>> cachedRecordClass = new WeakReference<Object>(null);
    private static final ClassValue<Class<?>> dummySubclasses = ClassValues.create(RecordSupport::createDummyProxyClass);

    private RecordSupport() {
    }

    private static Class<?> createDummyProxyClass(Class<?> type) {
        return new ByteBuddy().subclass(type).make().load(RecordSupport.class.getClassLoader()).getLoaded();
    }

    static boolean isRecord(Object bean) {
        return RecordSupport.isRecord(bean.getClass());
    }

    static boolean isRecord(Class<?> beanClass) {
        if (currentJvmIsKnownNotToSupportRecords) {
            return false;
        }
        try {
            Class<?> recordClass = RecordSupport.getRecordClass();
            return recordClass.isAssignableFrom(beanClass);
        }
        catch (ClassNotFoundException e) {
            currentJvmIsKnownNotToSupportRecords = true;
            return false;
        }
    }

    private static Class<?> getRecordClass() throws ClassNotFoundException {
        Class<?> recordClass = (Class<?>)cachedRecordClass.get();
        if (recordClass == null) {
            recordClass = Class.forName("java.lang.Record");
            cachedRecordClass = new WeakReference(recordClass);
        }
        return recordClass;
    }

    static <T> Method findMethod(Class<T> recordClass, TypedPropertyGetter<T, ?> componentAccessor) {
        Object[] uniqueValues = RecordSupport.buildUniqueValues(recordClass);
        try {
            Constructor<T> recordConstructor = RecordSupport.getRecordConstructor(recordClass);
            T record = ClassUtils.createInstance(recordConstructor, uniqueValues);
            Object value = componentAccessor.get(record);
            if (RecordSupport.needsFallbackToComponentSearch(uniqueValues, value)) {
                return RecordSupport.exhaustiveComponentSearch(value, recordClass, componentAccessor, uniqueValues, recordConstructor);
            }
            int componentIndex = ArrayUtils.indexOf(uniqueValues, value);
            Assert.isTrue(componentIndex >= 0, () -> "Failed to find a component in " + recordClass.getName() + " for the given component accessor.");
            return RecordSupport.getRecordComponentAccessor(recordClass, componentIndex);
        }
        catch (ReflectiveOperationException e) {
            throw new ReflectionRuntimeException(e);
        }
    }

    static Stream<RecordComponentInfo> getRecordComponents(Class<?> recordClass) {
        Assert.isTrue(RecordSupport.isRecord(recordClass), () -> recordClass + " is not a record");
        Object[] recordComponents = (Object[])RecordSupport.invokeMethod(recordClass, "getRecordComponents");
        return Arrays.stream(recordComponents).map(recordComponent -> {
            String name = (String)RecordSupport.invokeMethod(recordComponent, "getName");
            Class type = (Class)RecordSupport.invokeMethod(recordComponent, "getType");
            Method accessor = (Method)RecordSupport.invokeMethod(recordComponent, "getAccessor");
            return new RecordComponentInfo(name, type, accessor);
        });
    }

    private static <T> T invokeMethod(Object object, String methodName) {
        try {
            Method method = object.getClass().getMethod(methodName, new Class[0]);
            return (T)method.invoke(object, new Object[0]);
        }
        catch (ReflectiveOperationException e) {
            throw new ReflectionRuntimeException(e);
        }
    }

    static <T> Constructor<T> getRecordConstructor(Class<T> recordClass) throws NoSuchMethodException {
        Class[] constructorTypes = (Class[])RecordSupport.getRecordComponents(recordClass).map(RecordComponentInfo::getType).toArray(Class[]::new);
        return recordClass.getDeclaredConstructor(constructorTypes);
    }

    private static Object[] buildUniqueValues(Class<?> recordClass) {
        return RecordSupport.getRecordComponents(recordClass).map(RecordComponentInfo::getType).map(RecordSupport.uniqueValueBuilder()).toArray(Object[]::new);
    }

    private static Function<Class<?>, Object> uniqueValueBuilder() {
        IdentityHashMap index = new IdentityHashMap();
        return type -> {
            if (type.isAssignableFrom(Boolean.TYPE)) {
                return true;
            }
            if (type.isPrimitive() || type.isAssignableFrom(String.class) || Number.class.isAssignableFrom((Class<?>)type)) {
                long currentIndex = index.compute(type, (k, value) -> value == null ? 1L : value + 1L);
                if (type.isAssignableFrom(String.class)) {
                    return String.valueOf(currentIndex);
                }
                if (type.equals(Byte.TYPE) || type.equals(Byte.class)) {
                    return RecordSupport.safeNumberCast(currentIndex, (byte)currentIndex);
                }
                if (type.equals(Short.TYPE) || type.equals(Short.class)) {
                    return RecordSupport.safeNumberCast(currentIndex, (short)currentIndex);
                }
                if (type.equals(Integer.TYPE) || type.equals(Integer.class)) {
                    return RecordSupport.safeNumberCast(currentIndex, (int)currentIndex);
                }
                if (type.equals(Long.TYPE) || type.equals(Long.class)) {
                    return currentIndex;
                }
                if (type.equals(Float.TYPE) || type.equals(Float.class)) {
                    return RecordSupport.safeNumberCast(currentIndex, Float.valueOf(currentIndex));
                }
                if (type.equals(Double.TYPE) || type.equals(Double.class)) {
                    return RecordSupport.safeNumberCast(currentIndex, Double.valueOf(currentIndex));
                }
                if (type.equals(Character.TYPE)) {
                    return Character.valueOf(RecordSupport.safeNumberCast(currentIndex, (char)currentIndex));
                }
            }
            if (type.isInterface() || Modifier.isAbstract(type.getModifiers())) {
                Class<?> dummyClass = dummySubclasses.get((Class<?>)type);
                return ObjenesisHelper.newInstance(dummyClass);
            }
            return ObjenesisHelper.newInstance((Class)type);
        };
    }

    private static <T extends Number> T safeNumberCast(long currentIndex, T castedValue) {
        return RecordSupport.safeNumberCast(currentIndex, castedValue, castedValue.longValue(), castedValue.getClass());
    }

    private static char safeNumberCast(long currentIndex, char castedValue) {
        return RecordSupport.safeNumberCast(currentIndex, Character.valueOf(castedValue), castedValue, Character.TYPE).charValue();
    }

    private static <T> T safeNumberCast(long currentIndex, T castedValue, long castedValueAsLong, Class<?> valueType) {
        Assert.isTrue(castedValueAsLong == currentIndex, () -> "Having more than " + (currentIndex - 1L) + " record components of type " + valueType.getName() + " is currently not supported");
        return castedValue;
    }

    private static <T> Method exhaustiveComponentSearch(Object currentValue, Class<T> recordClass, TypedPropertyGetter<T, ?> componentAccessor, Object[] uniqueValues, Constructor<T> recordConstructor) throws ReflectiveOperationException {
        int nextIndex;
        Object[] values = Arrays.copyOf(uniqueValues, uniqueValues.length);
        while ((nextIndex = ArrayUtils.indexOf(values, currentValue)) >= 0) {
            values[nextIndex] = RecordSupport.getDefaultValue(currentValue);
            T record = ClassUtils.createInstance(recordConstructor, values);
            Object value = componentAccessor.get(record);
            if (value != values[nextIndex]) continue;
            return RecordSupport.getRecordComponentAccessor(recordClass, nextIndex);
        }
        throw new IllegalArgumentException("Failed to find the component of type " + currentValue.getClass().getName() + " in the record " + recordClass.getName() + " using the provided component accessor.");
    }

    private static Method getRecordComponentAccessor(Class<?> recordClass, int componentIndex) {
        return RecordSupport.getRecordComponents(recordClass).skip(componentIndex).findFirst().map(RecordComponentInfo::getAccessor).orElseThrow(IllegalStateException::new);
    }

    private static Object getDefaultValue(Object value) {
        Assert.isTrue(value instanceof Boolean, () -> "This is currently only expected to happen for boolean types");
        return false;
    }

    private static boolean needsFallbackToComponentSearch(Object[] uniqueValues, Object value) {
        int lastIndex;
        if (!(value instanceof Boolean)) {
            return false;
        }
        int firstIndex = ArrayUtils.indexOf(uniqueValues, value);
        return firstIndex != (lastIndex = ArrayUtils.lastIndexOf(uniqueValues, value));
    }

    static Collection<PropertyDescriptor> collectPropertyDescriptorsOfRecord(Class<?> type) {
        return Stream.concat(Stream.of(RecordSupport.getPropertyDescriptorsOfObject()), RecordSupport.getRecordComponents(type).map(recordComponent -> RecordSupport.toPropertyDescriptor(type, recordComponent))).collect(Collectors.toList());
    }

    private static PropertyDescriptor getPropertyDescriptorsOfObject() {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(Object.class);
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            Assert.isTrue(propertyDescriptors.length == 1, () -> "Expected one property descriptor but got " + propertyDescriptors.length);
            return propertyDescriptors[0];
        }
        catch (IntrospectionException e) {
            throw new ReflectionRuntimeException(e);
        }
    }

    private static PropertyDescriptor toPropertyDescriptor(Class<?> type, RecordComponentInfo recordComponent) {
        try {
            return new PropertyDescriptor(recordComponent.getName(), type, recordComponent.getAccessor().getName(), null);
        }
        catch (IntrospectionException e) {
            throw new ReflectionRuntimeException(e);
        }
    }

    @VisibleForTesting
    static final class ArrayUtils {
        private ArrayUtils() {
        }

        static int indexOf(Object[] values, Object valueToFind) {
            for (int i = 0; i < values.length; ++i) {
                if (!ArrayUtils.areTheSame(values[i], valueToFind)) continue;
                return i;
            }
            return -1;
        }

        static int lastIndexOf(Object[] values, Object valueToFind) {
            for (int i = values.length - 1; i >= 0; --i) {
                if (!ArrayUtils.areTheSame(values[i], valueToFind)) continue;
                return i;
            }
            return -1;
        }

        private static boolean areTheSame(Object one, Object other) {
            if (one instanceof Float || one instanceof Double) {
                return Objects.equals(one, other);
            }
            return one == other;
        }
    }

    static class RecordComponentInfo {
        private final String name;
        private final Class<?> type;
        private final Method accessor;

        RecordComponentInfo(String name, Class<?> type, Method accessor) {
            this.name = name;
            this.type = type;
            this.accessor = accessor;
        }

        public String getName() {
            return this.name;
        }

        public Class<?> getType() {
            return this.type;
        }

        public Method getAccessor() {
            return this.accessor;
        }

        public Object retrieveValueFrom(Object record) {
            try {
                return this.accessor.invoke(record, new Object[0]);
            }
            catch (ReflectiveOperationException e) {
                throw new ReflectionRuntimeException(e);
            }
        }
    }
}

