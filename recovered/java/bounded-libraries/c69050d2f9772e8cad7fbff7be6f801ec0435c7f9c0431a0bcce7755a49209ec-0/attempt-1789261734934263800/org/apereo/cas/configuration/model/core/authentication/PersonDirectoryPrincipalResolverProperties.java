/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.util.model.TriStateBoolean
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.core.authentication;

import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.authentication.PrincipalTransformationProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.apereo.cas.util.model.TriStateBoolean;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-core-authentication", automated=true)
public class PersonDirectoryPrincipalResolverProperties
implements Serializable {
    private static final long serialVersionUID = 8929912041234879300L;
    private String principalAttribute;
    private TriStateBoolean returnNull = TriStateBoolean.UNDEFINED;
    private TriStateBoolean principalResolutionFailureFatal = TriStateBoolean.UNDEFINED;
    private TriStateBoolean useExistingPrincipalId = TriStateBoolean.UNDEFINED;
    private TriStateBoolean attributeResolutionEnabled = TriStateBoolean.UNDEFINED;
    private String activeAttributeRepositoryIds;
    private String principalResolutionConflictStrategy = "last";
    @NestedConfigurationProperty
    private PrincipalTransformationProperties principalTransformation = new PrincipalTransformationProperties();

    @Generated
    public String getPrincipalAttribute() {
        return this.principalAttribute;
    }

    @Generated
    public TriStateBoolean getReturnNull() {
        return this.returnNull;
    }

    @Generated
    public TriStateBoolean getPrincipalResolutionFailureFatal() {
        return this.principalResolutionFailureFatal;
    }

    @Generated
    public TriStateBoolean getUseExistingPrincipalId() {
        return this.useExistingPrincipalId;
    }

    @Generated
    public TriStateBoolean getAttributeResolutionEnabled() {
        return this.attributeResolutionEnabled;
    }

    @Generated
    public String getActiveAttributeRepositoryIds() {
        return this.activeAttributeRepositoryIds;
    }

    @Generated
    public String getPrincipalResolutionConflictStrategy() {
        return this.principalResolutionConflictStrategy;
    }

    @Generated
    public PrincipalTransformationProperties getPrincipalTransformation() {
        return this.principalTransformation;
    }

    @Generated
    public PersonDirectoryPrincipalResolverProperties setPrincipalAttribute(String principalAttribute) {
        this.principalAttribute = principalAttribute;
        return this;
    }

    @Generated
    public PersonDirectoryPrincipalResolverProperties setReturnNull(TriStateBoolean returnNull) {
        this.returnNull = returnNull;
        return this;
    }

    @Generated
    public PersonDirectoryPrincipalResolverProperties setPrincipalResolutionFailureFatal(TriStateBoolean principalResolutionFailureFatal) {
        this.principalResolutionFailureFatal = principalResolutionFailureFatal;
        return this;
    }

    @Generated
    public PersonDirectoryPrincipalResolverProperties setUseExistingPrincipalId(TriStateBoolean useExistingPrincipalId) {
        this.useExistingPrincipalId = useExistingPrincipalId;
        return this;
    }

    @Generated
    public PersonDirectoryPrincipalResolverProperties setAttributeResolutionEnabled(TriStateBoolean attributeResolutionEnabled) {
        this.attributeResolutionEnabled = attributeResolutionEnabled;
        return this;
    }

    @Generated
    public PersonDirectoryPrincipalResolverProperties setActiveAttributeRepositoryIds(String activeAttributeRepositoryIds) {
        this.activeAttributeRepositoryIds = activeAttributeRepositoryIds;
        return this;
    }

    @Generated
    public PersonDirectoryPrincipalResolverProperties setPrincipalResolutionConflictStrategy(String principalResolutionConflictStrategy) {
        this.principalResolutionConflictStrategy = principalResolutionConflictStrategy;
        return this;
    }

    @Generated
    public PersonDirectoryPrincipalResolverProperties setPrincipalTransformation(PrincipalTransformationProperties principalTransformation) {
        this.principalTransformation = principalTransformation;
        return this;
    }
}

