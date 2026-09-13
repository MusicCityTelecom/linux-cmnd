/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.jmx;

import javax.management.ObjectName;
import org.springframework.integration.jmx.MBeanAttributeFilter;

public class DefaultMBeanAttributeFilter
implements MBeanAttributeFilter {
    @Override
    public boolean accept(ObjectName objectName, String attributeName) {
        return true;
    }
}

