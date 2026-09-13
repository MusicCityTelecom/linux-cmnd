/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.util.model.TriStateBoolean
 */
package org.apereo.cas.configuration.model.support.saml.sps;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;
import org.apereo.cas.util.model.TriStateBoolean;

@RequiresModule(name="cas-server-support-saml-idp")
public abstract class AbstractSamlSPProperties
implements Serializable {
    private static final long serialVersionUID = -5381463661659831898L;
    @RequiredProperty
    private String metadata;
    private String name = this.getClass().getSimpleName();
    private String description = this.getClass().getSimpleName().concat(" SAML SP Integration");
    private String nameIdAttribute;
    private String nameIdFormat;
    private List<String> attributes = new ArrayList<String>(0);
    private String signatureLocation;
    private List<String> entityIds = new ArrayList<String>(0);
    private boolean signResponses = true;
    private TriStateBoolean signAssertions = TriStateBoolean.FALSE;

    protected void addAttributes(String ... attributes) {
        this.setAttributes(Stream.of(attributes).collect(Collectors.toList()));
    }

    @Generated
    public String getMetadata() {
        return this.metadata;
    }

    @Generated
    public String getName() {
        return this.name;
    }

    @Generated
    public String getDescription() {
        return this.description;
    }

    @Generated
    public String getNameIdAttribute() {
        return this.nameIdAttribute;
    }

    @Generated
    public String getNameIdFormat() {
        return this.nameIdFormat;
    }

    @Generated
    public List<String> getAttributes() {
        return this.attributes;
    }

    @Generated
    public String getSignatureLocation() {
        return this.signatureLocation;
    }

    @Generated
    public List<String> getEntityIds() {
        return this.entityIds;
    }

    @Generated
    public boolean isSignResponses() {
        return this.signResponses;
    }

    @Generated
    public TriStateBoolean getSignAssertions() {
        return this.signAssertions;
    }

    @Generated
    public AbstractSamlSPProperties setMetadata(String metadata) {
        this.metadata = metadata;
        return this;
    }

    @Generated
    public AbstractSamlSPProperties setName(String name) {
        this.name = name;
        return this;
    }

    @Generated
    public AbstractSamlSPProperties setDescription(String description) {
        this.description = description;
        return this;
    }

    @Generated
    public AbstractSamlSPProperties setNameIdAttribute(String nameIdAttribute) {
        this.nameIdAttribute = nameIdAttribute;
        return this;
    }

    @Generated
    public AbstractSamlSPProperties setNameIdFormat(String nameIdFormat) {
        this.nameIdFormat = nameIdFormat;
        return this;
    }

    @Generated
    public AbstractSamlSPProperties setAttributes(List<String> attributes) {
        this.attributes = attributes;
        return this;
    }

    @Generated
    public AbstractSamlSPProperties setSignatureLocation(String signatureLocation) {
        this.signatureLocation = signatureLocation;
        return this;
    }

    @Generated
    public AbstractSamlSPProperties setEntityIds(List<String> entityIds) {
        this.entityIds = entityIds;
        return this;
    }

    @Generated
    public AbstractSamlSPProperties setSignResponses(boolean signResponses) {
        this.signResponses = signResponses;
        return this;
    }

    @Generated
    public AbstractSamlSPProperties setSignAssertions(TriStateBoolean signAssertions) {
        this.signAssertions = signAssertions;
        return this;
    }
}

