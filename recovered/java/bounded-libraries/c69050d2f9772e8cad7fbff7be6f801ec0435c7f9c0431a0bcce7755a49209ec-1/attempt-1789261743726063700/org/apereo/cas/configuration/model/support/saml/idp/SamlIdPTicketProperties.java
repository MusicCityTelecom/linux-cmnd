/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.saml.idp;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.saml.idp.AttributeQueryTicketProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-saml-idp")
@JsonFilter(value="SamlIdPTicketProperties")
public class SamlIdPTicketProperties
implements Serializable {
    private static final long serialVersionUID = 6837089259390742073L;
    private String samlArtifactsCacheStorageName = "samlArtifactsCache";
    private String samlAttributeQueryCacheStorageName = "samlAttributeQueryCache";
    @NestedConfigurationProperty
    private AttributeQueryTicketProperties attributeQuery = new AttributeQueryTicketProperties();

    @Generated
    public String getSamlArtifactsCacheStorageName() {
        return this.samlArtifactsCacheStorageName;
    }

    @Generated
    public String getSamlAttributeQueryCacheStorageName() {
        return this.samlAttributeQueryCacheStorageName;
    }

    @Generated
    public AttributeQueryTicketProperties getAttributeQuery() {
        return this.attributeQuery;
    }

    @Generated
    public SamlIdPTicketProperties setSamlArtifactsCacheStorageName(String samlArtifactsCacheStorageName) {
        this.samlArtifactsCacheStorageName = samlArtifactsCacheStorageName;
        return this;
    }

    @Generated
    public SamlIdPTicketProperties setSamlAttributeQueryCacheStorageName(String samlAttributeQueryCacheStorageName) {
        this.samlAttributeQueryCacheStorageName = samlAttributeQueryCacheStorageName;
        return this;
    }

    @Generated
    public SamlIdPTicketProperties setAttributeQuery(AttributeQueryTicketProperties attributeQuery) {
        this.attributeQuery = attributeQuery;
        return this;
    }
}

