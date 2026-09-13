/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  lombok.Generated
 *  org.apereo.cas.authentication.principal.ServiceFactory
 *  org.apereo.cas.authentication.principal.WebApplicationService
 *  org.apereo.cas.util.DigestUtils
 *  org.apereo.cas.web.support.ArgumentExtractor
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.web.support;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.Generated;
import org.apereo.cas.authentication.principal.ServiceFactory;
import org.apereo.cas.authentication.principal.WebApplicationService;
import org.apereo.cas.util.DigestUtils;
import org.apereo.cas.web.support.ArgumentExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractArgumentExtractor
implements ArgumentExtractor {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractArgumentExtractor.class);
    protected List<ServiceFactory<? extends WebApplicationService>> serviceFactories = new ArrayList<ServiceFactory<? extends WebApplicationService>>(0);

    public WebApplicationService extractService(HttpServletRequest request) {
        WebApplicationService service = this.extractServiceInternal(request);
        if (service == null) {
            LOGGER.trace("Extractor did not generate service via [{}].", (Object)this.getClass().getName());
        } else {
            LOGGER.trace("Extractor [{}] generated service type [{}] for: [{}]", new Object[]{this.getClass().getName(), service.getClass().getName(), DigestUtils.abbreviate((String)service.getId())});
        }
        return service;
    }

    protected abstract WebApplicationService extractServiceInternal(HttpServletRequest var1);

    public ServiceFactory<? extends WebApplicationService> getServiceFactory() {
        return this.serviceFactories.get(0);
    }

    @Generated
    protected AbstractArgumentExtractor() {
    }

    @Generated
    protected AbstractArgumentExtractor(List<ServiceFactory<? extends WebApplicationService>> serviceFactories) {
        this.serviceFactories = serviceFactories;
    }

    @Generated
    public List<ServiceFactory<? extends WebApplicationService>> getServiceFactories() {
        return this.serviceFactories;
    }
}

