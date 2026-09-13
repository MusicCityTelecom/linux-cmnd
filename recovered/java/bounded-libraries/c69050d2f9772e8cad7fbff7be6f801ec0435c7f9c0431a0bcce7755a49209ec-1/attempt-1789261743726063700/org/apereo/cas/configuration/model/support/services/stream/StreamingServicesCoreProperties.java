/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.services.stream;

import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-service-registry-stream")
public class StreamingServicesCoreProperties
implements Serializable {
    private static final long serialVersionUID = 2957227900906059461L;
    private ReplicationModes replicationMode = ReplicationModes.PASSIVE;
    private boolean enabled = true;

    @Generated
    public ReplicationModes getReplicationMode() {
        return this.replicationMode;
    }

    @Generated
    public boolean isEnabled() {
        return this.enabled;
    }

    @Generated
    public StreamingServicesCoreProperties setReplicationMode(ReplicationModes replicationMode) {
        this.replicationMode = replicationMode;
        return this;
    }

    @Generated
    public StreamingServicesCoreProperties setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public static enum ReplicationModes {
        ACTIVE,
        PASSIVE;

    }
}

