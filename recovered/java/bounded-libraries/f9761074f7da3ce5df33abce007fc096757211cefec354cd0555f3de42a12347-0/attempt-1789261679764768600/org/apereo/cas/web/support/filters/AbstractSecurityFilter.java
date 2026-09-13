/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.util.LoggingUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.web.support.filters;

import lombok.Generated;
import org.apereo.cas.util.LoggingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractSecurityFilter {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractSecurityFilter.class);
    public static final String THROW_ON_ERROR = "throwOnError";
    private static boolean THROW_ON_ERRORS;

    public static boolean isThrowOnErrors() {
        return THROW_ON_ERRORS;
    }

    public static void setThrowOnErrors(boolean throwOnErrors) {
        THROW_ON_ERRORS = throwOnErrors;
    }

    protected static void logException(Exception e) {
        LoggingUtils.error((Logger)LOGGER, (Throwable)e);
        if (AbstractSecurityFilter.isThrowOnErrors()) {
            throw new RuntimeException(e);
        }
    }
}

