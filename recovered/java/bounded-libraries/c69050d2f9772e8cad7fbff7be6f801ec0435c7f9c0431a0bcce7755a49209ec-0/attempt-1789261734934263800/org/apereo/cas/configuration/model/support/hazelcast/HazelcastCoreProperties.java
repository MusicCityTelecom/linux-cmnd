/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.hazelcast;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-hazelcast-core")
@JsonFilter(value="HazelcastCoreProperties")
public class HazelcastCoreProperties
implements Serializable {
    private static final long serialVersionUID = 5935324429402972680L;
    private String licenseKey;
    private boolean enableCompression;
    private boolean enableManagementCenterScripting = true;
    private boolean enableJet = true;

    @Generated
    public String getLicenseKey() {
        return this.licenseKey;
    }

    @Generated
    public boolean isEnableCompression() {
        return this.enableCompression;
    }

    @Generated
    public boolean isEnableManagementCenterScripting() {
        return this.enableManagementCenterScripting;
    }

    @Generated
    public boolean isEnableJet() {
        return this.enableJet;
    }

    @Generated
    public HazelcastCoreProperties setLicenseKey(String licenseKey) {
        this.licenseKey = licenseKey;
        return this;
    }

    @Generated
    public HazelcastCoreProperties setEnableCompression(boolean enableCompression) {
        this.enableCompression = enableCompression;
        return this;
    }

    @Generated
    public HazelcastCoreProperties setEnableManagementCenterScripting(boolean enableManagementCenterScripting) {
        this.enableManagementCenterScripting = enableManagementCenterScripting;
        return this;
    }

    @Generated
    public HazelcastCoreProperties setEnableJet(boolean enableJet) {
        this.enableJet = enableJet;
        return this;
    }
}

