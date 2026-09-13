/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.client.internal;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientRequest;
import org.glassfish.jersey.client.ClientResponse;
import org.glassfish.jersey.client.HttpUrlConnectorProvider;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.RequestEntityProcessing;
import org.glassfish.jersey.client.internal.ClientResponseProcessingException;
import org.glassfish.jersey.client.internal.LocalizationMessages;
import org.glassfish.jersey.client.spi.AsyncConnectorCallback;
import org.glassfish.jersey.client.spi.Connector;
import org.glassfish.jersey.internal.util.PropertiesHelper;
import org.glassfish.jersey.internal.util.collection.LazyValue;
import org.glassfish.jersey.internal.util.collection.UnsafeValue;
import org.glassfish.jersey.internal.util.collection.Value;
import org.glassfish.jersey.internal.util.collection.Values;
import org.glassfish.jersey.message.internal.Statuses;

public class HttpUrlConnector
implements Connector {
    private static final Logger LOGGER = Logger.getLogger(HttpUrlConnector.class.getName());
    private static final String ALLOW_RESTRICTED_HEADERS_SYSTEM_PROPERTY = "sun.net.http.allowRestrictedHeaders";
    private static final String[] restrictedHeaders = new String[]{"Access-Control-Request-Headers", "Access-Control-Request-Method", "Connection", "Content-Length", "Content-Transfer-Encoding", "Host", "Keep-Alive", "Origin", "Trailer", "Transfer-Encoding", "Upgrade", "Via"};
    private static final Set<String> restrictedHeaderSet = new HashSet<String>(restrictedHeaders.length);
    private final HttpUrlConnectorProvider.ConnectionFactory connectionFactory;
    private final int chunkSize;
    private final boolean fixLengthStreaming;
    private final boolean setMethodWorkaround;
    private final boolean isRestrictedHeaderPropertySet;
    private final LazyValue<SSLSocketFactory> sslSocketFactory;

    public HttpUrlConnector(final Client client, HttpUrlConnectorProvider.ConnectionFactory connectionFactory, int chunkSize, boolean fixLengthStreaming, boolean setMethodWorkaround) {
        this.sslSocketFactory = Values.lazy(new Value<SSLSocketFactory>(){

            @Override
            public SSLSocketFactory get() {
                return client.getSslContext().getSocketFactory();
            }
        });
        this.connectionFactory = connectionFactory;
        this.chunkSize = chunkSize;
        this.fixLengthStreaming = fixLengthStreaming;
        this.setMethodWorkaround = setMethodWorkaround;
        this.isRestrictedHeaderPropertySet = Boolean.valueOf(AccessController.doPrivileged(PropertiesHelper.getSystemProperty(ALLOW_RESTRICTED_HEADERS_SYSTEM_PROPERTY, "false")));
        LOGGER.config(this.isRestrictedHeaderPropertySet ? LocalizationMessages.RESTRICTED_HEADER_PROPERTY_SETTING_TRUE(ALLOW_RESTRICTED_HEADERS_SYSTEM_PROPERTY) : LocalizationMessages.RESTRICTED_HEADER_PROPERTY_SETTING_FALSE(ALLOW_RESTRICTED_HEADERS_SYSTEM_PROPERTY));
    }

    private static InputStream getInputStream(final HttpURLConnection uc) throws IOException {
        return new InputStream(){
            private final UnsafeValue<InputStream, IOException> in = Values.lazy(new UnsafeValue<InputStream, IOException>(){

                @Override
                public InputStream get() throws IOException {
                    if (uc.getResponseCode() < Response.Status.BAD_REQUEST.getStatusCode()) {
                        return uc.getInputStream();
                    }
                    InputStream ein = uc.getErrorStream();
                    return ein != null ? ein : new ByteArrayInputStream(new byte[0]);
                }
            });
            private volatile boolean closed = false;

            private void throwIOExceptionIfClosed() throws IOException {
                if (this.closed) {
                    throw new IOException("Stream closed");
                }
            }

            @Override
            public int read() throws IOException {
                int result = this.in.get().read();
                this.throwIOExceptionIfClosed();
                return result;
            }

            @Override
            public int read(byte[] b) throws IOException {
                int result = this.in.get().read(b);
                this.throwIOExceptionIfClosed();
                return result;
            }

            @Override
            public int read(byte[] b, int off, int len) throws IOException {
                int result = this.in.get().read(b, off, len);
                this.throwIOExceptionIfClosed();
                return result;
            }

            @Override
            public long skip(long n) throws IOException {
                long result = this.in.get().skip(n);
                this.throwIOExceptionIfClosed();
                return result;
            }

            @Override
            public int available() throws IOException {
                int result = this.in.get().available();
                this.throwIOExceptionIfClosed();
                return result;
            }

            @Override
            public void close() throws IOException {
                try {
                    this.in.get().close();
                }
                finally {
                    this.closed = true;
                }
            }

            @Override
            public void mark(int readLimit) {
                try {
                    this.in.get().mark(readLimit);
                }
                catch (IOException e) {
                    throw new IllegalStateException("Unable to retrieve the underlying input stream.", e);
                }
            }

            @Override
            public void reset() throws IOException {
                this.in.get().reset();
                this.throwIOExceptionIfClosed();
            }

            @Override
            public boolean markSupported() {
                try {
                    return this.in.get().markSupported();
                }
                catch (IOException e) {
                    throw new IllegalStateException("Unable to retrieve the underlying input stream.", e);
                }
            }
        };
    }

    @Override
    public ClientResponse apply(ClientRequest request) {
        try {
            return this._apply(request);
        }
        catch (IOException ex) {
            throw new ProcessingException(ex);
        }
    }

    @Override
    public Future<?> apply(ClientRequest request, AsyncConnectorCallback callback) {
        try {
            callback.response(this._apply(request));
        }
        catch (IOException ex) {
            callback.failure(new ProcessingException(ex));
        }
        catch (Throwable t) {
            callback.failure(t);
        }
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public void close() {
    }

    protected void secureConnection(JerseyClient client, HttpURLConnection uc) {
        if (uc instanceof HttpsURLConnection) {
            HttpsURLConnection suc = (HttpsURLConnection)uc;
            HostnameVerifier verifier = client.getHostnameVerifier();
            if (verifier != null) {
                suc.setHostnameVerifier(verifier);
            }
            if (HttpsURLConnection.getDefaultSSLSocketFactory() == suc.getSSLSocketFactory()) {
                suc.setSSLSocketFactory((SSLSocketFactory)this.sslSocketFactory.get());
            }
        }
    }

    private ClientResponse _apply(ClientRequest request) throws IOException {
        HttpURLConnection uc = this.connectionFactory.getConnection(request.getUri().toURL());
        uc.setDoInput(true);
        String httpMethod = request.getMethod();
        if (request.resolveProperty("jersey.config.client.httpUrlConnection.setMethodWorkaround", this.setMethodWorkaround).booleanValue()) {
            HttpUrlConnector.setRequestMethodViaJreBugWorkaround(uc, httpMethod);
        } else {
            uc.setRequestMethod(httpMethod);
        }
        uc.setInstanceFollowRedirects(request.resolveProperty("jersey.config.client.followRedirects", true));
        uc.setConnectTimeout(request.resolveProperty("jersey.config.client.connectTimeout", uc.getConnectTimeout()));
        uc.setReadTimeout(request.resolveProperty("jersey.config.client.readTimeout", uc.getReadTimeout()));
        this.secureConnection(request.getClient(), uc);
        Object entity = request.getEntity();
        Exception storedException = null;
        try {
            if (entity != null) {
                Logger logger;
                RequestEntityProcessing entityProcessing = (RequestEntityProcessing)((Object)request.resolveProperty("jersey.config.client.request.entity.processing", RequestEntityProcessing.class));
                if (entityProcessing == null || entityProcessing != RequestEntityProcessing.BUFFERED) {
                    long length = request.getLengthLong();
                    if (this.fixLengthStreaming && length > 0L) {
                        uc.setFixedLengthStreamingMode(length);
                    } else if (entityProcessing == RequestEntityProcessing.CHUNKED) {
                        uc.setChunkedStreamingMode(this.chunkSize);
                    }
                }
                uc.setDoOutput(true);
                if ("GET".equalsIgnoreCase(httpMethod) && (logger = Logger.getLogger(HttpUrlConnector.class.getName())).isLoggable(Level.INFO)) {
                    logger.log(Level.INFO, LocalizationMessages.HTTPURLCONNECTION_REPLACES_GET_WITH_ENTITY());
                }
                request.setStreamProvider(contentLength -> {
                    this.setOutboundHeaders(request.getStringHeaders(), uc);
                    return uc.getOutputStream();
                });
                request.writeEntity();
            } else {
                this.setOutboundHeaders(request.getStringHeaders(), uc);
            }
        }
        catch (IOException ioe) {
            if (uc.getResponseCode() == -1) {
                throw ioe;
            }
            storedException = ioe;
        }
        int code = uc.getResponseCode();
        String reasonPhrase = uc.getResponseMessage();
        Response.StatusType status = reasonPhrase == null ? Statuses.from(code) : Statuses.from(code, reasonPhrase);
        URI resolvedRequestUri = null;
        try {
            resolvedRequestUri = uc.getURL().toURI();
        }
        catch (URISyntaxException e) {
            if (storedException == null) {
                storedException = e;
            }
            storedException.addSuppressed(e);
        }
        ClientResponse responseContext = new ClientResponse(status, request, resolvedRequestUri);
        responseContext.headers(uc.getHeaderFields().entrySet().stream().filter(stringListEntry -> stringListEntry.getKey() != null).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
        try {
            InputStream inputStream = HttpUrlConnector.getInputStream(uc);
            responseContext.setEntityStream(inputStream);
        }
        catch (IOException ioe) {
            if (storedException == null) {
                storedException = ioe;
            }
            storedException.addSuppressed(ioe);
        }
        if (storedException != null) {
            throw new ClientResponseProcessingException(responseContext, (Throwable)storedException);
        }
        return responseContext;
    }

    private void setOutboundHeaders(MultivaluedMap<String, String> headers, HttpURLConnection uc) {
        boolean restrictedSent = false;
        for (Map.Entry header : headers.entrySet()) {
            String headerValue;
            String headerName = (String)header.getKey();
            List headerValues = (List)header.getValue();
            if (headerValues.size() == 1) {
                headerValue = (String)headerValues.get(0);
                uc.setRequestProperty(headerName, headerValue);
            } else {
                StringBuilder b = new StringBuilder();
                boolean add = false;
                for (Object value : headerValues) {
                    if (add) {
                        b.append(',');
                    }
                    add = true;
                    b.append(value);
                }
                headerValue = b.toString();
                uc.setRequestProperty(headerName, headerValue);
            }
            if (this.isRestrictedHeaderPropertySet || restrictedSent || !this.isHeaderRestricted(headerName, headerValue)) continue;
            restrictedSent = true;
        }
        if (restrictedSent) {
            LOGGER.warning(LocalizationMessages.RESTRICTED_HEADER_POSSIBLY_IGNORED(ALLOW_RESTRICTED_HEADERS_SYSTEM_PROPERTY));
        }
    }

    private boolean isHeaderRestricted(String name, String value) {
        return (name = name.toLowerCase(Locale.ROOT)).startsWith("sec-") || restrictedHeaderSet.contains(name) && (!"connection".equalsIgnoreCase(name) || !"close".equalsIgnoreCase(value));
    }

    private static void setRequestMethodViaJreBugWorkaround(final HttpURLConnection httpURLConnection, final String method) {
        try {
            httpURLConnection.setRequestMethod(method);
        }
        catch (ProtocolException pe) {
            try {
                AccessController.doPrivileged(new PrivilegedExceptionAction<Object>(){

                    @Override
                    public Object run() throws NoSuchFieldException, IllegalAccessException {
                        try {
                            httpURLConnection.setRequestMethod(method);
                        }
                        catch (ProtocolException pe) {
                            Class<?> connectionClass = httpURLConnection.getClass();
                            try {
                                Field delegateField = connectionClass.getDeclaredField("delegate");
                                delegateField.setAccessible(true);
                                HttpURLConnection delegateConnection = (HttpURLConnection)delegateField.get(httpURLConnection);
                                HttpUrlConnector.setRequestMethodViaJreBugWorkaround(delegateConnection, method);
                            }
                            catch (NoSuchFieldException delegateField) {
                            }
                            catch (IllegalAccessException | IllegalArgumentException e) {
                                throw new RuntimeException(e);
                            }
                            try {
                                while (connectionClass != null) {
                                    Field methodField;
                                    try {
                                        methodField = connectionClass.getDeclaredField("method");
                                    }
                                    catch (NoSuchFieldException e) {
                                        connectionClass = connectionClass.getSuperclass();
                                        continue;
                                    }
                                    methodField.setAccessible(true);
                                    methodField.set(httpURLConnection, method);
                                }
                            }
                            catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                        return null;
                    }
                });
            }
            catch (PrivilegedActionException e) {
                Throwable cause = e.getCause();
                if (cause instanceof RuntimeException) {
                    throw (RuntimeException)cause;
                }
                throw new RuntimeException(cause);
            }
        }
    }

    @Override
    public String getName() {
        return "HttpUrlConnection " + AccessController.doPrivileged(PropertiesHelper.getSystemProperty("java.version"));
    }

    static {
        for (String headerName : restrictedHeaders) {
            restrictedHeaderSet.add(headerName.toLowerCase(Locale.ROOT));
        }
    }
}

