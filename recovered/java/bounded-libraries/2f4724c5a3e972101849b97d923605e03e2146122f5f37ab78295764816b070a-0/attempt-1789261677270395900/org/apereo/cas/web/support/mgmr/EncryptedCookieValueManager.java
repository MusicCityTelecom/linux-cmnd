/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  lombok.Generated
 *  org.apache.commons.lang3.ArrayUtils
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.util.crypto.CipherExecutor
 *  org.apereo.cas.web.cookie.CookieValueManager
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.web.support.mgmr;

import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import lombok.Generated;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.util.crypto.CipherExecutor;
import org.apereo.cas.web.cookie.CookieValueManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EncryptedCookieValueManager
implements CookieValueManager {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(EncryptedCookieValueManager.class);
    private static final long serialVersionUID = 6362136147071376270L;
    private final transient CipherExecutor<Serializable, Serializable> cipherExecutor;

    public final String buildCookieValue(String givenCookieValue, HttpServletRequest request) {
        String res = this.buildCompoundCookieValue(givenCookieValue, request);
        LOGGER.trace("Encoding cookie value [{}]", (Object)res);
        return ((Serializable)this.cipherExecutor.encode((Object)res, ArrayUtils.EMPTY_OBJECT_ARRAY)).toString();
    }

    public String obtainCookieValue(String cookie, HttpServletRequest request) {
        Serializable decoded = (Serializable)this.cipherExecutor.decode((Object)cookie, ArrayUtils.EMPTY_OBJECT_ARRAY);
        if (decoded == null) {
            LOGGER.trace("Could not decode cookie value [{}] for cookie", (Object)cookie);
            return null;
        }
        String cookieValue = decoded.toString();
        LOGGER.trace("Decoded cookie value is [{}]", (Object)cookieValue);
        if (StringUtils.isBlank((CharSequence)cookieValue)) {
            LOGGER.trace("Retrieved decoded cookie value is blank. Failed to decode cookie");
            return null;
        }
        return this.obtainValueFromCompoundCookie(cookieValue, request);
    }

    protected String buildCompoundCookieValue(String cookieValue, HttpServletRequest request) {
        return cookieValue;
    }

    protected String obtainValueFromCompoundCookie(String compoundValue, HttpServletRequest request) {
        return compoundValue;
    }

    @Generated
    public EncryptedCookieValueManager(CipherExecutor<Serializable, Serializable> cipherExecutor) {
        this.cipherExecutor = cipherExecutor;
    }
}

