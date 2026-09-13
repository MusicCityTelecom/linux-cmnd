/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.security.web.access.channel;

import org.springframework.security.web.access.channel.AbstractRetryEntryPoint;

public class RetryWithHttpsEntryPoint
extends AbstractRetryEntryPoint {
    public RetryWithHttpsEntryPoint() {
        super("https://", 443);
    }

    @Override
    protected Integer getMappedPort(Integer mapFromPort) {
        return this.getPortMapper().lookupHttpsPort(mapFromPort);
    }
}

