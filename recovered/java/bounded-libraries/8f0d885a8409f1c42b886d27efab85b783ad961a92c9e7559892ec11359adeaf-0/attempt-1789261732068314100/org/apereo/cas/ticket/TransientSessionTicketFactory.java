/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.authentication.principal.Service
 */
package org.apereo.cas.ticket;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.ticket.ExpirationPolicy;
import org.apereo.cas.ticket.ExpirationPolicyBuilder;
import org.apereo.cas.ticket.TicketFactory;
import org.apereo.cas.ticket.TransientSessionTicket;

public interface TransientSessionTicketFactory<T extends TransientSessionTicket>
extends TicketFactory {
    public static String normalizeTicketId(String id) {
        return "TST-" + id;
    }

    public static ExpirationPolicy buildExpirationPolicy(ExpirationPolicyBuilder expirationPolicyBuilder, Map<String, Serializable> properties) {
        ExpirationPolicy expirationPolicy = expirationPolicyBuilder.buildTicketExpirationPolicy();
        if (properties.containsKey(ExpirationPolicy.class.getName())) {
            expirationPolicy = (ExpirationPolicy)ExpirationPolicy.class.cast(properties.remove(ExpirationPolicy.class.getName()));
        }
        return expirationPolicy;
    }

    public T create(Service var1, Map<String, Serializable> var2);

    default public T create(String id, Map<String, Serializable> properties) {
        return this.create(id, null, properties);
    }

    public T create(String var1, Service var2, Map<String, Serializable> var3);

    default public T create(Service service) {
        return this.create(service, new LinkedHashMap<String, Serializable>(0));
    }
}

