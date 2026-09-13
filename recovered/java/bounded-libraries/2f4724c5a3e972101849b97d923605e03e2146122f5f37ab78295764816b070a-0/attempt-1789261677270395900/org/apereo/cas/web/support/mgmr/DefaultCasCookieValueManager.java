/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Splitter
 *  javax.servlet.http.HttpServletRequest
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.configuration.model.support.cookie.PinnableCookieProperties
 *  org.apereo.cas.util.HttpRequestUtils
 *  org.apereo.cas.util.RegexUtils
 *  org.apereo.cas.util.crypto.CipherExecutor
 *  org.apereo.inspektr.common.web.ClientInfo
 *  org.apereo.inspektr.common.web.ClientInfoHolder
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.web.support.mgmr;

import com.google.common.base.Splitter;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Stream;
import javax.servlet.http.HttpServletRequest;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.configuration.model.support.cookie.PinnableCookieProperties;
import org.apereo.cas.util.HttpRequestUtils;
import org.apereo.cas.util.RegexUtils;
import org.apereo.cas.util.crypto.CipherExecutor;
import org.apereo.cas.web.support.InvalidCookieException;
import org.apereo.cas.web.support.mgmr.EncryptedCookieValueManager;
import org.apereo.inspektr.common.web.ClientInfo;
import org.apereo.inspektr.common.web.ClientInfoHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultCasCookieValueManager
extends EncryptedCookieValueManager {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultCasCookieValueManager.class);
    private static final char COOKIE_FIELD_SEPARATOR = '@';
    private static final int COOKIE_FIELDS_LENGTH = 3;
    private static final long serialVersionUID = -2696352696382374584L;
    private final PinnableCookieProperties cookieProperties;

    public DefaultCasCookieValueManager(CipherExecutor<Serializable, Serializable> cipherExecutor, PinnableCookieProperties cookieProperties) {
        super(cipherExecutor);
        this.cookieProperties = cookieProperties;
    }

    @Override
    protected String buildCompoundCookieValue(String givenCookieValue, HttpServletRequest request) {
        StringBuilder builder = new StringBuilder(givenCookieValue);
        if (this.cookieProperties.isPinToSession()) {
            String userAgent;
            ClientInfo clientInfo = ClientInfoHolder.getClientInfo();
            if (clientInfo != null) {
                builder.append('@').append(clientInfo.getClientIpAddress());
            }
            if (StringUtils.isBlank((CharSequence)(userAgent = HttpRequestUtils.getHttpServletRequestUserAgent((HttpServletRequest)request)))) {
                throw new IllegalStateException("Request does not specify a user-agent");
            }
            builder.append('@').append(userAgent);
        } else {
            LOGGER.trace("Cookie session-pinning is disabled");
        }
        return builder.toString();
    }

    @Override
    protected String obtainValueFromCompoundCookie(String value, HttpServletRequest request) {
        String agent;
        String cookieUserAgent;
        List cookieParts = Splitter.on((String)String.valueOf('@')).splitToList((CharSequence)value);
        String cookieValue = (String)cookieParts.get(0);
        if (!this.cookieProperties.isPinToSession()) {
            LOGGER.trace("Cookie session-pinning is disabled. Returning cookie value as it was provided");
            return cookieValue;
        }
        if (cookieParts.size() != 3) {
            throw new InvalidCookieException("Invalid cookie. Required fields are missing");
        }
        String cookieIpAddress = (String)cookieParts.get(1);
        if (Stream.of(cookieValue, cookieIpAddress, cookieUserAgent = (String)cookieParts.get(2)).anyMatch(StringUtils::isBlank)) {
            throw new InvalidCookieException("Invalid cookie. Required fields are empty");
        }
        ClientInfo clientInfo = ClientInfoHolder.getClientInfo();
        if (clientInfo == null) {
            String message = "Unable to match required remote address " + cookieIpAddress + " because client ip at time of cookie creation is unknown";
            LOGGER.warn(message);
            throw new InvalidCookieException(message);
        }
        String clientIpAddress = clientInfo.getClientIpAddress();
        if (!cookieIpAddress.equals(clientIpAddress)) {
            if (StringUtils.isBlank((CharSequence)this.cookieProperties.getAllowedIpAddressesPattern()) || !RegexUtils.find((String)this.cookieProperties.getAllowedIpAddressesPattern(), (String)clientIpAddress)) {
                String message = "Invalid cookie. Required remote address " + cookieIpAddress + " does not match " + clientIpAddress;
                LOGGER.warn(message);
                throw new InvalidCookieException(message);
            }
            LOGGER.debug("Required remote address [{}] does not match [{}], but it's authorized to proceed", (Object)cookieIpAddress, (Object)clientIpAddress);
        }
        if (!cookieUserAgent.equals(agent = HttpRequestUtils.getHttpServletRequestUserAgent((HttpServletRequest)request))) {
            String message = "Invalid cookie. Required user-agent " + cookieUserAgent + " does not match " + agent;
            LOGGER.warn(message);
            throw new InvalidCookieException(message);
        }
        return cookieValue;
    }
}

