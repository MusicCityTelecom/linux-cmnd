/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.principal.ResponseBuilder
 *  org.apereo.cas.authentication.principal.ResponseBuilderLocator
 *  org.apereo.cas.authentication.principal.WebApplicationService
 */
package org.apereo.cas.authentication.principal;

import java.util.List;
import lombok.Generated;
import org.apereo.cas.authentication.principal.ResponseBuilder;
import org.apereo.cas.authentication.principal.ResponseBuilderLocator;
import org.apereo.cas.authentication.principal.WebApplicationService;

public class DefaultWebApplicationResponseBuilderLocator
implements ResponseBuilderLocator<WebApplicationService> {
    private static final long serialVersionUID = 388417797622191740L;
    private final List<ResponseBuilder> builders;

    public ResponseBuilder<WebApplicationService> locate(WebApplicationService service) {
        return this.builders.stream().filter(r -> r.supports(service)).findFirst().orElse(null);
    }

    @Generated
    public DefaultWebApplicationResponseBuilderLocator(List<ResponseBuilder> builders) {
        this.builders = builders;
    }
}

