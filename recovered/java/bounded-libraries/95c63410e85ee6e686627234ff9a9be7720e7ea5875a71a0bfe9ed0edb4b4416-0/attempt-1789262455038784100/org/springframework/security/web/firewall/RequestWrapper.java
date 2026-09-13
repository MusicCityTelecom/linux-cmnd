/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.RequestDispatcher
 *  javax.servlet.ServletException
 *  javax.servlet.ServletRequest
 *  javax.servlet.ServletResponse
 *  javax.servlet.http.HttpServletRequest
 */
package org.springframework.security.web.firewall;

import java.io.IOException;
import java.util.StringTokenizer;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.web.firewall.FirewalledRequest;

final class RequestWrapper
extends FirewalledRequest {
    private final String strippedServletPath;
    private final String strippedPathInfo;
    private boolean stripPaths = true;

    RequestWrapper(HttpServletRequest request) {
        super(request);
        this.strippedServletPath = this.strip(request.getServletPath());
        String pathInfo = this.strip(request.getPathInfo());
        if (pathInfo != null && pathInfo.length() == 0) {
            pathInfo = null;
        }
        this.strippedPathInfo = pathInfo;
    }

    private String strip(String path) {
        int doubleSlashIndex;
        if (path == null) {
            return null;
        }
        int semicolonIndex = path.indexOf(59);
        if (semicolonIndex < 0 && (doubleSlashIndex = path.indexOf("//")) < 0) {
            return path;
        }
        StringTokenizer tokenizer = new StringTokenizer(path, "/");
        StringBuilder stripped = new StringBuilder(path.length());
        if (path.charAt(0) == '/') {
            stripped.append('/');
        }
        while (tokenizer.hasMoreTokens()) {
            String segment = tokenizer.nextToken();
            semicolonIndex = segment.indexOf(59);
            if (semicolonIndex >= 0) {
                segment = segment.substring(0, semicolonIndex);
            }
            stripped.append(segment).append('/');
        }
        if (path.charAt(path.length() - 1) != '/') {
            stripped.deleteCharAt(stripped.length() - 1);
        }
        return stripped.toString();
    }

    public String getPathInfo() {
        return this.stripPaths ? this.strippedPathInfo : super.getPathInfo();
    }

    public String getServletPath() {
        return this.stripPaths ? this.strippedServletPath : super.getServletPath();
    }

    public RequestDispatcher getRequestDispatcher(String path) {
        return this.stripPaths ? new FirewalledRequestAwareRequestDispatcher(path) : super.getRequestDispatcher(path);
    }

    @Override
    public void reset() {
        this.stripPaths = false;
    }

    private class FirewalledRequestAwareRequestDispatcher
    implements RequestDispatcher {
        private final String path;

        FirewalledRequestAwareRequestDispatcher(String path) {
            this.path = path;
        }

        public void forward(ServletRequest request, ServletResponse response) throws ServletException, IOException {
            RequestWrapper.this.reset();
            this.getDelegateDispatcher().forward(request, response);
        }

        public void include(ServletRequest request, ServletResponse response) throws ServletException, IOException {
            this.getDelegateDispatcher().include(request, response);
        }

        private RequestDispatcher getDelegateDispatcher() {
            return RequestWrapper.super.getRequestDispatcher(this.path);
        }
    }
}

