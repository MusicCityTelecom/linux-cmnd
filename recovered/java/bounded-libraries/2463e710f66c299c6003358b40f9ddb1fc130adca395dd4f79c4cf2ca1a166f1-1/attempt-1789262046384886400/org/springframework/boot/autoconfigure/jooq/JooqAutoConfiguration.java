/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jooq.Configuration
 *  org.jooq.ConnectionProvider
 *  org.jooq.DSLContext
 *  org.jooq.ExecuteListener
 *  org.jooq.ExecuteListenerProvider
 *  org.jooq.ExecutorProvider
 *  org.jooq.RecordListenerProvider
 *  org.jooq.RecordMapperProvider
 *  org.jooq.RecordUnmapperProvider
 *  org.jooq.TransactionListenerProvider
 *  org.jooq.TransactionProvider
 *  org.jooq.VisitListenerProvider
 *  org.jooq.conf.Settings
 *  org.jooq.impl.DataSourceConnectionProvider
 *  org.jooq.impl.DefaultConfiguration
 *  org.jooq.impl.DefaultDSLContext
 *  org.jooq.impl.DefaultExecuteListenerProvider
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.core.Ordered
 *  org.springframework.core.annotation.Order
 *  org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy
 *  org.springframework.transaction.PlatformTransactionManager
 */
package org.springframework.boot.autoconfigure.jooq;

import javax.sql.DataSource;
import org.jooq.Configuration;
import org.jooq.ConnectionProvider;
import org.jooq.DSLContext;
import org.jooq.ExecuteListener;
import org.jooq.ExecuteListenerProvider;
import org.jooq.ExecutorProvider;
import org.jooq.RecordListenerProvider;
import org.jooq.RecordMapperProvider;
import org.jooq.RecordUnmapperProvider;
import org.jooq.TransactionListenerProvider;
import org.jooq.TransactionProvider;
import org.jooq.VisitListenerProvider;
import org.jooq.conf.Settings;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jooq.DefaultConfigurationCustomizer;
import org.springframework.boot.autoconfigure.jooq.JooqExceptionTranslator;
import org.springframework.boot.autoconfigure.jooq.JooqProperties;
import org.springframework.boot.autoconfigure.jooq.SpringTransactionProvider;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.transaction.PlatformTransactionManager;

@AutoConfiguration(after={DataSourceAutoConfiguration.class, TransactionAutoConfiguration.class})
@ConditionalOnClass(value={DSLContext.class})
@ConditionalOnBean(value={DataSource.class})
public class JooqAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(value={ConnectionProvider.class})
    public DataSourceConnectionProvider dataSourceConnectionProvider(DataSource dataSource) {
        return new DataSourceConnectionProvider((DataSource)new TransactionAwareDataSourceProxy(dataSource));
    }

    @Bean
    @ConditionalOnBean(value={PlatformTransactionManager.class})
    public SpringTransactionProvider transactionProvider(PlatformTransactionManager txManager) {
        return new SpringTransactionProvider(txManager);
    }

    @Bean
    @Order(value=0)
    public DefaultExecuteListenerProvider jooqExceptionTranslatorExecuteListenerProvider() {
        return new DefaultExecuteListenerProvider((ExecuteListener)new JooqExceptionTranslator());
    }

    private static class OrderedDefaultConfigurationCustomizer
    implements DefaultConfigurationCustomizer,
    Ordered {
        private final DefaultConfigurationCustomizer delegate;

        OrderedDefaultConfigurationCustomizer(DefaultConfigurationCustomizer delegate) {
            this.delegate = delegate;
        }

        @Override
        public void customize(DefaultConfiguration configuration) {
            this.delegate.customize(configuration);
        }

        public int getOrder() {
            return 0;
        }
    }

    @org.springframework.context.annotation.Configuration(proxyBeanMethods=false)
    @ConditionalOnMissingBean(value={DSLContext.class})
    @EnableConfigurationProperties(value={JooqProperties.class})
    public static class DslContextConfiguration {
        @Bean
        public DefaultDSLContext dslContext(Configuration configuration) {
            return new DefaultDSLContext(configuration);
        }

        @Bean
        @ConditionalOnMissingBean(value={Configuration.class})
        public DefaultConfiguration jooqConfiguration(JooqProperties properties, ConnectionProvider connectionProvider, DataSource dataSource, ObjectProvider<ExecuteListenerProvider> executeListenerProviders, ObjectProvider<DefaultConfigurationCustomizer> configurationCustomizers) {
            DefaultConfiguration configuration = new DefaultConfiguration();
            configuration.set(properties.determineSqlDialect(dataSource));
            configuration.set(connectionProvider);
            configuration.set((ExecuteListenerProvider[])executeListenerProviders.orderedStream().toArray(ExecuteListenerProvider[]::new));
            configurationCustomizers.orderedStream().forEach(customizer -> customizer.customize(configuration));
            return configuration;
        }

        @Bean
        @Deprecated
        public DefaultConfigurationCustomizer jooqProvidersDefaultConfigurationCustomizer(ObjectProvider<TransactionProvider> transactionProvider, ObjectProvider<RecordMapperProvider> recordMapperProvider, ObjectProvider<RecordUnmapperProvider> recordUnmapperProvider, ObjectProvider<Settings> settings, ObjectProvider<RecordListenerProvider> recordListenerProviders, ObjectProvider<VisitListenerProvider> visitListenerProviders, ObjectProvider<TransactionListenerProvider> transactionListenerProviders, ObjectProvider<ExecutorProvider> executorProvider) {
            return new OrderedDefaultConfigurationCustomizer(configuration -> {
                transactionProvider.ifAvailable(arg_0 -> ((DefaultConfiguration)configuration).set(arg_0));
                recordMapperProvider.ifAvailable(arg_0 -> ((DefaultConfiguration)configuration).set(arg_0));
                recordUnmapperProvider.ifAvailable(arg_0 -> ((DefaultConfiguration)configuration).set(arg_0));
                settings.ifAvailable(arg_0 -> ((DefaultConfiguration)configuration).set(arg_0));
                executorProvider.ifAvailable(arg_0 -> ((DefaultConfiguration)configuration).set(arg_0));
                configuration.set((RecordListenerProvider[])recordListenerProviders.orderedStream().toArray(RecordListenerProvider[]::new));
                configuration.set((VisitListenerProvider[])visitListenerProviders.orderedStream().toArray(VisitListenerProvider[]::new));
                configuration.setTransactionListenerProvider((TransactionListenerProvider[])transactionListenerProviders.orderedStream().toArray(TransactionListenerProvider[]::new));
            });
        }
    }
}

