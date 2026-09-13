/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.oidc;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Generated;
import org.apereo.cas.configuration.support.DurationCapable;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-oidc")
@JsonFilter(value="OidcCoreProperties")
public class OidcCoreProperties
implements Serializable {
    private static final long serialVersionUID = 823028615694269276L;
    @RequiredProperty
    private String issuer = "http://localhost:8080/cas/oidc";
    private String acceptedIssuersPattern = "a^";
    @DurationCapable
    private String skew = "PT5M";
    private Map<String, String> userDefinedScopes = new HashMap<String, String>(0);
    private Map<String, String> claimsMap = new HashMap<String, String>(0);
    private List<String> authenticationContextReferenceMappings = new ArrayList<String>(0);

    @Generated
    public String getIssuer() {
        return this.issuer;
    }

    @Generated
    public String getAcceptedIssuersPattern() {
        return this.acceptedIssuersPattern;
    }

    @Generated
    public String getSkew() {
        return this.skew;
    }

    @Generated
    public Map<String, String> getUserDefinedScopes() {
        return this.userDefinedScopes;
    }

    @Generated
    public Map<String, String> getClaimsMap() {
        return this.claimsMap;
    }

    @Generated
    public List<String> getAuthenticationContextReferenceMappings() {
        return this.authenticationContextReferenceMappings;
    }

    @Generated
    public OidcCoreProperties setIssuer(String issuer) {
        this.issuer = issuer;
        return this;
    }

    @Generated
    public OidcCoreProperties setAcceptedIssuersPattern(String acceptedIssuersPattern) {
        this.acceptedIssuersPattern = acceptedIssuersPattern;
        return this;
    }

    @Generated
    public OidcCoreProperties setSkew(String skew) {
        this.skew = skew;
        return this;
    }

    @Generated
    public OidcCoreProperties setUserDefinedScopes(Map<String, String> userDefinedScopes) {
        this.userDefinedScopes = userDefinedScopes;
        return this;
    }

    @Generated
    public OidcCoreProperties setClaimsMap(Map<String, String> claimsMap) {
        this.claimsMap = claimsMap;
        return this;
    }

    @Generated
    public OidcCoreProperties setAuthenticationContextReferenceMappings(List<String> authenticationContextReferenceMappings) {
        this.authenticationContextReferenceMappings = authenticationContextReferenceMappings;
        return this;
    }
}

