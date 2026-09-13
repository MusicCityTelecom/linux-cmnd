/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.internal;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.net.URI;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NewCookie;
import org.glassfish.jersey.CommonProperties;
import org.glassfish.jersey.internal.LocalizationMessages;
import org.glassfish.jersey.internal.RuntimeDelegateDecorator;
import org.glassfish.jersey.internal.util.ReflectionHelper;
import org.glassfish.jersey.message.internal.AcceptableLanguageTag;
import org.glassfish.jersey.message.internal.AcceptableMediaType;
import org.glassfish.jersey.message.internal.CommittingOutputStream;
import org.glassfish.jersey.message.internal.HeaderUtils;
import org.glassfish.jersey.message.internal.HeaderValueException;
import org.glassfish.jersey.message.internal.HttpHeaderReader;
import org.glassfish.jersey.message.internal.LanguageTag;
import org.glassfish.jersey.message.internal.LinkProvider;
import org.glassfish.jersey.message.internal.MediaTypes;

public class OutboundMessageContext {
    private static final Annotation[] EMPTY_ANNOTATIONS = new Annotation[0];
    private static final List<MediaType> WILDCARD_ACCEPTABLE_TYPE_SINGLETON_LIST = Collections.singletonList(MediaTypes.WILDCARD_ACCEPTABLE_TYPE);
    private final MultivaluedMap<String, Object> headers;
    private final CommittingOutputStream committingOutputStream;
    private Configuration configuration;
    private Object entity;
    private GenericType<?> entityType;
    private Annotation[] entityAnnotations = EMPTY_ANNOTATIONS;
    private OutputStream entityStream;

    public OutboundMessageContext(Configuration configuration) {
        this.configuration = configuration;
        this.headers = HeaderUtils.createOutbound();
        this.committingOutputStream = new CommittingOutputStream();
        this.entityStream = this.committingOutputStream;
    }

    public OutboundMessageContext(OutboundMessageContext original) {
        this.headers = HeaderUtils.createOutbound();
        this.headers.putAll(original.headers);
        this.committingOutputStream = new CommittingOutputStream();
        this.entityStream = this.committingOutputStream;
        this.entity = original.entity;
        this.entityType = original.entityType;
        this.entityAnnotations = original.entityAnnotations;
        this.configuration = original.configuration;
    }

    @Deprecated
    public OutboundMessageContext() {
        this((Configuration)null);
    }

    public void replaceHeaders(MultivaluedMap<String, Object> headers) {
        this.getHeaders().clear();
        if (headers != null) {
            this.getHeaders().putAll(headers);
        }
    }

    public MultivaluedMap<String, String> getStringHeaders() {
        return HeaderUtils.asStringHeaders(this.headers, this.configuration);
    }

    public String getHeaderString(String name) {
        return HeaderUtils.asHeaderString((List)this.headers.get(name), RuntimeDelegateDecorator.configured(this.configuration));
    }

    private <T> T singleHeader(String name, Class<T> valueType, Function<String, T> converter, boolean convertNull) {
        List values = (List)this.headers.get(name);
        if (values == null || values.isEmpty()) {
            return convertNull ? (T)converter.apply(null) : null;
        }
        if (values.size() > 1) {
            throw new HeaderValueException(LocalizationMessages.TOO_MANY_HEADER_VALUES(name, values.toString()), HeaderValueException.Context.OUTBOUND);
        }
        Object value = values.get(0);
        if (value == null) {
            return convertNull ? (T)converter.apply(null) : null;
        }
        if (valueType.isInstance(value)) {
            return valueType.cast(value);
        }
        try {
            return converter.apply(HeaderUtils.asString(value, null));
        }
        catch (ProcessingException ex) {
            throw OutboundMessageContext.exception(name, value, ex);
        }
    }

    private static HeaderValueException exception(String headerName, Object headerValue, Exception e) {
        return new HeaderValueException(LocalizationMessages.UNABLE_TO_PARSE_HEADER_VALUE(headerName, headerValue), e, HeaderValueException.Context.OUTBOUND);
    }

    public MultivaluedMap<String, Object> getHeaders() {
        return this.headers;
    }

    public Date getDate() {
        return this.singleHeader("Date", Date.class, input -> {
            try {
                return HttpHeaderReader.readDate(input);
            }
            catch (ParseException e) {
                throw new ProcessingException(e);
            }
        }, false);
    }

    public Locale getLanguage() {
        return this.singleHeader("Content-Language", Locale.class, input -> {
            try {
                return new LanguageTag((String)input).getAsLocale();
            }
            catch (ParseException e) {
                throw new ProcessingException(e);
            }
        }, false);
    }

    public MediaType getMediaType() {
        return this.singleHeader("Content-Type", MediaType.class, RuntimeDelegateDecorator.configured(this.configuration).createHeaderDelegate(MediaType.class)::fromString, false);
    }

    public List<MediaType> getAcceptableMediaTypes() {
        List values = (List)this.headers.get("Accept");
        if (values == null || values.isEmpty()) {
            return WILDCARD_ACCEPTABLE_TYPE_SINGLETON_LIST;
        }
        ArrayList<AcceptableMediaType> result = new ArrayList<AcceptableMediaType>(values.size());
        boolean conversionApplied = false;
        for (Object value : values) {
            try {
                if (value instanceof MediaType) {
                    AcceptableMediaType _value = AcceptableMediaType.valueOf((MediaType)value);
                    conversionApplied = _value != value;
                    result.add(_value);
                    continue;
                }
                conversionApplied = true;
                result.addAll(HttpHeaderReader.readAcceptMediaType(HeaderUtils.asString(value, this.configuration)));
            }
            catch (ParseException e) {
                throw OutboundMessageContext.exception("Accept", value, e);
            }
        }
        if (conversionApplied) {
            this.headers.put("Accept", result.stream().map(mediaType -> mediaType).collect(Collectors.toList()));
        }
        return Collections.unmodifiableList(result);
    }

    public List<Locale> getAcceptableLanguages() {
        List values = (List)this.headers.get("Accept-Language");
        if (values == null || values.isEmpty()) {
            return Collections.singletonList(new AcceptableLanguageTag("*", null).getAsLocale());
        }
        ArrayList<Locale> result = new ArrayList<Locale>(values.size());
        boolean conversionApplied = false;
        for (Object value : values) {
            if (value instanceof Locale) {
                result.add((Locale)value);
                continue;
            }
            conversionApplied = true;
            try {
                result.addAll(HttpHeaderReader.readAcceptLanguage(HeaderUtils.asString(value, this.configuration)).stream().map(LanguageTag::getAsLocale).collect(Collectors.toList()));
            }
            catch (ParseException e) {
                throw OutboundMessageContext.exception("Accept-Language", value, e);
            }
        }
        if (conversionApplied) {
            this.headers.put("Accept-Language", result.stream().map(locale -> locale).collect(Collectors.toList()));
        }
        return Collections.unmodifiableList(result);
    }

    public Map<String, Cookie> getRequestCookies() {
        List cookies = (List)this.headers.get("Cookie");
        if (cookies == null || cookies.isEmpty()) {
            return Collections.emptyMap();
        }
        HashMap<String, Cookie> result = new HashMap<String, Cookie>();
        for (String cookie : HeaderUtils.asStringList((List<Object>)cookies, this.configuration)) {
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
            return new HashSet<String>(HttpHeaderReader.readStringList(allowed));
        }
        catch (ParseException e) {
            throw OutboundMessageContext.exception("Allow", allowed, e);
        }
    }

    public int getLength() {
        return this.singleHeader("Content-Length", Integer.class, input -> {
            try {
                int i;
                if (input != null && !input.isEmpty() && (i = Integer.parseInt(input)) >= 0) {
                    return i;
                }
                return -1;
            }
            catch (NumberFormatException ex) {
                throw new ProcessingException(ex);
            }
        }, true);
    }

    public long getLengthLong() {
        return this.singleHeader("Content-Length", Long.class, input -> {
            try {
                long l;
                if (input != null && !input.isEmpty() && (l = Long.parseLong(input)) >= 0L) {
                    return l;
                }
                return -1L;
            }
            catch (NumberFormatException ex) {
                throw new ProcessingException(ex);
            }
        }, true);
    }

    public Map<String, NewCookie> getResponseCookies() {
        List cookies = (List)this.headers.get("Set-Cookie");
        if (cookies == null || cookies.isEmpty()) {
            return Collections.emptyMap();
        }
        HashMap<String, NewCookie> result = new HashMap<String, NewCookie>();
        for (String cookie : HeaderUtils.asStringList((List<Object>)cookies, this.configuration)) {
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
        return this.singleHeader("ETag", EntityTag.class, new Function<String, EntityTag>(){

            @Override
            public EntityTag apply(String value) {
                try {
                    return value == null ? null : EntityTag.valueOf(value);
                }
                catch (IllegalArgumentException ex) {
                    throw new ProcessingException(ex);
                }
            }
        }, false);
    }

    public Date getLastModified() {
        return this.singleHeader("Last-Modified", Date.class, new Function<String, Date>(){

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
        return this.singleHeader("Location", URI.class, new Function<String, URI>(){

            @Override
            public URI apply(String value) {
                try {
                    return value == null ? null : URI.create(value);
                }
                catch (IllegalArgumentException ex) {
                    throw new ProcessingException(ex);
                }
            }
        }, false);
    }

    public Set<Link> getLinks() {
        List values = (List)this.headers.get("Link");
        if (values == null || values.isEmpty()) {
            return Collections.emptySet();
        }
        HashSet<Link> result = new HashSet<Link>(values.size());
        boolean conversionApplied = false;
        for (Object value : values) {
            if (value instanceof Link) {
                result.add((Link)value);
                continue;
            }
            conversionApplied = true;
            try {
                result.add(Link.valueOf(HeaderUtils.asString(value, this.configuration)));
            }
            catch (IllegalArgumentException e) {
                throw OutboundMessageContext.exception("Link", value, e);
            }
        }
        if (conversionApplied) {
            this.headers.put("Link", result.stream().map(link -> link).collect(Collectors.toList()));
        }
        return Collections.unmodifiableSet(result);
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

    public boolean hasEntity() {
        return this.entity != null;
    }

    public Object getEntity() {
        return this.entity;
    }

    public void setEntity(Object entity) {
        this.setEntity(entity, ReflectionHelper.genericTypeFor(entity));
    }

    public void setEntity(Object entity, Annotation[] annotations) {
        this.setEntity(entity, ReflectionHelper.genericTypeFor(entity));
        this.setEntityAnnotations(annotations);
    }

    private void setEntity(Object entity, GenericType<?> type) {
        this.entity = entity instanceof GenericEntity ? ((GenericEntity)entity).getEntity() : entity;
        this.entityType = type;
    }

    public void setEntity(Object entity, Type type, Annotation[] annotations) {
        this.setEntity(entity, new GenericType(type));
        this.setEntityAnnotations(annotations);
    }

    public void setEntity(Object entity, Annotation[] annotations, MediaType mediaType) {
        this.setEntity(entity, annotations);
        this.setMediaType(mediaType);
    }

    public void setMediaType(MediaType mediaType) {
        this.headers.putSingle("Content-Type", mediaType);
    }

    public Class<?> getEntityClass() {
        return this.entityType == null ? null : this.entityType.getRawType();
    }

    public Type getEntityType() {
        return this.entityType == null ? null : this.entityType.getType();
    }

    public void setEntityType(Type type) {
        this.entityType = new GenericType(type);
    }

    public Annotation[] getEntityAnnotations() {
        return (Annotation[])this.entityAnnotations.clone();
    }

    public void setEntityAnnotations(Annotation[] annotations) {
        this.entityAnnotations = annotations == null ? EMPTY_ANNOTATIONS : annotations;
    }

    public OutputStream getEntityStream() {
        return this.entityStream;
    }

    public void setEntityStream(OutputStream outputStream) {
        this.entityStream = outputStream;
    }

    public void enableBuffering(Configuration configuration) {
        Integer bufferSize = CommonProperties.getValue(configuration.getProperties(), configuration.getRuntimeType(), "jersey.config.contentLength.buffer", Integer.class);
        if (bufferSize != null) {
            this.committingOutputStream.enableBuffering(bufferSize);
        } else {
            this.committingOutputStream.enableBuffering();
        }
    }

    public void setStreamProvider(StreamProvider streamProvider) {
        this.committingOutputStream.setStreamProvider(streamProvider);
    }

    public void commitStream() throws IOException {
        if (!this.committingOutputStream.isCommitted()) {
            this.entityStream.flush();
            if (!this.committingOutputStream.isCommitted()) {
                this.committingOutputStream.commit();
                this.committingOutputStream.flush();
            }
        }
    }

    public boolean isCommitted() {
        return this.committingOutputStream.isCommitted();
    }

    public void close() {
        if (this.hasEntity()) {
            try {
                OutputStream es = this.getEntityStream();
                es.flush();
                es.close();
            }
            catch (IOException e) {
                Logger.getLogger(OutboundMessageContext.class.getName()).log(Level.FINE, e.getMessage(), e);
            }
            finally {
                if (!this.committingOutputStream.isClosed()) {
                    try {
                        this.committingOutputStream.close();
                    }
                    catch (IOException e) {
                        Logger.getLogger(OutboundMessageContext.class.getName()).log(Level.FINE, e.getMessage(), e);
                    }
                }
            }
        }
    }

    void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public Configuration getConfiguration() {
        return this.configuration;
    }

    public static interface StreamProvider {
        public OutputStream getOutputStream(int var1) throws IOException;
    }
}

