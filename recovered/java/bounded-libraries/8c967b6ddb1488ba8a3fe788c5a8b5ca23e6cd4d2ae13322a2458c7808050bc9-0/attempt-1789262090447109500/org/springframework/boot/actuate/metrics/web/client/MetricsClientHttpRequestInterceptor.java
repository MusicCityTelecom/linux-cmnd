/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.micrometer.core.instrument.MeterRegistry
 *  io.micrometer.core.instrument.Timer$Builder
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.boot.web.client.RootUriTemplateHandler
 *  org.springframework.core.NamedThreadLocal
 *  org.springframework.http.HttpRequest
 *  org.springframework.http.client.ClientHttpRequestExecution
 *  org.springframework.http.client.ClientHttpRequestInterceptor
 *  org.springframework.http.client.ClientHttpResponse
 *  org.springframework.web.util.UriTemplateHandler
 */
package org.springframework.boot.actuate.metrics.web.client;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import java.io.IOException;
import java.net.URI;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.actuate.metrics.AutoTimer;
import org.springframework.boot.actuate.metrics.web.client.RestTemplateExchangeTagsProvider;
import org.springframework.boot.web.client.RootUriTemplateHandler;
import org.springframework.core.NamedThreadLocal;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.util.UriTemplateHandler;

class MetricsClientHttpRequestInterceptor
implements ClientHttpRequestInterceptor {
    private static final Log logger = LogFactory.getLog(MetricsClientHttpRequestInterceptor.class);
    private static final ThreadLocal<Deque<String>> urlTemplate = new UrlTemplateThreadLocal();
    private final MeterRegistry meterRegistry;
    private final RestTemplateExchangeTagsProvider tagProvider;
    private final String metricName;
    private final AutoTimer autoTimer;

    MetricsClientHttpRequestInterceptor(MeterRegistry meterRegistry, RestTemplateExchangeTagsProvider tagProvider, String metricName, AutoTimer autoTimer) {
        this.tagProvider = tagProvider;
        this.meterRegistry = meterRegistry;
        this.metricName = metricName;
        this.autoTimer = autoTimer != null ? autoTimer : AutoTimer.DISABLED;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        if (!this.enabled()) {
            return execution.execute(request, body);
        }
        long startTime = System.nanoTime();
        ClientHttpResponse response = null;
        try {
            ClientHttpResponse clientHttpResponse = response = execution.execute(request, body);
            return clientHttpResponse;
        }
        finally {
            try {
                this.getTimeBuilder(request, response).register(this.meterRegistry).record(System.nanoTime() - startTime, TimeUnit.NANOSECONDS);
            }
            catch (Exception ex) {
                logger.info((Object)"Failed to record metrics.", (Throwable)ex);
            }
            if (urlTemplate.get().isEmpty()) {
                urlTemplate.remove();
            }
        }
    }

    private boolean enabled() {
        return this.autoTimer.isEnabled();
    }

    UriTemplateHandler createUriTemplateHandler(UriTemplateHandler delegate) {
        if (delegate instanceof RootUriTemplateHandler) {
            return ((RootUriTemplateHandler)delegate).withHandlerWrapper(x$0 -> new CapturingUriTemplateHandler((UriTemplateHandler)x$0));
        }
        return new CapturingUriTemplateHandler(delegate);
    }

    private Timer.Builder getTimeBuilder(HttpRequest request, ClientHttpResponse response) {
        return this.autoTimer.builder(this.metricName).tags(this.tagProvider.getTags(urlTemplate.get().poll(), request, response)).description("Timer of RestTemplate operation");
    }

    private static final class UrlTemplateThreadLocal
    extends NamedThreadLocal<Deque<String>> {
        private UrlTemplateThreadLocal() {
            super("Rest Template URL Template");
        }

        protected Deque<String> initialValue() {
            return new LinkedList<String>();
        }
    }

    private final class CapturingUriTemplateHandler
    implements UriTemplateHandler {
        private final UriTemplateHandler delegate;

        private CapturingUriTemplateHandler(UriTemplateHandler delegate) {
            this.delegate = delegate;
        }

        public URI expand(String url, Map<String, ?> arguments) {
            if (MetricsClientHttpRequestInterceptor.this.enabled()) {
                ((Deque)urlTemplate.get()).push(url);
            }
            return this.delegate.expand(url, arguments);
        }

        public URI expand(String url, Object ... arguments) {
            if (MetricsClientHttpRequestInterceptor.this.enabled()) {
                ((Deque)urlTemplate.get()).push(url);
            }
            return this.delegate.expand(url, arguments);
        }
    }
}

