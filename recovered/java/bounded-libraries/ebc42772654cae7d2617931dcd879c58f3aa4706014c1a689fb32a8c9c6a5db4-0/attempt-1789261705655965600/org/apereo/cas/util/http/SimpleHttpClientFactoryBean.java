/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.http.ConnectionReuseStrategy
 *  org.apache.http.Header
 *  org.apache.http.HttpHost
 *  org.apache.http.client.AuthenticationStrategy
 *  org.apache.http.client.ConnectionBackoffStrategy
 *  org.apache.http.client.CookieStore
 *  org.apache.http.client.CredentialsProvider
 *  org.apache.http.client.HttpClient
 *  org.apache.http.client.HttpRequestRetryHandler
 *  org.apache.http.client.RedirectStrategy
 *  org.apache.http.client.ServiceUnavailableRetryStrategy
 *  org.apache.http.client.config.RequestConfig
 *  org.apache.http.config.Registry
 *  org.apache.http.config.RegistryBuilder
 *  org.apache.http.conn.ConnectionKeepAliveStrategy
 *  org.apache.http.conn.HttpClientConnectionManager
 *  org.apache.http.conn.routing.HttpRoute
 *  org.apache.http.conn.socket.LayeredConnectionSocketFactory
 *  org.apache.http.conn.socket.PlainConnectionSocketFactory
 *  org.apache.http.conn.ssl.DefaultHostnameVerifier
 *  org.apache.http.conn.ssl.SSLConnectionSocketFactory
 *  org.apache.http.impl.DefaultConnectionReuseStrategy
 *  org.apache.http.impl.client.CloseableHttpClient
 *  org.apache.http.impl.client.DefaultBackoffStrategy
 *  org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy
 *  org.apache.http.impl.client.DefaultHttpRequestRetryHandler
 *  org.apache.http.impl.client.DefaultRedirectStrategy
 *  org.apache.http.impl.client.DefaultServiceUnavailableRetryStrategy
 *  org.apache.http.impl.client.FutureRequestExecutionService
 *  org.apache.http.impl.client.HttpClientBuilder
 *  org.apache.http.impl.client.HttpClients
 *  org.apache.http.impl.client.ProxyAuthenticationStrategy
 *  org.apache.http.impl.conn.PoolingHttpClientConnectionManager
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.util.http;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import lombok.Generated;
import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.client.AuthenticationStrategy;
import org.apache.http.client.ConnectionBackoffStrategy;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.RedirectStrategy;
import org.apache.http.client.ServiceUnavailableRetryStrategy;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultBackoffStrategy;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.client.DefaultServiceUnavailableRetryStrategy;
import org.apache.http.impl.client.FutureRequestExecutionService;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.ProxyAuthenticationStrategy;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apereo.cas.util.function.FunctionUtils;
import org.apereo.cas.util.http.HttpClientFactory;
import org.apereo.cas.util.http.SimpleHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleHttpClientFactoryBean
implements HttpClientFactory {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleHttpClientFactoryBean.class);
    public static final int MAX_CONNECTIONS_PER_ROUTE = 50;
    private static final int MAX_POOLED_CONNECTIONS = 100;
    private static final int DEFAULT_THREADS_NUMBER = 200;
    private static final int DEFAULT_TIMEOUT = 5000;
    private static final int TERMINATION_TIMEOUT_SECONDS = 5;
    private static final int[] DEFAULT_ACCEPTABLE_CODES = new int[]{200, 304, 302, 301, 202, 204};
    private static final int DEFAULT_QUEUE_SIZE = 40;
    private int threadsNumber = 200;
    private int queueSize = 40;
    private int maxPooledConnections = 100;
    private int maxConnectionsPerRoute = 50;
    private List<Integer> acceptableCodes = IntStream.of(DEFAULT_ACCEPTABLE_CODES).boxed().collect(Collectors.toList());
    private long connectionTimeout = 5000L;
    private int socketTimeout = 5000;
    private int readTimeout = 5000;
    private RedirectStrategy redirectionStrategy = new DefaultRedirectStrategy();
    private LayeredConnectionSocketFactory sslSocketFactory = SSLConnectionSocketFactory.getSocketFactory();
    private HostnameVerifier hostnameVerifier = new DefaultHostnameVerifier();
    private SSLContext sslContext;
    private TrustManager[] trustManagers;
    private CredentialsProvider credentialsProvider;
    private CookieStore cookieStore;
    private ConnectionReuseStrategy connectionReuseStrategy = new DefaultConnectionReuseStrategy();
    private ConnectionKeepAliveStrategy connectionKeepAliveStrategy = new DefaultConnectionKeepAliveStrategy();
    private ConnectionBackoffStrategy connectionBackoffStrategy = new DefaultBackoffStrategy();
    private ServiceUnavailableRetryStrategy serviceUnavailableRetryStrategy = new DefaultServiceUnavailableRetryStrategy();
    private HttpRequestRetryHandler httpRequestRetryHandler = new DefaultHttpRequestRetryHandler();
    private Collection<? extends Header> defaultHeaders = new ArrayList<Header>(0);
    private AuthenticationStrategy proxyAuthenticationStrategy = new ProxyAuthenticationStrategy();
    private boolean circularRedirectsAllowed = true;
    private boolean authenticationEnabled;
    private boolean redirectsEnabled = true;
    private ExecutorService executorService;
    private HttpHost proxy;

    public SimpleHttpClient getObject() {
        CloseableHttpClient httpClient = this.buildHttpClient();
        FutureRequestExecutionService requestExecutorService = this.buildRequestExecutorService(httpClient);
        List<Integer> codes = this.acceptableCodes.stream().sorted().collect(Collectors.toList());
        return new SimpleHttpClient(codes, httpClient, requestExecutorService, this);
    }

    public Class<?> getObjectType() {
        return SimpleHttpClient.class;
    }

    public boolean isSingleton() {
        return false;
    }

    private CloseableHttpClient buildHttpClient() {
        PlainConnectionSocketFactory plainSocketFactory = PlainConnectionSocketFactory.getSocketFactory();
        Registry registry = RegistryBuilder.create().register("http", (Object)plainSocketFactory).register("https", (Object)this.sslSocketFactory).build();
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
        connectionManager.setMaxTotal(this.maxPooledConnections);
        connectionManager.setDefaultMaxPerRoute(this.maxConnectionsPerRoute);
        connectionManager.setValidateAfterInactivity(5000);
        HttpHost httpHost = (HttpHost)FunctionUtils.doUnchecked(() -> new HttpHost(InetAddress.getLocalHost()));
        HttpRoute httpRoute = new HttpRoute(httpHost);
        connectionManager.setMaxPerRoute(httpRoute, 50);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(this.readTimeout).setConnectTimeout((int)this.connectionTimeout).setConnectionRequestTimeout((int)this.connectionTimeout).setCircularRedirectsAllowed(this.circularRedirectsAllowed).setRedirectsEnabled(this.redirectsEnabled).setAuthenticationEnabled(this.authenticationEnabled).setSocketTimeout(this.socketTimeout).build();
        HttpClientBuilder builder = HttpClients.custom().setConnectionManager((HttpClientConnectionManager)connectionManager).setDefaultRequestConfig(requestConfig).setSSLSocketFactory(this.sslSocketFactory).setSSLHostnameVerifier(this.hostnameVerifier).setRedirectStrategy(this.redirectionStrategy).setDefaultCredentialsProvider(this.credentialsProvider).setDefaultCookieStore(this.cookieStore).setConnectionReuseStrategy(this.connectionReuseStrategy).setKeepAliveStrategy(this.connectionKeepAliveStrategy).setConnectionBackoffStrategy(this.connectionBackoffStrategy).setServiceUnavailableRetryStrategy(this.serviceUnavailableRetryStrategy).setProxyAuthenticationStrategy(this.proxyAuthenticationStrategy).setDefaultHeaders(this.defaultHeaders).setProxy(this.proxy).setRetryHandler(this.httpRequestRetryHandler).useSystemProperties();
        return builder.build();
    }

    private FutureRequestExecutionService buildRequestExecutorService(CloseableHttpClient httpClient) {
        if (this.executorService == null) {
            this.executorService = new ThreadPoolExecutor(this.threadsNumber, this.threadsNumber, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(this.queueSize));
        }
        return new FutureRequestExecutionService((HttpClient)httpClient, this.executorService);
    }

    public void destroy() {
        if (this.executorService != null) {
            try {
                this.executorService.awaitTermination(5L, TimeUnit.SECONDS);
            }
            catch (Exception e) {
                LOGGER.trace(e.getMessage(), (Throwable)e);
            }
            this.executorService = null;
        }
    }

    @Generated
    public void setThreadsNumber(int threadsNumber) {
        this.threadsNumber = threadsNumber;
    }

    @Generated
    public void setQueueSize(int queueSize) {
        this.queueSize = queueSize;
    }

    @Generated
    public void setMaxPooledConnections(int maxPooledConnections) {
        this.maxPooledConnections = maxPooledConnections;
    }

    @Generated
    public void setMaxConnectionsPerRoute(int maxConnectionsPerRoute) {
        this.maxConnectionsPerRoute = maxConnectionsPerRoute;
    }

    @Generated
    public void setAcceptableCodes(List<Integer> acceptableCodes) {
        this.acceptableCodes = acceptableCodes;
    }

    @Generated
    public void setConnectionTimeout(long connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    @Generated
    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    @Generated
    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    @Generated
    public void setRedirectionStrategy(RedirectStrategy redirectionStrategy) {
        this.redirectionStrategy = redirectionStrategy;
    }

    @Generated
    public void setSslSocketFactory(LayeredConnectionSocketFactory sslSocketFactory) {
        this.sslSocketFactory = sslSocketFactory;
    }

    @Generated
    public void setHostnameVerifier(HostnameVerifier hostnameVerifier) {
        this.hostnameVerifier = hostnameVerifier;
    }

    @Generated
    public void setSslContext(SSLContext sslContext) {
        this.sslContext = sslContext;
    }

    @Generated
    public void setTrustManagers(TrustManager[] trustManagers) {
        this.trustManagers = trustManagers;
    }

    @Generated
    public void setCredentialsProvider(CredentialsProvider credentialsProvider) {
        this.credentialsProvider = credentialsProvider;
    }

    @Generated
    public void setCookieStore(CookieStore cookieStore) {
        this.cookieStore = cookieStore;
    }

    @Generated
    public void setConnectionReuseStrategy(ConnectionReuseStrategy connectionReuseStrategy) {
        this.connectionReuseStrategy = connectionReuseStrategy;
    }

    @Generated
    public void setConnectionKeepAliveStrategy(ConnectionKeepAliveStrategy connectionKeepAliveStrategy) {
        this.connectionKeepAliveStrategy = connectionKeepAliveStrategy;
    }

    @Generated
    public void setConnectionBackoffStrategy(ConnectionBackoffStrategy connectionBackoffStrategy) {
        this.connectionBackoffStrategy = connectionBackoffStrategy;
    }

    @Generated
    public void setServiceUnavailableRetryStrategy(ServiceUnavailableRetryStrategy serviceUnavailableRetryStrategy) {
        this.serviceUnavailableRetryStrategy = serviceUnavailableRetryStrategy;
    }

    @Generated
    public void setHttpRequestRetryHandler(HttpRequestRetryHandler httpRequestRetryHandler) {
        this.httpRequestRetryHandler = httpRequestRetryHandler;
    }

    @Generated
    public void setDefaultHeaders(Collection<? extends Header> defaultHeaders) {
        this.defaultHeaders = defaultHeaders;
    }

    @Generated
    public void setProxyAuthenticationStrategy(AuthenticationStrategy proxyAuthenticationStrategy) {
        this.proxyAuthenticationStrategy = proxyAuthenticationStrategy;
    }

    @Generated
    public void setCircularRedirectsAllowed(boolean circularRedirectsAllowed) {
        this.circularRedirectsAllowed = circularRedirectsAllowed;
    }

    @Generated
    public void setAuthenticationEnabled(boolean authenticationEnabled) {
        this.authenticationEnabled = authenticationEnabled;
    }

    @Generated
    public void setRedirectsEnabled(boolean redirectsEnabled) {
        this.redirectsEnabled = redirectsEnabled;
    }

    @Generated
    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @Generated
    public void setProxy(HttpHost proxy) {
        this.proxy = proxy;
    }

    @Generated
    public int getThreadsNumber() {
        return this.threadsNumber;
    }

    @Generated
    public int getQueueSize() {
        return this.queueSize;
    }

    @Generated
    public int getMaxPooledConnections() {
        return this.maxPooledConnections;
    }

    @Generated
    public int getMaxConnectionsPerRoute() {
        return this.maxConnectionsPerRoute;
    }

    @Generated
    public List<Integer> getAcceptableCodes() {
        return this.acceptableCodes;
    }

    @Override
    @Generated
    public long getConnectionTimeout() {
        return this.connectionTimeout;
    }

    @Generated
    public int getSocketTimeout() {
        return this.socketTimeout;
    }

    @Generated
    public int getReadTimeout() {
        return this.readTimeout;
    }

    @Generated
    public RedirectStrategy getRedirectionStrategy() {
        return this.redirectionStrategy;
    }

    @Override
    @Generated
    public LayeredConnectionSocketFactory getSslSocketFactory() {
        return this.sslSocketFactory;
    }

    @Override
    @Generated
    public HostnameVerifier getHostnameVerifier() {
        return this.hostnameVerifier;
    }

    @Override
    @Generated
    public SSLContext getSslContext() {
        return this.sslContext;
    }

    @Override
    @Generated
    public TrustManager[] getTrustManagers() {
        return this.trustManagers;
    }

    @Generated
    public CredentialsProvider getCredentialsProvider() {
        return this.credentialsProvider;
    }

    @Generated
    public CookieStore getCookieStore() {
        return this.cookieStore;
    }

    @Generated
    public ConnectionReuseStrategy getConnectionReuseStrategy() {
        return this.connectionReuseStrategy;
    }

    @Generated
    public ConnectionKeepAliveStrategy getConnectionKeepAliveStrategy() {
        return this.connectionKeepAliveStrategy;
    }

    @Generated
    public ConnectionBackoffStrategy getConnectionBackoffStrategy() {
        return this.connectionBackoffStrategy;
    }

    @Generated
    public ServiceUnavailableRetryStrategy getServiceUnavailableRetryStrategy() {
        return this.serviceUnavailableRetryStrategy;
    }

    @Generated
    public HttpRequestRetryHandler getHttpRequestRetryHandler() {
        return this.httpRequestRetryHandler;
    }

    @Generated
    public Collection<? extends Header> getDefaultHeaders() {
        return this.defaultHeaders;
    }

    @Generated
    public AuthenticationStrategy getProxyAuthenticationStrategy() {
        return this.proxyAuthenticationStrategy;
    }

    @Generated
    public boolean isCircularRedirectsAllowed() {
        return this.circularRedirectsAllowed;
    }

    @Generated
    public boolean isAuthenticationEnabled() {
        return this.authenticationEnabled;
    }

    @Generated
    public boolean isRedirectsEnabled() {
        return this.redirectsEnabled;
    }

    @Generated
    public ExecutorService getExecutorService() {
        return this.executorService;
    }

    @Override
    @Generated
    public HttpHost getProxy() {
        return this.proxy;
    }

    public static class DefaultHttpClient
    extends SimpleHttpClientFactoryBean {
    }
}

