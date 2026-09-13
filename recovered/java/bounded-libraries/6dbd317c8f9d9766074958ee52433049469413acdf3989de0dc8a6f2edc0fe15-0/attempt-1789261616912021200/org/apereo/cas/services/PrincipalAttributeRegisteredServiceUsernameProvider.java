/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.authentication.principal.Principal
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.services.RegisteredServiceAttributeReleasePolicyContext
 *  org.apereo.cas.services.UnauthorizedServiceException
 *  org.apereo.cas.util.CollectionUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.services.BaseRegisteredServiceUsernameAttributeProvider;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.RegisteredServiceAttributeReleasePolicyContext;
import org.apereo.cas.services.UnauthorizedServiceException;
import org.apereo.cas.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonInclude(value=JsonInclude.Include.NON_DEFAULT)
public class PrincipalAttributeRegisteredServiceUsernameProvider
extends BaseRegisteredServiceUsernameAttributeProvider {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(PrincipalAttributeRegisteredServiceUsernameProvider.class);
    private static final long serialVersionUID = -3546719400741715137L;
    private String usernameAttribute;

    public PrincipalAttributeRegisteredServiceUsernameProvider(String usernameAttribute, String canonicalizationMode) {
        super(canonicalizationMode, false, null);
        this.usernameAttribute = usernameAttribute;
    }

    @Override
    public String resolveUsernameInternal(Principal principal, Service service, RegisteredService registeredService) {
        String principalId = principal.getId();
        TreeMap originalPrincipalAttributes = new TreeMap(String.CASE_INSENSITIVE_ORDER);
        originalPrincipalAttributes.putAll(principal.getAttributes());
        LOGGER.debug("Original principal attributes available for selection of username attribute [{}] are [{}].", (Object)this.usernameAttribute, originalPrincipalAttributes);
        TreeMap<String, List<Object>> releasePolicyAttributes = new TreeMap<String, List<Object>>(String.CASE_INSENSITIVE_ORDER);
        releasePolicyAttributes.putAll(this.getPrincipalAttributesFromReleasePolicy(principal, service, registeredService));
        LOGGER.debug("Attributes resolved by the release policy available for selection of username attribute [{}] are [{}].", (Object)this.usernameAttribute, releasePolicyAttributes);
        if (StringUtils.isBlank((CharSequence)this.usernameAttribute)) {
            LOGGER.warn("No username attribute is defined for service [{}]. CAS will fall back onto using the default principal id. This is likely a mistake in the configuration of the registered service definition.", (Object)registeredService.getName());
        } else if (releasePolicyAttributes.containsKey(this.usernameAttribute)) {
            LOGGER.debug("Attribute release policy for registered service [{}] contains an attribute for [{}]", (Object)registeredService.getServiceId(), (Object)this.usernameAttribute);
            Object value = releasePolicyAttributes.get(this.usernameAttribute);
            principalId = CollectionUtils.wrap(value).get(0).toString();
        } else if (originalPrincipalAttributes.containsKey(this.usernameAttribute)) {
            LOGGER.debug("The selected username attribute [{}] was retrieved as a direct principal attribute and not through the attribute release policy for service [{}]. CAS is unable to detect new attribute values for [{}] after authentication unless the attribute is explicitly authorized for release via the service attribute release policy.", new Object[]{this.usernameAttribute, service, this.usernameAttribute});
            Object value = originalPrincipalAttributes.get(this.usernameAttribute);
            principalId = CollectionUtils.wrap(value).get(0).toString();
        } else {
            LOGGER.info("Principal [{}] does not have an attribute [{}] among attributes [{}] so CAS cannot provide the user attribute the service expects. CAS will instead return the default principal id [{}]. Ensure the attribute selected as the username is allowed to be released by the service attribute release policy.", new Object[]{principalId, this.usernameAttribute, releasePolicyAttributes, principalId});
        }
        LOGGER.debug("Principal id to return for [{}] is [{}]. The default principal id is [{}].", new Object[]{service.getId(), principalId, principal.getId()});
        return principalId.trim();
    }

    protected Map<String, List<Object>> getPrincipalAttributesFromReleasePolicy(Principal principal, Service service, RegisteredService registeredService) {
        if (registeredService != null && registeredService.getAccessStrategy().isServiceAccessAllowed()) {
            LOGGER.debug("Located service [{}] in the registry. Attempting to resolve attributes for [{}]", (Object)registeredService, (Object)principal.getId());
            if (registeredService.getAttributeReleasePolicy() == null) {
                LOGGER.debug("No attribute release policy is defined for [{}]. Returning default principal attributes", (Object)service.getId());
                return principal.getAttributes();
            }
            RegisteredServiceAttributeReleasePolicyContext context = RegisteredServiceAttributeReleasePolicyContext.builder().registeredService(registeredService).service(service).principal(principal).build();
            return registeredService.getAttributeReleasePolicy().getAttributes(context);
        }
        LOGGER.debug("Could not locate service [{}] in the registry.", (Object)service.getId());
        throw new UnauthorizedServiceException("screen.service.error.message");
    }

    @Generated
    public String toString() {
        return "PrincipalAttributeRegisteredServiceUsernameProvider(usernameAttribute=" + this.usernameAttribute + ")";
    }

    @Generated
    public String getUsernameAttribute() {
        return this.usernameAttribute;
    }

    @Generated
    public PrincipalAttributeRegisteredServiceUsernameProvider setUsernameAttribute(String usernameAttribute) {
        this.usernameAttribute = usernameAttribute;
        return this;
    }

    @Generated
    public PrincipalAttributeRegisteredServiceUsernameProvider() {
    }

    @Generated
    public PrincipalAttributeRegisteredServiceUsernameProvider(String usernameAttribute) {
        this.usernameAttribute = usernameAttribute;
    }

    @Override
    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PrincipalAttributeRegisteredServiceUsernameProvider)) {
            return false;
        }
        PrincipalAttributeRegisteredServiceUsernameProvider other = (PrincipalAttributeRegisteredServiceUsernameProvider)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        String this$usernameAttribute = this.usernameAttribute;
        String other$usernameAttribute = other.usernameAttribute;
        return !(this$usernameAttribute == null ? other$usernameAttribute != null : !this$usernameAttribute.equals(other$usernameAttribute));
    }

    @Override
    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof PrincipalAttributeRegisteredServiceUsernameProvider;
    }

    @Override
    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        String $usernameAttribute = this.usernameAttribute;
        result = result * 59 + ($usernameAttribute == null ? 43 : $usernameAttribute.hashCode());
        return result;
    }
}

