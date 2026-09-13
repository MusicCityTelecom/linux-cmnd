/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.web.codec.CodecCustomizer
 *  org.springframework.boot.web.reactive.function.client.WebClientCustomizer
 *  org.springframework.http.codec.CodecConfigurer
 *  org.springframework.web.reactive.function.client.WebClient$Builder
 */
package org.springframework.boot.autoconfigure.web.reactive.function.client;

import java.util.List;
import org.springframework.boot.web.codec.CodecCustomizer;
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.http.codec.CodecConfigurer;
import org.springframework.web.reactive.function.client.WebClient;

public class WebClientCodecCustomizer
implements WebClientCustomizer {
    private final List<CodecCustomizer> codecCustomizers;

    public WebClientCodecCustomizer(List<CodecCustomizer> codecCustomizers) {
        this.codecCustomizers = codecCustomizers;
    }

    public void customize(WebClient.Builder webClientBuilder) {
        webClientBuilder.codecs(codecs -> this.codecCustomizers.forEach(customizer -> customizer.customize((CodecConfigurer)codecs)));
    }
}

