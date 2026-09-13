/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.AuthenticationServiceSelectionPlan
 *  org.apereo.cas.authentication.ProtocolAttributeEncoder
 *  org.apereo.cas.authentication.principal.Principal
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.authentication.principal.WebApplicationService
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.services.ServicesManager
 *  org.apereo.cas.validation.Assertion
 *  org.apereo.cas.validation.AuthenticationAttributeReleasePolicy
 *  org.apereo.cas.validation.CasProtocolAttributesRenderer
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.web.servlet.view.AbstractView
 */
package org.apereo.cas.services.web.view;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Generated;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.AuthenticationServiceSelectionPlan;
import org.apereo.cas.authentication.ProtocolAttributeEncoder;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.authentication.principal.WebApplicationService;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.ServicesManager;
import org.apereo.cas.validation.Assertion;
import org.apereo.cas.validation.AuthenticationAttributeReleasePolicy;
import org.apereo.cas.validation.CasProtocolAttributesRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.AbstractView;

public abstract class AbstractCasView
extends AbstractView {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCasView.class);
    protected final boolean successResponse;
    protected final ProtocolAttributeEncoder protocolAttributeEncoder;
    protected final ServicesManager servicesManager;
    protected final AuthenticationAttributeReleasePolicy authenticationAttributeReleasePolicy;
    protected final AuthenticationServiceSelectionPlan authenticationRequestServiceSelectionStrategies;
    protected final CasProtocolAttributesRenderer attributesRenderer;

    protected Assertion getAssertionFrom(Map<String, Object> model) {
        return (Assertion)model.get("assertion");
    }

    protected String getErrorCodeFrom(Map<String, Object> model) {
        return model.get("code").toString();
    }

    protected String getErrorDescriptionFrom(Map<String, Object> model) {
        return model.get("description").toString();
    }

    protected String getProxyGrantingTicketIou(Map<String, Object> model) {
        return (String)model.get("pgtIou");
    }

    protected Authentication getPrimaryAuthenticationFrom(Map<String, Object> model) {
        return this.getAssertionFrom(model).getPrimaryAuthentication();
    }

    protected Map<String, Object> getModelAttributes(Map<String, Object> model) {
        return (Map)model.get("attributes");
    }

    protected Principal getPrincipal(Map<String, Object> model) {
        return this.getPrimaryAuthenticationFrom(model).getPrincipal();
    }

    protected Map<String, List<Object>> getPrincipalAttributesAsMultiValuedAttributes(Map<String, Object> model) {
        return this.getPrincipal(model).getAttributes();
    }

    protected WebApplicationService getServiceFrom(Map<String, Object> model) {
        return (WebApplicationService)model.get("service");
    }

    protected Collection<Authentication> getChainedAuthentications(Map<String, Object> model) {
        Assertion assertion = this.getAssertionFrom(model);
        List chainedAuthentications = assertion.getChainedAuthentications();
        return chainedAuthentications.stream().limit(chainedAuthentications.size() - 1).collect(Collectors.toList());
    }

    protected void putIntoModel(Map<String, Object> model, String key, Object value) {
        LOGGER.trace("Adding attribute [{}] into the view model for [{}] with value [{}]", new Object[]{key, ((Object)((Object)this)).getClass().getSimpleName(), value});
        model.put(key, value);
    }

    protected Map<String, List<Object>> getCasProtocolAuthenticationAttributes(Map<String, Object> model, RegisteredService registeredService) {
        Authentication authn = this.getPrimaryAuthenticationFrom(model);
        Assertion assertion = this.getAssertionFrom(model);
        return this.authenticationAttributeReleasePolicy.getAuthenticationAttributesForRelease(authn, assertion, model, registeredService);
    }

    protected Map prepareViewModelWithAuthenticationPrincipal(Map<String, Object> model) {
        this.putIntoModel(model, "principal", this.getPrincipal(model));
        this.putIntoModel(model, "chainedAuthentications", this.getChainedAuthentications(model));
        this.putIntoModel(model, "primaryAuthentication", this.getPrimaryAuthenticationFrom(model));
        LOGGER.trace("Prepared CAS response output model with attribute names [{}]", model.keySet());
        return model;
    }

    protected void prepareCasResponseAttributesForViewModel(Map<String, Object> model) {
        Service service = this.authenticationRequestServiceSelectionStrategies.resolveService((Service)this.getServiceFrom(model));
        RegisteredService registeredService = this.servicesManager.findServiceBy(service);
        Map<String, List<Object>> principalAttributes = this.getCasPrincipalAttributes(model, registeredService);
        HashMap<String, Object> attributes = new HashMap<String, Object>(principalAttributes);
        LOGGER.trace("Processed principal attributes from the output model to be [{}]", principalAttributes.keySet());
        Map<String, List<Object>> protocolAttributes = this.getCasProtocolAuthenticationAttributes(model, registeredService);
        attributes.putAll(protocolAttributes);
        LOGGER.debug("Final collection of attributes for the response are [{}].", attributes.keySet());
        this.putCasResponseAttributesIntoModel(model, attributes, registeredService, this.attributesRenderer);
    }

    protected Map<String, List<Object>> getCasPrincipalAttributes(Map<String, Object> model, RegisteredService registeredService) {
        return this.getPrincipalAttributesAsMultiValuedAttributes(model);
    }

    protected void putCasResponseAttributesIntoModel(Map<String, Object> model, Map<String, Object> attributes, RegisteredService registeredService, CasProtocolAttributesRenderer attributesRenderer) {
        LOGGER.trace("Beginning to encode attributes for the response");
        WebApplicationService webApplicationService = this.getServiceFrom(model);
        Map encodedAttributes = this.protocolAttributeEncoder.encodeAttributes(attributes, registeredService, webApplicationService);
        LOGGER.debug("Encoded attributes for the response are [{}]", (Object)encodedAttributes);
        this.putIntoModel(model, "attributes", encodedAttributes);
        Collection formattedAttributes = attributesRenderer.render(encodedAttributes);
        this.putIntoModel(model, "formattedAttributes", formattedAttributes);
    }

    @Generated
    public boolean isSuccessResponse() {
        return this.successResponse;
    }

    @Generated
    public ProtocolAttributeEncoder getProtocolAttributeEncoder() {
        return this.protocolAttributeEncoder;
    }

    @Generated
    public ServicesManager getServicesManager() {
        return this.servicesManager;
    }

    @Generated
    public AuthenticationAttributeReleasePolicy getAuthenticationAttributeReleasePolicy() {
        return this.authenticationAttributeReleasePolicy;
    }

    @Generated
    public AuthenticationServiceSelectionPlan getAuthenticationRequestServiceSelectionStrategies() {
        return this.authenticationRequestServiceSelectionStrategies;
    }

    @Generated
    public CasProtocolAttributesRenderer getAttributesRenderer() {
        return this.attributesRenderer;
    }

    @Generated
    protected AbstractCasView(boolean successResponse, ProtocolAttributeEncoder protocolAttributeEncoder, ServicesManager servicesManager, AuthenticationAttributeReleasePolicy authenticationAttributeReleasePolicy, AuthenticationServiceSelectionPlan authenticationRequestServiceSelectionStrategies, CasProtocolAttributesRenderer attributesRenderer) {
        this.successResponse = successResponse;
        this.protocolAttributeEncoder = protocolAttributeEncoder;
        this.servicesManager = servicesManager;
        this.authenticationAttributeReleasePolicy = authenticationAttributeReleasePolicy;
        this.authenticationRequestServiceSelectionStrategies = authenticationRequestServiceSelectionStrategies;
        this.attributesRenderer = attributesRenderer;
    }
}

