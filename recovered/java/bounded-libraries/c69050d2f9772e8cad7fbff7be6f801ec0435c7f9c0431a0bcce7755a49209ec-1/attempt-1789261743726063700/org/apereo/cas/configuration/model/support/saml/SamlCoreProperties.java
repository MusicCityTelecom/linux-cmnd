/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.saml;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.DurationCapable;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-saml")
@JsonFilter(value="SamlCoreProperties")
public class SamlCoreProperties
implements Serializable {
    private static final long serialVersionUID = -8505851926931247878L;
    @DurationCapable
    private String skewAllowance = "PT30S";
    @DurationCapable
    private String issueLength = "PT30S";
    private String attributeNamespace = "http://www.ja-sig.org/products/cas/";
    private String issuer = "localhost";
    private boolean ticketidSaml2;
    private String securityManager = "org.apache.xerces.util.SecurityManager";

    @Generated
    public String getSkewAllowance() {
        return this.skewAllowance;
    }

    @Generated
    public String getIssueLength() {
        return this.issueLength;
    }

    @Generated
    public String getAttributeNamespace() {
        return this.attributeNamespace;
    }

    @Generated
    public String getIssuer() {
        return this.issuer;
    }

    @Generated
    public boolean isTicketidSaml2() {
        return this.ticketidSaml2;
    }

    @Generated
    public String getSecurityManager() {
        return this.securityManager;
    }

    @Generated
    public SamlCoreProperties setSkewAllowance(String skewAllowance) {
        this.skewAllowance = skewAllowance;
        return this;
    }

    @Generated
    public SamlCoreProperties setIssueLength(String issueLength) {
        this.issueLength = issueLength;
        return this;
    }

    @Generated
    public SamlCoreProperties setAttributeNamespace(String attributeNamespace) {
        this.attributeNamespace = attributeNamespace;
        return this;
    }

    @Generated
    public SamlCoreProperties setIssuer(String issuer) {
        this.issuer = issuer;
        return this;
    }

    @Generated
    public SamlCoreProperties setTicketidSaml2(boolean ticketidSaml2) {
        this.ticketidSaml2 = ticketidSaml2;
        return this;
    }

    @Generated
    public SamlCoreProperties setSecurityManager(String securityManager) {
        this.securityManager = securityManager;
        return this;
    }
}

