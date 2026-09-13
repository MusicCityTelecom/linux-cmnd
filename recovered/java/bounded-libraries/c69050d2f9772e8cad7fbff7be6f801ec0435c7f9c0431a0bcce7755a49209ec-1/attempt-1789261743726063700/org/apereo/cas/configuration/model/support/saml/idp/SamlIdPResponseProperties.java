/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  com.google.common.base.Splitter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.saml.idp;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.google.common.base.Splitter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Generated;
import org.apereo.cas.configuration.support.DurationCapable;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-saml-idp")
@JsonFilter(value="SamlIdPResponseProperties")
public class SamlIdPResponseProperties
implements Serializable {
    private static final long serialVersionUID = 7200477683583467619L;
    private SignatureCredentialTypes credentialType = SignatureCredentialTypes.X509;
    @DurationCapable
    private String skewAllowance = "PT30S";
    private boolean signError;
    private String defaultAuthenticationContextClass = "urn:oasis:names:tc:SAML:2.0:ac:classes:PasswordProtectedTransport";
    private String defaultAttributeNameFormat = "uri";
    private List<String> attributeNameFormats = new ArrayList<String>(0);

    public Map<String, String> configureAttributeNameFormats() {
        if (this.attributeNameFormats.isEmpty()) {
            return new HashMap<String, String>(0);
        }
        HashMap<String, String> nameFormats = new HashMap<String, String>();
        this.attributeNameFormats.forEach(value -> Arrays.stream(value.split(",")).forEach(format -> {
            List values = Splitter.on((String)"->").splitToList((CharSequence)format);
            if (values.size() == 2) {
                nameFormats.put((String)values.get(0), (String)values.get(1));
            }
        }));
        return nameFormats;
    }

    @Generated
    public SignatureCredentialTypes getCredentialType() {
        return this.credentialType;
    }

    @Generated
    public String getSkewAllowance() {
        return this.skewAllowance;
    }

    @Generated
    public boolean isSignError() {
        return this.signError;
    }

    @Generated
    public String getDefaultAuthenticationContextClass() {
        return this.defaultAuthenticationContextClass;
    }

    @Generated
    public String getDefaultAttributeNameFormat() {
        return this.defaultAttributeNameFormat;
    }

    @Generated
    public List<String> getAttributeNameFormats() {
        return this.attributeNameFormats;
    }

    @Generated
    public SamlIdPResponseProperties setCredentialType(SignatureCredentialTypes credentialType) {
        this.credentialType = credentialType;
        return this;
    }

    @Generated
    public SamlIdPResponseProperties setSkewAllowance(String skewAllowance) {
        this.skewAllowance = skewAllowance;
        return this;
    }

    @Generated
    public SamlIdPResponseProperties setSignError(boolean signError) {
        this.signError = signError;
        return this;
    }

    @Generated
    public SamlIdPResponseProperties setDefaultAuthenticationContextClass(String defaultAuthenticationContextClass) {
        this.defaultAuthenticationContextClass = defaultAuthenticationContextClass;
        return this;
    }

    @Generated
    public SamlIdPResponseProperties setDefaultAttributeNameFormat(String defaultAttributeNameFormat) {
        this.defaultAttributeNameFormat = defaultAttributeNameFormat;
        return this;
    }

    @Generated
    public SamlIdPResponseProperties setAttributeNameFormats(List<String> attributeNameFormats) {
        this.attributeNameFormats = attributeNameFormats;
        return this;
    }

    public static enum SignatureCredentialTypes {
        BASIC,
        X509;

    }
}

