/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  javax.persistence.Transient
 *  lombok.Generated
 *  org.apereo.cas.configuration.support.ExpressionLanguageCapable
 *  org.apereo.cas.services.RegisteredServiceAccessStrategy
 *  org.apereo.cas.services.RegisteredServiceDelegatedAuthenticationPolicy
 *  org.apereo.cas.util.ResourceUtils
 *  org.apereo.cas.util.function.FunctionUtils
 *  org.apereo.cas.util.scripting.ScriptingUtils
 *  org.apereo.cas.util.spring.SpringExpressionLanguageValueResolver
 *  org.springframework.core.io.AbstractResource
 *  org.springframework.core.io.Resource
 *  org.springframework.data.annotation.Transient
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.net.URI;
import java.util.Map;
import java.util.Set;
import javax.persistence.Transient;
import lombok.Generated;
import org.apereo.cas.configuration.support.ExpressionLanguageCapable;
import org.apereo.cas.services.BaseRegisteredServiceAccessStrategy;
import org.apereo.cas.services.RegisteredServiceAccessStrategy;
import org.apereo.cas.services.RegisteredServiceDelegatedAuthenticationPolicy;
import org.apereo.cas.util.ResourceUtils;
import org.apereo.cas.util.function.FunctionUtils;
import org.apereo.cas.util.scripting.ScriptingUtils;
import org.apereo.cas.util.spring.SpringExpressionLanguageValueResolver;
import org.springframework.core.io.AbstractResource;
import org.springframework.core.io.Resource;

public class GroovyRegisteredServiceAccessStrategy
extends BaseRegisteredServiceAccessStrategy {
    private static final long serialVersionUID = -2407494148882123062L;
    private int order;
    @ExpressionLanguageCapable
    private String groovyScript;
    @JsonIgnore
    @Transient
    @org.springframework.data.annotation.Transient
    private transient RegisteredServiceAccessStrategy groovyStrategyInstance;

    @JsonIgnore
    public boolean isServiceAccessAllowed() {
        this.buildGroovyAccessStrategyInstanceIfNeeded();
        return this.groovyStrategyInstance.isServiceAccessAllowed();
    }

    @JsonIgnore
    public void setServiceAccessAllowed(boolean enabled) {
        this.buildGroovyAccessStrategyInstanceIfNeeded();
        this.groovyStrategyInstance.setServiceAccessAllowed(enabled);
    }

    @JsonIgnore
    public boolean isServiceAccessAllowedForSso() {
        this.buildGroovyAccessStrategyInstanceIfNeeded();
        return this.groovyStrategyInstance.isServiceAccessAllowedForSso();
    }

    @JsonIgnore
    public boolean doPrincipalAttributesAllowServiceAccess(String principal, Map<String, Object> attributes) {
        this.buildGroovyAccessStrategyInstanceIfNeeded();
        return this.groovyStrategyInstance.doPrincipalAttributesAllowServiceAccess(principal, attributes);
    }

    @JsonIgnore
    public URI getUnauthorizedRedirectUrl() {
        this.buildGroovyAccessStrategyInstanceIfNeeded();
        return this.groovyStrategyInstance.getUnauthorizedRedirectUrl();
    }

    @JsonIgnore
    public RegisteredServiceDelegatedAuthenticationPolicy getDelegatedAuthenticationPolicy() {
        this.buildGroovyAccessStrategyInstanceIfNeeded();
        return this.groovyStrategyInstance.getDelegatedAuthenticationPolicy();
    }

    @JsonIgnore
    public Map<String, Set<String>> getRequiredAttributes() {
        return this.groovyStrategyInstance.getRequiredAttributes();
    }

    private void buildGroovyAccessStrategyInstanceIfNeeded() {
        if (this.groovyStrategyInstance == null) {
            AbstractResource groovyResource = (AbstractResource)FunctionUtils.doUnchecked(() -> {
                String location = SpringExpressionLanguageValueResolver.getInstance().resolve(this.groovyScript);
                return ResourceUtils.getResourceFrom((String)location);
            });
            this.groovyStrategyInstance = (RegisteredServiceAccessStrategy)ScriptingUtils.getObjectInstanceFromGroovyResource((Resource)groovyResource, RegisteredServiceAccessStrategy.class);
        }
    }

    @Generated
    public int getOrder() {
        return this.order;
    }

    @Generated
    public String getGroovyScript() {
        return this.groovyScript;
    }

    @Generated
    public RegisteredServiceAccessStrategy getGroovyStrategyInstance() {
        return this.groovyStrategyInstance;
    }

    @Generated
    public GroovyRegisteredServiceAccessStrategy setOrder(int order) {
        this.order = order;
        return this;
    }

    @Generated
    public GroovyRegisteredServiceAccessStrategy setGroovyScript(String groovyScript) {
        this.groovyScript = groovyScript;
        return this;
    }

    @JsonIgnore
    @Generated
    public GroovyRegisteredServiceAccessStrategy setGroovyStrategyInstance(RegisteredServiceAccessStrategy groovyStrategyInstance) {
        this.groovyStrategyInstance = groovyStrategyInstance;
        return this;
    }

    @Generated
    public GroovyRegisteredServiceAccessStrategy() {
    }

    @Override
    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof GroovyRegisteredServiceAccessStrategy)) {
            return false;
        }
        GroovyRegisteredServiceAccessStrategy other = (GroovyRegisteredServiceAccessStrategy)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        if (this.order != other.order) {
            return false;
        }
        String this$groovyScript = this.groovyScript;
        String other$groovyScript = other.groovyScript;
        return !(this$groovyScript == null ? other$groovyScript != null : !this$groovyScript.equals(other$groovyScript));
    }

    @Override
    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof GroovyRegisteredServiceAccessStrategy;
    }

    @Override
    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        result = result * 59 + this.order;
        String $groovyScript = this.groovyScript;
        result = result * 59 + ($groovyScript == null ? 43 : $groovyScript.hashCode());
        return result;
    }
}

