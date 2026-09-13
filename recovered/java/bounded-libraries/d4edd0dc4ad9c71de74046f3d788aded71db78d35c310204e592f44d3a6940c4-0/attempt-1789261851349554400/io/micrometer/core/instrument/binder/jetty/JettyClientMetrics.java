/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.eclipse.jetty.client.api.ContentProvider
 *  org.eclipse.jetty.client.api.Request
 *  org.eclipse.jetty.client.api.Request$Listener
 */
package io.micrometer.core.instrument.binder.jetty;

import io.micrometer.core.annotation.Incubating;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.binder.jetty.JettyClientTagsProvider;
import io.micrometer.core.instrument.config.MeterFilter;
import io.micrometer.core.instrument.internal.OnlyOnceLoggingDenyMeterFilter;
import java.util.Optional;
import org.eclipse.jetty.client.api.ContentProvider;
import org.eclipse.jetty.client.api.Request;

@Incubating(since="1.5.0")
public class JettyClientMetrics
implements Request.Listener {
    private final MeterRegistry registry;
    private final JettyClientTagsProvider tagsProvider;
    private final String timingMetricName;
    private final String contentSizeMetricName;

    protected JettyClientMetrics(MeterRegistry registry, JettyClientTagsProvider tagsProvider, String timingMetricName, String contentSizeMetricName, int maxUriTags) {
        this.registry = registry;
        this.tagsProvider = tagsProvider;
        this.timingMetricName = timingMetricName;
        this.contentSizeMetricName = contentSizeMetricName;
        OnlyOnceLoggingDenyMeterFilter timingMetricDenyFilter = new OnlyOnceLoggingDenyMeterFilter(() -> String.format("Reached the maximum number of URI tags for '%s'.", timingMetricName));
        OnlyOnceLoggingDenyMeterFilter contentSizeMetricDenyFilter = new OnlyOnceLoggingDenyMeterFilter(() -> String.format("Reached the maximum number of URI tags for '%s'.", contentSizeMetricName));
        registry.config().meterFilter(MeterFilter.maximumAllowableTags(this.timingMetricName, "uri", maxUriTags, timingMetricDenyFilter)).meterFilter(MeterFilter.maximumAllowableTags(this.contentSizeMetricName, "uri", maxUriTags, contentSizeMetricDenyFilter));
    }

    public void onQueued(Request request) {
        Timer.Sample sample = Timer.start(this.registry);
        request.onComplete(result -> {
            long requestLength = Optional.ofNullable(result.getRequest().getContent()).map(ContentProvider::getLength).orElse(0L);
            Iterable<Tag> httpRequestTags = this.tagsProvider.httpRequestTags(result);
            if (requestLength >= 0L) {
                DistributionSummary.builder(this.contentSizeMetricName).description("Content sizes for Jetty HTTP client requests").tags(httpRequestTags).register(this.registry).record(requestLength);
            }
            sample.stop(((Timer.Builder)Timer.builder(this.timingMetricName).description("Jetty HTTP client request timing").tags((Iterable)httpRequestTags)).register(this.registry));
        });
    }

    public static Builder builder(MeterRegistry registry, JettyClientTagsProvider tagsProvider) {
        return new Builder(registry, tagsProvider);
    }

    public static class Builder {
        private final MeterRegistry registry;
        private final JettyClientTagsProvider tagsProvider;
        private String timingMetricName = "jetty.client.requests";
        private String contentSizeMetricName = "jetty.client.request.size";
        private int maxUriTags = 1000;

        Builder(MeterRegistry registry, JettyClientTagsProvider tagsProvider) {
            this.registry = registry;
            this.tagsProvider = tagsProvider;
        }

        public Builder timingMetricName(String metricName) {
            this.timingMetricName = metricName;
            return this;
        }

        public Builder contentSizeMetricName(String metricName) {
            this.contentSizeMetricName = metricName;
            return this;
        }

        public Builder maxUriTags(int maxUriTags) {
            this.maxUriTags = maxUriTags;
            return this;
        }

        public JettyClientMetrics build() {
            return new JettyClientMetrics(this.registry, this.tagsProvider, this.timingMetricName, this.contentSizeMetricName, this.maxUriTags);
        }
    }
}

