/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.security.core.Authentication
 *  org.springframework.security.core.userdetails.UserDetails
 *  org.springframework.security.core.userdetails.UserDetailsService
 *  org.springframework.security.crypto.codec.Hex
 *  org.springframework.security.crypto.codec.Utf8
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 */
package org.springframework.security.web.authentication.rememberme;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.codec.Utf8;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.security.web.authentication.rememberme.InvalidCookieException;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class TokenBasedRememberMeServices
extends AbstractRememberMeServices {
    public TokenBasedRememberMeServices(String key, UserDetailsService userDetailsService) {
        super(key, userDetailsService);
    }

    @Override
    protected UserDetails processAutoLoginCookie(String[] cookieTokens, HttpServletRequest request, HttpServletResponse response) {
        if (cookieTokens.length != 3) {
            throw new InvalidCookieException("Cookie token did not contain 3 tokens, but contained '" + Arrays.asList(cookieTokens) + "'");
        }
        long tokenExpiryTime = this.getTokenExpiryTime(cookieTokens);
        if (this.isTokenExpired(tokenExpiryTime)) {
            throw new InvalidCookieException("Cookie token[1] has expired (expired on '" + new Date(tokenExpiryTime) + "'; current time is '" + new Date() + "')");
        }
        UserDetails userDetails = this.getUserDetailsService().loadUserByUsername(cookieTokens[0]);
        Assert.notNull((Object)userDetails, () -> "UserDetailsService " + this.getUserDetailsService() + " returned null for username " + cookieTokens[0] + ". This is an interface contract violation");
        String expectedTokenSignature = this.makeTokenSignature(tokenExpiryTime, userDetails.getUsername(), userDetails.getPassword());
        if (!TokenBasedRememberMeServices.equals(expectedTokenSignature, cookieTokens[2])) {
            throw new InvalidCookieException("Cookie token[2] contained signature '" + cookieTokens[2] + "' but expected '" + expectedTokenSignature + "'");
        }
        return userDetails;
    }

    private long getTokenExpiryTime(String[] cookieTokens) {
        try {
            return new Long(cookieTokens[1]);
        }
        catch (NumberFormatException nfe) {
            throw new InvalidCookieException("Cookie token[1] did not contain a valid number (contained '" + cookieTokens[1] + "')");
        }
    }

    protected String makeTokenSignature(long tokenExpiryTime, String username, String password) {
        String data = username + ":" + tokenExpiryTime + ":" + password + ":" + this.getKey();
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            return new String(Hex.encode((byte[])digest.digest(data.getBytes())));
        }
        catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException("No MD5 algorithm available!");
        }
    }

    protected boolean isTokenExpired(long tokenExpiryTime) {
        return tokenExpiryTime < System.currentTimeMillis();
    }

    @Override
    public void onLoginSuccess(HttpServletRequest request, HttpServletResponse response, Authentication successfulAuthentication) {
        UserDetails user;
        String username = this.retrieveUserName(successfulAuthentication);
        String password = this.retrievePassword(successfulAuthentication);
        if (!StringUtils.hasLength((String)username)) {
            this.logger.debug((Object)"Unable to retrieve username");
            return;
        }
        if (!StringUtils.hasLength((String)password) && !StringUtils.hasLength((String)(password = (user = this.getUserDetailsService().loadUserByUsername(username)).getPassword()))) {
            this.logger.debug((Object)("Unable to obtain password for user: " + username));
            return;
        }
        int tokenLifetime = this.calculateLoginLifetime(request, successfulAuthentication);
        long expiryTime = System.currentTimeMillis();
        String signatureValue = this.makeTokenSignature(expiryTime += 1000L * (long)(tokenLifetime < 0 ? 1209600 : tokenLifetime), username, password);
        this.setCookie(new String[]{username, Long.toString(expiryTime), signatureValue}, tokenLifetime, request, response);
        if (this.logger.isDebugEnabled()) {
            this.logger.debug((Object)("Added remember-me cookie for user '" + username + "', expiry: '" + new Date(expiryTime) + "'"));
        }
    }

    protected int calculateLoginLifetime(HttpServletRequest request, Authentication authentication) {
        return this.getTokenValiditySeconds();
    }

    protected String retrieveUserName(Authentication authentication) {
        if (this.isInstanceOfUserDetails(authentication)) {
            return ((UserDetails)authentication.getPrincipal()).getUsername();
        }
        return authentication.getPrincipal().toString();
    }

    protected String retrievePassword(Authentication authentication) {
        if (this.isInstanceOfUserDetails(authentication)) {
            return ((UserDetails)authentication.getPrincipal()).getPassword();
        }
        if (authentication.getCredentials() != null) {
            return authentication.getCredentials().toString();
        }
        return null;
    }

    private boolean isInstanceOfUserDetails(Authentication authentication) {
        return authentication.getPrincipal() instanceof UserDetails;
    }

    private static boolean equals(String expected, String actual) {
        byte[] expectedBytes = TokenBasedRememberMeServices.bytesUtf8(expected);
        byte[] actualBytes = TokenBasedRememberMeServices.bytesUtf8(actual);
        return MessageDigest.isEqual(expectedBytes, actualBytes);
    }

    private static byte[] bytesUtf8(String s) {
        return s != null ? Utf8.encode((CharSequence)s) : null;
    }
}

