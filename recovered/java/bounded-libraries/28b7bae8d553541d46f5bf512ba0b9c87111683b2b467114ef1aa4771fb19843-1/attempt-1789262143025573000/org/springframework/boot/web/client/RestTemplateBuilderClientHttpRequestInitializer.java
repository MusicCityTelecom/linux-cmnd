/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.http.HttpHeaders
 *  org.springframework.http.client.ClientHttpRequest
 *  org.springframework.http.client.ClientHttpRequestInitializer
 */
package org.springframework.boot.web.client;

import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.boot.util.LambdaSafe;
import org.springframework.boot.web.client.BasicAuthentication;
import org.springframework.boot.web.client.RestTemplateRequestCustomizer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestInitializer;

class RestTemplateBuilderClientHttpRequestInitializer
implements ClientHttpRequestInitializer {
    private final BasicAuthentication basicAuthentication;
    private final Map<String, List<String>> defaultHeaders;
    private final Set<RestTemplateRequestCustomizer<?>> requestCustomizers;

    RestTemplateBuilderClientHttpRequestInitializer(BasicAuthentication basicAuthentication, Map<String, List<String>> defaultHeaders, Set<RestTemplateRequestCustomizer<?>> requestCustomizers) {
        this.basicAuthentication = basicAuthentication;
        this.defaultHeaders = defaultHeaders;
        this.requestCustomizers = requestCustomizers;
    }

    public void initialize(ClientHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        if (this.basicAuthentication != null) {
            this.basicAuthentication.applyTo(headers);
        }
        this.defaultHeaders.forEach((arg_0, arg_1) -> headers.putIfAbsent(arg_0, arg_1));
        LambdaSafe.callbacks(RestTemplateRequestCustomizer.class, this.requestCustomizers, request, new Object[0]).invoke(customizer -> customizer.customize(request));
    }
}

