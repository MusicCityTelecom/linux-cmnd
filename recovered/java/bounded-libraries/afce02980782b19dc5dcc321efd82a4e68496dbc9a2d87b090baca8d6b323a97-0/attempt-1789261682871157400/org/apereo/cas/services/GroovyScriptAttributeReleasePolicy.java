/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  javax.validation.constraints.NotNull
 *  lombok.Generated
 *  org.apereo.cas.configuration.support.ExpressionLanguageCapable
 *  org.apereo.cas.services.RegisteredServiceAttributeReleasePolicyContext
 *  org.apereo.cas.util.LoggingUtils
 *  org.apereo.cas.util.function.FunctionUtils
 *  org.apereo.cas.util.scripting.ExecutableCompiledGroovyScript
 *  org.apereo.cas.util.spring.ApplicationContextProvider
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.validation.constraints.NotNull;
import lombok.Generated;
import org.apereo.cas.configuration.support.ExpressionLanguageCapable;
import org.apereo.cas.services.AbstractRegisteredServiceAttributeReleasePolicy;
import org.apereo.cas.services.RegisteredServiceAttributeReleasePolicyContext;
import org.apereo.cas.util.LoggingUtils;
import org.apereo.cas.util.function.FunctionUtils;
import org.apereo.cas.util.scripting.ExecutableCompiledGroovyScript;
import org.apereo.cas.util.spring.ApplicationContextProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonInclude(value=JsonInclude.Include.NON_DEFAULT)
public class GroovyScriptAttributeReleasePolicy
extends AbstractRegisteredServiceAttributeReleasePolicy {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(GroovyScriptAttributeReleasePolicy.class);
    private static final long serialVersionUID = 1703080077563402223L;
    @ExpressionLanguageCapable
    private String groovyScript;

    private Map<String, List<Object>> fetchAttributeValueFromExternalGroovyScript(String file, RegisteredServiceAttributeReleasePolicyContext context, Map<String, List<Object>> attributes) {
        return ApplicationContextProvider.getScriptResourceCacheManager().map(cacheMgr -> {
            ExecutableCompiledGroovyScript script = cacheMgr.resolveScriptableResource(file, new String[]{file});
            return (Map)FunctionUtils.doIf((script != null ? 1 : 0) != 0, () -> this.fetchAttributeValueFromScript(script, context, attributes), TreeMap::new).get();
        }).orElseThrow(() -> new RuntimeException("No groovy script cache manager is available to execute attribute mappings"));
    }

    protected Map<String, List<Object>> fetchAttributeValueFromScript(@NotNull ExecutableCompiledGroovyScript script, RegisteredServiceAttributeReleasePolicyContext context, Map<String, List<Object>> attributes) {
        Object[] args = new Object[]{attributes, LOGGER, context.getPrincipal(), context.getRegisteredService()};
        Map result = (Map)script.execute(args, Map.class, false);
        if (result != null) {
            LOGGER.debug("Attribute release policy returned attributes [{}] from script [{}]", (Object)result, (Object)this.groovyScript);
        } else {
            LOGGER.warn("Attribute release policy script [{}] returned null", (Object)this.groovyScript);
        }
        return result;
    }

    @Override
    public Map<String, List<Object>> getAttributesInternal(RegisteredServiceAttributeReleasePolicyContext context, Map<String, List<Object>> attributes) {
        try {
            LOGGER.debug("Invoking Groovy script with attributes=[{}], principal=[{}], service=[{}] and default logger", new Object[]{attributes, context.getPrincipal(), context.getRegisteredService()});
            return this.fetchAttributeValueFromExternalGroovyScript(this.groovyScript, context, attributes);
        }
        catch (Exception e) {
            LoggingUtils.error((Logger)LOGGER, (Throwable)e);
            LOGGER.warn("Groovy script [{}] does not exist or cannot be loaded", (Object)this.groovyScript);
            return new HashMap<String, List<Object>>(0);
        }
    }

    @Generated
    public String getGroovyScript() {
        return this.groovyScript;
    }

    @Generated
    public void setGroovyScript(String groovyScript) {
        this.groovyScript = groovyScript;
    }

    @Generated
    public GroovyScriptAttributeReleasePolicy() {
    }

    @Generated
    public GroovyScriptAttributeReleasePolicy(String groovyScript) {
        this.groovyScript = groovyScript;
    }

    @Override
    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof GroovyScriptAttributeReleasePolicy)) {
            return false;
        }
        GroovyScriptAttributeReleasePolicy other = (GroovyScriptAttributeReleasePolicy)o;
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
        return other instanceof GroovyScriptAttributeReleasePolicy;
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

