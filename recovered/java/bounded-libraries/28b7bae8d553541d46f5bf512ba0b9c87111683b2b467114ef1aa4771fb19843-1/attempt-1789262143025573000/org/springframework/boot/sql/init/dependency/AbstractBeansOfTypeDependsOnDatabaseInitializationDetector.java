/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.ListableBeanFactory
 *  org.springframework.beans.factory.config.ConfigurableListableBeanFactory
 */
package org.springframework.boot.sql.init.dependency;

import java.util.Collections;
import java.util.Set;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.sql.init.dependency.BeansOfTypeDetector;
import org.springframework.boot.sql.init.dependency.DependsOnDatabaseInitializationDetector;

public abstract class AbstractBeansOfTypeDependsOnDatabaseInitializationDetector
implements DependsOnDatabaseInitializationDetector {
    @Override
    public Set<String> detect(ConfigurableListableBeanFactory beanFactory) {
        try {
            Set<Class<?>> types = this.getDependsOnDatabaseInitializationBeanTypes();
            return new BeansOfTypeDetector(types).detect((ListableBeanFactory)beanFactory);
        }
        catch (Throwable ex) {
            return Collections.emptySet();
        }
    }

    protected abstract Set<Class<?>> getDependsOnDatabaseInitializationBeanTypes();
}

