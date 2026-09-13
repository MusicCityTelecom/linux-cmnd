/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.services.RegisteredService
 */
package org.apereo.cas.services.resource;

import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.resource.RegisteredServiceResourceNamingStrategy;

public class DefaultRegisteredServiceResourceNamingStrategy
implements RegisteredServiceResourceNamingStrategy {
    @Override
    public String build(RegisteredService service, String extension) {
        return StringUtils.remove((String)service.getName(), (char)' ') + "-" + service.getId() + "." + extension;
    }
}

