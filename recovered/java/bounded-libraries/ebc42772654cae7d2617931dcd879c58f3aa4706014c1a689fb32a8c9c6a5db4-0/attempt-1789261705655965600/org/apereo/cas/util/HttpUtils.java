/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  lombok.NonNull
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.http.HttpEntity
 *  org.apache.http.HttpHost
 *  org.apache.http.HttpResponse
 *  org.apache.http.StatusLine
 *  org.apache.http.client.config.RequestConfig
 *  org.apache.http.client.config.RequestConfig$Builder
 *  org.apache.http.client.methods.CloseableHttpResponse
 *  org.apache.http.client.methods.HttpDelete
 *  org.apache.http.client.methods.HttpGet
 *  org.apache.http.client.methods.HttpPost
 *  org.apache.http.client.methods.HttpUriRequest
 *  org.apache.http.client.utils.URIBuilder
 *  org.apache.http.entity.StringEntity
 *  org.apache.http.impl.client.CloseableHttpClient
 *  org.apache.http.impl.client.HttpClientBuilder
 *  org.apache.http.message.BasicHttpResponse
 *  org.apache.http.message.BasicStatusLine
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.http.HttpHeaders
 *  org.springframework.http.HttpMethod
 *  org.springframework.http.MediaType
 */
package org.apereo.cas.util;

import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import javax.net.ssl.SSLHandshakeException;
import lombok.Generated;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.apereo.cas.util.CollectionUtils;
import org.apereo.cas.util.EncodingUtils;
import org.apereo.cas.util.LoggingUtils;
import org.apereo.cas.util.function.FunctionUtils;
import org.apereo.cas.util.http.HttpClient;
import org.apereo.cas.util.http.HttpClientFactory;
import org.apereo.cas.util.spring.SpringExpressionLanguageValueResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

public final class HttpUtils {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtils.class);
    private static final int MAX_CONNECTIONS = 200;
    private static final int MAX_CONNECTIONS_PER_ROUTE = 20;
    private static final int CONNECT_TIMEOUT_IN_MILLISECONDS = 500;
    private static final int CONNECTION_REQUEST_TIMEOUT_IN_MILLISECONDS = 5000;
    private static final int SOCKET_TIMEOUT_IN_MILLISECONDS = 10000;

    public static HttpResponse execute(HttpExecutionRequest execution) {
        URI uri = HttpUtils.buildHttpUri(execution.getUrl().trim(), execution.getParameters());
        HttpUriRequest request = HttpUtils.getHttpRequestByMethod(execution.getMethod().name().toLowerCase().trim(), execution.getEntity(), uri);
        try {
            SpringExpressionLanguageValueResolver expressionResolver = SpringExpressionLanguageValueResolver.getInstance();
            execution.getHeaders().forEach((key, value) -> {
                String headerValue = expressionResolver.resolve((String)value);
                String headerKey = expressionResolver.resolve((String)key);
                request.addHeader(headerKey, headerValue);
            });
            HttpUtils.prepareHttpRequest(request, execution);
            CloseableHttpClient client = HttpUtils.getHttpClient(execution);
            return client.execute(request);
        }
        catch (SSLHandshakeException e) {
            String sanitizedUrl = (String)FunctionUtils.doUnchecked(() -> new URIBuilder(execution.getUrl()).removeQuery().clearParameters().build().toASCIIString());
            LoggingUtils.error(LOGGER, "SSL error accessing: [" + sanitizedUrl + "]", e);
            return new BasicHttpResponse((StatusLine)new BasicStatusLine(request.getProtocolVersion(), 500, sanitizedUrl));
        }
        catch (Exception e) {
            LoggingUtils.error(LOGGER, e);
            return null;
        }
    }

    private static CloseableHttpClient getHttpClient(HttpExecutionRequest execution) throws Exception {
        HttpClientBuilder builder = HttpUtils.getHttpClientBuilder(execution);
        if (StringUtils.isNotBlank((CharSequence)execution.getProxyUrl())) {
            URL proxyEndpoint = new URL(execution.getProxyUrl());
            HttpHost proxy = new HttpHost(proxyEndpoint.getHost(), proxyEndpoint.getPort(), proxyEndpoint.getProtocol());
            builder.setProxy(proxy);
        }
        return builder.build();
    }

    public static void close(HttpResponse response) {
        if (response instanceof CloseableHttpResponse) {
            CloseableHttpResponse closeableHttpResponse = (CloseableHttpResponse)response;
            try {
                closeableHttpResponse.close();
            }
            catch (Exception e) {
                LoggingUtils.error(LOGGER, e);
            }
        }
    }

    public static HttpHeaders createBasicAuthHeaders(String basicAuthUser, String basicAuthPassword) {
        return HttpUtils.createBasicAuthHeaders(basicAuthUser, basicAuthPassword, "US-ASCII");
    }

    public static HttpHeaders createBasicAuthHeaders(String basicAuthUser, String basicAuthPassword, String basicCharset) {
        HttpHeaders acceptHeaders = new HttpHeaders();
        acceptHeaders.setAccept(CollectionUtils.wrap(MediaType.APPLICATION_JSON));
        if (StringUtils.isNotBlank((CharSequence)basicAuthUser) && StringUtils.isNotBlank((CharSequence)basicAuthPassword)) {
            String authorization = basicAuthUser + ":" + basicAuthPassword;
            String basic = EncodingUtils.encodeBase64(authorization.getBytes(Charset.forName(basicCharset)));
            acceptHeaders.set("Authorization", "Basic " + basic);
        }
        return acceptHeaders;
    }

    private static HttpUriRequest getHttpRequestByMethod(String method, String entity, URI uri) {
        if ("post".equalsIgnoreCase(method)) {
            HttpPost request = new HttpPost(uri);
            if (StringUtils.isNotBlank((CharSequence)entity)) {
                StringEntity stringEntity = new StringEntity(entity, StandardCharsets.UTF_8);
                request.setEntity((HttpEntity)stringEntity);
            }
            return request;
        }
        if ("delete".equalsIgnoreCase(method)) {
            return new HttpDelete(uri);
        }
        return new HttpGet(uri);
    }

    private static void prepareHttpRequest(HttpUriRequest request, HttpExecutionRequest execution) {
        if (execution.isBasicAuthentication()) {
            String auth = EncodingUtils.encodeBase64(execution.getBasicAuthUsername() + ":" + execution.getBasicAuthPassword());
            request.setHeader("Authorization", "Basic " + auth);
        }
        if (execution.isBearerAuthentication()) {
            request.setHeader("Authorization", "Bearer " + execution.getBearerToken());
        }
    }

    private static URI buildHttpUri(String url, Map<String, String> parameters) {
        return (URI)FunctionUtils.doUnchecked(() -> {
            URIBuilder uriBuilder = new URIBuilder(url);
            parameters.forEach((arg_0, arg_1) -> ((URIBuilder)uriBuilder).addParameter(arg_0, arg_1));
            return uriBuilder.build();
        });
    }

    private static HttpClientBuilder getHttpClientBuilder(HttpExecutionRequest execution) {
        RequestConfig.Builder requestConfig = RequestConfig.custom();
        requestConfig.setConnectTimeout(500);
        requestConfig.setConnectionRequestTimeout(5000);
        requestConfig.setSocketTimeout(10000);
        HttpClientBuilder builder = HttpClientBuilder.create().useSystemProperties().setMaxConnTotal(200).setMaxConnPerRoute(20).setDefaultRequestConfig(requestConfig.build());
        Optional.ofNullable(execution.getHttpClient()).ifPresent(client -> {
            HttpClientFactory httpClientFactory = client.getHttpClientFactory();
            builder.setSSLHostnameVerifier(httpClientFactory.getHostnameVerifier());
            builder.setSSLContext(httpClientFactory.getSslContext());
            builder.setSSLSocketFactory(httpClientFactory.getSslSocketFactory());
        });
        return builder;
    }

    @Generated
    private HttpUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static class HttpExecutionRequest {
        private final HttpClient httpClient;
        @NonNull
        private final HttpMethod method;
        @NonNull
        private final String url;
        private final String basicAuthUsername;
        private final String basicAuthPassword;
        private final String entity;
        private final String proxyUrl;
        private final String bearerToken;
        private final Map<String, String> parameters;
        private final Map<String, String> headers;

        private boolean isBasicAuthentication() {
            return StringUtils.isNotBlank((CharSequence)this.basicAuthUsername) && StringUtils.isNotBlank((CharSequence)this.basicAuthPassword);
        }

        private boolean isBearerAuthentication() {
            return StringUtils.isNotBlank((CharSequence)this.bearerToken);
        }

        @Generated
        private static Map<String, String> $default$parameters() {
            return new LinkedHashMap<String, String>();
        }

        @Generated
        private static Map<String, String> $default$headers() {
            return new LinkedHashMap<String, String>();
        }

        @Generated
        protected HttpExecutionRequest(HttpExecutionRequestBuilder<?, ?> b) {
            this.httpClient = b.httpClient;
            this.method = b.method;
            if (this.method == null) {
                throw new NullPointerException("method is marked non-null but is null");
            }
            this.url = b.url;
            if (this.url == null) {
                throw new NullPointerException("url is marked non-null but is null");
            }
            this.basicAuthUsername = b.basicAuthUsername;
            this.basicAuthPassword = b.basicAuthPassword;
            this.entity = b.entity;
            this.proxyUrl = b.proxyUrl;
            this.bearerToken = b.bearerToken;
            this.parameters = b.parameters$set ? b.parameters$value : HttpExecutionRequest.$default$parameters();
            this.headers = b.headers$set ? b.headers$value : HttpExecutionRequest.$default$headers();
        }

        @Generated
        public static HttpExecutionRequestBuilder<?, ?> builder() {
            return new HttpExecutionRequestBuilderImpl();
        }

        @Generated
        public HttpClient getHttpClient() {
            return this.httpClient;
        }

        @NonNull
        @Generated
        public HttpMethod getMethod() {
            return this.method;
        }

        @NonNull
        @Generated
        public String getUrl() {
            return this.url;
        }

        @Generated
        public String getBasicAuthUsername() {
            return this.basicAuthUsername;
        }

        @Generated
        public String getBasicAuthPassword() {
            return this.basicAuthPassword;
        }

        @Generated
        public String getEntity() {
            return this.entity;
        }

        @Generated
        public String getProxyUrl() {
            return this.proxyUrl;
        }

        @Generated
        public String getBearerToken() {
            return this.bearerToken;
        }

        @Generated
        public Map<String, String> getParameters() {
            return this.parameters;
        }

        @Generated
        public Map<String, String> getHeaders() {
            return this.headers;
        }

        @Generated
        private static final class HttpExecutionRequestBuilderImpl
        extends HttpExecutionRequestBuilder<HttpExecutionRequest, HttpExecutionRequestBuilderImpl> {
            @Generated
            private HttpExecutionRequestBuilderImpl() {
            }

            @Override
            @Generated
            protected HttpExecutionRequestBuilderImpl self() {
                return this;
            }

            @Override
            @Generated
            public HttpExecutionRequest build() {
                return new HttpExecutionRequest(this);
            }
        }

        @Generated
        public static abstract class HttpExecutionRequestBuilder<C extends HttpExecutionRequest, B extends HttpExecutionRequestBuilder<C, B>> {
            @Generated
            private HttpClient httpClient;
            @Generated
            private HttpMethod method;
            @Generated
            private String url;
            @Generated
            private String basicAuthUsername;
            @Generated
            private String basicAuthPassword;
            @Generated
            private String entity;
            @Generated
            private String proxyUrl;
            @Generated
            private String bearerToken;
            @Generated
            private boolean parameters$set;
            @Generated
            private Map<String, String> parameters$value;
            @Generated
            private boolean headers$set;
            @Generated
            private Map<String, String> headers$value;

            @Generated
            protected abstract B self();

            @Generated
            public abstract C build();

            @Generated
            public B httpClient(HttpClient httpClient) {
                this.httpClient = httpClient;
                return this.self();
            }

            @Generated
            public B method(@NonNull HttpMethod method) {
                if (method == null) {
                    throw new NullPointerException("method is marked non-null but is null");
                }
                this.method = method;
                return this.self();
            }

            @Generated
            public B url(@NonNull String url) {
                if (url == null) {
                    throw new NullPointerException("url is marked non-null but is null");
                }
                this.url = url;
                return this.self();
            }

            @Generated
            public B basicAuthUsername(String basicAuthUsername) {
                this.basicAuthUsername = basicAuthUsername;
                return this.self();
            }

            @Generated
            public B basicAuthPassword(String basicAuthPassword) {
                this.basicAuthPassword = basicAuthPassword;
                return this.self();
            }

            @Generated
            public B entity(String entity) {
                this.entity = entity;
                return this.self();
            }

            @Generated
            public B proxyUrl(String proxyUrl) {
                this.proxyUrl = proxyUrl;
                return this.self();
            }

            @Generated
            public B bearerToken(String bearerToken) {
                this.bearerToken = bearerToken;
                return this.self();
            }

            @Generated
            public B parameters(Map<String, String> parameters) {
                this.parameters$value = parameters;
                this.parameters$set = true;
                return this.self();
            }

            @Generated
            public B headers(Map<String, String> headers) {
                this.headers$value = headers;
                this.headers$set = true;
                return this.self();
            }

            @Generated
            public String toString() {
                return "HttpUtils.HttpExecutionRequest.HttpExecutionRequestBuilder(httpClient=" + this.httpClient + ", method=" + this.method + ", url=" + this.url + ", basicAuthUsername=" + this.basicAuthUsername + ", basicAuthPassword=" + this.basicAuthPassword + ", entity=" + this.entity + ", proxyUrl=" + this.proxyUrl + ", bearerToken=" + this.bearerToken + ", parameters$value=" + this.parameters$value + ", headers$value=" + this.headers$value + ")";
            }
        }
    }
}

