/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.micrometer.core.instrument.MeterRegistry
 *  io.micrometer.core.instrument.Tag
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.web.reactive.function.client.ClientRequest
 *  org.springframework.web.reactive.function.client.ClientResponse
 *  org.springframework.web.reactive.function.client.ExchangeFilterFunction
 *  org.springframework.web.reactive.function.client.ExchangeFunction
 *  reactor.core.publisher.Mono
 *  reactor.core.publisher.SignalType
 *  reactor.util.context.Context
 *  reactor.util.context.ContextView
 */
package org.springframework.boot.actuate.metrics.web.reactive.client;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.actuate.metrics.AutoTimer;
import org.springframework.boot.actuate.metrics.web.reactive.client.WebClientExchangeTagsProvider;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;
import reactor.util.context.Context;
import reactor.util.context.ContextView;

public class MetricsWebClientFilterFunction
implements ExchangeFilterFunction {
    private static final String METRICS_WEBCLIENT_START_TIME = MetricsWebClientFilterFunction.class.getName() + ".START_TIME";
    private static final Log logger = LogFactory.getLog(MetricsWebClientFilterFunction.class);
    private final MeterRegistry meterRegistry;
    private final WebClientExchangeTagsProvider tagProvider;
    private final String metricName;
    private final AutoTimer autoTimer;

    public MetricsWebClientFilterFunction(MeterRegistry meterRegistry, WebClientExchangeTagsProvider tagProvider, String metricName, AutoTimer autoTimer) {
        this.meterRegistry = meterRegistry;
        this.tagProvider = tagProvider;
        this.metricName = metricName;
        this.autoTimer = autoTimer != null ? autoTimer : AutoTimer.DISABLED;
    }

    public Mono<ClientResponse> filter(ClientRequest request, ExchangeFunction next) {
        if (!this.autoTimer.isEnabled()) {
            return next.exchange(request);
        }
        return ((Mono)next.exchange(request).as(responseMono -> this.instrumentResponse(request, (Mono<ClientResponse>)responseMono))).contextWrite(this::putStartTime);
    }

    private Mono<ClientResponse> instrumentResponse(ClientRequest request, Mono<ClientResponse> responseMono) {
        AtomicBoolean responseReceived = new AtomicBoolean();
        return Mono.deferContextual(ctx -> responseMono.doOnEach(signal -> {
            if (signal.isOnNext() || signal.isOnError()) {
                responseReceived.set(true);
                this.recordTimer(request, (ClientResponse)signal.get(), signal.getThrowable(), this.getStartTime((ContextView)ctx));
            }
        }).doFinally(signalType -> {
            if (!responseReceived.get() && SignalType.CANCEL.equals(signalType)) {
                this.recordTimer(request, null, null, this.getStartTime((ContextView)ctx));
            }
        }));
    }

    private void recordTimer(ClientRequest request, ClientResponse response, Throwable error, Long startTime) {
        try {
            Iterable<Tag> tags = this.tagProvider.tags(request, response, error);
            this.autoTimer.builder(this.metricName).tags(tags).description("Timer of WebClient operation").register(this.meterRegistry).record(System.nanoTime() - startTime, TimeUnit.NANOSECONDS);
        }
        catch (Exception ex) {
            logger.warn((Object)"Failed to record timer metrics", (Throwable)ex);
        }
    }

    private Long getStartTime(ContextView context) {
        return (Long)context.get((Object)METRICS_WEBCLIENT_START_TIME);
    }

    private Context putStartTime(Context context) {
        return context.put((Object)METRICS_WEBCLIENT_START_TIME, (Object)System.nanoTime());
    }
}

