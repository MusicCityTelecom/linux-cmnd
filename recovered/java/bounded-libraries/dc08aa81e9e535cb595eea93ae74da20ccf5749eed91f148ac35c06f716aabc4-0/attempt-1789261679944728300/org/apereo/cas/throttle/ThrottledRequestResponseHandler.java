/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package org.apereo.cas.throttle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@FunctionalInterface
public interface ThrottledRequestResponseHandler {
    public static final String BEAN_NAME = "throttledRequestResponseHandler";

    public boolean handle(HttpServletRequest var1, HttpServletResponse var2);
}

