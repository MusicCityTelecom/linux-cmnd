/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.configuration.CasConfigurationProperties
 */
package org.apereo.cas.ticket;

import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.ticket.TicketCatalog;

@FunctionalInterface
public interface TicketCatalogConfigurer {
    public void configureTicketCatalog(TicketCatalog var1, CasConfigurationProperties var2);

    default public String getName() {
        return this.getClass().getSimpleName();
    }
}

