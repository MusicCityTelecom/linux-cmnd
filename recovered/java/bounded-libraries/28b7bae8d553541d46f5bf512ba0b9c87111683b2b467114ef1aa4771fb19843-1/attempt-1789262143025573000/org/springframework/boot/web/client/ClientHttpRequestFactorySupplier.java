/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeanUtils
 *  org.springframework.http.client.ClientHttpRequestFactory
 *  org.springframework.http.client.SimpleClientHttpRequestFactory
 *  org.springframework.util.ClassUtils
 */
package org.springframework.boot.web.client;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;
import org.springframework.beans.BeanUtils;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.ClassUtils;

public class ClientHttpRequestFactorySupplier
implements Supplier<ClientHttpRequestFactory> {
    private static final Map<String, String> REQUEST_FACTORY_CANDIDATES;

    @Override
    public ClientHttpRequestFactory get() {
        for (Map.Entry<String, String> candidate : REQUEST_FACTORY_CANDIDATES.entrySet()) {
            ClassLoader classLoader = this.getClass().getClassLoader();
            if (!ClassUtils.isPresent((String)candidate.getKey(), (ClassLoader)classLoader)) continue;
            Class factoryClass = ClassUtils.resolveClassName((String)candidate.getValue(), (ClassLoader)classLoader);
            return (ClientHttpRequestFactory)BeanUtils.instantiateClass((Class)factoryClass);
        }
        return new SimpleClientHttpRequestFactory();
    }

    static {
        LinkedHashMap<String, String> candidates = new LinkedHashMap<String, String>();
        candidates.put("org.apache.http.client.HttpClient", "org.springframework.http.client.HttpComponentsClientHttpRequestFactory");
        candidates.put("okhttp3.OkHttpClient", "org.springframework.http.client.OkHttp3ClientHttpRequestFactory");
        REQUEST_FACTORY_CANDIDATES = Collections.unmodifiableMap(candidates);
    }
}

