/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.CoreAuthenticationUtils
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.services.RegisteredServiceAttributeReleasePolicy
 *  org.apereo.cas.util.CollectionUtils
 *  org.apereo.cas.validation.Assertion
 *  org.apereo.cas.validation.AuthenticationAttributeReleasePolicy
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.authentication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.CoreAuthenticationUtils;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.RegisteredServiceAttributeReleasePolicy;
import org.apereo.cas.util.CollectionUtils;
import org.apereo.cas.validation.Assertion;
import org.apereo.cas.validation.AuthenticationAttributeReleasePolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultAuthenticationAttributeReleasePolicy
implements AuthenticationAttributeReleasePolicy {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultAuthenticationAttributeReleasePolicy.class);
    private final Collection<String> onlyReleaseAttributes;
    private final Collection<String> neverReleaseAttributes;
    private final String authenticationContextAttribute;

    public DefaultAuthenticationAttributeReleasePolicy(String authenticationContextAttribute) {
        this(new ArrayList<String>(0), new ArrayList<String>(0), authenticationContextAttribute);
    }

    public Map<String, List<Object>> getAuthenticationAttributesForRelease(Authentication authentication, Assertion assertion, Map<String, Object> model, RegisteredService service) {
        List<Boolean> values;
        if (!service.getAttributeReleasePolicy().isAuthorizedToReleaseAuthenticationAttributes()) {
            LOGGER.debug("Attribute release policy for service [{}] is configured to never release any authentication attributes", (Object)service.getServiceId());
            return new LinkedHashMap<String, List<Object>>(0);
        }
        LinkedHashMap<String, List<Object>> attrs = new LinkedHashMap<String, List<Object>>(authentication.getAttributes());
        attrs.keySet().removeAll(this.neverReleaseAttributes);
        if (this.onlyReleaseAttributes != null && !this.onlyReleaseAttributes.isEmpty()) {
            attrs.keySet().retainAll(this.onlyReleaseAttributes);
        }
        if (this.isAttributeAllowedForRelease("authenticationDate")) {
            attrs.put("authenticationDate", CollectionUtils.wrap((Object)authentication.getAuthenticationDate()));
        }
        if (this.isAttributeAllowedForRelease("isFromNewLogin")) {
            boolean forceAuthn;
            boolean bl = forceAuthn = assertion != null && assertion.isFromNewLogin();
            if (!forceAuthn) {
                values = authentication.getAttributes().getOrDefault("isFromNewLogin", List.of(Boolean.FALSE));
                forceAuthn = values.contains(Boolean.TRUE);
            }
            attrs.put("isFromNewLogin", CollectionUtils.wrap((Object)forceAuthn));
        }
        if (this.isAttributeAllowedForRelease("longTermAuthenticationRequestTokenUsed")) {
            boolean rememberMe;
            boolean bl = rememberMe = assertion != null && CoreAuthenticationUtils.isRememberMeAuthentication((Authentication)authentication, (Assertion)assertion);
            if (!rememberMe) {
                values = authentication.getAttributes().getOrDefault("longTermAuthenticationRequestTokenUsed", List.of(Boolean.FALSE));
                rememberMe = values.contains(Boolean.TRUE);
            }
            attrs.put("longTermAuthenticationRequestTokenUsed", CollectionUtils.wrap((Object)rememberMe));
        }
        if (StringUtils.isNotBlank((CharSequence)this.authenticationContextAttribute) && model.containsKey(this.authenticationContextAttribute)) {
            Optional contextProvider = CollectionUtils.firstElement((Object)model.get(this.authenticationContextAttribute));
            contextProvider.ifPresent(provider -> {
                if (this.isAttributeAllowedForRelease(this.authenticationContextAttribute)) {
                    attrs.put(this.authenticationContextAttribute, CollectionUtils.wrap((Object)provider));
                }
            });
        }
        this.decideIfCredentialPasswordShouldBeReleasedAsAttribute(attrs, authentication, service);
        this.decideIfProxyGrantingTicketShouldBeReleasedAsAttribute(attrs, model, service);
        LOGGER.trace("Processed protocol/authentication attributes from the output model to be [{}]", attrs.keySet());
        return attrs;
    }

    protected boolean isAttributeAllowedForRelease(String attributeName) {
        return !this.neverReleaseAttributes.contains(attributeName) && (this.onlyReleaseAttributes.isEmpty() || this.onlyReleaseAttributes.contains(attributeName));
    }

    protected void decideIfCredentialPasswordShouldBeReleasedAsAttribute(Map<String, List<Object>> attributes, Authentication authentication, RegisteredService service) {
        RegisteredServiceAttributeReleasePolicy policy = service.getAttributeReleasePolicy();
        boolean isAuthorized = policy != null && policy.isAuthorizedToReleaseCredentialPassword();
        Optional element = CollectionUtils.firstElement(authentication.getAttributes().get("credential"));
        String credential = element.map(Object::toString).orElse(null);
        this.decideAttributeReleaseBasedOnServiceAttributePolicy(attributes, credential, "credential", service, isAuthorized);
    }

    protected void decideIfProxyGrantingTicketShouldBeReleasedAsAttribute(Map<String, List<Object>> attributes, Map<String, Object> model, RegisteredService service) {
        RegisteredServiceAttributeReleasePolicy policy = service.getAttributeReleasePolicy();
        boolean isAuthorized = policy != null && policy.isAuthorizedToReleaseProxyGrantingTicket();
        String pgtIou = (String)model.get("pgtIou");
        this.decideAttributeReleaseBasedOnServiceAttributePolicy(attributes, pgtIou, "pgtIou", service, isAuthorized);
        String pgtId = (String)model.get("proxyGrantingTicket");
        this.decideAttributeReleaseBasedOnServiceAttributePolicy(attributes, pgtId, "proxyGrantingTicket", service, isAuthorized);
    }

    protected void decideAttributeReleaseBasedOnServiceAttributePolicy(Map<String, List<Object>> attributes, String attributeValue, String attributeName, RegisteredService service, boolean doesAttributePolicyAllow) {
        if (StringUtils.isNotBlank((CharSequence)attributeValue)) {
            LOGGER.debug("Obtained [{}] as an authentication attribute", (Object)attributeName);
            if (doesAttributePolicyAllow) {
                LOGGER.debug("Obtained [{}] is passed to the CAS validation payload", (Object)attributeName);
                attributes.put(attributeName, CollectionUtils.wrap((Object)attributeValue));
            } else {
                LOGGER.debug("Attribute release policy for [{}] does not authorize the release of [{}]", (Object)service.getServiceId(), (Object)attributeName);
                attributes.remove(attributeName);
            }
        } else {
            LOGGER.trace("[{}] is not available and will not be released to the validation response.", (Object)attributeName);
        }
    }

    @Generated
    public DefaultAuthenticationAttributeReleasePolicy(Collection<String> onlyReleaseAttributes, Collection<String> neverReleaseAttributes, String authenticationContextAttribute) {
        this.onlyReleaseAttributes = onlyReleaseAttributes;
        this.neverReleaseAttributes = neverReleaseAttributes;
        this.authenticationContextAttribute = authenticationContextAttribute;
    }

    @Generated
    public Collection<String> getOnlyReleaseAttributes() {
        return this.onlyReleaseAttributes;
    }

    @Generated
    public Collection<String> getNeverReleaseAttributes() {
        return this.neverReleaseAttributes;
    }

    @Generated
    public String getAuthenticationContextAttribute() {
        return this.authenticationContextAttribute;
    }
}

