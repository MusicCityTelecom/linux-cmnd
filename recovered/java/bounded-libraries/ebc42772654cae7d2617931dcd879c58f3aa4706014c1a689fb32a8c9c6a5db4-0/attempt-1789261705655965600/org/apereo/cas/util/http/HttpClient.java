/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.http.client.HttpClient
 */
package org.apereo.cas.util.http;

import java.net.URL;
import org.apereo.cas.util.http.HttpClientFactory;
import org.apereo.cas.util.http.HttpMessage;

public interface HttpClient {
    public static final String BEAN_NAME_HTTPCLIENT_TRUST_STORE = "supportsTrustStoreSslSocketFactoryHttpClient";
    public static final String BEAN_NAME_HTTPCLIENT_NO_REDIRECT = "noRedirectHttpClient";

    public boolean sendMessageToEndPoint(HttpMessage var1);

    public HttpMessage sendMessageToEndPoint(URL var1);

    public boolean isValidEndPoint(String var1);

    public boolean isValidEndPoint(URL var1);

    public org.apache.http.client.HttpClient getWrappedHttpClient();

    public HttpClientFactory getHttpClientFactory();
}

