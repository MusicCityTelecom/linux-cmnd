/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.NoSuchBeanDefinitionException
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.config.BeanFactoryPostProcessor
 *  org.springframework.beans.factory.config.ConfigurableListableBeanFactory
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.context.EnvironmentAware
 *  org.springframework.context.annotation.ImportBeanDefinitionRegistrar
 *  org.springframework.core.Ordered
 *  org.springframework.core.env.Environment
 *  org.springframework.core.io.support.SpringFactoriesLoader
 *  org.springframework.core.type.AnnotationMetadata
 *  org.springframework.util.CollectionUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.sql.init.dependency;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.sql.init.dependency.DatabaseInitializerDetector;
import org.springframework.boot.sql.init.dependency.DependsOnDatabaseInitializationDetector;
import org.springframework.boot.util.Instantiator;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class DatabaseInitializationDependencyConfigurer
implements ImportBeanDefinitionRegistrar {
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        String name = DependsOnDatabaseInitializationPostProcessor.class.getName();
        if (!registry.containsBeanDefinition(name)) {
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(DependsOnDatabaseInitializationPostProcessor.class);
            registry.registerBeanDefinition(name, (BeanDefinition)builder.getBeanDefinition());
        }
    }

    static class DependsOnDatabaseInitializationPostProcessor
    implements BeanFactoryPostProcessor,
    EnvironmentAware,
    Ordered {
        private Environment environment;

        DependsOnDatabaseInitializationPostProcessor() {
        }

        public void setEnvironment(Environment environment) {
            this.environment = environment;
        }

        public int getOrder() {
            return 0;
        }

        public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
            InitializerBeanNames initializerBeanNames = this.detectInitializerBeanNames(beanFactory);
            if (initializerBeanNames.isEmpty()) {
                return;
            }
            Set previousInitializerBeanNamesBatch = null;
            for (Set initializerBeanNamesBatch : initializerBeanNames.batchedBeanNames()) {
                for (String initializerBeanName : initializerBeanNamesBatch) {
                    BeanDefinition beanDefinition = DependsOnDatabaseInitializationPostProcessor.getBeanDefinition(initializerBeanName, beanFactory);
                    beanDefinition.setDependsOn(this.merge(beanDefinition.getDependsOn(), previousInitializerBeanNamesBatch));
                }
                previousInitializerBeanNamesBatch = initializerBeanNamesBatch;
            }
            for (String dependsOnInitializationBeanNames : this.detectDependsOnInitializationBeanNames(beanFactory)) {
                BeanDefinition beanDefinition = DependsOnDatabaseInitializationPostProcessor.getBeanDefinition(dependsOnInitializationBeanNames, beanFactory);
                beanDefinition.setDependsOn(this.merge(beanDefinition.getDependsOn(), initializerBeanNames.beanNames()));
            }
        }

        private String[] merge(String[] source, Set<String> additional) {
            if (CollectionUtils.isEmpty(additional)) {
                return source;
            }
            LinkedHashSet result = new LinkedHashSet(source != null ? Arrays.asList(source) : Collections.emptySet());
            result.addAll(additional);
            return StringUtils.toStringArray(result);
        }

        private InitializerBeanNames detectInitializerBeanNames(ConfigurableListableBeanFactory beanFactory) {
            List<DatabaseInitializerDetector> detectors = this.getDetectors(beanFactory, DatabaseInitializerDetector.class);
            InitializerBeanNames initializerBeanNames = new InitializerBeanNames();
            for (DatabaseInitializerDetector detector : detectors) {
                for (String beanName : detector.detect(beanFactory)) {
                    BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
                    beanDefinition.setAttribute(DatabaseInitializerDetector.class.getName(), (Object)detector.getClass().getName());
                    initializerBeanNames.detected(detector, beanName);
                }
            }
            for (DatabaseInitializerDetector detector : detectors) {
                detector.detectionComplete(beanFactory, initializerBeanNames.beanNames());
            }
            return initializerBeanNames;
        }

        private Collection<String> detectDependsOnInitializationBeanNames(ConfigurableListableBeanFactory beanFactory) {
            List<DependsOnDatabaseInitializationDetector> detectors = this.getDetectors(beanFactory, DependsOnDatabaseInitializationDetector.class);
            HashSet<String> beanNames = new HashSet<String>();
            for (DependsOnDatabaseInitializationDetector detector : detectors) {
                beanNames.addAll(detector.detect(beanFactory));
            }
            return beanNames;
        }

        private <T> List<T> getDetectors(ConfigurableListableBeanFactory beanFactory, Class<T> type) {
            List names = SpringFactoriesLoader.loadFactoryNames(type, (ClassLoader)beanFactory.getBeanClassLoader());
            Instantiator instantiator = new Instantiator(type, availableParameters -> availableParameters.add(Environment.class, this.environment));
            return instantiator.instantiate(beanFactory.getBeanClassLoader(), names);
        }

        private static BeanDefinition getBeanDefinition(String beanName, ConfigurableListableBeanFactory beanFactory) {
            try {
                return beanFactory.getBeanDefinition(beanName);
            }
            catch (NoSuchBeanDefinitionException ex) {
                BeanFactory parentBeanFactory = beanFactory.getParentBeanFactory();
                if (parentBeanFactory instanceof ConfigurableListableBeanFactory) {
                    return DependsOnDatabaseInitializationPostProcessor.getBeanDefinition(beanName, (ConfigurableListableBeanFactory)parentBeanFactory);
                }
                throw ex;
            }
        }

        static class InitializerBeanNames {
            private final Map<DatabaseInitializerDetector, Set<String>> byDetectorBeanNames = new LinkedHashMap<DatabaseInitializerDetector, Set<String>>();
            private final Set<String> beanNames = new LinkedHashSet<String>();

            InitializerBeanNames() {
            }

            private void detected(DatabaseInitializerDetector detector, String beanName) {
                this.byDetectorBeanNames.computeIfAbsent(detector, key -> new LinkedHashSet()).add(beanName);
                this.beanNames.add(beanName);
            }

            private boolean isEmpty() {
                return this.beanNames.isEmpty();
            }

            private Iterable<Set<String>> batchedBeanNames() {
                return this.byDetectorBeanNames.values();
            }

            private Set<String> beanNames() {
                return Collections.unmodifiableSet(this.beanNames);
            }
        }
    }
}

