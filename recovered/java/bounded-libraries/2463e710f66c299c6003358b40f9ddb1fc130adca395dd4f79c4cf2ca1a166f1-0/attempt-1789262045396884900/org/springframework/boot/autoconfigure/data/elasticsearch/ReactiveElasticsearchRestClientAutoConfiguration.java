/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.boot.context.properties.PropertyMapper
 *  org.springframework.context.annotation.Bean
 *  org.springframework.data.elasticsearch.client.ClientConfiguration
 *  org.springframework.data.elasticsearch.client.ClientConfiguration$ClientConfigurationCallback
 *  org.springframework.data.elasticsearch.client.ClientConfiguration$MaybeSecureClientConfigurationBuilder
 *  org.springframework.data.elasticsearch.client.ClientConfiguration$TerminalClientConfigurationBuilder
 *  org.springframework.data.elasticsearch.client.reactive.ReactiveElasticsearchClient
 *  org.springframework.data.elasticsearch.client.reactive.ReactiveRestClients
 *  org.springframework.data.elasticsearch.client.reactive.ReactiveRestClients$WebClientConfigurationCallback
 *  org.springframework.util.Assert
 *  org.springframework.util.ObjectUtils
 *  org.springframework.util.unit.DataSize
 *  org.springframework.web.reactive.function.client.ExchangeStrategies
 *  org.springframework.web.reactive.function.client.WebClient
 *  reactor.netty.http.client.HttpClient
 */
package org.springframework.boot.autoconfigure.data.elasticsearch;

import java.net.URI;
import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.elasticsearch.DeprecatedReactiveElasticsearchRestClientProperties;
import org.springframework.boot.autoconfigure.data.elasticsearch.ReactiveElasticsearchRestClientProperties;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.reactive.ReactiveElasticsearchClient;
import org.springframework.data.elasticsearch.client.reactive.ReactiveRestClients;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.unit.DataSize;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@AutoConfiguration
@ConditionalOnClass(value={ReactiveRestClients.class, WebClient.class, HttpClient.class})
@EnableConfigurationProperties(value={ElasticsearchProperties.class, ReactiveElasticsearchRestClientProperties.class, DeprecatedReactiveElasticsearchRestClientProperties.class})
public class ReactiveElasticsearchRestClientAutoConfiguration {
    private final ConsolidatedProperties properties;

    ReactiveElasticsearchRestClientAutoConfiguration(ElasticsearchProperties properties, ReactiveElasticsearchRestClientProperties restClientProperties, DeprecatedReactiveElasticsearchRestClientProperties reactiveProperties) {
        this.properties = new ConsolidatedProperties(properties, restClientProperties, reactiveProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public ClientConfiguration clientConfiguration() {
        ClientConfiguration.MaybeSecureClientConfigurationBuilder builder = ClientConfiguration.builder().connectedTo(this.properties.getEndpoints().toArray(new String[0]));
        PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        map.from((Object)this.properties.isUseSsl()).whenTrue().toCall(() -> ((ClientConfiguration.MaybeSecureClientConfigurationBuilder)builder).usingSsl());
        map.from((Object)this.properties.getCredentials()).to(credentials -> builder.withBasicAuth(((ConsolidatedProperties.Credentials)credentials).getUsername(), ((ConsolidatedProperties.Credentials)credentials).getPassword()));
        map.from((Object)this.properties.getConnectionTimeout()).to(arg_0 -> ((ClientConfiguration.MaybeSecureClientConfigurationBuilder)builder).withConnectTimeout(arg_0));
        map.from((Object)this.properties.getSocketTimeout()).to(arg_0 -> ((ClientConfiguration.MaybeSecureClientConfigurationBuilder)builder).withSocketTimeout(arg_0));
        map.from((Object)this.properties.getPathPrefix()).to(arg_0 -> ((ClientConfiguration.MaybeSecureClientConfigurationBuilder)builder).withPathPrefix(arg_0));
        this.configureExchangeStrategies(map, (ClientConfiguration.TerminalClientConfigurationBuilder)builder);
        return builder.build();
    }

    private void configureExchangeStrategies(PropertyMapper map, ClientConfiguration.TerminalClientConfigurationBuilder builder) {
        map.from((Object)this.properties.getMaxInMemorySize()).asInt(DataSize::toBytes).to(maxInMemorySize -> builder.withClientConfigurer((ClientConfiguration.ClientConfigurationCallback)ReactiveRestClients.WebClientConfigurationCallback.from(webClient -> {
            ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder().codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(maxInMemorySize.intValue())).build();
            return webClient.mutate().exchangeStrategies(exchangeStrategies).build();
        })));
    }

    @Bean
    @ConditionalOnMissingBean
    public ReactiveElasticsearchClient reactiveElasticsearchClient(ClientConfiguration clientConfiguration) {
        return ReactiveRestClients.create((ClientConfiguration)clientConfiguration);
    }

    private static final class ConsolidatedProperties {
        private final ElasticsearchProperties properties;
        private final ReactiveElasticsearchRestClientProperties restClientProperties;
        private final DeprecatedReactiveElasticsearchRestClientProperties deprecatedProperties;
        private final List<URI> uris;

        private ConsolidatedProperties(ElasticsearchProperties properties, ReactiveElasticsearchRestClientProperties restClientProperties, DeprecatedReactiveElasticsearchRestClientProperties deprecatedreactiveProperties) {
            this.properties = properties;
            this.restClientProperties = restClientProperties;
            this.deprecatedProperties = deprecatedreactiveProperties;
            this.uris = properties.getUris().stream().map(s -> s.startsWith("http") ? s : "http://" + s).map(URI::create).collect(Collectors.toList());
        }

        private List<String> getEndpoints() {
            if (this.deprecatedProperties.isCustomized()) {
                return this.deprecatedProperties.getEndpoints();
            }
            return this.uris.stream().map(this::getEndpoint).collect(Collectors.toList());
        }

        private String getEndpoint(URI uri) {
            return uri.getHost() + ":" + uri.getPort();
        }

        private Credentials getCredentials() {
            if (this.deprecatedProperties.isCustomized()) {
                return Credentials.from(this.deprecatedProperties);
            }
            Credentials propertyCredentials = Credentials.from(this.properties);
            Credentials uriCredentials = Credentials.from(this.uris);
            if (uriCredentials == null) {
                return propertyCredentials;
            }
            Assert.isTrue((propertyCredentials == null || uriCredentials.equals(propertyCredentials) ? 1 : 0) != 0, (String)"Credentials from URI user info do not match those from spring.elasticsearch.username and spring.elasticsearch.password");
            return uriCredentials;
        }

        private Duration getConnectionTimeout() {
            return this.deprecatedProperties.isCustomized() ? this.deprecatedProperties.getConnectionTimeout() : this.properties.getConnectionTimeout();
        }

        private Duration getSocketTimeout() {
            return this.deprecatedProperties.isCustomized() ? this.deprecatedProperties.getSocketTimeout() : this.properties.getSocketTimeout();
        }

        private boolean isUseSsl() {
            if (this.deprecatedProperties.isCustomized()) {
                return this.deprecatedProperties.isUseSsl();
            }
            Set schemes = this.uris.stream().map(URI::getScheme).collect(Collectors.toSet());
            Assert.isTrue((schemes.size() == 1 ? 1 : 0) != 0, (String)"Configured Elasticsearch URIs have varying schemes");
            return ((String)schemes.iterator().next()).equals("https");
        }

        private DataSize getMaxInMemorySize() {
            return this.deprecatedProperties.isCustomized() ? this.deprecatedProperties.getMaxInMemorySize() : this.restClientProperties.getMaxInMemorySize();
        }

        private String getPathPrefix() {
            return this.deprecatedProperties.isCustomized() ? null : this.properties.getPathPrefix();
        }

        private static final class Credentials {
            private final String username;
            private final String password;

            private Credentials(String username, String password) {
                this.username = username;
                this.password = password;
            }

            private String getUsername() {
                return this.username;
            }

            private String getPassword() {
                return this.password;
            }

            private static Credentials from(List<URI> uris) {
                Set userInfos = uris.stream().map(URI::getUserInfo).collect(Collectors.toSet());
                Assert.isTrue((userInfos.size() == 1 ? 1 : 0) != 0, (String)"Configured Elasticsearch URIs have varying user infos");
                String userInfo = (String)userInfos.iterator().next();
                if (userInfo == null) {
                    return null;
                }
                String[] parts = userInfo.split(":");
                String username = parts[0];
                String password = parts.length != 2 ? "" : parts[1];
                return new Credentials(username, password);
            }

            private static Credentials from(ElasticsearchProperties properties) {
                return Credentials.getCredentials(properties.getUsername(), properties.getPassword());
            }

            private static Credentials from(DeprecatedReactiveElasticsearchRestClientProperties properties) {
                return Credentials.getCredentials(properties.getUsername(), properties.getPassword());
            }

            private static Credentials getCredentials(String username, String password) {
                if (username == null && password == null) {
                    return null;
                }
                return new Credentials(username, password);
            }

            public boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj == null || this.getClass() != obj.getClass()) {
                    return false;
                }
                Credentials other = (Credentials)obj;
                return ObjectUtils.nullSafeEquals((Object)this.username, (Object)other.username) && ObjectUtils.nullSafeEquals((Object)this.password, (Object)other.password);
            }

            public int hashCode() {
                int prime = 31;
                int result = 1;
                result = 31 * result + ObjectUtils.nullSafeHashCode((Object)this.username);
                result = 31 * result + ObjectUtils.nullSafeHashCode((Object)this.password);
                return result;
            }
        }
    }
}

