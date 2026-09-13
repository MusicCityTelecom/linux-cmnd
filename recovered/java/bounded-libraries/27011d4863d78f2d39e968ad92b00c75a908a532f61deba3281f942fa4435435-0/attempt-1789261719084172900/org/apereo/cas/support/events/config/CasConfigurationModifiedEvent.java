/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.support.events.config;

import java.nio.file.Path;
import java.util.regex.Pattern;
import lombok.Generated;
import org.apereo.cas.support.events.AbstractCasEvent;

public class CasConfigurationModifiedEvent
extends AbstractCasEvent {
    private static final long serialVersionUID = -5738763037210896455L;
    private static final Pattern CONFIG_FILE_PATTERN = Pattern.compile("\\.(properties|yml)", 2);
    private final transient Path file;
    private final boolean override;

    public CasConfigurationModifiedEvent(Object source, Path file) {
        this(source, file, false);
    }

    public CasConfigurationModifiedEvent(Object source, boolean override) {
        this(source, null, override);
    }

    public CasConfigurationModifiedEvent(Object source, Path file, boolean override) {
        super(source);
        this.file = file;
        this.override = override;
    }

    public boolean isEligibleForContextRefresh() {
        if (this.override) {
            return true;
        }
        String fileName = this.getFile().toFile().getName();
        return this.getFile() != null && CONFIG_FILE_PATTERN.matcher(fileName).find();
    }

    @Override
    @Generated
    public String toString() {
        return "CasConfigurationModifiedEvent(super=" + super.toString() + ", file=" + this.file + ", override=" + this.override + ")";
    }

    @Generated
    public Path getFile() {
        return this.file;
    }

    @Generated
    public boolean isOverride() {
        return this.override;
    }
}

