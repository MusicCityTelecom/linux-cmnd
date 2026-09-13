/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletRequestWrapper
 */
package org.springframework.security.web.firewall;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public abstract class FirewalledRequest
extends HttpServletRequestWrapper {
    public FirewalledRequest(HttpServletRequest request) {
        super(request);
    }

    public abstract void reset();

    public String toString() {
        return "FirewalledRequest[ " + this.getRequest() + "]";
    }
}

