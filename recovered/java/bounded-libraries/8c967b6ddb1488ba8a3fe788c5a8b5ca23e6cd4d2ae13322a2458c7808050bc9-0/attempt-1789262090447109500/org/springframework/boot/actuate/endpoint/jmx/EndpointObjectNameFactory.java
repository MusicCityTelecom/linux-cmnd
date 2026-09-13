/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.endpoint.jmx;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import org.springframework.boot.actuate.endpoint.jmx.ExposableJmxEndpoint;

@FunctionalInterface
public interface EndpointObjectNameFactory {
    public ObjectName getObjectName(ExposableJmxEndpoint var1) throws MalformedObjectNameException;
}

