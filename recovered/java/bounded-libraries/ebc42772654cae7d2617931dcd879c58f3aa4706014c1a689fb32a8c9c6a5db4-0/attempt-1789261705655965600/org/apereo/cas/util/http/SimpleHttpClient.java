/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.io.IOUtils
 *  org.apache.http.HttpEntity
 *  org.apache.http.client.ResponseHandler
 *  org.apache.http.client.methods.CloseableHttpResponse
 *  org.apache.http.client.methods.HttpGet
 *  org.apache.http.client.methods.HttpPost
 *  org.apache.http.client.methods.HttpUriRequest
 *  org.apache.http.client.protocol.HttpClientContext
 *  org.apache.http.entity.ContentType
 *  org.apache.http.entity.StringEntity
 *  org.apache.http.impl.client.CloseableHttpClient
 *  org.apache.http.impl.client.FutureRequestExecutionService
 *  org.apache.http.impl.client.HttpRequestFutureTask
 *  org.apache.http.protocol.HttpContext
 *  org.apache.http.util.EntityUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.DisposableBean
 */
package org.apereo.cas.util.http;

import java.io.Closeable;
import java.io.InputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;
import lombok.Generated;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.FutureRequestExecutionService;
import org.apache.http.impl.client.HttpRequestFutureTask;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.apereo.cas.util.LoggingUtils;
import org.apereo.cas.util.http.HttpClient;
import org.apereo.cas.util.http.HttpMessage;
import org.apereo.cas.util.http.SimpleHttpClientFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;

public class SimpleHttpClient
implements HttpClient,
Serializable,
DisposableBean {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleHttpClient.class);
    private static final long serialVersionUID = -4949380008568071855L;
    private final List<Integer> acceptableCodes;
    private final transient CloseableHttpClient wrappedHttpClient;
    private final FutureRequestExecutionService requestExecutorService;
    private final SimpleHttpClientFactoryBean httpClientFactory;

    @Override
    public boolean sendMessageToEndPoint(HttpMessage message) {
        try {
            HttpPost request = new HttpPost(message.getUrl().toURI());
            request.addHeader("Content-Type", message.getContentType());
            StringEntity entity = new StringEntity(message.getMessage(), ContentType.create((String)message.getContentType()));
            request.setEntity((HttpEntity)entity);
            ResponseHandler handler = response -> response.getStatusLine().getStatusCode() == 200;
            LOGGER.trace("Created HTTP post message payload [{}]", (Object)request);
            HttpRequestFutureTask task = this.requestExecutorService.execute((HttpUriRequest)request, (HttpContext)HttpClientContext.create(), handler);
            return message.isAsynchronous() || (Boolean)task.get() != false;
        }
        catch (RejectedExecutionException e) {
            LoggingUtils.warn(LOGGER, e);
            return false;
        }
        catch (Exception e) {
            LOGGER.debug("Unable to send message", (Throwable)e);
            return false;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public HttpMessage sendMessageToEndPoint(URL url) {
        try {
            CloseableHttpResponse response = this.wrappedHttpClient.execute((HttpUriRequest)new HttpGet(url.toURI()));
            int responseCode = response.getStatusLine().getStatusCode();
            for (Integer acceptableCode : this.acceptableCodes) {
                if (responseCode != acceptableCode) continue;
                LOGGER.debug("Response code received from server matched [{}].", (Object)responseCode);
                HttpEntity entity = response.getEntity();
                try {
                    HttpMessage msg = new HttpMessage(url, IOUtils.toString((InputStream)entity.getContent(), (Charset)StandardCharsets.UTF_8));
                    msg.setContentType(entity.getContentType().getValue());
                    msg.setResponseCode(responseCode);
                    HttpMessage httpMessage = msg;
                    return httpMessage;
                }
                finally {
                    EntityUtils.consumeQuietly((HttpEntity)entity);
                }
            }
            LOGGER.warn("Response code [{}] from [{}] did not match any of the acceptable response codes.", (Object)responseCode, (Object)url);
            if (responseCode != 500) return null;
            String value = response.getStatusLine().getReasonPhrase();
            LOGGER.error("There was an error contacting the endpoint: [{}]; The error:\n[{}]", (Object)url.toExternalForm(), (Object)value);
            return null;
            finally {
                if (response != null) {
                    response.close();
                }
            }
        }
        catch (Exception e) {
            LoggingUtils.error(LOGGER, e);
        }
        return null;
    }

    @Override
    public boolean isValidEndPoint(String url) {
        try {
            URL u = new URL(url);
            return this.isValidEndPoint(u);
        }
        catch (MalformedURLException e) {
            LoggingUtils.error(LOGGER, e);
            return false;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean isValidEndPoint(URL url) {
        try (CloseableHttpResponse response = this.wrappedHttpClient.execute((HttpUriRequest)new HttpGet(url.toURI()));){
            int responseCode = response.getStatusLine().getStatusCode();
            int idx = Collections.binarySearch(this.acceptableCodes, responseCode);
            if (idx >= 0) {
                LOGGER.debug("Response code from server matched [{}].", (Object)responseCode);
                boolean bl = true;
                return bl;
            }
            LOGGER.debug("Response code did not match any of the acceptable response codes. Code returned was [{}]", (Object)responseCode);
            if (responseCode == 500) {
                String value = response.getStatusLine().getReasonPhrase();
                LOGGER.error("There was an error contacting the endpoint: [{}]; The error was:\n[{}]", (Object)url.toExternalForm(), (Object)value);
            }
            HttpEntity entity = response.getEntity();
            try {
                LOGGER.debug("Located entity with length [{}]", (Object)entity.getContentLength());
                return false;
            }
            finally {
                EntityUtils.consumeQuietly((HttpEntity)entity);
            }
        }
        catch (Exception e) {
            LoggingUtils.error(LOGGER, e);
        }
        return false;
    }

    public void destroy() {
        IOUtils.closeQuietly((Closeable)this.wrappedHttpClient);
        IOUtils.closeQuietly((Closeable)this.requestExecutorService);
        this.httpClientFactory.destroy();
    }

    @Generated
    public List<Integer> getAcceptableCodes() {
        return this.acceptableCodes;
    }

    @Generated
    public CloseableHttpClient getWrappedHttpClient() {
        return this.wrappedHttpClient;
    }

    @Generated
    public FutureRequestExecutionService getRequestExecutorService() {
        return this.requestExecutorService;
    }

    @Override
    @Generated
    public SimpleHttpClientFactoryBean getHttpClientFactory() {
        return this.httpClientFactory;
    }

    @Generated
    public SimpleHttpClient(List<Integer> acceptableCodes, CloseableHttpClient wrappedHttpClient, FutureRequestExecutionService requestExecutorService, SimpleHttpClientFactoryBean httpClientFactory) {
        this.acceptableCodes = acceptableCodes;
        this.wrappedHttpClient = wrappedHttpClient;
        this.requestExecutorService = requestExecutorService;
        this.httpClientFactory = httpClientFactory;
    }
}

