/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.util.InetAddressUtils
 */
package org.apereo.cas.util;

import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.util.DefaultUniqueTicketIdGenerator;
import org.apereo.cas.util.InetAddressUtils;

public class HostNameBasedUniqueTicketIdGenerator
extends DefaultUniqueTicketIdGenerator {
    public HostNameBasedUniqueTicketIdGenerator(long maxLength, String suffix) {
        super(maxLength, HostNameBasedUniqueTicketIdGenerator.determineTicketSuffixByHostName(suffix));
    }

    private static String determineTicketSuffixByHostName(String suffix) {
        if (StringUtils.isNotBlank((CharSequence)suffix)) {
            return suffix;
        }
        return InetAddressUtils.getCasServerHostName();
    }
}

