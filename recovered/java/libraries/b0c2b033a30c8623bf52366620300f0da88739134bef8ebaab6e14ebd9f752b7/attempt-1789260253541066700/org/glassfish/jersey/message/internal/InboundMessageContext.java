/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.internal;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.net.URI;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.function.Function;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.xml.transform.Source;
import org.glassfish.jersey.internal.LocalizationMessages;
import org.glassfish.jersey.internal.PropertiesDelegate;
import org.glassfish.jersey.internal.RuntimeDelegateDecorator;
import org.glassfish.jersey.message.MessageBodyWorkers;
import org.glassfish.jersey.message.internal.AcceptableLanguageTag;
import org.glassfish.jersey.message.internal.AcceptableMediaType;
import org.glassfish.jersey.message.internal.AcceptableToken;
import org.glassfish.jersey.message.internal.EntityInputStream;
import org.glassfish.jersey.message.internal.HeaderUtils;
import org.glassfish.jersey.message.internal.HeaderValueException;
import org.glassfish.jersey.message.internal.HttpHeaderReader;
import org.glassfish.jersey.message.internal.LanguageTag;
import org.glassfish.jersey.message.internal.LinkProvider;
import org.glassfish.jersey.message.internal.MatchingEntityTag;
import org.glassfish.jersey.message.internal.MediaTypes;
import org.glassfish.jersey.message.internal.ReaderWriter;

public abstract class InboundMessageContext {
    private static final InputStream EMPTY = new InputStream(){

        @Override
        public int read() throws IOException {
            return -1;
        }

        @Override
        public void mark(int readlimit) {
        }

        @Override
        public void reset() throws IOException {
        }

        @Override
        public boolean markSupported() {
            return true;
        }
    };
    private static final Annotation[] EMPTY_ANNOTATIONS = new Annotation[0];
    private static final List<AcceptableMediaType> WILDCARD_ACCEPTABLE_TYPE_SINGLETON_LIST = Collections.singletonList(MediaTypes.WILDCARD_ACCEPTABLE_TYPE);
    private final MultivaluedMap<String, String> headers = HeaderUtils.createInbound();
    private final EntityContent entityContent = new EntityContent();
    private final boolean translateNce;
    private MessageBodyWorkers workers;
    private final Configuration configuration;

    public InboundMessageContext(Configuration configuration) {
        this(configuration, false);
    }

    public InboundMessageContext(Configuration configuration, boolean translateNce) {
        this.translateNce = translateNce;
        this.configuration = configuration;
    }

    @Deprecated
    public InboundMessageContext() {
        this(null);
    }

    @Deprecated
    public InboundMessageContext(boolean translateNce) {
        this(null, translateNce);
    }

    public InboundMessageContext header(String name, Object value) {
        this.getHeaders().add(name, HeaderUtils.asString(value, this.configuration));
        return this;
    }

    public InboundMessageContext headers(String name, Object ... values) {
        this.getHeaders().addAll(name, HeaderUtils.asStringList(Arrays.asList(values), this.configuration));
        return this;
    }

    public InboundMessageContext headers(String name, Iterable<?> values) {
        this.getHeaders().addAll(name, this.iterableToList(values));
        return this;
    }

    public InboundMessageContext headers(MultivaluedMap<String, String> newHeaders) {
        for (Map.Entry header : newHeaders.entrySet()) {
            this.headers.addAll((String)header.getKey(), (List)header.getValue());
        }
        return this;
    }

    public InboundMessageContext headers(Map<String, List<String>> newHeaders) {
        for (Map.Entry<String, List<String>> header : newHeaders.entrySet()) {
            this.headers.addAll(header.getKey(), header.getValue());
        }
        return this;
    }

    public InboundMessageContext remove(String name) {
        this.getHeaders().remove(name);
        return this;
    }

    private List<String> iterableToList(Iterable<?> values) {
        LinkedList<String> linkedList = new LinkedList<String>();
        for (Object element : values) {
            linkedList.add(HeaderUtils.asString(element, this.configuration));
        }
        return linkedList;
    }

    public String getHeaderString(String name) {
        List values = (List)this.headers.get(name);
        if (values == null) {
            return null;
        }
        if (values.isEmpty()) {
            return "";
        }
        Iterator valuesIterator = values.iterator();
        StringBuilder buffer = new StringBuilder((String)valuesIterator.next());
        while (valuesIterator.hasNext()) {
            buffer.append(',').append((String)valuesIterator.next());
        }
        return buffer.toString();
    }

    private <T> T singleHeader(String name, Function<String, T> converter, boolean convertNull) {
        List values = (List)this.headers.get(name);
        if (values == null || values.isEmpty()) {
            return convertNull ? (T)converter.apply(null) : null;
        }
        if (values.size() > 1) {
            throw new HeaderValueException(LocalizationMessages.TOO_MANY_HEADER_VALUES(name, values.toString()), HeaderValueException.Context.INBOUND);
        }
        Object value = values.get(0);
        if (value == null) {
            return convertNull ? (T)converter.apply(null) : null;
        }
        try {
            return converter.apply(HeaderUtils.asString(value, this.configuration));
        }
        catch (ProcessingException ex) {
            throw InboundMessageContext.exception(name, value, ex);
        }
    }

    private static HeaderValueException exception(String headerName, Object headerValue, Exception e) {
        return new HeaderValueException(LocalizationMessages.UNABLE_TO_PARSE_HEADER_VALUE(headerName, headerValue), e, HeaderValueException.Context.INBOUND);
    }

    public MultivaluedMap<String, String> getHeaders() {
        return this.headers;
    }

    public Date getDate() {
        return this.singleHeader("Date", new Function<String, Date>(){

            @Override
            public Date apply(String input) {
                try {
                    return HttpHeaderReader.readDate(input);
                }
                catch (ParseException ex) {
                    throw new ProcessingException(ex);
                }
            }
        }, false);
    }

    public Set<MatchingEntityTag> getIfMatch() {
        String ifMatch = this.getHeaderString("If-Match");
        if (ifMatch == null || ifMatch.isEmpty()) {
            return null;
        }
        try {
            return HttpHeaderReader.readMatchingEntityTag(ifMatch);
        }
        catch (ParseException e) {
            throw InboundMessageContext.exception("If-Match", ifMatch, e);
        }
    }

    public Set<MatchingEntityTag> getIfNoneMatch() {
        String ifNoneMatch = this.getHeaderString("If-None-Match");
        if (ifNoneMatch == null || ifNoneMatch.isEmpty()) {
            return null;
        }
        try {
            return HttpHeaderReader.readMatchingEntityTag(ifNoneMatch);
        }
        catch (ParseException e) {
            throw InboundMessageContext.exception("If-None-Match", ifNoneMatch, e);
        }
    }

    public Locale getLanguage() {
        return this.singleHeader("Content-Language", new Function<String, Locale>(){

            @Override
            public Locale apply(String input) {
                try {
                    return new LanguageTag(input).getAsLocale();
                }
                catch (ParseException e) {
                    throw new ProcessingException(e);
                }
            }
        }, false);
    }

    public int getLength() {
        return this.singleHeader("Content-Length", new Function<String, Integer>(){

            @Override
            public Integer apply(String input) {
                try {
                    return input != null && !input.isEmpty() ? Integer.parseInt(input) : -1;
                }
                catch (NumberFormatException ex) {
                    throw new ProcessingException(ex);
                }
            }
        }, true);
    }

    public MediaType getMediaType() {
        return this.singleHeader("Content-Type", new Function<String, MediaType>(){

            @Override
            public MediaType apply(String input) {
                try {
                    return RuntimeDelegateDecorator.configured(InboundMessageContext.this.configuration).createHeaderDelegate(MediaType.class).fromString(input);
                }
                catch (IllegalArgumentException iae) {
                    throw new ProcessingException(iae);
                }
            }
        }, false);
    }

    public List<AcceptableMediaType> getQualifiedAcceptableMediaTypes() {
        String value = this.getHeaderString("Accept");
        if (value == null || value.isEmpty()) {
            return WILDCARD_ACCEPTABLE_TYPE_SINGLETON_LIST;
        }
        try {
            return Collections.unmodifiableList(HttpHeaderReader.readAcceptMediaType(value));
        }
        catch (ParseException e) {
            throw InboundMessageContext.exception("Accept", value, e);
        }
    }

    public List<AcceptableLanguageTag> getQualifiedAcceptableLanguages() {
        String value = this.getHeaderString("Accept-Language");
        if (value == null || value.isEmpty()) {
            return Collections.singletonList(new AcceptableLanguageTag("*", null));
        }
        try {
            return Collections.unmodifiableList(HttpHeaderReader.readAcceptLanguage(value));
        }
        catch (ParseException e) {
            throw InboundMessageContext.exception("Accept-Language", value, e);
        }
    }

    public List<AcceptableToken> getQualifiedAcceptCharset() {
        String acceptCharset = this.getHeaderString("Accept-Charset");
        try {
            if (acceptCharset == null || acceptCharset.isEmpty()) {
                return Collections.singletonList(new AcceptableToken("*"));
            }
            return HttpHeaderReader.readAcceptToken(acceptCharset);
        }
        catch (ParseException e) {
            throw InboundMessageContext.exception("Accept-Charset", acceptCharset, e);
        }
    }

    public List<AcceptableToken> getQualifiedAcceptEncoding() {
        String acceptEncoding = this.getHeaderString("Accept-Encoding");
        try {
            if (acceptEncoding == null || acceptEncoding.isEmpty()) {
                return Collections.singletonList(new AcceptableToken("*"));
            }
            return HttpHeaderReader.readAcceptToken(acceptEncoding);
        }
        catch (ParseException e) {
            throw InboundMessageContext.exception("Accept-Encoding", acceptEncoding, e);
        }
    }

    public Map<String, Cookie> getRequestCookies() {
        List cookies = (List)this.headers.get("Cookie");
        if (cookies == null || cookies.isEmpty()) {
            return Collections.emptyMap();
        }
        HashMap<String, Cookie> result = new HashMap<String, Cookie>();
        for (String cookie : cookies) {
            if (cookie == null) continue;
            result.putAll(HttpHeaderReader.readCookies(cookie));
        }
        return result;
    }

    public Set<String> getAllowedMethods() {
        String allowed = this.getHeaderString("Allow");
        if (allowed == null || allowed.isEmpty()) {
            return Collections.emptySet();
        }
        try {
            return new HashSet<String>(HttpHeaderReader.readStringList(allowed.toUpperCase(Locale.ROOT)));
        }
        catch (ParseException e) {
            throw InboundMessageContext.exception("Allow", allowed, e);
        }
    }

    public Map<String, NewCookie> getResponseCookies() {
        List cookies = (List)this.headers.get("Set-Cookie");
        if (cookies == null || cookies.isEmpty()) {
            return Collections.emptyMap();
        }
        HashMap<String, NewCookie> result = new HashMap<String, NewCookie>();
        for (String cookie : cookies) {
            if (cookie == null) continue;
            NewCookie newCookie = HttpHeaderReader.readNewCookie(cookie);
            String cookieName = newCookie.getName();
            if (result.containsKey(cookieName)) {
                result.put(cookieName, HeaderUtils.getPreferredCookie((NewCookie)result.get(cookieName), newCookie));
                continue;
            }
            result.put(cookieName, newCookie);
        }
        return result;
    }

    public EntityTag getEntityTag() {
        return this.singleHeader("ETag", new Function<String, EntityTag>(){

            @Override
            public EntityTag apply(String value) {
                return EntityTag.valueOf(value);
            }
        }, false);
    }

    public Date getLastModified() {
        return this.singleHeader("Last-Modified", new Function<String, Date>(){

            @Override
            public Date apply(String input) {
                try {
                    return HttpHeaderReader.readDate(input);
                }
                catch (ParseException e) {
                    throw new ProcessingException(e);
                }
            }
        }, false);
    }

    public URI getLocation() {
        return this.singleHeader("Location", new Function<String, URI>(){

            @Override
            public URI apply(String value) {
                try {
                    return URI.create(value);
                }
                catch (IllegalArgumentException ex) {
                    throw new ProcessingException(ex);
                }
            }
        }, false);
    }

    public Set<Link> getLinks() {
        List links = (List)this.headers.get("Link");
        if (links == null || links.isEmpty()) {
            return Collections.emptySet();
        }
        try {
            HashSet<Link> result = new HashSet<Link>(links.size());
            for (String link : links) {
                StringBuilder linkString = new StringBuilder();
                StringTokenizer st = new StringTokenizer(link, "<>,", true);
                boolean linkOpen = false;
                while (st.hasMoreTokens()) {
                    String n = st.nextToken();
                    if (n.equals("<")) {
                        linkOpen = true;
                    } else if (n.equals(">")) {
                        linkOpen = false;
                    } else if (!linkOpen && n.equals(",")) {
                        result.add(Link.valueOf(linkString.toString().trim()));
                        linkString = new StringBuilder();
                        continue;
                    }
                    linkString.append(n);
                }
                if (linkString.length() <= 0) continue;
                result.add(Link.valueOf(linkString.toString().trim()));
            }
            return result;
        }
        catch (IllegalArgumentException e) {
            throw InboundMessageContext.exception("Link", links, e);
        }
    }

    public boolean hasLink(String relation) {
        for (Link link : this.getLinks()) {
            List<String> relations = LinkProvider.getLinkRelations(link.getRel());
            if (relations == null || !relations.contains(relation)) continue;
            return true;
        }
        return false;
    }

    public Link getLink(String relation) {
        for (Link link : this.getLinks()) {
            List<String> relations = LinkProvider.getLinkRelations(link.getRel());
            if (relations == null || !relations.contains(relation)) continue;
            return link;
        }
        return null;
    }

    public Link.Builder getLinkBuilder(String relation) {
        Link link = this.getLink(relation);
        if (link == null) {
            return null;
        }
        return Link.fromLink(link);
    }

    public MessageBodyWorkers getWorkers() {
        return this.workers;
    }

    public void setWorkers(MessageBodyWorkers workers) {
        this.workers = workers;
    }

    public boolean hasEntity() {
        this.entityContent.ensureNotClosed();
        try {
            return !this.entityContent.isEmpty();
        }
        catch (IllegalStateException ex) {
            return false;
        }
    }

    public InputStream getEntityStream() {
        this.entityContent.ensureNotClosed();
        return this.entityContent.getWrappedStream();
    }

    public void setEntityStream(InputStream input) {
        this.entityContent.setContent(input, false);
    }

    public <T> T readEntity(Class<T> rawType, PropertiesDelegate propertiesDelegate) {
        return this.readEntity(rawType, rawType, EMPTY_ANNOTATIONS, propertiesDelegate);
    }

    public <T> T readEntity(Class<T> rawType, Annotation[] annotations, PropertiesDelegate propertiesDelegate) {
        return this.readEntity(rawType, rawType, annotations, propertiesDelegate);
    }

    public <T> T readEntity(Class<T> rawType, Type type, PropertiesDelegate propertiesDelegate) {
        return this.readEntity(rawType, type, EMPTY_ANNOTATIONS, propertiesDelegate);
    }

    public <T> T readEntity(Class<T> rawType, Type type, Annotation[] annotations, PropertiesDelegate propertiesDelegate) {
        boolean buffered = this.entityContent.isBuffered();
        if (buffered) {
            this.entityContent.reset();
        }
        this.entityContent.ensureNotClosed();
        if (this.workers == null) {
            return null;
        }
        MediaType mediaType = this.getMediaType();
        mediaType = mediaType == null ? MediaType.APPLICATION_OCTET_STREAM_TYPE : mediaType;
        boolean shouldClose = !buffered;
        try {
            Object t = this.workers.readFrom(rawType, type, annotations, mediaType, this.headers, propertiesDelegate, this.entityContent.getWrappedStream(), this.entityContent.hasContent() ? this.getReaderInterceptors() : Collections.emptyList(), this.translateNce);
            shouldClose = shouldClose && !(t instanceof Closeable) && !(t instanceof Source);
            Object object = t;
            return (T)object;
        }
        catch (IOException ex) {
            throw new ProcessingException(LocalizationMessages.ERROR_READING_ENTITY_FROM_INPUT_STREAM(), ex);
        }
        finally {
            if (shouldClose) {
                ReaderWriter.safelyClose(this.entityContent);
            }
        }
    }

    public boolean bufferEntity() throws ProcessingException {
        this.entityContent.ensureNotClosed();
        try {
            if (this.entityContent.isBuffered() || !this.entityContent.hasContent()) {
                return true;
            }
            InputStream entityStream = this.entityContent.getWrappedStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                ReaderWriter.writeTo(entityStream, baos);
            }
            finally {
                ReaderWriter.safelyClose(entityStream);
            }
            this.entityContent.setContent(new ByteArrayInputStream(baos.toByteArray()), true);
            return true;
        }
        catch (IOException ex) {
            throw new ProcessingException(LocalizationMessages.MESSAGE_CONTENT_BUFFERING_FAILED(), ex);
        }
    }

    public void close() {
        this.entityContent.close(true);
    }

    protected abstract Iterable<ReaderInterceptor> getReaderInterceptors();

    public Configuration getConfiguration() {
        return this.configuration;
    }

    private static class EntityContent
    extends EntityInputStream {
        private boolean buffered;

        EntityContent() {
            super(EMPTY);
        }

        void setContent(InputStream content, boolean buffered) {
            this.buffered = buffered;
            this.setWrappedStream(content);
        }

        boolean hasContent() {
            return this.getWrappedStream() != EMPTY;
        }

        boolean isBuffered() {
            return this.buffered;
        }

        @Override
        public void close() {
            this.close(false);
        }

        void close(boolean force) {
            if (this.buffered && !force) {
                return;
            }
            try {
                super.close();
            }
            finally {
                this.buffered = false;
                this.setWrappedStream(null);
            }
        }
    }
}

