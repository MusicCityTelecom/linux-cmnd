/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.commons.lang3.tuple.Pair
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.core.io.Resource
 *  org.springframework.util.StringUtils
 */
package org.apereo.cas.authentication.attribute;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apereo.cas.authentication.attribute.AttributeDefinition;
import org.apereo.cas.services.RegisteredService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

public interface AttributeDefinitionStore {
    public static final String BEAN_NAME = "attributeDefinitionStore";
    public static final Logger LOGGER = LoggerFactory.getLogger(AttributeDefinitionStore.class);

    private static List<Object> determineValuesForAttributeDefinition(Map<String, List<Object>> attributes, String entry, AttributeDefinition definition) {
        String attributeKey = (String)StringUtils.defaultIfBlank((CharSequence)definition.getAttribute(), (CharSequence)entry);
        if (attributes.containsKey(attributeKey)) {
            return attributes.get(attributeKey);
        }
        return new ArrayList<Object>(0);
    }

    public AttributeDefinitionStore registerAttributeDefinition(AttributeDefinition var1);

    public AttributeDefinitionStore registerAttributeDefinition(String var1, AttributeDefinition var2);

    public AttributeDefinitionStore removeAttributeDefinition(String var1);

    public Optional<AttributeDefinition> locateAttributeDefinition(String var1);

    public <T extends AttributeDefinition> Optional<T> locateAttributeDefinition(String var1, Class<T> var2);

    public <T extends AttributeDefinition> Optional<T> locateAttributeDefinition(Predicate<AttributeDefinition> var1);

    public Collection<AttributeDefinition> getAttributeDefinitions();

    public Optional<Pair<AttributeDefinition, List<Object>>> resolveAttributeValues(String var1, List<Object> var2, RegisteredService var3, Map<String, List<Object>> var4);

    default public Map<String, List<Object>> resolveAttributeValues(Collection<String> attributeDefinitions, Map<String, List<Object>> availableAttributes, RegisteredService registeredService) {
        LinkedHashMap<String, List<Object>> finalAttributes = new LinkedHashMap<String, List<Object>>(attributeDefinitions.size());
        attributeDefinitions.forEach(entry -> this.locateAttributeDefinition((String)entry).ifPresentOrElse(definition -> {
            List<Object> attributeValues = AttributeDefinitionStore.determineValuesForAttributeDefinition(availableAttributes, entry, definition);
            LOGGER.trace("Resolving attribute [{}] from attribute definition store with values [{}]", entry, attributeValues);
            Optional<Pair<AttributeDefinition, List<Object>>> result = this.resolveAttributeValues((String)entry, attributeValues, registeredService, availableAttributes);
            if (result.isPresent()) {
                List resolvedValues = (List)result.get().getValue();
                if (!resolvedValues.isEmpty()) {
                    LOGGER.trace("Resolving attribute [{}] based on attribute definition [{}]", entry, definition);
                    Set attributeKeys = org.springframework.util.StringUtils.commaDelimitedListToSet((String)((String)StringUtils.defaultIfBlank((CharSequence)definition.getName(), (CharSequence)entry)));
                    attributeKeys.forEach(key -> {
                        LOGGER.trace("Determined attribute name to be [{}] with values [{}]", key, (Object)resolvedValues);
                        finalAttributes.put((String)key, resolvedValues);
                    });
                } else {
                    LOGGER.warn("Unable to produce or determine attributes values for attribute definition [{}]", definition);
                }
            }
        }, () -> {
            LOGGER.trace("Using already-resolved attribute name/value, as no attribute definition was found for [{}]", entry);
            finalAttributes.put((String)entry, (List)availableAttributes.get(entry));
        }));
        LOGGER.trace("Final collection of attributes resolved from attribute definition store is [{}]", finalAttributes);
        return finalAttributes;
    }

    public boolean isEmpty();

    public AttributeDefinitionStore store(Resource var1);
}

