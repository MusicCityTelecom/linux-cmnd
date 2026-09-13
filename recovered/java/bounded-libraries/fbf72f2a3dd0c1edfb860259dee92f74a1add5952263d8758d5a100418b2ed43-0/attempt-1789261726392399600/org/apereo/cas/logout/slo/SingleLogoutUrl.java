/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.services.RegisteredServiceLogoutType
 *  org.apereo.cas.services.WebBasedRegisteredService
 *  org.springframework.util.StringUtils
 */
package org.apereo.cas.logout.slo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Generated;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.RegisteredServiceLogoutType;
import org.apereo.cas.services.WebBasedRegisteredService;
import org.springframework.util.StringUtils;

public class SingleLogoutUrl
implements Serializable {
    private static final long serialVersionUID = 6611608175787696823L;
    private final String url;
    private final RegisteredServiceLogoutType logoutType;
    private final Map<String, String> properties = new LinkedHashMap<String, String>(2);

    public static List<SingleLogoutUrl> from(RegisteredService service) {
        WebBasedRegisteredService registeredService;
        if (service instanceof WebBasedRegisteredService && StringUtils.hasText((String)(registeredService = (WebBasedRegisteredService)service).getLogoutUrl())) {
            return Arrays.stream(StringUtils.commaDelimitedListToStringArray((String)registeredService.getLogoutUrl())).map(url -> new SingleLogoutUrl((String)url, registeredService.getLogoutType())).collect(Collectors.toList());
        }
        return new ArrayList<SingleLogoutUrl>(0);
    }

    @Generated
    public SingleLogoutUrl(String url, RegisteredServiceLogoutType logoutType) {
        this.url = url;
        this.logoutType = logoutType;
    }

    @Generated
    public String getUrl() {
        return this.url;
    }

    @Generated
    public RegisteredServiceLogoutType getLogoutType() {
        return this.logoutType;
    }

    @Generated
    public Map<String, String> getProperties() {
        return this.properties;
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof SingleLogoutUrl)) {
            return false;
        }
        SingleLogoutUrl other = (SingleLogoutUrl)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$url = this.url;
        String other$url = other.url;
        if (this$url == null ? other$url != null : !this$url.equals(other$url)) {
            return false;
        }
        RegisteredServiceLogoutType this$logoutType = this.logoutType;
        RegisteredServiceLogoutType other$logoutType = other.logoutType;
        if (this$logoutType == null ? other$logoutType != null : !this$logoutType.equals(other$logoutType)) {
            return false;
        }
        Map<String, String> this$properties = this.properties;
        Map<String, String> other$properties = other.properties;
        return !(this$properties == null ? other$properties != null : !((Object)this$properties).equals(other$properties));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof SingleLogoutUrl;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $url = this.url;
        result = result * 59 + ($url == null ? 43 : $url.hashCode());
        RegisteredServiceLogoutType $logoutType = this.logoutType;
        result = result * 59 + ($logoutType == null ? 43 : $logoutType.hashCode());
        Map<String, String> $properties = this.properties;
        result = result * 59 + ($properties == null ? 43 : ((Object)$properties).hashCode());
        return result;
    }
}

