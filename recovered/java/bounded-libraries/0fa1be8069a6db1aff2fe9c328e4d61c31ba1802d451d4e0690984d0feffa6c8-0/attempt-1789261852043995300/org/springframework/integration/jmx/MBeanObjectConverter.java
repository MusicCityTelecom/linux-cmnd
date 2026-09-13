/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.jmx;

import javax.management.MBeanServerConnection;
import javax.management.ObjectInstance;

@FunctionalInterface
public interface MBeanObjectConverter {
    public Object convert(MBeanServerConnection var1, ObjectInstance var2);
}

