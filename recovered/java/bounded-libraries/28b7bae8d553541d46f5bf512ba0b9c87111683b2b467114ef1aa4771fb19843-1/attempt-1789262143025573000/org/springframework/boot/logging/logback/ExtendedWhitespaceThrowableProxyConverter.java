/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ch.qos.logback.classic.pattern.ExtendedThrowableProxyConverter
 *  ch.qos.logback.classic.spi.IThrowableProxy
 *  ch.qos.logback.core.CoreConstants
 */
package org.springframework.boot.logging.logback;

import ch.qos.logback.classic.pattern.ExtendedThrowableProxyConverter;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.core.CoreConstants;

public class ExtendedWhitespaceThrowableProxyConverter
extends ExtendedThrowableProxyConverter {
    protected String throwableProxyToString(IThrowableProxy tp) {
        return CoreConstants.LINE_SEPARATOR + super.throwableProxyToString(tp) + CoreConstants.LINE_SEPARATOR;
    }
}

