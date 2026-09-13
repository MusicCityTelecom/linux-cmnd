/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.util;

import org.apereo.cas.util.HostNameBasedUniqueTicketIdGenerator;

public class ServiceTicketIdGenerator
extends HostNameBasedUniqueTicketIdGenerator {
    public ServiceTicketIdGenerator(int maxLength, String suffix) {
        super(maxLength, suffix);
    }
}

