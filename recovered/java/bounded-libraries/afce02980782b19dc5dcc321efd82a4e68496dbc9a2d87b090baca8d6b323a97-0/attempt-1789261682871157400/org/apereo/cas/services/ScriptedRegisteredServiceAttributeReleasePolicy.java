/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  lombok.Generated
 *  org.apache.commons.lang3.ObjectUtils
 *  org.apereo.cas.services.RegisteredServiceAttributeReleasePolicyContext
 *  org.apereo.cas.util.CollectionUtils
 *  org.apereo.cas.util.scripting.ScriptingUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import lombok.Generated;
import org.apache.commons.lang3.ObjectUtils;
import org.apereo.cas.services.AbstractRegisteredServiceAttributeReleasePolicy;
import org.apereo.cas.services.RegisteredServiceAttributeReleasePolicyContext;
import org.apereo.cas.util.CollectionUtils;
import org.apereo.cas.util.scripting.ScriptingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonInclude(value=JsonInclude.Include.NON_DEFAULT)
@Deprecated(since="6.2.0")
public class ScriptedRegisteredServiceAttributeReleasePolicy
extends AbstractRegisteredServiceAttributeReleasePolicy {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(ScriptedRegisteredServiceAttributeReleasePolicy.class);
    private static final long serialVersionUID = -979532578142774128L;
    private String scriptFile;

    @Override
    public Map<String, List<Object>> getAttributesInternal(RegisteredServiceAttributeReleasePolicyContext context, Map<String, List<Object>> attributes) {
        Matcher matcherInline = ScriptingUtils.getMatcherForInlineGroovyScript((String)this.scriptFile);
        if (matcherInline.find()) {
            return ScriptedRegisteredServiceAttributeReleasePolicy.getAttributesFromInlineGroovyScript(attributes, matcherInline);
        }
        return this.getScriptedAttributesFromFile(attributes);
    }

    private Map<String, List<Object>> getScriptedAttributesFromFile(Map<String, List<Object>> attributes) {
        Object[] args = new Object[]{attributes, LOGGER};
        Map map = (Map)ScriptingUtils.executeScriptEngine((String)this.scriptFile, (Object[])args, Map.class);
        return (Map)ObjectUtils.defaultIfNull((Object)map, new HashMap(0));
    }

    private static Map<String, List<Object>> getAttributesFromInlineGroovyScript(Map<String, List<Object>> attributes, Matcher matcherInline) {
        String script = matcherInline.group(1).trim();
        Map args = CollectionUtils.wrap((String)"attributes", attributes, (String)"logger", (Object)LOGGER);
        Map map = (Map)ScriptingUtils.executeGroovyScriptEngine((String)script, (Map)args, Map.class);
        return (Map)ObjectUtils.defaultIfNull((Object)map, new HashMap(0));
    }

    @Generated
    public String getScriptFile() {
        return this.scriptFile;
    }

    @Generated
    public void setScriptFile(String scriptFile) {
        this.scriptFile = scriptFile;
    }

    @Generated
    public ScriptedRegisteredServiceAttributeReleasePolicy() {
    }

    @Generated
    public ScriptedRegisteredServiceAttributeReleasePolicy(String scriptFile) {
        this.scriptFile = scriptFile;
    }

    @Override
    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ScriptedRegisteredServiceAttributeReleasePolicy)) {
            return false;
        }
        ScriptedRegisteredServiceAttributeReleasePolicy other = (ScriptedRegisteredServiceAttributeReleasePolicy)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        String this$scriptFile = this.scriptFile;
        String other$scriptFile = other.scriptFile;
        return !(this$scriptFile == null ? other$scriptFile != null : !this$scriptFile.equals(other$scriptFile));
    }

    @Override
    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof ScriptedRegisteredServiceAttributeReleasePolicy;
    }

    @Override
    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        String $scriptFile = this.scriptFile;
        result = result * 59 + ($scriptFile == null ? 43 : $scriptFile.hashCode());
        return result;
    }
}

