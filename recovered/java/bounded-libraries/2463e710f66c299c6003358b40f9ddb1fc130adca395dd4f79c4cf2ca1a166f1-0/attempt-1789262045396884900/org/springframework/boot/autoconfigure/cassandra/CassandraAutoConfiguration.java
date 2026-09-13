/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.datastax.oss.driver.api.core.CqlSession
 *  com.datastax.oss.driver.api.core.CqlSessionBuilder
 *  com.datastax.oss.driver.api.core.config.DefaultDriverOption
 *  com.datastax.oss.driver.api.core.config.DriverConfigLoader
 *  com.datastax.oss.driver.api.core.config.DriverOption
 *  com.datastax.oss.driver.api.core.config.ProgrammaticDriverConfigLoaderBuilder
 *  com.datastax.oss.driver.internal.core.config.typesafe.DefaultProgrammaticDriverConfigLoaderBuilder
 *  com.typesafe.config.Config
 *  com.typesafe.config.ConfigFactory
 *  com.typesafe.config.ConfigMergeable
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.boot.context.properties.PropertyMapper
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Lazy
 *  org.springframework.context.annotation.Scope
 *  org.springframework.core.io.Resource
 */
package org.springframework.boot.autoconfigure.cassandra;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.CqlSessionBuilder;
import com.datastax.oss.driver.api.core.config.DefaultDriverOption;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;
import com.datastax.oss.driver.api.core.config.DriverOption;
import com.datastax.oss.driver.api.core.config.ProgrammaticDriverConfigLoaderBuilder;
import com.datastax.oss.driver.internal.core.config.typesafe.DefaultProgrammaticDriverConfigLoaderBuilder;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigMergeable;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javax.net.ssl.SSLContext;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.cassandra.CassandraProperties;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.boot.autoconfigure.cassandra.DriverConfigLoaderBuilderCustomizer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;

@AutoConfiguration
@ConditionalOnClass(value={CqlSession.class})
@EnableConfigurationProperties(value={CassandraProperties.class})
public class CassandraAutoConfiguration {
    private static final Config SPRING_BOOT_DEFAULTS;

    @Bean
    @ConditionalOnMissingBean
    @Lazy
    public CqlSession cassandraSession(CqlSessionBuilder cqlSessionBuilder) {
        return (CqlSession)cqlSessionBuilder.build();
    }

    @Bean
    @ConditionalOnMissingBean
    @Scope(value="prototype")
    public CqlSessionBuilder cassandraSessionBuilder(CassandraProperties properties, DriverConfigLoader driverConfigLoader, ObjectProvider<CqlSessionBuilderCustomizer> builderCustomizers) {
        CqlSessionBuilder builder = (CqlSessionBuilder)CqlSession.builder().withConfigLoader(driverConfigLoader);
        this.configureAuthentication(properties, builder);
        this.configureSsl(properties, builder);
        builder.withKeyspace(properties.getKeyspaceName());
        builderCustomizers.orderedStream().forEach(customizer -> customizer.customize(builder));
        return builder;
    }

    private void configureAuthentication(CassandraProperties properties, CqlSessionBuilder builder) {
        if (properties.getUsername() != null) {
            builder.withAuthCredentials(properties.getUsername(), properties.getPassword());
        }
    }

    private void configureSsl(CassandraProperties properties, CqlSessionBuilder builder) {
        if (properties.isSsl()) {
            try {
                builder.withSslContext(SSLContext.getDefault());
            }
            catch (NoSuchAlgorithmException ex) {
                throw new IllegalStateException("Could not setup SSL default context for Cassandra", ex);
            }
        }
    }

    @Bean(destroyMethod="")
    @ConditionalOnMissingBean
    public DriverConfigLoader cassandraDriverConfigLoader(CassandraProperties properties, ObjectProvider<DriverConfigLoaderBuilderCustomizer> builderCustomizers) {
        DefaultProgrammaticDriverConfigLoaderBuilder builder = new DefaultProgrammaticDriverConfigLoaderBuilder(() -> this.cassandraConfiguration(properties), "datastax-java-driver");
        builderCustomizers.orderedStream().forEach(arg_0 -> CassandraAutoConfiguration.lambda$cassandraDriverConfigLoader$2((ProgrammaticDriverConfigLoaderBuilder)builder, arg_0));
        return builder.build();
    }

    private Config cassandraConfiguration(CassandraProperties properties) {
        ConfigFactory.invalidateCaches();
        Config config = ConfigFactory.defaultOverrides();
        config = config.withFallback((ConfigMergeable)this.mapConfig(properties));
        if (properties.getConfig() != null) {
            config = config.withFallback((ConfigMergeable)this.loadConfig(properties.getConfig()));
        }
        config = config.withFallback((ConfigMergeable)SPRING_BOOT_DEFAULTS);
        config = config.withFallback((ConfigMergeable)ConfigFactory.defaultReference());
        return config.resolve();
    }

    private Config loadConfig(Resource resource) {
        try {
            return ConfigFactory.parseURL((URL)resource.getURL());
        }
        catch (IOException ex) {
            throw new IllegalStateException("Failed to load cassandra configuration from " + resource, ex);
        }
    }

    private Config mapConfig(CassandraProperties properties) {
        CassandraDriverOptions options = new CassandraDriverOptions();
        PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        map.from((Object)properties.getSessionName()).whenHasText().to(sessionName -> options.add((DriverOption)DefaultDriverOption.SESSION_NAME, sessionName));
        map.from(properties::getUsername).to(username -> options.add((DriverOption)DefaultDriverOption.AUTH_PROVIDER_USER_NAME, username).add((DriverOption)DefaultDriverOption.AUTH_PROVIDER_PASSWORD, properties.getPassword()));
        map.from(properties::getCompression).to(compression -> options.add((DriverOption)DefaultDriverOption.PROTOCOL_COMPRESSION, compression));
        this.mapConnectionOptions(properties, options);
        this.mapPoolingOptions(properties, options);
        this.mapRequestOptions(properties, options);
        this.mapControlConnectionOptions(properties, options);
        map.from(this.mapContactPoints(properties)).to(contactPoints -> options.add((DriverOption)DefaultDriverOption.CONTACT_POINTS, contactPoints));
        map.from((Object)properties.getLocalDatacenter()).whenHasText().to(localDatacenter -> options.add((DriverOption)DefaultDriverOption.LOAD_BALANCING_LOCAL_DATACENTER, localDatacenter));
        return options.build();
    }

    private void mapConnectionOptions(CassandraProperties properties, CassandraDriverOptions options) {
        PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        CassandraProperties.Connection connectionProperties = properties.getConnection();
        map.from(connectionProperties::getConnectTimeout).asInt(Duration::toMillis).to(connectTimeout -> options.add((DriverOption)DefaultDriverOption.CONNECTION_CONNECT_TIMEOUT, connectTimeout));
        map.from(connectionProperties::getInitQueryTimeout).asInt(Duration::toMillis).to(initQueryTimeout -> options.add((DriverOption)DefaultDriverOption.CONNECTION_INIT_QUERY_TIMEOUT, initQueryTimeout));
    }

    private void mapPoolingOptions(CassandraProperties properties, CassandraDriverOptions options) {
        PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        CassandraProperties.Pool poolProperties = properties.getPool();
        map.from(poolProperties::getIdleTimeout).asInt(Duration::toMillis).to(idleTimeout -> options.add((DriverOption)DefaultDriverOption.HEARTBEAT_TIMEOUT, idleTimeout));
        map.from(poolProperties::getHeartbeatInterval).asInt(Duration::toMillis).to(heartBeatInterval -> options.add((DriverOption)DefaultDriverOption.HEARTBEAT_INTERVAL, heartBeatInterval));
    }

    private void mapRequestOptions(CassandraProperties properties, CassandraDriverOptions options) {
        PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        CassandraProperties.Request requestProperties = properties.getRequest();
        map.from(requestProperties::getTimeout).asInt(Duration::toMillis).to(timeout -> options.add((DriverOption)DefaultDriverOption.REQUEST_TIMEOUT, timeout));
        map.from(requestProperties::getConsistency).to(consistency -> options.add((DriverOption)DefaultDriverOption.REQUEST_CONSISTENCY, (Enum)consistency));
        map.from(requestProperties::getSerialConsistency).to(serialConsistency -> options.add((DriverOption)DefaultDriverOption.REQUEST_SERIAL_CONSISTENCY, (Enum)serialConsistency));
        map.from(requestProperties::getPageSize).to(pageSize -> options.add((DriverOption)DefaultDriverOption.REQUEST_PAGE_SIZE, pageSize));
        CassandraProperties.Throttler throttlerProperties = requestProperties.getThrottler();
        map.from(throttlerProperties::getType).as(CassandraProperties.ThrottlerType::type).to(type -> options.add((DriverOption)DefaultDriverOption.REQUEST_THROTTLER_CLASS, type));
        map.from(throttlerProperties::getMaxQueueSize).to(maxQueueSize -> options.add((DriverOption)DefaultDriverOption.REQUEST_THROTTLER_MAX_QUEUE_SIZE, maxQueueSize));
        map.from(throttlerProperties::getMaxConcurrentRequests).to(maxConcurrentRequests -> options.add((DriverOption)DefaultDriverOption.REQUEST_THROTTLER_MAX_CONCURRENT_REQUESTS, maxConcurrentRequests));
        map.from(throttlerProperties::getMaxRequestsPerSecond).to(maxRequestsPerSecond -> options.add((DriverOption)DefaultDriverOption.REQUEST_THROTTLER_MAX_REQUESTS_PER_SECOND, maxRequestsPerSecond));
        map.from(throttlerProperties::getDrainInterval).asInt(Duration::toMillis).to(drainInterval -> options.add((DriverOption)DefaultDriverOption.REQUEST_THROTTLER_DRAIN_INTERVAL, drainInterval));
    }

    private void mapControlConnectionOptions(CassandraProperties properties, CassandraDriverOptions options) {
        PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        CassandraProperties.Controlconnection controlProperties = properties.getControlconnection();
        map.from(controlProperties::getTimeout).asInt(Duration::toMillis).to(timeout -> options.add((DriverOption)DefaultDriverOption.CONTROL_CONNECTION_TIMEOUT, timeout));
    }

    private List<String> mapContactPoints(CassandraProperties properties) {
        if (properties.getContactPoints() != null) {
            return properties.getContactPoints().stream().map(candidate -> this.formatContactPoint((String)candidate, properties.getPort())).collect(Collectors.toList());
        }
        return null;
    }

    private String formatContactPoint(String candidate, int port) {
        int i = candidate.lastIndexOf(58);
        if (i == -1 || !this.isPort(() -> candidate.substring(i + 1))) {
            return String.format("%s:%s", candidate, port);
        }
        return candidate;
    }

    private boolean isPort(Supplier<String> value) {
        try {
            int i = Integer.parseInt(value.get());
            return i > 0 && i < 65535;
        }
        catch (Exception ex) {
            return false;
        }
    }

    private static /* synthetic */ void lambda$cassandraDriverConfigLoader$2(ProgrammaticDriverConfigLoaderBuilder builder, DriverConfigLoaderBuilderCustomizer customizer) {
        customizer.customize(builder);
    }

    static {
        CassandraDriverOptions options = new CassandraDriverOptions();
        options.add((DriverOption)DefaultDriverOption.CONTACT_POINTS, Collections.singletonList("127.0.0.1:9042"));
        options.add((DriverOption)DefaultDriverOption.PROTOCOL_COMPRESSION, "none");
        options.add((DriverOption)DefaultDriverOption.CONTROL_CONNECTION_TIMEOUT, (int)Duration.ofSeconds(5L).toMillis());
        SPRING_BOOT_DEFAULTS = options.build();
    }

    private static class CassandraDriverOptions {
        private final Map<String, String> options = new LinkedHashMap<String, String>();

        private CassandraDriverOptions() {
        }

        private CassandraDriverOptions add(DriverOption option, String value) {
            String key = CassandraDriverOptions.createKeyFor(option);
            this.options.put(key, value);
            return this;
        }

        private CassandraDriverOptions add(DriverOption option, int value) {
            return this.add(option, String.valueOf(value));
        }

        private CassandraDriverOptions add(DriverOption option, Enum<?> value) {
            return this.add(option, value.name());
        }

        private CassandraDriverOptions add(DriverOption option, List<String> values) {
            for (int i = 0; i < values.size(); ++i) {
                this.options.put(String.format("%s.%s", CassandraDriverOptions.createKeyFor(option), i), values.get(i));
            }
            return this;
        }

        private Config build() {
            return ConfigFactory.parseMap(this.options, (String)"Environment");
        }

        private static String createKeyFor(DriverOption option) {
            return String.format("%s.%s", "datastax-java-driver", option.getPath());
        }
    }
}

