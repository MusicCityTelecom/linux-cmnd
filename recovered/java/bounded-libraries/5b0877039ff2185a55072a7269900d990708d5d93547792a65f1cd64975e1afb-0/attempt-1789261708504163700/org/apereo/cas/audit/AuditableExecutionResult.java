/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.AuthenticationResult
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.ticket.ServiceTicket
 *  org.apereo.cas.ticket.TicketGrantingTicket
 */
package org.apereo.cas.audit;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import lombok.Generated;
import org.apereo.cas.audit.AuditableContext;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.AuthenticationResult;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.ticket.ServiceTicket;
import org.apereo.cas.ticket.TicketGrantingTicket;

public class AuditableExecutionResult {
    private RegisteredService registeredService;
    private Service service;
    private ServiceTicket serviceTicket;
    private Authentication authentication;
    private RuntimeException exception;
    private Object executionResult;
    private TicketGrantingTicket ticketGrantingTicket;
    private AuthenticationResult authenticationResult;
    private Map<String, Object> properties;

    public static AuditableExecutionResult of(AuditableContext context) {
        AuditableExecutionResultBuilder<?, ?> builder = AuditableExecutionResult.builder();
        context.getTicketGrantingTicket().ifPresent(builder::ticketGrantingTicket);
        context.getAuthentication().ifPresent(builder::authentication);
        context.getAuthenticationResult().ifPresent(builder::authenticationResult);
        context.getRegisteredService().ifPresent(builder::registeredService);
        context.getService().ifPresent(builder::service);
        context.getServiceTicket().ifPresent(builder::serviceTicket);
        builder.properties(context.getProperties());
        return builder.build();
    }

    public boolean isExecutionFailure() {
        return this.getException().isPresent();
    }

    public void throwExceptionIfNeeded() {
        if (this.isExecutionFailure()) {
            throw this.getException().get();
        }
    }

    public void addProperty(String name, Object value) {
        this.properties.put(name, value);
    }

    public Optional<RegisteredService> getRegisteredService() {
        return Optional.ofNullable(this.registeredService);
    }

    public Optional<Service> getService() {
        return Optional.ofNullable(this.service);
    }

    public Optional<ServiceTicket> getServiceTicket() {
        return Optional.ofNullable(this.serviceTicket);
    }

    public Optional<Object> getExecutionResult() {
        return Optional.ofNullable(this.executionResult);
    }

    public Optional<Authentication> getAuthentication() {
        return Optional.ofNullable(this.authentication);
    }

    public Optional<TicketGrantingTicket> getTicketGrantingTicket() {
        return Optional.ofNullable(this.ticketGrantingTicket);
    }

    public Optional<AuthenticationResult> getAuthenticationResult() {
        return Optional.ofNullable(this.authenticationResult);
    }

    public Optional<RuntimeException> getException() {
        return Optional.ofNullable(this.exception);
    }

    public Map<String, Object> getProperties() {
        return this.properties;
    }

    @Generated
    private static Map<String, Object> $default$properties() {
        return new TreeMap<String, Object>();
    }

    @Generated
    protected AuditableExecutionResult(AuditableExecutionResultBuilder<?, ?> b) {
        this.registeredService = b.registeredService;
        this.service = b.service;
        this.serviceTicket = b.serviceTicket;
        this.authentication = b.authentication;
        this.exception = b.exception;
        this.executionResult = b.executionResult;
        this.ticketGrantingTicket = b.ticketGrantingTicket;
        this.authenticationResult = b.authenticationResult;
        this.properties = b.properties$set ? b.properties$value : AuditableExecutionResult.$default$properties();
    }

    @Generated
    public static AuditableExecutionResultBuilder<?, ?> builder() {
        return new AuditableExecutionResultBuilderImpl();
    }

    @Generated
    public AuditableExecutionResult(RegisteredService registeredService, Service service, ServiceTicket serviceTicket, Authentication authentication, RuntimeException exception, Object executionResult, TicketGrantingTicket ticketGrantingTicket, AuthenticationResult authenticationResult, Map<String, Object> properties) {
        this.registeredService = registeredService;
        this.service = service;
        this.serviceTicket = serviceTicket;
        this.authentication = authentication;
        this.exception = exception;
        this.executionResult = executionResult;
        this.ticketGrantingTicket = ticketGrantingTicket;
        this.authenticationResult = authenticationResult;
        this.properties = properties;
    }

    @Generated
    public AuditableExecutionResult() {
        this.properties = AuditableExecutionResult.$default$properties();
    }

    @Generated
    public void setException(RuntimeException exception) {
        this.exception = exception;
    }

    @Generated
    public void setExecutionResult(Object executionResult) {
        this.executionResult = executionResult;
    }

    @Generated
    private static final class AuditableExecutionResultBuilderImpl
    extends AuditableExecutionResultBuilder<AuditableExecutionResult, AuditableExecutionResultBuilderImpl> {
        @Generated
        private AuditableExecutionResultBuilderImpl() {
        }

        @Override
        @Generated
        protected AuditableExecutionResultBuilderImpl self() {
            return this;
        }

        @Override
        @Generated
        public AuditableExecutionResult build() {
            return new AuditableExecutionResult(this);
        }
    }

    @Generated
    public static abstract class AuditableExecutionResultBuilder<C extends AuditableExecutionResult, B extends AuditableExecutionResultBuilder<C, B>> {
        @Generated
        private RegisteredService registeredService;
        @Generated
        private Service service;
        @Generated
        private ServiceTicket serviceTicket;
        @Generated
        private Authentication authentication;
        @Generated
        private RuntimeException exception;
        @Generated
        private Object executionResult;
        @Generated
        private TicketGrantingTicket ticketGrantingTicket;
        @Generated
        private AuthenticationResult authenticationResult;
        @Generated
        private boolean properties$set;
        @Generated
        private Map<String, Object> properties$value;

        @Generated
        protected abstract B self();

        @Generated
        public abstract C build();

        @Generated
        public B registeredService(RegisteredService registeredService) {
            this.registeredService = registeredService;
            return this.self();
        }

        @Generated
        public B service(Service service) {
            this.service = service;
            return this.self();
        }

        @Generated
        public B serviceTicket(ServiceTicket serviceTicket) {
            this.serviceTicket = serviceTicket;
            return this.self();
        }

        @Generated
        public B authentication(Authentication authentication) {
            this.authentication = authentication;
            return this.self();
        }

        @Generated
        public B exception(RuntimeException exception) {
            this.exception = exception;
            return this.self();
        }

        @Generated
        public B executionResult(Object executionResult) {
            this.executionResult = executionResult;
            return this.self();
        }

        @Generated
        public B ticketGrantingTicket(TicketGrantingTicket ticketGrantingTicket) {
            this.ticketGrantingTicket = ticketGrantingTicket;
            return this.self();
        }

        @Generated
        public B authenticationResult(AuthenticationResult authenticationResult) {
            this.authenticationResult = authenticationResult;
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
            return "AuditableExecutionResult.AuditableExecutionResultBuilder(registeredService=" + this.registeredService + ", service=" + this.service + ", serviceTicket=" + this.serviceTicket + ", authentication=" + this.authentication + ", exception=" + this.exception + ", executionResult=" + this.executionResult + ", ticketGrantingTicket=" + this.ticketGrantingTicket + ", authenticationResult=" + this.authenticationResult + ", properties$value=" + this.properties$value + ")";
        }
    }
}

