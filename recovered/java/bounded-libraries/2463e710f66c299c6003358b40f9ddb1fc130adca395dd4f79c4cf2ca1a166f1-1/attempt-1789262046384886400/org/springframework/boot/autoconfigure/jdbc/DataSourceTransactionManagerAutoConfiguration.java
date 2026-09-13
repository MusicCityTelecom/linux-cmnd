/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.core.env.Environment
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.datasource.DataSourceTransactionManager
 *  org.springframework.jdbc.support.JdbcTransactionManager
 *  org.springframework.transaction.PlatformTransactionManager
 *  org.springframework.transaction.TransactionManager
 */
package org.springframework.boot.autoconfigure.jdbc;

import javax.sql.DataSource;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;

@AutoConfiguration
@ConditionalOnClass(value={JdbcTemplate.class, TransactionManager.class})
@AutoConfigureOrder(value=0x7FFFFFFF)
@EnableConfigurationProperties(value={DataSourceProperties.class})
public class DataSourceTransactionManagerAutoConfiguration {

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnSingleCandidate(value=DataSource.class)
    static class JdbcTransactionManagerConfiguration {
        JdbcTransactionManagerConfiguration() {
        }

        @Bean
        @ConditionalOnMissingBean(value={TransactionManager.class})
        DataSourceTransactionManager transactionManager(Environment environment, DataSource dataSource, ObjectProvider<TransactionManagerCustomizers> transactionManagerCustomizers) {
            DataSourceTransactionManager transactionManager = this.createTransactionManager(environment, dataSource);
            transactionManagerCustomizers.ifAvailable(customizers -> customizers.customize((PlatformTransactionManager)transactionManager));
            return transactionManager;
        }

        private DataSourceTransactionManager createTransactionManager(Environment environment, DataSource dataSource) {
            return (Boolean)environment.getProperty("spring.dao.exceptiontranslation.enabled", Boolean.class, (Object)Boolean.TRUE) != false ? new JdbcTransactionManager(dataSource) : new DataSourceTransactionManager(dataSource);
        }
    }
}

