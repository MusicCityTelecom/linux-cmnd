/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.principal.PrincipalFactory
 *  org.apereo.cas.configuration.model.RestEndpointProperties
 *  org.springframework.core.io.Resource
 */
package org.apereo.cas.authentication.principal;

import lombok.Generated;
import org.apereo.cas.authentication.principal.DefaultPrincipalFactory;
import org.apereo.cas.authentication.principal.GroovyPrincipalFactory;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.authentication.principal.RestfulPrincipalFactory;
import org.apereo.cas.configuration.model.RestEndpointProperties;
import org.springframework.core.io.Resource;

public final class PrincipalFactoryUtils {
    public static PrincipalFactory newPrincipalFactory() {
        return new DefaultPrincipalFactory();
    }

    public static PrincipalFactory newGroovyPrincipalFactory(Resource resource) {
        return new GroovyPrincipalFactory(resource);
    }

    public static PrincipalFactory newRestfulPrincipalFactory(RestEndpointProperties properties) {
        return new RestfulPrincipalFactory(properties);
    }

    @Generated
    private PrincipalFactoryUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

