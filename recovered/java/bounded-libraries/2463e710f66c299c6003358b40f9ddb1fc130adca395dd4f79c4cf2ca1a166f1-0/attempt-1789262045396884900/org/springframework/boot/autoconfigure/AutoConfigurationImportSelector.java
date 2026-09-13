/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.Aware
 *  org.springframework.beans.factory.BeanClassLoaderAware
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.beans.factory.NoSuchBeanDefinitionException
 *  org.springframework.beans.factory.config.ConfigurableListableBeanFactory
 *  org.springframework.boot.context.annotation.ImportCandidates
 *  org.springframework.boot.context.properties.bind.Binder
 *  org.springframework.context.EnvironmentAware
 *  org.springframework.context.ResourceLoaderAware
 *  org.springframework.context.annotation.DeferredImportSelector
 *  org.springframework.context.annotation.DeferredImportSelector$Group
 *  org.springframework.context.annotation.DeferredImportSelector$Group$Entry
 *  org.springframework.core.Ordered
 *  org.springframework.core.annotation.AnnotationAttributes
 *  org.springframework.core.env.ConfigurableEnvironment
 *  org.springframework.core.env.Environment
 *  org.springframework.core.io.ResourceLoader
 *  org.springframework.core.io.support.SpringFactoriesLoader
 *  org.springframework.core.type.AnnotationMetadata
 *  org.springframework.core.type.classreading.CachingMetadataReaderFactory
 *  org.springframework.core.type.classreading.MetadataReaderFactory
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.autoconfigure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.Aware;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurationImportEvent;
import org.springframework.boot.autoconfigure.AutoConfigurationImportFilter;
import org.springframework.boot.autoconfigure.AutoConfigurationImportListener;
import org.springframework.boot.autoconfigure.AutoConfigurationMetadata;
import org.springframework.boot.autoconfigure.AutoConfigurationMetadataLoader;
import org.springframework.boot.autoconfigure.AutoConfigurationSorter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.annotation.ImportCandidates;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

public class AutoConfigurationImportSelector
implements DeferredImportSelector,
BeanClassLoaderAware,
ResourceLoaderAware,
BeanFactoryAware,
EnvironmentAware,
Ordered {
    private static final AutoConfigurationEntry EMPTY_ENTRY = new AutoConfigurationEntry();
    private static final String[] NO_IMPORTS = new String[0];
    private static final Log logger = LogFactory.getLog(AutoConfigurationImportSelector.class);
    private static final String PROPERTY_NAME_AUTOCONFIGURE_EXCLUDE = "spring.autoconfigure.exclude";
    private ConfigurableListableBeanFactory beanFactory;
    private Environment environment;
    private ClassLoader beanClassLoader;
    private ResourceLoader resourceLoader;
    private ConfigurationClassFilter configurationClassFilter;

    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        if (!this.isEnabled(annotationMetadata)) {
            return NO_IMPORTS;
        }
        AutoConfigurationEntry autoConfigurationEntry = this.getAutoConfigurationEntry(annotationMetadata);
        return StringUtils.toStringArray(autoConfigurationEntry.getConfigurations());
    }

    public Predicate<String> getExclusionFilter() {
        return this::shouldExclude;
    }

    private boolean shouldExclude(String configurationClassName) {
        return this.getConfigurationClassFilter().filter(Collections.singletonList(configurationClassName)).isEmpty();
    }

    protected AutoConfigurationEntry getAutoConfigurationEntry(AnnotationMetadata annotationMetadata) {
        if (!this.isEnabled(annotationMetadata)) {
            return EMPTY_ENTRY;
        }
        AnnotationAttributes attributes = this.getAttributes(annotationMetadata);
        List<String> configurations = this.getCandidateConfigurations(annotationMetadata, attributes);
        configurations = this.removeDuplicates(configurations);
        Set<String> exclusions = this.getExclusions(annotationMetadata, attributes);
        this.checkExcludedClasses(configurations, exclusions);
        configurations.removeAll(exclusions);
        configurations = this.getConfigurationClassFilter().filter(configurations);
        this.fireAutoConfigurationImportEvents(configurations, exclusions);
        return new AutoConfigurationEntry(configurations, exclusions);
    }

    public Class<? extends DeferredImportSelector.Group> getImportGroup() {
        return AutoConfigurationGroup.class;
    }

    protected boolean isEnabled(AnnotationMetadata metadata) {
        if (this.getClass() == AutoConfigurationImportSelector.class) {
            return (Boolean)this.getEnvironment().getProperty("spring.boot.enableautoconfiguration", Boolean.class, (Object)true);
        }
        return true;
    }

    protected AnnotationAttributes getAttributes(AnnotationMetadata metadata) {
        String name = this.getAnnotationClass().getName();
        AnnotationAttributes attributes = AnnotationAttributes.fromMap((Map)metadata.getAnnotationAttributes(name, true));
        Assert.notNull((Object)attributes, () -> "No auto-configuration attributes found. Is " + metadata.getClassName() + " annotated with " + ClassUtils.getShortName((String)name) + "?");
        return attributes;
    }

    protected Class<?> getAnnotationClass() {
        return EnableAutoConfiguration.class;
    }

    protected List<String> getCandidateConfigurations(AnnotationMetadata metadata, AnnotationAttributes attributes) {
        ArrayList<String> configurations = new ArrayList<String>(SpringFactoriesLoader.loadFactoryNames(this.getSpringFactoriesLoaderFactoryClass(), (ClassLoader)this.getBeanClassLoader()));
        ImportCandidates.load(AutoConfiguration.class, (ClassLoader)this.getBeanClassLoader()).forEach(configurations::add);
        Assert.notEmpty(configurations, (String)"No auto configuration classes found in META-INF/spring.factories nor in META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports. If you are using a custom packaging, make sure that file is correct.");
        return configurations;
    }

    protected Class<?> getSpringFactoriesLoaderFactoryClass() {
        return EnableAutoConfiguration.class;
    }

    private void checkExcludedClasses(List<String> configurations, Set<String> exclusions) {
        ArrayList<String> invalidExcludes = new ArrayList<String>(exclusions.size());
        for (String exclusion : exclusions) {
            if (!ClassUtils.isPresent((String)exclusion, (ClassLoader)this.getClass().getClassLoader()) || configurations.contains(exclusion)) continue;
            invalidExcludes.add(exclusion);
        }
        if (!invalidExcludes.isEmpty()) {
            this.handleInvalidExcludes(invalidExcludes);
        }
    }

    protected void handleInvalidExcludes(List<String> invalidExcludes) {
        StringBuilder message = new StringBuilder();
        for (String exclude : invalidExcludes) {
            message.append("\t- ").append(exclude).append(String.format("%n", new Object[0]));
        }
        throw new IllegalStateException(String.format("The following classes could not be excluded because they are not auto-configuration classes:%n%s", message));
    }

    protected Set<String> getExclusions(AnnotationMetadata metadata, AnnotationAttributes attributes) {
        LinkedHashSet<String> excluded = new LinkedHashSet<String>();
        excluded.addAll(this.asList(attributes, "exclude"));
        excluded.addAll(this.asList(attributes, "excludeName"));
        excluded.addAll(this.getExcludeAutoConfigurationsProperty());
        return excluded;
    }

    protected List<String> getExcludeAutoConfigurationsProperty() {
        Environment environment = this.getEnvironment();
        if (environment == null) {
            return Collections.emptyList();
        }
        if (environment instanceof ConfigurableEnvironment) {
            Binder binder = Binder.get((Environment)environment);
            return (List)binder.bind(PROPERTY_NAME_AUTOCONFIGURE_EXCLUDE, String[].class).map(Arrays::asList).orElse(Collections.emptyList());
        }
        String[] excludes = (String[])environment.getProperty(PROPERTY_NAME_AUTOCONFIGURE_EXCLUDE, String[].class);
        return excludes != null ? Arrays.asList(excludes) : Collections.emptyList();
    }

    protected List<AutoConfigurationImportFilter> getAutoConfigurationImportFilters() {
        return SpringFactoriesLoader.loadFactories(AutoConfigurationImportFilter.class, (ClassLoader)this.beanClassLoader);
    }

    private ConfigurationClassFilter getConfigurationClassFilter() {
        if (this.configurationClassFilter == null) {
            List<AutoConfigurationImportFilter> filters = this.getAutoConfigurationImportFilters();
            for (AutoConfigurationImportFilter filter : filters) {
                this.invokeAwareMethods(filter);
            }
            this.configurationClassFilter = new ConfigurationClassFilter(this.beanClassLoader, filters);
        }
        return this.configurationClassFilter;
    }

    protected final <T> List<T> removeDuplicates(List<T> list) {
        return new ArrayList<T>(new LinkedHashSet<T>(list));
    }

    protected final List<String> asList(AnnotationAttributes attributes, String name) {
        String[] value = attributes.getStringArray(name);
        return Arrays.asList(value);
    }

    private void fireAutoConfigurationImportEvents(List<String> configurations, Set<String> exclusions) {
        List<AutoConfigurationImportListener> listeners = this.getAutoConfigurationImportListeners();
        if (!listeners.isEmpty()) {
            AutoConfigurationImportEvent event = new AutoConfigurationImportEvent(this, configurations, exclusions);
            for (AutoConfigurationImportListener listener : listeners) {
                this.invokeAwareMethods(listener);
                listener.onAutoConfigurationImportEvent(event);
            }
        }
    }

    protected List<AutoConfigurationImportListener> getAutoConfigurationImportListeners() {
        return SpringFactoriesLoader.loadFactories(AutoConfigurationImportListener.class, (ClassLoader)this.beanClassLoader);
    }

    private void invokeAwareMethods(Object instance) {
        if (instance instanceof Aware) {
            if (instance instanceof BeanClassLoaderAware) {
                ((BeanClassLoaderAware)instance).setBeanClassLoader(this.beanClassLoader);
            }
            if (instance instanceof BeanFactoryAware) {
                ((BeanFactoryAware)instance).setBeanFactory((BeanFactory)this.beanFactory);
            }
            if (instance instanceof EnvironmentAware) {
                ((EnvironmentAware)instance).setEnvironment(this.environment);
            }
            if (instance instanceof ResourceLoaderAware) {
                ((ResourceLoaderAware)instance).setResourceLoader(this.resourceLoader);
            }
        }
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        Assert.isInstanceOf(ConfigurableListableBeanFactory.class, (Object)beanFactory);
        this.beanFactory = (ConfigurableListableBeanFactory)beanFactory;
    }

    protected final ConfigurableListableBeanFactory getBeanFactory() {
        return this.beanFactory;
    }

    public void setBeanClassLoader(ClassLoader classLoader) {
        this.beanClassLoader = classLoader;
    }

    protected ClassLoader getBeanClassLoader() {
        return this.beanClassLoader;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    protected final Environment getEnvironment() {
        return this.environment;
    }

    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    protected final ResourceLoader getResourceLoader() {
        return this.resourceLoader;
    }

    public int getOrder() {
        return 0x7FFFFFFE;
    }

    protected static class AutoConfigurationEntry {
        private final List<String> configurations;
        private final Set<String> exclusions;

        private AutoConfigurationEntry() {
            this.configurations = Collections.emptyList();
            this.exclusions = Collections.emptySet();
        }

        AutoConfigurationEntry(Collection<String> configurations, Collection<String> exclusions) {
            this.configurations = new ArrayList<String>(configurations);
            this.exclusions = new HashSet<String>(exclusions);
        }

        public List<String> getConfigurations() {
            return this.configurations;
        }

        public Set<String> getExclusions() {
            return this.exclusions;
        }
    }

    private static class AutoConfigurationGroup
    implements DeferredImportSelector.Group,
    BeanClassLoaderAware,
    BeanFactoryAware,
    ResourceLoaderAware {
        private final Map<String, AnnotationMetadata> entries = new LinkedHashMap<String, AnnotationMetadata>();
        private final List<AutoConfigurationEntry> autoConfigurationEntries = new ArrayList<AutoConfigurationEntry>();
        private ClassLoader beanClassLoader;
        private BeanFactory beanFactory;
        private ResourceLoader resourceLoader;
        private AutoConfigurationMetadata autoConfigurationMetadata;

        private AutoConfigurationGroup() {
        }

        public void setBeanClassLoader(ClassLoader classLoader) {
            this.beanClassLoader = classLoader;
        }

        public void setBeanFactory(BeanFactory beanFactory) {
            this.beanFactory = beanFactory;
        }

        public void setResourceLoader(ResourceLoader resourceLoader) {
            this.resourceLoader = resourceLoader;
        }

        public void process(AnnotationMetadata annotationMetadata, DeferredImportSelector deferredImportSelector) {
            Assert.state((boolean)(deferredImportSelector instanceof AutoConfigurationImportSelector), () -> String.format("Only %s implementations are supported, got %s", AutoConfigurationImportSelector.class.getSimpleName(), deferredImportSelector.getClass().getName()));
            AutoConfigurationEntry autoConfigurationEntry = ((AutoConfigurationImportSelector)deferredImportSelector).getAutoConfigurationEntry(annotationMetadata);
            this.autoConfigurationEntries.add(autoConfigurationEntry);
            for (String importClassName : autoConfigurationEntry.getConfigurations()) {
                this.entries.putIfAbsent(importClassName, annotationMetadata);
            }
        }

        public Iterable<DeferredImportSelector.Group.Entry> selectImports() {
            if (this.autoConfigurationEntries.isEmpty()) {
                return Collections.emptyList();
            }
            Set allExclusions = this.autoConfigurationEntries.stream().map(AutoConfigurationEntry::getExclusions).flatMap(Collection::stream).collect(Collectors.toSet());
            Set processedConfigurations = this.autoConfigurationEntries.stream().map(AutoConfigurationEntry::getConfigurations).flatMap(Collection::stream).collect(Collectors.toCollection(LinkedHashSet::new));
            processedConfigurations.removeAll(allExclusions);
            return this.sortAutoConfigurations(processedConfigurations, this.getAutoConfigurationMetadata()).stream().map(importClassName -> new DeferredImportSelector.Group.Entry(this.entries.get(importClassName), importClassName)).collect(Collectors.toList());
        }

        private AutoConfigurationMetadata getAutoConfigurationMetadata() {
            if (this.autoConfigurationMetadata == null) {
                this.autoConfigurationMetadata = AutoConfigurationMetadataLoader.loadMetadata(this.beanClassLoader);
            }
            return this.autoConfigurationMetadata;
        }

        private List<String> sortAutoConfigurations(Set<String> configurations, AutoConfigurationMetadata autoConfigurationMetadata) {
            return new AutoConfigurationSorter(this.getMetadataReaderFactory(), autoConfigurationMetadata).getInPriorityOrder(configurations);
        }

        private MetadataReaderFactory getMetadataReaderFactory() {
            try {
                return (MetadataReaderFactory)this.beanFactory.getBean("org.springframework.boot.autoconfigure.internalCachingMetadataReaderFactory", MetadataReaderFactory.class);
            }
            catch (NoSuchBeanDefinitionException ex) {
                return new CachingMetadataReaderFactory(this.resourceLoader);
            }
        }
    }

    private static class ConfigurationClassFilter {
        private final AutoConfigurationMetadata autoConfigurationMetadata;
        private final List<AutoConfigurationImportFilter> filters;

        ConfigurationClassFilter(ClassLoader classLoader, List<AutoConfigurationImportFilter> filters) {
            this.autoConfigurationMetadata = AutoConfigurationMetadataLoader.loadMetadata(classLoader);
            this.filters = filters;
        }

        List<String> filter(List<String> configurations) {
            long startTime = System.nanoTime();
            String[] candidates = StringUtils.toStringArray(configurations);
            boolean skipped = false;
            for (AutoConfigurationImportFilter autoConfigurationImportFilter : this.filters) {
                boolean[] match = autoConfigurationImportFilter.match(candidates, this.autoConfigurationMetadata);
                for (int i = 0; i < match.length; ++i) {
                    if (match[i]) continue;
                    candidates[i] = null;
                    skipped = true;
                }
            }
            if (!skipped) {
                return configurations;
            }
            ArrayList<String> result = new ArrayList<String>(candidates.length);
            for (String candidate : candidates) {
                if (candidate == null) continue;
                result.add(candidate);
            }
            if (logger.isTraceEnabled()) {
                int n = configurations.size() - result.size();
                logger.trace((Object)("Filtered " + n + " auto configuration class in " + TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime) + " ms"));
            }
            return result;
        }
    }
}

