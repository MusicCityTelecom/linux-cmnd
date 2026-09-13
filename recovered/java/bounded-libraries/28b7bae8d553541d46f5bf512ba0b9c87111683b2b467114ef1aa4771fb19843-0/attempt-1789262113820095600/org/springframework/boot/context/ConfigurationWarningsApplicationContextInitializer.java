/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.annotation.AnnotatedBeanDefinition
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.config.BeanFactoryPostProcessor
 *  org.springframework.beans.factory.config.ConfigurableListableBeanFactory
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor
 *  org.springframework.context.ApplicationContextInitializer
 *  org.springframework.context.ConfigurableApplicationContext
 *  org.springframework.context.annotation.ComponentScan
 *  org.springframework.core.PriorityOrdered
 *  org.springframework.core.annotation.AnnotationAttributes
 *  org.springframework.core.type.AnnotationMetadata
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

public class ConfigurationWarningsApplicationContextInitializer
implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    private static final Log logger = LogFactory.getLog(ConfigurationWarningsApplicationContextInitializer.class);

    public void initialize(ConfigurableApplicationContext context) {
        context.addBeanFactoryPostProcessor((BeanFactoryPostProcessor)new ConfigurationWarningsPostProcessor(this.getChecks()));
    }

    protected Check[] getChecks() {
        return new Check[]{new ComponentScanPackageCheck()};
    }

    protected static class ComponentScanPackageCheck
    implements Check {
        private static final Set<String> PROBLEM_PACKAGES;

        protected ComponentScanPackageCheck() {
        }

        @Override
        public String getWarning(BeanDefinitionRegistry registry) {
            Set<String> scannedPackages = this.getComponentScanningPackages(registry);
            List<String> problematicPackages = this.getProblematicPackages(scannedPackages);
            if (problematicPackages.isEmpty()) {
                return null;
            }
            return "Your ApplicationContext is unlikely to start due to a @ComponentScan of " + StringUtils.collectionToDelimitedString(problematicPackages, (String)", ") + ".";
        }

        protected Set<String> getComponentScanningPackages(BeanDefinitionRegistry registry) {
            String[] names;
            LinkedHashSet<String> packages = new LinkedHashSet<String>();
            for (String name : names = registry.getBeanDefinitionNames()) {
                BeanDefinition definition = registry.getBeanDefinition(name);
                if (!(definition instanceof AnnotatedBeanDefinition)) continue;
                AnnotatedBeanDefinition annotatedDefinition = (AnnotatedBeanDefinition)definition;
                this.addComponentScanningPackages(packages, annotatedDefinition.getMetadata());
            }
            return packages;
        }

        private void addComponentScanningPackages(Set<String> packages, AnnotationMetadata metadata) {
            AnnotationAttributes attributes = AnnotationAttributes.fromMap((Map)metadata.getAnnotationAttributes(ComponentScan.class.getName(), true));
            if (attributes != null) {
                this.addPackages(packages, attributes.getStringArray("value"));
                this.addPackages(packages, attributes.getStringArray("basePackages"));
                this.addClasses(packages, attributes.getStringArray("basePackageClasses"));
                if (packages.isEmpty()) {
                    packages.add(ClassUtils.getPackageName((String)metadata.getClassName()));
                }
            }
        }

        private void addPackages(Set<String> packages, String[] values) {
            if (values != null) {
                Collections.addAll(packages, values);
            }
        }

        private void addClasses(Set<String> packages, String[] values) {
            if (values != null) {
                for (String value : values) {
                    packages.add(ClassUtils.getPackageName((String)value));
                }
            }
        }

        private List<String> getProblematicPackages(Set<String> scannedPackages) {
            ArrayList<String> problematicPackages = new ArrayList<String>();
            for (String scannedPackage : scannedPackages) {
                if (!this.isProblematicPackage(scannedPackage)) continue;
                problematicPackages.add(this.getDisplayName(scannedPackage));
            }
            return problematicPackages;
        }

        private boolean isProblematicPackage(String scannedPackage) {
            if (scannedPackage == null || scannedPackage.isEmpty()) {
                return true;
            }
            return PROBLEM_PACKAGES.contains(scannedPackage);
        }

        private String getDisplayName(String scannedPackage) {
            if (scannedPackage == null || scannedPackage.isEmpty()) {
                return "the default package";
            }
            return "'" + scannedPackage + "'";
        }

        static {
            HashSet<String> packages = new HashSet<String>();
            packages.add("org.springframework");
            packages.add("org");
            PROBLEM_PACKAGES = Collections.unmodifiableSet(packages);
        }
    }

    @FunctionalInterface
    protected static interface Check {
        public String getWarning(BeanDefinitionRegistry var1);
    }

    protected static final class ConfigurationWarningsPostProcessor
    implements PriorityOrdered,
    BeanDefinitionRegistryPostProcessor {
        private Check[] checks;

        public ConfigurationWarningsPostProcessor(Check[] checks) {
            this.checks = checks;
        }

        public int getOrder() {
            return 0x7FFFFFFE;
        }

        public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        }

        public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
            for (Check check : this.checks) {
                String message = check.getWarning(registry);
                if (!StringUtils.hasLength((String)message)) continue;
                this.warn(message);
            }
        }

        private void warn(String message) {
            if (logger.isWarnEnabled()) {
                logger.warn((Object)String.format("%n%n** WARNING ** : %s%n%n", message));
            }
        }
    }
}

