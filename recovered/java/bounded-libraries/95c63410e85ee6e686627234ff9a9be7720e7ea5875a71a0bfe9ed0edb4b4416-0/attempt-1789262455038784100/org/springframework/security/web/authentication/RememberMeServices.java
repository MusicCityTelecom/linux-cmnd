/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.security.core.Authentication
 */
package org.springframework.security.web.authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;

public interface RememberMeServices {
    public Authentication autoLogin(HttpServletRequest var1, HttpServletResponse var2);

    public void loginFail(HttpServletRequest var1, HttpServletResponse var2);

    public void loginSuccess(HttpServletRequest var1, HttpServletResponse var2, Authentication var3);
}

