/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  lombok.Generated
 *  org.apereo.cas.authentication.AuthenticationPolicy
 *  org.apereo.cas.authentication.policy.RestfulAuthenticationPolicy
 *  org.apereo.cas.configuration.model.core.authentication.RestAuthenticationPolicyProperties
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.services.RegisteredServiceAuthenticationPolicyCriteria
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Generated;
import org.apereo.cas.authentication.AuthenticationPolicy;
import org.apereo.cas.authentication.policy.RestfulAuthenticationPolicy;
import org.apereo.cas.configuration.model.core.authentication.RestAuthenticationPolicyProperties;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.RegisteredServiceAuthenticationPolicyCriteria;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
@JsonInclude(value=JsonInclude.Include.NON_DEFAULT)
public class RestfulRegisteredServiceAuthenticationPolicyCriteria
implements RegisteredServiceAuthenticationPolicyCriteria {
    private static final long serialVersionUID = -2915826778096374574L;
    private String url;
    private String basicAuthUsername;
    private String basicAuthPassword;

    public AuthenticationPolicy toAuthenticationPolicy(RegisteredService registeredService) {
        RestAuthenticationPolicyProperties props = new RestAuthenticationPolicyProperties();
        props.setUrl(this.url);
        props.setBasicAuthUsername(this.basicAuthUsername);
        props.setBasicAuthPassword(this.basicAuthPassword);
        return new RestfulAuthenticationPolicy(props);
    }

    @Generated
    public String toString() {
        return "RestfulRegisteredServiceAuthenticationPolicyCriteria(url=" + this.url + ", basicAuthUsername=" + this.basicAuthUsername + ", basicAuthPassword=" + this.basicAuthPassword + ")";
    }

    @Generated
    public String getUrl() {
        return this.url;
    }

    @Generated
    public String getBasicAuthUsername() {
        return this.basicAuthUsername;
    }

    @Generated
    public String getBasicAuthPassword() {
        return this.basicAuthPassword;
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RestfulRegisteredServiceAuthenticationPolicyCriteria)) {
            return false;
        }
        RestfulRegisteredServiceAuthenticationPolicyCriteria other = (RestfulRegisteredServiceAuthenticationPolicyCriteria)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$url = this.url;
        String other$url = other.url;
        if (this$url == null ? other$url != null : !this$url.equals(other$url)) {
            return false;
        }
        String this$basicAuthUsername = this.basicAuthUsername;
        String other$basicAuthUsername = other.basicAuthUsername;
        if (this$basicAuthUsername == null ? other$basicAuthUsername != null : !this$basicAuthUsername.equals(other$basicAuthUsername)) {
            return false;
        }
        String this$basicAuthPassword = this.basicAuthPassword;
        String other$basicAuthPassword = other.basicAuthPassword;
        return !(this$basicAuthPassword == null ? other$basicAuthPassword != null : !this$basicAuthPassword.equals(other$basicAuthPassword));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof RestfulRegisteredServiceAuthenticationPolicyCriteria;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $url = this.url;
        result = result * 59 + ($url == null ? 43 : $url.hashCode());
        String $basicAuthUsername = this.basicAuthUsername;
        result = result * 59 + ($basicAuthUsername == null ? 43 : $basicAuthUsername.hashCode());
        String $basicAuthPassword = this.basicAuthPassword;
        result = result * 59 + ($basicAuthPassword == null ? 43 : $basicAuthPassword.hashCode());
        return result;
    }

    @Generated
    public RestfulRegisteredServiceAuthenticationPolicyCriteria setUrl(String url) {
        this.url = url;
        return this;
    }

    @Generated
    public RestfulRegisteredServiceAuthenticationPolicyCriteria setBasicAuthUsername(String basicAuthUsername) {
        this.basicAuthUsername = basicAuthUsername;
        return this;
    }

    @Generated
    public RestfulRegisteredServiceAuthenticationPolicyCriteria setBasicAuthPassword(String basicAuthPassword) {
        this.basicAuthPassword = basicAuthPassword;
        return this;
    }
}

