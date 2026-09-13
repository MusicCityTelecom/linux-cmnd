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

public interface ThrottledRequestExecutor {
    public static final String DEFAULT_BEAN_NAME = "throttledRequestExecutor";

    default public boolean throttle(HttpServletRequest request, HttpServletResponse response) {
        return false;
    }

    public static ThrottledRequestExecutor noOp() {
        return new ThrottledRequestExecutor(){};
    }
}

