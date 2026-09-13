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
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.logging.LoggingInterceptor;
import org.glassfish.jersey.message.MessageUtils;

@ConstrainedTo(value=RuntimeType.SERVER)
@PreMatching
@Priority(value=-2147483648)
final class ServerLoggingFilter
extends LoggingInterceptor
implements ContainerRequestFilter,
ContainerResponseFilter {
    public ServerLoggingFilter(Logger logger, Level level, LoggingFeature.Verbosity verbosity, int maxEntitySize) {
        super(logger, level, verbosity, maxEntitySize);
    }

    @Override
    public void filter(ContainerRequestContext context) throws IOException {
        if (!this.logger.isLoggable(this.level)) {
            return;
        }
        long id = this._id.incrementAndGet();
        context.setProperty(LOGGING_ID_PROPERTY, id);
        StringBuilder b = new StringBuilder();
        this.printRequestLine(b, "Server has received a request", id, context.getMethod(), context.getUriInfo().getRequestUri());
        this.printPrefixedHeaders(b, id, "> ", context.getHeaders());
        if (context.hasEntity() && ServerLoggingFilter.printEntity(this.verbosity, context.getMediaType())) {
            context.setEntityStream(this.logInboundEntity(b, context.getEntityStream(), MessageUtils.getCharset(context.getMediaType())));
        }
        this.log(b);
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        if (!this.logger.isLoggable(this.level)) {
            return;
        }
        Object requestId = requestContext.getProperty(LOGGING_ID_PROPERTY);
        long id = requestId != null ? ((Long)requestId).longValue() : this._id.incrementAndGet();
        StringBuilder b = new StringBuilder();
        this.printResponseLine(b, "Server responded with a response", id, responseContext.getStatus());
        this.printPrefixedHeaders(b, id, "< ", responseContext.getStringHeaders());
        if (responseContext.hasEntity() && ServerLoggingFilter.printEntity(this.verbosity, responseContext.getMediaType())) {
            LoggingInterceptor.LoggingStream stream = new LoggingInterceptor.LoggingStream(b, responseContext.getEntityStream());
            responseContext.setEntityStream(stream);
            requestContext.setProperty(ENTITY_LOGGER_PROPERTY, stream);
        } else {
            this.log(b);
        }
    }
}

