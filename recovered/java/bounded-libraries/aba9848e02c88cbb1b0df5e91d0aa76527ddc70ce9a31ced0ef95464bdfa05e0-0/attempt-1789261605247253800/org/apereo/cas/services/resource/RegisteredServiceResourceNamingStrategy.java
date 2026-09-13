/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.util.RegexUtils
 */
package org.apereo.cas.services.resource;

import java.util.regex.Pattern;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.util.RegexUtils;

@FunctionalInterface
public interface RegisteredServiceResourceNamingStrategy {
    public String build(RegisteredService var1, String var2);

    default public Pattern buildNamingPattern(String ... extensions) {
        Object pattern = "";
        if (extensions.length > 0) {
            pattern = String.join((CharSequence)"|", extensions);
        }
        if (extensions.length > 1) {
            pattern = "(" + (String)pattern + ")";
        }
        return RegexUtils.createPattern((String)"(\\w+-)+(\\d+)\\.".concat((String)pattern));
    }
}

