/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 */
package org.apereo.cas.validation;

import java.util.Collection;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

@FunctionalInterface
public interface CasProtocolAttributesRenderer {
    public static String sanitizeAttributeName(String name) {
        return StringUtils.replace((String)name.trim(), (String)" ", (String)"_");
    }

    public Collection<String> render(Map<String, Object> var1);
}

