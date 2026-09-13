/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.prometheus.client.CollectorRegistry
 *  org.springframework.lang.Nullable
 */
package org.springframework.boot.actuate.metrics.export.prometheus;

import io.prometheus.client.CollectorRegistry;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Enumeration;
import java.util.Set;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.web.WebEndpointResponse;
import org.springframework.boot.actuate.endpoint.web.annotation.WebEndpoint;
import org.springframework.boot.actuate.metrics.export.prometheus.TextOutputFormat;
import org.springframework.lang.Nullable;

@WebEndpoint(id="prometheus")
public class PrometheusScrapeEndpoint {
    private static final int METRICS_SCRAPE_CHARS_EXTRA = 1024;
    private final CollectorRegistry collectorRegistry;
    private volatile int nextMetricsScrapeSize = 16;

    public PrometheusScrapeEndpoint(CollectorRegistry collectorRegistry) {
        this.collectorRegistry = collectorRegistry;
    }

    @ReadOperation(producesFrom=TextOutputFormat.class)
    public WebEndpointResponse<String> scrape(TextOutputFormat format, @Nullable Set<String> includedNames) {
        try {
            StringWriter writer = new StringWriter(this.nextMetricsScrapeSize);
            Enumeration samples = includedNames != null ? this.collectorRegistry.filteredMetricFamilySamples(includedNames) : this.collectorRegistry.metricFamilySamples();
            format.write(writer, samples);
            String scrapePage = ((Object)writer).toString();
            this.nextMetricsScrapeSize = scrapePage.length() + 1024;
            return new WebEndpointResponse<String>(scrapePage, format);
        }
        catch (IOException ex) {
            throw new IllegalStateException("Writing metrics failed", ex);
        }
    }
}

