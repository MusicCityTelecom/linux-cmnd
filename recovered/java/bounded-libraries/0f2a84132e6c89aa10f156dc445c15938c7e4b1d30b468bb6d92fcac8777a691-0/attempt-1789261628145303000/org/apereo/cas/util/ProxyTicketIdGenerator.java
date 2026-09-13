/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.util;

import org.apereo.cas.util.HostNameBasedUniqueTicketIdGenerator;

public class ProxyTicketIdGenerator
extends HostNameBasedUniqueTicketIdGenerator {
    public ProxyTicketIdGenerator(long maxLength, String suffix) {
        super(maxLength, suffix);
    }
}

