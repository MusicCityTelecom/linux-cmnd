/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.core.ticket;

import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-core-tickets", automated=true)
public class ProxyGrantingTicketProperties
implements Serializable {
    private static final long serialVersionUID = 8478961497316814687L;
    private long maxLength = 50L;

    @Generated
    public long getMaxLength() {
        return this.maxLength;
    }

    @Generated
    public ProxyGrantingTicketProperties setMaxLength(long maxLength) {
        this.maxLength = maxLength;
        return this;
    }
}

