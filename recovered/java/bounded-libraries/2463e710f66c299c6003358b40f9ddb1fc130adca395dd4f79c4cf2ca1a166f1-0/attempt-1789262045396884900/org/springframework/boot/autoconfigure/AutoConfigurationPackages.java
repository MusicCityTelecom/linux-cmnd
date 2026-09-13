/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.NoSuchBeanDefinitionException
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.beans.factory.support.GenericBeanDefinition
 *  org.springframework.boot.context.annotation.DeterminableImports
 *  org.springframework.context.annotation.ImportBeanDefinitionRegistrar
 *  org.springframework.core.annotation.AnnotationAttributes
 *  org.springframework.core.type.AnnotationMetadata
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.autoconfigure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.context.annotation.DeterminableImports;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

public abstract class AutoConfigurationPackages {
    private static final Log logger = LogFactory.getLog(AutoConfigurationPackages.class);
    private static final String BEAN = AutoConfigurationPackages.class.getName();

    public static boolean has(BeanFactory beanFactory) {
        return beanFactory.containsBean(BEAN) && !AutoConfigurationPackages.get(beanFactory).isEmpty();
    }

    public static List<String> get(BeanFactory beanFactory) {
        try {
            return ((BasePackages)beanFactory.getBean(BEAN, BasePackages.class)).get();
        }
        catch (NoSuchBeanDefinitionException ex) {
            throw new IllegalStateException("Unable to retrieve @EnableAutoConfiguration base packages");
        }
    }

    public static void register(BeanDefinitionRegistry registry, String ... packageNames) {
        if (registry.containsBeanDefinition(BEAN)) {
            BasePackagesBeanDefinition beanDefinition = (BasePackagesBeanDefinition)registry.getBeanDefinition(BEAN);
            beanDefinition.addBasePackages(packageNames);
        } else {
            registry.registerBeanDefinition(BEAN, (BeanDefinition)new BasePackagesBeanDefinition(packageNames));
        }
    }

    static final class BasePackagesBeanDefinition
    extends GenericBeanDefinition {
        private final Set<String> basePackages = new LinkedHashSet<String>();

        BasePackagesBeanDefinition(String ... basePackages) {
            this.setBeanClass(BasePackages.class);
            this.setRole(2);
            this.addBasePackages(basePackages);
        }

        public Supplier<?> getInstanceSupplier() {
            return () -> new BasePackages(StringUtils.toStringArray(this.basePackages));
        }

        private void addBasePackages(String[] additionalBasePackages) {
            this.basePackages.addAll(Arrays.asList(additionalBasePackages));
        }
    }

    static final class BasePackages {
        private final List<String> packages;
        private boolean loggedBasePackageInfo;

        BasePackages(String ... names) {
            ArrayList<String> packages = new ArrayList<String>();
            for (String name : names) {
                if (!StringUtils.hasText((String)name)) continue;
                packages.add(name);
            }
            this.packages = packages;
        }

        List<String> get() {
            if (!this.loggedBasePackageInfo) {
                if (this.packages.isEmpty()) {
                    if (logger.isWarnEnabled()) {
                        logger.warn((Object)"@EnableAutoConfiguration was declared on a class in the default package. Automatic @Repository and @Entity scanning is not enabled.");
                    }
                } else if (logger.isDebugEnabled()) {
                    String packageNames = StringUtils.collectionToCommaDelimitedString(this.packages);
                    logger.debug((Object)("@EnableAutoConfiguration was declared on a class in the package '" + packageNames + "'. Automatic @Repository and @Entity scanning is enabled."));
                }
                this.loggedBasePackageInfo = true;
            }
            return this.packages;
        }
    }

    private static final class PackageImports {
        private final List<String> packageNames;

        PackageImports(AnnotationMetadata metadata) {
            AnnotationAttributes attributes = AnnotationAttributes.fromMap((Map)metadata.getAnnotationAttributes(AutoConfigurationPackage.class.getName(), false));
            ArrayList<String> packageNames = new ArrayList<String>(Arrays.asList(attributes.getStringArray("basePackages")));
            for (Class basePackageClass : attributes.getClassArray("basePackageClasses")) {
                packageNames.add(basePackageClass.getPackage().getName());
            }
            if (packageNames.isEmpty()) {
                packageNames.add(ClassUtils.getPackageName((String)metadata.getClassName()));
            }
            this.packageNames = Collections.unmodifiableList(packageNames);
        }

        List<String> getPackageNames() {
            return this.packageNames;
        }

        public boolean equals(Object obj) {
            if (obj == null || this.getClass() != obj.getClass()) {
                return false;
            }
            return this.packageNames.equals(((PackageImports)obj).packageNames);
        }

        public int hashCode() {
            return this.packageNames.hashCode();
        }

        public String toString() {
            return "Package Imports " + this.packageNames;
        }
    }

    static class Registrar
    implements ImportBeanDefinitionRegistrar,
    DeterminableImports {
        Registrar() {
        }

        public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
            AutoConfigurationPackages.register(registry, new PackageImports(metadata).getPackageNames().toArray(new String[0]));
        }

        public Set<Object> determineImports(AnnotationMetadata metadata) {
            return Collections.singleton(new PackageImports(metadata));
        }
    }
}

