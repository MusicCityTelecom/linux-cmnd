/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 */
package org.springframework.integration.jmx;

import java.util.Arrays;
import javax.management.ObjectName;
import org.springframework.integration.jmx.MBeanAttributeFilter;
import org.springframework.util.Assert;

public class NotNamedFieldsMBeanAttributeFilter
implements MBeanAttributeFilter {
    private final String[] namedFields;

    public NotNamedFieldsMBeanAttributeFilter(String ... namedFields) {
        Assert.notNull((Object)namedFields, (String)"'namedFields' must not be null");
        this.namedFields = Arrays.copyOf(namedFields, namedFields.length);
    }

    @Override
    public boolean accept(ObjectName objectName, String attributeName) {
        for (String namedField : this.namedFields) {
            if (!namedField.equals(attributeName)) continue;
            return false;
        }
        return true;
    }
}

