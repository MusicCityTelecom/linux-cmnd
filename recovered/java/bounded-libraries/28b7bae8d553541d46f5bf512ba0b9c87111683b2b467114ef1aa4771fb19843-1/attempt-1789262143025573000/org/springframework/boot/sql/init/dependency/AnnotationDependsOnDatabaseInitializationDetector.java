/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.config.ConfigurableListableBeanFactory
 */
package org.springframework.boot.sql.init.dependency;

import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.sql.init.dependency.DependsOnDatabaseInitialization;
import org.springframework.boot.sql.init.dependency.DependsOnDatabaseInitializationDetector;

class AnnotationDependsOnDatabaseInitializationDetector
implements DependsOnDatabaseInitializationDetector {
    AnnotationDependsOnDatabaseInitializationDetector() {
    }

    @Override
    public Set<String> detect(ConfigurableListableBeanFactory beanFactory) {
        HashSet<String> dependentBeans = new HashSet<String>();
        for (String beanName : beanFactory.getBeanDefinitionNames()) {
            if (beanFactory.findAnnotationOnBean(beanName, DependsOnDatabaseInitialization.class, false) == null) continue;
            dependentBeans.add(beanName);
        }
        return dependentBeans;
    }
}

