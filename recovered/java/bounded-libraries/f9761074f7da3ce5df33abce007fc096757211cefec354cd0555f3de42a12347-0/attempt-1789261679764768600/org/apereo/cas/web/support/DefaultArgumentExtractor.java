/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  lombok.Generated
 *  org.apereo.cas.authentication.principal.ServiceFactory
 *  org.apereo.cas.authentication.principal.WebApplicationService
 *  org.apereo.cas.util.CollectionUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.web.support;

import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import lombok.Generated;
import org.apereo.cas.authentication.principal.ServiceFactory;
import org.apereo.cas.authentication.principal.WebApplicationService;
import org.apereo.cas.util.CollectionUtils;
import org.apereo.cas.web.support.AbstractArgumentExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultArgumentExtractor
extends AbstractArgumentExtractor {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultArgumentExtractor.class);

    public DefaultArgumentExtractor(ServiceFactory<? extends WebApplicationService> serviceFactory) {
        super(CollectionUtils.wrapList((Object[])new ServiceFactory[]{serviceFactory}));
    }

    public DefaultArgumentExtractor(List<ServiceFactory<? extends WebApplicationService>> serviceFactoryList) {
        super(serviceFactoryList);
    }

    @Override
    public WebApplicationService extractServiceInternal(HttpServletRequest request) {
        return this.getServiceFactories().stream().map(factory -> {
            WebApplicationService service = (WebApplicationService)factory.createService(request);
            LOGGER.trace("Created [{}] based on [{}]", (Object)service, factory);
            return service;
        }).filter(Objects::nonNull).findFirst().orElseGet(() -> {
            LOGGER.trace("No service could be extracted based on the given request");
            return null;
        });
    }
}

