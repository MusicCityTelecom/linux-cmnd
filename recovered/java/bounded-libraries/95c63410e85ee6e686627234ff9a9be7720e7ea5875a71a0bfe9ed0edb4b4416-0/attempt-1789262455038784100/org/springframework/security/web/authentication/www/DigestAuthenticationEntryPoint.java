/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.core.Ordered
 *  org.springframework.core.log.LogMessage
 *  org.springframework.http.HttpStatus
 *  org.springframework.security.core.AuthenticationException
 *  org.springframework.util.Assert
 */
package org.springframework.security.web.authentication.www;

import java.io.IOException;
import java.util.Base64;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.Ordered;
import org.springframework.core.log.LogMessage;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.DigestAuthUtils;
import org.springframework.security.web.authentication.www.NonceExpiredException;
import org.springframework.util.Assert;

public class DigestAuthenticationEntryPoint
implements AuthenticationEntryPoint,
InitializingBean,
Ordered {
    private static final Log logger = LogFactory.getLog(DigestAuthenticationEntryPoint.class);
    private String key;
    private String realmName;
    private int nonceValiditySeconds = 300;
    private int order = Integer.MAX_VALUE;

    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void afterPropertiesSet() {
        Assert.hasLength((String)this.realmName, (String)"realmName must be specified");
        Assert.hasLength((String)this.key, (String)"key must be specified");
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        long expiryTime = System.currentTimeMillis() + (long)(this.nonceValiditySeconds * 1000);
        String signatureValue = DigestAuthUtils.md5Hex(expiryTime + ":" + this.key);
        String nonceValue = expiryTime + ":" + signatureValue;
        String nonceValueBase64 = new String(Base64.getEncoder().encode(nonceValue.getBytes()));
        String authenticateHeader = "Digest realm=\"" + this.realmName + "\", qop=\"auth\", nonce=\"" + nonceValueBase64 + "\"";
        if (authException instanceof NonceExpiredException) {
            authenticateHeader = authenticateHeader + ", stale=\"true\"";
        }
        logger.debug((Object)LogMessage.format((String)"WWW-Authenticate header sent to user agent: %s", (Object)authenticateHeader));
        response.addHeader("WWW-Authenticate", authenticateHeader);
        response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
    }

    public String getKey() {
        return this.key;
    }

    public int getNonceValiditySeconds() {
        return this.nonceValiditySeconds;
    }

    public String getRealmName() {
        return this.realmName;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setNonceValiditySeconds(int nonceValiditySeconds) {
        this.nonceValiditySeconds = nonceValiditySeconds;
    }

    public void setRealmName(String realmName) {
        this.realmName = realmName;
    }
}

