/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  lombok.Generated
 *  org.apache.http.HttpResponse
 *  org.apereo.cas.util.CollectionUtils
 *  org.apereo.cas.util.HttpUtils
 *  org.apereo.cas.util.HttpUtils$HttpExecutionRequest
 *  org.springframework.http.HttpMethod
 *  org.springframework.util.StringUtils
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Map;
import java.util.Set;
import lombok.Generated;
import org.apache.http.HttpResponse;
import org.apereo.cas.services.BaseRegisteredServiceAccessStrategy;
import org.apereo.cas.util.CollectionUtils;
import org.apereo.cas.util.HttpUtils;
import org.springframework.http.HttpMethod;
import org.springframework.util.StringUtils;

@JsonInclude(value=JsonInclude.Include.NON_DEFAULT)
public class RemoteEndpointServiceAccessStrategy
extends BaseRegisteredServiceAccessStrategy {
    private static final long serialVersionUID = -1108201604115278440L;
    private String endpointUrl;
    private String acceptableResponseCodes;

    public boolean doPrincipalAttributesAllowServiceAccess(String principal, Map<String, Object> principalAttributes) {
        HttpUtils.HttpExecutionRequest exec = HttpUtils.HttpExecutionRequest.builder().method(HttpMethod.GET).url(this.endpointUrl).parameters(CollectionUtils.wrap((String)"username", (Object)principal)).build();
        HttpResponse response = HttpUtils.execute((HttpUtils.HttpExecutionRequest)exec);
        Set currentCodes = StringUtils.commaDelimitedListToSet((String)this.acceptableResponseCodes);
        return response != null && currentCodes.contains(String.valueOf(response.getStatusLine().getStatusCode()));
    }

    @Override
    @Generated
    public String toString() {
        return "RemoteEndpointServiceAccessStrategy(super=" + super.toString() + ", endpointUrl=" + this.endpointUrl + ", acceptableResponseCodes=" + this.acceptableResponseCodes + ")";
    }

    @Generated
    public String getEndpointUrl() {
        return this.endpointUrl;
    }

    @Generated
    public String getAcceptableResponseCodes() {
        return this.acceptableResponseCodes;
    }

    @Generated
    public RemoteEndpointServiceAccessStrategy setEndpointUrl(String endpointUrl) {
        this.endpointUrl = endpointUrl;
        return this;
    }

    @Generated
    public RemoteEndpointServiceAccessStrategy setAcceptableResponseCodes(String acceptableResponseCodes) {
        this.acceptableResponseCodes = acceptableResponseCodes;
        return this;
    }

    @Override
    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RemoteEndpointServiceAccessStrategy)) {
            return false;
        }
        RemoteEndpointServiceAccessStrategy other = (RemoteEndpointServiceAccessStrategy)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        String this$endpointUrl = this.endpointUrl;
        String other$endpointUrl = other.endpointUrl;
        if (this$endpointUrl == null ? other$endpointUrl != null : !this$endpointUrl.equals(other$endpointUrl)) {
            return false;
        }
        String this$acceptableResponseCodes = this.acceptableResponseCodes;
        String other$acceptableResponseCodes = other.acceptableResponseCodes;
        return !(this$acceptableResponseCodes == null ? other$acceptableResponseCodes != null : !this$acceptableResponseCodes.equals(other$acceptableResponseCodes));
    }

    @Override
    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof RemoteEndpointServiceAccessStrategy;
    }

    @Override
    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        String $endpointUrl = this.endpointUrl;
        result = result * 59 + ($endpointUrl == null ? 43 : $endpointUrl.hashCode());
        String $acceptableResponseCodes = this.acceptableResponseCodes;
        result = result * 59 + ($acceptableResponseCodes == null ? 43 : $acceptableResponseCodes.hashCode());
        return result;
    }

    @Generated
    public RemoteEndpointServiceAccessStrategy() {
    }
}

