/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.context.ApplicationContext
 *  org.springframework.core.Ordered
 *  org.springframework.core.io.Resource
 *  org.springframework.http.HttpStatus
 *  org.springframework.http.HttpStatus$Series
 *  org.springframework.util.Assert
 *  org.springframework.util.FileCopyUtils
 *  org.springframework.web.servlet.ModelAndView
 *  org.springframework.web.servlet.View
 */
package org.springframework.boot.autoconfigure.web.servlet.error;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.boot.autoconfigure.template.TemplateAvailabilityProvider;
import org.springframework.boot.autoconfigure.template.TemplateAvailabilityProviders;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.context.ApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

public class DefaultErrorViewResolver
implements ErrorViewResolver,
Ordered {
    private static final Map<HttpStatus.Series, String> SERIES_VIEWS;
    private ApplicationContext applicationContext;
    private final WebProperties.Resources resources;
    private final TemplateAvailabilityProviders templateAvailabilityProviders;
    private int order = Integer.MAX_VALUE;

    public DefaultErrorViewResolver(ApplicationContext applicationContext, WebProperties.Resources resources) {
        Assert.notNull((Object)applicationContext, (String)"ApplicationContext must not be null");
        Assert.notNull((Object)resources, (String)"Resources must not be null");
        this.applicationContext = applicationContext;
        this.resources = resources;
        this.templateAvailabilityProviders = new TemplateAvailabilityProviders(applicationContext);
    }

    DefaultErrorViewResolver(ApplicationContext applicationContext, WebProperties.Resources resourceProperties, TemplateAvailabilityProviders templateAvailabilityProviders) {
        Assert.notNull((Object)applicationContext, (String)"ApplicationContext must not be null");
        Assert.notNull((Object)resourceProperties, (String)"Resources must not be null");
        this.applicationContext = applicationContext;
        this.resources = resourceProperties;
        this.templateAvailabilityProviders = templateAvailabilityProviders;
    }

    @Override
    public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> model) {
        ModelAndView modelAndView = this.resolve(String.valueOf(status.value()), model);
        if (modelAndView == null && SERIES_VIEWS.containsKey(status.series())) {
            modelAndView = this.resolve(SERIES_VIEWS.get(status.series()), model);
        }
        return modelAndView;
    }

    private ModelAndView resolve(String viewName, Map<String, Object> model) {
        String errorViewName = "error/" + viewName;
        TemplateAvailabilityProvider provider = this.templateAvailabilityProviders.getProvider(errorViewName, this.applicationContext);
        if (provider != null) {
            return new ModelAndView(errorViewName, model);
        }
        return this.resolveResource(errorViewName, model);
    }

    private ModelAndView resolveResource(String viewName, Map<String, Object> model) {
        for (String location : this.resources.getStaticLocations()) {
            try {
                Resource resource = this.applicationContext.getResource(location);
                resource = resource.createRelative(viewName + ".html");
                if (!resource.exists()) continue;
                return new ModelAndView((View)new HtmlResourceView(resource), model);
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return null;
    }

    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    static {
        EnumMap<HttpStatus.Series, String> views = new EnumMap<HttpStatus.Series, String>(HttpStatus.Series.class);
        views.put(HttpStatus.Series.CLIENT_ERROR, "4xx");
        views.put(HttpStatus.Series.SERVER_ERROR, "5xx");
        SERIES_VIEWS = Collections.unmodifiableMap(views);
    }

    private static class HtmlResourceView
    implements View {
        private Resource resource;

        HtmlResourceView(Resource resource) {
            this.resource = resource;
        }

        public String getContentType() {
            return "text/html";
        }

        public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
            response.setContentType(this.getContentType());
            FileCopyUtils.copy((InputStream)this.resource.getInputStream(), (OutputStream)response.getOutputStream());
        }
    }
}

