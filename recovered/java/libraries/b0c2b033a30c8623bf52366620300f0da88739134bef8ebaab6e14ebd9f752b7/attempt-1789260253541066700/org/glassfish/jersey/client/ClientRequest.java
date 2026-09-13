/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Variant;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.WriterInterceptor;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientRuntime;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.internal.LocalizationMessages;
import org.glassfish.jersey.internal.MapPropertiesDelegate;
import org.glassfish.jersey.internal.PropertiesDelegate;
import org.glassfish.jersey.internal.guava.Preconditions;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.internal.inject.InjectionManagerSupplier;
import org.glassfish.jersey.internal.util.ExceptionUtils;
import org.glassfish.jersey.internal.util.PropertiesHelper;
import org.glassfish.jersey.message.MessageBodyWorkers;
import org.glassfish.jersey.message.internal.HeaderUtils;
import org.glassfish.jersey.message.internal.OutboundMessageContext;

public class ClientRequest
extends OutboundMessageContext
implements ClientRequestContext,
HttpHeaders,
InjectionManagerSupplier {
    private final ClientConfig clientConfig;
    private final PropertiesDelegate propertiesDelegate;
    private URI requestUri;
    private String httpMethod;
    private Response abortResponse;
    private MessageBodyWorkers workers;
    private boolean asynchronous;
    private boolean entityWritten;
    private Iterable<WriterInterceptor> writerInterceptors;
    private Iterable<ReaderInterceptor> readerInterceptors;
    private boolean ignoreUserAgent;
    private static final Logger LOGGER = Logger.getLogger(ClientRequest.class.getName());

    protected ClientRequest(URI requestUri, ClientConfig clientConfig, PropertiesDelegate propertiesDelegate) {
        super(clientConfig.getConfiguration());
        clientConfig.checkClient();
        this.requestUri = requestUri;
        this.clientConfig = clientConfig;
        this.propertiesDelegate = propertiesDelegate;
    }

    public ClientRequest(ClientRequest original) {
        super(original);
        this.requestUri = original.requestUri;
        this.httpMethod = original.httpMethod;
        this.workers = original.workers;
        this.clientConfig = original.clientConfig.snapshot();
        this.asynchronous = original.isAsynchronous();
        this.readerInterceptors = original.readerInterceptors;
        this.writerInterceptors = original.writerInterceptors;
        this.propertiesDelegate = new MapPropertiesDelegate(original.propertiesDelegate);
        this.ignoreUserAgent = original.ignoreUserAgent;
    }

    public <T> T resolveProperty(String name, Class<T> type) {
        return this.resolveProperty(name, null, type);
    }

    public <T> T resolveProperty(String name, T defaultValue) {
        return (T)this.resolveProperty(name, defaultValue, defaultValue.getClass());
    }

    private <T> T resolveProperty(String name, Object defaultValue, Class<T> type) {
        Object result = this.clientConfig.getProperty(name);
        if (result != null) {
            defaultValue = result;
        }
        if ((result = this.propertiesDelegate.getProperty(name)) == null) {
            result = defaultValue;
        }
        return result == null ? null : (T)PropertiesHelper.convertValue(result, type);
    }

    @Override
    public Object getProperty(String name) {
        return this.propertiesDelegate.getProperty(name);
    }

    @Override
    public Collection<String> getPropertyNames() {
        return this.propertiesDelegate.getPropertyNames();
    }

    @Override
    public void setProperty(String name, Object object) {
        this.propertiesDelegate.setProperty(name, object);
    }

    @Override
    public void removeProperty(String name) {
        this.propertiesDelegate.removeProperty(name);
    }

    PropertiesDelegate getPropertiesDelegate() {
        return this.propertiesDelegate;
    }

    ClientRuntime getClientRuntime() {
        return this.clientConfig.getRuntime();
    }

    @Override
    public URI getUri() {
        return this.requestUri;
    }

    @Override
    public void setUri(URI uri) {
        this.requestUri = uri;
    }

    @Override
    public String getMethod() {
        return this.httpMethod;
    }

    @Override
    public void setMethod(String method) {
        this.httpMethod = method;
    }

    @Override
    public JerseyClient getClient() {
        return this.clientConfig.getClient();
    }

    @Override
    public void abortWith(Response response) {
        this.abortResponse = response;
    }

    public Response getAbortResponse() {
        return this.abortResponse;
    }

    @Override
    public Configuration getConfiguration() {
        return this.clientConfig.getRuntime().getConfig();
    }

    ClientConfig getClientConfig() {
        return this.clientConfig;
    }

    @Override
    public List<String> getRequestHeader(String name) {
        return HeaderUtils.asStringList((List<Object>)((List)this.getHeaders().get(name)), this.clientConfig.getConfiguration());
    }

    @Override
    public MultivaluedMap<String, String> getRequestHeaders() {
        return HeaderUtils.asStringHeaders(this.getHeaders(), this.clientConfig.getConfiguration());
    }

    @Override
    public Map<String, Cookie> getCookies() {
        return super.getRequestCookies();
    }

    public MessageBodyWorkers getWorkers() {
        return this.workers;
    }

    public void setWorkers(MessageBodyWorkers workers) {
        this.workers = workers;
    }

    public void accept(MediaType ... types) {
        this.getHeaders().addAll("Accept", (Object[])types);
    }

    public void accept(String ... types) {
        this.getHeaders().addAll("Accept", (Object[])types);
    }

    public void acceptLanguage(Locale ... locales) {
        this.getHeaders().addAll("Accept-Language", (Object[])locales);
    }

    public void acceptLanguage(String ... locales) {
        this.getHeaders().addAll("Accept-Language", (Object[])locales);
    }

    public void cookie(Cookie cookie) {
        this.getHeaders().add("Cookie", cookie);
    }

    public void cacheControl(CacheControl cacheControl) {
        this.getHeaders().add("Cache-Control", cacheControl);
    }

    public void encoding(String encoding) {
        if (encoding == null) {
            this.getHeaders().remove("Content-Encoding");
        } else {
            this.getHeaders().putSingle("Content-Encoding", encoding);
        }
    }

    public void language(String language) {
        if (language == null) {
            this.getHeaders().remove("Content-Language");
        } else {
            this.getHeaders().putSingle("Content-Language", language);
        }
    }

    public void language(Locale language) {
        if (language == null) {
            this.getHeaders().remove("Content-Language");
        } else {
            this.getHeaders().putSingle("Content-Language", language);
        }
    }

    public void type(MediaType type) {
        this.setMediaType(type);
    }

    public void type(String type) {
        this.type(type == null ? null : MediaType.valueOf(type));
    }

    public void variant(Variant variant) {
        if (variant == null) {
            this.type((MediaType)null);
            this.language((String)null);
            this.encoding(null);
        } else {
            this.type(variant.getMediaType());
            this.language(variant.getLanguage());
            this.encoding(variant.getEncoding());
        }
    }

    public boolean isAsynchronous() {
        return this.asynchronous;
    }

    void setAsynchronous(boolean async) {
        this.asynchronous = async;
    }

    public void enableBuffering() {
        this.enableBuffering(this.getConfiguration());
    }

    public void writeEntity() throws IOException {
        Preconditions.checkState(!this.entityWritten, LocalizationMessages.REQUEST_ENTITY_ALREADY_WRITTEN());
        this.entityWritten = true;
        this.ensureMediaType();
        GenericType entityType = new GenericType(this.getEntityType());
        this.doWriteEntity(this.workers, entityType);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void doWriteEntity(MessageBodyWorkers writeWorkers, GenericType<?> entityType) throws IOException {
        block19: {
            boolean runtimeException;
            block20: {
                OutputStream entityStream = null;
                boolean connectionFailed = false;
                runtimeException = false;
                try {
                    try {
                        entityStream = writeWorkers.writeTo(this.getEntity(), entityType.getRawType(), entityType.getType(), this.getEntityAnnotations(), this.getMediaType(), this.getHeaders(), this.getPropertiesDelegate(), this.getEntityStream(), this.writerInterceptors);
                        this.setEntityStream(entityStream);
                    }
                    catch (IOException e) {
                        connectionFailed = true;
                        throw e;
                    }
                    catch (RuntimeException e) {
                        runtimeException = true;
                        throw e;
                    }
                    if (connectionFailed) break block19;
                    if (entityStream == null) break block20;
                }
                catch (Throwable throwable) {
                    if (!connectionFailed) {
                        if (entityStream != null) {
                            try {
                                entityStream.close();
                            }
                            catch (IOException e) {
                                ExceptionUtils.conditionallyReThrow(e, !runtimeException, LOGGER, LocalizationMessages.ERROR_CLOSING_OUTPUT_STREAM(), Level.FINE);
                            }
                            catch (RuntimeException e) {
                                ExceptionUtils.conditionallyReThrow(e, !runtimeException, LOGGER, LocalizationMessages.ERROR_CLOSING_OUTPUT_STREAM(), Level.FINE);
                            }
                        }
                        try {
                            this.commitStream();
                        }
                        catch (IOException e) {
                            ExceptionUtils.conditionallyReThrow(e, !runtimeException, LOGGER, LocalizationMessages.ERROR_COMMITTING_OUTPUT_STREAM(), Level.FINE);
                        }
                        catch (RuntimeException e) {
                            ExceptionUtils.conditionallyReThrow(e, !runtimeException, LOGGER, LocalizationMessages.ERROR_COMMITTING_OUTPUT_STREAM(), Level.FINE);
                        }
                    }
                    throw throwable;
                }
                try {
                    entityStream.close();
                }
                catch (IOException e) {
                    ExceptionUtils.conditionallyReThrow(e, !runtimeException, LOGGER, LocalizationMessages.ERROR_CLOSING_OUTPUT_STREAM(), Level.FINE);
                }
                catch (RuntimeException e) {
                    ExceptionUtils.conditionallyReThrow(e, !runtimeException, LOGGER, LocalizationMessages.ERROR_CLOSING_OUTPUT_STREAM(), Level.FINE);
                }
            }
            try {
                this.commitStream();
            }
            catch (IOException e) {
                ExceptionUtils.conditionallyReThrow(e, !runtimeException, LOGGER, LocalizationMessages.ERROR_COMMITTING_OUTPUT_STREAM(), Level.FINE);
            }
            catch (RuntimeException e) {
                ExceptionUtils.conditionallyReThrow(e, !runtimeException, LOGGER, LocalizationMessages.ERROR_COMMITTING_OUTPUT_STREAM(), Level.FINE);
            }
        }
    }

    private void ensureMediaType() {
        if (this.getMediaType() == null) {
            GenericType entityType = new GenericType(this.getEntityType());
            List<MediaType> mediaTypes = this.workers.getMessageBodyWriterMediaTypes(entityType.getRawType(), entityType.getType(), this.getEntityAnnotations());
            this.setMediaType(this.getMediaType(mediaTypes));
        }
    }

    private MediaType getMediaType(List<MediaType> mediaTypes) {
        if (mediaTypes.isEmpty()) {
            return MediaType.APPLICATION_OCTET_STREAM_TYPE;
        }
        MediaType mediaType = mediaTypes.get(0);
        if (mediaType.isWildcardType() || mediaType.isWildcardSubtype()) {
            mediaType = MediaType.APPLICATION_OCTET_STREAM_TYPE;
        }
        return mediaType;
    }

    void setWriterInterceptors(Iterable<WriterInterceptor> writerInterceptors) {
        this.writerInterceptors = writerInterceptors;
    }

    public Iterable<WriterInterceptor> getWriterInterceptors() {
        return this.writerInterceptors;
    }

    public Iterable<ReaderInterceptor> getReaderInterceptors() {
        return this.readerInterceptors;
    }

    void setReaderInterceptors(Iterable<ReaderInterceptor> readerInterceptors) {
        this.readerInterceptors = readerInterceptors;
    }

    @Override
    public InjectionManager getInjectionManager() {
        return this.getClientRuntime().getInjectionManager();
    }

    public boolean ignoreUserAgent() {
        return this.ignoreUserAgent;
    }

    public void ignoreUserAgent(boolean ignore) {
        this.ignoreUserAgent = ignore;
    }
}

