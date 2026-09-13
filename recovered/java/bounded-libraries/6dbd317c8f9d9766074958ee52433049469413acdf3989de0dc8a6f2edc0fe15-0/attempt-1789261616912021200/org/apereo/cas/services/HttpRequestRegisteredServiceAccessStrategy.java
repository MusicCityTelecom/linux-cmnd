/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.util.RegexUtils
 *  org.apereo.inspektr.common.web.ClientInfoHolder
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Optional;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.services.BaseRegisteredServiceAccessStrategy;
import org.apereo.cas.util.RegexUtils;
import org.apereo.inspektr.common.web.ClientInfoHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonInclude(value=JsonInclude.Include.NON_DEFAULT)
public class HttpRequestRegisteredServiceAccessStrategy
extends BaseRegisteredServiceAccessStrategy {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpRequestRegisteredServiceAccessStrategy.class);
    private static final long serialVersionUID = -1108201604115278440L;
    private String ipAddress;
    private String userAgent;

    public boolean isServiceAccessAllowed() {
        return Optional.ofNullable(ClientInfoHolder.getClientInfo()).stream().anyMatch(info -> {
            boolean match = true;
            if (StringUtils.isNoneBlank((CharSequence[])new CharSequence[]{this.ipAddress})) {
                LOGGER.debug("Evaluating IP address [{}] against pattern [{}]", (Object)info.getClientIpAddress(), (Object)this.ipAddress);
                match = RegexUtils.find((String)this.ipAddress, (String)info.getClientIpAddress());
            }
            if (match && StringUtils.isNoneBlank((CharSequence[])new CharSequence[]{this.userAgent})) {
                LOGGER.debug("Evaluating user agent [{}] against pattern [{}]", (Object)info.getUserAgent(), (Object)this.userAgent);
                match = RegexUtils.find((String)this.userAgent, (String)info.getUserAgent());
            }
            return match;
        });
    }

    @Override
    @Generated
    public String toString() {
        return "HttpRequestRegisteredServiceAccessStrategy(super=" + super.toString() + ", ipAddress=" + this.ipAddress + ", userAgent=" + this.userAgent + ")";
    }

    @Generated
    public String getIpAddress() {
        return this.ipAddress;
    }

    @Generated
    public String getUserAgent() {
        return this.userAgent;
    }

    @Generated
    public HttpRequestRegisteredServiceAccessStrategy setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
        return this;
    }

    @Generated
    public HttpRequestRegisteredServiceAccessStrategy setUserAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
    }

    @Override
    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof HttpRequestRegisteredServiceAccessStrategy)) {
            return false;
        }
        HttpRequestRegisteredServiceAccessStrategy other = (HttpRequestRegisteredServiceAccessStrategy)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        String this$ipAddress = this.ipAddress;
        String other$ipAddress = other.ipAddress;
        if (this$ipAddress == null ? other$ipAddress != null : !this$ipAddress.equals(other$ipAddress)) {
            return false;
        }
        String this$userAgent = this.userAgent;
        String other$userAgent = other.userAgent;
        return !(this$userAgent == null ? other$userAgent != null : !this$userAgent.equals(other$userAgent));
    }

    @Override
    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof HttpRequestRegisteredServiceAccessStrategy;
    }

    @Override
    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        String $ipAddress = this.ipAddress;
        result = result * 59 + ($ipAddress == null ? 43 : $ipAddress.hashCode());
        String $userAgent = this.userAgent;
        result = result * 59 + ($userAgent == null ? 43 : $userAgent.hashCode());
        return result;
    }

    @Generated
    public HttpRequestRegisteredServiceAccessStrategy() {
    }
}

