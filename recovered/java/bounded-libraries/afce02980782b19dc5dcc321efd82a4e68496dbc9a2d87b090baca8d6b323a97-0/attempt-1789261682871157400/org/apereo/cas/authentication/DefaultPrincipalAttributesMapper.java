/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.validation.constraints.NotNull
 *  lombok.Generated
 *  org.apereo.cas.util.CollectionUtils
 *  org.apereo.cas.util.function.FunctionUtils
 *  org.apereo.cas.util.scripting.ExecutableCompiledGroovyScript
 *  org.apereo.cas.util.scripting.ScriptingUtils
 *  org.apereo.cas.util.spring.ApplicationContextProvider
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.authentication;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import javax.validation.constraints.NotNull;
import lombok.Generated;
import org.apereo.cas.authentication.AttributeMappingRequest;
import org.apereo.cas.authentication.PrincipalAttributesMapper;
import org.apereo.cas.util.CollectionUtils;
import org.apereo.cas.util.function.FunctionUtils;
import org.apereo.cas.util.scripting.ExecutableCompiledGroovyScript;
import org.apereo.cas.util.scripting.ScriptingUtils;
import org.apereo.cas.util.spring.ApplicationContextProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultPrincipalAttributesMapper
implements PrincipalAttributesMapper {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultPrincipalAttributesMapper.class);

    @Override
    public Map<String, List<Object>> map(AttributeMappingRequest request) {
        Matcher matcherInline = ScriptingUtils.getMatcherForInlineGroovyScript((String)request.getMappedAttributeName());
        Matcher matcherFile = ScriptingUtils.getMatcherForExternalGroovyScript((String)request.getMappedAttributeName());
        if (matcherInline.find()) {
            String inlineGroovy = matcherInline.group(1);
            return DefaultPrincipalAttributesMapper.fetchAttributeValueAsInlineGroovyScript(request.getAttributeName(), request.getResolvedAttributes(), inlineGroovy);
        }
        if (matcherFile.find()) {
            String file = matcherFile.group();
            return DefaultPrincipalAttributesMapper.fetchAttributeValueFromExternalGroovyScript(request.getAttributeName(), request.getResolvedAttributes(), file);
        }
        return DefaultPrincipalAttributesMapper.mapSimpleSingleAttributeDefinition(request);
    }

    private static Map<String, List<Object>> fetchAttributeValueFromExternalGroovyScript(String attributeName, Map<String, List<Object>> resolvedAttributes, String file) {
        return ApplicationContextProvider.getScriptResourceCacheManager().map((? super T cacheMgr) -> {
            ExecutableCompiledGroovyScript script = cacheMgr.resolveScriptableResource(file, new String[]{attributeName, file});
            return (Map)FunctionUtils.doIf((script != null ? 1 : 0) != 0, () -> DefaultPrincipalAttributesMapper.fetchAttributeValueFromScript(script, attributeName, resolvedAttributes), TreeMap::new).get();
        }).orElseThrow(() -> new RuntimeException("No groovy script cache manager is available to execute attribute mappings"));
    }

    private static Map<String, List<Object>> fetchAttributeValueAsInlineGroovyScript(String attributeName, Map<String, List<Object>> resolvedAttributes, String inlineGroovy) {
        return ApplicationContextProvider.getScriptResourceCacheManager().map((? super T cacheMgr) -> {
            ExecutableCompiledGroovyScript script = cacheMgr.resolveScriptableResource(inlineGroovy, new String[]{attributeName, inlineGroovy});
            return DefaultPrincipalAttributesMapper.fetchAttributeValueFromScript(script, attributeName, resolvedAttributes);
        }).orElseThrow(() -> new RuntimeException("No groovy script cache manager is available to execute attribute mappings"));
    }

    private static Map<String, List<Object>> mapSimpleSingleAttributeDefinition(AttributeMappingRequest request) {
        TreeMap<String, List<Object>> attributesToRelease = new TreeMap<String, List<Object>>(String.CASE_INSENSITIVE_ORDER);
        if (request.getAttributeValue() != null && !request.getAttributeValue().isEmpty()) {
            LOGGER.debug("Found attribute [{}] in the list of allowed attributes, mapped to the name [{}]", (Object)request.getAttributeName(), (Object)request.getMappedAttributeName());
            ArrayList values = (ArrayList)CollectionUtils.toCollection(request.getAttributeValue(), ArrayList.class);
            attributesToRelease.put(request.getMappedAttributeName(), values);
        } else if (request.getResolvedAttributes().containsKey(request.getMappedAttributeName())) {
            List<Object> mappedValue = request.getResolvedAttributes().get(request.getMappedAttributeName());
            LOGGER.debug("Reusing existing already-remapped attribute [{}] with value [{}]", (Object)request.getMappedAttributeName(), mappedValue);
            attributesToRelease.put(request.getMappedAttributeName(), mappedValue);
        } else {
            LOGGER.warn("Could not find value for mapped attribute [{}] that is based off of [{}] in the allowed attributes list. Ensure the original attribute [{}] is retrieved and contains at least a single value. Attribute [{}] will and can not be released without the presence of a value.", new Object[]{request.getMappedAttributeName(), request.getAttributeName(), request.getAttributeName(), request.getMappedAttributeName()});
        }
        return attributesToRelease;
    }

    private static Map<String, List<Object>> fetchAttributeValueFromScript(@NotNull ExecutableCompiledGroovyScript script, String attributeName, Map<String, List<Object>> resolvedAttributes) {
        TreeMap<String, List<Object>> attributesToRelease = new TreeMap<String, List<Object>>(String.CASE_INSENSITIVE_ORDER);
        Map args = CollectionUtils.wrap((String)"attributes", resolvedAttributes, (String)"logger", (Object)LOGGER);
        script.setBinding(args);
        Object result = script.execute(args.values().toArray(), Object.class, false);
        if (result != null) {
            LOGGER.debug("Mapped attribute [{}] to [{}] from script", (Object)attributeName, result);
            attributesToRelease.put(attributeName, CollectionUtils.wrapList((Object[])new Object[]{result}));
        } else {
            LOGGER.warn("Groovy-scripted attribute returned no value for [{}]", (Object)attributeName);
        }
        return attributesToRelease;
    }
}

