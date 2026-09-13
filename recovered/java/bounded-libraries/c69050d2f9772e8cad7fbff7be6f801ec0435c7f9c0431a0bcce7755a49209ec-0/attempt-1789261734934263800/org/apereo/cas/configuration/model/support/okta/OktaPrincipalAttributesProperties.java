/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.okta;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Generated;
import org.apereo.cas.configuration.model.SpringResourceProperties;
import org.apereo.cas.configuration.model.support.okta.BaseOktaProperties;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-okta-authentication")
@JsonFilter(value="OktaPrincipalAttributesProperties")
public class OktaPrincipalAttributesProperties
extends BaseOktaProperties {
    private static final long serialVersionUID = -6573755681498251678L;
    @RequiredProperty
    private String usernameAttribute = "username";
    private String id;
    @RequiredProperty
    private List<String> scopes = Stream.of("okta.users.read", "okta.apps.read").collect(Collectors.toList());
    @RequiredProperty
    private String clientId;
    @NestedConfigurationProperty
    private SpringResourceProperties privateKey = new SpringResourceProperties();
    private String apiToken;

    @Generated
    public String getUsernameAttribute() {
        return this.usernameAttribute;
    }

    @Generated
    public String getId() {
        return this.id;
    }

    @Generated
    public List<String> getScopes() {
        return this.scopes;
    }

    @Generated
    public String getClientId() {
        return this.clientId;
    }

    @Generated
    public SpringResourceProperties getPrivateKey() {
        return this.privateKey;
    }

    @Generated
    public String getApiToken() {
        return this.apiToken;
    }

    @Generated
    public OktaPrincipalAttributesProperties setUsernameAttribute(String usernameAttribute) {
        this.usernameAttribute = usernameAttribute;
        return this;
    }

    @Generated
    public OktaPrincipalAttributesProperties setId(String id) {
        this.id = id;
        return this;
    }

    @Generated
    public OktaPrincipalAttributesProperties setScopes(List<String> scopes) {
        this.scopes = scopes;
        return this;
    }

    @Generated
    public OktaPrincipalAttributesProperties setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    @Generated
    public OktaPrincipalAttributesProperties setPrivateKey(SpringResourceProperties privateKey) {
        this.privateKey = privateKey;
        return this;
    }

    @Generated
    public OktaPrincipalAttributesProperties setApiToken(String apiToken) {
        this.apiToken = apiToken;
        return this;
    }
}

