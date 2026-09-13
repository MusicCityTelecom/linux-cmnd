/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.http.client.ClientHttpRequestFactory
 *  org.springframework.util.Assert
 *  org.springframework.util.ReflectionUtils
 *  org.springframework.ws.transport.WebServiceMessageSender
 *  org.springframework.ws.transport.http.ClientHttpRequestMessageSender
 */
package org.springframework.boot.webservices.client;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.function.Supplier;
import org.springframework.boot.web.client.ClientHttpRequestFactorySupplier;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;
import org.springframework.ws.transport.WebServiceMessageSender;
import org.springframework.ws.transport.http.ClientHttpRequestMessageSender;

public class HttpWebServiceMessageSenderBuilder {
    private Duration connectTimeout;
    private Duration readTimeout;
    private Supplier<ClientHttpRequestFactory> requestFactorySupplier;

    public HttpWebServiceMessageSenderBuilder setConnectTimeout(Duration connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    public HttpWebServiceMessageSenderBuilder setReadTimeout(Duration readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }

    public HttpWebServiceMessageSenderBuilder requestFactory(Supplier<ClientHttpRequestFactory> requestFactorySupplier) {
        Assert.notNull(requestFactorySupplier, (String)"RequestFactory Supplier must not be null");
        this.requestFactorySupplier = requestFactorySupplier;
        return this;
    }

    public WebServiceMessageSender build() {
        ClientHttpRequestFactory requestFactory;
        ClientHttpRequestFactory clientHttpRequestFactory = requestFactory = this.requestFactorySupplier != null ? this.requestFactorySupplier.get() : new ClientHttpRequestFactorySupplier().get();
        if (this.connectTimeout != null) {
            new TimeoutRequestFactoryCustomizer(this.connectTimeout, "setConnectTimeout").customize(requestFactory);
        }
        if (this.readTimeout != null) {
            new TimeoutRequestFactoryCustomizer(this.readTimeout, "setReadTimeout").customize(requestFactory);
        }
        return new ClientHttpRequestMessageSender(requestFactory);
    }

    private static class TimeoutRequestFactoryCustomizer {
        private final Duration timeout;
        private final String methodName;

        TimeoutRequestFactoryCustomizer(Duration timeout, String methodName) {
            this.timeout = timeout;
            this.methodName = methodName;
        }

        void customize(ClientHttpRequestFactory factory) {
            ReflectionUtils.invokeMethod((Method)this.findMethod(factory), (Object)factory, (Object[])new Object[]{Math.toIntExact(this.timeout.toMillis())});
        }

        private Method findMethod(ClientHttpRequestFactory factory) {
            Method method = ReflectionUtils.findMethod(factory.getClass(), (String)this.methodName, (Class[])new Class[]{Integer.TYPE});
            if (method != null) {
                return method;
            }
            throw new IllegalStateException("Request factory " + factory.getClass() + " does not have a " + this.methodName + "(int) method");
        }
    }
}

