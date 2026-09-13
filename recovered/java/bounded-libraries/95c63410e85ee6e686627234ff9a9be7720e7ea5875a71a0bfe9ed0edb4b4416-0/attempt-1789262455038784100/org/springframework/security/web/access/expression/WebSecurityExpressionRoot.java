/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  org.springframework.security.access.expression.SecurityExpressionRoot
 *  org.springframework.security.core.Authentication
 */
package org.springframework.security.web.access.expression;

import javax.servlet.http.HttpServletRequest;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.util.matcher.IpAddressMatcher;

public class WebSecurityExpressionRoot
extends SecurityExpressionRoot {
    public final HttpServletRequest request;

    public WebSecurityExpressionRoot(Authentication a, FilterInvocation fi) {
        super(a);
        this.request = fi.getRequest();
    }

    public boolean hasIpAddress(String ipAddress) {
        IpAddressMatcher matcher = new IpAddressMatcher(ipAddress);
        return matcher.matches(this.request);
    }
}

