/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.google.errorprone.annotations.CanIgnoreReturnValue
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.commons.lang3.tuple.Pair
 *  org.apereo.cas.authentication.attribute.AttributeDefinition
 *  org.apereo.cas.authentication.attribute.AttributeDefinitionStore
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.util.LoggingUtils
 *  org.apereo.cas.util.ResourceUtils
 *  org.apereo.cas.util.function.FunctionUtils
 *  org.apereo.cas.util.io.FileWatcherService
 *  org.apereo.cas.util.serialization.JacksonObjectMapperFactory
 *  org.hjson.JsonValue
 *  org.jooq.lambda.Unchecked
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.DisposableBean
 *  org.springframework.core.io.FileSystemResource
 *  org.springframework.core.io.Resource
 */
package org.apereo.cas.authentication.attribute;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Predicate;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apereo.cas.authentication.attribute.AttributeDefinition;
import org.apereo.cas.authentication.attribute.AttributeDefinitionStore;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.util.LoggingUtils;
import org.apereo.cas.util.ResourceUtils;
import org.apereo.cas.util.function.FunctionUtils;
import org.apereo.cas.util.io.FileWatcherService;
import org.apereo.cas.util.serialization.JacksonObjectMapperFactory;
import org.hjson.JsonValue;
import org.jooq.lambda.Unchecked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

public class DefaultAttributeDefinitionStore
implements AttributeDefinitionStore,
DisposableBean,
AutoCloseable {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultAttributeDefinitionStore.class);
    private static final ObjectMapper MAPPER = JacksonObjectMapperFactory.builder().defaultTypingEnabled(true).build().toObjectMapper();
    private final Map<String, AttributeDefinition> attributeDefinitions = new TreeMap<String, AttributeDefinition>();
    private FileWatcherService storeWatcherService;
    private String scope = "";

    public DefaultAttributeDefinitionStore(Resource resource) throws Exception {
        if (ResourceUtils.doesResourceExist((Resource)resource)) {
            this.loadAttributeDefinitionsFromInputStream(resource);
            if (ResourceUtils.isFile((Resource)resource)) {
                this.storeWatcherService = new FileWatcherService(resource.getFile(), Unchecked.consumer(file -> this.loadAttributeDefinitionsFromInputStream((Resource)new FileSystemResource(file))));
                this.storeWatcherService.start(this.getClass().getSimpleName());
            }
        }
    }

    public DefaultAttributeDefinitionStore(AttributeDefinition ... defns) {
        Arrays.stream(defns).forEach(this::registerAttributeDefinition);
    }

    @CanIgnoreReturnValue
    public AttributeDefinitionStore registerAttributeDefinition(AttributeDefinition defn) {
        return this.registerAttributeDefinition(defn.getKey(), defn);
    }

    @CanIgnoreReturnValue
    public AttributeDefinitionStore registerAttributeDefinition(String key, AttributeDefinition defn) {
        LOGGER.trace("Registering attribute definition [{}] by key [{}]", (Object)defn, (Object)key);
        if (StringUtils.isNotBlank((CharSequence)defn.getKey()) && !StringUtils.equalsIgnoreCase((CharSequence)defn.getKey(), (CharSequence)key)) {
            LOGGER.warn("Attribute definition contains a key property [{}] that differs from its registering key [{}]. This is likely due to misconfiguration of the attribute definition, and CAS will use the key property [{}] to register the attribute definition in the attribute store", new Object[]{defn.getKey(), key, defn.getKey()});
            this.attributeDefinitions.put(defn.getKey(), defn);
        } else {
            this.attributeDefinitions.put(key, defn);
        }
        return this;
    }

    @CanIgnoreReturnValue
    public AttributeDefinitionStore removeAttributeDefinition(String key) {
        LOGGER.debug("Removing attribute definition by key [{}]", (Object)key);
        if (this.attributeDefinitions.containsKey(key)) {
            AttributeDefinition defn = this.attributeDefinitions.remove(key);
            LOGGER.debug("Attribute definition [{}] has been removed from the definition store", (Object)defn);
        } else {
            LOGGER.debug("Attribute definition with the registered key [{}] was not found and the store was not altered", (Object)key);
        }
        return this;
    }

    public Optional<AttributeDefinition> locateAttributeDefinition(String key) {
        LOGGER.trace("Locating attribute definition for [{}]", (Object)key);
        return Optional.ofNullable(this.attributeDefinitions.get(key));
    }

    public <T extends AttributeDefinition> Optional<T> locateAttributeDefinition(String key, Class<T> clazz) {
        LOGGER.trace("Locating attribute definition for [{}]", (Object)key);
        AttributeDefinition attributeDefinition = this.attributeDefinitions.get(key);
        if (attributeDefinition != null && clazz.isAssignableFrom(attributeDefinition.getClass())) {
            return Optional.of(attributeDefinition);
        }
        return Optional.empty();
    }

    public <T extends AttributeDefinition> Optional<T> locateAttributeDefinition(Predicate<AttributeDefinition> predicate) {
        return this.attributeDefinitions.values().stream().filter(predicate).map(defn -> defn).findFirst();
    }

    public Collection<AttributeDefinition> getAttributeDefinitions() {
        return this.attributeDefinitions.values();
    }

    public Optional<Pair<AttributeDefinition, List<Object>>> resolveAttributeValues(String key, List<Object> attributeValues, RegisteredService registeredService, Map<String, List<Object>> attributes) {
        Optional<AttributeDefinition> result = this.locateAttributeDefinition(key);
        if (result.isEmpty()) {
            return Optional.empty();
        }
        AttributeDefinition definition = result.get();
        List currentValues = definition.resolveAttributeValues(attributeValues, this.scope, registeredService, attributes);
        return Optional.of(Pair.of((Object)definition, (Object)currentValues));
    }

    public boolean isEmpty() {
        return this.attributeDefinitions.isEmpty();
    }

    @CanIgnoreReturnValue
    public AttributeDefinitionStore store(Resource resource) {
        return (AttributeDefinitionStore)FunctionUtils.doUnchecked(() -> {
            String json = MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(this.attributeDefinitions);
            LOGGER.trace("Storing attribute definitions as [{}] to [{}]", (Object)json, (Object)resource);
            try (BufferedWriter writer = Files.newBufferedWriter(resource.getFile().toPath(), StandardCharsets.UTF_8, new OpenOption[0]);){
                writer.write(json);
                writer.flush();
            }
            return this;
        });
    }

    @Override
    public void close() {
        if (this.storeWatcherService != null) {
            this.storeWatcherService.close();
        }
    }

    public void destroy() {
        this.close();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void loadAttributeDefinitionsFromInputStream(Resource resource) {
        try {
            LOGGER.trace("Loading attribute definitions from [{}]", (Object)resource);
            String json = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            LOGGER.trace("Loaded attribute definitions [{}] from [{}]", (Object)json, (Object)resource);
            Map map = (Map)MAPPER.readValue(JsonValue.readHjson((String)json).toString(), (TypeReference)new TypeReference<Map<String, AttributeDefinition>>(){});
            map.forEach(this::registerAttributeDefinition);
        }
        catch (Exception e) {
            LoggingUtils.warn((Logger)LOGGER, (Throwable)e);
        }
        finally {
            LOGGER.debug("Loaded [{}] attribute definition(s).", (Object)this.attributeDefinitions.size());
        }
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DefaultAttributeDefinitionStore)) {
            return false;
        }
        DefaultAttributeDefinitionStore other = (DefaultAttributeDefinitionStore)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Map<String, AttributeDefinition> this$attributeDefinitions = this.attributeDefinitions;
        Map<String, AttributeDefinition> other$attributeDefinitions = other.attributeDefinitions;
        return !(this$attributeDefinitions == null ? other$attributeDefinitions != null : !((Object)this$attributeDefinitions).equals(other$attributeDefinitions));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof DefaultAttributeDefinitionStore;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Map<String, AttributeDefinition> $attributeDefinitions = this.attributeDefinitions;
        result = result * 59 + ($attributeDefinitions == null ? 43 : ((Object)$attributeDefinitions).hashCode());
        return result;
    }

    @Generated
    public String toString() {
        return "DefaultAttributeDefinitionStore(attributeDefinitions=" + this.attributeDefinitions + ")";
    }

    @Generated
    public void setScope(String scope) {
        this.scope = scope;
    }

    @Generated
    public String getScope() {
        return this.scope;
    }
}

