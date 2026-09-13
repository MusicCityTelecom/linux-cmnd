/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.context.properties.ConfigurationProperties
 *  org.springframework.boot.context.properties.DeprecatedConfigurationProperty
 */
package org.springframework.boot.autoconfigure.elasticsearch;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.DeprecatedConfigurationProperty;

@ConfigurationProperties(prefix="spring.elasticsearch.rest")
@Deprecated
class DeprecatedElasticsearchRestClientProperties {
    private List<String> uris = new ArrayList<String>(Collections.singletonList("http://localhost:9200"));
    private String username;
    private String password;
    private Duration connectionTimeout = Duration.ofSeconds(1L);
    private Duration readTimeout = Duration.ofSeconds(30L);
    private final Sniffer sniffer = new Sniffer();
    private boolean customized = false;

    DeprecatedElasticsearchRestClientProperties() {
    }

    @DeprecatedConfigurationProperty(replacement="spring.elasticsearch.uris")
    public List<String> getUris() {
        return this.uris;
    }

    public void setUris(List<String> uris) {
        this.customized = true;
        this.uris = uris;
    }

    @DeprecatedConfigurationProperty(replacement="spring.elasticsearch.username")
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.customized = true;
        this.username = username;
    }

    @DeprecatedConfigurationProperty(replacement="spring.elasticsearch.password")
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.customized = true;
        this.password = password;
    }

    @DeprecatedConfigurationProperty(replacement="spring.elasticsearch.connection-timeout")
    public Duration getConnectionTimeout() {
        return this.connectionTimeout;
    }

    public void setConnectionTimeout(Duration connectionTimeout) {
        this.customized = true;
        this.connectionTimeout = connectionTimeout;
    }

    @DeprecatedConfigurationProperty(replacement="spring.elasticsearch.socket-timeout")
    public Duration getReadTimeout() {
        return this.readTimeout;
    }

    public void setReadTimeout(Duration readTimeout) {
        this.customized = true;
        this.readTimeout = readTimeout;
    }

    boolean isCustomized() {
        return this.customized;
    }

    public Sniffer getSniffer() {
        return this.sniffer;
    }

    @Deprecated
    class Sniffer {
        private Duration interval = Duration.ofMinutes(5L);
        private Duration delayAfterFailure = Duration.ofMinutes(1L);

        Sniffer() {
        }

        @DeprecatedConfigurationProperty(replacement="spring.elasticsearch.restclient.sniffer.interval")
        public Duration getInterval() {
            return this.interval;
        }

        public void setInterval(Duration interval) {
            DeprecatedElasticsearchRestClientProperties.this.customized = true;
            this.interval = interval;
        }

        @DeprecatedConfigurationProperty(replacement="spring.elasticsearch.restclient.sniffer.delay-after-failure")
        public Duration getDelayAfterFailure() {
            return this.delayAfterFailure;
        }

        public void setDelayAfterFailure(Duration delayAfterFailure) {
            DeprecatedElasticsearchRestClientProperties.this.customized = true;
            this.delayAfterFailure = delayAfterFailure;
        }
    }
}

