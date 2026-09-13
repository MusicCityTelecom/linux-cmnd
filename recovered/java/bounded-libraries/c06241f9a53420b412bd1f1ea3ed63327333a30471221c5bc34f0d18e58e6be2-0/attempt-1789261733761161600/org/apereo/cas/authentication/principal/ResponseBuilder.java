/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  org.springframework.core.Ordered
 */
package org.apereo.cas.authentication.principal;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.io.Serializable;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.principal.Response;
import org.apereo.cas.authentication.principal.WebApplicationService;
import org.springframework.core.Ordered;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public interface ResponseBuilder<T extends WebApplicationService>
extends Serializable,
Ordered {
    public Response build(T var1, String var2, Authentication var3);

    default public boolean supports(T service) {
        return service != null;
    }
}

