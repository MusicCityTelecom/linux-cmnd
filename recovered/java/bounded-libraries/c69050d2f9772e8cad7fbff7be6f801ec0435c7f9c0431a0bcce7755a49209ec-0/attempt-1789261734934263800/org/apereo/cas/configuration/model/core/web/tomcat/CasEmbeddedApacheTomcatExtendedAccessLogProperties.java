/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.core.web.tomcat;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-webapp-tomcat")
@JsonFilter(value="CasEmbeddedApacheTomcatExtendedAccessLogProperties")
public class CasEmbeddedApacheTomcatExtendedAccessLogProperties
implements Serializable {
    private static final long serialVersionUID = 6738161402499196038L;
    @RequiredProperty
    private boolean enabled;
    private String pattern = "c-ip s-ip cs-uri sc-status time x-threadname x-H(secure) x-H(remoteUser)";
    private String suffix = ".log";
    private String prefix = "localhost_access_extended";
    private String directory;

    @Generated
    public boolean isEnabled() {
        return this.enabled;
    }

    @Generated
    public String getPattern() {
        return this.pattern;
    }

    @Generated
    public String getSuffix() {
        return this.suffix;
    }

    @Generated
    public String getPrefix() {
        return this.prefix;
    }

    @Generated
    public String getDirectory() {
        return this.directory;
    }

    @Generated
    public CasEmbeddedApacheTomcatExtendedAccessLogProperties setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatExtendedAccessLogProperties setPattern(String pattern) {
        this.pattern = pattern;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatExtendedAccessLogProperties setSuffix(String suffix) {
        this.suffix = suffix;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatExtendedAccessLogProperties setPrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatExtendedAccessLogProperties setDirectory(String directory) {
        this.directory = directory;
        return this;
    }
}

