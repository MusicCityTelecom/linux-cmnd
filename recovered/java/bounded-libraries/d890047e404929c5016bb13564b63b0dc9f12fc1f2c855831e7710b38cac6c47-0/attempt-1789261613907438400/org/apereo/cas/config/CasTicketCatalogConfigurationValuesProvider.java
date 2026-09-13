/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.cas.ticket.ExpirationPolicyBuilder
 *  org.springframework.context.ConfigurableApplicationContext
 */
package org.apereo.cas.config;

import java.util.function.Function;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.ticket.ExpirationPolicyBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public interface CasTicketCatalogConfigurationValuesProvider {
    public static final String STORAGE_NAME_SERVICE_TICKETS = "serviceTicketsCache";
    public static final String STORAGE_NAME_PROXY_TICKET = "proxyTicketsCache";
    public static final String STORAGE_NAME_TICKET_GRANTING_TICKETS = "ticketGrantingTicketsCache";
    public static final String STORAGE_NAME_PROXY_GRANTING_TICKETS = "proxyGrantingTicketsCache";
    public static final String STORAGE_NAME_TRANSIENT_SESSION_TICKETS = "transientSessionTicketsCache";

    default public Function<ConfigurableApplicationContext, Long> getServiceTicketStorageTimeout() {
        return c -> ((ExpirationPolicyBuilder)c.getBean("serviceTicketExpirationPolicy", ExpirationPolicyBuilder.class)).buildTicketExpirationPolicy().getTimeToLive();
    }

    default public Function<CasConfigurationProperties, String> getServiceTicketStorageName() {
        return p -> STORAGE_NAME_SERVICE_TICKETS;
    }

    default public Function<ConfigurableApplicationContext, Long> getProxyTicketStorageTimeout() {
        return c -> ((ExpirationPolicyBuilder)c.getBean("proxyTicketExpirationPolicy", ExpirationPolicyBuilder.class)).buildTicketExpirationPolicy().getTimeToLive();
    }

    default public Function<CasConfigurationProperties, String> getProxyTicketStorageName() {
        return p -> STORAGE_NAME_PROXY_TICKET;
    }

    default public Function<ConfigurableApplicationContext, Long> getTicketGrantingTicketStorageTimeout() {
        return c -> ((ExpirationPolicyBuilder)c.getBean("grantingTicketExpirationPolicy", ExpirationPolicyBuilder.class)).buildTicketExpirationPolicy().getTimeToLive();
    }

    default public Function<CasConfigurationProperties, String> getTicketGrantingTicketStorageName() {
        return p -> STORAGE_NAME_TICKET_GRANTING_TICKETS;
    }

    default public Function<ConfigurableApplicationContext, Long> getProxyGrantingTicketStorageTimeout() {
        return c -> ((ExpirationPolicyBuilder)c.getBean("proxyGrantingTicketExpirationPolicy", ExpirationPolicyBuilder.class)).buildTicketExpirationPolicy().getTimeToLive();
    }

    default public Function<CasConfigurationProperties, String> getProxyGrantingTicketStorageName() {
        return p -> STORAGE_NAME_PROXY_GRANTING_TICKETS;
    }

    default public Function<ConfigurableApplicationContext, Long> getTransientSessionStorageTimeout() {
        return c -> ((ExpirationPolicyBuilder)c.getBean("transientSessionTicketExpirationPolicy", ExpirationPolicyBuilder.class)).buildTicketExpirationPolicy().getTimeToLive();
    }

    default public Function<CasConfigurationProperties, String> getTransientSessionStorageName() {
        return p -> STORAGE_NAME_TRANSIENT_SESSION_TICKETS;
    }
}

