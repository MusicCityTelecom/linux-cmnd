/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.ws.rs.container.ContainerRequestContext
 */
package org.springframework.boot.actuate.endpoint.web.jersey;

import javax.ws.rs.container.ContainerRequestContext;

interface JerseyRemainingPathSegmentProvider {
    public String get(ContainerRequestContext var1, String var2);
}

