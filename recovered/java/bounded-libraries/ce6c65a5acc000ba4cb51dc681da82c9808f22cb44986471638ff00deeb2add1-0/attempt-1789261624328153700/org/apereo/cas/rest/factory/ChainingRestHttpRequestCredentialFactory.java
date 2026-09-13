/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  lombok.Generated
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.Credential
 *  org.apereo.cas.authentication.MultifactorAuthenticationProvider
 *  org.apereo.cas.util.spring.beans.BeanSupplier
 *  org.springframework.core.annotation.AnnotationAwareOrderComparator
 *  org.springframework.util.MultiValueMap
 */
package org.apereo.cas.rest.factory;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.servlet.http.HttpServletRequest;
import lombok.Generated;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.MultifactorAuthenticationProvider;
import org.apereo.cas.rest.factory.RestHttpRequestCredentialFactory;
import org.apereo.cas.util.spring.beans.BeanSupplier;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.util.MultiValueMap;

public class ChainingRestHttpRequestCredentialFactory
implements RestHttpRequestCredentialFactory {
    private final List<RestHttpRequestCredentialFactory> chain;

    public ChainingRestHttpRequestCredentialFactory(RestHttpRequestCredentialFactory ... chain) {
        this.chain = Stream.of(chain).sorted(Comparator.comparing(RestHttpRequestCredentialFactory::getOrder)).collect(Collectors.toList());
    }

    public void registerCredentialFactory(RestHttpRequestCredentialFactory factory) {
        if (BeanSupplier.isNotProxy((Object)factory)) {
            this.chain.add(factory);
            AnnotationAwareOrderComparator.sort(this.chain);
        }
    }

    @Override
    public List<Credential> fromRequest(HttpServletRequest request, MultiValueMap<String, String> requestBody) {
        return this.chain.stream().sorted(Comparator.comparing(RestHttpRequestCredentialFactory::getOrder)).map(f -> f.fromRequest(request, requestBody)).flatMap(Collection::stream).collect(Collectors.toList());
    }

    @Override
    public List<Credential> fromAuthentication(HttpServletRequest request, MultiValueMap<String, String> requestBody, Authentication authentication, MultifactorAuthenticationProvider provider) {
        return this.chain.stream().sorted(Comparator.comparing(RestHttpRequestCredentialFactory::getOrder)).map(f -> f.fromAuthentication(request, requestBody, authentication, provider)).flatMap(Collection::stream).collect(Collectors.toList());
    }

    @Generated
    public List<RestHttpRequestCredentialFactory> getChain() {
        return this.chain;
    }

    @Generated
    public ChainingRestHttpRequestCredentialFactory(List<RestHttpRequestCredentialFactory> chain) {
        this.chain = chain;
    }
}

