/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.Servlet
 *  org.springframework.boot.web.servlet.ServletRegistrationBean
 *  org.springframework.context.ApplicationContext
 *  org.springframework.web.context.WebApplicationContext
 *  org.springframework.web.method.HandlerMethod
 *  org.springframework.web.servlet.DispatcherServlet
 *  org.springframework.web.servlet.handler.AbstractUrlHandlerMapping
 *  org.springframework.web.servlet.mvc.method.RequestMappingInfo
 *  org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping
 */
package org.springframework.boot.actuate.web.mappings.servlet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.servlet.Servlet;
import org.springframework.boot.actuate.web.mappings.HandlerMethodDescription;
import org.springframework.boot.actuate.web.mappings.MappingDescriptionProvider;
import org.springframework.boot.actuate.web.mappings.servlet.DispatcherServletHandlerMappings;
import org.springframework.boot.actuate.web.mappings.servlet.DispatcherServletMappingDescription;
import org.springframework.boot.actuate.web.mappings.servlet.DispatcherServletMappingDetails;
import org.springframework.boot.actuate.web.mappings.servlet.RequestMappingConditionsDescription;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.handler.AbstractUrlHandlerMapping;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;

public class DispatcherServletsMappingDescriptionProvider
implements MappingDescriptionProvider {
    private static final List<HandlerMappingDescriptionProvider<?>> descriptionProviders;

    @Override
    public String getMappingName() {
        return "dispatcherServlets";
    }

    @Override
    public Map<String, List<DispatcherServletMappingDescription>> describeMappings(ApplicationContext context) {
        if (context instanceof WebApplicationContext) {
            return this.describeMappings((WebApplicationContext)context);
        }
        return Collections.emptyMap();
    }

    private Map<String, List<DispatcherServletMappingDescription>> describeMappings(WebApplicationContext context) {
        HashMap<String, List<DispatcherServletMappingDescription>> mappings = new HashMap<String, List<DispatcherServletMappingDescription>>();
        this.determineDispatcherServlets(context).forEach((name, dispatcherServlet) -> mappings.put((String)name, this.describeMappings(new DispatcherServletHandlerMappings((String)name, (DispatcherServlet)dispatcherServlet, context))));
        return mappings;
    }

    private Map<String, DispatcherServlet> determineDispatcherServlets(WebApplicationContext context) {
        LinkedHashMap<String, DispatcherServlet> dispatcherServlets = new LinkedHashMap<String, DispatcherServlet>();
        context.getBeansOfType(ServletRegistrationBean.class).values().forEach(registration -> {
            Servlet servlet = registration.getServlet();
            if (servlet instanceof DispatcherServlet && !dispatcherServlets.containsValue(servlet)) {
                dispatcherServlets.put(registration.getServletName(), (DispatcherServlet)servlet);
            }
        });
        context.getBeansOfType(DispatcherServlet.class).forEach((name, dispatcherServlet) -> {
            if (!dispatcherServlets.containsValue(dispatcherServlet)) {
                dispatcherServlets.put((String)name, (DispatcherServlet)dispatcherServlet);
            }
        });
        return dispatcherServlets;
    }

    private List<DispatcherServletMappingDescription> describeMappings(DispatcherServletHandlerMappings mappings) {
        return mappings.getHandlerMappings().stream().flatMap(this::describe).collect(Collectors.toList());
    }

    private <T> Stream<DispatcherServletMappingDescription> describe(T handlerMapping) {
        return DispatcherServletsMappingDescriptionProvider.describe(handlerMapping, descriptionProviders).stream();
    }

    private static <T> List<DispatcherServletMappingDescription> describe(T handlerMapping, List<HandlerMappingDescriptionProvider<?>> descriptionProviders) {
        for (HandlerMappingDescriptionProvider<?> descriptionProvider : descriptionProviders) {
            if (!descriptionProvider.getMappingClass().isInstance(handlerMapping)) continue;
            return descriptionProvider.describe(handlerMapping);
        }
        return Collections.emptyList();
    }

    static {
        ArrayList<HandlerMappingDescriptionProvider<RequestMappingInfoHandlerMapping>> providers = new ArrayList<HandlerMappingDescriptionProvider<RequestMappingInfoHandlerMapping>>();
        providers.add(new RequestMappingInfoHandlerMappingDescriptionProvider());
        providers.add(new UrlHandlerMappingDescriptionProvider());
        providers.add(new IterableDelegatesHandlerMappingDescriptionProvider(new ArrayList(providers)));
        descriptionProviders = Collections.unmodifiableList(providers);
    }

    private static final class IterableDelegatesHandlerMappingDescriptionProvider
    implements HandlerMappingDescriptionProvider<Iterable> {
        private final List<HandlerMappingDescriptionProvider<?>> descriptionProviders;

        private IterableDelegatesHandlerMappingDescriptionProvider(List<HandlerMappingDescriptionProvider<?>> descriptionProviders) {
            this.descriptionProviders = descriptionProviders;
        }

        @Override
        public Class<Iterable> getMappingClass() {
            return Iterable.class;
        }

        @Override
        public List<DispatcherServletMappingDescription> describe(Iterable handlerMapping) {
            ArrayList<DispatcherServletMappingDescription> descriptions = new ArrayList<DispatcherServletMappingDescription>();
            for (Object delegate : handlerMapping) {
                descriptions.addAll(DispatcherServletsMappingDescriptionProvider.describe(delegate, this.descriptionProviders));
            }
            return descriptions;
        }
    }

    private static final class UrlHandlerMappingDescriptionProvider
    implements HandlerMappingDescriptionProvider<AbstractUrlHandlerMapping> {
        private UrlHandlerMappingDescriptionProvider() {
        }

        @Override
        public Class<AbstractUrlHandlerMapping> getMappingClass() {
            return AbstractUrlHandlerMapping.class;
        }

        @Override
        public List<DispatcherServletMappingDescription> describe(AbstractUrlHandlerMapping handlerMapping) {
            return handlerMapping.getHandlerMap().entrySet().stream().map(this::describe).collect(Collectors.toList());
        }

        private DispatcherServletMappingDescription describe(Map.Entry<String, Object> mapping) {
            return new DispatcherServletMappingDescription(mapping.getKey(), mapping.getValue().toString(), null);
        }
    }

    private static final class RequestMappingInfoHandlerMappingDescriptionProvider
    implements HandlerMappingDescriptionProvider<RequestMappingInfoHandlerMapping> {
        private RequestMappingInfoHandlerMappingDescriptionProvider() {
        }

        @Override
        public Class<RequestMappingInfoHandlerMapping> getMappingClass() {
            return RequestMappingInfoHandlerMapping.class;
        }

        @Override
        public List<DispatcherServletMappingDescription> describe(RequestMappingInfoHandlerMapping handlerMapping) {
            Map handlerMethods = handlerMapping.getHandlerMethods();
            return handlerMethods.entrySet().stream().map(this::describe).collect(Collectors.toList());
        }

        private DispatcherServletMappingDescription describe(Map.Entry<RequestMappingInfo, HandlerMethod> mapping) {
            DispatcherServletMappingDetails mappingDetails = new DispatcherServletMappingDetails();
            mappingDetails.setHandlerMethod(new HandlerMethodDescription(mapping.getValue()));
            mappingDetails.setRequestMappingConditions(new RequestMappingConditionsDescription(mapping.getKey()));
            return new DispatcherServletMappingDescription(mapping.getKey().toString(), mapping.getValue().toString(), mappingDetails);
        }
    }

    private static interface HandlerMappingDescriptionProvider<T> {
        public Class<T> getMappingClass();

        public List<DispatcherServletMappingDescription> describe(T var1);
    }
}

