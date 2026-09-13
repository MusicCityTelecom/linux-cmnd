/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean
 *  org.springframework.data.jpa.repository.config.EnableJpaRepositories
 */
package org.springframework.boot.autoconfigure.data.jpa;

import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesRegistrar;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

class EnversRevisionRepositoriesRegistrar
extends JpaRepositoriesRegistrar {
    EnversRevisionRepositoriesRegistrar() {
    }

    @Override
    protected Class<?> getConfiguration() {
        return EnableJpaRepositoriesConfiguration.class;
    }

    @EnableJpaRepositories(repositoryFactoryBeanClass=EnversRevisionRepositoryFactoryBean.class)
    private static class EnableJpaRepositoriesConfiguration {
        private EnableJpaRepositoriesConfiguration() {
        }
    }
}

