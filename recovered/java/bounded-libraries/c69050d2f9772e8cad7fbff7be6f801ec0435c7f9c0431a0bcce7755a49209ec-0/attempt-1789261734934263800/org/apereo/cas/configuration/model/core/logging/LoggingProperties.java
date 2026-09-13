/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.core.logging;

import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-core-logging", automated=true)
public class LoggingProperties
implements Serializable {
    private static final long serialVersionUID = 7455171260665661949L;
    private boolean mdcEnabled = true;

    @Generated
    public boolean isMdcEnabled() {
        return this.mdcEnabled;
    }

    @Generated
    public LoggingProperties setMdcEnabled(boolean mdcEnabled) {
        this.mdcEnabled = mdcEnabled;
        return this;
    }
}

