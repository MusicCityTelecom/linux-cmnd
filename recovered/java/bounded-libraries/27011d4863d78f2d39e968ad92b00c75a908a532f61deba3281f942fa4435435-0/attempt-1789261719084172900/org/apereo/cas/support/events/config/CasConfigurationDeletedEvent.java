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

public class CasConfigurationDeletedEvent
extends AbstractCasEvent {
    private static final long serialVersionUID = -5738769364210896455L;
    private final transient Path file;

    public CasConfigurationDeletedEvent(Object source, Path file) {
        super(source);
        this.file = file;
    }

    @Override
    @Generated
    public String toString() {
        return "CasConfigurationDeletedEvent(super=" + super.toString() + ", file=" + this.file + ")";
    }

    @Generated
    public Path getFile() {
        return this.file;
    }
}

