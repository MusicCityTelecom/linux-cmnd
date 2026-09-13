/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.context.ApplicationContext
 *  org.springframework.core.io.Resource
 *  org.springframework.http.MediaType
 *  org.springframework.util.StringUtils
 *  org.springframework.web.servlet.handler.AbstractUrlHandlerMapping
 *  org.springframework.web.servlet.mvc.ParameterizableViewController
 */
package org.springframework.boot.autoconfigure.web.servlet;

import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.template.TemplateAvailabilityProviders;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.AbstractUrlHandlerMapping;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

final class WelcomePageHandlerMapping
extends AbstractUrlHandlerMapping {
    private static final Log logger = LogFactory.getLog(WelcomePageHandlerMapping.class);
    private static final List<MediaType> MEDIA_TYPES_ALL = Collections.singletonList(MediaType.ALL);

    WelcomePageHandlerMapping(TemplateAvailabilityProviders templateAvailabilityProviders, ApplicationContext applicationContext, Resource welcomePage, String staticPathPattern) {
        if (welcomePage != null && "/**".equals(staticPathPattern)) {
            logger.info((Object)("Adding welcome page: " + welcomePage));
            this.setRootViewName("forward:index.html");
        } else if (this.welcomeTemplateExists(templateAvailabilityProviders, applicationContext)) {
            logger.info((Object)"Adding welcome page template: index");
            this.setRootViewName("index");
        }
    }

    private boolean welcomeTemplateExists(TemplateAvailabilityProviders templateAvailabilityProviders, ApplicationContext applicationContext) {
        return templateAvailabilityProviders.getProvider("index", applicationContext) != null;
    }

    private void setRootViewName(String viewName) {
        ParameterizableViewController controller = new ParameterizableViewController();
        controller.setViewName(viewName);
        this.setRootHandler(controller);
        this.setOrder(2);
    }

    public Object getHandlerInternal(HttpServletRequest request) throws Exception {
        for (MediaType mediaType : this.getAcceptedMediaTypes(request)) {
            if (!mediaType.includes(MediaType.TEXT_HTML)) continue;
            return super.getHandlerInternal(request);
        }
        return null;
    }

    private List<MediaType> getAcceptedMediaTypes(HttpServletRequest request) {
        String acceptHeader = request.getHeader("Accept");
        if (StringUtils.hasText((String)acceptHeader)) {
            return MediaType.parseMediaTypes((String)acceptHeader);
        }
        return MEDIA_TYPES_ALL;
    }
}

