/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.security.web.access.channel;

import org.springframework.security.web.access.channel.AbstractRetryEntryPoint;

public class RetryWithHttpEntryPoint
extends AbstractRetryEntryPoint {
    public RetryWithHttpEntryPoint() {
        super("http://", 80);
    }

    @Override
    protected Integer getMappedPort(Integer mapFromPort) {
        return this.getPortMapper().lookupHttpPort(mapFromPort);
    }
}

