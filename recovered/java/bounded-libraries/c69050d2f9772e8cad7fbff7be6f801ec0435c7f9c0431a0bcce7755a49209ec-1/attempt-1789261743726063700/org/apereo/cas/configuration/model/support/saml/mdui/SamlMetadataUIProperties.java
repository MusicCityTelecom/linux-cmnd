/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.saml.mdui;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.quartz.SchedulingProperties;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-saml-mdui")
@JsonFilter(value="SamlMetadataUIProperties")
public class SamlMetadataUIProperties
implements Serializable {
    private static final long serialVersionUID = 2113479681245996975L;
    private String parameter = "entityId";
    private long maxValidity;
    private boolean requireSignedRoot;
    private boolean requireValidMetadata = true;
    @RequiredProperty
    private List<String> resources = new ArrayList<String>(0);
    @NestedConfigurationProperty
    private SchedulingProperties schedule = new SchedulingProperties();

    public SamlMetadataUIProperties() {
        this.schedule.setEnabled(true);
        this.schedule.setStartDelay("PT30S");
        this.schedule.setRepeatInterval("PT2M");
    }

    @Generated
    public String getParameter() {
        return this.parameter;
    }

    @Generated
    public long getMaxValidity() {
        return this.maxValidity;
    }

    @Generated
    public boolean isRequireSignedRoot() {
        return this.requireSignedRoot;
    }

    @Generated
    public boolean isRequireValidMetadata() {
        return this.requireValidMetadata;
    }

    @Generated
    public List<String> getResources() {
        return this.resources;
    }

    @Generated
    public SchedulingProperties getSchedule() {
        return this.schedule;
    }

    @Generated
    public SamlMetadataUIProperties setParameter(String parameter) {
        this.parameter = parameter;
        return this;
    }

    @Generated
    public SamlMetadataUIProperties setMaxValidity(long maxValidity) {
        this.maxValidity = maxValidity;
        return this;
    }

    @Generated
    public SamlMetadataUIProperties setRequireSignedRoot(boolean requireSignedRoot) {
        this.requireSignedRoot = requireSignedRoot;
        return this;
    }

    @Generated
    public SamlMetadataUIProperties setRequireValidMetadata(boolean requireValidMetadata) {
        this.requireValidMetadata = requireValidMetadata;
        return this;
    }

    @Generated
    public SamlMetadataUIProperties setResources(List<String> resources) {
        this.resources = resources;
        return this;
    }

    @Generated
    public SamlMetadataUIProperties setSchedule(SchedulingProperties schedule) {
        this.schedule = schedule;
        return this;
    }
}

