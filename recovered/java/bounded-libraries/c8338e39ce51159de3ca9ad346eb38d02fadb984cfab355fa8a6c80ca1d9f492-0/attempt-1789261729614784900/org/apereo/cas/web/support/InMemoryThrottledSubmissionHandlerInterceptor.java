/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 */
package org.apereo.cas.web.support;

import javax.servlet.http.HttpServletRequest;
import org.apereo.cas.web.support.ThrottledSubmissionHandlerInterceptor;

@FunctionalInterface
public interface InMemoryThrottledSubmissionHandlerInterceptor
extends ThrottledSubmissionHandlerInterceptor {
    public String constructKey(HttpServletRequest var1);
}

