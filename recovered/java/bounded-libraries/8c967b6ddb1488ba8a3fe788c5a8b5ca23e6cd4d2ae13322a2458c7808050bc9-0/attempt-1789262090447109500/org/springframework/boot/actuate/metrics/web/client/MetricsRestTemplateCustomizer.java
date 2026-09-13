/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.micrometer.core.instrument.MeterRegistry
 *  org.springframework.boot.web.client.RestTemplateCustomizer
 *  org.springframework.web.client.RestTemplate
 *  org.springframework.web.util.UriTemplateHandler
 */
package org.springframework.boot.actuate.metrics.web.client;

import io.micrometer.core.instrument.MeterRegistry;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.actuate.metrics.AutoTimer;
import org.springframework.boot.actuate.metrics.web.client.MetricsClientHttpRequestInterceptor;
import org.springframework.boot.actuate.metrics.web.client.RestTemplateExchangeTagsProvider;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplateHandler;

public class MetricsRestTemplateCustomizer
implements RestTemplateCustomizer {
    private final MetricsClientHttpRequestInterceptor interceptor;

    public MetricsRestTemplateCustomizer(MeterRegistry meterRegistry, RestTemplateExchangeTagsProvider tagProvider, String metricName, AutoTimer autoTimer) {
        this.interceptor = new MetricsClientHttpRequestInterceptor(meterRegistry, tagProvider, metricName, autoTimer);
    }

    public void customize(RestTemplate restTemplate) {
        UriTemplateHandler templateHandler = restTemplate.getUriTemplateHandler();
        templateHandler = this.interceptor.createUriTemplateHandler(templateHandler);
        restTemplate.setUriTemplateHandler(templateHandler);
        List existingInterceptors = restTemplate.getInterceptors();
        if (!existingInterceptors.contains(this.interceptor)) {
            ArrayList<MetricsClientHttpRequestInterceptor> interceptors = new ArrayList<MetricsClientHttpRequestInterceptor>();
            interceptors.add(this.interceptor);
            interceptors.addAll(existingInterceptors);
            restTemplate.setInterceptors(interceptors);
        }
    }
}

