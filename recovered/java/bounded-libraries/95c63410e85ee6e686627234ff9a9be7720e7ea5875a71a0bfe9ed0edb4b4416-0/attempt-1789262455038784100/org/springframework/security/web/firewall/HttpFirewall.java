/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package org.springframework.security.web.firewall;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.web.firewall.FirewalledRequest;
import org.springframework.security.web.firewall.RequestRejectedException;

public interface HttpFirewall {
    public FirewalledRequest getFirewalledRequest(HttpServletRequest var1) throws RequestRejectedException;

    public HttpServletResponse getFirewalledResponse(HttpServletResponse var1);
}

