/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  org.apereo.cas.authentication.principal.ServiceFactory
 *  org.apereo.cas.authentication.principal.WebApplicationService
 */
package org.apereo.cas.web.support;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apereo.cas.authentication.principal.ServiceFactory;
import org.apereo.cas.authentication.principal.WebApplicationService;

public interface ArgumentExtractor {
    public static final String BEAN_NAME = "argumentExtractor";

    public WebApplicationService extractService(HttpServletRequest var1);

    public List<ServiceFactory<? extends WebApplicationService>> getServiceFactories();
}

