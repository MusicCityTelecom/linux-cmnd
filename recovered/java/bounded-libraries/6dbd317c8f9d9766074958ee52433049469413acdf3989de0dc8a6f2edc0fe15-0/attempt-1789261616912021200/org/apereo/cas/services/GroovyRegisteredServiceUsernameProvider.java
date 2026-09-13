/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.authentication.principal.Principal
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.configuration.support.ExpressionLanguageCapable
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.util.CollectionUtils
 *  org.apereo.cas.util.scripting.ExecutableCompiledGroovyScript
 *  org.apereo.cas.util.spring.ApplicationContextProvider
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.configuration.support.ExpressionLanguageCapable;
import org.apereo.cas.services.BaseRegisteredServiceUsernameAttributeProvider;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.util.CollectionUtils;
import org.apereo.cas.util.scripting.ExecutableCompiledGroovyScript;
import org.apereo.cas.util.spring.ApplicationContextProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GroovyRegisteredServiceUsernameProvider
extends BaseRegisteredServiceUsernameAttributeProvider {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(GroovyRegisteredServiceUsernameProvider.class);
    private static final long serialVersionUID = 5823989148794052951L;
    @ExpressionLanguageCapable
    private String groovyScript;

    @JsonCreator
    public GroovyRegisteredServiceUsernameProvider(@JsonProperty(value="groovyScript") String script) {
        this.groovyScript = script;
    }

    private static String fetchAttributeValue(Principal principal, Service service, RegisteredService registeredService, String groovyScript) {
        return ApplicationContextProvider.getScriptResourceCacheManager().map(cacheMgr -> {
            ExecutableCompiledGroovyScript script = cacheMgr.resolveScriptableResource(groovyScript, new String[]{registeredService.getServiceId(), groovyScript});
            return GroovyRegisteredServiceUsernameProvider.fetchAttributeValueFromScript(script, principal, service);
        }).map(result -> result.toString()).orElseThrow(() -> new RuntimeException("No groovy script cache manager is available to execute username provider"));
    }

    @Override
    public String resolveUsernameInternal(Principal principal, Service service, RegisteredService registeredService) {
        String result;
        if (StringUtils.isNotBlank((CharSequence)this.groovyScript) && (result = GroovyRegisteredServiceUsernameProvider.fetchAttributeValue(principal, service, registeredService, this.groovyScript)) != null) {
            LOGGER.debug("Found username [{}] from script", (Object)result);
            return result;
        }
        LOGGER.warn("Groovy script [{}] is not valid. CAS will switch to use the default principal identifier [{}]", (Object)this.groovyScript, (Object)principal.getId());
        return principal.getId();
    }

    private static Object fetchAttributeValueFromScript(ExecutableCompiledGroovyScript script, Principal principal, Service service) {
        Map args = CollectionUtils.wrap((String)"attributes", (Object)principal.getAttributes(), (String)"id", (Object)principal.getId(), (String)"service", (Object)service, (String)"logger", (Object)LOGGER);
        script.setBinding(args);
        return script.execute(args.values().toArray(), Object.class);
    }

    @Generated
    public String getGroovyScript() {
        return this.groovyScript;
    }

    @Generated
    public GroovyRegisteredServiceUsernameProvider setGroovyScript(String groovyScript) {
        this.groovyScript = groovyScript;
        return this;
    }

    @Generated
    public GroovyRegisteredServiceUsernameProvider() {
    }

    @Override
    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof GroovyRegisteredServiceUsernameProvider)) {
            return false;
        }
        GroovyRegisteredServiceUsernameProvider other = (GroovyRegisteredServiceUsernameProvider)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        String this$groovyScript = this.groovyScript;
        String other$groovyScript = other.groovyScript;
        return !(this$groovyScript == null ? other$groovyScript != null : !this$groovyScript.equals(other$groovyScript));
    }

    @Override
    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof GroovyRegisteredServiceUsernameProvider;
    }

    @Override
    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        String $groovyScript = this.groovyScript;
        result = result * 59 + ($groovyScript == null ? 43 : $groovyScript.hashCode());
        return result;
    }
}

