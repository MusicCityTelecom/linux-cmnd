/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  lombok.Generated
 *  org.apereo.cas.services.CasModelRegisteredService
 *  org.apereo.cas.util.function.FunctionUtils
 */
package org.apereo.cas.authentication.credential;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.net.URL;
import lombok.Generated;
import org.apereo.cas.authentication.credential.AbstractCredential;
import org.apereo.cas.services.CasModelRegisteredService;
import org.apereo.cas.util.function.FunctionUtils;

public class HttpBasedServiceCredential
extends AbstractCredential {
    private static final long serialVersionUID = 1492607216336354503L;
    private URL callbackUrl;
    private CasModelRegisteredService service;

    @JsonCreator
    public HttpBasedServiceCredential(@JsonProperty(value="callbackUrl") String callbackUrl, @JsonProperty(value="service") CasModelRegisteredService service) {
        this.callbackUrl = (URL)FunctionUtils.doUnchecked(() -> new URL(callbackUrl));
        this.service = service;
    }

    @JsonIgnore
    public String getId() {
        return this.callbackUrl.toExternalForm();
    }

    @Generated
    public URL getCallbackUrl() {
        return this.callbackUrl;
    }

    @Generated
    public CasModelRegisteredService getService() {
        return this.service;
    }

    @Generated
    public void setCallbackUrl(URL callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    @Generated
    public void setService(CasModelRegisteredService service) {
        this.service = service;
    }

    @Generated
    public HttpBasedServiceCredential() {
    }

    @Generated
    public HttpBasedServiceCredential(URL callbackUrl, CasModelRegisteredService service) {
        this.callbackUrl = callbackUrl;
        this.service = service;
    }

    @Override
    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof HttpBasedServiceCredential)) {
            return false;
        }
        HttpBasedServiceCredential other = (HttpBasedServiceCredential)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        URL this$callbackUrl = this.callbackUrl;
        URL other$callbackUrl = other.callbackUrl;
        if (this$callbackUrl == null ? other$callbackUrl != null : !((Object)this$callbackUrl).equals(other$callbackUrl)) {
            return false;
        }
        CasModelRegisteredService this$service = this.service;
        CasModelRegisteredService other$service = other.service;
        return !(this$service == null ? other$service != null : !this$service.equals(other$service));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof HttpBasedServiceCredential;
    }

    @Override
    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        URL $callbackUrl = this.callbackUrl;
        result = result * 59 + ($callbackUrl == null ? 43 : ((Object)$callbackUrl).hashCode());
        CasModelRegisteredService $service = this.service;
        result = result * 59 + ($service == null ? 43 : $service.hashCode());
        return result;
    }
}

