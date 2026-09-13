/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.model.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.Priority;
import javax.ws.rs.ConstrainedTo;
import javax.ws.rs.RuntimeType;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import org.glassfish.jersey.ExtendedConfig;
import org.glassfish.jersey.internal.LocalizationMessages;
import org.glassfish.jersey.internal.ServiceFinder;
import org.glassfish.jersey.internal.inject.Binder;
import org.glassfish.jersey.internal.inject.CompositeBinder;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.internal.inject.ProviderBinder;
import org.glassfish.jersey.internal.spi.AutoDiscoverable;
import org.glassfish.jersey.internal.spi.ForcedAutoDiscoverable;
import org.glassfish.jersey.internal.util.PropertiesHelper;
import org.glassfish.jersey.model.ContractProvider;
import org.glassfish.jersey.model.internal.ComponentBag;
import org.glassfish.jersey.model.internal.FeatureContextWrapper;
import org.glassfish.jersey.model.internal.ManagedObjectsFinalizer;
import org.glassfish.jersey.process.Inflector;

public class CommonConfig
implements FeatureContext,
ExtendedConfig {
    private static final Logger LOGGER = Logger.getLogger(CommonConfig.class.getName());
    private static final Function<Object, Binder> CAST_TO_BINDER = Binder.class::cast;
    private final RuntimeType type;
    private final Map<String, Object> properties;
    private final Map<String, Object> immutablePropertiesView;
    private final Collection<String> immutablePropertyNames;
    private final ComponentBag componentBag;
    private final List<FeatureRegistration> newFeatureRegistrations;
    private final Set<Class<? extends Feature>> enabledFeatureClasses;
    private final Set<Feature> enabledFeatures;
    private boolean disableMetaProviderConfiguration;

    public CommonConfig(RuntimeType type, Predicate<ContractProvider> registrationStrategy) {
        this.type = type;
        this.properties = new HashMap<String, Object>();
        this.immutablePropertiesView = Collections.unmodifiableMap(this.properties);
        this.immutablePropertyNames = Collections.unmodifiableCollection(this.properties.keySet());
        this.componentBag = ComponentBag.newInstance(registrationStrategy);
        this.newFeatureRegistrations = new LinkedList<FeatureRegistration>();
        this.enabledFeatureClasses = Collections.newSetFromMap(new IdentityHashMap());
        this.enabledFeatures = new HashSet<Feature>();
        this.disableMetaProviderConfiguration = false;
    }

    public CommonConfig(CommonConfig config) {
        this.type = config.type;
        this.properties = new HashMap<String, Object>(config.properties.size());
        this.immutablePropertiesView = Collections.unmodifiableMap(this.properties);
        this.immutablePropertyNames = Collections.unmodifiableCollection(this.properties.keySet());
        this.componentBag = config.componentBag.copy();
        this.newFeatureRegistrations = new LinkedList<FeatureRegistration>();
        this.enabledFeatureClasses = Collections.newSetFromMap(new IdentityHashMap());
        this.enabledFeatures = new HashSet<Feature>();
        this.copy(config, false);
    }

    private void copy(CommonConfig config, boolean loadComponentBag) {
        this.properties.clear();
        this.properties.putAll(config.properties);
        this.newFeatureRegistrations.clear();
        this.newFeatureRegistrations.addAll(config.newFeatureRegistrations);
        this.enabledFeatureClasses.clear();
        this.enabledFeatureClasses.addAll(config.enabledFeatureClasses);
        this.enabledFeatures.clear();
        this.enabledFeatures.addAll(config.enabledFeatures);
        this.disableMetaProviderConfiguration = config.disableMetaProviderConfiguration;
        if (loadComponentBag) {
            this.componentBag.loadFrom(config.componentBag);
        }
    }

    @Override
    public ExtendedConfig getConfiguration() {
        return this;
    }

    @Override
    public RuntimeType getRuntimeType() {
        return this.type;
    }

    @Override
    public Map<String, Object> getProperties() {
        return this.immutablePropertiesView;
    }

    @Override
    public Object getProperty(String name) {
        return this.properties.get(name);
    }

    @Override
    public boolean isProperty(String name) {
        return PropertiesHelper.isProperty(this.getProperty(name));
    }

    @Override
    public Collection<String> getPropertyNames() {
        return this.immutablePropertyNames;
    }

    @Override
    public boolean isEnabled(Class<? extends Feature> featureClass) {
        return this.enabledFeatureClasses.contains(featureClass);
    }

    @Override
    public boolean isEnabled(Feature feature) {
        return this.enabledFeatures.contains(feature);
    }

    @Override
    public boolean isRegistered(Object component) {
        return this.componentBag.getInstances().contains(component);
    }

    @Override
    public boolean isRegistered(Class<?> componentClass) {
        return this.componentBag.getRegistrations().contains(componentClass);
    }

    @Override
    public Map<Class<?>, Integer> getContracts(Class<?> componentClass) {
        ContractProvider model = this.componentBag.getModel(componentClass);
        return model == null ? Collections.emptyMap() : model.getContractMap();
    }

    @Override
    public Set<Class<?>> getClasses() {
        return this.componentBag.getClasses();
    }

    @Override
    public Set<Object> getInstances() {
        return this.componentBag.getInstances();
    }

    public final ComponentBag getComponentBag() {
        return this.componentBag;
    }

    protected Inflector<ContractProvider.Builder, ContractProvider> getModelEnhancer(Class<?> componentClass) {
        return ComponentBag.AS_IS;
    }

    public CommonConfig setProperties(Map<String, ?> properties) {
        this.properties.clear();
        if (properties != null) {
            this.properties.putAll(properties);
        }
        return this;
    }

    public CommonConfig addProperties(Map<String, ?> properties) {
        if (properties != null) {
            this.properties.putAll(properties);
        }
        return this;
    }

    @Override
    public CommonConfig property(String name, Object value) {
        if (value == null) {
            this.properties.remove(name);
        } else {
            this.properties.put(name, value);
        }
        return this;
    }

    @Override
    public CommonConfig register(Class<?> componentClass) {
        this.checkComponentClassNotNull(componentClass);
        if (this.componentBag.register(componentClass, this.getModelEnhancer(componentClass))) {
            this.processFeatureRegistration(null, componentClass);
        }
        return this;
    }

    @Override
    public CommonConfig register(Class<?> componentClass, int bindingPriority) {
        this.checkComponentClassNotNull(componentClass);
        if (this.componentBag.register(componentClass, bindingPriority, this.getModelEnhancer(componentClass))) {
            this.processFeatureRegistration(null, componentClass);
        }
        return this;
    }

    @Override
    public CommonConfig register(Class<?> componentClass, Class<?> ... contracts) {
        this.checkComponentClassNotNull(componentClass);
        if (contracts == null || contracts.length == 0) {
            LOGGER.warning(LocalizationMessages.COMPONENT_CONTRACTS_EMPTY_OR_NULL(componentClass));
            return this;
        }
        if (this.componentBag.register(componentClass, this.asNewIdentitySet(contracts), this.getModelEnhancer(componentClass))) {
            this.processFeatureRegistration(null, componentClass);
        }
        return this;
    }

    @Override
    public CommonConfig register(Class<?> componentClass, Map<Class<?>, Integer> contracts) {
        this.checkComponentClassNotNull(componentClass);
        if (this.componentBag.register(componentClass, contracts, this.getModelEnhancer(componentClass))) {
            this.processFeatureRegistration(null, componentClass);
        }
        return this;
    }

    @Override
    public CommonConfig register(Object component) {
        this.checkProviderNotNull(component);
        Class<?> componentClass = component.getClass();
        if (this.componentBag.register(component, this.getModelEnhancer(componentClass))) {
            this.processFeatureRegistration(component, componentClass);
        }
        return this;
    }

    @Override
    public CommonConfig register(Object component, int bindingPriority) {
        this.checkProviderNotNull(component);
        Class<?> componentClass = component.getClass();
        if (this.componentBag.register(component, bindingPriority, this.getModelEnhancer(componentClass))) {
            this.processFeatureRegistration(component, componentClass);
        }
        return this;
    }

    @Override
    public CommonConfig register(Object component, Class<?> ... contracts) {
        this.checkProviderNotNull(component);
        Class<?> componentClass = component.getClass();
        if (contracts == null || contracts.length == 0) {
            LOGGER.warning(LocalizationMessages.COMPONENT_CONTRACTS_EMPTY_OR_NULL(componentClass));
            return this;
        }
        if (this.componentBag.register(component, this.asNewIdentitySet(contracts), this.getModelEnhancer(componentClass))) {
            this.processFeatureRegistration(component, componentClass);
        }
        return this;
    }

    @Override
    public CommonConfig register(Object component, Map<Class<?>, Integer> contracts) {
        this.checkProviderNotNull(component);
        Class<?> componentClass = component.getClass();
        if (this.componentBag.register(component, contracts, this.getModelEnhancer(componentClass))) {
            this.processFeatureRegistration(component, componentClass);
        }
        return this;
    }

    private void processFeatureRegistration(Object component, Class<?> componentClass) {
        ContractProvider model = this.componentBag.getModel(componentClass);
        if (model.getContracts().contains(Feature.class)) {
            FeatureRegistration registration = component != null ? new FeatureRegistration((Feature)component) : new FeatureRegistration(componentClass);
            this.newFeatureRegistrations.add(registration);
        }
    }

    public CommonConfig loadFrom(Configuration config) {
        if (config instanceof CommonConfig) {
            CommonConfig commonConfig = (CommonConfig)config;
            this.copy(commonConfig, true);
            this.disableMetaProviderConfiguration = !commonConfig.enabledFeatureClasses.isEmpty();
        } else {
            this.setProperties(config.getProperties());
            this.enabledFeatures.clear();
            this.enabledFeatureClasses.clear();
            this.componentBag.clear();
            this.resetRegistrations();
            for (Class<?> clazz : config.getClasses()) {
                if (Feature.class.isAssignableFrom(clazz) && config.isEnabled(clazz)) {
                    this.disableMetaProviderConfiguration = true;
                }
                this.register((Class)clazz, (Map)config.getContracts(clazz));
            }
            for (Object instance : config.getInstances()) {
                if (instance instanceof Feature && config.isEnabled((Feature)instance)) {
                    this.disableMetaProviderConfiguration = true;
                }
                this.register(instance, (Map)config.getContracts(instance.getClass()));
            }
        }
        return this;
    }

    private Set<Class<?>> asNewIdentitySet(Class<?> ... contracts) {
        Set<Class<?>> result = Collections.newSetFromMap(new IdentityHashMap());
        result.addAll(Arrays.asList(contracts));
        return result;
    }

    private void checkProviderNotNull(Object provider) {
        if (provider == null) {
            throw new IllegalArgumentException(LocalizationMessages.COMPONENT_CANNOT_BE_NULL());
        }
    }

    private void checkComponentClassNotNull(Class<?> componentClass) {
        if (componentClass == null) {
            throw new IllegalArgumentException(LocalizationMessages.COMPONENT_CLASS_CANNOT_BE_NULL());
        }
    }

    public void configureAutoDiscoverableProviders(InjectionManager injectionManager, Collection<AutoDiscoverable> autoDiscoverables, boolean forcedOnly) {
        if (!this.disableMetaProviderConfiguration) {
            TreeSet providers = new TreeSet((o1, o2) -> {
                int p1 = o1.getClass().isAnnotationPresent(Priority.class) ? o1.getClass().getAnnotation(Priority.class).value() : 5000;
                int p2 = o2.getClass().isAnnotationPresent(Priority.class) ? o2.getClass().getAnnotation(Priority.class).value() : 5000;
                return p1 < p2 || p1 == p2 ? -1 : 1;
            });
            LinkedList<ForcedAutoDiscoverable> forcedAutoDiscroverables = new LinkedList<ForcedAutoDiscoverable>();
            for (Class<ForcedAutoDiscoverable> forcedADType : ServiceFinder.find(ForcedAutoDiscoverable.class, true).toClassArray()) {
                forcedAutoDiscroverables.add(injectionManager.createAndInitialize(forcedADType));
            }
            providers.addAll(forcedAutoDiscroverables);
            if (!forcedOnly) {
                providers.addAll(autoDiscoverables);
            }
            for (AutoDiscoverable autoDiscoverable : providers) {
                ConstrainedTo constrainedTo = autoDiscoverable.getClass().getAnnotation(ConstrainedTo.class);
                if (constrainedTo != null && !this.type.equals((Object)constrainedTo.value())) continue;
                try {
                    autoDiscoverable.configure(this);
                }
                catch (Exception e) {
                    LOGGER.log(Level.FINE, LocalizationMessages.AUTODISCOVERABLE_CONFIGURATION_FAILED(autoDiscoverable.getClass()), e);
                }
            }
        }
    }

    public void configureMetaProviders(InjectionManager injectionManager, ManagedObjectsFinalizer finalizer) {
        Set<Object> configuredExternals = Collections.newSetFromMap(new IdentityHashMap());
        Set<Binder> configuredBinders = this.configureBinders(injectionManager, Collections.emptySet());
        if (!this.disableMetaProviderConfiguration) {
            this.configureExternalObjects(injectionManager, configuredExternals);
            this.configureFeatures(injectionManager, new HashSet<FeatureRegistration>(), this.resetRegistrations(), finalizer);
            this.configureExternalObjects(injectionManager, configuredExternals);
            this.configureBinders(injectionManager, configuredBinders);
        }
    }

    private Set<Binder> configureBinders(InjectionManager injectionManager, Set<Binder> configured) {
        Set<Binder> allConfigured = Collections.newSetFromMap(new IdentityHashMap());
        allConfigured.addAll(configured);
        Collection<Binder> binders = this.getBinder(configured);
        if (!binders.isEmpty()) {
            injectionManager.register(CompositeBinder.wrap(binders));
            allConfigured.addAll(binders);
        }
        return allConfigured;
    }

    private Collection<Binder> getBinder(Set<Binder> configured) {
        return this.componentBag.getInstances(ComponentBag.BINDERS_ONLY).stream().map(CAST_TO_BINDER).filter(binder -> !configured.contains(binder)).collect(Collectors.toList());
    }

    private void configureExternalObjects(InjectionManager injectionManager, Set<Object> externalObjects) {
        Consumer<Object> registerOnce = o -> {
            if (!externalObjects.contains(o)) {
                injectionManager.register(o);
                externalObjects.add(o);
            }
        };
        this.componentBag.getInstances(model -> ComponentBag.EXTERNAL_ONLY.test((ContractProvider)model, injectionManager)).forEach(registerOnce);
        this.componentBag.getClasses(model -> ComponentBag.EXTERNAL_ONLY.test((ContractProvider)model, injectionManager)).forEach(registerOnce);
    }

    private void configureFeatures(InjectionManager injectionManager, Set<FeatureRegistration> processed, List<FeatureRegistration> unprocessed, ManagedObjectsFinalizer managedObjectsFinalizer) {
        FeatureContextWrapper featureContextWrapper = null;
        for (FeatureRegistration registration : unprocessed) {
            boolean success;
            if (processed.contains(registration)) {
                LOGGER.config(LocalizationMessages.FEATURE_HAS_ALREADY_BEEN_PROCESSED(registration.getFeatureClass()));
                continue;
            }
            RuntimeType runtimeTypeConstraint = registration.getFeatureRuntimeType();
            if (runtimeTypeConstraint != null && !this.type.equals((Object)runtimeTypeConstraint)) {
                LOGGER.config(LocalizationMessages.FEATURE_CONSTRAINED_TO_IGNORED(registration.getFeatureClass(), (Object)registration.runtimeType, (Object)this.type));
                continue;
            }
            Feature feature = registration.getFeature();
            if (feature == null) {
                feature = (Feature)injectionManager.createAndInitialize(registration.getFeatureClass());
                managedObjectsFinalizer.registerForPreDestroyCall(feature);
            } else if (!RuntimeType.CLIENT.equals((Object)this.type)) {
                injectionManager.inject(feature);
            }
            if (this.enabledFeatures.contains(feature)) {
                LOGGER.config(LocalizationMessages.FEATURE_HAS_ALREADY_BEEN_PROCESSED(feature));
                continue;
            }
            if (featureContextWrapper == null) {
                featureContextWrapper = new FeatureContextWrapper(this, injectionManager);
            }
            if (!(success = feature.configure(featureContextWrapper))) continue;
            processed.add(registration);
            ContractProvider providerModel = this.componentBag.getModel(feature.getClass());
            if (providerModel != null) {
                ProviderBinder.bindProvider(feature, providerModel, injectionManager);
            }
            this.configureFeatures(injectionManager, processed, this.resetRegistrations(), managedObjectsFinalizer);
            this.enabledFeatureClasses.add(registration.getFeatureClass());
            this.enabledFeatures.add(feature);
        }
    }

    private List<FeatureRegistration> resetRegistrations() {
        ArrayList<FeatureRegistration> result = new ArrayList<FeatureRegistration>(this.newFeatureRegistrations);
        this.newFeatureRegistrations.clear();
        return result;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CommonConfig)) {
            return false;
        }
        CommonConfig that = (CommonConfig)o;
        if (this.type != that.type) {
            return false;
        }
        if (!this.properties.equals(that.properties)) {
            return false;
        }
        if (!this.componentBag.equals(that.componentBag)) {
            return false;
        }
        if (!this.enabledFeatureClasses.equals(that.enabledFeatureClasses)) {
            return false;
        }
        if (!this.enabledFeatures.equals(that.enabledFeatures)) {
            return false;
        }
        return this.newFeatureRegistrations.equals(that.newFeatureRegistrations);
    }

    public int hashCode() {
        int result = this.type.hashCode();
        result = 31 * result + this.properties.hashCode();
        result = 31 * result + this.componentBag.hashCode();
        result = 31 * result + this.newFeatureRegistrations.hashCode();
        result = 31 * result + this.enabledFeatures.hashCode();
        result = 31 * result + this.enabledFeatureClasses.hashCode();
        return result;
    }

    private static final class FeatureRegistration {
        private final Class<? extends Feature> featureClass;
        private final Feature feature;
        private final RuntimeType runtimeType;

        private FeatureRegistration(Class<? extends Feature> featureClass) {
            this.featureClass = featureClass;
            this.feature = null;
            ConstrainedTo runtimeTypeConstraint = featureClass.getAnnotation(ConstrainedTo.class);
            this.runtimeType = runtimeTypeConstraint == null ? null : runtimeTypeConstraint.value();
        }

        private FeatureRegistration(Feature feature) {
            this.featureClass = feature.getClass();
            this.feature = feature;
            ConstrainedTo runtimeTypeConstraint = this.featureClass.getAnnotation(ConstrainedTo.class);
            this.runtimeType = runtimeTypeConstraint == null ? null : runtimeTypeConstraint.value();
        }

        private Class<? extends Feature> getFeatureClass() {
            return this.featureClass;
        }

        private Feature getFeature() {
            return this.feature;
        }

        private RuntimeType getFeatureRuntimeType() {
            return this.runtimeType;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof FeatureRegistration)) {
                return false;
            }
            FeatureRegistration other = (FeatureRegistration)obj;
            return this.featureClass == other.featureClass || this.feature != null && (this.feature == other.feature || this.feature.equals(other.feature));
        }

        public int hashCode() {
            int hash = 47;
            hash = 13 * hash + (this.feature != null ? this.feature.hashCode() : 0);
            hash = 13 * hash + (this.featureClass != null ? this.featureClass.hashCode() : 0);
            return hash;
        }
    }
}

