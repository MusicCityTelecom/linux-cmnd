/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.saml.idp;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-saml-idp")
@JsonFilter(value="SamlIdPAlgorithmsProperties")
public class SamlIdPAlgorithmsProperties
implements Serializable {
    private static final long serialVersionUID = 6547093517788229284L;
    private List<String> overrideDataEncryptionAlgorithms = new ArrayList<String>(0);
    private List<String> overrideKeyEncryptionAlgorithms = new ArrayList<String>(0);
    private List<String> overrideBlockedEncryptionAlgorithms = new ArrayList<String>(0);
    private List<String> overrideAllowedAlgorithms = new ArrayList<String>(0);
    private List<String> overrideSignatureReferenceDigestMethods = new ArrayList<String>(0);
    private List<String> overrideSignatureAlgorithms = new ArrayList<String>(0);
    private List<String> overrideBlockedSignatureSigningAlgorithms = new ArrayList<String>(0);
    private List<String> overrideAllowedSignatureSigningAlgorithms = new ArrayList<String>(0);
    private String overrideSignatureCanonicalizationAlgorithm;
    private String privateKeyAlgName = "RSA";

    @Generated
    public List<String> getOverrideDataEncryptionAlgorithms() {
        return this.overrideDataEncryptionAlgorithms;
    }

    @Generated
    public List<String> getOverrideKeyEncryptionAlgorithms() {
        return this.overrideKeyEncryptionAlgorithms;
    }

    @Generated
    public List<String> getOverrideBlockedEncryptionAlgorithms() {
        return this.overrideBlockedEncryptionAlgorithms;
    }

    @Generated
    public List<String> getOverrideAllowedAlgorithms() {
        return this.overrideAllowedAlgorithms;
    }

    @Generated
    public List<String> getOverrideSignatureReferenceDigestMethods() {
        return this.overrideSignatureReferenceDigestMethods;
    }

    @Generated
    public List<String> getOverrideSignatureAlgorithms() {
        return this.overrideSignatureAlgorithms;
    }

    @Generated
    public List<String> getOverrideBlockedSignatureSigningAlgorithms() {
        return this.overrideBlockedSignatureSigningAlgorithms;
    }

    @Generated
    public List<String> getOverrideAllowedSignatureSigningAlgorithms() {
        return this.overrideAllowedSignatureSigningAlgorithms;
    }

    @Generated
    public String getOverrideSignatureCanonicalizationAlgorithm() {
        return this.overrideSignatureCanonicalizationAlgorithm;
    }

    @Generated
    public String getPrivateKeyAlgName() {
        return this.privateKeyAlgName;
    }

    @Generated
    public SamlIdPAlgorithmsProperties setOverrideDataEncryptionAlgorithms(List<String> overrideDataEncryptionAlgorithms) {
        this.overrideDataEncryptionAlgorithms = overrideDataEncryptionAlgorithms;
        return this;
    }

    @Generated
    public SamlIdPAlgorithmsProperties setOverrideKeyEncryptionAlgorithms(List<String> overrideKeyEncryptionAlgorithms) {
        this.overrideKeyEncryptionAlgorithms = overrideKeyEncryptionAlgorithms;
        return this;
    }

    @Generated
    public SamlIdPAlgorithmsProperties setOverrideBlockedEncryptionAlgorithms(List<String> overrideBlockedEncryptionAlgorithms) {
        this.overrideBlockedEncryptionAlgorithms = overrideBlockedEncryptionAlgorithms;
        return this;
    }

    @Generated
    public SamlIdPAlgorithmsProperties setOverrideAllowedAlgorithms(List<String> overrideAllowedAlgorithms) {
        this.overrideAllowedAlgorithms = overrideAllowedAlgorithms;
        return this;
    }

    @Generated
    public SamlIdPAlgorithmsProperties setOverrideSignatureReferenceDigestMethods(List<String> overrideSignatureReferenceDigestMethods) {
        this.overrideSignatureReferenceDigestMethods = overrideSignatureReferenceDigestMethods;
        return this;
    }

    @Generated
    public SamlIdPAlgorithmsProperties setOverrideSignatureAlgorithms(List<String> overrideSignatureAlgorithms) {
        this.overrideSignatureAlgorithms = overrideSignatureAlgorithms;
        return this;
    }

    @Generated
    public SamlIdPAlgorithmsProperties setOverrideBlockedSignatureSigningAlgorithms(List<String> overrideBlockedSignatureSigningAlgorithms) {
        this.overrideBlockedSignatureSigningAlgorithms = overrideBlockedSignatureSigningAlgorithms;
        return this;
    }

    @Generated
    public SamlIdPAlgorithmsProperties setOverrideAllowedSignatureSigningAlgorithms(List<String> overrideAllowedSignatureSigningAlgorithms) {
        this.overrideAllowedSignatureSigningAlgorithms = overrideAllowedSignatureSigningAlgorithms;
        return this;
    }

    @Generated
    public SamlIdPAlgorithmsProperties setOverrideSignatureCanonicalizationAlgorithm(String overrideSignatureCanonicalizationAlgorithm) {
        this.overrideSignatureCanonicalizationAlgorithm = overrideSignatureCanonicalizationAlgorithm;
        return this;
    }

    @Generated
    public SamlIdPAlgorithmsProperties setPrivateKeyAlgName(String privateKeyAlgName) {
        this.privateKeyAlgName = privateKeyAlgName;
        return this;
    }
}

