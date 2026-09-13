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
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-saml-idp")
@JsonFilter(value="FileSystemSamlMetadataProperties")
public class FileSystemSamlMetadataProperties
implements Serializable {
    private static final long serialVersionUID = -8336473583467202828L;
    @RequiredProperty
    @ExpressionLanguageCapable
    private String location = "file:/etc/cas/saml";
    private boolean signMetadata;

    @Generated
    public String getLocation() {
        return this.location;
    }

    @Generated
    public boolean isSignMetadata() {
        return this.signMetadata;
    }

    @Generated
    public FileSystemSamlMetadataProperties setLocation(String location) {
        this.location = location;
        return this;
    }

    @Generated
    public FileSystemSamlMetadataProperties setSignMetadata(boolean signMetadata) {
        this.signMetadata = signMetadata;
        return this;
    }
}

