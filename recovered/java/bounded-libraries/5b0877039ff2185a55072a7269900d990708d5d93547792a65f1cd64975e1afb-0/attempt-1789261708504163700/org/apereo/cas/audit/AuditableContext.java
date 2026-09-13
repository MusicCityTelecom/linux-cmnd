/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.AuthenticationResult
 *  org.apereo.cas.authentication.principal.Principal
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.ticket.ServiceTicket
 *  org.apereo.cas.ticket.TicketGrantingTicket
 */
package org.apereo.cas.audit;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import lombok.Generated;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.AuthenticationResult;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.ticket.ServiceTicket;
import org.apereo.cas.ticket.TicketGrantingTicket;

public class AuditableContext {
    private final Service service;
    private final RegisteredService registeredService;
    private final Principal principal;
    private final Authentication authentication;
    private final ServiceTicket serviceTicket;
    private final AuthenticationResult authenticationResult;
    private final TicketGrantingTicket ticketGrantingTicket;
    private final Object httpRequest;
    private final Object httpResponse;
    private Map<String, Object> properties;

    public Optional<Service> getService() {
        return Optional.ofNullable(this.service);
    }

    public Optional<RegisteredService> getRegisteredService() {
        return Optional.ofNullable(this.registeredService);
    }

    public Optional<Authentication> getAuthentication() {
        return Optional.ofNullable(this.authentication);
    }

    public Optional<Principal> getPrincipal() {
        return Optional.ofNullable(this.principal);
    }

    public Optional<ServiceTicket> getServiceTicket() {
        return Optional.ofNullable(this.serviceTicket);
    }

    public Optional<Object> getRequest() {
        return Optional.ofNullable(this.httpRequest);
    }

    public Optional<Object> getResponse() {
        return Optional.ofNullable(this.httpResponse);
    }

    public Optional<AuthenticationResult> getAuthenticationResult() {
        return Optional.ofNullable(this.authenticationResult);
    }

    public Optional<TicketGrantingTicket> getTicketGrantingTicket() {
        return Optional.ofNullable(this.ticketGrantingTicket);
    }

    public Map<String, Object> getProperties() {
        return this.properties;
    }

    @Generated
    private static Map<String, Object> $default$properties() {
        return new LinkedHashMap<String, Object>(0);
    }

    @Generated
    protected AuditableContext(AuditableContextBuilder<?, ?> b) {
        this.service = b.service;
        this.registeredService = b.registeredService;
        this.principal = b.principal;
        this.authentication = b.authentication;
        this.serviceTicket = b.serviceTicket;
        this.authenticationResult = b.authenticationResult;
        this.ticketGrantingTicket = b.ticketGrantingTicket;
        this.httpRequest = b.httpRequest;
        this.httpResponse = b.httpResponse;
        this.properties = b.properties$set ? b.properties$value : AuditableContext.$default$properties();
    }

    @Generated
    public static AuditableContextBuilder<?, ?> builder() {
        return new AuditableContextBuilderImpl();
    }

    @Generated
    private static final class AuditableContextBuilderImpl
    extends AuditableContextBuilder<AuditableContext, AuditableContextBuilderImpl> {
        @Generated
        private AuditableContextBuilderImpl() {
        }

        @Override
        @Generated
        protected AuditableContextBuilderImpl self() {
            return this;
        }

        @Override
        @Generated
        public AuditableContext build() {
            return new AuditableContext(this);
        }
    }

    @Generated
    public static abstract class AuditableContextBuilder<C extends AuditableContext, B extends AuditableContextBuilder<C, B>> {
        @Generated
        private Service service;
        @Generated
        private RegisteredService registeredService;
        @Generated
        private Principal principal;
        @Generated
        private Authentication authentication;
        @Generated
        private ServiceTicket serviceTicket;
        @Generated
        private AuthenticationResult authenticationResult;
        @Generated
        private TicketGrantingTicket ticketGrantingTicket;
        @Generated
        private Object httpRequest;
        @Generated
        private Object httpResponse;
        @Generated
        private boolean properties$set;
        @Generated
        private Map<String, Object> properties$value;

        @Generated
        protected abstract B self();

        @Generated
        public abstract C build();

        @Generated
        public B service(Service service) {
            this.service = service;
            return this.self();
        }

        @Generated
        public B registeredService(RegisteredService registeredService) {
            this.registeredService = registeredService;
            return this.self();
        }

        @Generated
        public B principal(Principal principal) {
            this.principal = principal;
            return this.self();
        }

        @Generated
        public B authentication(Authentication authentication) {
            this.authentication = authentication;
            return this.self();
        }

        @Generated
        public B serviceTicket(ServiceTicket serviceTicket) {
            this.serviceTicket = serviceTicket;
            return this.self();
        }

        @Generated
        public B authenticationResult(AuthenticationResult authenticationResult) {
            this.authenticationResult = authenticationResult;
            return this.self();
        }

        @Generated
        public B ticketGrantingTicket(TicketGrantingTicket ticketGrantingTicket) {
            this.ticketGrantingTicket = ticketGrantingTicket;
            return this.self();
        }

        @Generated
        public B httpRequest(Object httpRequest) {
            this.httpRequest = httpRequest;
            return this.self();
        }

        @Generated
        public B httpResponse(Object httpResponse) {
            this.httpResponse = httpResponse;
            return this.self();
        }

        @Generated
        public B properties(Map<String, Object> properties) {
            this.properties$value = properties;
            this.properties$set = true;
            return this.self();
        }

        @Generated
        public String toString() {
            return "AuditableContext.AuditableContextBuilder(service=" + this.service + ", registeredService=" + this.registeredService + ", principal=" + this.principal + ", authentication=" + this.authentication + ", serviceTicket=" + this.serviceTicket + ", authenticationResult=" + this.authenticationResult + ", ticketGrantingTicket=" + this.ticketGrantingTicket + ", httpRequest=" + this.httpRequest + ", httpResponse=" + this.httpResponse + ", properties$value=" + this.properties$value + ")";
        }
    }
}

