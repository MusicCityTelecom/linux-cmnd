/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  org.springframework.security.authentication.AuthenticationDetailsSource
 */
package org.springframework.security.web.authentication;

import javax.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

public class WebAuthenticationDetailsSource
implements AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> {
    public WebAuthenticationDetails buildDetails(HttpServletRequest context) {
        return new WebAuthenticationDetails(context);
    }
}

