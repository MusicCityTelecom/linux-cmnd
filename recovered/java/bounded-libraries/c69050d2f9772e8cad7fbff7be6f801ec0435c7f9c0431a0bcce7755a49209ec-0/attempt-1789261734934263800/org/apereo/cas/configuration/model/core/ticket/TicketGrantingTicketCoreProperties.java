/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.core.ticket;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-core-tickets", automated=true)
@JsonFilter(value="TicketGrantingTicketCoreProperties")
public class TicketGrantingTicketCoreProperties
implements Serializable {
    private static final long serialVersionUID = 2349179252583399336L;
    private int maxLength = 50;
    private boolean onlyTrackMostRecentSession = true;

    @Generated
    public int getMaxLength() {
        return this.maxLength;
    }

    @Generated
    public boolean isOnlyTrackMostRecentSession() {
        return this.onlyTrackMostRecentSession;
    }

    @Generated
    public TicketGrantingTicketCoreProperties setMaxLength(int maxLength) {
        this.maxLength = maxLength;
        return this;
    }

    @Generated
    public TicketGrantingTicketCoreProperties setOnlyTrackMostRecentSession(boolean onlyTrackMostRecentSession) {
        this.onlyTrackMostRecentSession = onlyTrackMostRecentSession;
        return this;
    }
}

