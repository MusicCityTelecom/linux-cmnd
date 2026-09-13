/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  org.springframework.security.core.Authentication
 */
package org.springframework.security.web.authentication;

import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

public interface AuthenticationConverter {
    public Authentication convert(HttpServletRequest var1);
}

