/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  lombok.Generated
 *  org.apereo.cas.authentication.principal.Principal
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.configuration.support.ExpressionLanguageCapable
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.util.scripting.ScriptingUtils
 *  org.apereo.cas.util.spring.SpringExpressionLanguageValueResolver
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Generated;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.configuration.support.ExpressionLanguageCapable;
import org.apereo.cas.services.BaseRegisteredServiceUsernameAttributeProvider;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.util.scripting.ScriptingUtils;
import org.apereo.cas.util.spring.SpringExpressionLanguageValueResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonInclude(value=JsonInclude.Include.NON_DEFAULT)
@Deprecated(since="6.2.0")
public class ScriptedRegisteredServiceUsernameProvider
extends BaseRegisteredServiceUsernameAttributeProvider {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(ScriptedRegisteredServiceUsernameProvider.class);
    private static final long serialVersionUID = -678554831202936052L;
    @ExpressionLanguageCapable
    private String script;

    @Override
    protected String resolveUsernameInternal(Principal principal, Service service, RegisteredService registeredService) {
        LOGGER.trace("Found groovy script [{}] to execute", (Object)this.script);
        Object[] args = new Object[]{principal.getAttributes(), principal.getId(), LOGGER};
        Object result = ScriptingUtils.executeScriptEngine((String)SpringExpressionLanguageValueResolver.getInstance().resolve(this.script), (Object[])args, Object.class);
        if (result != null) {
            LOGGER.debug("Found username [{}] from script [{}]", result, (Object)this.script);
            return result.toString();
        }
        LOGGER.warn("Script [{}] returned no value for username attribute. Fallback to default [{}]", (Object)this.script, (Object)principal.getId());
        return principal.getId();
    }

    @Generated
    public String getScript() {
        return this.script;
    }

    @Generated
    public void setScript(String script) {
        this.script = script;
    }

    @Generated
    public ScriptedRegisteredServiceUsernameProvider() {
    }

    @Override
    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ScriptedRegisteredServiceUsernameProvider)) {
            return false;
        }
        ScriptedRegisteredServiceUsernameProvider other = (ScriptedRegisteredServiceUsernameProvider)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        String this$script = this.script;
        String other$script = other.script;
        return !(this$script == null ? other$script != null : !this$script.equals(other$script));
    }

    @Override
    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof ScriptedRegisteredServiceUsernameProvider;
    }

    @Override
    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        String $script = this.script;
        result = result * 59 + ($script == null ? 43 : $script.hashCode());
        return result;
    }

    @Generated
    public ScriptedRegisteredServiceUsernameProvider(String script) {
        this.script = script;
    }
}

