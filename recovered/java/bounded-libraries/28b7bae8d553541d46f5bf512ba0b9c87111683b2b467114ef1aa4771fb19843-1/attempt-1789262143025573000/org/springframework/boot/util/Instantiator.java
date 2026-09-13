/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.annotation.AnnotationAwareOrderComparator
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.ReflectionUtils
 */
package org.springframework.boot.util;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

public class Instantiator<T> {
    private static final Comparator<Constructor<?>> CONSTRUCTOR_COMPARATOR = Comparator.comparingInt(Constructor::getParameterCount).reversed();
    private static final FailureHandler throwingFailureHandler = (type, implementationName, failure) -> {
        throw new IllegalArgumentException("Unable to instantiate " + implementationName + " [" + type.getName() + "]", failure);
    };
    private final Class<?> type;
    private final Map<Class<?>, Function<Class<?>, Object>> availableParameters;
    private final FailureHandler failureHandler;

    public Instantiator(Class<?> type, Consumer<AvailableParameters> availableParameters) {
        this(type, availableParameters, throwingFailureHandler);
    }

    public Instantiator(Class<?> type, Consumer<AvailableParameters> availableParameters, FailureHandler failureHandler) {
        this.type = type;
        this.availableParameters = this.getAvailableParameters(availableParameters);
        this.failureHandler = failureHandler;
    }

    private Map<Class<?>, Function<Class<?>, Object>> getAvailableParameters(Consumer<AvailableParameters> availableParameters) {
        final LinkedHashMap result = new LinkedHashMap();
        availableParameters.accept(new AvailableParameters(){

            @Override
            public void add(Class<?> type, Object instance) {
                result.put(type, factoryType -> instance);
            }

            @Override
            public void add(Class<?> type, Function<Class<?>, Object> factory) {
                result.put(type, factory);
            }
        });
        return Collections.unmodifiableMap(result);
    }

    public List<T> instantiate(Collection<String> names) {
        return this.instantiate(null, names);
    }

    public List<T> instantiate(ClassLoader classLoader, Collection<String> names) {
        Assert.notNull(names, (String)"Names must not be null");
        return this.instantiate(names.stream().map(name -> TypeSupplier.forName(classLoader, name)));
    }

    public List<T> instantiateTypes(Collection<Class<?>> types) {
        Assert.notNull(types, (String)"Types must not be null");
        return this.instantiate(types.stream().map(TypeSupplier::forType));
    }

    private List<T> instantiate(Stream<TypeSupplier> typeSuppliers) {
        List instances = typeSuppliers.map(this::instantiate).collect(Collectors.toList());
        AnnotationAwareOrderComparator.sort(instances);
        return Collections.unmodifiableList(instances);
    }

    private T instantiate(TypeSupplier typeSupplier) {
        try {
            Class<?> type = typeSupplier.get();
            Assert.isAssignable(this.type, type);
            return this.instantiate(type);
        }
        catch (Throwable ex) {
            this.failureHandler.handleFailure(this.type, typeSupplier.getName(), ex);
            return null;
        }
    }

    private T instantiate(Class<?> type) throws Exception {
        Constructor<?>[] constructors = type.getDeclaredConstructors();
        Arrays.sort(constructors, CONSTRUCTOR_COMPARATOR);
        for (Constructor<?> constructor : constructors) {
            Object[] args = this.getArgs(constructor.getParameterTypes());
            if (args == null) continue;
            ReflectionUtils.makeAccessible(constructor);
            return (T)constructor.newInstance(args);
        }
        throw new IllegalAccessException("Class [" + type.getName() + "] has no suitable constructor");
    }

    private Object[] getArgs(Class<?>[] parameterTypes) {
        Object[] args = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; ++i) {
            Function<Class<?>, Object> parameter = this.getAvailableParameter(parameterTypes[i]);
            if (parameter == null) {
                return null;
            }
            args[i] = parameter.apply(this.type);
        }
        return args;
    }

    private Function<Class<?>, Object> getAvailableParameter(Class<?> parameterType) {
        for (Map.Entry<Class<?>, Function<Class<?>, Object>> entry : this.availableParameters.entrySet()) {
            if (!entry.getKey().isAssignableFrom(parameterType)) continue;
            return entry.getValue();
        }
        return null;
    }

    public static interface FailureHandler {
        public void handleFailure(Class<?> var1, String var2, Throwable var3);
    }

    private static interface TypeSupplier {
        public String getName();

        public Class<?> get() throws ClassNotFoundException;

        public static TypeSupplier forName(final ClassLoader classLoader, final String name) {
            return new TypeSupplier(){

                @Override
                public String getName() {
                    return name;
                }

                @Override
                public Class<?> get() throws ClassNotFoundException {
                    return ClassUtils.forName((String)name, (ClassLoader)classLoader);
                }
            };
        }

        public static TypeSupplier forType(final Class<?> type) {
            return new TypeSupplier(){

                @Override
                public String getName() {
                    return type.getName();
                }

                @Override
                public Class<?> get() throws ClassNotFoundException {
                    return type;
                }
            };
        }
    }

    public static interface AvailableParameters {
        public void add(Class<?> var1, Object var2);

        public void add(Class<?> var1, Function<Class<?>, Object> var2);
    }
}

