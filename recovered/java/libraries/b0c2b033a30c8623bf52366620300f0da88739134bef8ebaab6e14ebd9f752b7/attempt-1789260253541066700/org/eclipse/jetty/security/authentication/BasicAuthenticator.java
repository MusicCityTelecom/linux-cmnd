/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.security.authentication;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.security.ServerAuthException;
import org.eclipse.jetty.security.UserAuthentication;
import org.eclipse.jetty.security.authentication.DeferredAuthentication;
import org.eclipse.jetty.security.authentication.LoginAuthenticator;
import org.eclipse.jetty.server.Authentication;
import org.eclipse.jetty.server.UserIdentity;

public class BasicAuthenticator
extends LoginAuthenticator {
    private Charset _charset;

    public Charset getCharset() {
        return this._charset;
    }

    public void setCharset(Charset charset) {
        this._charset = charset;
    }

    @Override
    public String getAuthMethod() {
        return "BASIC";
    }

    @Override
    public Authentication validateRequest(ServletRequest req, ServletResponse res, boolean mandatory) throws ServerAuthException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)res;
        String credentials = request.getHeader(HttpHeader.AUTHORIZATION.asString());
        try {
            String method;
            int space;
            if (!mandatory) {
                return new DeferredAuthentication(this);
            }
            if (credentials != null && (space = credentials.indexOf(32)) > 0 && "basic".equalsIgnoreCase(method = credentials.substring(0, space))) {
                String password;
                String username;
                UserIdentity user;
                int i;
                credentials = credentials.substring(space + 1);
                Charset charset = this.getCharset();
                if (charset == null) {
                    charset = StandardCharsets.ISO_8859_1;
                }
                if ((i = (credentials = new String(Base64.getDecoder().decode(credentials), charset)).indexOf(58)) > 0 && (user = this.login(username = credentials.substring(0, i), password = credentials.substring(i + 1), request)) != null) {
                    return new UserAuthentication(this.getAuthMethod(), user);
                }
            }
            if (DeferredAuthentication.isDeferred(response)) {
                return Authentication.UNAUTHENTICATED;
            }
            String value = "basic realm=\"" + this._loginService.getName() + "\"";
            Charset charset = this.getCharset();
            if (charset != null) {
                value = value + ", charset=\"" + charset.name() + "\"";
            }
            response.setHeader(HttpHeader.WWW_AUTHENTICATE.asString(), value);
            response.sendError(401);
            return Authentication.SEND_CONTINUE;
        }
        catch (IOException e) {
            throw new ServerAuthException(e);
        }
    }

    @Override
    public boolean secureResponse(ServletRequest req, ServletResponse res, boolean mandatory, Authentication.User validatedUser) throws ServerAuthException {
        return true;
    }
}

