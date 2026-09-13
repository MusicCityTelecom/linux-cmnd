/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.client;

import java.lang.annotation.Annotation;
import java.net.URI;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientResponse;
import org.glassfish.jersey.internal.util.Producer;
import org.glassfish.jersey.process.internal.RequestContext;
import org.glassfish.jersey.process.internal.RequestScope;

class InboundJaxrsResponse
extends Response {
    private final ClientResponse context;
    private final RequestScope scope;
    private final RequestContext requestContext;

    public InboundJaxrsResponse(ClientResponse context, RequestScope scope) {
        this.context = context;
        this.scope = scope;
        this.requestContext = this.scope != null ? scope.referenceCurrent() : null;
    }

    @Override
    public int getStatus() {
        return this.context.getStatus();
    }

    @Override
    public Response.StatusType getStatusInfo() {
        return this.context.getStatusInfo();
    }

    @Override
    public Object getEntity() throws IllegalStateException {
        return this.context.getEntity();
    }

    @Override
    public <T> T readEntity(final Class<T> entityType) throws ProcessingException, IllegalStateException {
        return this.runInScopeIfPossible(new Producer<T>(){

            @Override
            public T call() {
                return InboundJaxrsResponse.this.context.readEntity(entityType);
            }
        });
    }

    @Override
    public <T> T readEntity(final GenericType<T> entityType) throws ProcessingException, IllegalStateException {
        return this.runInScopeIfPossible(new Producer<T>(){

            @Override
            public T call() {
                return InboundJaxrsResponse.this.context.readEntity(entityType);
            }
        });
    }

    @Override
    public <T> T readEntity(final Class<T> entityType, final Annotation[] annotations) throws ProcessingException, IllegalStateException {
        return this.runInScopeIfPossible(new Producer<T>(){

            @Override
            public T call() {
                return InboundJaxrsResponse.this.context.readEntity(entityType, annotations);
            }
        });
    }

    @Override
    public <T> T readEntity(final GenericType<T> entityType, final Annotation[] annotations) throws ProcessingException, IllegalStateException {
        return this.runInScopeIfPossible(new Producer<T>(){

            @Override
            public T call() {
                return InboundJaxrsResponse.this.context.readEntity(entityType, annotations);
            }
        });
    }

    @Override
    public boolean hasEntity() {
        return this.context.hasEntity();
    }

    @Override
    public boolean bufferEntity() throws ProcessingException {
        return this.context.bufferEntity();
    }

    @Override
    public void close() throws ProcessingException {
        try {
            this.context.close();
        }
        finally {
            if (this.requestContext != null) {
                this.requestContext.release();
            }
        }
    }

    @Override
    public String getHeaderString(String name) {
        return this.context.getHeaderString(name);
    }

    @Override
    public MultivaluedMap<String, String> getStringHeaders() {
        return this.context.getHeaders();
    }

    @Override
    public MediaType getMediaType() {
        return this.context.getMediaType();
    }

    @Override
    public Locale getLanguage() {
        return this.context.getLanguage();
    }

    @Override
    public int getLength() {
        return this.context.getLength();
    }

    @Override
    public Map<String, NewCookie> getCookies() {
        return this.context.getResponseCookies();
    }

    @Override
    public EntityTag getEntityTag() {
        return this.context.getEntityTag();
    }

    @Override
    public Date getDate() {
        return this.context.getDate();
    }

    @Override
    public Date getLastModified() {
        return this.context.getLastModified();
    }

    @Override
    public Set<String> getAllowedMethods() {
        return this.context.getAllowedMethods();
    }

    @Override
    public URI getLocation() {
        return this.context.getLocation();
    }

    @Override
    public Set<Link> getLinks() {
        return this.context.getLinks();
    }

    @Override
    public boolean hasLink(String relation) {
        return this.context.hasLink(relation);
    }

    @Override
    public Link getLink(String relation) {
        return this.context.getLink(relation);
    }

    @Override
    public Link.Builder getLinkBuilder(String relation) {
        return this.context.getLinkBuilder(relation);
    }

    @Override
    public MultivaluedMap<String, Object> getMetadata() {
        MultivaluedMap<String, Object> headers = this.context.getHeaders();
        return headers;
    }

    public String toString() {
        return "InboundJaxrsResponse{context=" + this.context + "}";
    }

    private <T> T runInScopeIfPossible(Producer<T> producer) {
        if (this.scope != null && this.requestContext != null) {
            return this.scope.runInScope(this.requestContext, producer);
        }
        return producer.call();
    }
}

