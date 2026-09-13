/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.AuthenticationServiceSelectionStrategy
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.authentication.principal.ServiceFactory
 *  org.apereo.cas.authentication.principal.WebApplicationService
 *  org.apereo.cas.services.ServicesManager
 *  org.apereo.cas.util.CollectionUtils
 */
package org.apereo.cas.authentication;

import java.util.LinkedHashMap;
import java.util.List;
import lombok.Generated;
import org.apereo.cas.authentication.AuthenticationServiceSelectionStrategy;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.authentication.principal.ServiceFactory;
import org.apereo.cas.authentication.principal.WebApplicationService;
import org.apereo.cas.services.ServicesManager;
import org.apereo.cas.util.CollectionUtils;

public abstract class BaseAuthenticationServiceSelectionStrategy
implements AuthenticationServiceSelectionStrategy {
    private static final long serialVersionUID = -7458940344679793681L;
    private final transient ServicesManager servicesManager;
    private final transient ServiceFactory<WebApplicationService> webApplicationServiceFactory;
    private int order = Integer.MIN_VALUE;

    protected Service createService(String identifier, Service original) {
        WebApplicationService result = (WebApplicationService)this.webApplicationServiceFactory.createService(identifier);
        LinkedHashMap<String, List> attributes = new LinkedHashMap<String, List>(original.getAttributes());
        attributes.put(Service.class.getName(), CollectionUtils.wrapList((Object[])new Object[]{original.getOriginalUrl()}));
        result.setAttributes(attributes);
        return result;
    }

    @Generated
    public void setOrder(int order) {
        this.order = order;
    }

    @Generated
    public ServicesManager getServicesManager() {
        return this.servicesManager;
    }

    @Generated
    public ServiceFactory<WebApplicationService> getWebApplicationServiceFactory() {
        return this.webApplicationServiceFactory;
    }

    @Generated
    public int getOrder() {
        return this.order;
    }

    @Generated
    protected BaseAuthenticationServiceSelectionStrategy(ServicesManager servicesManager, ServiceFactory<WebApplicationService> webApplicationServiceFactory) {
        this.servicesManager = servicesManager;
        this.webApplicationServiceFactory = webApplicationServiceFactory;
    }
}

