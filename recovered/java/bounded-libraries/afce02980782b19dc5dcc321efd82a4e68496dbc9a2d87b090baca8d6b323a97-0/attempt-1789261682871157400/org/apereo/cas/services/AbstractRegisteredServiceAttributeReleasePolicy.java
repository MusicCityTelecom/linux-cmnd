/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  javax.persistence.PostLoad
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.authentication.principal.Principal
 *  org.apereo.cas.authentication.principal.RegisteredServicePrincipalAttributesRepository
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.services.RegisteredServiceAttributeFilter
 *  org.apereo.cas.services.RegisteredServiceAttributeReleasePolicy
 *  org.apereo.cas.services.RegisteredServiceAttributeReleasePolicyContext
 *  org.apereo.cas.services.RegisteredServiceConsentPolicy
 *  org.apereo.cas.services.RegisteredServiceUsernameAttributeProvider
 *  org.apereo.cas.util.CollectionUtils
 *  org.apereo.cas.util.RegexUtils
 *  org.apereo.cas.util.spring.ApplicationContextProvider
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import javax.persistence.PostLoad;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.authentication.principal.DefaultPrincipalAttributesRepository;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.authentication.principal.RegisteredServicePrincipalAttributesRepository;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.RegisteredServiceAttributeFilter;
import org.apereo.cas.services.RegisteredServiceAttributeReleasePolicy;
import org.apereo.cas.services.RegisteredServiceAttributeReleasePolicyContext;
import org.apereo.cas.services.RegisteredServiceConsentPolicy;
import org.apereo.cas.services.RegisteredServiceUsernameAttributeProvider;
import org.apereo.cas.services.consent.DefaultRegisteredServiceConsentPolicy;
import org.apereo.cas.util.CollectionUtils;
import org.apereo.cas.util.RegexUtils;
import org.apereo.cas.util.spring.ApplicationContextProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonInclude(value=JsonInclude.Include.NON_DEFAULT)
public abstract class AbstractRegisteredServiceAttributeReleasePolicy
implements RegisteredServiceAttributeReleasePolicy {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractRegisteredServiceAttributeReleasePolicy.class);
    private static final long serialVersionUID = 5325460875620586503L;
    private RegisteredServiceAttributeFilter attributeFilter;
    private RegisteredServicePrincipalAttributesRepository principalAttributesRepository = new DefaultPrincipalAttributesRepository();
    private RegisteredServiceConsentPolicy consentPolicy = new DefaultRegisteredServiceConsentPolicy();
    private boolean authorizedToReleaseCredentialPassword;
    private boolean authorizedToReleaseProxyGrantingTicket;
    private boolean excludeDefaultAttributes;
    private boolean authorizedToReleaseAuthenticationAttributes = true;
    private String principalIdAttribute;
    private int order;

    @PostLoad
    public void postLoad() {
        if (this.principalAttributesRepository == null) {
            this.principalAttributesRepository = new DefaultPrincipalAttributesRepository();
        }
        if (this.consentPolicy == null) {
            this.consentPolicy = new DefaultRegisteredServiceConsentPolicy();
        }
    }

    public Map<String, List<Object>> getAttributes(RegisteredServiceAttributeReleasePolicyContext context) {
        TreeMap<String, List<Object>> attributesToRelease = new TreeMap<String, List<Object>>(String.CASE_INSENSITIVE_ORDER);
        if (this.supports(context)) {
            LOGGER.debug("Initiating attributes release phase via [{}] for principal [{}] accessing service [{}] defined by registered service [{}]...", new Object[]{this.getClass().getSimpleName(), context.getPrincipal().getId(), context.getService(), context.getRegisteredService().getServiceId()});
            Map<String, List<Object>> principalAttributes = this.resolveAttributesFromPrincipalAttributeRepository(context.getPrincipal(), context.getRegisteredService());
            LOGGER.debug("Found principal attributes [{}] for [{}]", principalAttributes, (Object)context.getPrincipal().getId());
            Map<String, List<Object>> availableAttributes = this.resolveAttributesFromAttributeDefinitionStore(context, principalAttributes);
            LOGGER.trace("Resolved principal attributes [{}] for [{}] from attribute definition store", availableAttributes, (Object)context.getPrincipal().getId());
            this.getRegisteredServicePrincipalAttributesRepository().ifPresent(repository -> repository.update(context.getPrincipal().getId(), availableAttributes, context.getRegisteredService()));
            LOGGER.trace("Updating principal attributes repository cache for [{}] with [{}]", (Object)context.getPrincipal().getId(), availableAttributes);
            LOGGER.trace("Calling attribute policy [{}] to process attributes for [{}]", (Object)this.getClass().getSimpleName(), (Object)context.getPrincipal().getId());
            Map<String, List<Object>> policyAttributes = this.getAttributesInternal(context, availableAttributes);
            LOGGER.debug("Attribute policy [{}] allows release of [{}] for [{}]", new Object[]{this.getClass().getSimpleName(), policyAttributes, context.getPrincipal().getId()});
            LOGGER.trace("Attempting to merge policy attributes and default attributes");
            if (this.isExcludeDefaultAttributes()) {
                LOGGER.debug("Ignoring default attribute policy attributes");
            } else {
                LOGGER.trace("Checking default attribute policy attributes");
                Map<String, List<Object>> defaultAttributes = this.getReleasedByDefaultAttributes(context.getPrincipal(), availableAttributes);
                LOGGER.debug("Default attributes found to be released are [{}]", defaultAttributes);
                if (!defaultAttributes.isEmpty()) {
                    LOGGER.debug("Adding default attributes first to the released set of attributes");
                    attributesToRelease.putAll(defaultAttributes);
                }
            }
            LOGGER.trace("Adding policy attributes to the released set of attributes");
            attributesToRelease.putAll(policyAttributes);
            this.insertPrincipalIdAsAttributeIfNeeded(context.getPrincipal(), attributesToRelease, context.getService(), context.getRegisteredService());
            if (this.getAttributeFilter() != null) {
                LOGGER.debug("Invoking attribute filter [{}] on the final set of attributes", (Object)this.getAttributeFilter());
                return this.getAttributeFilter().filter(attributesToRelease);
            }
            LOGGER.debug("Finalizing attributes release phase for principal [{}] accessing service [{}] defined by registered service [{}]...", new Object[]{context.getPrincipal().getId(), context.getService(), context.getRegisteredService().getServiceId()});
            return this.returnFinalAttributesCollection(attributesToRelease, context.getRegisteredService());
        }
        return attributesToRelease;
    }

    public Map<String, List<Object>> getConsentableAttributes(RegisteredServiceAttributeReleasePolicyContext context) {
        Map<String, List<Object>> attributes = this.getAttributes(context);
        LOGGER.debug("Initial set of consentable attributes are [{}]", attributes);
        Map results = Optional.ofNullable(this.consentPolicy).filter(policy -> Objects.nonNull(policy.getStatus())).map(policy -> {
            if (policy.getStatus().isFalse()) {
                LOGGER.debug("Cconsent policy is turned off and disabled for service [{}].", (Object)context.getService());
                return new HashMap();
            }
            if (policy.getStatus().isTrue()) {
                if (policy.getExcludedServices() != null && policy.getExcludedServices().stream().anyMatch(ex -> RegexUtils.find((String)ex, (String)context.getService().getId()))) {
                    LOGGER.debug("Consent policy will exclude service [{}].", (Object)context.getService());
                    return new HashMap();
                }
                LOGGER.debug("Activating consent policy [{}] for service [{}]", policy, (Object)context.getService());
                Set excludedAttributes = policy.getExcludedAttributes();
                if (excludedAttributes != null && !excludedAttributes.isEmpty()) {
                    excludedAttributes.forEach(attributes::remove);
                    LOGGER.debug("Consentable attributes after removing excluded attributes are [{}]", (Object)attributes);
                } else {
                    LOGGER.debug("No attributes are defined per the consent policy to be excluded from the consentable attributes");
                }
                Set includeOnlyAttributes = policy.getIncludeOnlyAttributes();
                if (includeOnlyAttributes != null && !includeOnlyAttributes.isEmpty()) {
                    attributes.keySet().retainAll(includeOnlyAttributes);
                    LOGGER.debug("Consentable attributes after force-including attributes are [{}]", (Object)attributes);
                } else {
                    LOGGER.debug("No attributes are defined per the consent policy to forcefully be included in the consentable attributes");
                }
            }
            return attributes;
        }).orElseGet(() -> {
            LOGGER.debug("No consent policy is defined for service [{}]. Using the collection of attributes released for consent", (Object)context.getService());
            return attributes;
        });
        LOGGER.debug("Finalized set of consentable attributes are [{}]", (Object)results);
        return results;
    }

    public abstract Map<String, List<Object>> getAttributesInternal(RegisteredServiceAttributeReleasePolicyContext var1, Map<String, List<Object>> var2);

    protected boolean supports(RegisteredServiceAttributeReleasePolicyContext context) {
        return true;
    }

    protected Map<String, List<Object>> resolveAttributesFromAttributeDefinitionStore(RegisteredServiceAttributeReleasePolicyContext context, Map<String, List<Object>> principalAttributes) {
        LOGGER.trace("Retrieving attribute definition store and attribute definitions...");
        return ApplicationContextProvider.getAttributeDefinitionStore().map(attributeDefinitionStore -> {
            LinkedHashMap availableAttributes = new LinkedHashMap(principalAttributes);
            availableAttributes.putAll(context.getReleasingAttributes());
            if (attributeDefinitionStore.isEmpty()) {
                LOGGER.trace("No attribute definitions are defined in the attribute definition store, or no attribute definitions are requested.");
                return availableAttributes;
            }
            ArrayList<String> requestedDefinitions = new ArrayList<String>(this.determineRequestedAttributeDefinitions(context));
            requestedDefinitions.addAll(principalAttributes.keySet());
            LOGGER.debug("Finding requested attribute definitions [{}] based on available attributes [{}]", requestedDefinitions, availableAttributes);
            return attributeDefinitionStore.resolveAttributeValues(requestedDefinitions, availableAttributes, context.getRegisteredService());
        }).orElseGet(() -> {
            LOGGER.trace("No attribute definition store is available in application context");
            return principalAttributes;
        });
    }

    protected Map<String, List<Object>> resolveAttributesFromPrincipalAttributeRepository(Principal principal, RegisteredService registeredService) {
        Map attributes = this.getRegisteredServicePrincipalAttributesRepository().map(repository -> {
            LOGGER.debug("Using principal attribute repository [{}] to retrieve attributes", repository);
            return repository.getAttributes(principal, registeredService);
        }).orElseGet(() -> ((Principal)principal).getAttributes());
        LOGGER.debug("Attributes retrieved from principal attribute repository for [{}] are [{}]", (Object)principal.getId(), (Object)attributes);
        return attributes;
    }

    protected void insertPrincipalIdAsAttributeIfNeeded(Principal principal, Map<String, List<Object>> attributesToRelease, Service service, RegisteredService registeredService) {
        if (StringUtils.isNotBlank((CharSequence)this.getPrincipalIdAttribute()) && !attributesToRelease.containsKey(this.getPrincipalIdAttribute())) {
            LOGGER.debug("Attempting to resolve the principal id for service [{}]", (Object)registeredService.getServiceId());
            RegisteredServiceUsernameAttributeProvider usernameProvider = registeredService.getUsernameAttributeProvider();
            if (usernameProvider != null) {
                String id = usernameProvider.resolveUsername(principal, service, registeredService);
                LOGGER.debug("Releasing resolved principal id [{}] as attribute [{}]", (Object)id, (Object)this.getPrincipalIdAttribute());
                attributesToRelease.put(this.getPrincipalIdAttribute(), CollectionUtils.wrapList((Object[])new Object[]{id}));
            }
        }
    }

    protected Map<String, List<Object>> returnFinalAttributesCollection(Map<String, List<Object>> attributesToRelease, RegisteredService service) {
        LOGGER.debug("Final collection of attributes allowed are: [{}]", attributesToRelease);
        return attributesToRelease;
    }

    protected Map<String, List<Object>> getReleasedByDefaultAttributes(Principal principal, Map<String, List<Object>> attributes) {
        return ApplicationContextProvider.getCasConfigurationProperties().map(properties -> {
            Set defaultAttrs = properties.getAuthn().getAttributeRepository().getCore().getDefaultAttributesToRelease();
            LOGGER.debug("Default attributes for release are: [{}]", (Object)defaultAttrs);
            TreeMap defaultAttributesToRelease = new TreeMap(String.CASE_INSENSITIVE_ORDER);
            defaultAttrs.forEach(key -> {
                if (attributes.containsKey(key)) {
                    LOGGER.debug("Found and added default attribute for release: [{}]", key);
                    defaultAttributesToRelease.put(key, (List)attributes.get(key));
                }
            });
            return defaultAttributesToRelease;
        }).orElseGet(TreeMap::new);
    }

    protected List<String> determineRequestedAttributeDefinitions(RegisteredServiceAttributeReleasePolicyContext context) {
        return new ArrayList<String>();
    }

    private Optional<RegisteredServicePrincipalAttributesRepository> getRegisteredServicePrincipalAttributesRepository() {
        return Optional.ofNullable(this.principalAttributesRepository).or(ApplicationContextProvider::getPrincipalAttributesRepository);
    }

    @Generated
    public String toString() {
        return "AbstractRegisteredServiceAttributeReleasePolicy(attributeFilter=" + this.attributeFilter + ", principalAttributesRepository=" + this.principalAttributesRepository + ", consentPolicy=" + this.consentPolicy + ", authorizedToReleaseCredentialPassword=" + this.authorizedToReleaseCredentialPassword + ", authorizedToReleaseProxyGrantingTicket=" + this.authorizedToReleaseProxyGrantingTicket + ", excludeDefaultAttributes=" + this.excludeDefaultAttributes + ", authorizedToReleaseAuthenticationAttributes=" + this.authorizedToReleaseAuthenticationAttributes + ", principalIdAttribute=" + this.principalIdAttribute + ", order=" + this.order + ")";
    }

    @Generated
    public RegisteredServiceAttributeFilter getAttributeFilter() {
        return this.attributeFilter;
    }

    @Generated
    public RegisteredServicePrincipalAttributesRepository getPrincipalAttributesRepository() {
        return this.principalAttributesRepository;
    }

    @Generated
    public RegisteredServiceConsentPolicy getConsentPolicy() {
        return this.consentPolicy;
    }

    @Generated
    public boolean isAuthorizedToReleaseCredentialPassword() {
        return this.authorizedToReleaseCredentialPassword;
    }

    @Generated
    public boolean isAuthorizedToReleaseProxyGrantingTicket() {
        return this.authorizedToReleaseProxyGrantingTicket;
    }

    @Generated
    public boolean isExcludeDefaultAttributes() {
        return this.excludeDefaultAttributes;
    }

    @Generated
    public boolean isAuthorizedToReleaseAuthenticationAttributes() {
        return this.authorizedToReleaseAuthenticationAttributes;
    }

    @Generated
    public String getPrincipalIdAttribute() {
        return this.principalIdAttribute;
    }

    @Generated
    public int getOrder() {
        return this.order;
    }

    @Generated
    public void setAttributeFilter(RegisteredServiceAttributeFilter attributeFilter) {
        this.attributeFilter = attributeFilter;
    }

    @Generated
    public void setPrincipalAttributesRepository(RegisteredServicePrincipalAttributesRepository principalAttributesRepository) {
        this.principalAttributesRepository = principalAttributesRepository;
    }

    @Generated
    public void setConsentPolicy(RegisteredServiceConsentPolicy consentPolicy) {
        this.consentPolicy = consentPolicy;
    }

    @Generated
    public void setAuthorizedToReleaseCredentialPassword(boolean authorizedToReleaseCredentialPassword) {
        this.authorizedToReleaseCredentialPassword = authorizedToReleaseCredentialPassword;
    }

    @Generated
    public void setAuthorizedToReleaseProxyGrantingTicket(boolean authorizedToReleaseProxyGrantingTicket) {
        this.authorizedToReleaseProxyGrantingTicket = authorizedToReleaseProxyGrantingTicket;
    }

    @Generated
    public void setExcludeDefaultAttributes(boolean excludeDefaultAttributes) {
        this.excludeDefaultAttributes = excludeDefaultAttributes;
    }

    @Generated
    public void setAuthorizedToReleaseAuthenticationAttributes(boolean authorizedToReleaseAuthenticationAttributes) {
        this.authorizedToReleaseAuthenticationAttributes = authorizedToReleaseAuthenticationAttributes;
    }

    @Generated
    public void setPrincipalIdAttribute(String principalIdAttribute) {
        this.principalIdAttribute = principalIdAttribute;
    }

    @Generated
    public void setOrder(int order) {
        this.order = order;
    }

    @Generated
    protected AbstractRegisteredServiceAttributeReleasePolicy() {
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AbstractRegisteredServiceAttributeReleasePolicy)) {
            return false;
        }
        AbstractRegisteredServiceAttributeReleasePolicy other = (AbstractRegisteredServiceAttributeReleasePolicy)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.authorizedToReleaseCredentialPassword != other.authorizedToReleaseCredentialPassword) {
            return false;
        }
        if (this.authorizedToReleaseProxyGrantingTicket != other.authorizedToReleaseProxyGrantingTicket) {
            return false;
        }
        if (this.excludeDefaultAttributes != other.excludeDefaultAttributes) {
            return false;
        }
        if (this.authorizedToReleaseAuthenticationAttributes != other.authorizedToReleaseAuthenticationAttributes) {
            return false;
        }
        if (this.order != other.order) {
            return false;
        }
        RegisteredServiceAttributeFilter this$attributeFilter = this.attributeFilter;
        RegisteredServiceAttributeFilter other$attributeFilter = other.attributeFilter;
        if (this$attributeFilter == null ? other$attributeFilter != null : !this$attributeFilter.equals(other$attributeFilter)) {
            return false;
        }
        RegisteredServicePrincipalAttributesRepository this$principalAttributesRepository = this.principalAttributesRepository;
        RegisteredServicePrincipalAttributesRepository other$principalAttributesRepository = other.principalAttributesRepository;
        if (this$principalAttributesRepository == null ? other$principalAttributesRepository != null : !this$principalAttributesRepository.equals(other$principalAttributesRepository)) {
            return false;
        }
        RegisteredServiceConsentPolicy this$consentPolicy = this.consentPolicy;
        RegisteredServiceConsentPolicy other$consentPolicy = other.consentPolicy;
        if (this$consentPolicy == null ? other$consentPolicy != null : !this$consentPolicy.equals(other$consentPolicy)) {
            return false;
        }
        String this$principalIdAttribute = this.principalIdAttribute;
        String other$principalIdAttribute = other.principalIdAttribute;
        return !(this$principalIdAttribute == null ? other$principalIdAttribute != null : !this$principalIdAttribute.equals(other$principalIdAttribute));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof AbstractRegisteredServiceAttributeReleasePolicy;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + (this.authorizedToReleaseCredentialPassword ? 79 : 97);
        result = result * 59 + (this.authorizedToReleaseProxyGrantingTicket ? 79 : 97);
        result = result * 59 + (this.excludeDefaultAttributes ? 79 : 97);
        result = result * 59 + (this.authorizedToReleaseAuthenticationAttributes ? 79 : 97);
        result = result * 59 + this.order;
        RegisteredServiceAttributeFilter $attributeFilter = this.attributeFilter;
        result = result * 59 + ($attributeFilter == null ? 43 : $attributeFilter.hashCode());
        RegisteredServicePrincipalAttributesRepository $principalAttributesRepository = this.principalAttributesRepository;
        result = result * 59 + ($principalAttributesRepository == null ? 43 : $principalAttributesRepository.hashCode());
        RegisteredServiceConsentPolicy $consentPolicy = this.consentPolicy;
        result = result * 59 + ($consentPolicy == null ? 43 : $consentPolicy.hashCode());
        String $principalIdAttribute = this.principalIdAttribute;
        result = result * 59 + ($principalIdAttribute == null ? 43 : $principalIdAttribute.hashCode());
        return result;
    }
}

