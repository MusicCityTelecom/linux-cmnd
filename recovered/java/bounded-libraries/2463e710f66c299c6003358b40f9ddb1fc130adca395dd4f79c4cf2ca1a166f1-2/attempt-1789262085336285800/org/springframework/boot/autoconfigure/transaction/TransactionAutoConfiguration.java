/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.transaction.PlatformTransactionManager
 *  org.springframework.transaction.ReactiveTransactionManager
 *  org.springframework.transaction.TransactionManager
 *  org.springframework.transaction.annotation.AbstractTransactionManagementConfiguration
 *  org.springframework.transaction.annotation.EnableTransactionManagement
 *  org.springframework.transaction.reactive.TransactionalOperator
 *  org.springframework.transaction.support.TransactionOperations
 *  org.springframework.transaction.support.TransactionTemplate
 */
package org.springframework.boot.autoconfigure.transaction;

import java.util.stream.Collectors;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.data.neo4j.Neo4jDataAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.PlatformTransactionManagerCustomizer;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.boot.autoconfigure.transaction.TransactionProperties;
import org.springframework.boot.autoconfigure.transaction.jta.JtaAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.AbstractTransactionManagementConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.reactive.TransactionalOperator;
import org.springframework.transaction.support.TransactionOperations;
import org.springframework.transaction.support.TransactionTemplate;

@AutoConfiguration(after={JtaAutoConfiguration.class, HibernateJpaAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, Neo4jDataAutoConfiguration.class})
@ConditionalOnClass(value={PlatformTransactionManager.class})
@EnableConfigurationProperties(value={TransactionProperties.class})
public class TransactionAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public TransactionManagerCustomizers platformTransactionManagerCustomizers(ObjectProvider<PlatformTransactionManagerCustomizer<?>> customizers) {
        return new TransactionManagerCustomizers(customizers.orderedStream().collect(Collectors.toList()));
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnSingleCandidate(value=ReactiveTransactionManager.class)
    public TransactionalOperator transactionalOperator(ReactiveTransactionManager transactionManager) {
        return TransactionalOperator.create((ReactiveTransactionManager)transactionManager);
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnBean(value={TransactionManager.class})
    @ConditionalOnMissingBean(value={AbstractTransactionManagementConfiguration.class})
    public static class EnableTransactionManagementConfiguration {

        @Configuration(proxyBeanMethods=false)
        @EnableTransactionManagement(proxyTargetClass=true)
        @ConditionalOnProperty(prefix="spring.aop", name={"proxy-target-class"}, havingValue="true", matchIfMissing=true)
        public static class CglibAutoProxyConfiguration {
        }

        @Configuration(proxyBeanMethods=false)
        @EnableTransactionManagement(proxyTargetClass=false)
        @ConditionalOnProperty(prefix="spring.aop", name={"proxy-target-class"}, havingValue="false")
        public static class JdkDynamicAutoProxyConfiguration {
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnSingleCandidate(value=PlatformTransactionManager.class)
    public static class TransactionTemplateConfiguration {
        @Bean
        @ConditionalOnMissingBean(value={TransactionOperations.class})
        public TransactionTemplate transactionTemplate(PlatformTransactionManager transactionManager) {
            return new TransactionTemplate(transactionManager);
        }
    }
}

