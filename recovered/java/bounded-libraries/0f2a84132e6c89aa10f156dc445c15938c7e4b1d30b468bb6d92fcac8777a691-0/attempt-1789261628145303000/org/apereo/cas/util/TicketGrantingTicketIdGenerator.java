/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.util;

import org.apereo.cas.util.HostNameBasedUniqueTicketIdGenerator;

public class TicketGrantingTicketIdGenerator
extends HostNameBasedUniqueTicketIdGenerator {
    public TicketGrantingTicketIdGenerator(int maxLength, String suffix) {
        super(maxLength, suffix);
    }
}

