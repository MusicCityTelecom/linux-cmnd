/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.saml.idp.metadata;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.ExpressionLanguageCapable;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-saml-idp")
@JsonFilter(value="HttpSamlMetadataProperties")
public class HttpSamlMetadataProperties
implements Serializable {
    private static final long serialVersionUID = -8226473583467202828L;
    private boolean forceMetadataRefresh = true;
    @ExpressionLanguageCapable
    private String metadataBackupLocation;

    @Generated
    public boolean isForceMetadataRefresh() {
        return this.forceMetadataRefresh;
    }

    @Generated
    public String getMetadataBackupLocation() {
        return this.metadataBackupLocation;
    }

    @Generated
    public HttpSamlMetadataProperties setForceMetadataRefresh(boolean forceMetadataRefresh) {
        this.forceMetadataRefresh = forceMetadataRefresh;
        return this;
    }

    @Generated
    public HttpSamlMetadataProperties setMetadataBackupLocation(String metadataBackupLocation) {
        this.metadataBackupLocation = metadataBackupLocation;
        return this;
    }
}

