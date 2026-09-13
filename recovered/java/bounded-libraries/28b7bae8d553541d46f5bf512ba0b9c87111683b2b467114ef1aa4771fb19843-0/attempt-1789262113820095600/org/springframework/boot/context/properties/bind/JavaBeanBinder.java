/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeanUtils
 *  org.springframework.core.MethodParameter
 *  org.springframework.core.ResolvableType
 */
package org.springframework.boot.context.properties.bind;

import java.beans.Introspector;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.bind.DataObjectBinder;
import org.springframework.boot.context.properties.bind.DataObjectPropertyBinder;
import org.springframework.boot.context.properties.bind.DataObjectPropertyName;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.ConfigurationPropertyState;
import org.springframework.core.MethodParameter;
import org.springframework.core.ResolvableType;

class JavaBeanBinder
implements DataObjectBinder {
    static final JavaBeanBinder INSTANCE = new JavaBeanBinder();

    JavaBeanBinder() {
    }

    @Override
    public <T> T bind(ConfigurationPropertyName name, Bindable<T> target, Binder.Context context, DataObjectPropertyBinder propertyBinder) {
        boolean hasKnownBindableProperties = target.getValue() != null && this.hasKnownBindableProperties(name, context);
        Bean<T> bean = Bean.get(target, hasKnownBindableProperties);
        if (bean == null) {
            return null;
        }
        BeanSupplier<T> beanSupplier = bean.getSupplier(target);
        boolean bound = this.bind(propertyBinder, bean, beanSupplier, context);
        return bound ? (T)beanSupplier.get() : null;
    }

    @Override
    public <T> T create(Bindable<T> target, Binder.Context context) {
        Class type = target.getType().resolve();
        return (T)(type != null ? BeanUtils.instantiateClass((Class)type) : null);
    }

    private boolean hasKnownBindableProperties(ConfigurationPropertyName name, Binder.Context context) {
        for (ConfigurationPropertySource source : context.getSources()) {
            if (source.containsDescendantOf(name) != ConfigurationPropertyState.PRESENT) continue;
            return true;
        }
        return false;
    }

    private <T> boolean bind(DataObjectPropertyBinder propertyBinder, Bean<T> bean, BeanSupplier<T> beanSupplier, Binder.Context context) {
        boolean bound = false;
        for (BeanProperty beanProperty : bean.getProperties().values()) {
            bound |= this.bind(beanSupplier, propertyBinder, beanProperty);
            context.clearConfigurationProperty();
        }
        return bound;
    }

    private <T> boolean bind(BeanSupplier<T> beanSupplier, DataObjectPropertyBinder propertyBinder, BeanProperty property) {
        String propertyName = property.getName();
        ResolvableType type = property.getType();
        Supplier<Object> value = property.getValue(beanSupplier);
        Annotation[] annotations = property.getAnnotations();
        Object bound = propertyBinder.bindProperty(propertyName, Bindable.of(type).withSuppliedValue(value).withAnnotations(annotations));
        if (bound == null) {
            return false;
        }
        if (property.isSettable()) {
            property.setValue(beanSupplier, bound);
        } else if (value == null || !bound.equals(value.get())) {
            throw new IllegalStateException("No setter found for property: " + property.getName());
        }
        return true;
    }

    static class BeanProperty {
        private final String name;
        private final ResolvableType declaringClassType;
        private Method getter;
        private Method setter;
        private Field field;

        BeanProperty(String name, ResolvableType declaringClassType) {
            this.name = DataObjectPropertyName.toDashedForm(name);
            this.declaringClassType = declaringClassType;
        }

        void addGetter(Method getter) {
            if (this.getter == null || this.isBetterGetter(getter)) {
                this.getter = getter;
            }
        }

        private boolean isBetterGetter(Method getter) {
            return this.getter != null && this.getter.getName().startsWith("is");
        }

        void addSetter(Method setter) {
            if (this.setter == null || this.isBetterSetter(setter)) {
                this.setter = setter;
            }
        }

        private boolean isBetterSetter(Method setter) {
            return this.getter != null && this.getter.getReturnType().equals(setter.getParameterTypes()[0]);
        }

        void addField(Field field) {
            if (this.field == null) {
                this.field = field;
            }
        }

        String getName() {
            return this.name;
        }

        ResolvableType getType() {
            if (this.setter != null) {
                MethodParameter methodParameter = new MethodParameter(this.setter, 0);
                return ResolvableType.forMethodParameter((MethodParameter)methodParameter, (ResolvableType)this.declaringClassType);
            }
            MethodParameter methodParameter = new MethodParameter(this.getter, -1);
            return ResolvableType.forMethodParameter((MethodParameter)methodParameter, (ResolvableType)this.declaringClassType);
        }

        Annotation[] getAnnotations() {
            try {
                return this.field != null ? this.field.getDeclaredAnnotations() : null;
            }
            catch (Exception ex) {
                return null;
            }
        }

        Supplier<Object> getValue(Supplier<?> instance) {
            if (this.getter == null) {
                return null;
            }
            return () -> {
                try {
                    this.getter.setAccessible(true);
                    return this.getter.invoke(instance.get(), new Object[0]);
                }
                catch (Exception ex) {
                    throw new IllegalStateException("Unable to get value for property " + this.name, ex);
                }
            };
        }

        boolean isSettable() {
            return this.setter != null;
        }

        void setValue(Supplier<?> instance, Object value) {
            try {
                this.setter.setAccessible(true);
                this.setter.invoke(instance.get(), value);
            }
            catch (Exception ex) {
                throw new IllegalStateException("Unable to set value for property " + this.name, ex);
            }
        }
    }

    private static class BeanSupplier<T>
    implements Supplier<T> {
        private final Supplier<T> factory;
        private T instance;

        BeanSupplier(Supplier<T> factory) {
            this.factory = factory;
        }

        @Override
        public T get() {
            if (this.instance == null) {
                this.instance = this.factory.get();
            }
            return this.instance;
        }
    }

    static class Bean<T> {
        private static Bean<?> cached;
        private final ResolvableType type;
        private final Class<?> resolvedType;
        private final Map<String, BeanProperty> properties = new LinkedHashMap<String, BeanProperty>();

        Bean(ResolvableType type, Class<?> resolvedType) {
            this.type = type;
            this.resolvedType = resolvedType;
            this.addProperties(resolvedType);
        }

        private void addProperties(Class<?> type) {
            while (type != null && !Object.class.equals(type)) {
                Method[] declaredMethods = this.getSorted(type, Class::getDeclaredMethods, Method::getName);
                Field[] declaredFields = this.getSorted(type, Class::getDeclaredFields, Field::getName);
                this.addProperties(declaredMethods, declaredFields);
                type = type.getSuperclass();
            }
        }

        private <S, E> E[] getSorted(S source, Function<S, E[]> elements, Function<E, String> name) {
            E[] result = elements.apply(source);
            Arrays.sort(result, Comparator.comparing(name));
            return result;
        }

        protected void addProperties(Method[] declaredMethods, Field[] declaredFields) {
            for (int i = 0; i < declaredMethods.length; ++i) {
                if (this.isCandidate(declaredMethods[i])) continue;
                declaredMethods[i] = null;
            }
            for (Method method : declaredMethods) {
                this.addMethodIfPossible(method, "is", 0, BeanProperty::addGetter);
            }
            for (Method method : declaredMethods) {
                this.addMethodIfPossible(method, "get", 0, BeanProperty::addGetter);
            }
            for (Method method : declaredMethods) {
                this.addMethodIfPossible(method, "set", 1, BeanProperty::addSetter);
            }
            for (AccessibleObject accessibleObject : declaredFields) {
                this.addField((Field)accessibleObject);
            }
        }

        private boolean isCandidate(Method method) {
            int modifiers = method.getModifiers();
            return !Modifier.isPrivate(modifiers) && !Modifier.isProtected(modifiers) && !Modifier.isAbstract(modifiers) && !Modifier.isStatic(modifiers) && !method.isBridge() && !Object.class.equals(method.getDeclaringClass()) && !Class.class.equals(method.getDeclaringClass()) && method.getName().indexOf(36) == -1;
        }

        private void addMethodIfPossible(Method method, String prefix, int parameterCount, BiConsumer<BeanProperty, Method> consumer) {
            if (method != null && method.getParameterCount() == parameterCount && method.getName().startsWith(prefix) && method.getName().length() > prefix.length()) {
                String propertyName = Introspector.decapitalize(method.getName().substring(prefix.length()));
                consumer.accept(this.properties.computeIfAbsent(propertyName, this::getBeanProperty), method);
            }
        }

        private BeanProperty getBeanProperty(String name) {
            return new BeanProperty(name, this.type);
        }

        private void addField(Field field) {
            BeanProperty property = this.properties.get(field.getName());
            if (property != null) {
                property.addField(field);
            }
        }

        Map<String, BeanProperty> getProperties() {
            return this.properties;
        }

        BeanSupplier<T> getSupplier(Bindable<T> target) {
            return new BeanSupplier<Object>(() -> {
                Object instance = null;
                if (target.getValue() != null) {
                    instance = target.getValue().get();
                }
                if (instance == null) {
                    instance = BeanUtils.instantiateClass(this.resolvedType);
                }
                return instance;
            });
        }

        static <T> Bean<T> get(Bindable<T> bindable, boolean canCallGetValue) {
            ResolvableType type = bindable.getType();
            Class<?> resolvedType = type.resolve(Object.class);
            Supplier<T> value = bindable.getValue();
            Object instance = null;
            if (canCallGetValue && value != null) {
                instance = value.get();
                Class<?> clazz = resolvedType = instance != null ? instance.getClass() : resolvedType;
            }
            if (instance == null && !Bean.isInstantiable(resolvedType)) {
                return null;
            }
            Bean<Object> bean = cached;
            if (bean == null || !super.isOfType(type, resolvedType)) {
                cached = bean = new Bean<T>(type, resolvedType);
            }
            return bean;
        }

        private static boolean isInstantiable(Class<?> type) {
            if (type.isInterface()) {
                return false;
            }
            try {
                type.getDeclaredConstructor(new Class[0]);
                return true;
            }
            catch (Exception ex) {
                return false;
            }
        }

        private boolean isOfType(ResolvableType type, Class<?> resolvedType) {
            if (this.type.hasGenerics() || type.hasGenerics()) {
                return this.type.equals((Object)type);
            }
            return this.resolvedType != null && this.resolvedType.equals(resolvedType);
        }
    }
}

