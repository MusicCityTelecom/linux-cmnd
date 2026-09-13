/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.EntityManagerFactory
 *  org.springframework.beans.factory.FactoryBean
 *  org.springframework.orm.jpa.AbstractEntityManagerFactoryBean
 */
package org.springframework.boot.autoconfigure.orm.jpa;

import javax.persistence.EntityManagerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.boot.autoconfigure.AbstractDependsOnBeanFactoryPostProcessor;
import org.springframework.orm.jpa.AbstractEntityManagerFactoryBean;

public class EntityManagerFactoryDependsOnPostProcessor
extends AbstractDependsOnBeanFactoryPostProcessor {
    public EntityManagerFactoryDependsOnPostProcessor(String ... dependsOn) {
        super(EntityManagerFactory.class, AbstractEntityManagerFactoryBean.class, dependsOn);
    }

    public EntityManagerFactoryDependsOnPostProcessor(Class<?> ... dependsOn) {
        super((Class<?>)EntityManagerFactory.class, (Class<? extends FactoryBean<?>>)AbstractEntityManagerFactoryBean.class, dependsOn);
    }
}

