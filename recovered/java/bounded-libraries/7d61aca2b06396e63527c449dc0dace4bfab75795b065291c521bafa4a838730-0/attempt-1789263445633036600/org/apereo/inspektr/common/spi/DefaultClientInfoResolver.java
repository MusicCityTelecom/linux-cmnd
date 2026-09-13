/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.aspectj.lang.JoinPoint
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.inspektr.common.spi;

import org.apereo.inspektr.common.spi.ClientInfoResolver;
import org.apereo.inspektr.common.web.ClientInfo;
import org.apereo.inspektr.common.web.ClientInfoHolder;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultClientInfoResolver
implements ClientInfoResolver {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public ClientInfo resolveFrom(JoinPoint joinPoint, Object retVal) {
        ClientInfo clientInfo = ClientInfoHolder.getClientInfo();
        if (clientInfo != null) {
            return clientInfo;
        }
        this.log.warn("No ClientInfo could be found.  Returning empty ClientInfo object.");
        return ClientInfo.EMPTY_CLIENT_INFO;
    }
}

