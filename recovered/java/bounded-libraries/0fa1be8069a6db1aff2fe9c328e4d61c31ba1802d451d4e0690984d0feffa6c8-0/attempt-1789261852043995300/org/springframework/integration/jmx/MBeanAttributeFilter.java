/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.jmx;

import javax.management.ObjectName;

@FunctionalInterface
public interface MBeanAttributeFilter {
    public boolean accept(ObjectName var1, String var2);
}

