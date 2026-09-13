/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.neo4j.driver.AuthToken
 *  org.neo4j.driver.AuthTokens
 *  org.neo4j.driver.Config
 *  org.neo4j.driver.Config$ConfigBuilder
 *  org.neo4j.driver.Config$TrustStrategy
 *  org.neo4j.driver.Driver
 *  org.neo4j.driver.GraphDatabase
 *  org.neo4j.driver.Logging
 *  org.neo4j.driver.internal.Scheme
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.boot.context.properties.source.InvalidConfigurationPropertyValueException
 *  org.springframework.context.annotation.Bean
 *  org.springframework.core.env.Environment
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.autoconfigure.neo4j;

import java.io.File;
import java.net.URI;
import java.time.Duration;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.neo4j.driver.AuthToken;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Config;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Logging;
import org.neo4j.driver.internal.Scheme;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.neo4j.ConfigBuilderCustomizer;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.boot.autoconfigure.neo4j.Neo4jSpringJclLogging;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.source.InvalidConfigurationPropertyValueException;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

@AutoConfiguration
@ConditionalOnClass(value={Driver.class})
@EnableConfigurationProperties(value={Neo4jProperties.class})
public class Neo4jAutoConfiguration {
    private static final URI DEFAULT_SERVER_URI = URI.create("bolt://localhost:7687");

    @Bean
    @ConditionalOnMissingBean
    public Driver neo4jDriver(Neo4jProperties properties, Environment environment, ObjectProvider<ConfigBuilderCustomizer> configBuilderCustomizers) {
        AuthToken authToken = this.mapAuthToken(properties.getAuthentication(), environment);
        Config config = this.mapDriverConfig(properties, configBuilderCustomizers.orderedStream().collect(Collectors.toList()));
        URI serverUri = this.determineServerUri(properties, environment);
        return GraphDatabase.driver((URI)serverUri, (AuthToken)authToken, (Config)config);
    }

    URI determineServerUri(Neo4jProperties properties, Environment environment) {
        return this.getOrFallback(properties.getUri(), () -> {
            URI deprecatedProperty = (URI)environment.getProperty("spring.data.neo4j.uri", URI.class);
            return deprecatedProperty != null ? deprecatedProperty : DEFAULT_SERVER_URI;
        });
    }

    AuthToken mapAuthToken(Neo4jProperties.Authentication authentication, Environment environment) {
        String username = this.getOrFallback(authentication.getUsername(), () -> (String)environment.getProperty("spring.data.neo4j.username", String.class));
        String password = this.getOrFallback(authentication.getPassword(), () -> (String)environment.getProperty("spring.data.neo4j.password", String.class));
        String kerberosTicket = authentication.getKerberosTicket();
        String realm = authentication.getRealm();
        boolean hasUsername = StringUtils.hasText((String)username);
        boolean hasPassword = StringUtils.hasText((String)password);
        boolean hasKerberosTicket = StringUtils.hasText((String)kerberosTicket);
        if (hasUsername && hasKerberosTicket) {
            throw new IllegalStateException(String.format("Cannot specify both username ('%s') and kerberos ticket ('%s')", username, kerberosTicket));
        }
        if (hasUsername && hasPassword) {
            return AuthTokens.basic((String)username, (String)password, (String)realm);
        }
        if (hasKerberosTicket) {
            return AuthTokens.kerberos((String)kerberosTicket);
        }
        return AuthTokens.none();
    }

    private <T> T getOrFallback(T value, Supplier<T> fallback) {
        if (value != null) {
            return value;
        }
        return fallback.get();
    }

    Config mapDriverConfig(Neo4jProperties properties, List<ConfigBuilderCustomizer> customizers) {
        Config.ConfigBuilder builder = Config.builder();
        this.configurePoolSettings(builder, properties.getPool());
        URI uri = properties.getUri();
        String scheme = uri != null ? uri.getScheme() : "bolt";
        this.configureDriverSettings(builder, properties, this.isSimpleScheme(scheme));
        builder.withLogging((Logging)new Neo4jSpringJclLogging());
        customizers.forEach(customizer -> customizer.customize(builder));
        return builder.build();
    }

    private boolean isSimpleScheme(String scheme) {
        String lowerCaseScheme = scheme.toLowerCase(Locale.ENGLISH);
        try {
            Scheme.validateScheme((String)lowerCaseScheme);
        }
        catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(String.format("'%s' is not a supported scheme.", scheme));
        }
        return lowerCaseScheme.equals("bolt") || lowerCaseScheme.equals("neo4j");
    }

    private void configurePoolSettings(Config.ConfigBuilder builder, Neo4jProperties.Pool pool) {
        if (pool.isLogLeakedSessions()) {
            builder.withLeakedSessionsLogging();
        }
        builder.withMaxConnectionPoolSize(pool.getMaxConnectionPoolSize());
        Duration idleTimeBeforeConnectionTest = pool.getIdleTimeBeforeConnectionTest();
        if (idleTimeBeforeConnectionTest != null) {
            builder.withConnectionLivenessCheckTimeout(idleTimeBeforeConnectionTest.toMillis(), TimeUnit.MILLISECONDS);
        }
        builder.withMaxConnectionLifetime(pool.getMaxConnectionLifetime().toMillis(), TimeUnit.MILLISECONDS);
        builder.withConnectionAcquisitionTimeout(pool.getConnectionAcquisitionTimeout().toMillis(), TimeUnit.MILLISECONDS);
        if (pool.isMetricsEnabled()) {
            builder.withDriverMetrics();
        } else {
            builder.withoutDriverMetrics();
        }
    }

    private void configureDriverSettings(Config.ConfigBuilder builder, Neo4jProperties properties, boolean withEncryptionAndTrustSettings) {
        if (withEncryptionAndTrustSettings) {
            this.applyEncryptionAndTrustSettings(builder, properties.getSecurity());
        }
        builder.withConnectionTimeout(properties.getConnectionTimeout().toMillis(), TimeUnit.MILLISECONDS);
        builder.withMaxTransactionRetryTime(properties.getMaxTransactionRetryTime().toMillis(), TimeUnit.MILLISECONDS);
    }

    private void applyEncryptionAndTrustSettings(Config.ConfigBuilder builder, Neo4jProperties.Security securityProperties) {
        if (securityProperties.isEncrypted()) {
            builder.withEncryption();
        } else {
            builder.withoutEncryption();
        }
        builder.withTrustStrategy(this.mapTrustStrategy(securityProperties));
    }

    private Config.TrustStrategy mapTrustStrategy(Neo4jProperties.Security securityProperties) {
        String propertyName = "spring.neo4j.security.trust-strategy";
        Neo4jProperties.Security.TrustStrategy strategy = securityProperties.getTrustStrategy();
        Config.TrustStrategy trustStrategy = this.createTrustStrategy(securityProperties, propertyName, strategy);
        if (securityProperties.isHostnameVerificationEnabled()) {
            trustStrategy.withHostnameVerification();
        } else {
            trustStrategy.withoutHostnameVerification();
        }
        return trustStrategy;
    }

    private Config.TrustStrategy createTrustStrategy(Neo4jProperties.Security securityProperties, String propertyName, Neo4jProperties.Security.TrustStrategy strategy) {
        switch (strategy) {
            case TRUST_ALL_CERTIFICATES: {
                return Config.TrustStrategy.trustAllCertificates();
            }
            case TRUST_SYSTEM_CA_SIGNED_CERTIFICATES: {
                return Config.TrustStrategy.trustSystemCertificates();
            }
            case TRUST_CUSTOM_CA_SIGNED_CERTIFICATES: {
                File certFile = securityProperties.getCertFile();
                if (certFile == null || !certFile.isFile()) {
                    throw new InvalidConfigurationPropertyValueException(propertyName, (Object)strategy.name(), "Configured trust strategy requires a certificate file.");
                }
                return Config.TrustStrategy.trustCustomCertificateSignedBy((File[])new File[]{certFile});
            }
        }
        throw new InvalidConfigurationPropertyValueException(propertyName, (Object)strategy.name(), "Unknown strategy.");
    }
}

