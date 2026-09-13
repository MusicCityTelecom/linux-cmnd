/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  javax.persistence.PostLoad
 *  javax.persistence.Transient
 *  lombok.Generated
 *  org.apereo.cas.services.RegisteredServiceAttributeFilter
 *  org.apereo.cas.util.CollectionUtils
 *  org.apereo.cas.util.ResourceUtils
 *  org.apereo.cas.util.function.FunctionUtils
 *  org.apereo.cas.util.scripting.ExecutableCompiledGroovyScript
 *  org.apereo.cas.util.scripting.GroovyShellScript
 *  org.apereo.cas.util.scripting.ScriptingUtils
 *  org.apereo.cas.util.scripting.WatchableGroovyScriptResource
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.core.io.AbstractResource
 *  org.springframework.core.io.Resource
 *  org.springframework.data.annotation.Transient
 */
package org.apereo.cas.services.support;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import javax.persistence.PostLoad;
import javax.persistence.Transient;
import lombok.Generated;
import org.apereo.cas.services.RegisteredServiceAttributeFilter;
import org.apereo.cas.util.CollectionUtils;
import org.apereo.cas.util.ResourceUtils;
import org.apereo.cas.util.function.FunctionUtils;
import org.apereo.cas.util.scripting.ExecutableCompiledGroovyScript;
import org.apereo.cas.util.scripting.GroovyShellScript;
import org.apereo.cas.util.scripting.ScriptingUtils;
import org.apereo.cas.util.scripting.WatchableGroovyScriptResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.AbstractResource;
import org.springframework.core.io.Resource;

@JsonInclude(value=JsonInclude.Include.NON_DEFAULT)
public class RegisteredServiceScriptedAttributeFilter
implements RegisteredServiceAttributeFilter {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(RegisteredServiceScriptedAttributeFilter.class);
    private static final long serialVersionUID = 122972056984610198L;
    private int order;
    private String script;
    @JsonIgnore
    @Transient
    @org.springframework.data.annotation.Transient
    private transient ExecutableCompiledGroovyScript executableScript;

    @JsonCreator
    public RegisteredServiceScriptedAttributeFilter(@JsonProperty(value="order") int order, @JsonProperty(value="script") String script) {
        this.order = order;
        this.script = script;
    }

    @PostLoad
    private void initializeWatchableScriptIfNeeded() {
        if (this.executableScript == null) {
            Matcher matcherInline = ScriptingUtils.getMatcherForInlineGroovyScript((String)this.script);
            Matcher matcherFile = ScriptingUtils.getMatcherForExternalGroovyScript((String)this.script);
            if (matcherFile.find()) {
                AbstractResource resource = (AbstractResource)FunctionUtils.doUnchecked(() -> ResourceUtils.getRawResourceFrom((String)matcherFile.group(2)));
                this.executableScript = new WatchableGroovyScriptResource((Resource)resource);
            } else if (matcherInline.find()) {
                this.executableScript = new GroovyShellScript(matcherInline.group(1));
            }
        }
    }

    private Map<String, List<Object>> getGroovyAttributeValue(Map<String, List<Object>> resolvedAttributes) {
        Map args = CollectionUtils.wrap((String)"attributes", resolvedAttributes, (String)"logger", (Object)LOGGER);
        this.executableScript.setBinding(args);
        return (Map)this.executableScript.execute(args.values().toArray(), Map.class);
    }

    public Map<String, List<Object>> filter(Map<String, List<Object>> givenAttributes) {
        this.initializeWatchableScriptIfNeeded();
        return this.getGroovyAttributeValue(givenAttributes);
    }

    @Generated
    public String toString() {
        return "RegisteredServiceScriptedAttributeFilter(order=" + this.order + ", script=" + this.script + ", executableScript=" + this.executableScript + ")";
    }

    @Generated
    public int getOrder() {
        return this.order;
    }

    @Generated
    public String getScript() {
        return this.script;
    }

    @Generated
    public ExecutableCompiledGroovyScript getExecutableScript() {
        return this.executableScript;
    }

    @Generated
    public void setOrder(int order) {
        this.order = order;
    }

    @Generated
    public void setScript(String script) {
        this.script = script;
    }

    @JsonIgnore
    @Generated
    public void setExecutableScript(ExecutableCompiledGroovyScript executableScript) {
        this.executableScript = executableScript;
    }

    @Generated
    public RegisteredServiceScriptedAttributeFilter() {
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RegisteredServiceScriptedAttributeFilter)) {
            return false;
        }
        RegisteredServiceScriptedAttributeFilter other = (RegisteredServiceScriptedAttributeFilter)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.order != other.order) {
            return false;
        }
        String this$script = this.script;
        String other$script = other.script;
        return !(this$script == null ? other$script != null : !this$script.equals(other$script));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof RegisteredServiceScriptedAttributeFilter;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.order;
        String $script = this.script;
        result = result * 59 + ($script == null ? 43 : $script.hashCode());
        return result;
    }
}

