/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.env.Environment
 *  org.springframework.data.jpa.repository.config.EnableJpaRepositories
 *  org.springframework.data.jpa.repository.config.JpaRepositoryConfigExtension
 *  org.springframework.data.repository.config.BootstrapMode
 *  org.springframework.data.repository.config.RepositoryConfigurationExtension
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.autoconfigure.data.jpa;

import java.lang.annotation.Annotation;
import java.util.Locale;
import org.springframework.boot.autoconfigure.data.AbstractRepositoryConfigurationSourceSupport;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.jpa.repository.config.JpaRepositoryConfigExtension;
import org.springframework.data.repository.config.BootstrapMode;
import org.springframework.data.repository.config.RepositoryConfigurationExtension;
import org.springframework.util.StringUtils;

class JpaRepositoriesRegistrar
extends AbstractRepositoryConfigurationSourceSupport {
    private BootstrapMode bootstrapMode = null;

    JpaRepositoriesRegistrar() {
    }

    @Override
    protected Class<? extends Annotation> getAnnotation() {
        return EnableJpaRepositories.class;
    }

    @Override
    protected Class<?> getConfiguration() {
        return EnableJpaRepositoriesConfiguration.class;
    }

    @Override
    protected RepositoryConfigurationExtension getRepositoryConfigurationExtension() {
        return new JpaRepositoryConfigExtension();
    }

    @Override
    protected BootstrapMode getBootstrapMode() {
        return this.bootstrapMode == null ? BootstrapMode.DEFAULT : this.bootstrapMode;
    }

    @Override
    public void setEnvironment(Environment environment) {
        super.setEnvironment(environment);
        this.configureBootstrapMode(environment);
    }

    private void configureBootstrapMode(Environment environment) {
        String property = environment.getProperty("spring.data.jpa.repositories.bootstrap-mode");
        if (StringUtils.hasText((String)property)) {
            this.bootstrapMode = BootstrapMode.valueOf((String)property.toUpperCase(Locale.ENGLISH));
        }
    }

    @EnableJpaRepositories
    private static class EnableJpaRepositoriesConfiguration {
        private EnableJpaRepositoriesConfiguration() {
        }
    }
}

