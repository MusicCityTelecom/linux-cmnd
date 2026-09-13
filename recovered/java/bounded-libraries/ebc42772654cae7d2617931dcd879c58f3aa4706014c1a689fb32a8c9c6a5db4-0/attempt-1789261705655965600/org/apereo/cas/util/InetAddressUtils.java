/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.jooq.lambda.Unchecked
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.util;

import java.net.InetAddress;
import java.net.URL;
import lombok.Generated;
import org.apereo.cas.util.function.FunctionUtils;
import org.jooq.lambda.Unchecked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class InetAddressUtils {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(InetAddressUtils.class);

    public static InetAddress getByName(String urlAddr) {
        return (InetAddress)FunctionUtils.doAndHandle(() -> {
            URL url = new URL(urlAddr);
            return InetAddress.getByName(url.getHost());
        }, e -> {
            LOGGER.trace("Host name could not be determined automatically.", e);
            return null;
        }).get();
    }

    public static String getCasServerHostName() {
        return (String)FunctionUtils.doAndHandle(() -> {
            String hostName = InetAddress.getLocalHost().getHostName();
            int index = hostName.indexOf(46);
            if (index > 0) {
                return hostName.substring(0, index);
            }
            return hostName;
        }, throwable -> "unknown").get();
    }

    public static String getCasServerHostAddress(String name) {
        return (String)Unchecked.supplier(() -> {
            InetAddress host = InetAddressUtils.getByName(name);
            return host != null ? host.getHostAddress() : null;
        }).get();
    }

    @Generated
    private InetAddressUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

