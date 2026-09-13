/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.webflow.execution.RequestContext
 */
package org.apereo.cas.authentication.adaptive;

import org.apereo.cas.authentication.adaptive.geo.GeoLocationRequest;
import org.springframework.webflow.execution.RequestContext;

@FunctionalInterface
public interface AdaptiveAuthenticationPolicy {
    public boolean apply(RequestContext var1, String var2, GeoLocationRequest var3);
}

