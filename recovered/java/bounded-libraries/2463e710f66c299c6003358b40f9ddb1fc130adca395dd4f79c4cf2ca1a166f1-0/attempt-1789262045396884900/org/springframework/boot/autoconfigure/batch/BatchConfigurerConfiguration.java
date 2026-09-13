/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.EntityManagerFactory
 *  org.springframework.batch.core.configuration.annotation.BatchConfigurer
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.transaction.PlatformTransactionManager
 */
package org.springframework.boot.autoconfigure.batch;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.batch.BasicBatchConfigurer;
import org.springframework.boot.autoconfigure.batch.BatchDataSource;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.boot.autoconfigure.batch.JpaBatchConfigurer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@ConditionalOnClass(value={PlatformTransactionManager.class})
@ConditionalOnBean(value={DataSource.class})
@ConditionalOnMissingBean(value={BatchConfigurer.class})
@Configuration(proxyBeanMethods=false)
class BatchConfigurerConfiguration {
    BatchConfigurerConfiguration() {
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={EntityManagerFactory.class})
    @ConditionalOnBean(name={"entityManagerFactory"})
    static class JpaBatchConfiguration {
        JpaBatchConfiguration() {
        }

        @Bean
        JpaBatchConfigurer batchConfigurer(BatchProperties properties, DataSource dataSource, @BatchDataSource ObjectProvider<DataSource> batchDataSource, ObjectProvider<TransactionManagerCustomizers> transactionManagerCustomizers, EntityManagerFactory entityManagerFactory) {
            return new JpaBatchConfigurer(properties, (DataSource)batchDataSource.getIfAvailable(() -> dataSource), (TransactionManagerCustomizers)transactionManagerCustomizers.getIfAvailable(), entityManagerFactory);
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnMissingBean(name={"entityManagerFactory"})
    static class JdbcBatchConfiguration {
        JdbcBatchConfiguration() {
        }

        @Bean
        BasicBatchConfigurer batchConfigurer(BatchProperties properties, DataSource dataSource, @BatchDataSource ObjectProvider<DataSource> batchDataSource, ObjectProvider<TransactionManagerCustomizers> transactionManagerCustomizers) {
            return new BasicBatchConfigurer(properties, (DataSource)batchDataSource.getIfAvailable(() -> dataSource), (TransactionManagerCustomizers)transactionManagerCustomizers.getIfAvailable());
        }
    }
}

