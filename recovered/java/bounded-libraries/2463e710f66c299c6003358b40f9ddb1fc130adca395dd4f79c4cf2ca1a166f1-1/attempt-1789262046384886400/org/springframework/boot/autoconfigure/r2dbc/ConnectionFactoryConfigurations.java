/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.r2dbc.pool.ConnectionPool
 *  io.r2dbc.pool.ConnectionPoolConfiguration
 *  io.r2dbc.pool.ConnectionPoolConfiguration$Builder
 *  io.r2dbc.spi.ConnectionFactory
 *  io.r2dbc.spi.ConnectionFactoryOptions$Builder
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.boot.context.properties.PropertyMapper
 *  org.springframework.boot.context.properties.bind.BindResult
 *  org.springframework.boot.context.properties.bind.Bindable
 *  org.springframework.boot.context.properties.bind.Binder
 *  org.springframework.boot.r2dbc.ConnectionFactoryBuilder
 *  org.springframework.boot.r2dbc.EmbeddedDatabaseConnection
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.ConditionContext
 *  org.springframework.context.annotation.Conditional
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.core.env.Environment
 *  org.springframework.core.io.ResourceLoader
 *  org.springframework.core.type.AnnotatedTypeMetadata
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.autoconfigure.r2dbc;

import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.boot.autoconfigure.r2dbc.ConnectionFactoryOptionsBuilderCustomizer;
import org.springframework.boot.autoconfigure.r2dbc.ConnectionFactoryOptionsInitializer;
import org.springframework.boot.autoconfigure.r2dbc.MissingR2dbcPoolDependencyException;
import org.springframework.boot.autoconfigure.r2dbc.MultipleConnectionPoolConfigurationsException;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.r2dbc.ConnectionFactoryBuilder;
import org.springframework.boot.r2dbc.EmbeddedDatabaseConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

abstract class ConnectionFactoryConfigurations {
    ConnectionFactoryConfigurations() {
    }

    protected static ConnectionFactory createConnectionFactory(R2dbcProperties properties, ClassLoader classLoader, List<ConnectionFactoryOptionsBuilderCustomizer> optionsCustomizers) {
        try {
            return ConnectionFactoryBuilder.withOptions((ConnectionFactoryOptions.Builder)new ConnectionFactoryOptionsInitializer().initialize(properties, () -> EmbeddedDatabaseConnection.get((ClassLoader)classLoader))).configure(options -> {
                for (ConnectionFactoryOptionsBuilderCustomizer optionsCustomizer : optionsCustomizers) {
                    optionsCustomizer.customize((ConnectionFactoryOptions.Builder)options);
                }
            }).build();
        }
        catch (IllegalStateException ex) {
            String message = ex.getMessage();
            if (message != null && message.contains("driver=pool") && !ClassUtils.isPresent((String)"io.r2dbc.pool.ConnectionPool", (ClassLoader)classLoader)) {
                throw new MissingR2dbcPoolDependencyException();
            }
            throw ex;
        }
    }

    static class PooledConnectionFactoryCondition
    extends SpringBootCondition {
        PooledConnectionFactoryCondition() {
        }

        @Override
        public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
            BindResult pool = Binder.get((Environment)context.getEnvironment()).bind("spring.r2dbc.pool", Bindable.of(R2dbcProperties.Pool.class));
            if (this.hasPoolUrl(context.getEnvironment())) {
                if (pool.isBound()) {
                    throw new MultipleConnectionPoolConfigurationsException();
                }
                return ConditionOutcome.noMatch("URL-based pooling has been configured");
            }
            if (pool.isBound() && !ClassUtils.isPresent((String)"io.r2dbc.pool.ConnectionPool", (ClassLoader)context.getClassLoader())) {
                throw new MissingR2dbcPoolDependencyException();
            }
            if (((R2dbcProperties.Pool)pool.orElseGet(R2dbcProperties.Pool::new)).isEnabled()) {
                return ConditionOutcome.match("Property-based pooling is enabled");
            }
            return ConditionOutcome.noMatch("Property-based pooling is disabled");
        }

        private boolean hasPoolUrl(Environment environment) {
            String url = environment.getProperty("spring.r2dbc.url");
            return StringUtils.hasText((String)url) && url.contains(":pool:");
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnProperty(prefix="spring.r2dbc.pool", value={"enabled"}, havingValue="false", matchIfMissing=true)
    @ConditionalOnMissingBean(value={ConnectionFactory.class})
    static class GenericConfiguration {
        GenericConfiguration() {
        }

        @Bean
        ConnectionFactory connectionFactory(R2dbcProperties properties, ResourceLoader resourceLoader, ObjectProvider<ConnectionFactoryOptionsBuilderCustomizer> customizers) {
            return ConnectionFactoryConfigurations.createConnectionFactory(properties, resourceLoader.getClassLoader(), customizers.orderedStream().collect(Collectors.toList()));
        }
    }

    @Configuration(proxyBeanMethods=false)
    @Conditional(value={PooledConnectionFactoryCondition.class})
    @ConditionalOnMissingBean(value={ConnectionFactory.class})
    static class PoolConfiguration {
        PoolConfiguration() {
        }

        @Configuration(proxyBeanMethods=false)
        @ConditionalOnClass(value={ConnectionPool.class})
        static class PooledConnectionFactoryConfiguration {
            PooledConnectionFactoryConfiguration() {
            }

            @Bean(destroyMethod="dispose")
            ConnectionPool connectionFactory(R2dbcProperties properties, ResourceLoader resourceLoader, ObjectProvider<ConnectionFactoryOptionsBuilderCustomizer> customizers) {
                ConnectionFactory connectionFactory = ConnectionFactoryConfigurations.createConnectionFactory(properties, resourceLoader.getClassLoader(), customizers.orderedStream().collect(Collectors.toList()));
                R2dbcProperties.Pool pool = properties.getPool();
                PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
                ConnectionPoolConfiguration.Builder builder = ConnectionPoolConfiguration.builder((ConnectionFactory)connectionFactory);
                map.from((Object)pool.getMaxIdleTime()).to(arg_0 -> ((ConnectionPoolConfiguration.Builder)builder).maxIdleTime(arg_0));
                map.from((Object)pool.getMaxLifeTime()).to(arg_0 -> ((ConnectionPoolConfiguration.Builder)builder).maxLifeTime(arg_0));
                map.from((Object)pool.getMaxAcquireTime()).to(arg_0 -> ((ConnectionPoolConfiguration.Builder)builder).maxAcquireTime(arg_0));
                map.from((Object)pool.getMaxCreateConnectionTime()).to(arg_0 -> ((ConnectionPoolConfiguration.Builder)builder).maxCreateConnectionTime(arg_0));
                map.from((Object)pool.getInitialSize()).to(arg_0 -> ((ConnectionPoolConfiguration.Builder)builder).initialSize(arg_0));
                map.from((Object)pool.getMaxSize()).to(arg_0 -> ((ConnectionPoolConfiguration.Builder)builder).maxSize(arg_0));
                map.from((Object)pool.getValidationQuery()).whenHasText().to(arg_0 -> ((ConnectionPoolConfiguration.Builder)builder).validationQuery(arg_0));
                map.from((Object)pool.getValidationDepth()).to(arg_0 -> ((ConnectionPoolConfiguration.Builder)builder).validationDepth(arg_0));
                return new ConnectionPool(builder.build());
            }
        }
    }
}

