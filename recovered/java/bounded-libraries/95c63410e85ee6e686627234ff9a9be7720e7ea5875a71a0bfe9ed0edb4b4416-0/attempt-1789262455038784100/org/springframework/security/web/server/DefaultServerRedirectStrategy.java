/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.core.log.LogMessage
 *  org.springframework.http.HttpStatus
 *  org.springframework.http.server.reactive.ServerHttpResponse
 *  org.springframework.util.Assert
 *  org.springframework.web.server.ServerWebExchange
 *  reactor.core.publisher.Mono
 */
package org.springframework.security.web.server;

import java.net.URI;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.log.LogMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.web.server.ServerRedirectStrategy;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class DefaultServerRedirectStrategy
implements ServerRedirectStrategy {
    private static final Log logger = LogFactory.getLog(DefaultServerRedirectStrategy.class);
    private HttpStatus httpStatus = HttpStatus.FOUND;
    private boolean contextRelative = true;

    @Override
    public Mono<Void> sendRedirect(ServerWebExchange exchange, URI location) {
        Assert.notNull((Object)exchange, (String)"exchange cannot be null");
        Assert.notNull((Object)location, (String)"location cannot be null");
        return Mono.fromRunnable(() -> {
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(this.httpStatus);
            URI newLocation = this.createLocation(exchange, location);
            logger.debug((Object)LogMessage.format((String)"Redirecting to '%s'", (Object)newLocation));
            response.getHeaders().setLocation(newLocation);
        });
    }

    private URI createLocation(ServerWebExchange exchange, URI location) {
        if (!this.contextRelative) {
            return location;
        }
        String url = location.toASCIIString();
        if (url.startsWith("/")) {
            String context = exchange.getRequest().getPath().contextPath().value();
            return URI.create(context + url);
        }
        return location;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        Assert.notNull((Object)httpStatus, (String)"httpStatus cannot be null");
        this.httpStatus = httpStatus;
    }

    public void setContextRelative(boolean contextRelative) {
        this.contextRelative = contextRelative;
    }
}

