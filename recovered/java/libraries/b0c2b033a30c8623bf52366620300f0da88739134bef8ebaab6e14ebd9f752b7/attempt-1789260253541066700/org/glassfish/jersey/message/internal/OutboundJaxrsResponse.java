/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.internal;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.net.URI;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Variant;
import org.glassfish.jersey.internal.LocalizationMessages;
import org.glassfish.jersey.message.internal.OutboundMessageContext;
import org.glassfish.jersey.message.internal.Statuses;

public class OutboundJaxrsResponse
extends Response {
    private final OutboundMessageContext context;
    private final Response.StatusType status;
    private boolean closed = false;
    private boolean buffered = false;

    public static OutboundJaxrsResponse from(Response response, Configuration configuration) {
        if (response instanceof OutboundJaxrsResponse) {
            ((OutboundJaxrsResponse)response).context.setConfiguration(configuration);
            return (OutboundJaxrsResponse)response;
        }
        Response.StatusType status = response.getStatusInfo();
        OutboundMessageContext context = new OutboundMessageContext(configuration);
        context.getHeaders().putAll(response.getMetadata());
        context.setEntity(response.getEntity());
        return new OutboundJaxrsResponse(status, context);
    }

    @Deprecated
    public static OutboundJaxrsResponse from(Response response) {
        return OutboundJaxrsResponse.from(response, null);
    }

    public OutboundJaxrsResponse(Response.StatusType status, OutboundMessageContext context) {
        this.status = status;
        this.context = context;
    }

    public OutboundMessageContext getContext() {
        return this.context;
    }

    @Override
    public int getStatus() {
        return this.status.getStatusCode();
    }

    @Override
    public Response.StatusType getStatusInfo() {
        return this.status;
    }

    @Override
    public Object getEntity() {
        if (this.closed) {
            throw new IllegalStateException(LocalizationMessages.RESPONSE_CLOSED());
        }
        return this.context.getEntity();
    }

    @Override
    public <T> T readEntity(Class<T> type) throws ProcessingException {
        throw new IllegalStateException(LocalizationMessages.NOT_SUPPORTED_ON_OUTBOUND_MESSAGE());
    }

    @Override
    public <T> T readEntity(GenericType<T> entityType) throws ProcessingException {
        throw new IllegalStateException(LocalizationMessages.NOT_SUPPORTED_ON_OUTBOUND_MESSAGE());
    }

    @Override
    public <T> T readEntity(Class<T> type, Annotation[] annotations) throws ProcessingException {
        throw new IllegalStateException(LocalizationMessages.NOT_SUPPORTED_ON_OUTBOUND_MESSAGE());
    }

    @Override
    public <T> T readEntity(GenericType<T> entityType, Annotation[] annotations) throws ProcessingException {
        throw new IllegalStateException(LocalizationMessages.NOT_SUPPORTED_ON_OUTBOUND_MESSAGE());
    }

    @Override
    public boolean hasEntity() {
        if (this.closed) {
            throw new IllegalStateException(LocalizationMessages.RESPONSE_CLOSED());
        }
        return this.context.hasEntity();
    }

    @Override
    public boolean bufferEntity() throws ProcessingException {
        if (this.closed) {
            throw new IllegalStateException(LocalizationMessages.RESPONSE_CLOSED());
        }
        if (!this.context.hasEntity() || !InputStream.class.isAssignableFrom(this.context.getEntityClass())) {
            return false;
        }
        if (this.buffered) {
            return true;
        }
        InputStream in = (InputStream)InputStream.class.cast(this.context.getEntity());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        try {
            int len;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
        }
        catch (IOException ex) {
            throw new ProcessingException(ex);
        }
        finally {
            try {
                in.close();
            }
            catch (IOException ex) {
                throw new ProcessingException(ex);
            }
        }
        this.context.setEntity(new ByteArrayInputStream(out.toByteArray()));
        this.buffered = true;
        return true;
    }

    @Override
    public void close() throws ProcessingException {
        this.closed = true;
        this.context.close();
        if (this.buffered) {
            this.context.setEntity(null);
        } else if (this.context.hasEntity() && InputStream.class.isAssignableFrom(this.context.getEntityClass())) {
            try {
                ((InputStream)InputStream.class.cast(this.context.getEntity())).close();
            }
            catch (IOException ex) {
                throw new ProcessingException(ex);
            }
        }
    }

    @Override
    public MultivaluedMap<String, String> getStringHeaders() {
        return this.context.getStringHeaders();
    }

    @Override
    public String getHeaderString(String name) {
        return this.context.getHeaderString(name);
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
        return this.context.getHeaders();
    }

    public String toString() {
        return "OutboundJaxrsResponse{status=" + this.status.getStatusCode() + ", reason=" + this.status.getReasonPhrase() + ", hasEntity=" + this.context.hasEntity() + ", closed=" + this.closed + ", buffered=" + this.buffered + "}";
    }

    public static class Builder
    extends Response.ResponseBuilder {
        private Response.StatusType status;
        private final OutboundMessageContext context;
        private static final InheritableThreadLocal<URI> baseUriThreadLocal = new InheritableThreadLocal();

        public static void setBaseUri(URI baseUri) {
            baseUriThreadLocal.set(baseUri);
        }

        private static URI getBaseUri() {
            return (URI)baseUriThreadLocal.get();
        }

        public static void clearBaseUri() {
            baseUriThreadLocal.remove();
        }

        public Builder(OutboundMessageContext context) {
            this.context = context;
        }

        @Override
        public Response build() {
            Response.StatusType st = this.status;
            if (st == null) {
                st = this.context.hasEntity() ? Response.Status.OK : Response.Status.NO_CONTENT;
            }
            return new OutboundJaxrsResponse(st, new OutboundMessageContext(this.context));
        }

        @Override
        public Response.ResponseBuilder clone() {
            return new Builder(new OutboundMessageContext(this.context)).status(this.status);
        }

        @Override
        public Response.ResponseBuilder status(Response.StatusType status) {
            if (status == null) {
                throw new IllegalArgumentException("Response status must not be 'null'");
            }
            this.status = status;
            return this;
        }

        @Override
        public Response.ResponseBuilder status(final int status, final String reasonPhrase) {
            if (status < 100 || status > 599) {
                throw new IllegalArgumentException("Response status must not be less than '100' or greater than '599'");
            }
            final Response.Status.Family family = Response.Status.Family.familyOf(status);
            this.status = new Response.StatusType(){

                @Override
                public int getStatusCode() {
                    return status;
                }

                @Override
                public Response.Status.Family getFamily() {
                    return family;
                }

                @Override
                public String getReasonPhrase() {
                    return reasonPhrase;
                }
            };
            return this;
        }

        @Override
        public Response.ResponseBuilder status(int code) {
            this.status = Statuses.from(code);
            return this;
        }

        @Override
        public Response.ResponseBuilder entity(Object entity) {
            this.context.setEntity(entity);
            return this;
        }

        @Override
        public Response.ResponseBuilder entity(Object entity, Annotation[] annotations) {
            this.context.setEntity(entity, annotations);
            return this;
        }

        @Override
        public Response.ResponseBuilder type(MediaType type) {
            this.context.setMediaType(type);
            return this;
        }

        @Override
        public Response.ResponseBuilder type(String type) {
            return this.type(type == null ? null : MediaType.valueOf(type));
        }

        @Override
        public Response.ResponseBuilder variant(Variant variant) {
            if (variant == null) {
                this.type((MediaType)null);
                this.language((String)null);
                this.encoding(null);
                return this;
            }
            this.type(variant.getMediaType());
            this.language(variant.getLanguage());
            this.encoding(variant.getEncoding());
            return this;
        }

        @Override
        public Response.ResponseBuilder variants(List<Variant> variants) {
            if (variants == null) {
                this.header("Vary", null);
                return this;
            }
            if (variants.isEmpty()) {
                return this;
            }
            MediaType accept = variants.get(0).getMediaType();
            boolean vAccept = false;
            Locale acceptLanguage = variants.get(0).getLanguage();
            boolean vAcceptLanguage = false;
            String acceptEncoding = variants.get(0).getEncoding();
            boolean vAcceptEncoding = false;
            for (Variant v : variants) {
                vAccept |= !vAccept && this.vary(v.getMediaType(), accept);
                vAcceptLanguage |= !vAcceptLanguage && this.vary(v.getLanguage(), acceptLanguage);
                vAcceptEncoding |= !vAcceptEncoding && this.vary(v.getEncoding(), acceptEncoding);
            }
            StringBuilder vary = new StringBuilder();
            this.append(vary, vAccept, "Accept");
            this.append(vary, vAcceptLanguage, "Accept-Language");
            this.append(vary, vAcceptEncoding, "Accept-Encoding");
            if (vary.length() > 0) {
                this.header("Vary", vary.toString());
            }
            return this;
        }

        private boolean vary(MediaType v, MediaType vary) {
            return v != null && !v.equals(vary);
        }

        private boolean vary(Locale v, Locale vary) {
            return v != null && !v.equals(vary);
        }

        private boolean vary(String v, String vary) {
            return v != null && !v.equalsIgnoreCase(vary);
        }

        private void append(StringBuilder sb, boolean v, String s) {
            if (v) {
                if (sb.length() > 0) {
                    sb.append(',');
                }
                sb.append(s);
            }
        }

        @Override
        public Response.ResponseBuilder language(String language) {
            this.headerSingle("Content-Language", language);
            return this;
        }

        @Override
        public Response.ResponseBuilder language(Locale language) {
            this.headerSingle("Content-Language", language);
            return this;
        }

        @Override
        public Response.ResponseBuilder location(URI location) {
            URI baseUri;
            URI locationUri = location;
            if (location != null && !location.isAbsolute() && (baseUri = Builder.getBaseUri()) != null) {
                locationUri = baseUri.resolve(location);
            }
            this.headerSingle("Location", locationUri);
            return this;
        }

        @Override
        public Response.ResponseBuilder contentLocation(URI location) {
            this.headerSingle("Content-Location", location);
            return this;
        }

        @Override
        public Response.ResponseBuilder encoding(String encoding) {
            this.headerSingle("Content-Encoding", encoding);
            return this;
        }

        @Override
        public Response.ResponseBuilder tag(EntityTag tag) {
            this.headerSingle("ETag", tag);
            return this;
        }

        @Override
        public Response.ResponseBuilder tag(String tag) {
            return this.tag(tag == null ? null : new EntityTag(tag));
        }

        @Override
        public Response.ResponseBuilder lastModified(Date lastModified) {
            this.headerSingle("Last-Modified", lastModified);
            return this;
        }

        @Override
        public Response.ResponseBuilder cacheControl(CacheControl cacheControl) {
            this.headerSingle("Cache-Control", cacheControl);
            return this;
        }

        @Override
        public Response.ResponseBuilder expires(Date expires) {
            this.headerSingle("Expires", expires);
            return this;
        }

        @Override
        public Response.ResponseBuilder cookie(NewCookie ... cookies) {
            if (cookies != null) {
                for (NewCookie cookie : cookies) {
                    this.header("Set-Cookie", cookie);
                }
            } else {
                this.header("Set-Cookie", null);
            }
            return this;
        }

        @Override
        public Response.ResponseBuilder header(String name, Object value) {
            return this.header(name, value, false);
        }

        private Response.ResponseBuilder headerSingle(String name, Object value) {
            return this.header(name, value, true);
        }

        private Response.ResponseBuilder header(String name, Object value, boolean single) {
            if (value != null) {
                if (single) {
                    this.context.getHeaders().putSingle(name, value);
                } else {
                    this.context.getHeaders().add(name, value);
                }
            } else {
                this.context.getHeaders().remove(name);
            }
            return this;
        }

        @Override
        public Response.ResponseBuilder variants(Variant ... variants) {
            return this.variants(Arrays.asList(variants));
        }

        @Override
        public Response.ResponseBuilder links(Link ... links) {
            if (links != null) {
                for (Link link : links) {
                    this.header("Link", link);
                }
            } else {
                this.header("Link", null);
            }
            return this;
        }

        @Override
        public Response.ResponseBuilder link(URI uri, String rel) {
            this.header("Link", Link.fromUri(uri).rel(rel).build(new Object[0]));
            return this;
        }

        @Override
        public Response.ResponseBuilder link(String uri, String rel) {
            this.header("Link", Link.fromUri(uri).rel(rel).build(new Object[0]));
            return this;
        }

        @Override
        public Response.ResponseBuilder allow(String ... methods) {
            if (methods == null || methods.length == 1 && methods[0] == null) {
                return this.allow((Set<String>)null);
            }
            return this.allow(new HashSet<String>(Arrays.asList(methods)));
        }

        @Override
        public Response.ResponseBuilder allow(Set<String> methods) {
            if (methods == null) {
                return this.header("Allow", null, true);
            }
            StringBuilder allow = new StringBuilder();
            for (String m : methods) {
                this.append(allow, true, m);
            }
            return this.header("Allow", allow, true);
        }

        @Override
        public Response.ResponseBuilder replaceAll(MultivaluedMap<String, Object> headers) {
            this.context.replaceHeaders(headers);
            return this;
        }
    }
}

