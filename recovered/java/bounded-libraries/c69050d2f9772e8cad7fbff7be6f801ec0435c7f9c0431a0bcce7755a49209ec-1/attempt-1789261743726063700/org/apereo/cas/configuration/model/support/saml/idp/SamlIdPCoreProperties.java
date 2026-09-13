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
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.replication.SessionReplicationProperties;
import org.apereo.cas.configuration.support.ExpressionLanguageCapable;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-saml-idp")
@JsonFilter(value="SamlIdPCoreProperties")
public class SamlIdPCoreProperties
implements Serializable {
    private static final long serialVersionUID = -1848175783676789852L;
    private boolean attributeQueryProfileEnabled;
    private SessionStorageTypes sessionStorageType = SessionStorageTypes.HTTP;
    @RequiredProperty
    @ExpressionLanguageCapable
    private String entityId = "https://cas.example.org/idp";
    private List<String> authenticationContextClassMappings = new ArrayList<String>(0);
    private List<String> attributeFriendlyNames = new ArrayList<String>(0);
    @NestedConfigurationProperty
    private SessionReplicationProperties sessionReplication = new SessionReplicationProperties();

    @Generated
    public boolean isAttributeQueryProfileEnabled() {
        return this.attributeQueryProfileEnabled;
    }

    @Generated
    public SessionStorageTypes getSessionStorageType() {
        return this.sessionStorageType;
    }

    @Generated
    public String getEntityId() {
        return this.entityId;
    }

    @Generated
    public List<String> getAuthenticationContextClassMappings() {
        return this.authenticationContextClassMappings;
    }

    @Generated
    public List<String> getAttributeFriendlyNames() {
        return this.attributeFriendlyNames;
    }

    @Generated
    public SessionReplicationProperties getSessionReplication() {
        return this.sessionReplication;
    }

    @Generated
    public SamlIdPCoreProperties setAttributeQueryProfileEnabled(boolean attributeQueryProfileEnabled) {
        this.attributeQueryProfileEnabled = attributeQueryProfileEnabled;
        return this;
    }

    @Generated
    public SamlIdPCoreProperties setSessionStorageType(SessionStorageTypes sessionStorageType) {
        this.sessionStorageType = sessionStorageType;
        return this;
    }

    @Generated
    public SamlIdPCoreProperties setEntityId(String entityId) {
        this.entityId = entityId;
        return this;
    }

    @Generated
    public SamlIdPCoreProperties setAuthenticationContextClassMappings(List<String> authenticationContextClassMappings) {
        this.authenticationContextClassMappings = authenticationContextClassMappings;
        return this;
    }

    @Generated
    public SamlIdPCoreProperties setAttributeFriendlyNames(List<String> attributeFriendlyNames) {
        this.attributeFriendlyNames = attributeFriendlyNames;
        return this;
    }

    @Generated
    public SamlIdPCoreProperties setSessionReplication(SessionReplicationProperties sessionReplication) {
        this.sessionReplication = sessionReplication;
        return this;
    }

    public static enum SessionStorageTypes {
        HTTP,
        BROWSER_SESSION_STORAGE,
        TICKET_REGISTRY;

    }
}

