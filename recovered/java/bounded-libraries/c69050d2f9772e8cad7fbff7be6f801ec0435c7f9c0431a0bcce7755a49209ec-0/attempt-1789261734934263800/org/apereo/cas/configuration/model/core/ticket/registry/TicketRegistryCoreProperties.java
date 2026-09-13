/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.core.ticket.registry;

import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-core-tickets", automated=true)
public class TicketRegistryCoreProperties
implements Serializable {
    private static final long serialVersionUID = -6927362599655259000L;
    private boolean enableLocking = true;

    @Generated
    public boolean isEnableLocking() {
        return this.enableLocking;
    }

    @Generated
    public TicketRegistryCoreProperties setEnableLocking(boolean enableLocking) {
        this.enableLocking = enableLocking;
        return this;
    }
}

