/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.trace.http;

import java.net.URI;
import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.boot.actuate.trace.http.Include;
import org.springframework.boot.actuate.trace.http.TraceableRequest;
import org.springframework.boot.actuate.trace.http.TraceableResponse;

public class HttpExchangeTracer {
    private final Set<Include> includes;

    public HttpExchangeTracer(Set<Include> includes) {
        this.includes = includes;
    }

    public final HttpTrace receivedRequest(TraceableRequest request) {
        return new HttpTrace(new FilteredTraceableRequest(request));
    }

    public final void sendingResponse(HttpTrace trace, TraceableResponse response, Supplier<Principal> principal, Supplier<String> sessionId) {
        this.setIfIncluded(Include.TIME_TAKEN, () -> this.calculateTimeTaken(trace), trace::setTimeTaken);
        this.setIfIncluded(Include.SESSION_ID, sessionId, trace::setSessionId);
        this.setIfIncluded(Include.PRINCIPAL, principal, trace::setPrincipal);
        trace.setResponse(new HttpTrace.Response(new FilteredTraceableResponse(response)));
    }

    protected void postProcessRequestHeaders(Map<String, List<String>> headers) {
    }

    private <T> T getIfIncluded(Include include, Supplier<T> valueSupplier) {
        return this.includes.contains((Object)include) ? (T)valueSupplier.get() : null;
    }

    private <T> void setIfIncluded(Include include, Supplier<T> supplier, Consumer<T> consumer) {
        if (this.includes.contains((Object)include)) {
            consumer.accept(supplier.get());
        }
    }

    private Map<String, List<String>> getHeadersIfIncluded(Include include, Supplier<Map<String, List<String>>> headersSupplier, Predicate<String> headerPredicate) {
        if (!this.includes.contains((Object)include)) {
            return new LinkedHashMap<String, List<String>>();
        }
        return headersSupplier.get().entrySet().stream().filter(entry -> headerPredicate.test((String)entry.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private long calculateTimeTaken(HttpTrace trace) {
        return TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - trace.getStartNanoTime());
    }

    private final class FilteredTraceableResponse
    implements TraceableResponse {
        private final TraceableResponse delegate;

        private FilteredTraceableResponse(TraceableResponse delegate) {
            this.delegate = delegate;
        }

        @Override
        public int getStatus() {
            return this.delegate.getStatus();
        }

        @Override
        public Map<String, List<String>> getHeaders() {
            return HttpExchangeTracer.this.getHeadersIfIncluded(Include.RESPONSE_HEADERS, this.delegate::getHeaders, this::includedHeader);
        }

        private boolean includedHeader(String name) {
            if (name.equalsIgnoreCase("Set-Cookie")) {
                return HttpExchangeTracer.this.includes.contains((Object)Include.COOKIE_HEADERS);
            }
            return true;
        }
    }

    private final class FilteredTraceableRequest
    implements TraceableRequest {
        private final TraceableRequest delegate;

        private FilteredTraceableRequest(TraceableRequest delegate) {
            this.delegate = delegate;
        }

        @Override
        public String getMethod() {
            return this.delegate.getMethod();
        }

        @Override
        public URI getUri() {
            return this.delegate.getUri();
        }

        @Override
        public Map<String, List<String>> getHeaders() {
            Map headers = HttpExchangeTracer.this.getHeadersIfIncluded(Include.REQUEST_HEADERS, this.delegate::getHeaders, this::includedHeader);
            HttpExchangeTracer.this.postProcessRequestHeaders(headers);
            return headers;
        }

        private boolean includedHeader(String name) {
            if (name.equalsIgnoreCase("Cookie")) {
                return HttpExchangeTracer.this.includes.contains((Object)Include.COOKIE_HEADERS);
            }
            if (name.equalsIgnoreCase("Authorization")) {
                return HttpExchangeTracer.this.includes.contains((Object)Include.AUTHORIZATION_HEADER);
            }
            return true;
        }

        @Override
        public String getRemoteAddress() {
            return (String)HttpExchangeTracer.this.getIfIncluded(Include.REMOTE_ADDRESS, this.delegate::getRemoteAddress);
        }
    }
}

