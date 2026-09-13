/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.Closure
 *  org.springframework.beans.BeanUtils
 *  org.springframework.beans.factory.BeanDefinitionStoreException
 *  org.springframework.beans.factory.groovy.GroovyBeanDefinitionReader
 *  org.springframework.beans.factory.support.AbstractBeanDefinitionReader
 *  org.springframework.beans.factory.support.BeanDefinitionReader
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.beans.factory.support.BeanNameGenerator
 *  org.springframework.beans.factory.xml.XmlBeanDefinitionReader
 *  org.springframework.context.annotation.AnnotatedBeanDefinitionReader
 *  org.springframework.context.annotation.ClassPathBeanDefinitionScanner
 *  org.springframework.core.SpringProperties
 *  org.springframework.core.env.ConfigurableEnvironment
 *  org.springframework.core.env.Environment
 *  org.springframework.core.io.ClassPathResource
 *  org.springframework.core.io.Resource
 *  org.springframework.core.io.ResourceLoader
 *  org.springframework.core.io.support.PathMatchingResourcePatternResolver
 *  org.springframework.core.io.support.ResourcePatternResolver
 *  org.springframework.core.type.filter.AbstractTypeHierarchyTraversingFilter
 *  org.springframework.core.type.filter.TypeFilter
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.ObjectUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot;

import groovy.lang.Closure;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.groovy.GroovyBeanDefinitionReader;
import org.springframework.beans.factory.support.AbstractBeanDefinitionReader;
import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.SpringProperties;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.filter.AbstractTypeHierarchyTraversingFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

class BeanDefinitionLoader {
    private static final boolean XML_ENABLED = !SpringProperties.getFlag((String)"spring.xml.ignore");
    private static final Pattern GROOVY_CLOSURE_PATTERN = Pattern.compile(".*\\$_.*closure.*");
    private final Object[] sources;
    private final AnnotatedBeanDefinitionReader annotatedReader;
    private final AbstractBeanDefinitionReader xmlReader;
    private final BeanDefinitionReader groovyReader;
    private final ClassPathBeanDefinitionScanner scanner;
    private ResourceLoader resourceLoader;

    BeanDefinitionLoader(BeanDefinitionRegistry registry, Object ... sources) {
        Assert.notNull((Object)registry, (String)"Registry must not be null");
        Assert.notEmpty((Object[])sources, (String)"Sources must not be empty");
        this.sources = sources;
        this.annotatedReader = new AnnotatedBeanDefinitionReader(registry);
        this.xmlReader = XML_ENABLED ? new XmlBeanDefinitionReader(registry) : null;
        this.groovyReader = this.isGroovyPresent() ? new GroovyBeanDefinitionReader(registry) : null;
        this.scanner = new ClassPathBeanDefinitionScanner(registry);
        this.scanner.addExcludeFilter((TypeFilter)new ClassExcludeFilter(sources));
    }

    void setBeanNameGenerator(BeanNameGenerator beanNameGenerator) {
        this.annotatedReader.setBeanNameGenerator(beanNameGenerator);
        this.scanner.setBeanNameGenerator(beanNameGenerator);
        if (this.xmlReader != null) {
            this.xmlReader.setBeanNameGenerator(beanNameGenerator);
        }
    }

    void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
        this.scanner.setResourceLoader(resourceLoader);
        if (this.xmlReader != null) {
            this.xmlReader.setResourceLoader(resourceLoader);
        }
    }

    void setEnvironment(ConfigurableEnvironment environment) {
        this.annotatedReader.setEnvironment((Environment)environment);
        this.scanner.setEnvironment((Environment)environment);
        if (this.xmlReader != null) {
            this.xmlReader.setEnvironment((Environment)environment);
        }
    }

    void load() {
        for (Object source : this.sources) {
            this.load(source);
        }
    }

    private void load(Object source) {
        Assert.notNull((Object)source, (String)"Source must not be null");
        if (source instanceof Class) {
            this.load((Class)source);
            return;
        }
        if (source instanceof Resource) {
            this.load((Resource)source);
            return;
        }
        if (source instanceof Package) {
            this.load((Package)source);
            return;
        }
        if (source instanceof CharSequence) {
            this.load((CharSequence)source);
            return;
        }
        throw new IllegalArgumentException("Invalid source type " + source.getClass());
    }

    private void load(Class<?> source) {
        if (this.isGroovyPresent() && GroovyBeanDefinitionSource.class.isAssignableFrom(source)) {
            GroovyBeanDefinitionSource loader = (GroovyBeanDefinitionSource)BeanUtils.instantiateClass(source, GroovyBeanDefinitionSource.class);
            ((GroovyBeanDefinitionReader)this.groovyReader).beans(loader.getBeans());
        }
        if (this.isEligible(source)) {
            this.annotatedReader.register(new Class[]{source});
        }
    }

    private void load(Resource source) {
        if (source.getFilename().endsWith(".groovy")) {
            if (this.groovyReader == null) {
                throw new BeanDefinitionStoreException("Cannot load Groovy beans without Groovy on classpath");
            }
            this.groovyReader.loadBeanDefinitions(source);
        } else {
            if (this.xmlReader == null) {
                throw new BeanDefinitionStoreException("Cannot load XML bean definitions when XML support is disabled");
            }
            this.xmlReader.loadBeanDefinitions(source);
        }
    }

    private void load(Package source) {
        this.scanner.scan(new String[]{source.getName()});
    }

    private void load(CharSequence source) {
        String resolvedSource = this.scanner.getEnvironment().resolvePlaceholders(source.toString());
        try {
            this.load(ClassUtils.forName((String)resolvedSource, null));
            return;
        }
        catch (ClassNotFoundException | IllegalArgumentException exception) {
            if (this.loadAsResources(resolvedSource)) {
                return;
            }
            Package packageResource = this.findPackage(resolvedSource);
            if (packageResource != null) {
                this.load(packageResource);
                return;
            }
            throw new IllegalArgumentException("Invalid source '" + resolvedSource + "'");
        }
    }

    private boolean loadAsResources(String resolvedSource) {
        Resource[] resources;
        boolean foundCandidate = false;
        for (Resource resource : resources = this.findResources(resolvedSource)) {
            if (!this.isLoadCandidate(resource)) continue;
            foundCandidate = true;
            this.load(resource);
        }
        return foundCandidate;
    }

    private boolean isGroovyPresent() {
        return ClassUtils.isPresent((String)"groovy.lang.MetaClass", null);
    }

    private Resource[] findResources(String source) {
        ResourceLoader loader = this.resourceLoader != null ? this.resourceLoader : new PathMatchingResourcePatternResolver();
        try {
            if (loader instanceof ResourcePatternResolver) {
                return ((ResourcePatternResolver)loader).getResources(source);
            }
            return new Resource[]{loader.getResource(source)};
        }
        catch (IOException ex) {
            throw new IllegalStateException("Error reading source '" + source + "'");
        }
    }

    private boolean isLoadCandidate(Resource resource) {
        String path;
        if (resource == null || !resource.exists()) {
            return false;
        }
        if (resource instanceof ClassPathResource && (path = ((ClassPathResource)resource).getPath()).indexOf(46) == -1) {
            try {
                return Package.getPackage(path) == null;
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return true;
    }

    private Package findPackage(CharSequence source) {
        Package pkg = Package.getPackage(source.toString());
        if (pkg != null) {
            return pkg;
        }
        try {
            Resource[] resources;
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(this.getClass().getClassLoader());
            Resource[] resourceArray = resources = resolver.getResources(ClassUtils.convertClassNameToResourcePath((String)source.toString()) + "/*.class");
            int n = resourceArray.length;
            int n2 = 0;
            if (n2 < n) {
                Resource resource = resourceArray[n2];
                String className = StringUtils.stripFilenameExtension((String)resource.getFilename());
                this.load(Class.forName(source.toString() + "." + className));
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return Package.getPackage(source.toString());
    }

    private boolean isEligible(Class<?> type) {
        return !type.isAnonymousClass() && !this.isGroovyClosure(type) && !this.hasNoConstructors(type);
    }

    private boolean isGroovyClosure(Class<?> type) {
        return GROOVY_CLOSURE_PATTERN.matcher(type.getName()).matches();
    }

    private boolean hasNoConstructors(Class<?> type) {
        Object[] constructors = type.getDeclaredConstructors();
        return ObjectUtils.isEmpty((Object[])constructors);
    }

    @FunctionalInterface
    protected static interface GroovyBeanDefinitionSource {
        public Closure<?> getBeans();
    }

    private static class ClassExcludeFilter
    extends AbstractTypeHierarchyTraversingFilter {
        private final Set<String> classNames = new HashSet<String>();

        ClassExcludeFilter(Object ... sources) {
            super(false, false);
            for (Object source : sources) {
                if (!(source instanceof Class)) continue;
                this.classNames.add(((Class)source).getName());
            }
        }

        protected boolean matchClassName(String className) {
            return this.classNames.contains(className);
        }
    }
}

