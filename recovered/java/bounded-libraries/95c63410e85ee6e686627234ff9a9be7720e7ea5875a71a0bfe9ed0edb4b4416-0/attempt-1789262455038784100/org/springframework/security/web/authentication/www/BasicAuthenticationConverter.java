/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  org.springframework.security.authentication.AuthenticationDetailsSource
 *  org.springframework.security.authentication.BadCredentialsException
 *  org.springframework.security.authentication.UsernamePasswordAuthenticationToken
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 */
package org.springframework.security.web.authentication.www;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class BasicAuthenticationConverter
implements AuthenticationConverter {
    public static final String AUTHENTICATION_SCHEME_BASIC = "Basic";
    private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource;
    private Charset credentialsCharset = StandardCharsets.UTF_8;

    public BasicAuthenticationConverter() {
        this(new WebAuthenticationDetailsSource());
    }

    public BasicAuthenticationConverter(AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource) {
        this.authenticationDetailsSource = authenticationDetailsSource;
    }

    public Charset getCredentialsCharset() {
        return this.credentialsCharset;
    }

    public void setCredentialsCharset(Charset credentialsCharset) {
        this.credentialsCharset = credentialsCharset;
    }

    public AuthenticationDetailsSource<HttpServletRequest, ?> getAuthenticationDetailsSource() {
        return this.authenticationDetailsSource;
    }

    public void setAuthenticationDetailsSource(AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource) {
        Assert.notNull(authenticationDetailsSource, (String)"AuthenticationDetailsSource required");
        this.authenticationDetailsSource = authenticationDetailsSource;
    }

    public UsernamePasswordAuthenticationToken convert(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header == null) {
            return null;
        }
        if (!StringUtils.startsWithIgnoreCase((String)(header = header.trim()), (String)AUTHENTICATION_SCHEME_BASIC)) {
            return null;
        }
        if (header.equalsIgnoreCase(AUTHENTICATION_SCHEME_BASIC)) {
            throw new BadCredentialsException("Empty basic authentication token");
        }
        byte[] base64Token = header.substring(6).getBytes(StandardCharsets.UTF_8);
        byte[] decoded = this.decode(base64Token);
        String token = new String(decoded, this.getCredentialsCharset(request));
        int delim = token.indexOf(":");
        if (delim == -1) {
            throw new BadCredentialsException("Invalid basic authentication token");
        }
        UsernamePasswordAuthenticationToken result = UsernamePasswordAuthenticationToken.unauthenticated((Object)token.substring(0, delim), (Object)token.substring(delim + 1));
        result.setDetails(this.authenticationDetailsSource.buildDetails((Object)request));
        return result;
    }

    private byte[] decode(byte[] base64Token) {
        try {
            return Base64.getDecoder().decode(base64Token);
        }
        catch (IllegalArgumentException ex) {
            throw new BadCredentialsException("Failed to decode basic authentication token");
        }
    }

    protected Charset getCredentialsCharset(HttpServletRequest request) {
        return this.getCredentialsCharset();
    }
}

