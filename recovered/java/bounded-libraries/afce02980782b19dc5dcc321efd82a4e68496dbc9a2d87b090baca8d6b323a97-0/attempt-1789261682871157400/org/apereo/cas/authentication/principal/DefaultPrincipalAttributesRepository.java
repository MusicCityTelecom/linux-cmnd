/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  lombok.Generated
 *  org.apereo.cas.authentication.CoreAuthenticationUtils
 *  org.apereo.cas.authentication.principal.Principal
 *  org.apereo.cas.configuration.model.core.authentication.PrincipalAttributesCoreProperties$MergingStrategyTypes
 *  org.apereo.cas.services.RegisteredService
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.authentication.principal;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import java.util.Map;
import lombok.Generated;
import org.apereo.cas.authentication.CoreAuthenticationUtils;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.authentication.principal.cache.AbstractPrincipalAttributesRepository;
import org.apereo.cas.configuration.model.core.authentication.PrincipalAttributesCoreProperties;
import org.apereo.cas.services.RegisteredService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonInclude(value=JsonInclude.Include.NON_DEFAULT)
public class DefaultPrincipalAttributesRepository
extends AbstractPrincipalAttributesRepository {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultPrincipalAttributesRepository.class);
    private static final long serialVersionUID = -4535358847021241725L;

    public Map<String, List<Object>> getAttributes(Principal principal, RegisteredService registeredService) {
        PrincipalAttributesCoreProperties.MergingStrategyTypes mergeStrategy = this.determineMergingStrategy();
        Map<String, List<Object>> principalAttributes = this.getPrincipalAttributes(principal);
        LOGGER.trace("Operating principal attributes for processing are [{}]", principalAttributes);
        if (this.areAttributeRepositoryIdsDefined()) {
            Map<String, List<Object>> personDirectoryAttributes = this.retrievePersonAttributesFromAttributeRepository(principal);
            LOGGER.debug("Merging current principal attributes with that of the repository via strategy [{}]", (Object)mergeStrategy);
            Map mergedAttributes = CoreAuthenticationUtils.getAttributeMerger((PrincipalAttributesCoreProperties.MergingStrategyTypes)mergeStrategy).mergeAttributes(principalAttributes, personDirectoryAttributes);
            LOGGER.debug("Merged current principal attributes are [{}]", (Object)mergedAttributes);
            return this.convertAttributesToPrincipalAttributesAndCache(principal, mergedAttributes, registeredService);
        }
        return this.convertAttributesToPrincipalAttributesAndCache(principal, principalAttributes, registeredService);
    }

    @Override
    @Generated
    public String toString() {
        return "DefaultPrincipalAttributesRepository()";
    }

    @Override
    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DefaultPrincipalAttributesRepository)) {
            return false;
        }
        DefaultPrincipalAttributesRepository other = (DefaultPrincipalAttributesRepository)o;
        if (!other.canEqual(this)) {
            return false;
        }
        return super.equals(o);
    }

    @Override
    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof DefaultPrincipalAttributesRepository;
    }

    @Override
    @Generated
    public int hashCode() {
        int result = super.hashCode();
        return result;
    }
}

