/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.http.HttpClientConnection
 *  org.apache.http.HttpException
 *  org.apache.http.HttpRequest
 *  org.apache.http.HttpResponse
 *  org.apache.http.protocol.HttpContext
 *  org.apache.http.protocol.HttpRequestExecutor
 */
package io.micrometer.core.instrument.binder.httpcomponents;

import io.micrometer.core.annotation.Incubating;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.binder.httpcomponents.DefaultUriMapper;
import io.micrometer.core.instrument.binder.httpcomponents.HttpContextUtils;
import java.io.IOException;
import java.util.Collections;
import java.util.Optional;
import java.util.function.Function;
import org.apache.http.HttpClientConnection;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestExecutor;

@Incubating(since="1.2.0")
public class MicrometerHttpRequestExecutor
extends HttpRequestExecutor {
    @Deprecated
    public static final String DEFAULT_URI_PATTERN_HEADER = "URI_PATTERN";
    private static final String METER_NAME = "httpcomponents.httpclient.request";
    private static final Tag STATUS_UNKNOWN = Tag.of("status", "UNKNOWN");
    private static final Tag STATUS_CLIENT_ERROR = Tag.of("status", "CLIENT_ERROR");
    private static final Tag STATUS_IO_ERROR = Tag.of("status", "IO_ERROR");
    private final MeterRegistry registry;
    private final Function<HttpRequest, String> uriMapper;
    private final Iterable<Tag> extraTags;
    private final boolean exportTagsForRoute;

    private MicrometerHttpRequestExecutor(int waitForContinue, MeterRegistry registry, Function<HttpRequest, String> uriMapper, Iterable<Tag> extraTags, boolean exportTagsForRoute) {
        super(waitForContinue);
        this.registry = Optional.ofNullable(registry).orElseThrow(() -> new IllegalArgumentException("registry is required but has been initialized with null"));
        this.uriMapper = Optional.ofNullable(uriMapper).orElseThrow(() -> new IllegalArgumentException("uriMapper is required but has been initialized with null"));
        this.extraTags = Optional.ofNullable(extraTags).orElse(Collections.emptyList());
        this.exportTagsForRoute = exportTagsForRoute;
    }

    public static Builder builder(MeterRegistry registry) {
        return new Builder(registry);
    }

    public HttpResponse execute(HttpRequest request, HttpClientConnection conn, HttpContext context) throws IOException, HttpException {
        HttpResponse httpResponse;
        Timer.Sample timerSample = Timer.start(this.registry);
        Tag method = Tag.of("method", request.getRequestLine().getMethod());
        Tag uri = Tag.of("uri", this.uriMapper.apply(request));
        Tag status = STATUS_UNKNOWN;
        Tags routeTags = this.exportTagsForRoute ? HttpContextUtils.generateTagsForRoute(context) : Tags.empty();
        try {
            HttpResponse response = super.execute(request, conn, context);
            status = response != null ? Tag.of("status", Integer.toString(response.getStatusLine().getStatusCode())) : STATUS_CLIENT_ERROR;
            httpResponse = response;
        }
        catch (IOException | RuntimeException | HttpException e) {
            try {
                status = STATUS_IO_ERROR;
                throw e;
            }
            catch (Throwable throwable) {
                Tags tags = Tags.of(this.extraTags).and(routeTags).and(uri, method, status);
                timerSample.stop(((Timer.Builder)Timer.builder(METER_NAME).description("Duration of Apache HttpClient request execution").tags((Iterable)tags)).register(this.registry));
                throw throwable;
            }
        }
        Tags tags = Tags.of(this.extraTags).and(routeTags).and(uri, method, status);
        timerSample.stop(((Timer.Builder)Timer.builder(METER_NAME).description("Duration of Apache HttpClient request execution").tags((Iterable)tags)).register(this.registry));
        return httpResponse;
    }

    public static class Builder {
        private final MeterRegistry registry;
        private int waitForContinue = 3000;
        private Iterable<Tag> tags = Collections.emptyList();
        private Function<HttpRequest, String> uriMapper = new DefaultUriMapper();
        private boolean exportTagsForRoute = false;

        Builder(MeterRegistry registry) {
            this.registry = registry;
        }

        public Builder waitForContinue(int waitForContinue) {
            this.waitForContinue = waitForContinue;
            return this;
        }

        public Builder tags(Iterable<Tag> tags) {
            this.tags = tags;
            return this;
        }

        public Builder uriMapper(Function<HttpRequest, String> uriMapper) {
            this.uriMapper = uriMapper;
            return this;
        }

        public Builder exportTagsForRoute(boolean exportTagsForRoute) {
            this.exportTagsForRoute = exportTagsForRoute;
            return this;
        }

        public MicrometerHttpRequestExecutor build() {
            return new MicrometerHttpRequestExecutor(this.waitForContinue, this.registry, this.uriMapper, this.tags, this.exportTagsForRoute);
        }
    }
}

