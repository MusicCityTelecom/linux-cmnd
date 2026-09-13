/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.http.HttpHeaders
 *  org.springframework.util.Assert
 */
package org.springframework.boot.web.client;

import java.nio.charset.Charset;
import org.springframework.http.HttpHeaders;
import org.springframework.util.Assert;

class BasicAuthentication {
    private final String username;
    private final String password;
    private final Charset charset;

    BasicAuthentication(String username, String password, Charset charset) {
        Assert.notNull((Object)username, (String)"Username must not be null");
        Assert.notNull((Object)password, (String)"Password must not be null");
        this.username = username;
        this.password = password;
        this.charset = charset;
    }

    void applyTo(HttpHeaders headers) {
        if (!headers.containsKey((Object)"Authorization")) {
            headers.setBasicAuth(this.username, this.password, this.charset);
        }
    }
}

