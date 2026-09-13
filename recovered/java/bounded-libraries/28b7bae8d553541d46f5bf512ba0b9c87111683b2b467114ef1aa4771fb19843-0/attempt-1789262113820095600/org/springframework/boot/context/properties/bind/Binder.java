/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.PropertyEditorRegistry
 *  org.springframework.core.convert.ConversionService
 *  org.springframework.core.convert.ConverterNotFoundException
 *  org.springframework.core.env.Environment
 *  org.springframework.util.Assert
 */
package org.springframework.boot.context.properties.bind;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.boot.context.properties.bind.AggregateBinder;
import org.springframework.boot.context.properties.bind.AggregateElementBinder;
import org.springframework.boot.context.properties.bind.ArrayBinder;
import org.springframework.boot.context.properties.bind.BindConstructorProvider;
import org.springframework.boot.context.properties.bind.BindContext;
import org.springframework.boot.context.properties.bind.BindConverter;
import org.springframework.boot.context.properties.bind.BindException;
import org.springframework.boot.context.properties.bind.BindHandler;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.CollectionBinder;
import org.springframework.boot.context.properties.bind.DataObjectBinder;
import org.springframework.boot.context.properties.bind.DataObjectPropertyBinder;
import org.springframework.boot.context.properties.bind.JavaBeanBinder;
import org.springframework.boot.context.properties.bind.MapBinder;
import org.springframework.boot.context.properties.bind.PlaceholdersResolver;
import org.springframework.boot.context.properties.bind.PropertySourcesPlaceholdersResolver;
import org.springframework.boot.context.properties.bind.ValueObjectBinder;
import org.springframework.boot.context.properties.source.ConfigurationProperty;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.boot.context.properties.source.ConfigurationPropertyState;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.ConverterNotFoundException;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;

public class Binder {
    private static final Set<Class<?>> NON_BEAN_CLASSES = Collections.unmodifiableSet(new HashSet<Class>(Arrays.asList(Object.class, Class.class)));
    private final Iterable<ConfigurationPropertySource> sources;
    private final PlaceholdersResolver placeholdersResolver;
    private final BindConverter bindConverter;
    private final BindHandler defaultBindHandler;
    private final List<DataObjectBinder> dataObjectBinders;

    public Binder(ConfigurationPropertySource ... sources) {
        this(sources != null ? Arrays.asList(sources) : null, null, null, null);
    }

    public Binder(Iterable<ConfigurationPropertySource> sources) {
        this(sources, null, null, null);
    }

    public Binder(Iterable<ConfigurationPropertySource> sources, PlaceholdersResolver placeholdersResolver) {
        this(sources, placeholdersResolver, null, null);
    }

    public Binder(Iterable<ConfigurationPropertySource> sources, PlaceholdersResolver placeholdersResolver, ConversionService conversionService) {
        this(sources, placeholdersResolver, conversionService, null);
    }

    public Binder(Iterable<ConfigurationPropertySource> sources, PlaceholdersResolver placeholdersResolver, ConversionService conversionService, Consumer<PropertyEditorRegistry> propertyEditorInitializer) {
        this(sources, placeholdersResolver, conversionService, propertyEditorInitializer, null);
    }

    public Binder(Iterable<ConfigurationPropertySource> sources, PlaceholdersResolver placeholdersResolver, ConversionService conversionService, Consumer<PropertyEditorRegistry> propertyEditorInitializer, BindHandler defaultBindHandler) {
        this(sources, placeholdersResolver, conversionService, propertyEditorInitializer, defaultBindHandler, null);
    }

    public Binder(Iterable<ConfigurationPropertySource> sources, PlaceholdersResolver placeholdersResolver, ConversionService conversionService, Consumer<PropertyEditorRegistry> propertyEditorInitializer, BindHandler defaultBindHandler, BindConstructorProvider constructorProvider) {
        this(sources, placeholdersResolver, conversionService != null ? Collections.singletonList(conversionService) : (List)null, propertyEditorInitializer, defaultBindHandler, constructorProvider);
    }

    public Binder(Iterable<ConfigurationPropertySource> sources, PlaceholdersResolver placeholdersResolver, List<ConversionService> conversionServices, Consumer<PropertyEditorRegistry> propertyEditorInitializer, BindHandler defaultBindHandler, BindConstructorProvider constructorProvider) {
        Assert.notNull(sources, (String)"Sources must not be null");
        for (ConfigurationPropertySource source : sources) {
            Assert.notNull((Object)source, (String)"Sources must not contain null elements");
        }
        this.sources = sources;
        this.placeholdersResolver = placeholdersResolver != null ? placeholdersResolver : PlaceholdersResolver.NONE;
        this.bindConverter = BindConverter.get(conversionServices, propertyEditorInitializer);
        BindHandler bindHandler = this.defaultBindHandler = defaultBindHandler != null ? defaultBindHandler : BindHandler.DEFAULT;
        if (constructorProvider == null) {
            constructorProvider = BindConstructorProvider.DEFAULT;
        }
        ValueObjectBinder valueObjectBinder = new ValueObjectBinder(constructorProvider);
        JavaBeanBinder javaBeanBinder = JavaBeanBinder.INSTANCE;
        this.dataObjectBinders = Collections.unmodifiableList(Arrays.asList(valueObjectBinder, javaBeanBinder));
    }

    public <T> BindResult<T> bind(String name, Class<T> target) {
        return this.bind(name, Bindable.of(target));
    }

    public <T> BindResult<T> bind(String name, Bindable<T> target) {
        return this.bind(ConfigurationPropertyName.of(name), target, null);
    }

    public <T> BindResult<T> bind(ConfigurationPropertyName name, Bindable<T> target) {
        return this.bind(name, target, null);
    }

    public <T> BindResult<T> bind(String name, Bindable<T> target, BindHandler handler) {
        return this.bind(ConfigurationPropertyName.of(name), target, handler);
    }

    public <T> BindResult<T> bind(ConfigurationPropertyName name, Bindable<T> target, BindHandler handler) {
        T bound = this.bind(name, target, handler, false);
        return BindResult.of(bound);
    }

    public <T> T bindOrCreate(String name, Class<T> target) {
        return this.bindOrCreate(name, Bindable.of(target));
    }

    public <T> T bindOrCreate(String name, Bindable<T> target) {
        return this.bindOrCreate(ConfigurationPropertyName.of(name), target, null);
    }

    public <T> T bindOrCreate(String name, Bindable<T> target, BindHandler handler) {
        return this.bindOrCreate(ConfigurationPropertyName.of(name), target, handler);
    }

    public <T> T bindOrCreate(ConfigurationPropertyName name, Bindable<T> target, BindHandler handler) {
        return this.bind(name, target, handler, true);
    }

    private <T> T bind(ConfigurationPropertyName name, Bindable<T> target, BindHandler handler, boolean create) {
        Assert.notNull((Object)name, (String)"Name must not be null");
        Assert.notNull(target, (String)"Target must not be null");
        handler = handler != null ? handler : this.defaultBindHandler;
        Context context = new Context();
        return this.bind(name, target, handler, context, false, create);
    }

    private <T> T bind(ConfigurationPropertyName name, Bindable<T> target, BindHandler handler, Context context, boolean allowRecursiveBinding, boolean create) {
        try {
            Bindable<T> replacementTarget = handler.onStart(name, target, context);
            if (replacementTarget == null) {
                return this.handleBindResult(name, target, handler, context, null, create);
            }
            target = replacementTarget;
            Object bound = this.bindObject(name, target, handler, context, allowRecursiveBinding);
            return this.handleBindResult(name, target, handler, context, bound, create);
        }
        catch (Exception ex) {
            return this.handleBindError(name, target, handler, context, ex);
        }
    }

    private <T> T handleBindResult(ConfigurationPropertyName name, Bindable<T> target, BindHandler handler, Context context, Object result, boolean create) throws Exception {
        if (result != null) {
            result = handler.onSuccess(name, target, context, result);
            result = context.getConverter().convert(result, target);
        }
        if (result == null && create) {
            result = this.create(target, context);
            result = handler.onCreate(name, target, context, result);
            result = context.getConverter().convert(result, target);
            Assert.state((result != null ? 1 : 0) != 0, () -> "Unable to create instance for " + target.getType());
        }
        handler.onFinish(name, target, context, result);
        return context.getConverter().convert(result, target);
    }

    private Object create(Bindable<?> target, Context context) {
        for (DataObjectBinder dataObjectBinder : this.dataObjectBinders) {
            Object instance = dataObjectBinder.create(target, context);
            if (instance == null) continue;
            return instance;
        }
        return null;
    }

    private <T> T handleBindError(ConfigurationPropertyName name, Bindable<T> target, BindHandler handler, Context context, Exception error) {
        try {
            Object result = handler.onFailure(name, target, context, error);
            return context.getConverter().convert(result, target);
        }
        catch (Exception ex) {
            if (ex instanceof BindException) {
                throw (BindException)ex;
            }
            throw new BindException(name, target, context.getConfigurationProperty(), ex);
        }
    }

    private <T> Object bindObject(ConfigurationPropertyName name, Bindable<T> target, BindHandler handler, Context context, boolean allowRecursiveBinding) {
        ConfigurationProperty property = this.findProperty(name, target, context);
        if (property == null && context.depth != 0 && this.containsNoDescendantOf(context.getSources(), name)) {
            return null;
        }
        AggregateBinder<?> aggregateBinder = this.getAggregateBinder(target, context);
        if (aggregateBinder != null) {
            return this.bindAggregate(name, target, handler, context, aggregateBinder);
        }
        if (property != null) {
            try {
                return this.bindProperty(target, context, property);
            }
            catch (ConverterNotFoundException ex) {
                Object instance = this.bindDataObject(name, target, handler, context, allowRecursiveBinding);
                if (instance != null) {
                    return instance;
                }
                throw ex;
            }
        }
        return this.bindDataObject(name, target, handler, context, allowRecursiveBinding);
    }

    private AggregateBinder<?> getAggregateBinder(Bindable<?> target, Context context) {
        Class resolvedType = target.getType().resolve(Object.class);
        if (Map.class.isAssignableFrom(resolvedType)) {
            return new MapBinder(context);
        }
        if (Collection.class.isAssignableFrom(resolvedType)) {
            return new CollectionBinder(context);
        }
        if (target.getType().isArray()) {
            return new ArrayBinder(context);
        }
        return null;
    }

    private <T> Object bindAggregate(ConfigurationPropertyName name, Bindable<T> target, BindHandler handler, Context context, AggregateBinder<?> aggregateBinder) {
        AggregateElementBinder elementBinder = (itemName, itemTarget, source) -> {
            boolean allowRecursiveBinding = aggregateBinder.isAllowRecursiveBinding(source);
            Supplier<Object> supplier = () -> this.bind(itemName, itemTarget, handler, context, allowRecursiveBinding, false);
            return context.withSource(source, supplier);
        };
        return context.withIncreasedDepth(() -> aggregateBinder.bind(name, target, elementBinder));
    }

    private <T> ConfigurationProperty findProperty(ConfigurationPropertyName name, Bindable<T> target, Context context) {
        if (name.isEmpty() || target.hasBindRestriction(Bindable.BindRestriction.NO_DIRECT_PROPERTY)) {
            return null;
        }
        for (ConfigurationPropertySource source : context.getSources()) {
            ConfigurationProperty property = source.getConfigurationProperty(name);
            if (property == null) continue;
            return property;
        }
        return null;
    }

    private <T> Object bindProperty(Bindable<T> target, Context context, ConfigurationProperty property) {
        context.setConfigurationProperty(property);
        Object result = property.getValue();
        result = this.placeholdersResolver.resolvePlaceholders(result);
        result = context.getConverter().convert(result, target);
        return result;
    }

    private Object bindDataObject(ConfigurationPropertyName name, Bindable<?> target, BindHandler handler, Context context, boolean allowRecursiveBinding) {
        if (this.isUnbindableBean(name, target, context)) {
            return null;
        }
        Class type = target.getType().resolve(Object.class);
        if (!allowRecursiveBinding && context.isBindingDataObject(type)) {
            return null;
        }
        DataObjectPropertyBinder propertyBinder = (propertyName, propertyTarget) -> this.bind(name.append(propertyName), propertyTarget, handler, context, false, false);
        return context.withDataObject(type, () -> {
            for (DataObjectBinder dataObjectBinder : this.dataObjectBinders) {
                Object instance = dataObjectBinder.bind(name, target, context, propertyBinder);
                if (instance == null) continue;
                return instance;
            }
            return null;
        });
    }

    private boolean isUnbindableBean(ConfigurationPropertyName name, Bindable<?> target, Context context) {
        for (ConfigurationPropertySource source : context.getSources()) {
            if (source.containsDescendantOf(name) != ConfigurationPropertyState.PRESENT) continue;
            return false;
        }
        Class resolved = target.getType().resolve(Object.class);
        if (resolved.isPrimitive() || NON_BEAN_CLASSES.contains(resolved)) {
            return true;
        }
        return resolved.getName().startsWith("java.");
    }

    private boolean containsNoDescendantOf(Iterable<ConfigurationPropertySource> sources, ConfigurationPropertyName name) {
        for (ConfigurationPropertySource source : sources) {
            if (source.containsDescendantOf(name) == ConfigurationPropertyState.ABSENT) continue;
            return false;
        }
        return true;
    }

    public static Binder get(Environment environment) {
        return Binder.get(environment, null);
    }

    public static Binder get(Environment environment, BindHandler defaultBindHandler) {
        Iterable<ConfigurationPropertySource> sources = ConfigurationPropertySources.get(environment);
        PropertySourcesPlaceholdersResolver placeholdersResolver = new PropertySourcesPlaceholdersResolver(environment);
        return new Binder(sources, placeholdersResolver, null, null, defaultBindHandler);
    }

    final class Context
    implements BindContext {
        private int depth;
        private final List<ConfigurationPropertySource> source = Arrays.asList(new ConfigurationPropertySource[]{null});
        private int sourcePushCount;
        private final Deque<Class<?>> dataObjectBindings = new ArrayDeque();
        private final Deque<Class<?>> constructorBindings = new ArrayDeque();
        private ConfigurationProperty configurationProperty;

        Context() {
        }

        private void increaseDepth() {
            ++this.depth;
        }

        private void decreaseDepth() {
            --this.depth;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private <T> T withSource(ConfigurationPropertySource source, Supplier<T> supplier) {
            if (source == null) {
                return supplier.get();
            }
            this.source.set(0, source);
            ++this.sourcePushCount;
            try {
                T t = supplier.get();
                return t;
            }
            finally {
                --this.sourcePushCount;
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private <T> T withDataObject(Class<?> type, Supplier<T> supplier) {
            this.dataObjectBindings.push(type);
            try {
                T t = this.withIncreasedDepth(supplier);
                return t;
            }
            finally {
                this.dataObjectBindings.pop();
            }
        }

        private boolean isBindingDataObject(Class<?> type) {
            return this.dataObjectBindings.contains(type);
        }

        private <T> T withIncreasedDepth(Supplier<T> supplier) {
            this.increaseDepth();
            try {
                T t = supplier.get();
                return t;
            }
            finally {
                this.decreaseDepth();
            }
        }

        void setConfigurationProperty(ConfigurationProperty configurationProperty) {
            this.configurationProperty = configurationProperty;
        }

        void clearConfigurationProperty() {
            this.configurationProperty = null;
        }

        void pushConstructorBoundTypes(Class<?> value) {
            this.constructorBindings.push(value);
        }

        boolean isNestedConstructorBinding() {
            return !this.constructorBindings.isEmpty();
        }

        void popConstructorBoundTypes() {
            this.constructorBindings.pop();
        }

        PlaceholdersResolver getPlaceholdersResolver() {
            return Binder.this.placeholdersResolver;
        }

        BindConverter getConverter() {
            return Binder.this.bindConverter;
        }

        @Override
        public Binder getBinder() {
            return Binder.this;
        }

        @Override
        public int getDepth() {
            return this.depth;
        }

        @Override
        public Iterable<ConfigurationPropertySource> getSources() {
            if (this.sourcePushCount > 0) {
                return this.source;
            }
            return Binder.this.sources;
        }

        @Override
        public ConfigurationProperty getConfigurationProperty() {
            return this.configurationProperty;
        }
    }
}

