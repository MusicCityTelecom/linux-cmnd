/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.logging;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Priority;
import javax.ws.rs.ConstrainedTo;
import javax.ws.rs.RuntimeType;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.container.PreMatching;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.logging.LoggingInterceptor;
import org.glassfish.jersey.message.MessageUtils;

@ConstrainedTo(value=RuntimeType.CLIENT)
@PreMatching
@Priority(value=0x7FFFFFFF)
final class ClientLoggingFilter
extends LoggingInterceptor
implements ClientRequestFilter,
ClientResponseFilter {
    public ClientLoggingFilter(Logger logger, Level level, LoggingFeature.Verbosity verbosity, int maxEntitySize) {
        super(logger, level, verbosity, maxEntitySize);
    }

    @Override
    public void filter(ClientRequestContext context) throws IOException {
        if (!this.logger.isLoggable(this.level)) {
            return;
        }
        long id = this._id.incrementAndGet();
        context.setProperty(LOGGING_ID_PROPERTY, id);
        StringBuilder b = new StringBuilder();
        this.printRequestLine(b, "Sending client request", id, context.getMethod(), context.getUri());
        this.printPrefixedHeaders(b, id, "> ", context.getStringHeaders());
        if (context.hasEntity() && ClientLoggingFilter.printEntity(this.verbosity, context.getMediaType())) {
            LoggingInterceptor.LoggingStream stream = new LoggingInterceptor.LoggingStream(this, b, context.getEntityStream());
            context.setEntityStream(stream);
            context.setProperty(ENTITY_LOGGER_PROPERTY, stream);
        } else {
            this.log(b);
        }
    }

    @Override
    public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext) throws IOException {
        if (!this.logger.isLoggable(this.level)) {
            return;
        }
        Object requestId = requestContext.getProperty(LOGGING_ID_PROPERTY);
        long id = requestId != null ? ((Long)requestId).longValue() : this._id.incrementAndGet();
        StringBuilder b = new StringBuilder();
        this.printResponseLine(b, "Client response received", id, responseContext.getStatus());
        this.printPrefixedHeaders(b, id, "< ", responseContext.getHeaders());
        if (responseContext.hasEntity() && ClientLoggingFilter.printEntity(this.verbosity, responseContext.getMediaType())) {
            responseContext.setEntityStream(this.logInboundEntity(b, responseContext.getEntityStream(), MessageUtils.getCharset(responseContext.getMediaType())));
        }
        this.log(b);
    }
}

