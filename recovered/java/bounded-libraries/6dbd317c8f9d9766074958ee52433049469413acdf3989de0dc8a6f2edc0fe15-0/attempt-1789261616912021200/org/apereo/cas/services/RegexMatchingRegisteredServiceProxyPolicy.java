/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  lombok.Generated
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.services.RegisteredServiceProxyPolicy
 *  org.apereo.cas.util.RegexUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.net.URL;
import lombok.Generated;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.RegisteredServiceProxyPolicy;
import org.apereo.cas.util.RegexUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonInclude(value=JsonInclude.Include.NON_DEFAULT)
public class RegexMatchingRegisteredServiceProxyPolicy
implements RegisteredServiceProxyPolicy {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(RegexMatchingRegisteredServiceProxyPolicy.class);
    private static final long serialVersionUID = -211069319543047324L;
    private String pattern;
    private boolean useServiceId;
    private boolean exactMatch;

    @JsonIgnore
    public boolean isAllowedToProxy() {
        return true;
    }

    public boolean isAllowedProxyCallbackUrl(RegisteredService registeredService, URL pgtUrl) {
        String patternToUse;
        String string = patternToUse = this.useServiceId ? registeredService.getServiceId() : this.pattern;
        if (!RegexUtils.isValidRegex((String)patternToUse)) {
            LOGGER.warn("Pattern specified [{}] is not a valid regular expression", (Object)patternToUse);
            return false;
        }
        if (this.exactMatch) {
            LOGGER.debug("Pattern [{}] is compared against URL [{}] for exact equality", (Object)patternToUse, (Object)pgtUrl.toExternalForm());
            return patternToUse.equals(pgtUrl.toExternalForm());
        }
        LOGGER.debug("Using pattern [{}] to authorize proxy policy for URL [{}]", (Object)patternToUse, (Object)pgtUrl.toExternalForm());
        return RegexUtils.find((String)patternToUse, (String)pgtUrl.toExternalForm());
    }

    @Generated
    public String toString() {
        return "RegexMatchingRegisteredServiceProxyPolicy(pattern=" + this.pattern + ", useServiceId=" + this.useServiceId + ", exactMatch=" + this.exactMatch + ")";
    }

    @Generated
    public String getPattern() {
        return this.pattern;
    }

    @Generated
    public boolean isUseServiceId() {
        return this.useServiceId;
    }

    @Generated
    public boolean isExactMatch() {
        return this.exactMatch;
    }

    @Generated
    public RegexMatchingRegisteredServiceProxyPolicy setPattern(String pattern) {
        this.pattern = pattern;
        return this;
    }

    @Generated
    public RegexMatchingRegisteredServiceProxyPolicy setUseServiceId(boolean useServiceId) {
        this.useServiceId = useServiceId;
        return this;
    }

    @Generated
    public RegexMatchingRegisteredServiceProxyPolicy setExactMatch(boolean exactMatch) {
        this.exactMatch = exactMatch;
        return this;
    }

    @Generated
    public RegexMatchingRegisteredServiceProxyPolicy() {
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RegexMatchingRegisteredServiceProxyPolicy)) {
            return false;
        }
        RegexMatchingRegisteredServiceProxyPolicy other = (RegexMatchingRegisteredServiceProxyPolicy)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.useServiceId != other.useServiceId) {
            return false;
        }
        if (this.exactMatch != other.exactMatch) {
            return false;
        }
        String this$pattern = this.pattern;
        String other$pattern = other.pattern;
        return !(this$pattern == null ? other$pattern != null : !this$pattern.equals(other$pattern));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof RegexMatchingRegisteredServiceProxyPolicy;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + (this.useServiceId ? 79 : 97);
        result = result * 59 + (this.exactMatch ? 79 : 97);
        String $pattern = this.pattern;
        result = result * 59 + ($pattern == null ? 43 : $pattern.hashCode());
        return result;
    }
}

