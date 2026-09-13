/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.data.ldap.repository.config.EnableLdapRepositories
 *  org.springframework.data.ldap.repository.config.LdapRepositoryConfigurationExtension
 *  org.springframework.data.repository.config.RepositoryConfigurationExtension
 */
package org.springframework.boot.autoconfigure.data.ldap;

import java.lang.annotation.Annotation;
import org.springframework.boot.autoconfigure.data.AbstractRepositoryConfigurationSourceSupport;
import org.springframework.data.ldap.repository.config.EnableLdapRepositories;
import org.springframework.data.ldap.repository.config.LdapRepositoryConfigurationExtension;
import org.springframework.data.repository.config.RepositoryConfigurationExtension;

class LdapRepositoriesRegistrar
extends AbstractRepositoryConfigurationSourceSupport {
    LdapRepositoriesRegistrar() {
    }

    @Override
    protected Class<? extends Annotation> getAnnotation() {
        return EnableLdapRepositories.class;
    }

    @Override
    protected Class<?> getConfiguration() {
        return EnableLdapRepositoriesConfiguration.class;
    }

    @Override
    protected RepositoryConfigurationExtension getRepositoryConfigurationExtension() {
        return new LdapRepositoryConfigurationExtension();
    }

    @EnableLdapRepositories
    private static class EnableLdapRepositoriesConfiguration {
        private EnableLdapRepositoriesConfiguration() {
        }
    }
}

