/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.AuthenticationResult
 *  org.apereo.cas.authentication.principal.WebApplicationService
 *  org.apereo.inspektr.audit.annotation.Audit
 *  org.springframework.http.ResponseEntity
 */
package org.apereo.cas.rest.factory;

import java.util.Collection;
import lombok.Generated;
import org.apereo.cas.authentication.AuthenticationResult;
import org.apereo.cas.authentication.principal.WebApplicationService;
import org.apereo.cas.rest.factory.ServiceTicketResourceEntityResponseFactory;
import org.apereo.inspektr.audit.annotation.Audit;
import org.springframework.http.ResponseEntity;

public class CompositeServiceTicketResourceEntityResponseFactory
implements ServiceTicketResourceEntityResponseFactory {
    private final Collection<ServiceTicketResourceEntityResponseFactory> chain;

    @Override
    @Audit(action="REST_API_SERVICE_TICKET", actionResolverName="REST_API_SERVICE_TICKET_ACTION_RESOLVER", resourceResolverName="REST_API_SERVICE_TICKET_RESOURCE_RESOLVER")
    public ResponseEntity<String> build(String ticketGrantingTicket, WebApplicationService service, AuthenticationResult authenticationResult) {
        ServiceTicketResourceEntityResponseFactory factory = this.chain.stream().filter(f -> f.supports(service, authenticationResult)).findFirst().orElseThrow(() -> new IllegalArgumentException("Unable to locate a response entity factory to build a service ticket. This generally is due to a configuration issue where CAS is unable to recognize the incoming request"));
        return factory.build(ticketGrantingTicket, service, authenticationResult);
    }

    @Override
    public boolean supports(WebApplicationService service, AuthenticationResult authenticationResult) {
        return service != null && authenticationResult != null;
    }

    public int getOrder() {
        return Integer.MAX_VALUE;
    }

    @Generated
    public CompositeServiceTicketResourceEntityResponseFactory(Collection<ServiceTicketResourceEntityResponseFactory> chain) {
        this.chain = chain;
    }
}

