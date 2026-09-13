/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.authentication;

import java.util.List;
import java.util.Map;
import org.apereo.cas.authentication.AttributeMappingRequest;
import org.apereo.cas.authentication.DefaultPrincipalAttributesMapper;

@FunctionalInterface
public interface PrincipalAttributesMapper {
    public Map<String, List<Object>> map(AttributeMappingRequest var1);

    public static PrincipalAttributesMapper defaultMapper() {
        return new DefaultPrincipalAttributesMapper();
    }
}

