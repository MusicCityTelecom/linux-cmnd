/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.errorprone.annotations.CanIgnoreReturnValue
 *  javax.annotation.Nonnull
 *  lombok.Generated
 *  org.apache.commons.lang3.ArrayUtils
 */
package org.apereo.cas.util.spring.beans;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import javax.annotation.Nonnull;
import lombok.Generated;
import org.apache.commons.lang3.ArrayUtils;
import org.apereo.cas.util.ResourceUtils;

public interface BeanSupplier<T>
extends Supplier<T> {
    public static final String PROXY_BEAN_TOSTRING_PREFIX = "ProxyBean-";

    public static <T> BeanSupplier<T> of(Class<T> clazz) {
        return new DefaultBeanSupplier<T>(clazz);
    }

    public static boolean isProxy(Object result) {
        return result != null && Proxy.isProxyClass(result.getClass()) && result.toString().startsWith(PROXY_BEAN_TOSTRING_PREFIX);
    }

    public static boolean isNotProxy(Object result) {
        return result != null && !BeanSupplier.isProxy(result);
    }

    @Override
    public T get();

    default public BeanSupplier<T> alwaysMatch() {
        return this.when(true);
    }

    default public BeanSupplier<T> neverMatch() {
        return this.when(false);
    }

    public BeanSupplier<T> when(Supplier<Boolean> var1);

    default public BeanSupplier<T> when(boolean rawValue) {
        return this.when(() -> rawValue);
    }

    default public BeanSupplier<T> ifExists(String resource) {
        return this.when(() -> ResourceUtils.doesResourceExist(resource));
    }

    default public BeanSupplier<T> and(Supplier<Boolean> conditionSupplier) {
        return this.when(conditionSupplier);
    }

    public BeanSupplier<T> supply(Supplier<T> var1);

    public BeanSupplier<T> otherwise(Supplier<T> var1);

    public BeanSupplier<T> otherwiseProxy();

    public static class ProxiedBeanSupplier<T>
    implements Supplier<T> {
        private static final Map<Class, Object> TYPES_AND_VALUES;
        private static final Map<String, Object> PROXIES;
        private final Class<T> clazz;

        @Override
        public T get() {
            if (!this.clazz.isInterface()) {
                throw new IllegalArgumentException("Cannot create bean supplier proxy for non-interface type " + this.clazz.getSimpleName());
            }
            return (T)PROXIES.computeIfAbsent(this.clazz.getName(), s -> Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{this.clazz}, (proxy, method, args) -> {
                if (method.getName().equals("toString")) {
                    return BeanSupplier.PROXY_BEAN_TOSTRING_PREFIX + this.clazz.getName();
                }
                Class<?> returnType = method.getReturnType();
                return TYPES_AND_VALUES.getOrDefault(returnType, null);
            }));
        }

        @Generated
        public ProxiedBeanSupplier(Class<T> clazz) {
            this.clazz = clazz;
        }

        static {
            PROXIES = new ConcurrentHashMap<String, Object>();
            TYPES_AND_VALUES = new HashMap<Class, Object>();
            TYPES_AND_VALUES.put(Object[].class, ArrayUtils.EMPTY_OBJECT_ARRAY);
            TYPES_AND_VALUES.put(Class[].class, ArrayUtils.EMPTY_CLASS_ARRAY);
            TYPES_AND_VALUES.put(String[].class, ArrayUtils.EMPTY_STRING_ARRAY);
            TYPES_AND_VALUES.put(Integer[].class, ArrayUtils.EMPTY_INTEGER_OBJECT_ARRAY);
            TYPES_AND_VALUES.put(int[].class, ArrayUtils.EMPTY_INT_ARRAY);
            TYPES_AND_VALUES.put(Byte[].class, ArrayUtils.EMPTY_BYTE_OBJECT_ARRAY);
            TYPES_AND_VALUES.put(byte[].class, ArrayUtils.EMPTY_BYTE_ARRAY);
            TYPES_AND_VALUES.put(Boolean[].class, ArrayUtils.EMPTY_BOOLEAN_OBJECT_ARRAY);
            TYPES_AND_VALUES.put(boolean[].class, ArrayUtils.EMPTY_BOOLEAN_ARRAY);
            TYPES_AND_VALUES.put(Double[].class, ArrayUtils.EMPTY_DOUBLE_OBJECT_ARRAY);
            TYPES_AND_VALUES.put(double[].class, ArrayUtils.EMPTY_DOUBLE_ARRAY);
            TYPES_AND_VALUES.put(Long[].class, ArrayUtils.EMPTY_LONG_OBJECT_ARRAY);
            TYPES_AND_VALUES.put(long[].class, ArrayUtils.EMPTY_LONG_ARRAY);
            TYPES_AND_VALUES.put(Float[].class, ArrayUtils.EMPTY_FLOAT_OBJECT_ARRAY);
            TYPES_AND_VALUES.put(float[].class, ArrayUtils.EMPTY_FLOAT_ARRAY);
            TYPES_AND_VALUES.put(Optional.class, Optional.empty());
            TYPES_AND_VALUES.put(Double.TYPE, 0.0);
            TYPES_AND_VALUES.put(Double.class, 0.0);
            TYPES_AND_VALUES.put(Long.TYPE, 0L);
            TYPES_AND_VALUES.put(Long.class, 0L);
            TYPES_AND_VALUES.put(Integer.TYPE, 0);
            TYPES_AND_VALUES.put(Integer.class, 0);
            TYPES_AND_VALUES.put(Float.TYPE, 0);
            TYPES_AND_VALUES.put(Float.class, 0);
            TYPES_AND_VALUES.put(Boolean.TYPE, false);
            TYPES_AND_VALUES.put(Boolean.class, Boolean.FALSE);
            TYPES_AND_VALUES.put(List.class, new ArrayList());
            TYPES_AND_VALUES.put(Set.class, new HashSet());
            TYPES_AND_VALUES.put(Map.class, new HashMap());
            TYPES_AND_VALUES.put(Collection.class, new ArrayList());
        }
    }

    public static class DefaultBeanSupplier<T>
    implements BeanSupplier<T> {
        @Nonnull
        private final Class<T> clazz;
        private final List<Supplier<Boolean>> conditionSuppliers = new ArrayList<Supplier<Boolean>>();
        private Supplier<T> beanSupplier;
        private Supplier<T> proxySupplier;

        @Override
        public T get() {
            if (!this.conditionSuppliers.isEmpty() && this.conditionSuppliers.stream().allMatch(Supplier::get)) {
                return this.beanSupplier.get();
            }
            return this.proxySupplier.get();
        }

        @Override
        public BeanSupplier<T> alwaysMatch() {
            return BeanSupplier.super.alwaysMatch();
        }

        @Override
        @CanIgnoreReturnValue
        public BeanSupplier<T> when(Supplier<Boolean> conditionSupplier) {
            this.conditionSuppliers.add(conditionSupplier);
            return this;
        }

        @Override
        @CanIgnoreReturnValue
        public BeanSupplier<T> supply(Supplier<T> beanSupplier) {
            this.beanSupplier = beanSupplier;
            return this;
        }

        @Override
        @CanIgnoreReturnValue
        public BeanSupplier<T> otherwise(Supplier<T> beanSupplier) {
            this.proxySupplier = beanSupplier;
            return this;
        }

        @Override
        @CanIgnoreReturnValue
        public BeanSupplier<T> otherwiseProxy() {
            return this.otherwise(new ProxiedBeanSupplier<T>(this.clazz));
        }

        @Generated
        public DefaultBeanSupplier(@Nonnull Class<T> clazz) {
            if (clazz == null) {
                throw new NullPointerException("clazz is marked non-null but is null");
            }
            this.clazz = clazz;
        }
    }
}

