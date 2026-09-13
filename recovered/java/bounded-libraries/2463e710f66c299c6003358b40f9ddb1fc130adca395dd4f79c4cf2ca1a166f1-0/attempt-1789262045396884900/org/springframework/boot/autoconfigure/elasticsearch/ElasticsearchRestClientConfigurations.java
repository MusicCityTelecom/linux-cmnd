/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.http.HttpHost
 *  org.apache.http.auth.AuthScope
 *  org.apache.http.auth.Credentials
 *  org.apache.http.auth.UsernamePasswordCredentials
 *  org.apache.http.client.CredentialsProvider
 *  org.apache.http.client.config.RequestConfig$Builder
 *  org.apache.http.impl.client.BasicCredentialsProvider
 *  org.apache.http.impl.nio.client.HttpAsyncClientBuilder
 *  org.elasticsearch.client.RestClient
 *  org.elasticsearch.client.RestClientBuilder
 *  org.elasticsearch.client.RestHighLevelClient
 *  org.elasticsearch.client.sniff.Sniffer
 *  org.elasticsearch.client.sniff.SnifferBuilder
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.boot.context.properties.PropertyMapper
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.autoconfigure.elasticsearch;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.List;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.sniff.Sniffer;
import org.elasticsearch.client.sniff.SnifferBuilder;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.elasticsearch.DeprecatedElasticsearchRestClientProperties;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchProperties;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchRestClientProperties;
import org.springframework.boot.autoconfigure.elasticsearch.RestClientBuilderCustomizer;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

class ElasticsearchRestClientConfigurations {
    ElasticsearchRestClientConfigurations() {
    }

    private static final class ConsolidatedProperties {
        private final ElasticsearchProperties properties;
        private final DeprecatedElasticsearchRestClientProperties deprecatedProperties;

        private ConsolidatedProperties(ElasticsearchProperties properties, DeprecatedElasticsearchRestClientProperties deprecatedProperties) {
            this.properties = properties;
            this.deprecatedProperties = deprecatedProperties;
        }

        private List<String> getUris() {
            return this.deprecatedProperties.isCustomized() ? this.deprecatedProperties.getUris() : this.properties.getUris();
        }

        private String getUsername() {
            return this.deprecatedProperties.isCustomized() ? this.deprecatedProperties.getUsername() : this.properties.getUsername();
        }

        private String getPassword() {
            return this.deprecatedProperties.isCustomized() ? this.deprecatedProperties.getPassword() : this.properties.getPassword();
        }

        private Duration getConnectionTimeout() {
            return this.deprecatedProperties.isCustomized() ? this.deprecatedProperties.getConnectionTimeout() : this.properties.getConnectionTimeout();
        }

        private Duration getSocketTimeout() {
            return this.deprecatedProperties.isCustomized() ? this.deprecatedProperties.getReadTimeout() : this.properties.getSocketTimeout();
        }

        private String getPathPrefix() {
            return this.deprecatedProperties.isCustomized() ? null : this.properties.getPathPrefix();
        }
    }

    private static class PropertiesCredentialsProvider
    extends BasicCredentialsProvider {
        PropertiesCredentialsProvider(ConsolidatedProperties properties) {
            if (StringUtils.hasText((String)properties.getUsername())) {
                UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(properties.getUsername(), properties.getPassword());
                this.setCredentials(AuthScope.ANY, (Credentials)credentials);
            }
            properties.getUris().stream().map(this::toUri).filter(this::hasUserInfo).forEach(this::addUserInfoCredentials);
        }

        private URI toUri(String uri) {
            try {
                return URI.create(uri);
            }
            catch (IllegalArgumentException ex) {
                return null;
            }
        }

        private boolean hasUserInfo(URI uri) {
            return uri != null && StringUtils.hasLength((String)uri.getUserInfo());
        }

        private void addUserInfoCredentials(URI uri) {
            AuthScope authScope = new AuthScope(uri.getHost(), uri.getPort());
            Credentials credentials = this.createUserInfoCredentials(uri.getUserInfo());
            this.setCredentials(authScope, credentials);
        }

        private Credentials createUserInfoCredentials(String userInfo) {
            int delimiter = userInfo.indexOf(":");
            if (delimiter == -1) {
                return new UsernamePasswordCredentials(userInfo, null);
            }
            String username = userInfo.substring(0, delimiter);
            String password = userInfo.substring(delimiter + 1);
            return new UsernamePasswordCredentials(username, password);
        }
    }

    static class DefaultRestClientBuilderCustomizer
    implements RestClientBuilderCustomizer {
        private static final PropertyMapper map = PropertyMapper.get();
        private final ConsolidatedProperties properties;

        DefaultRestClientBuilderCustomizer(ConsolidatedProperties properties) {
            this.properties = properties;
        }

        @Override
        public void customize(RestClientBuilder builder) {
        }

        @Override
        public void customize(HttpAsyncClientBuilder builder) {
            builder.setDefaultCredentialsProvider((CredentialsProvider)new PropertiesCredentialsProvider(this.properties));
        }

        @Override
        public void customize(RequestConfig.Builder builder) {
            map.from(() -> this.properties.getConnectionTimeout()).whenNonNull().asInt(Duration::toMillis).to(arg_0 -> ((RequestConfig.Builder)builder).setConnectTimeout(arg_0));
            map.from(() -> this.properties.getSocketTimeout()).whenNonNull().asInt(Duration::toMillis).to(arg_0 -> ((RequestConfig.Builder)builder).setSocketTimeout(arg_0));
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={Sniffer.class})
    @ConditionalOnSingleCandidate(value=RestClient.class)
    static class RestClientSnifferConfiguration {
        RestClientSnifferConfiguration() {
        }

        @Bean
        @ConditionalOnMissingBean
        Sniffer elasticsearchSniffer(RestClient client, ElasticsearchRestClientProperties properties, DeprecatedElasticsearchRestClientProperties deprecatedProperties) {
            SnifferBuilder builder = Sniffer.builder((RestClient)client);
            PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
            Duration interval = deprecatedProperties.isCustomized() ? deprecatedProperties.getSniffer().getInterval() : properties.getSniffer().getInterval();
            map.from((Object)interval).asInt(Duration::toMillis).to(arg_0 -> ((SnifferBuilder)builder).setSniffIntervalMillis(arg_0));
            Duration delayAfterFailure = deprecatedProperties.isCustomized() ? deprecatedProperties.getSniffer().getDelayAfterFailure() : properties.getSniffer().getDelayAfterFailure();
            map.from((Object)delayAfterFailure).asInt(Duration::toMillis).to(arg_0 -> ((SnifferBuilder)builder).setSniffAfterFailureDelayMillis(arg_0));
            return builder.build();
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnMissingClass(value={"org.elasticsearch.client.RestHighLevelClient"})
    @ConditionalOnMissingBean(value={RestClient.class})
    static class RestClientConfiguration {
        RestClientConfiguration() {
        }

        @Bean
        RestClient elasticsearchRestClient(RestClientBuilder restClientBuilder) {
            return restClientBuilder.build();
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={RestHighLevelClient.class})
    @ConditionalOnSingleCandidate(value=RestHighLevelClient.class)
    @ConditionalOnMissingBean(value={RestClient.class})
    static class RestClientFromRestHighLevelClientConfiguration {
        RestClientFromRestHighLevelClientConfiguration() {
        }

        @Bean
        RestClient elasticsearchRestClient(RestHighLevelClient restHighLevelClient) {
            return restHighLevelClient.getLowLevelClient();
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={RestHighLevelClient.class})
    @ConditionalOnMissingBean(value={RestHighLevelClient.class, RestClient.class})
    static class RestHighLevelClientConfiguration {
        RestHighLevelClientConfiguration() {
        }

        @Bean
        RestHighLevelClient elasticsearchRestHighLevelClient(RestClientBuilder restClientBuilder) {
            return new RestHighLevelClient(restClientBuilder);
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnMissingBean(value={RestClientBuilder.class})
    static class RestClientBuilderConfiguration {
        private final ConsolidatedProperties properties;

        RestClientBuilderConfiguration(ElasticsearchProperties properties, DeprecatedElasticsearchRestClientProperties deprecatedProperties) {
            this.properties = new ConsolidatedProperties(properties, deprecatedProperties);
        }

        @Bean
        RestClientBuilderCustomizer defaultRestClientBuilderCustomizer() {
            return new DefaultRestClientBuilderCustomizer(this.properties);
        }

        @Bean
        RestClientBuilder elasticsearchRestClientBuilder(ObjectProvider<RestClientBuilderCustomizer> builderCustomizers) {
            HttpHost[] hosts = (HttpHost[])this.properties.getUris().stream().map(this::createHttpHost).toArray(HttpHost[]::new);
            RestClientBuilder builder = RestClient.builder((HttpHost[])hosts);
            builder.setHttpClientConfigCallback(httpClientBuilder -> {
                builderCustomizers.orderedStream().forEach(customizer -> customizer.customize(httpClientBuilder));
                return httpClientBuilder;
            });
            builder.setRequestConfigCallback(requestConfigBuilder -> {
                builderCustomizers.orderedStream().forEach(customizer -> customizer.customize(requestConfigBuilder));
                return requestConfigBuilder;
            });
            if (this.properties.getPathPrefix() != null) {
                builder.setPathPrefix(this.properties.properties.getPathPrefix());
            }
            builderCustomizers.orderedStream().forEach(customizer -> customizer.customize(builder));
            return builder;
        }

        private HttpHost createHttpHost(String uri) {
            try {
                return this.createHttpHost(URI.create(uri));
            }
            catch (IllegalArgumentException ex) {
                return HttpHost.create((String)uri);
            }
        }

        private HttpHost createHttpHost(URI uri) {
            if (!StringUtils.hasLength((String)uri.getUserInfo())) {
                return HttpHost.create((String)uri.toString());
            }
            try {
                return HttpHost.create((String)new URI(uri.getScheme(), null, uri.getHost(), uri.getPort(), uri.getPath(), uri.getQuery(), uri.getFragment()).toString());
            }
            catch (URISyntaxException ex) {
                throw new IllegalStateException(ex);
            }
        }
    }
}

