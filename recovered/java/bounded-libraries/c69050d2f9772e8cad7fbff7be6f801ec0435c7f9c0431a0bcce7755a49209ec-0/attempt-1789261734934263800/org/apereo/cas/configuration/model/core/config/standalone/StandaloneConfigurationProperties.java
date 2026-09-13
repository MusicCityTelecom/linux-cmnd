/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.core.config.standalone;

import java.io.File;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.config.standalone.StandaloneConfigurationSecurityProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-core-configuration", automated=true)
public class StandaloneConfigurationProperties
implements Serializable {
    private static final long serialVersionUID = -7749293768878152908L;
    private File configurationDirectory;
    private File configurationFile;
    @NestedConfigurationProperty
    private StandaloneConfigurationSecurityProperties configurationSecurity = new StandaloneConfigurationSecurityProperties();

    @Generated
    public File getConfigurationDirectory() {
        return this.configurationDirectory;
    }

    @Generated
    public File getConfigurationFile() {
        return this.configurationFile;
    }

    @Generated
    public StandaloneConfigurationSecurityProperties getConfigurationSecurity() {
        return this.configurationSecurity;
    }

    @Generated
    public StandaloneConfigurationProperties setConfigurationDirectory(File configurationDirectory) {
        this.configurationDirectory = configurationDirectory;
        return this;
    }

    @Generated
    public StandaloneConfigurationProperties setConfigurationFile(File configurationFile) {
        this.configurationFile = configurationFile;
        return this;
    }

    @Generated
    public StandaloneConfigurationProperties setConfigurationSecurity(StandaloneConfigurationSecurityProperties configurationSecurity) {
        this.configurationSecurity = configurationSecurity;
        return this;
    }
}

