/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.context.properties.ConfigurationProperties
 */
package org.springframework.boot.autoconfigure.elasticsearch;

import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="spring.elasticsearch.restclient")
public class ElasticsearchRestClientProperties {
    private final Sniffer sniffer = new Sniffer();

    public Sniffer getSniffer() {
        return this.sniffer;
    }

    public static class Sniffer {
        private Duration interval = Duration.ofMinutes(5L);
        private Duration delayAfterFailure = Duration.ofMinutes(1L);

        public Duration getInterval() {
            return this.interval;
        }

        public void setInterval(Duration interval) {
            this.interval = interval;
        }

        public Duration getDelayAfterFailure() {
            return this.delayAfterFailure;
        }

        public void setDelayAfterFailure(Duration delayAfterFailure) {
            this.delayAfterFailure = delayAfterFailure;
        }
    }
}

