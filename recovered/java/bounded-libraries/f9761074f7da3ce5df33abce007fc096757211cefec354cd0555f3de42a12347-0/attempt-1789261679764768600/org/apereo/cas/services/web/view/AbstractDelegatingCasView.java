/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  lombok.Generated
 *  org.apereo.cas.authentication.AuthenticationServiceSelectionPlan
 *  org.apereo.cas.authentication.ProtocolAttributeEncoder
 *  org.apereo.cas.services.ServicesManager
 *  org.apereo.cas.validation.AuthenticationAttributeReleasePolicy
 *  org.apereo.cas.validation.CasProtocolAttributesRenderer
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.web.servlet.View
 *  org.springframework.web.util.ContentCachingRequestWrapper
 *  org.springframework.web.util.ContentCachingResponseWrapper
 */
package org.apereo.cas.services.web.view;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.Generated;
import org.apereo.cas.authentication.AuthenticationServiceSelectionPlan;
import org.apereo.cas.authentication.ProtocolAttributeEncoder;
import org.apereo.cas.services.ServicesManager;
import org.apereo.cas.services.web.view.AbstractCasView;
import org.apereo.cas.validation.AuthenticationAttributeReleasePolicy;
import org.apereo.cas.validation.CasProtocolAttributesRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.View;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

public abstract class AbstractDelegatingCasView
extends AbstractCasView {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractDelegatingCasView.class);
    protected final View view;

    protected AbstractDelegatingCasView(boolean successResponse, ProtocolAttributeEncoder protocolAttributeEncoder, ServicesManager servicesManager, View view, AuthenticationAttributeReleasePolicy authenticationAttributeReleasePolicy, AuthenticationServiceSelectionPlan authenticationRequestServiceSelectionStrategies, CasProtocolAttributesRenderer attributesRenderer) {
        super(successResponse, protocolAttributeEncoder, servicesManager, authenticationAttributeReleasePolicy, authenticationRequestServiceSelectionStrategies, attributesRenderer);
        this.view = view;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        LOGGER.debug("Preparing the output model [{}] to render view [{}]", model.keySet(), (Object)((Object)((Object)this)).getClass().getSimpleName());
        this.prepareMergedOutputModel(model, request, response);
        LOGGER.trace("Prepared output model with objects [{}]. Now rendering view...", model.keySet().toArray());
        try {
            this.getView().render(model, (HttpServletRequest)requestWrapper, (HttpServletResponse)responseWrapper);
        }
        catch (Throwable throwable) {
            byte[] responseArray = responseWrapper.getContentAsByteArray();
            String output = new String(responseArray, responseWrapper.getCharacterEncoding());
            String message = String.format("Final CAS response for [%s] is:%n%s%n", this.getView().toString(), output);
            LOGGER.debug(message);
            responseWrapper.copyBodyToResponse();
            throw throwable;
        }
        byte[] responseArray = responseWrapper.getContentAsByteArray();
        String output = new String(responseArray, responseWrapper.getCharacterEncoding());
        String message = String.format("Final CAS response for [%s] is:%n%s%n", this.getView().toString(), output);
        LOGGER.debug(message);
        responseWrapper.copyBodyToResponse();
    }

    protected abstract void prepareMergedOutputModel(Map<String, Object> var1, HttpServletRequest var2, HttpServletResponse var3) throws Exception;

    @Generated
    public View getView() {
        return this.view;
    }
}

