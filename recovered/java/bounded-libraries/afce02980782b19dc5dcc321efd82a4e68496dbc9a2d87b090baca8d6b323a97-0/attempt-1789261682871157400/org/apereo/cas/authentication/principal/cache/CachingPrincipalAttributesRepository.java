/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  lombok.Generated
 *  org.apereo.cas.authentication.CoreAuthenticationUtils
 *  org.apereo.cas.authentication.principal.Principal
 *  org.apereo.cas.authentication.principal.RegisteredServicePrincipalAttributesRepository
 *  org.apereo.cas.configuration.model.core.authentication.PrincipalAttributesCoreProperties$MergingStrategyTypes
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.util.spring.ApplicationContextProvider
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.authentication.principal.cache;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Generated;
import org.apereo.cas.authentication.CoreAuthenticationUtils;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.authentication.principal.RegisteredServicePrincipalAttributesRepository;
import org.apereo.cas.authentication.principal.cache.AbstractPrincipalAttributesRepository;
import org.apereo.cas.configuration.model.core.authentication.PrincipalAttributesCoreProperties;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.util.spring.ApplicationContextProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CachingPrincipalAttributesRepository
extends AbstractPrincipalAttributesRepository {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(CachingPrincipalAttributesRepository.class);
    private static final long serialVersionUID = 6350244643948535906L;
    protected long expiration;
    protected String timeUnit;

    @JsonCreator
    public CachingPrincipalAttributesRepository(@JsonProperty(value="timeUnit") String timeUnit, @JsonProperty(value="expiration") long expiryDuration) {
        this.timeUnit = timeUnit;
        this.expiration = expiryDuration;
    }

    public Map<String, List<Object>> getAttributes(Principal principal, RegisteredService registeredService) {
        PrincipalAttributesCoreProperties.MergingStrategyTypes mergeStrategy = this.determineMergingStrategy();
        LOGGER.trace("Determined merging strategy as [{}]", (Object)mergeStrategy);
        Map<String, List<Object>> cachedAttributes = this.fetchCachedPrincipalAttributes(principal, registeredService);
        if (cachedAttributes != null && !cachedAttributes.isEmpty()) {
            LOGGER.debug("Found [{}] cached attributes for principal [{}] that are [{}]", new Object[]{cachedAttributes.size(), principal.getId(), cachedAttributes});
            return cachedAttributes;
        }
        Map<String, List<Object>> principalAttributes = this.getPrincipalAttributes(principal);
        LOGGER.trace("Principal attributes extracted for [{}] are [{}]", (Object)principal.getId(), principalAttributes);
        if (this.areAttributeRepositoryIdsDefined()) {
            Map<String, List<Object>> personDirectoryAttributes = this.retrievePersonAttributesFromAttributeRepository(principal);
            LOGGER.debug("Found [{}] attributes for principal [{}] from the attribute repository.", (Object)personDirectoryAttributes.size(), (Object)principal.getId());
            LOGGER.debug("Merging current principal attributes with that of the repository via strategy [{}]", (Object)mergeStrategy);
            Map mergedAttributes = CoreAuthenticationUtils.getAttributeMerger((PrincipalAttributesCoreProperties.MergingStrategyTypes)mergeStrategy).mergeAttributes(principalAttributes, personDirectoryAttributes);
            return this.convertAttributesToPrincipalAttributesAndCache(principal, mergedAttributes, registeredService);
        }
        return this.convertAttributesToPrincipalAttributesAndCache(principal, principalAttributes, registeredService);
    }

    public void update(String id, Map<String, List<Object>> attributes, RegisteredService registeredService) {
        ApplicationContextProvider.getPrincipalAttributesRepositoryCache().ifPresent(cache -> {
            cache.putAttributes(registeredService, (RegisteredServicePrincipalAttributesRepository)this, id, attributes);
            LOGGER.trace("Cached attributes for [{}] and [{}]", (Object)id, (Object)registeredService.getName());
        });
    }

    protected Map<String, List<Object>> fetchCachedPrincipalAttributes(Principal principal, RegisteredService registeredService) {
        return ApplicationContextProvider.getPrincipalAttributesRepositoryCache().map(cache -> cache.fetchAttributes(registeredService, (RegisteredServicePrincipalAttributesRepository)this, principal)).orElseGet(() -> new HashMap(0));
    }

    @Override
    @Generated
    public String toString() {
        return "CachingPrincipalAttributesRepository(super=" + super.toString() + ", expiration=" + this.expiration + ", timeUnit=" + this.timeUnit + ")";
    }

    @Generated
    public long getExpiration() {
        return this.expiration;
    }

    @Generated
    public String getTimeUnit() {
        return this.timeUnit;
    }

    @Generated
    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }

    @Generated
    public void setTimeUnit(String timeUnit) {
        this.timeUnit = timeUnit;
    }

    @Generated
    public CachingPrincipalAttributesRepository() {
    }

    @Override
    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CachingPrincipalAttributesRepository)) {
            return false;
        }
        CachingPrincipalAttributesRepository other = (CachingPrincipalAttributesRepository)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        if (this.expiration != other.expiration) {
            return false;
        }
        String this$timeUnit = this.timeUnit;
        String other$timeUnit = other.timeUnit;
        return !(this$timeUnit == null ? other$timeUnit != null : !this$timeUnit.equals(other$timeUnit));
    }

    @Override
    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof CachingPrincipalAttributesRepository;
    }

    @Override
    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        long $expiration = this.expiration;
        result = result * 59 + (int)($expiration >>> 32 ^ $expiration);
        String $timeUnit = this.timeUnit;
        result = result * 59 + ($timeUnit == null ? 43 : $timeUnit.hashCode());
        return result;
    }
}

