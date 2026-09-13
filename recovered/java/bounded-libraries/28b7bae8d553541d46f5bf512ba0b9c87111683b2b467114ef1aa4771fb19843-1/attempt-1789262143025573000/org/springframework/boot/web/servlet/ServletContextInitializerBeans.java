/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.Filter
 *  javax.servlet.MultipartConfigElement
 *  javax.servlet.Servlet
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.aop.scope.ScopedProxyUtils
 *  org.springframework.beans.factory.ListableBeanFactory
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.core.annotation.AnnotationAwareOrderComparator
 *  org.springframework.util.LinkedMultiValueMap
 *  org.springframework.util.MultiValueMap
 */
package org.springframework.boot.web.servlet;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EventListener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.Servlet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.web.servlet.DelegatingFilterProxyRegistrationBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.RegistrationBean;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class ServletContextInitializerBeans
extends AbstractCollection<ServletContextInitializer> {
    private static final String DISPATCHER_SERVLET_NAME = "dispatcherServlet";
    private static final Log logger = LogFactory.getLog(ServletContextInitializerBeans.class);
    private final Set<Object> seen = new HashSet<Object>();
    private final MultiValueMap<Class<?>, ServletContextInitializer> initializers = new LinkedMultiValueMap();
    private final List<Class<? extends ServletContextInitializer>> initializerTypes;
    private List<ServletContextInitializer> sortedList;

    @SafeVarargs
    public ServletContextInitializerBeans(ListableBeanFactory beanFactory, Class<? extends ServletContextInitializer> ... initializerTypes) {
        this.initializerTypes = initializerTypes.length != 0 ? Arrays.asList(initializerTypes) : Collections.singletonList(ServletContextInitializer.class);
        this.addServletContextInitializerBeans(beanFactory);
        this.addAdaptableBeans(beanFactory);
        List sortedInitializers = this.initializers.values().stream().flatMap(value -> value.stream().sorted(AnnotationAwareOrderComparator.INSTANCE)).collect(Collectors.toList());
        this.sortedList = Collections.unmodifiableList(sortedInitializers);
        this.logMappings(this.initializers);
    }

    private void addServletContextInitializerBeans(ListableBeanFactory beanFactory) {
        for (Class<? extends ServletContextInitializer> initializerType : this.initializerTypes) {
            for (Map.Entry<String, ? extends ServletContextInitializer> initializerBean : this.getOrderedBeansOfType(beanFactory, initializerType)) {
                this.addServletContextInitializerBean(initializerBean.getKey(), initializerBean.getValue(), beanFactory);
            }
        }
    }

    private void addServletContextInitializerBean(String beanName, ServletContextInitializer initializer, ListableBeanFactory beanFactory) {
        if (initializer instanceof ServletRegistrationBean) {
            Object source = ((ServletRegistrationBean)initializer).getServlet();
            this.addServletContextInitializerBean(Servlet.class, beanName, initializer, beanFactory, source);
        } else if (initializer instanceof FilterRegistrationBean) {
            Object source = ((FilterRegistrationBean)initializer).getFilter();
            this.addServletContextInitializerBean(Filter.class, beanName, initializer, beanFactory, source);
        } else if (initializer instanceof DelegatingFilterProxyRegistrationBean) {
            String source = ((DelegatingFilterProxyRegistrationBean)initializer).getTargetBeanName();
            this.addServletContextInitializerBean(Filter.class, beanName, initializer, beanFactory, source);
        } else if (initializer instanceof ServletListenerRegistrationBean) {
            Object source = ((ServletListenerRegistrationBean)initializer).getListener();
            this.addServletContextInitializerBean(EventListener.class, beanName, initializer, beanFactory, source);
        } else {
            this.addServletContextInitializerBean(ServletContextInitializer.class, beanName, initializer, beanFactory, initializer);
        }
    }

    private void addServletContextInitializerBean(Class<?> type, String beanName, ServletContextInitializer initializer, ListableBeanFactory beanFactory, Object source) {
        this.initializers.add(type, (Object)initializer);
        if (source != null) {
            this.seen.add(source);
        }
        if (logger.isTraceEnabled()) {
            String resourceDescription = this.getResourceDescription(beanName, beanFactory);
            int order = this.getOrder(initializer);
            logger.trace((Object)("Added existing " + type.getSimpleName() + " initializer bean '" + beanName + "'; order=" + order + ", resource=" + resourceDescription));
        }
    }

    private String getResourceDescription(String beanName, ListableBeanFactory beanFactory) {
        if (beanFactory instanceof BeanDefinitionRegistry) {
            BeanDefinitionRegistry registry = (BeanDefinitionRegistry)beanFactory;
            return registry.getBeanDefinition(beanName).getResourceDescription();
        }
        return "unknown";
    }

    protected void addAdaptableBeans(ListableBeanFactory beanFactory) {
        MultipartConfigElement multipartConfig = this.getMultipartConfig(beanFactory);
        this.addAsRegistrationBean(beanFactory, Servlet.class, new ServletRegistrationBeanAdapter(multipartConfig));
        this.addAsRegistrationBean(beanFactory, Filter.class, new FilterRegistrationBeanAdapter());
        for (Class<?> listenerType : ServletListenerRegistrationBean.getSupportedTypes()) {
            this.addAsRegistrationBean(beanFactory, EventListener.class, listenerType, new ServletListenerRegistrationBeanAdapter());
        }
    }

    private MultipartConfigElement getMultipartConfig(ListableBeanFactory beanFactory) {
        List<Map.Entry<String, MultipartConfigElement>> beans = this.getOrderedBeansOfType(beanFactory, MultipartConfigElement.class);
        return beans.isEmpty() ? null : beans.get(0).getValue();
    }

    protected <T> void addAsRegistrationBean(ListableBeanFactory beanFactory, Class<T> type, RegistrationBeanAdapter<T> adapter) {
        this.addAsRegistrationBean(beanFactory, type, type, adapter);
    }

    private <T, B extends T> void addAsRegistrationBean(ListableBeanFactory beanFactory, Class<T> type, Class<B> beanType, RegistrationBeanAdapter<T> adapter) {
        List<Map.Entry<String, B>> entries = this.getOrderedBeansOfType(beanFactory, beanType, this.seen);
        for (Map.Entry<String, B> entry : entries) {
            String beanName = entry.getKey();
            B bean = entry.getValue();
            if (!this.seen.add(bean)) continue;
            RegistrationBean registration = adapter.createRegistrationBean(beanName, bean, entries.size());
            int order = this.getOrder(bean);
            registration.setOrder(order);
            this.initializers.add(type, (Object)registration);
            if (!logger.isTraceEnabled()) continue;
            logger.trace((Object)("Created " + type.getSimpleName() + " initializer for bean '" + beanName + "'; order=" + order + ", resource=" + this.getResourceDescription(beanName, beanFactory)));
        }
    }

    private int getOrder(Object value) {
        return new AnnotationAwareOrderComparator(){

            public int getOrder(Object obj) {
                return super.getOrder(obj);
            }
        }.getOrder(value);
    }

    private <T> List<Map.Entry<String, T>> getOrderedBeansOfType(ListableBeanFactory beanFactory, Class<T> type) {
        return this.getOrderedBeansOfType(beanFactory, type, Collections.emptySet());
    }

    private <T> List<Map.Entry<String, T>> getOrderedBeansOfType(ListableBeanFactory beanFactory, Class<T> type, Set<?> excludes) {
        String[] names = beanFactory.getBeanNamesForType(type, true, false);
        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
        for (String name : names) {
            Object bean;
            if (excludes.contains(name) || ScopedProxyUtils.isScopedTarget((String)name) || excludes.contains(bean = beanFactory.getBean(name, type))) continue;
            map.put(name, bean);
        }
        ArrayList<Map.Entry<String, T>> beans = new ArrayList<Map.Entry<String, T>>(map.entrySet());
        beans.sort((o1, o2) -> AnnotationAwareOrderComparator.INSTANCE.compare(o1.getValue(), o2.getValue()));
        return beans;
    }

    private void logMappings(MultiValueMap<Class<?>, ServletContextInitializer> initializers) {
        if (logger.isDebugEnabled()) {
            this.logMappings("filters", initializers, Filter.class, FilterRegistrationBean.class);
            this.logMappings("servlets", initializers, Servlet.class, ServletRegistrationBean.class);
        }
    }

    private void logMappings(String name, MultiValueMap<Class<?>, ServletContextInitializer> initializers, Class<?> type, Class<? extends RegistrationBean> registrationType) {
        ArrayList registrations = new ArrayList();
        registrations.addAll((Collection)initializers.getOrDefault(registrationType, Collections.emptyList()));
        registrations.addAll((Collection)initializers.getOrDefault(type, Collections.emptyList()));
        String info = registrations.stream().map(Object::toString).collect(Collectors.joining(", "));
        logger.debug((Object)("Mapping " + name + ": " + info));
    }

    @Override
    public Iterator<ServletContextInitializer> iterator() {
        return this.sortedList.iterator();
    }

    @Override
    public int size() {
        return this.sortedList.size();
    }

    private static class ServletListenerRegistrationBeanAdapter
    implements RegistrationBeanAdapter<EventListener> {
        private ServletListenerRegistrationBeanAdapter() {
        }

        @Override
        public RegistrationBean createRegistrationBean(String name, EventListener source, int totalNumberOfSourceBeans) {
            return new ServletListenerRegistrationBean<EventListener>(source);
        }
    }

    private static class FilterRegistrationBeanAdapter
    implements RegistrationBeanAdapter<Filter> {
        private FilterRegistrationBeanAdapter() {
        }

        @Override
        public RegistrationBean createRegistrationBean(String name, Filter source, int totalNumberOfSourceBeans) {
            FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<Filter>(source, new ServletRegistrationBean[0]);
            bean.setName(name);
            return bean;
        }
    }

    private static class ServletRegistrationBeanAdapter
    implements RegistrationBeanAdapter<Servlet> {
        private final MultipartConfigElement multipartConfig;

        ServletRegistrationBeanAdapter(MultipartConfigElement multipartConfig) {
            this.multipartConfig = multipartConfig;
        }

        @Override
        public RegistrationBean createRegistrationBean(String name, Servlet source, int totalNumberOfSourceBeans) {
            String url;
            String string = url = totalNumberOfSourceBeans != 1 ? "/" + name + "/" : "/";
            if (name.equals(ServletContextInitializerBeans.DISPATCHER_SERVLET_NAME)) {
                url = "/";
            }
            ServletRegistrationBean<Servlet> bean = new ServletRegistrationBean<Servlet>(source, url);
            bean.setName(name);
            bean.setMultipartConfig(this.multipartConfig);
            return bean;
        }
    }

    @FunctionalInterface
    protected static interface RegistrationBeanAdapter<T> {
        public RegistrationBean createRegistrationBean(String var1, T var2, int var3);
    }
}

