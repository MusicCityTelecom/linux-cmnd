/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.ext.ExceptionMapper;
import org.glassfish.jersey.internal.BootstrapBag;
import org.glassfish.jersey.internal.BootstrapConfigurator;
import org.glassfish.jersey.internal.LocalizationMessages;
import org.glassfish.jersey.internal.inject.Bindings;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.internal.inject.InstanceBinding;
import org.glassfish.jersey.internal.inject.Providers;
import org.glassfish.jersey.internal.inject.ServiceHolder;
import org.glassfish.jersey.internal.util.ReflectionHelper;
import org.glassfish.jersey.internal.util.collection.ClassTypePair;
import org.glassfish.jersey.internal.util.collection.LazyValue;
import org.glassfish.jersey.internal.util.collection.Value;
import org.glassfish.jersey.internal.util.collection.Values;
import org.glassfish.jersey.spi.ExceptionMappers;
import org.glassfish.jersey.spi.ExtendedExceptionMapper;

public class ExceptionMapperFactory
implements ExceptionMappers {
    private static final Logger LOGGER = Logger.getLogger(ExceptionMapperFactory.class.getName());
    private final Value<Set<ExceptionMapperType>> exceptionMapperTypes;

    @Override
    public <T extends Throwable> ExceptionMapper<T> findMapping(T exceptionInstance) {
        return this.find(exceptionInstance.getClass(), exceptionInstance);
    }

    @Override
    public <T extends Throwable> ExceptionMapper<T> find(Class<T> type) {
        return this.find(type, null);
    }

    private <T extends Throwable> ExceptionMapper<T> find(Class<T> type, T exceptionInstance) {
        ExceptionMapper mapper = null;
        int minDistance = Integer.MAX_VALUE;
        for (ExceptionMapperType mapperType : this.exceptionMapperTypes.get()) {
            ExceptionMapper candidate;
            int d = this.distance(type, mapperType.exceptionType);
            if (d < 0 || d > minDistance || !this.isPreferredCandidate(exceptionInstance, candidate = mapperType.mapper.getInstance(), d == minDistance)) continue;
            mapper = candidate;
            minDistance = d;
            if (d != 0) continue;
            return mapper;
        }
        return mapper;
    }

    private <T extends Throwable> boolean isPreferredCandidate(T exceptionInstance, ExceptionMapper<T> candidate, boolean sameDistance) {
        if (exceptionInstance == null) {
            return true;
        }
        if (candidate instanceof ExtendedExceptionMapper) {
            return !sameDistance && ((ExtendedExceptionMapper)candidate).isMappable(exceptionInstance);
        }
        return !sameDistance;
    }

    public ExceptionMapperFactory(InjectionManager injectionManager) {
        this.exceptionMapperTypes = this.createLazyExceptionMappers(injectionManager);
    }

    private LazyValue<Set<ExceptionMapperType>> createLazyExceptionMappers(InjectionManager injectionManager) {
        return Values.lazy(() -> {
            Collection<ServiceHolder<ExceptionMapper>> mapperHandles = Providers.getAllServiceHolders(injectionManager, ExceptionMapper.class);
            LinkedHashSet<ExceptionMapperType> exceptionMapperTypes = new LinkedHashSet<ExceptionMapperType>();
            for (ServiceHolder<ExceptionMapper> mapperHandle : mapperHandles) {
                ExceptionMapper mapper = mapperHandle.getInstance();
                if (Proxy.isProxyClass(mapper.getClass())) {
                    Class<? extends Throwable> c;
                    TreeSet<Class> mapperTypes = new TreeSet<Class>((o1, o2) -> o1.isAssignableFrom((Class<?>)o2) ? -1 : 1);
                    Set<Type> contracts = mapperHandle.getContractTypes();
                    for (Type contract : contracts) {
                        if (!(contract instanceof Class) || !ExceptionMapper.class.isAssignableFrom((Class)contract) || contract == ExceptionMapper.class) continue;
                        mapperTypes.add((Class)contract);
                    }
                    if (mapperTypes.isEmpty() || (c = this.getExceptionType((Class)mapperTypes.first())) == null) continue;
                    exceptionMapperTypes.add(new ExceptionMapperType(mapperHandle, c));
                    continue;
                }
                Class<? extends Throwable> c = this.getExceptionType(mapper.getClass());
                if (c == null) continue;
                exceptionMapperTypes.add(new ExceptionMapperType(mapperHandle, c));
            }
            return exceptionMapperTypes;
        });
    }

    private int distance(Class<?> c, Class<?> emtc) {
        int distance = 0;
        if (!emtc.isAssignableFrom(c)) {
            return -1;
        }
        while (c != emtc) {
            c = c.getSuperclass();
            ++distance;
        }
        return distance;
    }

    private Class<? extends Throwable> getExceptionType(Class<? extends ExceptionMapper> c) {
        Class t = this.getType(c);
        if (Throwable.class.isAssignableFrom(t)) {
            return t;
        }
        if (LOGGER.isLoggable(Level.WARNING)) {
            LOGGER.warning(LocalizationMessages.EXCEPTION_MAPPER_SUPPORTED_TYPE_UNKNOWN(c.getName()));
        }
        return null;
    }

    private Class getType(Class<? extends ExceptionMapper> clazz) {
        for (Class<? extends ExceptionMapper> clazzHolder = clazz; clazzHolder != Object.class; clazzHolder = clazzHolder.getSuperclass()) {
            Class type = this.getTypeFromInterface(clazzHolder, clazz);
            if (type == null) continue;
            return type;
        }
        throw new ProcessingException(LocalizationMessages.ERROR_FINDING_EXCEPTION_MAPPER_TYPE(clazz));
    }

    private Class getTypeFromInterface(Class<?> clazz, Class<? extends ExceptionMapper> original) {
        Type[] types;
        for (Type type : types = clazz.getGenericInterfaces()) {
            if (type instanceof ParameterizedType) {
                ParameterizedType pt = (ParameterizedType)type;
                if (pt.getRawType() != ExceptionMapper.class && pt.getRawType() != ExtendedExceptionMapper.class) continue;
                return this.getResolvedType(pt.getActualTypeArguments()[0], original, clazz);
            }
            if (!(type instanceof Class) || !ExceptionMapper.class.isAssignableFrom(clazz = (Class)type)) continue;
            return this.getTypeFromInterface(clazz, original);
        }
        return null;
    }

    private Class getResolvedType(Type t, Class c, Class dc) {
        if (t instanceof Class) {
            return (Class)t;
        }
        if (t instanceof TypeVariable) {
            ClassTypePair ct = ReflectionHelper.resolveTypeVariable(c, dc, (TypeVariable)t);
            if (ct != null) {
                return ct.rawClass();
            }
            return null;
        }
        if (t instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType)t;
            return (Class)pt.getRawType();
        }
        return null;
    }

    private static class ExceptionMapperType {
        ServiceHolder<ExceptionMapper> mapper;
        Class<? extends Throwable> exceptionType;

        public ExceptionMapperType(ServiceHolder<ExceptionMapper> mapper, Class<? extends Throwable> exceptionType) {
            this.mapper = mapper;
            this.exceptionType = exceptionType;
        }
    }

    public static class ExceptionMappersConfigurator
    implements BootstrapConfigurator {
        private ExceptionMapperFactory exceptionMapperFactory;

        @Override
        public void init(InjectionManager injectionManager, BootstrapBag bootstrapBag) {
            this.exceptionMapperFactory = new ExceptionMapperFactory(injectionManager);
            InstanceBinding binding = (InstanceBinding)Bindings.service(this.exceptionMapperFactory).to(ExceptionMappers.class);
            injectionManager.register(binding);
        }

        @Override
        public void postInit(InjectionManager injectionManager, BootstrapBag bootstrapBag) {
            bootstrapBag.setExceptionMappers(this.exceptionMapperFactory);
        }
    }
}

