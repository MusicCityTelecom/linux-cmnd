/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.aop.scope.ScopedProxyUtils
 *  org.springframework.beans.BeanUtils
 *  org.springframework.beans.factory.BeanFactoryUtils
 *  org.springframework.beans.factory.ListableBeanFactory
 *  org.springframework.boot.util.LambdaSafe
 *  org.springframework.boot.util.LambdaSafe$Callback
 *  org.springframework.context.ApplicationContext
 *  org.springframework.core.ResolvableType
 *  org.springframework.core.annotation.MergedAnnotation
 *  org.springframework.core.annotation.MergedAnnotations
 *  org.springframework.core.annotation.MergedAnnotations$SearchStrategy
 *  org.springframework.core.env.Environment
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.CollectionUtils
 *  org.springframework.util.LinkedMultiValueMap
 *  org.springframework.util.MultiValueMap
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.actuate.endpoint.annotation;

import java.lang.reflect.AnnotatedElement;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.boot.actuate.endpoint.EndpointFilter;
import org.springframework.boot.actuate.endpoint.EndpointId;
import org.springframework.boot.actuate.endpoint.EndpointsSupplier;
import org.springframework.boot.actuate.endpoint.ExposableEndpoint;
import org.springframework.boot.actuate.endpoint.Operation;
import org.springframework.boot.actuate.endpoint.annotation.DiscoveredOperationMethod;
import org.springframework.boot.actuate.endpoint.annotation.DiscoveredOperationsFactory;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.EndpointExtension;
import org.springframework.boot.actuate.endpoint.annotation.FilteredEndpoint;
import org.springframework.boot.actuate.endpoint.invoke.OperationInvoker;
import org.springframework.boot.actuate.endpoint.invoke.OperationInvokerAdvisor;
import org.springframework.boot.actuate.endpoint.invoke.ParameterValueMapper;
import org.springframework.boot.util.LambdaSafe;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ResolvableType;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

public abstract class EndpointDiscoverer<E extends ExposableEndpoint<O>, O extends Operation>
implements EndpointsSupplier<E> {
    private final ApplicationContext applicationContext;
    private final Collection<EndpointFilter<E>> filters;
    private final DiscoveredOperationsFactory<O> operationsFactory;
    private final Map<EndpointBean, E> filterEndpoints = new ConcurrentHashMap<EndpointBean, E>();
    private volatile Collection<E> endpoints;

    public EndpointDiscoverer(ApplicationContext applicationContext, ParameterValueMapper parameterValueMapper, Collection<OperationInvokerAdvisor> invokerAdvisors, Collection<EndpointFilter<E>> filters) {
        Assert.notNull((Object)applicationContext, (String)"ApplicationContext must not be null");
        Assert.notNull((Object)parameterValueMapper, (String)"ParameterValueMapper must not be null");
        Assert.notNull(invokerAdvisors, (String)"InvokerAdvisors must not be null");
        Assert.notNull(filters, (String)"Filters must not be null");
        this.applicationContext = applicationContext;
        this.filters = Collections.unmodifiableCollection(filters);
        this.operationsFactory = this.getOperationsFactory(parameterValueMapper, invokerAdvisors);
    }

    private DiscoveredOperationsFactory<O> getOperationsFactory(ParameterValueMapper parameterValueMapper, Collection<OperationInvokerAdvisor> invokerAdvisors) {
        return new DiscoveredOperationsFactory<O>(parameterValueMapper, invokerAdvisors){

            @Override
            protected O createOperation(EndpointId endpointId, DiscoveredOperationMethod operationMethod, OperationInvoker invoker) {
                return EndpointDiscoverer.this.createOperation(endpointId, operationMethod, invoker);
            }
        };
    }

    @Override
    public final Collection<E> getEndpoints() {
        if (this.endpoints == null) {
            this.endpoints = this.discoverEndpoints();
        }
        return this.endpoints;
    }

    private Collection<E> discoverEndpoints() {
        Collection<EndpointBean> endpointBeans = this.createEndpointBeans();
        this.addExtensionBeans(endpointBeans);
        return this.convertToEndpoints(endpointBeans);
    }

    private Collection<EndpointBean> createEndpointBeans() {
        String[] beanNames;
        LinkedHashMap<EndpointId, EndpointBean> byId = new LinkedHashMap<EndpointId, EndpointBean>();
        for (String beanName : beanNames = BeanFactoryUtils.beanNamesForAnnotationIncludingAncestors((ListableBeanFactory)this.applicationContext, Endpoint.class)) {
            if (ScopedProxyUtils.isScopedTarget((String)beanName)) continue;
            EndpointBean endpointBean = this.createEndpointBean(beanName);
            EndpointBean previous = byId.putIfAbsent(endpointBean.getId(), endpointBean);
            Assert.state((previous == null ? 1 : 0) != 0, () -> "Found two endpoints with the id '" + endpointBean.getId() + "': '" + endpointBean.getBeanName() + "' and '" + previous.getBeanName() + "'");
        }
        return byId.values();
    }

    private EndpointBean createEndpointBean(String beanName) {
        Class beanType = ClassUtils.getUserClass((Class)this.applicationContext.getType(beanName, false));
        Supplier<Object> beanSupplier = () -> this.applicationContext.getBean(beanName);
        return new EndpointBean(this.applicationContext.getEnvironment(), beanName, beanType, beanSupplier);
    }

    private void addExtensionBeans(Collection<EndpointBean> endpointBeans) {
        String[] beanNames;
        Map byId = endpointBeans.stream().collect(Collectors.toMap(EndpointBean::getId, Function.identity()));
        for (String beanName : beanNames = BeanFactoryUtils.beanNamesForAnnotationIncludingAncestors((ListableBeanFactory)this.applicationContext, EndpointExtension.class)) {
            ExtensionBean extensionBean = this.createExtensionBean(beanName);
            EndpointBean endpointBean = (EndpointBean)byId.get(extensionBean.getEndpointId());
            Assert.state((endpointBean != null ? 1 : 0) != 0, () -> "Invalid extension '" + extensionBean.getBeanName() + "': no endpoint found with id '" + extensionBean.getEndpointId() + "'");
            this.addExtensionBean(endpointBean, extensionBean);
        }
    }

    private ExtensionBean createExtensionBean(String beanName) {
        Class beanType = ClassUtils.getUserClass((Class)this.applicationContext.getType(beanName));
        Supplier<Object> beanSupplier = () -> this.applicationContext.getBean(beanName);
        return new ExtensionBean(this.applicationContext.getEnvironment(), beanName, beanType, beanSupplier);
    }

    private void addExtensionBean(EndpointBean endpointBean, ExtensionBean extensionBean) {
        if (this.isExtensionExposed(endpointBean, extensionBean)) {
            Assert.state((this.isEndpointExposed(endpointBean) || this.isEndpointFiltered(endpointBean) ? 1 : 0) != 0, () -> "Endpoint bean '" + endpointBean.getBeanName() + "' cannot support the extension bean '" + extensionBean.getBeanName() + "'");
            endpointBean.addExtension(extensionBean);
        }
    }

    private Collection<E> convertToEndpoints(Collection<EndpointBean> endpointBeans) {
        LinkedHashSet<E> endpoints = new LinkedHashSet<E>();
        for (EndpointBean endpointBean : endpointBeans) {
            if (!this.isEndpointExposed(endpointBean)) continue;
            endpoints.add(this.convertToEndpoint(endpointBean));
        }
        return Collections.unmodifiableSet(endpoints);
    }

    private E convertToEndpoint(EndpointBean endpointBean) {
        LinkedMultiValueMap indexed = new LinkedMultiValueMap();
        EndpointId id = endpointBean.getId();
        this.addOperations((MultiValueMap<OperationKey, O>)indexed, id, endpointBean.getBean(), false);
        if (endpointBean.getExtensions().size() > 1) {
            String extensionBeans = endpointBean.getExtensions().stream().map(ExtensionBean::getBeanName).collect(Collectors.joining(", "));
            throw new IllegalStateException("Found multiple extensions for the endpoint bean " + endpointBean.getBeanName() + " (" + extensionBeans + ")");
        }
        for (ExtensionBean extensionBean : endpointBean.getExtensions()) {
            this.addOperations((MultiValueMap<OperationKey, O>)indexed, id, extensionBean.getBean(), true);
        }
        this.assertNoDuplicateOperations(endpointBean, (MultiValueMap<OperationKey, O>)indexed);
        List operations = indexed.values().stream().map(this::getLast).filter(Objects::nonNull).collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
        return this.createEndpoint(endpointBean.getBean(), id, endpointBean.isEnabledByDefault(), operations);
    }

    private void addOperations(MultiValueMap<OperationKey, O> indexed, EndpointId id, Object target, boolean replaceLast) {
        HashSet<OperationKey> replacedLast = new HashSet<OperationKey>();
        Collection<O> operations = this.operationsFactory.createOperations(id, target);
        for (Operation operation : operations) {
            OperationKey key = this.createOperationKey(operation);
            Operation last = (Operation)this.getLast((List)indexed.get((Object)key));
            if (replaceLast && replacedLast.add(key) && last != null) {
                ((List)indexed.get((Object)key)).remove(last);
            }
            indexed.add((Object)key, (Object)operation);
        }
    }

    private <T> T getLast(List<T> list) {
        return CollectionUtils.isEmpty(list) ? null : (T)list.get(list.size() - 1);
    }

    private void assertNoDuplicateOperations(EndpointBean endpointBean, MultiValueMap<OperationKey, O> indexed) {
        List duplicates = indexed.entrySet().stream().filter(entry -> ((List)entry.getValue()).size() > 1).map(Map.Entry::getKey).collect(Collectors.toList());
        if (!duplicates.isEmpty()) {
            Set<ExtensionBean> extensions = endpointBean.getExtensions();
            String extensionBeanNames = extensions.stream().map(ExtensionBean::getBeanName).collect(Collectors.joining(", "));
            throw new IllegalStateException("Unable to map duplicate endpoint operations: " + duplicates.toString() + " to " + endpointBean.getBeanName() + (extensions.isEmpty() ? "" : " (" + extensionBeanNames + ")"));
        }
    }

    private boolean isExtensionExposed(EndpointBean endpointBean, ExtensionBean extensionBean) {
        return this.isFilterMatch(extensionBean.getFilter(), endpointBean) && this.isExtensionTypeExposed(extensionBean.getBeanType());
    }

    protected boolean isExtensionTypeExposed(Class<?> extensionBeanType) {
        return true;
    }

    private boolean isEndpointExposed(EndpointBean endpointBean) {
        return this.isFilterMatch(endpointBean.getFilter(), endpointBean) && !this.isEndpointFiltered(endpointBean) && this.isEndpointTypeExposed(endpointBean.getBeanType());
    }

    protected boolean isEndpointTypeExposed(Class<?> beanType) {
        return true;
    }

    private boolean isEndpointFiltered(EndpointBean endpointBean) {
        for (EndpointFilter<E> filter : this.filters) {
            if (this.isFilterMatch(filter, (E)endpointBean)) continue;
            return true;
        }
        return false;
    }

    private boolean isFilterMatch(Class<?> filter, EndpointBean endpointBean) {
        if (!this.isEndpointTypeExposed(endpointBean.getBeanType())) {
            return false;
        }
        if (filter == null) {
            return true;
        }
        E endpoint = this.getFilterEndpoint(endpointBean);
        Class generic = ResolvableType.forClass(EndpointFilter.class, filter).resolveGeneric(new int[]{0});
        if (generic == null || generic.isInstance(endpoint)) {
            EndpointFilter instance = (EndpointFilter)BeanUtils.instantiateClass(filter);
            return this.isFilterMatch(instance, endpoint);
        }
        return false;
    }

    private boolean isFilterMatch(EndpointFilter<E> filter, EndpointBean endpointBean) {
        return this.isFilterMatch(filter, this.getFilterEndpoint(endpointBean));
    }

    private boolean isFilterMatch(EndpointFilter<E> filter, E endpoint) {
        return (Boolean)((LambdaSafe.Callback)LambdaSafe.callback(EndpointFilter.class, filter, endpoint, (Object[])new Object[0]).withLogger(EndpointDiscoverer.class)).invokeAnd(f -> f.match(endpoint)).get();
    }

    private E getFilterEndpoint(EndpointBean endpointBean) {
        ExposableEndpoint<Object> endpoint = (ExposableEndpoint)this.filterEndpoints.get(endpointBean);
        if (endpoint == null) {
            endpoint = this.createEndpoint(endpointBean.getBean(), endpointBean.getId(), endpointBean.isEnabledByDefault(), Collections.emptySet());
            this.filterEndpoints.put(endpointBean, endpoint);
        }
        return (E)endpoint;
    }

    protected Class<? extends E> getEndpointType() {
        return ResolvableType.forClass(EndpointDiscoverer.class, this.getClass()).resolveGeneric(new int[]{0});
    }

    protected abstract E createEndpoint(Object var1, EndpointId var2, boolean var3, Collection<O> var4);

    protected abstract O createOperation(EndpointId var1, DiscoveredOperationMethod var2, OperationInvoker var3);

    protected abstract OperationKey createOperationKey(O var1);

    private static class ExtensionBean {
        private final String beanName;
        private final Class<?> beanType;
        private final Supplier<Object> beanSupplier;
        private final EndpointId endpointId;
        private final Class<?> filter;

        ExtensionBean(Environment environment, String beanName, Class<?> beanType, Supplier<Object> beanSupplier) {
            this.beanName = beanName;
            this.beanType = beanType;
            this.beanSupplier = beanSupplier;
            MergedAnnotation extensionAnnotation = MergedAnnotations.from(beanType, (MergedAnnotations.SearchStrategy)MergedAnnotations.SearchStrategy.TYPE_HIERARCHY).get(EndpointExtension.class);
            Class endpointType = extensionAnnotation.getClass("endpoint");
            MergedAnnotation endpointAnnotation = MergedAnnotations.from((AnnotatedElement)endpointType, (MergedAnnotations.SearchStrategy)MergedAnnotations.SearchStrategy.TYPE_HIERARCHY).get(Endpoint.class);
            Assert.state((boolean)endpointAnnotation.isPresent(), () -> "Extension " + endpointType.getName() + " does not specify an endpoint");
            this.endpointId = EndpointId.of(environment, endpointAnnotation.getString("id"));
            this.filter = extensionAnnotation.getClass("filter");
        }

        String getBeanName() {
            return this.beanName;
        }

        Class<?> getBeanType() {
            return this.beanType;
        }

        Object getBean() {
            return this.beanSupplier.get();
        }

        EndpointId getEndpointId() {
            return this.endpointId;
        }

        Class<?> getFilter() {
            return this.filter;
        }
    }

    private static class EndpointBean {
        private final String beanName;
        private final Class<?> beanType;
        private final Supplier<Object> beanSupplier;
        private final EndpointId id;
        private boolean enabledByDefault;
        private final Class<?> filter;
        private Set<ExtensionBean> extensions = new LinkedHashSet<ExtensionBean>();

        EndpointBean(Environment environment, String beanName, Class<?> beanType, Supplier<Object> beanSupplier) {
            MergedAnnotation annotation = MergedAnnotations.from(beanType, (MergedAnnotations.SearchStrategy)MergedAnnotations.SearchStrategy.TYPE_HIERARCHY).get(Endpoint.class);
            String id = annotation.getString("id");
            Assert.state((boolean)StringUtils.hasText((String)id), () -> "No @Endpoint id attribute specified for " + beanType.getName());
            this.beanName = beanName;
            this.beanType = beanType;
            this.beanSupplier = beanSupplier;
            this.id = EndpointId.of(environment, id);
            this.enabledByDefault = annotation.getBoolean("enableByDefault");
            this.filter = this.getFilter(beanType);
        }

        void addExtension(ExtensionBean extensionBean) {
            this.extensions.add(extensionBean);
        }

        Set<ExtensionBean> getExtensions() {
            return this.extensions;
        }

        private Class<?> getFilter(Class<?> type) {
            return MergedAnnotations.from(type, (MergedAnnotations.SearchStrategy)MergedAnnotations.SearchStrategy.TYPE_HIERARCHY).get(FilteredEndpoint.class).getValue("value", Class.class).orElse(null);
        }

        String getBeanName() {
            return this.beanName;
        }

        Class<?> getBeanType() {
            return this.beanType;
        }

        Object getBean() {
            return this.beanSupplier.get();
        }

        EndpointId getId() {
            return this.id;
        }

        boolean isEnabledByDefault() {
            return this.enabledByDefault;
        }

        Class<?> getFilter() {
            return this.filter;
        }
    }

    protected static final class OperationKey {
        private final Object key;
        private final Supplier<String> description;

        public OperationKey(Object key, Supplier<String> description) {
            Assert.notNull((Object)key, (String)"Key must not be null");
            Assert.notNull(description, (String)"Description must not be null");
            this.key = key;
            this.description = description;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null || this.getClass() != obj.getClass()) {
                return false;
            }
            return this.key.equals(((OperationKey)obj).key);
        }

        public int hashCode() {
            return this.key.hashCode();
        }

        public String toString() {
            return this.description.get();
        }
    }
}

