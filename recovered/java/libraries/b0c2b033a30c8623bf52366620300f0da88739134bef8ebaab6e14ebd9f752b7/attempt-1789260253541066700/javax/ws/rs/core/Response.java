/*
 * Decompiled with CFR 0.152.
 */
package javax.ws.rs.core;

import java.lang.annotation.Annotation;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Variant;
import javax.ws.rs.ext.RuntimeDelegate;

public abstract class Response
implements AutoCloseable {
    protected Response() {
    }

    public abstract int getStatus();

    public abstract StatusType getStatusInfo();

    public abstract Object getEntity();

    public abstract <T> T readEntity(Class<T> var1);

    public abstract <T> T readEntity(GenericType<T> var1);

    public abstract <T> T readEntity(Class<T> var1, Annotation[] var2);

    public abstract <T> T readEntity(GenericType<T> var1, Annotation[] var2);

    public abstract boolean hasEntity();

    public abstract boolean bufferEntity();

    @Override
    public abstract void close();

    public abstract MediaType getMediaType();

    public abstract Locale getLanguage();

    public abstract int getLength();

    public abstract Set<String> getAllowedMethods();

    public abstract Map<String, NewCookie> getCookies();

    public abstract EntityTag getEntityTag();

    public abstract Date getDate();

    public abstract Date getLastModified();

    public abstract URI getLocation();

    public abstract Set<Link> getLinks();

    public abstract boolean hasLink(String var1);

    public abstract Link getLink(String var1);

    public abstract Link.Builder getLinkBuilder(String var1);

    public abstract MultivaluedMap<String, Object> getMetadata();

    public MultivaluedMap<String, Object> getHeaders() {
        return this.getMetadata();
    }

    public abstract MultivaluedMap<String, String> getStringHeaders();

    public abstract String getHeaderString(String var1);

    public static ResponseBuilder fromResponse(Response response) {
        ResponseBuilder b = Response.status(response.getStatus());
        if (response.hasEntity()) {
            b.entity(response.getEntity());
        }
        for (String headerName : response.getHeaders().keySet()) {
            List headerValues = (List)response.getHeaders().get(headerName);
            for (Object headerValue : headerValues) {
                b.header(headerName, headerValue);
            }
        }
        return b;
    }

    public static ResponseBuilder status(StatusType status) {
        return ResponseBuilder.newInstance().status(status);
    }

    public static ResponseBuilder status(Status status) {
        return Response.status((StatusType)status);
    }

    public static ResponseBuilder status(int status) {
        return ResponseBuilder.newInstance().status(status);
    }

    public static ResponseBuilder status(int status, String reasonPhrase) {
        return ResponseBuilder.newInstance().status(status, reasonPhrase);
    }

    public static ResponseBuilder ok() {
        return Response.status(Status.OK);
    }

    public static ResponseBuilder ok(Object entity) {
        ResponseBuilder b = Response.ok();
        b.entity(entity);
        return b;
    }

    public static ResponseBuilder ok(Object entity, MediaType type) {
        return Response.ok().entity(entity).type(type);
    }

    public static ResponseBuilder ok(Object entity, String type) {
        return Response.ok().entity(entity).type(type);
    }

    public static ResponseBuilder ok(Object entity, Variant variant) {
        return Response.ok().entity(entity).variant(variant);
    }

    public static ResponseBuilder serverError() {
        return Response.status(Status.INTERNAL_SERVER_ERROR);
    }

    public static ResponseBuilder created(URI location) {
        return Response.status(Status.CREATED).location(location);
    }

    public static ResponseBuilder accepted() {
        return Response.status(Status.ACCEPTED);
    }

    public static ResponseBuilder accepted(Object entity) {
        return Response.accepted().entity(entity);
    }

    public static ResponseBuilder noContent() {
        return Response.status(Status.NO_CONTENT);
    }

    public static ResponseBuilder notModified() {
        return Response.status(Status.NOT_MODIFIED);
    }

    public static ResponseBuilder notModified(EntityTag tag) {
        return Response.notModified().tag(tag);
    }

    public static ResponseBuilder notModified(String tag) {
        return Response.notModified().tag(tag);
    }

    public static ResponseBuilder seeOther(URI location) {
        return Response.status(Status.SEE_OTHER).location(location);
    }

    public static ResponseBuilder temporaryRedirect(URI location) {
        return Response.status(Status.TEMPORARY_REDIRECT).location(location);
    }

    public static ResponseBuilder notAcceptable(List<Variant> variants) {
        return Response.status(Status.NOT_ACCEPTABLE).variants(variants);
    }

    public static enum Status implements StatusType
    {
        OK(200, "OK"),
        CREATED(201, "Created"),
        ACCEPTED(202, "Accepted"),
        NO_CONTENT(204, "No Content"),
        RESET_CONTENT(205, "Reset Content"),
        PARTIAL_CONTENT(206, "Partial Content"),
        MOVED_PERMANENTLY(301, "Moved Permanently"),
        FOUND(302, "Found"),
        SEE_OTHER(303, "See Other"),
        NOT_MODIFIED(304, "Not Modified"),
        USE_PROXY(305, "Use Proxy"),
        TEMPORARY_REDIRECT(307, "Temporary Redirect"),
        BAD_REQUEST(400, "Bad Request"),
        UNAUTHORIZED(401, "Unauthorized"),
        PAYMENT_REQUIRED(402, "Payment Required"),
        FORBIDDEN(403, "Forbidden"),
        NOT_FOUND(404, "Not Found"),
        METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
        NOT_ACCEPTABLE(406, "Not Acceptable"),
        PROXY_AUTHENTICATION_REQUIRED(407, "Proxy Authentication Required"),
        REQUEST_TIMEOUT(408, "Request Timeout"),
        CONFLICT(409, "Conflict"),
        GONE(410, "Gone"),
        LENGTH_REQUIRED(411, "Length Required"),
        PRECONDITION_FAILED(412, "Precondition Failed"),
        REQUEST_ENTITY_TOO_LARGE(413, "Request Entity Too Large"),
        REQUEST_URI_TOO_LONG(414, "Request-URI Too Long"),
        UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type"),
        REQUESTED_RANGE_NOT_SATISFIABLE(416, "Requested Range Not Satisfiable"),
        EXPECTATION_FAILED(417, "Expectation Failed"),
        PRECONDITION_REQUIRED(428, "Precondition Required"),
        TOO_MANY_REQUESTS(429, "Too Many Requests"),
        REQUEST_HEADER_FIELDS_TOO_LARGE(431, "Request Header Fields Too Large"),
        INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
        NOT_IMPLEMENTED(501, "Not Implemented"),
        BAD_GATEWAY(502, "Bad Gateway"),
        SERVICE_UNAVAILABLE(503, "Service Unavailable"),
        GATEWAY_TIMEOUT(504, "Gateway Timeout"),
        HTTP_VERSION_NOT_SUPPORTED(505, "HTTP Version Not Supported"),
        NETWORK_AUTHENTICATION_REQUIRED(511, "Network Authentication Required");

        private final int code;
        private final String reason;
        private final Family family;

        private Status(int statusCode, String reasonPhrase) {
            this.code = statusCode;
            this.reason = reasonPhrase;
            this.family = Family.familyOf(statusCode);
        }

        @Override
        public Family getFamily() {
            return this.family;
        }

        @Override
        public int getStatusCode() {
            return this.code;
        }

        @Override
        public String getReasonPhrase() {
            return this.toString();
        }

        public String toString() {
            return this.reason;
        }

        public static Status fromStatusCode(int statusCode) {
            for (Status s : Status.values()) {
                if (s.code != statusCode) continue;
                return s;
            }
            return null;
        }

        public static enum Family {
            INFORMATIONAL,
            SUCCESSFUL,
            REDIRECTION,
            CLIENT_ERROR,
            SERVER_ERROR,
            OTHER;


            public static Family familyOf(int statusCode) {
                switch (statusCode / 100) {
                    case 1: {
                        return INFORMATIONAL;
                    }
                    case 2: {
                        return SUCCESSFUL;
                    }
                    case 3: {
                        return REDIRECTION;
                    }
                    case 4: {
                        return CLIENT_ERROR;
                    }
                    case 5: {
                        return SERVER_ERROR;
                    }
                }
                return OTHER;
            }
        }
    }

    public static interface StatusType {
        public int getStatusCode();

        public Status.Family getFamily();

        public String getReasonPhrase();

        default public Status toEnum() {
            return Status.fromStatusCode(this.getStatusCode());
        }
    }

    public static abstract class ResponseBuilder {
        protected ResponseBuilder() {
        }

        protected static ResponseBuilder newInstance() {
            return RuntimeDelegate.getInstance().createResponseBuilder();
        }

        public abstract Response build();

        public abstract ResponseBuilder clone();

        public abstract ResponseBuilder status(int var1);

        public abstract ResponseBuilder status(int var1, String var2);

        public ResponseBuilder status(StatusType status) {
            if (status == null) {
                throw new IllegalArgumentException();
            }
            return this.status(status.getStatusCode(), status.getReasonPhrase());
        }

        public ResponseBuilder status(Status status) {
            return this.status((StatusType)status);
        }

        public abstract ResponseBuilder entity(Object var1);

        public abstract ResponseBuilder entity(Object var1, Annotation[] var2);

        public abstract ResponseBuilder allow(String ... var1);

        public abstract ResponseBuilder allow(Set<String> var1);

        public abstract ResponseBuilder cacheControl(CacheControl var1);

        public abstract ResponseBuilder encoding(String var1);

        public abstract ResponseBuilder header(String var1, Object var2);

        public abstract ResponseBuilder replaceAll(MultivaluedMap<String, Object> var1);

        public abstract ResponseBuilder language(String var1);

        public abstract ResponseBuilder language(Locale var1);

        public abstract ResponseBuilder type(MediaType var1);

        public abstract ResponseBuilder type(String var1);

        public abstract ResponseBuilder variant(Variant var1);

        public abstract ResponseBuilder contentLocation(URI var1);

        public abstract ResponseBuilder cookie(NewCookie ... var1);

        public abstract ResponseBuilder expires(Date var1);

        public abstract ResponseBuilder lastModified(Date var1);

        public abstract ResponseBuilder location(URI var1);

        public abstract ResponseBuilder tag(EntityTag var1);

        public abstract ResponseBuilder tag(String var1);

        public abstract ResponseBuilder variants(Variant ... var1);

        public abstract ResponseBuilder variants(List<Variant> var1);

        public abstract ResponseBuilder links(Link ... var1);

        public abstract ResponseBuilder link(URI var1, String var2);

        public abstract ResponseBuilder link(String var1, String var2);
    }
}

