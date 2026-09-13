/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.context.annotation.Import
 *  org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration
 *  org.springframework.data.jdbc.repository.config.JdbcRepositoryConfigExtension
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
 *  org.springframework.transaction.PlatformTransactionManager
 */
package org.springframework.boot.autoconfigure.data.jdbc;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.jdbc.JdbcRepositoriesRegistrar;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.JdbcRepositoryConfigExtension;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.transaction.PlatformTransactionManager;

@AutoConfiguration(after={JdbcTemplateAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@ConditionalOnBean(value={NamedParameterJdbcOperations.class, PlatformTransactionManager.class})
@ConditionalOnClass(value={NamedParameterJdbcOperations.class, AbstractJdbcConfiguration.class})
@ConditionalOnProperty(prefix="spring.data.jdbc.repositories", name={"enabled"}, havingValue="true", matchIfMissing=true)
public class JdbcRepositoriesAutoConfiguration {

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnMissingBean(value={AbstractJdbcConfiguration.class})
    static class SpringBootJdbcConfiguration
    extends AbstractJdbcConfiguration {
        SpringBootJdbcConfiguration() {
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnMissingBean(value={JdbcRepositoryConfigExtension.class})
    @Import(value={JdbcRepositoriesRegistrar.class})
    static class JdbcRepositoriesConfiguration {
        JdbcRepositoriesConfiguration() {
        }
    }
}

