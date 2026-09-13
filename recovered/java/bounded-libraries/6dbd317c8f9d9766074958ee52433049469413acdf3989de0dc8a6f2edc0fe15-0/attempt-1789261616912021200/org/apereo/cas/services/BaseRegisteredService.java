/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.commons.lang3.builder.CompareToBuilder
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.services.RegisteredServiceAccessStrategy
 *  org.apereo.cas.services.RegisteredServiceAttributeReleasePolicy
 *  org.apereo.cas.services.RegisteredServiceAuthenticationPolicy
 *  org.apereo.cas.services.RegisteredServiceContact
 *  org.apereo.cas.services.RegisteredServiceExpirationPolicy
 *  org.apereo.cas.services.RegisteredServiceLogoutType
 *  org.apereo.cas.services.RegisteredServiceMatchingStrategy
 *  org.apereo.cas.services.RegisteredServiceMultifactorPolicy
 *  org.apereo.cas.services.RegisteredServiceProperty
 *  org.apereo.cas.services.RegisteredServicePublicKey
 *  org.apereo.cas.services.RegisteredServiceTicketGrantingTicketExpirationPolicy
 *  org.apereo.cas.services.RegisteredServiceUsernameAttributeProvider
 *  org.apereo.cas.services.ReturnAllowedAttributeReleasePolicy
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.data.annotation.Id
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.services.DefaultRegisteredServiceAccessStrategy;
import org.apereo.cas.services.DefaultRegisteredServiceAuthenticationPolicy;
import org.apereo.cas.services.DefaultRegisteredServiceExpirationPolicy;
import org.apereo.cas.services.DefaultRegisteredServiceMultifactorPolicy;
import org.apereo.cas.services.DefaultRegisteredServiceUsernameProvider;
import org.apereo.cas.services.FullRegexRegisteredServiceMatchingStrategy;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.RegisteredServiceAccessStrategy;
import org.apereo.cas.services.RegisteredServiceAttributeReleasePolicy;
import org.apereo.cas.services.RegisteredServiceAuthenticationPolicy;
import org.apereo.cas.services.RegisteredServiceContact;
import org.apereo.cas.services.RegisteredServiceExpirationPolicy;
import org.apereo.cas.services.RegisteredServiceLogoutType;
import org.apereo.cas.services.RegisteredServiceMatchingStrategy;
import org.apereo.cas.services.RegisteredServiceMultifactorPolicy;
import org.apereo.cas.services.RegisteredServiceProperty;
import org.apereo.cas.services.RegisteredServicePublicKey;
import org.apereo.cas.services.RegisteredServiceTicketGrantingTicketExpirationPolicy;
import org.apereo.cas.services.RegisteredServiceUsernameAttributeProvider;
import org.apereo.cas.services.ReturnAllowedAttributeReleasePolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;

@JsonInclude(value=JsonInclude.Include.NON_DEFAULT)
public abstract class BaseRegisteredService
implements RegisteredService {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseRegisteredService.class);
    private static final long serialVersionUID = 7645279151115635245L;
    protected String serviceId;
    private String name;
    private String theme;
    private String locale;
    private String informationUrl;
    private String privacyUrl;
    private String responseType;
    @Id
    private long id = -1L;
    private String description;
    private RegisteredServiceExpirationPolicy expirationPolicy = new DefaultRegisteredServiceExpirationPolicy();
    private RegisteredServiceTicketGrantingTicketExpirationPolicy ticketGrantingTicketExpirationPolicy;
    private int evaluationOrder;
    private RegisteredServiceUsernameAttributeProvider usernameAttributeProvider = new DefaultRegisteredServiceUsernameProvider();
    private RegisteredServiceLogoutType logoutType = RegisteredServiceLogoutType.BACK_CHANNEL;
    private Set<String> environments = new LinkedHashSet<String>(0);
    private RegisteredServiceAttributeReleasePolicy attributeReleasePolicy = new ReturnAllowedAttributeReleasePolicy();
    @JsonProperty(value="multifactorPolicy")
    private RegisteredServiceMultifactorPolicy multifactorAuthenticationPolicy = new DefaultRegisteredServiceMultifactorPolicy();
    private RegisteredServicePublicKey publicKey;
    private RegisteredServiceMatchingStrategy matchingStrategy = new FullRegexRegisteredServiceMatchingStrategy();
    private String logo;
    private String logoutUrl;
    private RegisteredServiceAccessStrategy accessStrategy = new DefaultRegisteredServiceAccessStrategy();
    private RegisteredServiceAuthenticationPolicy authenticationPolicy = new DefaultRegisteredServiceAuthenticationPolicy();
    private Map<String, RegisteredServiceProperty> properties = new HashMap<String, RegisteredServiceProperty>(0);
    private List<RegisteredServiceContact> contacts = new ArrayList<RegisteredServiceContact>(0);

    public int compareTo(RegisteredService other) {
        return new CompareToBuilder().append(this.getEvaluationPriority(), other.getEvaluationPriority()).append(this.getEvaluationOrder(), other.getEvaluationOrder()).append((Object)((String)StringUtils.defaultIfBlank((CharSequence)this.getName(), (CharSequence)"")).toLowerCase(), (Object)((String)StringUtils.defaultIfBlank((CharSequence)other.getName(), (CharSequence)"")).toLowerCase()).append((Object)this.getServiceId(), (Object)other.getServiceId()).append(this.getId(), other.getId()).toComparison();
    }

    @Deprecated(since="6.2.0")
    @JsonIgnore
    public Set<String> getRequiredHandlers() {
        LOGGER.debug("Assigning a collection of required authentication handlers to a registered service is deprecated. This field is scheduled to be removed in the future. If you need to, consider defining an authentication policy for the registered service instead to specify required authentication handlers");
        return this.getAuthenticationPolicy().getRequiredAuthenticationHandlers();
    }

    @Deprecated(since="6.2.0")
    @JsonIgnore
    public void setRequiredHandlers(Set<String> requiredHandlers) {
        if (requiredHandlers != null) {
            LOGGER.debug("Assigning a collection of required authentication handlers to a registered service is deprecated. This field is scheduled to be removed in the future. If you need to, consider defining an authentication policy for the registered service instead to specify required authentication handlers [{}]", requiredHandlers);
            this.initialize();
            this.getAuthenticationPolicy().getRequiredAuthenticationHandlers().addAll(requiredHandlers);
        }
    }

    public boolean matches(Service service) {
        return service != null && this.matches(service.getId());
    }

    public boolean matches(String serviceId) {
        this.configureMatchingStrategy();
        return !StringUtils.isBlank((CharSequence)serviceId) && this.getMatchingStrategy().matches((RegisteredService)this, serviceId);
    }

    protected void configureMatchingStrategy() {
        if (this.getMatchingStrategy() == null) {
            this.setMatchingStrategy(new FullRegexRegisteredServiceMatchingStrategy());
        }
    }

    @Generated
    public String toString() {
        return "BaseRegisteredService(serviceId=" + this.serviceId + ", name=" + this.name + ", theme=" + this.theme + ", locale=" + this.locale + ", informationUrl=" + this.informationUrl + ", privacyUrl=" + this.privacyUrl + ", responseType=" + this.responseType + ", id=" + this.id + ", description=" + this.description + ", expirationPolicy=" + this.expirationPolicy + ", ticketGrantingTicketExpirationPolicy=" + this.ticketGrantingTicketExpirationPolicy + ", evaluationOrder=" + this.evaluationOrder + ", usernameAttributeProvider=" + this.usernameAttributeProvider + ", logoutType=" + this.logoutType + ", environments=" + this.environments + ", attributeReleasePolicy=" + this.attributeReleasePolicy + ", multifactorAuthenticationPolicy=" + this.multifactorAuthenticationPolicy + ", publicKey=" + this.publicKey + ", matchingStrategy=" + this.matchingStrategy + ", logo=" + this.logo + ", logoutUrl=" + this.logoutUrl + ", accessStrategy=" + this.accessStrategy + ", authenticationPolicy=" + this.authenticationPolicy + ", properties=" + this.properties + ", contacts=" + this.contacts + ")";
    }

    @Generated
    public String getServiceId() {
        return this.serviceId;
    }

    @Generated
    public String getName() {
        return this.name;
    }

    @Generated
    public String getTheme() {
        return this.theme;
    }

    @Generated
    public String getLocale() {
        return this.locale;
    }

    @Generated
    public String getInformationUrl() {
        return this.informationUrl;
    }

    @Generated
    public String getPrivacyUrl() {
        return this.privacyUrl;
    }

    @Generated
    public String getResponseType() {
        return this.responseType;
    }

    @Generated
    public long getId() {
        return this.id;
    }

    @Generated
    public String getDescription() {
        return this.description;
    }

    @Generated
    public RegisteredServiceExpirationPolicy getExpirationPolicy() {
        return this.expirationPolicy;
    }

    @Generated
    public RegisteredServiceTicketGrantingTicketExpirationPolicy getTicketGrantingTicketExpirationPolicy() {
        return this.ticketGrantingTicketExpirationPolicy;
    }

    @Generated
    public int getEvaluationOrder() {
        return this.evaluationOrder;
    }

    @Generated
    public RegisteredServiceUsernameAttributeProvider getUsernameAttributeProvider() {
        return this.usernameAttributeProvider;
    }

    @Generated
    public RegisteredServiceLogoutType getLogoutType() {
        return this.logoutType;
    }

    @Generated
    public Set<String> getEnvironments() {
        return this.environments;
    }

    @Generated
    public RegisteredServiceAttributeReleasePolicy getAttributeReleasePolicy() {
        return this.attributeReleasePolicy;
    }

    @Generated
    public RegisteredServiceMultifactorPolicy getMultifactorAuthenticationPolicy() {
        return this.multifactorAuthenticationPolicy;
    }

    @Generated
    public RegisteredServicePublicKey getPublicKey() {
        return this.publicKey;
    }

    @Generated
    public RegisteredServiceMatchingStrategy getMatchingStrategy() {
        return this.matchingStrategy;
    }

    @Generated
    public String getLogo() {
        return this.logo;
    }

    @Generated
    public String getLogoutUrl() {
        return this.logoutUrl;
    }

    @Generated
    public RegisteredServiceAccessStrategy getAccessStrategy() {
        return this.accessStrategy;
    }

    @Generated
    public RegisteredServiceAuthenticationPolicy getAuthenticationPolicy() {
        return this.authenticationPolicy;
    }

    @Generated
    public Map<String, RegisteredServiceProperty> getProperties() {
        return this.properties;
    }

    @Generated
    public List<RegisteredServiceContact> getContacts() {
        return this.contacts;
    }

    @Generated
    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    @Generated
    public void setName(String name) {
        this.name = name;
    }

    @Generated
    public void setTheme(String theme) {
        this.theme = theme;
    }

    @Generated
    public void setLocale(String locale) {
        this.locale = locale;
    }

    @Generated
    public void setInformationUrl(String informationUrl) {
        this.informationUrl = informationUrl;
    }

    @Generated
    public void setPrivacyUrl(String privacyUrl) {
        this.privacyUrl = privacyUrl;
    }

    @Generated
    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    @Generated
    public void setId(long id) {
        this.id = id;
    }

    @Generated
    public void setDescription(String description) {
        this.description = description;
    }

    @Generated
    public void setExpirationPolicy(RegisteredServiceExpirationPolicy expirationPolicy) {
        this.expirationPolicy = expirationPolicy;
    }

    @Generated
    public void setTicketGrantingTicketExpirationPolicy(RegisteredServiceTicketGrantingTicketExpirationPolicy ticketGrantingTicketExpirationPolicy) {
        this.ticketGrantingTicketExpirationPolicy = ticketGrantingTicketExpirationPolicy;
    }

    @Generated
    public void setEvaluationOrder(int evaluationOrder) {
        this.evaluationOrder = evaluationOrder;
    }

    @Generated
    public void setUsernameAttributeProvider(RegisteredServiceUsernameAttributeProvider usernameAttributeProvider) {
        this.usernameAttributeProvider = usernameAttributeProvider;
    }

    @Generated
    public void setLogoutType(RegisteredServiceLogoutType logoutType) {
        this.logoutType = logoutType;
    }

    @Generated
    public void setEnvironments(Set<String> environments) {
        this.environments = environments;
    }

    @Generated
    public void setAttributeReleasePolicy(RegisteredServiceAttributeReleasePolicy attributeReleasePolicy) {
        this.attributeReleasePolicy = attributeReleasePolicy;
    }

    @JsonProperty(value="multifactorPolicy")
    @Generated
    public void setMultifactorAuthenticationPolicy(RegisteredServiceMultifactorPolicy multifactorAuthenticationPolicy) {
        this.multifactorAuthenticationPolicy = multifactorAuthenticationPolicy;
    }

    @Generated
    public void setPublicKey(RegisteredServicePublicKey publicKey) {
        this.publicKey = publicKey;
    }

    @Generated
    public void setMatchingStrategy(RegisteredServiceMatchingStrategy matchingStrategy) {
        this.matchingStrategy = matchingStrategy;
    }

    @Generated
    public void setLogo(String logo) {
        this.logo = logo;
    }

    @Generated
    public void setLogoutUrl(String logoutUrl) {
        this.logoutUrl = logoutUrl;
    }

    @Generated
    public void setAccessStrategy(RegisteredServiceAccessStrategy accessStrategy) {
        this.accessStrategy = accessStrategy;
    }

    @Generated
    public void setAuthenticationPolicy(RegisteredServiceAuthenticationPolicy authenticationPolicy) {
        this.authenticationPolicy = authenticationPolicy;
    }

    @Generated
    public void setProperties(Map<String, RegisteredServiceProperty> properties) {
        this.properties = properties;
    }

    @Generated
    public void setContacts(List<RegisteredServiceContact> contacts) {
        this.contacts = contacts;
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof BaseRegisteredService)) {
            return false;
        }
        BaseRegisteredService other = (BaseRegisteredService)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.evaluationOrder != other.evaluationOrder) {
            return false;
        }
        String this$serviceId = this.serviceId;
        String other$serviceId = other.serviceId;
        if (this$serviceId == null ? other$serviceId != null : !this$serviceId.equals(other$serviceId)) {
            return false;
        }
        String this$name = this.name;
        String other$name = other.name;
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) {
            return false;
        }
        String this$theme = this.theme;
        String other$theme = other.theme;
        if (this$theme == null ? other$theme != null : !this$theme.equals(other$theme)) {
            return false;
        }
        String this$locale = this.locale;
        String other$locale = other.locale;
        if (this$locale == null ? other$locale != null : !this$locale.equals(other$locale)) {
            return false;
        }
        String this$informationUrl = this.informationUrl;
        String other$informationUrl = other.informationUrl;
        if (this$informationUrl == null ? other$informationUrl != null : !this$informationUrl.equals(other$informationUrl)) {
            return false;
        }
        String this$privacyUrl = this.privacyUrl;
        String other$privacyUrl = other.privacyUrl;
        if (this$privacyUrl == null ? other$privacyUrl != null : !this$privacyUrl.equals(other$privacyUrl)) {
            return false;
        }
        String this$responseType = this.responseType;
        String other$responseType = other.responseType;
        if (this$responseType == null ? other$responseType != null : !this$responseType.equals(other$responseType)) {
            return false;
        }
        String this$description = this.description;
        String other$description = other.description;
        if (this$description == null ? other$description != null : !this$description.equals(other$description)) {
            return false;
        }
        RegisteredServiceExpirationPolicy this$expirationPolicy = this.expirationPolicy;
        RegisteredServiceExpirationPolicy other$expirationPolicy = other.expirationPolicy;
        if (this$expirationPolicy == null ? other$expirationPolicy != null : !this$expirationPolicy.equals(other$expirationPolicy)) {
            return false;
        }
        RegisteredServiceTicketGrantingTicketExpirationPolicy this$ticketGrantingTicketExpirationPolicy = this.ticketGrantingTicketExpirationPolicy;
        RegisteredServiceTicketGrantingTicketExpirationPolicy other$ticketGrantingTicketExpirationPolicy = other.ticketGrantingTicketExpirationPolicy;
        if (this$ticketGrantingTicketExpirationPolicy == null ? other$ticketGrantingTicketExpirationPolicy != null : !this$ticketGrantingTicketExpirationPolicy.equals(other$ticketGrantingTicketExpirationPolicy)) {
            return false;
        }
        RegisteredServiceUsernameAttributeProvider this$usernameAttributeProvider = this.usernameAttributeProvider;
        RegisteredServiceUsernameAttributeProvider other$usernameAttributeProvider = other.usernameAttributeProvider;
        if (this$usernameAttributeProvider == null ? other$usernameAttributeProvider != null : !this$usernameAttributeProvider.equals(other$usernameAttributeProvider)) {
            return false;
        }
        RegisteredServiceLogoutType this$logoutType = this.logoutType;
        RegisteredServiceLogoutType other$logoutType = other.logoutType;
        if (this$logoutType == null ? other$logoutType != null : !this$logoutType.equals(other$logoutType)) {
            return false;
        }
        Set<String> this$environments = this.environments;
        Set<String> other$environments = other.environments;
        if (this$environments == null ? other$environments != null : !((Object)this$environments).equals(other$environments)) {
            return false;
        }
        RegisteredServiceAttributeReleasePolicy this$attributeReleasePolicy = this.attributeReleasePolicy;
        RegisteredServiceAttributeReleasePolicy other$attributeReleasePolicy = other.attributeReleasePolicy;
        if (this$attributeReleasePolicy == null ? other$attributeReleasePolicy != null : !this$attributeReleasePolicy.equals(other$attributeReleasePolicy)) {
            return false;
        }
        RegisteredServiceMultifactorPolicy this$multifactorAuthenticationPolicy = this.multifactorAuthenticationPolicy;
        RegisteredServiceMultifactorPolicy other$multifactorAuthenticationPolicy = other.multifactorAuthenticationPolicy;
        if (this$multifactorAuthenticationPolicy == null ? other$multifactorAuthenticationPolicy != null : !this$multifactorAuthenticationPolicy.equals(other$multifactorAuthenticationPolicy)) {
            return false;
        }
        RegisteredServicePublicKey this$publicKey = this.publicKey;
        RegisteredServicePublicKey other$publicKey = other.publicKey;
        if (this$publicKey == null ? other$publicKey != null : !this$publicKey.equals(other$publicKey)) {
            return false;
        }
        RegisteredServiceMatchingStrategy this$matchingStrategy = this.matchingStrategy;
        RegisteredServiceMatchingStrategy other$matchingStrategy = other.matchingStrategy;
        if (this$matchingStrategy == null ? other$matchingStrategy != null : !this$matchingStrategy.equals(other$matchingStrategy)) {
            return false;
        }
        String this$logo = this.logo;
        String other$logo = other.logo;
        if (this$logo == null ? other$logo != null : !this$logo.equals(other$logo)) {
            return false;
        }
        String this$logoutUrl = this.logoutUrl;
        String other$logoutUrl = other.logoutUrl;
        if (this$logoutUrl == null ? other$logoutUrl != null : !this$logoutUrl.equals(other$logoutUrl)) {
            return false;
        }
        RegisteredServiceAccessStrategy this$accessStrategy = this.accessStrategy;
        RegisteredServiceAccessStrategy other$accessStrategy = other.accessStrategy;
        if (this$accessStrategy == null ? other$accessStrategy != null : !this$accessStrategy.equals(other$accessStrategy)) {
            return false;
        }
        RegisteredServiceAuthenticationPolicy this$authenticationPolicy = this.authenticationPolicy;
        RegisteredServiceAuthenticationPolicy other$authenticationPolicy = other.authenticationPolicy;
        if (this$authenticationPolicy == null ? other$authenticationPolicy != null : !this$authenticationPolicy.equals(other$authenticationPolicy)) {
            return false;
        }
        Map<String, RegisteredServiceProperty> this$properties = this.properties;
        Map<String, RegisteredServiceProperty> other$properties = other.properties;
        if (this$properties == null ? other$properties != null : !((Object)this$properties).equals(other$properties)) {
            return false;
        }
        List<RegisteredServiceContact> this$contacts = this.contacts;
        List<RegisteredServiceContact> other$contacts = other.contacts;
        return !(this$contacts == null ? other$contacts != null : !((Object)this$contacts).equals(other$contacts));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof BaseRegisteredService;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.evaluationOrder;
        String $serviceId = this.serviceId;
        result = result * 59 + ($serviceId == null ? 43 : $serviceId.hashCode());
        String $name = this.name;
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        String $theme = this.theme;
        result = result * 59 + ($theme == null ? 43 : $theme.hashCode());
        String $locale = this.locale;
        result = result * 59 + ($locale == null ? 43 : $locale.hashCode());
        String $informationUrl = this.informationUrl;
        result = result * 59 + ($informationUrl == null ? 43 : $informationUrl.hashCode());
        String $privacyUrl = this.privacyUrl;
        result = result * 59 + ($privacyUrl == null ? 43 : $privacyUrl.hashCode());
        String $responseType = this.responseType;
        result = result * 59 + ($responseType == null ? 43 : $responseType.hashCode());
        String $description = this.description;
        result = result * 59 + ($description == null ? 43 : $description.hashCode());
        RegisteredServiceExpirationPolicy $expirationPolicy = this.expirationPolicy;
        result = result * 59 + ($expirationPolicy == null ? 43 : $expirationPolicy.hashCode());
        RegisteredServiceTicketGrantingTicketExpirationPolicy $ticketGrantingTicketExpirationPolicy = this.ticketGrantingTicketExpirationPolicy;
        result = result * 59 + ($ticketGrantingTicketExpirationPolicy == null ? 43 : $ticketGrantingTicketExpirationPolicy.hashCode());
        RegisteredServiceUsernameAttributeProvider $usernameAttributeProvider = this.usernameAttributeProvider;
        result = result * 59 + ($usernameAttributeProvider == null ? 43 : $usernameAttributeProvider.hashCode());
        RegisteredServiceLogoutType $logoutType = this.logoutType;
        result = result * 59 + ($logoutType == null ? 43 : $logoutType.hashCode());
        Set<String> $environments = this.environments;
        result = result * 59 + ($environments == null ? 43 : ((Object)$environments).hashCode());
        RegisteredServiceAttributeReleasePolicy $attributeReleasePolicy = this.attributeReleasePolicy;
        result = result * 59 + ($attributeReleasePolicy == null ? 43 : $attributeReleasePolicy.hashCode());
        RegisteredServiceMultifactorPolicy $multifactorAuthenticationPolicy = this.multifactorAuthenticationPolicy;
        result = result * 59 + ($multifactorAuthenticationPolicy == null ? 43 : $multifactorAuthenticationPolicy.hashCode());
        RegisteredServicePublicKey $publicKey = this.publicKey;
        result = result * 59 + ($publicKey == null ? 43 : $publicKey.hashCode());
        RegisteredServiceMatchingStrategy $matchingStrategy = this.matchingStrategy;
        result = result * 59 + ($matchingStrategy == null ? 43 : $matchingStrategy.hashCode());
        String $logo = this.logo;
        result = result * 59 + ($logo == null ? 43 : $logo.hashCode());
        String $logoutUrl = this.logoutUrl;
        result = result * 59 + ($logoutUrl == null ? 43 : $logoutUrl.hashCode());
        RegisteredServiceAccessStrategy $accessStrategy = this.accessStrategy;
        result = result * 59 + ($accessStrategy == null ? 43 : $accessStrategy.hashCode());
        RegisteredServiceAuthenticationPolicy $authenticationPolicy = this.authenticationPolicy;
        result = result * 59 + ($authenticationPolicy == null ? 43 : $authenticationPolicy.hashCode());
        Map<String, RegisteredServiceProperty> $properties = this.properties;
        result = result * 59 + ($properties == null ? 43 : ((Object)$properties).hashCode());
        List<RegisteredServiceContact> $contacts = this.contacts;
        result = result * 59 + ($contacts == null ? 43 : ((Object)$contacts).hashCode());
        return result;
    }
}

