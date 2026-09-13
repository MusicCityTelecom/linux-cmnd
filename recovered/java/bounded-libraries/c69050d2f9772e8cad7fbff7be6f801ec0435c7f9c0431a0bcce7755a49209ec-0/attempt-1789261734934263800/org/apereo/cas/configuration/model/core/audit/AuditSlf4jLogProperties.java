/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.core.audit;

import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-core-audit", automated=true)
public class AuditSlf4jLogProperties
implements Serializable {
    private static final long serialVersionUID = 4227475246873515918L;
    private boolean useSingleLine;
    private String singlelineSeparator = "|";
    private boolean enabled = true;

    @Generated
    public boolean isUseSingleLine() {
        return this.useSingleLine;
    }

    @Generated
    public String getSinglelineSeparator() {
        return this.singlelineSeparator;
    }

    @Generated
    public boolean isEnabled() {
        return this.enabled;
    }

    @Generated
    public AuditSlf4jLogProperties setUseSingleLine(boolean useSingleLine) {
        this.useSingleLine = useSingleLine;
        return this;
    }

    @Generated
    public AuditSlf4jLogProperties setSinglelineSeparator(String singlelineSeparator) {
        this.singlelineSeparator = singlelineSeparator;
        return this;
    }

    @Generated
    public AuditSlf4jLogProperties setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }
}

