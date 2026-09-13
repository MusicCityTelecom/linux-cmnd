/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.core.Ordered
 *  org.springframework.http.HttpMethod
 */
package org.apereo.cas.throttle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;

@FunctionalInterface
public interface ThrottledRequestFilter
extends Ordered {
    public static ThrottledRequestFilter httpPost() {
        return (request, response) -> HttpMethod.POST.name().equals(request.getMethod());
    }

    public boolean supports(HttpServletRequest var1, HttpServletResponse var2);

    default public int getOrder() {
        return Integer.MIN_VALUE;
    }
}

