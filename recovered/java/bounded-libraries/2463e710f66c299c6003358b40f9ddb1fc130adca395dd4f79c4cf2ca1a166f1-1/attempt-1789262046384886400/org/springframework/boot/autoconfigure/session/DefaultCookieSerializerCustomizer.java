/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.session.web.http.DefaultCookieSerializer
 */
package org.springframework.boot.autoconfigure.session;

import org.springframework.session.web.http.DefaultCookieSerializer;

@FunctionalInterface
public interface DefaultCookieSerializerCustomizer {
    public void customize(DefaultCookieSerializer var1);
}

