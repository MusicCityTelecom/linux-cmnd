/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.micrometer.core.instrument.MeterRegistry
 *  org.springframework.boot.web.reactive.function.client.WebClientCustomizer
 *  org.springframework.web.reactive.function.client.WebClient$Builder
 */
package org.springframework.boot.actuate.metrics.web.reactive.client;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.actuate.metrics.AutoTimer;
import org.springframework.boot.actuate.metrics.web.reactive.client.MetricsWebClientFilterFunction;
import org.springframework.boot.actuate.metrics.web.reactive.client.WebClientExchangeTagsProvider;
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.web.reactive.function.client.WebClient;

public class MetricsWebClientCustomizer
implements WebClientCustomizer {
    private final MetricsWebClientFilterFunction filterFunction;

    public MetricsWebClientCustomizer(MeterRegistry meterRegistry, WebClientExchangeTagsProvider tagProvider, String metricName, AutoTimer autoTimer) {
        this.filterFunction = new MetricsWebClientFilterFunction(meterRegistry, tagProvider, metricName, autoTimer);
    }

    public void customize(WebClient.Builder webClientBuilder) {
        webClientBuilder.filters(filterFunctions -> {
            if (!filterFunctions.contains(this.filterFunction)) {
                filterFunctions.add(0, this.filterFunction);
            }
        });
    }
}

