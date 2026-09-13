/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.http.HttpHeaders
 *  org.springframework.web.server.ServerWebExchange
 *  reactor.core.publisher.Mono
 */
package org.springframework.security.web.server.header;

import java.util.Arrays;
import org.springframework.http.HttpHeaders;
import org.springframework.security.web.server.header.ServerHttpHeadersWriter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class StaticServerHttpHeadersWriter
implements ServerHttpHeadersWriter {
    private final HttpHeaders headersToAdd;

    public StaticServerHttpHeadersWriter(HttpHeaders headersToAdd) {
        this.headersToAdd = headersToAdd;
    }

    @Override
    public Mono<Void> writeHttpHeaders(ServerWebExchange exchange) {
        HttpHeaders headers = exchange.getResponse().getHeaders();
        boolean containsNoHeadersToAdd = true;
        for (String headerName : this.headersToAdd.keySet()) {
            if (!headers.containsKey((Object)headerName)) continue;
            containsNoHeadersToAdd = false;
            break;
        }
        if (containsNoHeadersToAdd) {
            this.headersToAdd.forEach((arg_0, arg_1) -> ((HttpHeaders)headers).put(arg_0, arg_1));
        }
        return Mono.empty();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private HttpHeaders headers = new HttpHeaders();

        public Builder header(String headerName, String ... values) {
            this.headers.put(headerName, Arrays.asList(values));
            return this;
        }

        public StaticServerHttpHeadersWriter build() {
            return new StaticServerHttpHeadersWriter(this.headers);
        }
    }
}

