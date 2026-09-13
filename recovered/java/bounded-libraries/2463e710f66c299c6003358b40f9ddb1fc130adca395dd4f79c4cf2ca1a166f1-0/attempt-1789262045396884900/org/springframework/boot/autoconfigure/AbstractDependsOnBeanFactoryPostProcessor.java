/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryUtils
 *  org.springframework.beans.factory.FactoryBean
 *  org.springframework.beans.factory.ListableBeanFactory
 *  org.springframework.beans.factory.NoSuchBeanDefinitionException
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.config.BeanFactoryPostProcessor
 *  org.springframework.beans.factory.config.ConfigurableListableBeanFactory
 *  org.springframework.core.Ordered
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.autoconfigure;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.Ordered;
import org.springframework.util.StringUtils;

public abstract class AbstractDependsOnBeanFactoryPostProcessor
implements BeanFactoryPostProcessor,
Ordered {
    private final Class<?> beanClass;
    private final Class<? extends FactoryBean<?>> factoryBeanClass;
    private final Function<ListableBeanFactory, Set<String>> dependsOn;

    protected AbstractDependsOnBeanFactoryPostProcessor(Class<?> beanClass, Class<? extends FactoryBean<?>> factoryBeanClass, String ... dependsOn) {
        this.beanClass = beanClass;
        this.factoryBeanClass = factoryBeanClass;
        this.dependsOn = beanFactory -> new HashSet<String>(Arrays.asList(dependsOn));
    }

    protected AbstractDependsOnBeanFactoryPostProcessor(Class<?> beanClass, Class<? extends FactoryBean<?>> factoryBeanClass, Class<?> ... dependencyTypes) {
        this.beanClass = beanClass;
        this.factoryBeanClass = factoryBeanClass;
        this.dependsOn = beanFactory -> Arrays.stream(dependencyTypes).flatMap(dependencyType -> AbstractDependsOnBeanFactoryPostProcessor.getBeanNames(beanFactory, dependencyType).stream()).collect(Collectors.toSet());
    }

    protected AbstractDependsOnBeanFactoryPostProcessor(Class<?> beanClass, String ... dependsOn) {
        this(beanClass, (Class<? extends FactoryBean<?>>)null, dependsOn);
    }

    protected AbstractDependsOnBeanFactoryPostProcessor(Class<?> beanClass, Class<?> ... dependencyTypes) {
        this(beanClass, (Class<? extends FactoryBean<?>>)null, dependencyTypes);
    }

    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        for (String beanName : this.getBeanNames((ListableBeanFactory)beanFactory)) {
            BeanDefinition definition = AbstractDependsOnBeanFactoryPostProcessor.getBeanDefinition(beanName, beanFactory);
            String[] dependencies = definition.getDependsOn();
            for (String dependencyName : this.dependsOn.apply((ListableBeanFactory)beanFactory)) {
                dependencies = StringUtils.addStringToArray((String[])dependencies, (String)dependencyName);
            }
            definition.setDependsOn(dependencies);
        }
    }

    public int getOrder() {
        return 0;
    }

    private Set<String> getBeanNames(ListableBeanFactory beanFactory) {
        Set<String> names = AbstractDependsOnBeanFactoryPostProcessor.getBeanNames(beanFactory, this.beanClass);
        if (this.factoryBeanClass != null) {
            names.addAll(AbstractDependsOnBeanFactoryPostProcessor.getBeanNames(beanFactory, this.factoryBeanClass));
        }
        return names;
    }

    private static Set<String> getBeanNames(ListableBeanFactory beanFactory, Class<?> beanClass) {
        String[] names = BeanFactoryUtils.beanNamesForTypeIncludingAncestors((ListableBeanFactory)beanFactory, beanClass, (boolean)true, (boolean)false);
        return Arrays.stream(names).map(BeanFactoryUtils::transformedBeanName).collect(Collectors.toSet());
    }

    private static BeanDefinition getBeanDefinition(String beanName, ConfigurableListableBeanFactory beanFactory) {
        try {
            return beanFactory.getBeanDefinition(beanName);
        }
        catch (NoSuchBeanDefinitionException ex) {
            BeanFactory parentBeanFactory = beanFactory.getParentBeanFactory();
            if (parentBeanFactory instanceof ConfigurableListableBeanFactory) {
                return AbstractDependsOnBeanFactoryPostProcessor.getBeanDefinition(beanName, (ConfigurableListableBeanFactory)parentBeanFactory);
            }
            throw ex;
        }
    }
}

