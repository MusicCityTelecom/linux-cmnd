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
import org.springframework.security.web.firewall.FirewalledResponse;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.security.web.firewall.RequestWrapper;

public class DefaultHttpFirewall
implements HttpFirewall {
    private boolean allowUrlEncodedSlash;

    @Override
    public FirewalledRequest getFirewalledRequest(HttpServletRequest request) throws RequestRejectedException {
        RequestWrapper firewalledRequest = new RequestWrapper(request);
        if (!this.isNormalized(firewalledRequest.getServletPath()) || !this.isNormalized(firewalledRequest.getPathInfo())) {
            throw new RequestRejectedException("Un-normalized paths are not supported: " + firewalledRequest.getServletPath() + (firewalledRequest.getPathInfo() != null ? firewalledRequest.getPathInfo() : ""));
        }
        String requestURI = firewalledRequest.getRequestURI();
        if (this.containsInvalidUrlEncodedSlash(requestURI)) {
            throw new RequestRejectedException("The requestURI cannot contain encoded slash. Got " + requestURI);
        }
        return firewalledRequest;
    }

    @Override
    public HttpServletResponse getFirewalledResponse(HttpServletResponse response) {
        return new FirewalledResponse(response);
    }

    public void setAllowUrlEncodedSlash(boolean allowUrlEncodedSlash) {
        this.allowUrlEncodedSlash = allowUrlEncodedSlash;
    }

    private boolean containsInvalidUrlEncodedSlash(String uri) {
        if (this.allowUrlEncodedSlash || uri == null) {
            return false;
        }
        return uri.contains("%2f") || uri.contains("%2F");
    }

    private boolean isNormalized(String path) {
        if (path == null) {
            return true;
        }
        int i = path.length();
        while (i > 0) {
            int slashIndex = path.lastIndexOf(47, i - 1);
            int gap = i - slashIndex;
            if (gap == 2 && path.charAt(slashIndex + 1) == '.') {
                return false;
            }
            if (gap == 3 && path.charAt(slashIndex + 1) == '.' && path.charAt(slashIndex + 2) == '.') {
                return false;
            }
            i = slashIndex;
        }
        return true;
    }
}

