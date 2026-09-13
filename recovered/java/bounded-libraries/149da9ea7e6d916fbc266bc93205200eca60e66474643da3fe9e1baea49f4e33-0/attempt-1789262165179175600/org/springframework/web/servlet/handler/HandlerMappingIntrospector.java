/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletRequest
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletRequestWrapper
 *  org.springframework.beans.factory.BeanFactoryUtils
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.beans.factory.ListableBeanFactory
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ApplicationContextAware
 *  org.springframework.core.annotation.AnnotationAwareOrderComparator
 *  org.springframework.core.io.ClassPathResource
 *  org.springframework.core.io.Resource
 *  org.springframework.core.io.support.PropertiesLoaderUtils
 *  org.springframework.http.server.RequestPath
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.StringUtils
 *  org.springframework.web.cors.CorsConfiguration
 *  org.springframework.web.cors.CorsConfigurationSource
 *  org.springframework.web.util.ServletRequestPathUtils
 *  org.springframework.web.util.UrlPathHelper
 */
package org.springframework.web.servlet.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.http.server.RequestPath;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.MatchableHandlerMapping;
import org.springframework.web.servlet.handler.PathPatternMatchableHandlerMapping;
import org.springframework.web.servlet.handler.RequestMatchResult;
import org.springframework.web.util.ServletRequestPathUtils;
import org.springframework.web.util.UrlPathHelper;

public class HandlerMappingIntrospector
implements CorsConfigurationSource,
ApplicationContextAware,
InitializingBean {
    @Nullable
    private ApplicationContext applicationContext;
    @Nullable
    private List<HandlerMapping> handlerMappings;
    private Map<HandlerMapping, PathPatternMatchableHandlerMapping> pathPatternHandlerMappings = Collections.emptyMap();

    public HandlerMappingIntrospector() {
    }

    @Deprecated
    public HandlerMappingIntrospector(ApplicationContext context) {
        this.handlerMappings = HandlerMappingIntrospector.initHandlerMappings(context);
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void afterPropertiesSet() {
        if (this.handlerMappings == null) {
            Assert.notNull((Object)this.applicationContext, (String)"No ApplicationContext");
            this.handlerMappings = HandlerMappingIntrospector.initHandlerMappings(this.applicationContext);
            this.pathPatternHandlerMappings = HandlerMappingIntrospector.initPathPatternMatchableHandlerMappings(this.handlerMappings);
        }
    }

    public List<HandlerMapping> getHandlerMappings() {
        return this.handlerMappings != null ? this.handlerMappings : Collections.emptyList();
    }

    @Nullable
    public MatchableHandlerMapping getMatchableHandlerMapping(HttpServletRequest request) throws Exception {
        AttributesPreservingRequest wrappedRequest = new AttributesPreservingRequest(request);
        return this.doWithMatchingMapping((HttpServletRequest)wrappedRequest, false, (arg_0, arg_1) -> this.lambda$getMatchableHandlerMapping$0((HttpServletRequest)wrappedRequest, arg_0, arg_1));
    }

    @Nullable
    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
        AttributesPreservingRequest wrappedRequest = new AttributesPreservingRequest(request);
        return this.doWithMatchingMappingIgnoringException((HttpServletRequest)wrappedRequest, (handlerMapping, executionChain) -> {
            for (HandlerInterceptor interceptor : executionChain.getInterceptorList()) {
                if (!(interceptor instanceof CorsConfigurationSource)) continue;
                return ((CorsConfigurationSource)interceptor).getCorsConfiguration((HttpServletRequest)wrappedRequest);
            }
            if (executionChain.getHandler() instanceof CorsConfigurationSource) {
                return ((CorsConfigurationSource)executionChain.getHandler()).getCorsConfiguration((HttpServletRequest)wrappedRequest);
            }
            return null;
        });
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    private <T> T doWithMatchingMapping(HttpServletRequest request, boolean ignoreException, BiFunction<HandlerMapping, HandlerExecutionChain, T> matchHandler) throws Exception {
        Assert.notNull(this.handlerMappings, (String)"Handler mappings not initialized");
        boolean parseRequestPath = !this.pathPatternHandlerMappings.isEmpty();
        RequestPath previousPath = null;
        if (parseRequestPath) {
            previousPath = (RequestPath)request.getAttribute(ServletRequestPathUtils.PATH_ATTRIBUTE);
            ServletRequestPathUtils.parseAndCache((HttpServletRequest)request);
        }
        try {
            for (HandlerMapping handlerMapping : this.handlerMappings) {
                HandlerExecutionChain chain;
                block8: {
                    chain = null;
                    try {
                        chain = handlerMapping.getHandler(request);
                    }
                    catch (Exception ex) {
                        if (ignoreException) break block8;
                        throw ex;
                    }
                }
                if (chain == null) continue;
                T t = matchHandler.apply(handlerMapping, chain);
                return t;
            }
        }
        finally {
            if (parseRequestPath) {
                ServletRequestPathUtils.setParsedRequestPath((RequestPath)previousPath, (ServletRequest)request);
            }
        }
        return null;
    }

    @Nullable
    private <T> T doWithMatchingMappingIgnoringException(HttpServletRequest request, BiFunction<HandlerMapping, HandlerExecutionChain, T> matchHandler) {
        try {
            return this.doWithMatchingMapping(request, true, matchHandler);
        }
        catch (Exception ex) {
            throw new IllegalStateException("HandlerMapping exception not suppressed", ex);
        }
    }

    private static List<HandlerMapping> initHandlerMappings(ApplicationContext applicationContext) {
        Map beans = BeanFactoryUtils.beansOfTypeIncludingAncestors((ListableBeanFactory)applicationContext, HandlerMapping.class, (boolean)true, (boolean)false);
        if (!beans.isEmpty()) {
            ArrayList mappings = new ArrayList(beans.values());
            AnnotationAwareOrderComparator.sort(mappings);
            return Collections.unmodifiableList(mappings);
        }
        return Collections.unmodifiableList(HandlerMappingIntrospector.initFallback(applicationContext));
    }

    private static List<HandlerMapping> initFallback(ApplicationContext applicationContext) {
        Properties props;
        String path = "DispatcherServlet.properties";
        try {
            ClassPathResource resource = new ClassPathResource(path, DispatcherServlet.class);
            props = PropertiesLoaderUtils.loadProperties((Resource)resource);
        }
        catch (IOException ex) {
            throw new IllegalStateException("Could not load '" + path + "': " + ex.getMessage());
        }
        String value = props.getProperty(HandlerMapping.class.getName());
        String[] names = StringUtils.commaDelimitedListToStringArray((String)value);
        ArrayList<HandlerMapping> result = new ArrayList<HandlerMapping>(names.length);
        for (String name : names) {
            try {
                Class clazz = ClassUtils.forName((String)name, (ClassLoader)DispatcherServlet.class.getClassLoader());
                Object mapping = applicationContext.getAutowireCapableBeanFactory().createBean(clazz);
                result.add((HandlerMapping)mapping);
            }
            catch (ClassNotFoundException ex) {
                throw new IllegalStateException("Could not find default HandlerMapping [" + name + "]");
            }
        }
        return result;
    }

    private static Map<HandlerMapping, PathPatternMatchableHandlerMapping> initPathPatternMatchableHandlerMappings(List<HandlerMapping> mappings) {
        return mappings.stream().filter(mapping -> mapping instanceof MatchableHandlerMapping).map(mapping -> (MatchableHandlerMapping)mapping).filter(mapping -> mapping.getPatternParser() != null).collect(Collectors.toMap(mapping -> mapping, PathPatternMatchableHandlerMapping::new));
    }

    private /* synthetic */ PathSettingHandlerMapping lambda$getMatchableHandlerMapping$0(HttpServletRequest wrappedRequest, HandlerMapping matchedMapping, HandlerExecutionChain executionChain) {
        if (matchedMapping instanceof MatchableHandlerMapping) {
            PathPatternMatchableHandlerMapping mapping = this.pathPatternHandlerMappings.get(matchedMapping);
            if (mapping != null) {
                RequestPath requestPath = ServletRequestPathUtils.getParsedRequestPath((ServletRequest)wrappedRequest);
                return new PathSettingHandlerMapping(mapping, requestPath);
            }
            String lookupPath = (String)wrappedRequest.getAttribute(UrlPathHelper.PATH_ATTRIBUTE);
            return new PathSettingHandlerMapping((MatchableHandlerMapping)matchedMapping, lookupPath);
        }
        throw new IllegalStateException("HandlerMapping is not a MatchableHandlerMapping");
    }

    private static class PathSettingHandlerMapping
    implements MatchableHandlerMapping {
        private final MatchableHandlerMapping delegate;
        private final Object path;
        private final String pathAttributeName;

        PathSettingHandlerMapping(MatchableHandlerMapping delegate, Object path) {
            this.delegate = delegate;
            this.path = path;
            this.pathAttributeName = path instanceof RequestPath ? ServletRequestPathUtils.PATH_ATTRIBUTE : UrlPathHelper.PATH_ATTRIBUTE;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Nullable
        public RequestMatchResult match(HttpServletRequest request, String pattern) {
            Object previousPath = request.getAttribute(this.pathAttributeName);
            request.setAttribute(this.pathAttributeName, this.path);
            try {
                RequestMatchResult requestMatchResult = this.delegate.match(request, pattern);
                return requestMatchResult;
            }
            finally {
                request.setAttribute(this.pathAttributeName, previousPath);
            }
        }

        @Override
        @Nullable
        public HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
            return this.delegate.getHandler(request);
        }
    }

    private static class AttributesPreservingRequest
    extends HttpServletRequestWrapper {
        private final Map<String, Object> attributes;

        AttributesPreservingRequest(HttpServletRequest request) {
            super(request);
            this.attributes = this.initAttributes(request);
        }

        private Map<String, Object> initAttributes(HttpServletRequest request) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            Enumeration names = request.getAttributeNames();
            while (names.hasMoreElements()) {
                String name = (String)names.nextElement();
                map.put(name, request.getAttribute(name));
            }
            return map;
        }

        public void setAttribute(String name, Object value) {
            this.attributes.put(name, value);
        }

        public Object getAttribute(String name) {
            return this.attributes.get(name);
        }

        public Enumeration<String> getAttributeNames() {
            return Collections.enumeration(this.attributes.keySet());
        }

        public void removeAttribute(String name) {
            this.attributes.remove(name);
        }
    }
}

