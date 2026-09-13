/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.beans.factory.support.BeanNameGenerator
 *  org.springframework.context.annotation.AnnotationBeanNameGenerator
 *  org.springframework.context.annotation.ConfigurationClassPostProcessor
 *  org.springframework.context.annotation.TypeFilterUtils
 *  org.springframework.core.annotation.AnnotationAttributes
 *  org.springframework.core.env.Environment
 *  org.springframework.core.io.ResourceLoader
 *  org.springframework.core.type.AnnotationMetadata
 *  org.springframework.core.type.filter.TypeFilter
 *  org.springframework.lang.NonNull
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.data.repository.config;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.annotation.TypeFilterUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.data.config.ConfigurationUtils;
import org.springframework.data.repository.config.BootstrapMode;
import org.springframework.data.repository.config.DefaultRepositoryBaseClass;
import org.springframework.data.repository.config.RepositoryConfigurationSourceSupport;
import org.springframework.data.util.Streamable;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

public class AnnotationRepositoryConfigurationSource
extends RepositoryConfigurationSourceSupport {
    private static final String REPOSITORY_IMPLEMENTATION_POSTFIX = "repositoryImplementationPostfix";
    private static final String BASE_PACKAGES = "basePackages";
    private static final String BASE_PACKAGE_CLASSES = "basePackageClasses";
    private static final String NAMED_QUERIES_LOCATION = "namedQueriesLocation";
    private static final String QUERY_LOOKUP_STRATEGY = "queryLookupStrategy";
    private static final String REPOSITORY_FACTORY_BEAN_CLASS = "repositoryFactoryBeanClass";
    private static final String REPOSITORY_BASE_CLASS = "repositoryBaseClass";
    private static final String CONSIDER_NESTED_REPOSITORIES = "considerNestedRepositories";
    private static final String BOOTSTRAP_MODE = "bootstrapMode";
    private final AnnotationMetadata configMetadata;
    private final AnnotationMetadata enableAnnotationMetadata;
    private final AnnotationAttributes attributes;
    private final ResourceLoader resourceLoader;
    private final Environment environment;
    private final BeanDefinitionRegistry registry;
    private final boolean hasExplicitFilters;

    @Deprecated
    public AnnotationRepositoryConfigurationSource(AnnotationMetadata metadata, Class<? extends Annotation> annotation, ResourceLoader resourceLoader, Environment environment, BeanDefinitionRegistry registry) {
        this(metadata, annotation, resourceLoader, environment, registry, null);
    }

    public AnnotationRepositoryConfigurationSource(AnnotationMetadata metadata, Class<? extends Annotation> annotation, ResourceLoader resourceLoader, Environment environment, BeanDefinitionRegistry registry, @Nullable BeanNameGenerator generator) {
        super(environment, ConfigurationUtils.getRequiredClassLoader(resourceLoader), registry, AnnotationRepositoryConfigurationSource.defaultBeanNameGenerator(generator));
        Assert.notNull((Object)metadata, (String)"Metadata must not be null");
        Assert.notNull(annotation, (String)"Annotation must not be null");
        Assert.notNull((Object)resourceLoader, (String)"ResourceLoader must not be null");
        Map annotationAttributes = metadata.getAnnotationAttributes(annotation.getName());
        if (annotationAttributes == null) {
            throw new IllegalStateException(String.format("Unable to obtain annotation attributes for %s", annotation));
        }
        this.attributes = new AnnotationAttributes(annotationAttributes);
        this.enableAnnotationMetadata = AnnotationMetadata.introspect(annotation);
        this.configMetadata = metadata;
        this.resourceLoader = resourceLoader;
        this.environment = environment;
        this.registry = registry;
        this.hasExplicitFilters = AnnotationRepositoryConfigurationSource.hasExplicitFilters(this.attributes);
    }

    @Override
    public Streamable<String> getBasePackages() {
        String[] value = this.attributes.getStringArray("value");
        String[] basePackages = this.attributes.getStringArray(BASE_PACKAGES);
        Class[] basePackageClasses = this.attributes.getClassArray(BASE_PACKAGE_CLASSES);
        if (value.length == 0 && basePackages.length == 0 && basePackageClasses.length == 0) {
            String className = this.configMetadata.getClassName();
            return Streamable.of(ClassUtils.getPackageName((String)className));
        }
        HashSet<String> packages = new HashSet<String>();
        packages.addAll(Arrays.asList(value));
        packages.addAll(Arrays.asList(basePackages));
        Arrays.stream(basePackageClasses).map(ClassUtils::getPackageName).forEach(it -> packages.add((String)it));
        return Streamable.of(packages);
    }

    @Override
    public Optional<Object> getQueryLookupStrategyKey() {
        return Optional.ofNullable(this.attributes.get((Object)QUERY_LOOKUP_STRATEGY));
    }

    @Override
    public Optional<String> getNamedQueryLocation() {
        return this.getNullDefaultedAttribute(NAMED_QUERIES_LOCATION);
    }

    @Override
    public Optional<String> getRepositoryImplementationPostfix() {
        return this.getNullDefaultedAttribute(REPOSITORY_IMPLEMENTATION_POSTFIX);
    }

    @Override
    @NonNull
    public Object getSource() {
        return this.configMetadata;
    }

    @Override
    protected Iterable<TypeFilter> getIncludeFilters() {
        return this.parseFilters("includeFilters");
    }

    @Override
    public Streamable<TypeFilter> getExcludeFilters() {
        return this.parseFilters("excludeFilters");
    }

    @Override
    public Optional<String> getRepositoryFactoryBeanClassName() {
        return Optional.of(this.attributes.getClass(REPOSITORY_FACTORY_BEAN_CLASS).getName());
    }

    @Override
    public Optional<String> getRepositoryBaseClassName() {
        if (!this.attributes.containsKey((Object)REPOSITORY_BASE_CLASS)) {
            return Optional.empty();
        }
        Class repositoryBaseClass = this.attributes.getClass(REPOSITORY_BASE_CLASS);
        return DefaultRepositoryBaseClass.class.equals((Object)repositoryBaseClass) ? Optional.empty() : Optional.of(repositoryBaseClass.getName());
    }

    public AnnotationAttributes getAttributes() {
        return this.attributes;
    }

    public AnnotationMetadata getEnableAnnotationMetadata() {
        return this.enableAnnotationMetadata;
    }

    @Override
    public boolean shouldConsiderNestedRepositories() {
        return this.attributes.containsKey((Object)CONSIDER_NESTED_REPOSITORIES) && this.attributes.getBoolean(CONSIDER_NESTED_REPOSITORIES);
    }

    @Override
    public Optional<String> getAttribute(String name) {
        return this.getAttribute(name, String.class);
    }

    @Override
    public <T> Optional<T> getAttribute(String name, Class<T> type) {
        if (!this.attributes.containsKey((Object)name)) {
            throw new IllegalArgumentException(String.format("No attribute named %s found", name));
        }
        Object value = this.attributes.get((Object)name);
        if (value == null) {
            return Optional.empty();
        }
        Assert.isInstanceOf(type, (Object)value, () -> String.format("Attribute value for %s is of type %s but was expected to be of type %s", name, value.getClass(), type));
        Object result = String.class.isInstance(value) ? (StringUtils.hasText((String)((String)value)) ? value : null) : value;
        return Optional.ofNullable(type.cast(result));
    }

    @Override
    public boolean usesExplicitFilters() {
        return this.hasExplicitFilters;
    }

    @Override
    public BootstrapMode getBootstrapMode() {
        try {
            return (BootstrapMode)this.attributes.getEnum(BOOTSTRAP_MODE);
        }
        catch (IllegalArgumentException o_O) {
            return BootstrapMode.DEFAULT;
        }
    }

    @Override
    public String getResourceDescription() {
        String simpleClassName = ClassUtils.getShortName((String)this.configMetadata.getClassName());
        String annoationClassName = ClassUtils.getShortName((String)this.enableAnnotationMetadata.getClassName());
        return String.format("@%s declared on %s", annoationClassName, simpleClassName);
    }

    private Streamable<TypeFilter> parseFilters(String attributeName) {
        AnnotationAttributes[] filters = this.attributes.getAnnotationArray(attributeName);
        return Streamable.of(() -> Arrays.stream(filters).flatMap(it -> TypeFilterUtils.createTypeFiltersFor((AnnotationAttributes)it, (Environment)this.environment, (ResourceLoader)this.resourceLoader, (BeanDefinitionRegistry)this.registry).stream()));
    }

    private Optional<String> getNullDefaultedAttribute(String attributeName) {
        String attribute = this.attributes.getString(attributeName);
        return StringUtils.hasText((String)attribute) ? Optional.of(attribute) : Optional.empty();
    }

    private static boolean hasExplicitFilters(AnnotationAttributes attributes) {
        return Stream.of("includeFilters", "excludeFilters").anyMatch(it -> attributes.getAnnotationArray(it).length > 0);
    }

    private static BeanNameGenerator defaultBeanNameGenerator(@Nullable BeanNameGenerator generator) {
        return generator == null || ConfigurationClassPostProcessor.IMPORT_BEAN_NAME_GENERATOR.equals(generator) ? new AnnotationBeanNameGenerator() : generator;
    }
}

