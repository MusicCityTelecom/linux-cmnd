/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.r2dbc.pool.ConnectionPool
 *  io.r2dbc.pool.ConnectionPoolConfiguration
 *  io.r2dbc.pool.ConnectionPoolConfiguration$Builder
 *  io.r2dbc.pool.PoolingConnectionFactoryProvider
 *  io.r2dbc.spi.ConnectionFactories
 *  io.r2dbc.spi.ConnectionFactory
 *  io.r2dbc.spi.ConnectionFactoryOptions
 *  io.r2dbc.spi.ConnectionFactoryOptions$Builder
 *  io.r2dbc.spi.ValidationDepth
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 */
package org.springframework.boot.r2dbc;

import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.pool.PoolingConnectionFactoryProvider;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import io.r2dbc.spi.ValidationDepth;
import java.time.Duration;
import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.Function;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.boot.r2dbc.OptionsCapableConnectionFactory;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

public final class ConnectionFactoryBuilder {
    private static final OptionsCapableWrapper optionsCapableWrapper = ClassUtils.isPresent((String)"io.r2dbc.pool.ConnectionPool", (ClassLoader)ConnectionFactoryBuilder.class.getClassLoader()) ? new PoolingAwareOptionsCapableWrapper() : new OptionsCapableWrapper();
    private static final String COLON = ":";
    private final ConnectionFactoryOptions.Builder optionsBuilder;

    private ConnectionFactoryBuilder(ConnectionFactoryOptions.Builder optionsBuilder) {
        this.optionsBuilder = optionsBuilder;
    }

    public static ConnectionFactoryBuilder withUrl(String url) {
        Assert.hasText((String)url, () -> "Url must not be null");
        return ConnectionFactoryBuilder.withOptions(ConnectionFactoryOptions.parse((CharSequence)url).mutate());
    }

    public static ConnectionFactoryBuilder withOptions(ConnectionFactoryOptions.Builder options) {
        return new ConnectionFactoryBuilder(options);
    }

    public static ConnectionFactoryBuilder derivedFrom(ConnectionFactory connectionFactory) {
        ConnectionFactoryOptions options = ConnectionFactoryBuilder.extractOptionsIfPossible(connectionFactory);
        if (options == null) {
            throw new IllegalArgumentException("ConnectionFactoryOptions could not be extracted from " + connectionFactory);
        }
        return ConnectionFactoryBuilder.withOptions(options.mutate());
    }

    private static ConnectionFactoryOptions extractOptionsIfPossible(ConnectionFactory connectionFactory) {
        OptionsCapableConnectionFactory optionsCapable = OptionsCapableConnectionFactory.unwrapFrom(connectionFactory);
        if (optionsCapable != null) {
            return optionsCapable.getOptions();
        }
        return null;
    }

    public ConnectionFactoryBuilder configure(Consumer<ConnectionFactoryOptions.Builder> options) {
        options.accept(this.optionsBuilder);
        return this;
    }

    public ConnectionFactoryBuilder username(String username) {
        return this.configure(options -> options.option(ConnectionFactoryOptions.USER, (Object)username));
    }

    public ConnectionFactoryBuilder password(CharSequence password) {
        return this.configure(options -> options.option(ConnectionFactoryOptions.PASSWORD, (Object)password));
    }

    public ConnectionFactoryBuilder hostname(String host) {
        return this.configure(options -> options.option(ConnectionFactoryOptions.HOST, (Object)host));
    }

    public ConnectionFactoryBuilder port(int port) {
        return this.configure(options -> options.option(ConnectionFactoryOptions.PORT, (Object)port));
    }

    public ConnectionFactoryBuilder database(String database) {
        return this.configure(options -> options.option(ConnectionFactoryOptions.DATABASE, (Object)database));
    }

    public ConnectionFactory build() {
        ConnectionFactoryOptions options = this.buildOptions();
        return optionsCapableWrapper.buildAndWrap(options);
    }

    public ConnectionFactoryOptions buildOptions() {
        return this.optionsBuilder.build();
    }

    static final class PoolingAwareOptionsCapableWrapper
    extends OptionsCapableWrapper {
        private final PoolingConnectionFactoryProvider poolingProvider = new PoolingConnectionFactoryProvider();

        PoolingAwareOptionsCapableWrapper() {
        }

        @Override
        ConnectionFactory buildAndWrap(ConnectionFactoryOptions options) {
            if (!this.poolingProvider.supports(options)) {
                return super.buildAndWrap(options);
            }
            ConnectionFactoryOptions delegateOptions = this.delegateFactoryOptions(options);
            ConnectionFactory connectionFactory = super.buildAndWrap(delegateOptions);
            ConnectionPoolConfiguration poolConfiguration = this.connectionPoolConfiguration(delegateOptions, connectionFactory);
            return new ConnectionPool(poolConfiguration);
        }

        private ConnectionFactoryOptions delegateFactoryOptions(ConnectionFactoryOptions options) {
            String protocol = this.toString(options.getRequiredValue(ConnectionFactoryOptions.PROTOCOL));
            if (protocol.trim().length() == 0) {
                throw new IllegalArgumentException(String.format("Protocol %s is not valid.", protocol));
            }
            String[] protocols = protocol.split(ConnectionFactoryBuilder.COLON, 2);
            String driverDelegate = protocols[0];
            String protocolDelegate = protocols.length != 2 ? "" : protocols[1];
            return ConnectionFactoryOptions.builder().from(options).option(ConnectionFactoryOptions.DRIVER, (Object)driverDelegate).option(ConnectionFactoryOptions.PROTOCOL, (Object)protocolDelegate).build();
        }

        ConnectionPoolConfiguration connectionPoolConfiguration(ConnectionFactoryOptions options, ConnectionFactory connectionFactory) {
            ConnectionPoolConfiguration.Builder builder = ConnectionPoolConfiguration.builder((ConnectionFactory)connectionFactory);
            PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
            map.from(options.getValue(PoolingConnectionFactoryProvider.BACKGROUND_EVICTION_INTERVAL)).as(this::toDuration).to(arg_0 -> ((ConnectionPoolConfiguration.Builder)builder).backgroundEvictionInterval(arg_0));
            map.from(options.getValue(PoolingConnectionFactoryProvider.INITIAL_SIZE)).as(this::toInteger).to(arg_0 -> ((ConnectionPoolConfiguration.Builder)builder).initialSize(arg_0));
            map.from(options.getValue(PoolingConnectionFactoryProvider.MAX_SIZE)).as(this::toInteger).to(arg_0 -> ((ConnectionPoolConfiguration.Builder)builder).maxSize(arg_0));
            map.from(options.getValue(PoolingConnectionFactoryProvider.ACQUIRE_RETRY)).as(this::toInteger).to(arg_0 -> ((ConnectionPoolConfiguration.Builder)builder).acquireRetry(arg_0));
            map.from(options.getValue(PoolingConnectionFactoryProvider.MAX_LIFE_TIME)).as(this::toDuration).to(arg_0 -> ((ConnectionPoolConfiguration.Builder)builder).maxLifeTime(arg_0));
            map.from(options.getValue(PoolingConnectionFactoryProvider.MAX_ACQUIRE_TIME)).as(this::toDuration).to(arg_0 -> ((ConnectionPoolConfiguration.Builder)builder).maxAcquireTime(arg_0));
            map.from(options.getValue(PoolingConnectionFactoryProvider.MAX_IDLE_TIME)).as(this::toDuration).to(arg_0 -> ((ConnectionPoolConfiguration.Builder)builder).maxIdleTime(arg_0));
            map.from(options.getValue(PoolingConnectionFactoryProvider.MAX_CREATE_CONNECTION_TIME)).as(this::toDuration).to(arg_0 -> ((ConnectionPoolConfiguration.Builder)builder).maxCreateConnectionTime(arg_0));
            map.from(options.getValue(PoolingConnectionFactoryProvider.POOL_NAME)).as(this::toString).to(arg_0 -> ((ConnectionPoolConfiguration.Builder)builder).name(arg_0));
            map.from(options.getValue(PoolingConnectionFactoryProvider.PRE_RELEASE)).to(function -> builder.preRelease((Function)function));
            map.from(options.getValue(PoolingConnectionFactoryProvider.POST_ALLOCATE)).to(function -> builder.postAllocate((Function)function));
            map.from(options.getValue(PoolingConnectionFactoryProvider.REGISTER_JMX)).as(this::toBoolean).to(arg_0 -> ((ConnectionPoolConfiguration.Builder)builder).registerJmx(arg_0));
            map.from(options.getValue(PoolingConnectionFactoryProvider.VALIDATION_QUERY)).as(this::toString).to(arg_0 -> ((ConnectionPoolConfiguration.Builder)builder).validationQuery(arg_0));
            map.from(options.getValue(PoolingConnectionFactoryProvider.VALIDATION_DEPTH)).as(this::toValidationDepth).to(arg_0 -> ((ConnectionPoolConfiguration.Builder)builder).validationDepth(arg_0));
            return builder.build();
        }

        private String toString(Object object) {
            return this.toType(String.class, object, String::valueOf);
        }

        private Integer toInteger(Object object) {
            return this.toType(Integer.class, object, Integer::valueOf);
        }

        private Duration toDuration(Object object) {
            return this.toType(Duration.class, object, Duration::parse);
        }

        private Boolean toBoolean(Object object) {
            return this.toType(Boolean.class, object, Boolean::valueOf);
        }

        private ValidationDepth toValidationDepth(Object object) {
            return this.toType(ValidationDepth.class, object, string -> ValidationDepth.valueOf((String)string.toUpperCase(Locale.ENGLISH)));
        }

        private <T> T toType(Class<T> type, Object object, Function<String, T> converter) {
            if (type.isInstance(object)) {
                return type.cast(object);
            }
            if (object instanceof String) {
                return converter.apply((String)object);
            }
            throw new IllegalArgumentException("Cannot convert '" + object + "' to " + type.getName());
        }
    }

    private static class OptionsCapableWrapper {
        private OptionsCapableWrapper() {
        }

        ConnectionFactory buildAndWrap(ConnectionFactoryOptions options) {
            ConnectionFactory connectionFactory = ConnectionFactories.get((ConnectionFactoryOptions)options);
            return new OptionsCapableConnectionFactory(options, connectionFactory);
        }
    }
}

