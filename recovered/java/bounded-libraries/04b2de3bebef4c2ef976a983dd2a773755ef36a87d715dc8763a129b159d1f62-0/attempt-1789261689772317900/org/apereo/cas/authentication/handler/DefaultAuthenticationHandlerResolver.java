/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.authentication.AuthenticationHandlerResolver
 */
package org.apereo.cas.authentication.handler;

import org.apereo.cas.authentication.AuthenticationHandlerResolver;

public class DefaultAuthenticationHandlerResolver
implements AuthenticationHandlerResolver {
    public int getOrder() {
        return Integer.MAX_VALUE;
    }
}

