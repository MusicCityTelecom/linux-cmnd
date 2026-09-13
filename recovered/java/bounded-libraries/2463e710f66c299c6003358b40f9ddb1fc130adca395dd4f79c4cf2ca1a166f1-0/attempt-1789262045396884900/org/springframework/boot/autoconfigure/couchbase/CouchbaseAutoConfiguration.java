/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.couchbase.client.core.env.IoConfig
 *  com.couchbase.client.core.env.SecurityConfig
 *  com.couchbase.client.core.env.TimeoutConfig
 *  com.couchbase.client.java.Cluster
 *  com.couchbase.client.java.ClusterOptions
 *  com.couchbase.client.java.codec.JacksonJsonSerializer
 *  com.couchbase.client.java.codec.JsonSerializer
 *  com.couchbase.client.java.env.ClusterEnvironment
 *  com.couchbase.client.java.env.ClusterEnvironment$Builder
 *  com.couchbase.client.java.json.JsonValueModule
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.core.Ordered
 *  org.springframework.util.ResourceUtils
 */
package org.springframework.boot.autoconfigure.couchbase;

import com.couchbase.client.core.env.IoConfig;
import com.couchbase.client.core.env.SecurityConfig;
import com.couchbase.client.core.env.TimeoutConfig;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.ClusterOptions;
import com.couchbase.client.java.codec.JacksonJsonSerializer;
import com.couchbase.client.java.codec.JsonSerializer;
import com.couchbase.client.java.env.ClusterEnvironment;
import com.couchbase.client.java.json.JsonValueModule;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyStore;
import java.time.Duration;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.couchbase.ClusterEnvironmentBuilderCustomizer;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.util.ResourceUtils;

@AutoConfiguration(after={JacksonAutoConfiguration.class})
@ConditionalOnClass(value={Cluster.class})
@ConditionalOnProperty(value={"spring.couchbase.connection-string"})
@EnableConfigurationProperties(value={CouchbaseProperties.class})
public class CouchbaseAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public ClusterEnvironment couchbaseClusterEnvironment(CouchbaseProperties properties, ObjectProvider<ClusterEnvironmentBuilderCustomizer> customizers) {
        ClusterEnvironment.Builder builder = this.initializeEnvironmentBuilder(properties);
        customizers.orderedStream().forEach(customizer -> customizer.customize(builder));
        return builder.build();
    }

    @Bean(destroyMethod="disconnect")
    @ConditionalOnMissingBean
    public Cluster couchbaseCluster(CouchbaseProperties properties, ClusterEnvironment couchbaseClusterEnvironment) {
        ClusterOptions options = ClusterOptions.clusterOptions((String)properties.getUsername(), (String)properties.getPassword()).environment(couchbaseClusterEnvironment);
        return Cluster.connect((String)properties.getConnectionString(), (ClusterOptions)options);
    }

    private ClusterEnvironment.Builder initializeEnvironmentBuilder(CouchbaseProperties properties) {
        ClusterEnvironment.Builder builder = ClusterEnvironment.builder();
        CouchbaseProperties.Timeouts timeouts = properties.getEnv().getTimeouts();
        builder.timeoutConfig(TimeoutConfig.kvTimeout((Duration)timeouts.getKeyValue()).analyticsTimeout(timeouts.getAnalytics()).kvDurableTimeout(timeouts.getKeyValueDurable()).queryTimeout(timeouts.getQuery()).viewTimeout(timeouts.getView()).searchTimeout(timeouts.getSearch()).managementTimeout(timeouts.getManagement()).connectTimeout(timeouts.getConnect()).disconnectTimeout(timeouts.getDisconnect()));
        CouchbaseProperties.Io io = properties.getEnv().getIo();
        builder.ioConfig(IoConfig.maxHttpConnections((int)io.getMaxEndpoints()).numKvConnections(io.getMinEndpoints()).idleHttpConnectionTimeout(io.getIdleHttpConnectionTimeout()));
        if (properties.getEnv().getSsl().getEnabled().booleanValue()) {
            builder.securityConfig(SecurityConfig.enableTls((boolean)true).trustManagerFactory(this.getTrustManagerFactory(properties.getEnv().getSsl())));
        }
        return builder;
    }

    private TrustManagerFactory getTrustManagerFactory(CouchbaseProperties.Ssl ssl) {
        String resource = ssl.getKeyStore();
        try {
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            KeyStore keyStore = this.loadKeyStore(resource, ssl.getKeyStorePassword());
            trustManagerFactory.init(keyStore);
            return trustManagerFactory;
        }
        catch (Exception ex) {
            throw new IllegalStateException("Could not load Couchbase key store '" + resource + "'", ex);
        }
    }

    private KeyStore loadKeyStore(String resource, String keyStorePassword) throws Exception {
        KeyStore store = KeyStore.getInstance(KeyStore.getDefaultType());
        URL url = ResourceUtils.getURL((String)resource);
        try (InputStream stream = url.openStream();){
            store.load(stream, keyStorePassword != null ? keyStorePassword.toCharArray() : null);
        }
        return store;
    }

    private static final class JacksonClusterEnvironmentBuilderCustomizer
    implements ClusterEnvironmentBuilderCustomizer,
    Ordered {
        private final ObjectMapper objectMapper;

        private JacksonClusterEnvironmentBuilderCustomizer(ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
        }

        @Override
        public void customize(ClusterEnvironment.Builder builder) {
            builder.jsonSerializer((JsonSerializer)JacksonJsonSerializer.create((ObjectMapper)this.objectMapper));
        }

        public int getOrder() {
            return 0;
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={ObjectMapper.class})
    static class JacksonConfiguration {
        JacksonConfiguration() {
        }

        @Bean
        @ConditionalOnSingleCandidate(value=ObjectMapper.class)
        ClusterEnvironmentBuilderCustomizer jacksonClusterEnvironmentBuilderCustomizer(ObjectMapper objectMapper) {
            return new JacksonClusterEnvironmentBuilderCustomizer(objectMapper.copy().registerModule((Module)new JsonValueModule()));
        }
    }
}

