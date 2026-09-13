/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
 *  org.springframework.data.r2dbc.repository.config.R2dbcRepositoryConfigurationExtension
 *  org.springframework.data.repository.config.RepositoryConfigurationExtension
 */
package org.springframework.boot.autoconfigure.data.r2dbc;

import java.lang.annotation.Annotation;
import org.springframework.boot.autoconfigure.data.AbstractRepositoryConfigurationSourceSupport;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.data.r2dbc.repository.config.R2dbcRepositoryConfigurationExtension;
import org.springframework.data.repository.config.RepositoryConfigurationExtension;

class R2dbcRepositoriesAutoConfigureRegistrar
extends AbstractRepositoryConfigurationSourceSupport {
    R2dbcRepositoriesAutoConfigureRegistrar() {
    }

    @Override
    protected Class<? extends Annotation> getAnnotation() {
        return EnableR2dbcRepositories.class;
    }

    @Override
    protected Class<?> getConfiguration() {
        return EnableR2dbcRepositoriesConfiguration.class;
    }

    @Override
    protected RepositoryConfigurationExtension getRepositoryConfigurationExtension() {
        return new R2dbcRepositoryConfigurationExtension();
    }

    @EnableR2dbcRepositories
    private static class EnableR2dbcRepositoriesConfiguration {
        private EnableR2dbcRepositoriesConfiguration() {
        }
    }
}

