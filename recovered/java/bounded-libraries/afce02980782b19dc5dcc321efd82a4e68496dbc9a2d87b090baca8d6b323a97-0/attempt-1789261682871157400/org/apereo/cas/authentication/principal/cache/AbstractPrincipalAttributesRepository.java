/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  javax.persistence.Transient
 *  lombok.Generated
 *  org.apache.commons.lang3.ObjectUtils
 *  org.apereo.cas.authentication.attribute.PrincipalAttributeRepositoryFetcher
 *  org.apereo.cas.authentication.principal.Principal
 *  org.apereo.cas.authentication.principal.RegisteredServicePrincipalAttributesRepository
 *  org.apereo.cas.configuration.model.core.authentication.PrincipalAttributesCoreProperties$MergingStrategyTypes
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.util.CollectionUtils
 *  org.apereo.cas.util.spring.ApplicationContextProvider
 *  org.apereo.services.persondir.IPersonAttributeDao
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.data.annotation.Transient
 */
package org.apereo.cas.authentication.principal.cache;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import javax.persistence.Transient;
import lombok.Generated;
import org.apache.commons.lang3.ObjectUtils;
import org.apereo.cas.authentication.attribute.PrincipalAttributeRepositoryFetcher;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.authentication.principal.RegisteredServicePrincipalAttributesRepository;
import org.apereo.cas.configuration.model.core.authentication.PrincipalAttributesCoreProperties;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.util.CollectionUtils;
import org.apereo.cas.util.spring.ApplicationContextProvider;
import org.apereo.services.persondir.IPersonAttributeDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonInclude(value=JsonInclude.Include.NON_DEFAULT)
public abstract class AbstractPrincipalAttributesRepository
implements RegisteredServicePrincipalAttributesRepository,
AutoCloseable {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractPrincipalAttributesRepository.class);
    private static final long serialVersionUID = 6350245643948535906L;
    @JsonIgnore
    @Transient
    @org.springframework.data.annotation.Transient
    private final transient Object lock = new Object();
    private PrincipalAttributesCoreProperties.MergingStrategyTypes mergingStrategy = PrincipalAttributesCoreProperties.MergingStrategyTypes.MULTIVALUED;
    private Set<String> attributeRepositoryIds = new LinkedHashSet<String>(0);
    private boolean ignoreResolvedAttributes;

    @JsonIgnore
    protected static IPersonAttributeDao getAttributeRepository() {
        Optional repositories = ApplicationContextProvider.getAttributeRepository();
        return repositories.orElse(null);
    }

    protected static Map<String, List<Object>> convertPrincipalAttributesToPersonAttributes(Map<String, ?> attributes) {
        TreeMap<String, List<Object>> convertedAttributes = new TreeMap<String, List<Object>>(String.CASE_INSENSITIVE_ORDER);
        LinkedHashMap principalAttributes = new LinkedHashMap(attributes);
        LOGGER.trace("Principal attributes to convert to person attributes are [{}]", principalAttributes);
        principalAttributes.forEach((key, values) -> {
            if (values instanceof Collection) {
                LinkedHashSet uniqueValues = new LinkedHashSet((Collection)Collection.class.cast(values));
                ArrayList listedValues = new ArrayList(uniqueValues);
                convertedAttributes.put((String)key, listedValues);
            } else {
                convertedAttributes.put((String)key, CollectionUtils.wrap((Object)values));
            }
        });
        LOGGER.trace("Converted principal attributes, now as person attributes are [{}]", convertedAttributes);
        return convertedAttributes;
    }

    protected static Map<String, List<Object>> convertPersonAttributesToPrincipalAttributes(Map<String, List<Object>> attributes) {
        return attributes.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public void close() {
    }

    protected Map<String, List<Object>> convertAttributesToPrincipalAttributesAndCache(Principal principal, Map<String, List<Object>> sourceAttributes, RegisteredService registeredService) {
        Map<String, List<Object>> finalAttributes = AbstractPrincipalAttributesRepository.convertPersonAttributesToPrincipalAttributes(sourceAttributes);
        this.update(principal.getId(), finalAttributes, registeredService);
        LOGGER.trace("Final principal attributes after caching, if any, are [{}]", finalAttributes);
        return finalAttributes;
    }

    protected PrincipalAttributesCoreProperties.MergingStrategyTypes determineMergingStrategy() {
        return (PrincipalAttributesCoreProperties.MergingStrategyTypes)ObjectUtils.defaultIfNull((Object)this.getMergingStrategy(), (Object)PrincipalAttributesCoreProperties.MergingStrategyTypes.MULTIVALUED);
    }

    @JsonIgnore
    protected boolean areAttributeRepositoryIdsDefined() {
        return this.attributeRepositoryIds != null && !this.attributeRepositoryIds.isEmpty();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected Map<String, List<Object>> retrievePersonAttributesFromAttributeRepository(Principal principal) {
        Object object = this.lock;
        synchronized (object) {
            IPersonAttributeDao repository = AbstractPrincipalAttributesRepository.getAttributeRepository();
            if (repository == null) {
                LOGGER.warn("No attribute repositories could be fetched from application context");
                return new HashMap<String, List<Object>>(0);
            }
            return PrincipalAttributeRepositoryFetcher.builder().attributeRepository(repository).principalId(principal.getId()).activeAttributeRepositoryIdentifiers(this.attributeRepositoryIds).currentPrincipal(principal).build().retrieve();
        }
    }

    @JsonIgnore
    protected Map<String, List<Object>> getPrincipalAttributes(Principal principal) {
        if (this.ignoreResolvedAttributes) {
            return new HashMap<String, List<Object>>(0);
        }
        return AbstractPrincipalAttributesRepository.convertPrincipalAttributesToPersonAttributes(principal.getAttributes());
    }

    @Generated
    public String toString() {
        return "AbstractPrincipalAttributesRepository(mergingStrategy=" + this.mergingStrategy + ", attributeRepositoryIds=" + this.attributeRepositoryIds + ", ignoreResolvedAttributes=" + this.ignoreResolvedAttributes + ")";
    }

    @Generated
    protected AbstractPrincipalAttributesRepository() {
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AbstractPrincipalAttributesRepository)) {
            return false;
        }
        AbstractPrincipalAttributesRepository other = (AbstractPrincipalAttributesRepository)o;
        if (!other.canEqual(this)) {
            return false;
        }
        PrincipalAttributesCoreProperties.MergingStrategyTypes this$mergingStrategy = this.mergingStrategy;
        PrincipalAttributesCoreProperties.MergingStrategyTypes other$mergingStrategy = other.mergingStrategy;
        if (this$mergingStrategy == null ? other$mergingStrategy != null : !this$mergingStrategy.equals(other$mergingStrategy)) {
            return false;
        }
        Set<String> this$attributeRepositoryIds = this.attributeRepositoryIds;
        Set<String> other$attributeRepositoryIds = other.attributeRepositoryIds;
        return !(this$attributeRepositoryIds == null ? other$attributeRepositoryIds != null : !((Object)this$attributeRepositoryIds).equals(other$attributeRepositoryIds));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof AbstractPrincipalAttributesRepository;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        PrincipalAttributesCoreProperties.MergingStrategyTypes $mergingStrategy = this.mergingStrategy;
        result = result * 59 + ($mergingStrategy == null ? 43 : $mergingStrategy.hashCode());
        Set<String> $attributeRepositoryIds = this.attributeRepositoryIds;
        result = result * 59 + ($attributeRepositoryIds == null ? 43 : ((Object)$attributeRepositoryIds).hashCode());
        return result;
    }

    @Generated
    public PrincipalAttributesCoreProperties.MergingStrategyTypes getMergingStrategy() {
        return this.mergingStrategy;
    }

    @Generated
    public void setMergingStrategy(PrincipalAttributesCoreProperties.MergingStrategyTypes mergingStrategy) {
        this.mergingStrategy = mergingStrategy;
    }

    @Generated
    public Set<String> getAttributeRepositoryIds() {
        return this.attributeRepositoryIds;
    }

    @Generated
    public void setAttributeRepositoryIds(Set<String> attributeRepositoryIds) {
        this.attributeRepositoryIds = attributeRepositoryIds;
    }

    @Generated
    public boolean isIgnoreResolvedAttributes() {
        return this.ignoreResolvedAttributes;
    }

    @Generated
    public void setIgnoreResolvedAttributes(boolean ignoreResolvedAttributes) {
        this.ignoreResolvedAttributes = ignoreResolvedAttributes;
    }
}

