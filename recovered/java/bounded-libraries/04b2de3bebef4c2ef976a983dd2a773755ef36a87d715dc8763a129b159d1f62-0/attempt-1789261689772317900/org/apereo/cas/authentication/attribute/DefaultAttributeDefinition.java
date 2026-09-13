/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.commons.lang3.builder.CompareToBuilder
 *  org.apereo.cas.authentication.attribute.AttributeDefinition
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.services.RegisteredServicePublicKey
 *  org.apereo.cas.util.CollectionUtils
 *  org.apereo.cas.util.EncodingUtils
 *  org.apereo.cas.util.scripting.ExecutableCompiledGroovyScript
 *  org.apereo.cas.util.scripting.ScriptResourceCacheManager
 *  org.apereo.cas.util.scripting.ScriptingUtils
 *  org.apereo.cas.util.spring.ApplicationContextProvider
 *  org.apereo.services.persondir.util.CaseCanonicalizationMode
 *  org.jooq.lambda.Unchecked
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.authentication.attribute;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.stream.Collectors;
import javax.crypto.Cipher;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apereo.cas.authentication.attribute.AttributeDefinition;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.RegisteredServicePublicKey;
import org.apereo.cas.util.CollectionUtils;
import org.apereo.cas.util.EncodingUtils;
import org.apereo.cas.util.scripting.ExecutableCompiledGroovyScript;
import org.apereo.cas.util.scripting.ScriptResourceCacheManager;
import org.apereo.cas.util.scripting.ScriptingUtils;
import org.apereo.cas.util.spring.ApplicationContextProvider;
import org.apereo.services.persondir.util.CaseCanonicalizationMode;
import org.jooq.lambda.Unchecked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public class DefaultAttributeDefinition
implements AttributeDefinition {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultAttributeDefinition.class);
    private static final long serialVersionUID = 6898745248727445565L;
    private String key;
    private String name;
    private boolean scoped;
    private boolean encrypted;
    private String attribute;
    private String patternFormat;
    private String script;
    private String canonicalizationMode;

    public int compareTo(AttributeDefinition o) {
        return new CompareToBuilder().append((Object)this.getKey(), (Object)o.getKey()).build();
    }

    @JsonIgnore
    public List<Object> resolveAttributeValues(List<Object> attributeValues, String scope, RegisteredService registeredService, Map<String, List<Object>> attributes) {
        List<Object> currentValues = new ArrayList<Object>(attributeValues);
        if (StringUtils.isNotBlank((CharSequence)this.getScript())) {
            currentValues = this.getScriptedAttributeValue(this.key, currentValues, registeredService, attributes);
        }
        if (this.isScoped()) {
            currentValues = DefaultAttributeDefinition.formatValuesWithScope(scope, currentValues);
        }
        if (StringUtils.isNotBlank((CharSequence)this.getPatternFormat())) {
            currentValues = this.formatValuesWithPattern(currentValues);
        }
        if (this.isEncrypted()) {
            currentValues = DefaultAttributeDefinition.encryptValues(currentValues, registeredService);
        }
        if (StringUtils.isNotBlank((CharSequence)this.canonicalizationMode)) {
            CaseCanonicalizationMode mode = CaseCanonicalizationMode.valueOf((String)this.canonicalizationMode.toUpperCase());
            currentValues = currentValues.stream().map(value -> mode.canonicalize(value.toString())).collect(Collectors.toList());
        }
        LOGGER.trace("Resolved values [{}] for attribute definition [{}]", currentValues, (Object)this);
        return currentValues;
    }

    private static List<Object> formatValuesWithScope(String scope, List<Object> currentValues) {
        return currentValues.stream().map(v -> String.format("%s@%s", v, scope)).collect(Collectors.toCollection(ArrayList::new));
    }

    private static List<Object> encryptValues(List<Object> currentValues, RegisteredService registeredService) {
        RegisteredServicePublicKey publicKey = registeredService.getPublicKey();
        if (publicKey == null) {
            LOGGER.error("No public key is defined for service [{}]. No attributes will be released", (Object)registeredService);
            return new ArrayList<Object>(0);
        }
        Cipher cipher = publicKey.toCipher();
        if (cipher == null) {
            LOGGER.error("Unable to initialize cipher given the public key algorithm [{}]", (Object)publicKey.getAlgorithm());
            return new ArrayList<Object>(0);
        }
        return currentValues.stream().map(Unchecked.function(value -> {
            LOGGER.trace("Encrypting attribute value [{}]", value);
            String result = EncodingUtils.encodeBase64((byte[])cipher.doFinal(value.toString().getBytes(StandardCharsets.UTF_8)));
            LOGGER.trace("Encrypted attribute value [{}]", (Object)result);
            return result;
        })).collect(Collectors.toCollection(ArrayList::new));
    }

    private List<Object> formatValuesWithPattern(List<Object> currentValues) {
        return currentValues.stream().map(v -> MessageFormat.format(this.getPatternFormat(), v)).collect(Collectors.toCollection(ArrayList::new));
    }

    @JsonIgnore
    private List<Object> getScriptedAttributeValue(String attributeKey, List<Object> currentValues, RegisteredService registeredService, Map<String, List<Object>> attributes) {
        LOGGER.trace("Locating attribute value via script for definition [{}]", (Object)this);
        Matcher matcherInline = ScriptingUtils.getMatcherForInlineGroovyScript((String)this.getScript());
        if (matcherInline.find()) {
            return DefaultAttributeDefinition.fetchAttributeValueAsInlineGroovyScript(attributeKey, currentValues, matcherInline.group(1), registeredService, attributes);
        }
        Matcher matcherFile = ScriptingUtils.getMatcherForExternalGroovyScript((String)this.getScript());
        if (matcherFile.find()) {
            return DefaultAttributeDefinition.fetchAttributeValueFromExternalGroovyScript(attributeKey, currentValues, matcherFile.group(), registeredService, attributes);
        }
        return new ArrayList<Object>(0);
    }

    private static List<Object> fetchAttributeValueFromExternalGroovyScript(String attributeName, List<Object> currentValues, String file, RegisteredService registeredService, Map<String, List<Object>> attributes) {
        Optional result = ApplicationContextProvider.getScriptResourceCacheManager();
        if (result.isPresent()) {
            ScriptResourceCacheManager cacheMgr = (ScriptResourceCacheManager)result.get();
            ExecutableCompiledGroovyScript script = cacheMgr.resolveScriptableResource(file, new String[]{attributeName, file});
            if (script != null) {
                return DefaultAttributeDefinition.fetchAttributeValueFromScript(script, attributeName, currentValues, registeredService, attributes);
            }
        }
        LOGGER.warn("No groovy script cache manager is available to execute attribute mappings");
        return new ArrayList<Object>(0);
    }

    private static List<Object> fetchAttributeValueAsInlineGroovyScript(String attributeName, List<Object> currentValues, String inlineGroovy, RegisteredService registeredService, Map<String, List<Object>> attributes) {
        Optional result = ApplicationContextProvider.getScriptResourceCacheManager();
        if (result.isPresent()) {
            ScriptResourceCacheManager cacheMgr = (ScriptResourceCacheManager)result.get();
            ExecutableCompiledGroovyScript script = cacheMgr.resolveScriptableResource(inlineGroovy, new String[]{attributeName, inlineGroovy});
            return DefaultAttributeDefinition.fetchAttributeValueFromScript(script, attributeName, currentValues, registeredService, attributes);
        }
        LOGGER.warn("No groovy script cache manager is available to execute attribute mappings");
        return new ArrayList<Object>(0);
    }

    private static List<Object> fetchAttributeValueFromScript(ExecutableCompiledGroovyScript scriptToExec, String attributeKey, List<Object> currentValues, RegisteredService registeredService, Map<String, List<Object>> attributes) {
        Map args = CollectionUtils.wrap((String)"attributeName", (Object)Objects.requireNonNull(attributeKey), (String)"attributeValues", currentValues, (String)"logger", (Object)LOGGER, (String)"registeredService", (Object)registeredService, (String)"attributes", attributes);
        scriptToExec.setBinding(args);
        return (List)scriptToExec.execute(args.values().toArray(), List.class);
    }

    @Generated
    protected DefaultAttributeDefinition(DefaultAttributeDefinitionBuilder<?, ?> b) {
        this.key = b.key;
        this.name = b.name;
        this.scoped = b.scoped;
        this.encrypted = b.encrypted;
        this.attribute = b.attribute;
        this.patternFormat = b.patternFormat;
        this.script = b.script;
        this.canonicalizationMode = b.canonicalizationMode;
    }

    @Generated
    public static DefaultAttributeDefinitionBuilder<?, ?> builder() {
        return new DefaultAttributeDefinitionBuilderImpl();
    }

    @Generated
    public String toString() {
        return "DefaultAttributeDefinition(key=" + this.key + ", name=" + this.name + ", scoped=" + this.scoped + ", encrypted=" + this.encrypted + ", attribute=" + this.attribute + ", patternFormat=" + this.patternFormat + ", script=" + this.script + ", canonicalizationMode=" + this.canonicalizationMode + ")";
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DefaultAttributeDefinition)) {
            return false;
        }
        DefaultAttributeDefinition other = (DefaultAttributeDefinition)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$key = this.key;
        String other$key = other.key;
        return !(this$key == null ? other$key != null : !this$key.equals(other$key));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof DefaultAttributeDefinition;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $key = this.key;
        result = result * 59 + ($key == null ? 43 : $key.hashCode());
        return result;
    }

    @Generated
    public String getKey() {
        return this.key;
    }

    @Generated
    public String getName() {
        return this.name;
    }

    @Generated
    public boolean isScoped() {
        return this.scoped;
    }

    @Generated
    public boolean isEncrypted() {
        return this.encrypted;
    }

    @Generated
    public String getAttribute() {
        return this.attribute;
    }

    @Generated
    public String getPatternFormat() {
        return this.patternFormat;
    }

    @Generated
    public String getScript() {
        return this.script;
    }

    @Generated
    public String getCanonicalizationMode() {
        return this.canonicalizationMode;
    }

    @Generated
    public void setKey(String key) {
        this.key = key;
    }

    @Generated
    public void setName(String name) {
        this.name = name;
    }

    @Generated
    public void setScoped(boolean scoped) {
        this.scoped = scoped;
    }

    @Generated
    public void setEncrypted(boolean encrypted) {
        this.encrypted = encrypted;
    }

    @Generated
    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    @Generated
    public void setPatternFormat(String patternFormat) {
        this.patternFormat = patternFormat;
    }

    @Generated
    public void setScript(String script) {
        this.script = script;
    }

    @Generated
    public void setCanonicalizationMode(String canonicalizationMode) {
        this.canonicalizationMode = canonicalizationMode;
    }

    @Generated
    public DefaultAttributeDefinition(String key, String name, boolean scoped, boolean encrypted, String attribute, String patternFormat, String script, String canonicalizationMode) {
        this.key = key;
        this.name = name;
        this.scoped = scoped;
        this.encrypted = encrypted;
        this.attribute = attribute;
        this.patternFormat = patternFormat;
        this.script = script;
        this.canonicalizationMode = canonicalizationMode;
    }

    @Generated
    public DefaultAttributeDefinition() {
    }

    @Generated
    private static final class DefaultAttributeDefinitionBuilderImpl
    extends DefaultAttributeDefinitionBuilder<DefaultAttributeDefinition, DefaultAttributeDefinitionBuilderImpl> {
        @Generated
        private DefaultAttributeDefinitionBuilderImpl() {
        }

        @Override
        @Generated
        protected DefaultAttributeDefinitionBuilderImpl self() {
            return this;
        }

        @Override
        @Generated
        public DefaultAttributeDefinition build() {
            return new DefaultAttributeDefinition(this);
        }
    }

    @Generated
    public static abstract class DefaultAttributeDefinitionBuilder<C extends DefaultAttributeDefinition, B extends DefaultAttributeDefinitionBuilder<C, B>> {
        @Generated
        private String key;
        @Generated
        private String name;
        @Generated
        private boolean scoped;
        @Generated
        private boolean encrypted;
        @Generated
        private String attribute;
        @Generated
        private String patternFormat;
        @Generated
        private String script;
        @Generated
        private String canonicalizationMode;

        @Generated
        protected abstract B self();

        @Generated
        public abstract C build();

        @Generated
        public B key(String key) {
            this.key = key;
            return this.self();
        }

        @Generated
        public B name(String name) {
            this.name = name;
            return this.self();
        }

        @Generated
        public B scoped(boolean scoped) {
            this.scoped = scoped;
            return this.self();
        }

        @Generated
        public B encrypted(boolean encrypted) {
            this.encrypted = encrypted;
            return this.self();
        }

        @Generated
        public B attribute(String attribute) {
            this.attribute = attribute;
            return this.self();
        }

        @Generated
        public B patternFormat(String patternFormat) {
            this.patternFormat = patternFormat;
            return this.self();
        }

        @Generated
        public B script(String script) {
            this.script = script;
            return this.self();
        }

        @Generated
        public B canonicalizationMode(String canonicalizationMode) {
            this.canonicalizationMode = canonicalizationMode;
            return this.self();
        }

        @Generated
        public String toString() {
            return "DefaultAttributeDefinition.DefaultAttributeDefinitionBuilder(key=" + this.key + ", name=" + this.name + ", scoped=" + this.scoped + ", encrypted=" + this.encrypted + ", attribute=" + this.attribute + ", patternFormat=" + this.patternFormat + ", script=" + this.script + ", canonicalizationMode=" + this.canonicalizationMode + ")";
        }
    }
}

