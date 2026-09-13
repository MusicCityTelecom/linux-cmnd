/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.EntityManagerFactory
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.config.ConfigurableListableBeanFactory
 *  org.springframework.core.env.Environment
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.orm.jpa;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.persistence.EntityManagerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.sql.init.dependency.AbstractBeansOfTypeDatabaseInitializerDetector;
import org.springframework.boot.sql.init.dependency.DatabaseInitializerDetector;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

class JpaDatabaseInitializerDetector
extends AbstractBeansOfTypeDatabaseInitializerDetector {
    private final Environment environment;

    JpaDatabaseInitializerDetector(Environment environment) {
        this.environment = environment;
    }

    @Override
    protected Set<Class<?>> getDatabaseInitializerBeanTypes() {
        boolean deferred = (Boolean)this.environment.getProperty("spring.jpa.defer-datasource-initialization", Boolean.TYPE, (Object)false);
        return deferred ? Collections.singleton(EntityManagerFactory.class) : Collections.emptySet();
    }

    @Override
    public void detectionComplete(ConfigurableListableBeanFactory beanFactory, Set<String> dataSourceInitializerNames) {
        this.configureOtherInitializersToDependOnJpaInitializers(beanFactory, dataSourceInitializerNames);
    }

    private void configureOtherInitializersToDependOnJpaInitializers(ConfigurableListableBeanFactory beanFactory, Set<String> dataSourceInitializerNames) {
        HashSet<String> jpaInitializers = new HashSet<String>();
        HashSet<String> otherInitializers = new HashSet<String>(dataSourceInitializerNames);
        Iterator iterator = otherInitializers.iterator();
        while (iterator.hasNext()) {
            String initializerName = (String)iterator.next();
            BeanDefinition initializerDefinition = beanFactory.getBeanDefinition(initializerName);
            if (!JpaDatabaseInitializerDetector.class.getName().equals(initializerDefinition.getAttribute(DatabaseInitializerDetector.class.getName()))) continue;
            iterator.remove();
            jpaInitializers.add(initializerName);
        }
        for (String otherInitializerName : otherInitializers) {
            BeanDefinition definition = beanFactory.getBeanDefinition(otherInitializerName);
            String[] dependencies = definition.getDependsOn();
            for (String dependencyName : jpaInitializers) {
                dependencies = StringUtils.addStringToArray((String[])dependencies, (String)dependencyName);
            }
            definition.setDependsOn(dependencies);
        }
    }
}

