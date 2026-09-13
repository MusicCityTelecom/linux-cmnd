/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 */
package org.springframework.boot.actuate.trace.http;

import java.util.List;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.util.Assert;

@Endpoint(id="httptrace")
public class HttpTraceEndpoint {
    private final HttpTraceRepository repository;

    public HttpTraceEndpoint(HttpTraceRepository repository) {
        Assert.notNull((Object)repository, (String)"Repository must not be null");
        this.repository = repository;
    }

    @ReadOperation
    public HttpTraceDescriptor traces() {
        return new HttpTraceDescriptor(this.repository.findAll());
    }

    public static final class HttpTraceDescriptor {
        private final List<HttpTrace> traces;

        private HttpTraceDescriptor(List<HttpTrace> traces) {
            this.traces = traces;
        }

        public List<HttpTrace> getTraces() {
            return this.traces;
        }
    }
}

