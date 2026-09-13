/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.support.events.config;

import java.nio.file.Path;
import lombok.Generated;
import org.apereo.cas.support.events.AbstractCasEvent;

public class CasConfigurationCreatedEvent
extends AbstractCasEvent {
    private static final long serialVersionUID = -9038763901650896455L;
    private final transient Path file;

    public CasConfigurationCreatedEvent(Object source, Path file) {
        super(source);
        this.file = file;
    }

    @Override
    @Generated
    public String toString() {
        return "CasConfigurationCreatedEvent(super=" + super.toString() + ", file=" + this.file + ")";
    }

    @Generated
    public Path getFile() {
        return this.file;
    }
}

