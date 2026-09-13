/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.http.HttpHost
 *  org.apache.http.conn.socket.LayeredConnectionSocketFactory
 *  org.springframework.beans.factory.DisposableBean
 *  org.springframework.beans.factory.FactoryBean
 */
package org.apereo.cas.util.http;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import org.apache.http.HttpHost;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;

public interface HttpClientFactory
extends FactoryBean,
DisposableBean {
    public HttpHost getProxy();

    public LayeredConnectionSocketFactory getSslSocketFactory();

    public HostnameVerifier getHostnameVerifier();

    public long getConnectionTimeout();

    public SSLContext getSslContext();

    public TrustManager[] getTrustManagers();
}

