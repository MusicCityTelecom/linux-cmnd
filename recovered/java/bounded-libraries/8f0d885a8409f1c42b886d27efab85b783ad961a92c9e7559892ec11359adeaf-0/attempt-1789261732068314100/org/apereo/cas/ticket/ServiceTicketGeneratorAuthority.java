/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.authentication.AuthenticationResult
 *  org.apereo.cas.authentication.principal.Service
 *  org.springframework.core.Ordered
 */
package org.apereo.cas.ticket;

import org.apereo.cas.authentication.AuthenticationResult;
import org.apereo.cas.authentication.principal.Service;
import org.springframework.core.Ordered;

public interface ServiceTicketGeneratorAuthority
extends Ordered {
    default public boolean supports(AuthenticationResult authenticationResult, Service service) {
        return true;
    }

    default public boolean shouldGenerate(AuthenticationResult authenticationResult, Service service) {
        return true;
    }

    default public int getOrder() {
        return Integer.MAX_VALUE;
    }

    public static ServiceTicketGeneratorAuthority allow() {
        return new ServiceTicketGeneratorAuthority(){};
    }
}

