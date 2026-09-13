/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package org.springframework.security.web.csrf;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.web.csrf.CsrfToken;

public interface CsrfTokenRepository {
    public CsrfToken generateToken(HttpServletRequest var1);

    public void saveToken(CsrfToken var1, HttpServletRequest var2, HttpServletResponse var3);

    public CsrfToken loadToken(HttpServletRequest var1);
}

