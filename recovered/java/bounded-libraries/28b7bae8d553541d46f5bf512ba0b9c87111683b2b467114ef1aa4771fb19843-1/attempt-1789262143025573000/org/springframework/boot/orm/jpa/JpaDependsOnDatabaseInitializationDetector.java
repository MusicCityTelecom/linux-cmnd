/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.EntityManagerFactory
 *  org.springframework.core.env.Environment
 *  org.springframework.orm.jpa.AbstractEntityManagerFactoryBean
 */
package org.springframework.boot.orm.jpa;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.EntityManagerFactory;
import org.springframework.boot.sql.init.dependency.AbstractBeansOfTypeDependsOnDatabaseInitializationDetector;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.AbstractEntityManagerFactoryBean;

class JpaDependsOnDatabaseInitializationDetector
extends AbstractBeansOfTypeDependsOnDatabaseInitializationDetector {
    private final Environment environment;

    JpaDependsOnDatabaseInitializationDetector(Environment environment) {
        this.environment = environment;
    }

    @Override
    protected Set<Class<?>> getDependsOnDatabaseInitializationBeanTypes() {
        boolean postpone = (Boolean)this.environment.getProperty("spring.jpa.defer-datasource-initialization", Boolean.TYPE, (Object)false);
        return postpone ? Collections.emptySet() : new HashSet<Class>(Arrays.asList(EntityManagerFactory.class, AbstractEntityManagerFactoryBean.class));
    }
}

