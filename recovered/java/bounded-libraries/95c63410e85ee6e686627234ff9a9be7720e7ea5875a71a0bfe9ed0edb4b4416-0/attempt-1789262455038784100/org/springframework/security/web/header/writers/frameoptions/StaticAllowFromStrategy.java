/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 */
package org.springframework.security.web.header.writers.frameoptions;

import java.net.URI;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.web.header.writers.frameoptions.AllowFromStrategy;

@Deprecated
public final class StaticAllowFromStrategy
implements AllowFromStrategy {
    private final URI uri;

    public StaticAllowFromStrategy(URI uri) {
        this.uri = uri;
    }

    @Override
    public String getAllowFromValue(HttpServletRequest request) {
        return this.uri.toString();
    }
}

