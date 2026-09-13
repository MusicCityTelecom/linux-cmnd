/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  javax.persistence.Transient
 *  lombok.Generated
 *  org.apereo.cas.configuration.model.support.mfa.BaseMultifactorAuthenticationProviderProperties$MultifactorAuthenticationProviderFailureModes
 *  org.apereo.cas.services.RegisteredServiceMultifactorPolicy
 *  org.apereo.cas.util.AsciiArtUtils
 *  org.apereo.cas.util.ResourceUtils
 *  org.apereo.cas.util.scripting.ScriptingUtils
 *  org.apereo.cas.util.spring.SpringExpressionLanguageValueResolver
 *  org.jooq.lambda.Unchecked
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.core.io.AbstractResource
 *  org.springframework.core.io.Resource
 *  org.springframework.data.annotation.Transient
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Set;
import javax.persistence.Transient;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.mfa.BaseMultifactorAuthenticationProviderProperties;
import org.apereo.cas.services.RegisteredServiceMultifactorPolicy;
import org.apereo.cas.util.AsciiArtUtils;
import org.apereo.cas.util.ResourceUtils;
import org.apereo.cas.util.scripting.ScriptingUtils;
import org.apereo.cas.util.spring.SpringExpressionLanguageValueResolver;
import org.jooq.lambda.Unchecked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.AbstractResource;
import org.springframework.core.io.Resource;

@JsonInclude(value=JsonInclude.Include.NON_DEFAULT)
@Deprecated(since="6.2.0")
public class GroovyRegisteredServiceMultifactorPolicy
implements RegisteredServiceMultifactorPolicy {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(GroovyRegisteredServiceMultifactorPolicy.class);
    private static final long serialVersionUID = -3075860754996106437L;
    private String groovyScript;
    @JsonIgnore
    @Transient
    @org.springframework.data.annotation.Transient
    private transient RegisteredServiceMultifactorPolicy groovyPolicyInstance;

    public GroovyRegisteredServiceMultifactorPolicy() {
        AsciiArtUtils.printAsciiArtWarning((Logger)LOGGER, (String)(this.getClass().getName() + " is now deprecated and scheduled to be removed in the future."));
    }

    @JsonIgnore
    public Set<String> getMultifactorAuthenticationProviders() {
        this.buildGroovyMultifactorPolicyInstanceIfNeeded();
        return this.groovyPolicyInstance.getMultifactorAuthenticationProviders();
    }

    @JsonIgnore
    public BaseMultifactorAuthenticationProviderProperties.MultifactorAuthenticationProviderFailureModes getFailureMode() {
        this.buildGroovyMultifactorPolicyInstanceIfNeeded();
        return this.groovyPolicyInstance.getFailureMode();
    }

    @JsonIgnore
    public String getPrincipalAttributeNameTrigger() {
        this.buildGroovyMultifactorPolicyInstanceIfNeeded();
        return this.groovyPolicyInstance.getPrincipalAttributeNameTrigger();
    }

    @JsonIgnore
    public String getPrincipalAttributeValueToMatch() {
        this.buildGroovyMultifactorPolicyInstanceIfNeeded();
        return this.groovyPolicyInstance.getPrincipalAttributeValueToMatch();
    }

    @JsonIgnore
    public boolean isBypassEnabled() {
        this.buildGroovyMultifactorPolicyInstanceIfNeeded();
        return this.groovyPolicyInstance.isBypassEnabled();
    }

    @JsonIgnore
    public boolean isForceExecution() {
        this.buildGroovyMultifactorPolicyInstanceIfNeeded();
        return this.groovyPolicyInstance.isForceExecution();
    }

    @JsonIgnore
    public boolean isBypassTrustedDeviceEnabled() {
        this.buildGroovyMultifactorPolicyInstanceIfNeeded();
        return this.groovyPolicyInstance.isBypassTrustedDeviceEnabled();
    }

    @JsonIgnore
    public String getBypassPrincipalAttributeName() {
        this.buildGroovyMultifactorPolicyInstanceIfNeeded();
        return this.groovyPolicyInstance.getBypassPrincipalAttributeName();
    }

    @JsonIgnore
    public String getBypassPrincipalAttributeValue() {
        this.buildGroovyMultifactorPolicyInstanceIfNeeded();
        return this.groovyPolicyInstance.getBypassPrincipalAttributeValue();
    }

    @JsonIgnore
    public String getScript() {
        this.buildGroovyMultifactorPolicyInstanceIfNeeded();
        return this.groovyPolicyInstance.getScript();
    }

    private void buildGroovyMultifactorPolicyInstanceIfNeeded() {
        Unchecked.consumer(o -> {
            if (this.groovyPolicyInstance == null) {
                AbstractResource groovyResource = ResourceUtils.getResourceFrom((String)SpringExpressionLanguageValueResolver.getInstance().resolve(this.groovyScript));
                this.groovyPolicyInstance = (RegisteredServiceMultifactorPolicy)ScriptingUtils.getObjectInstanceFromGroovyResource((Resource)groovyResource, RegisteredServiceMultifactorPolicy.class);
            }
        }).accept(this.groovyScript);
    }

    @Generated
    public String getGroovyScript() {
        return this.groovyScript;
    }

    @Generated
    public RegisteredServiceMultifactorPolicy getGroovyPolicyInstance() {
        return this.groovyPolicyInstance;
    }

    @Generated
    public void setGroovyScript(String groovyScript) {
        this.groovyScript = groovyScript;
    }

    @JsonIgnore
    @Generated
    public void setGroovyPolicyInstance(RegisteredServiceMultifactorPolicy groovyPolicyInstance) {
        this.groovyPolicyInstance = groovyPolicyInstance;
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof GroovyRegisteredServiceMultifactorPolicy)) {
            return false;
        }
        GroovyRegisteredServiceMultifactorPolicy other = (GroovyRegisteredServiceMultifactorPolicy)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$groovyScript = this.groovyScript;
        String other$groovyScript = other.groovyScript;
        return !(this$groovyScript == null ? other$groovyScript != null : !this$groovyScript.equals(other$groovyScript));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof GroovyRegisteredServiceMultifactorPolicy;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $groovyScript = this.groovyScript;
        result = result * 59 + ($groovyScript == null ? 43 : $groovyScript.hashCode());
        return result;
    }
}

