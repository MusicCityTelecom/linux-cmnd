/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.NoSuchBeanDefinitionException
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.beans.factory.support.GenericBeanDefinition
 *  org.springframework.context.annotation.ImportBeanDefinitionRegistrar
 *  org.springframework.core.annotation.AnnotationAttributes
 *  org.springframework.core.env.Environment
 *  org.springframework.core.type.AnnotationMetadata
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.autoconfigure.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

public class EntityScanPackages {
    private static final String BEAN = EntityScanPackages.class.getName();
    private static final EntityScanPackages NONE = new EntityScanPackages(new String[0]);
    private final List<String> packageNames;

    EntityScanPackages(String ... packageNames) {
        ArrayList<String> packages = new ArrayList<String>();
        for (String name : packageNames) {
            if (!StringUtils.hasText((String)name)) continue;
            packages.add(name);
        }
        this.packageNames = Collections.unmodifiableList(packages);
    }

    public List<String> getPackageNames() {
        return this.packageNames;
    }

    public static EntityScanPackages get(BeanFactory beanFactory) {
        try {
            return (EntityScanPackages)beanFactory.getBean(BEAN, EntityScanPackages.class);
        }
        catch (NoSuchBeanDefinitionException ex) {
            return NONE;
        }
    }

    public static void register(BeanDefinitionRegistry registry, String ... packageNames) {
        Assert.notNull((Object)registry, (String)"Registry must not be null");
        Assert.notNull((Object)packageNames, (String)"PackageNames must not be null");
        EntityScanPackages.register(registry, Arrays.asList(packageNames));
    }

    public static void register(BeanDefinitionRegistry registry, Collection<String> packageNames) {
        Assert.notNull((Object)registry, (String)"Registry must not be null");
        Assert.notNull(packageNames, (String)"PackageNames must not be null");
        if (registry.containsBeanDefinition(BEAN)) {
            EntityScanPackagesBeanDefinition beanDefinition = (EntityScanPackagesBeanDefinition)registry.getBeanDefinition(BEAN);
            beanDefinition.addPackageNames(packageNames);
        } else {
            registry.registerBeanDefinition(BEAN, (BeanDefinition)new EntityScanPackagesBeanDefinition(packageNames));
        }
    }

    static class EntityScanPackagesBeanDefinition
    extends GenericBeanDefinition {
        private final Set<String> packageNames = new LinkedHashSet<String>();

        EntityScanPackagesBeanDefinition(Collection<String> packageNames) {
            this.setBeanClass(EntityScanPackages.class);
            this.setRole(2);
            this.addPackageNames(packageNames);
        }

        public Supplier<?> getInstanceSupplier() {
            return () -> new EntityScanPackages(StringUtils.toStringArray(this.packageNames));
        }

        private void addPackageNames(Collection<String> additionalPackageNames) {
            this.packageNames.addAll(additionalPackageNames);
        }
    }

    static class Registrar
    implements ImportBeanDefinitionRegistrar {
        private final Environment environment;

        Registrar(Environment environment) {
            this.environment = environment;
        }

        public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
            EntityScanPackages.register(registry, this.getPackagesToScan(metadata));
        }

        private Set<String> getPackagesToScan(AnnotationMetadata metadata) {
            AnnotationAttributes attributes = AnnotationAttributes.fromMap((Map)metadata.getAnnotationAttributes(EntityScan.class.getName()));
            LinkedHashSet<String> packagesToScan = new LinkedHashSet<String>();
            for (String basePackage : attributes.getStringArray("basePackages")) {
                String[] tokenized = StringUtils.tokenizeToStringArray((String)this.environment.resolvePlaceholders(basePackage), (String)",; \t\n");
                Collections.addAll(packagesToScan, tokenized);
            }
            for (Class basePackageClass : attributes.getClassArray("basePackageClasses")) {
                packagesToScan.add(this.environment.resolvePlaceholders(ClassUtils.getPackageName((Class)basePackageClass)));
            }
            if (packagesToScan.isEmpty()) {
                String packageName = ClassUtils.getPackageName((String)metadata.getClassName());
                Assert.state((boolean)StringUtils.hasLength((String)packageName), (String)"@EntityScan cannot be used with the default package");
                return Collections.singleton(packageName);
            }
            return packagesToScan;
        }
    }
}

