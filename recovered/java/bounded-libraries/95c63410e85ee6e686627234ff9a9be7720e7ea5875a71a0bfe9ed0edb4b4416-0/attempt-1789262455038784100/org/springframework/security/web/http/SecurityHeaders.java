/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.http.HttpHeaders
 *  org.springframework.util.Assert
 */
package org.springframework.security.web.http;

import java.util.function.Consumer;
import org.springframework.http.HttpHeaders;
import org.springframework.util.Assert;

public final class SecurityHeaders {
    private SecurityHeaders() {
    }

    public static Consumer<HttpHeaders> bearerToken(String bearerTokenValue) {
        Assert.hasText((String)bearerTokenValue, (String)"bearerTokenValue cannot be null");
        return headers -> headers.set("Authorization", "Bearer " + bearerTokenValue);
    }
}

