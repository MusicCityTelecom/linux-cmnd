/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.authentication.AuthenticationResult
 *  org.apereo.cas.authentication.principal.WebApplicationService
 *  org.springframework.core.Ordered
 *  org.springframework.http.ResponseEntity
 */
package org.apereo.cas.rest.factory;

import org.apereo.cas.authentication.AuthenticationResult;
import org.apereo.cas.authentication.principal.WebApplicationService;
import org.springframework.core.Ordered;
import org.springframework.http.ResponseEntity;

public interface ServiceTicketResourceEntityResponseFactory
extends Ordered {
    public ResponseEntity<String> build(String var1, WebApplicationService var2, AuthenticationResult var3);

    public boolean supports(WebApplicationService var1, AuthenticationResult var2);
}

