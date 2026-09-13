/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.aop.support.AopUtils
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.beans.factory.SmartInitializingSingleton
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.config.BeanPostProcessor
 *  org.springframework.beans.factory.config.ConfigurableListableBeanFactory
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.beans.factory.support.RootBeanDefinition
 *  org.springframework.core.annotation.AnnotatedElementUtils
 *  org.springframework.core.annotation.AnnotationUtils
 *  org.springframework.core.annotation.MergedAnnotation
 *  org.springframework.core.annotation.MergedAnnotations
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.CollectionUtils
 *  org.springframework.util.ReflectionUtils
 *  org.springframework.util.ReflectionUtils$MethodFilter
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.config.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.integration.annotation.Aggregator;
import org.springframework.integration.annotation.BridgeFrom;
import org.springframework.integration.annotation.BridgeTo;
import org.springframework.integration.annotation.Filter;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Role;
import org.springframework.integration.annotation.Router;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Splitter;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.config.annotation.AggregatorAnnotationPostProcessor;
import org.springframework.integration.config.annotation.BridgeFromAnnotationPostProcessor;
import org.springframework.integration.config.annotation.BridgeToAnnotationPostProcessor;
import org.springframework.integration.config.annotation.FilterAnnotationPostProcessor;
import org.springframework.integration.config.annotation.InboundChannelAdapterAnnotationPostProcessor;
import org.springframework.integration.config.annotation.MethodAnnotationPostProcessor;
import org.springframework.integration.config.annotation.RouterAnnotationPostProcessor;
import org.springframework.integration.config.annotation.ServiceActivatorAnnotationPostProcessor;
import org.springframework.integration.config.annotation.SplitterAnnotationPostProcessor;
import org.springframework.integration.config.annotation.TransformerAnnotationPostProcessor;
import org.springframework.integration.endpoint.AbstractEndpoint;
import org.springframework.integration.util.MessagingAnnotationUtils;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

public class MessagingAnnotationPostProcessor
implements BeanPostProcessor,
BeanFactoryAware,
InitializingBean,
SmartInitializingSingleton {
    protected final Log logger = LogFactory.getLog(this.getClass());
    private final Map<Class<? extends Annotation>, MethodAnnotationPostProcessor<?>> postProcessors = new HashMap();
    private ConfigurableListableBeanFactory beanFactory;
    private final Set<Class<?>> noAnnotationsCache = Collections.newSetFromMap(new ConcurrentHashMap(256));
    private final List<Runnable> methodsToPostProcessAfterContextInitialization = new ArrayList<Runnable>();
    private volatile boolean initialized;

    public void setBeanFactory(BeanFactory beanFactory) {
        Assert.isAssignable(ConfigurableListableBeanFactory.class, beanFactory.getClass(), (String)"a ConfigurableListableBeanFactory is required");
        this.beanFactory = (ConfigurableListableBeanFactory)beanFactory;
    }

    protected ConfigurableListableBeanFactory getBeanFactory() {
        return this.beanFactory;
    }

    public void afterPropertiesSet() {
        Assert.notNull((Object)this.beanFactory, (String)"BeanFactory must not be null");
        this.postProcessors.put(Filter.class, new FilterAnnotationPostProcessor(this.beanFactory));
        this.postProcessors.put(Router.class, new RouterAnnotationPostProcessor(this.beanFactory));
        this.postProcessors.put(Transformer.class, new TransformerAnnotationPostProcessor(this.beanFactory));
        this.postProcessors.put(ServiceActivator.class, new ServiceActivatorAnnotationPostProcessor(this.beanFactory));
        this.postProcessors.put(Splitter.class, new SplitterAnnotationPostProcessor(this.beanFactory));
        this.postProcessors.put(Aggregator.class, new AggregatorAnnotationPostProcessor(this.beanFactory));
        this.postProcessors.put(InboundChannelAdapter.class, new InboundChannelAdapterAnnotationPostProcessor(this.beanFactory));
        this.postProcessors.put(BridgeFrom.class, new BridgeFromAnnotationPostProcessor(this.beanFactory));
        this.postProcessors.put(BridgeTo.class, new BridgeToAnnotationPostProcessor(this.beanFactory));
        Map<Class<? extends Annotation>, MethodAnnotationPostProcessor<?>> customPostProcessors = this.setupCustomPostProcessors();
        if (!CollectionUtils.isEmpty(customPostProcessors)) {
            this.postProcessors.putAll(customPostProcessors);
        }
    }

    protected Map<Class<? extends Annotation>, MethodAnnotationPostProcessor<?>> setupCustomPostProcessors() {
        return null;
    }

    public <A extends Annotation> void addMessagingAnnotationPostProcessor(Class<A> annotation, MethodAnnotationPostProcessor<A> postProcessor) {
        this.postProcessors.put(annotation, postProcessor);
    }

    public void afterSingletonsInstantiated() {
        this.initialized = true;
        this.methodsToPostProcessAfterContextInitialization.forEach(Runnable::run);
        this.methodsToPostProcessAfterContextInitialization.clear();
    }

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Assert.notNull((Object)this.beanFactory, (String)"BeanFactory must not be null");
        Class beanClass = AopUtils.getTargetClass((Object)bean);
        if (this.noAnnotationsCache.contains(beanClass)) {
            return bean;
        }
        ReflectionUtils.doWithMethods((Class)beanClass, method -> this.doWithMethod(method, bean, beanName, beanClass), (ReflectionUtils.MethodFilter)ReflectionUtils.USER_DECLARED_METHODS);
        return bean;
    }

    private void doWithMethod(Method method, Object bean, String beanName, Class<?> beanClass) {
        HashMap<Class<? extends Annotation>, List<Annotation>> annotationChains = new HashMap<Class<? extends Annotation>, List<Annotation>>();
        for (Class<? extends Annotation> clazz : this.postProcessors.keySet()) {
            List<Annotation> annotationChain;
            if (!AnnotatedElementUtils.isAnnotated((AnnotatedElement)method, (String)clazz.getName()) || (annotationChain = this.getAnnotationChain(method, clazz)).size() <= 0) continue;
            annotationChains.put(clazz, annotationChain);
        }
        if (StringUtils.hasText((String)MessagingAnnotationUtils.endpointIdValue(method)) && annotationChains.keySet().size() > 1) {
            throw new IllegalStateException("@EndpointId on " + method.toGenericString() + " can only have one EIP annotation, found: " + annotationChains.keySet().size());
        }
        for (Map.Entry entry : annotationChains.entrySet()) {
            Class annotationType = (Class)entry.getKey();
            List annotations = (List)entry.getValue();
            this.processAnnotationTypeOnMethod(bean, beanName, method, annotationType, annotations);
        }
        if (annotationChains.size() == 0) {
            this.noAnnotationsCache.add(beanClass);
        }
    }

    protected void processAnnotationTypeOnMethod(Object bean, String beanName, Method method, Class<? extends Annotation> annotationType, List<Annotation> annotations) {
        MethodAnnotationPostProcessor<?> postProcessor = this.postProcessors.get(annotationType);
        if (postProcessor != null && postProcessor.shouldCreateEndpoint(method, annotations)) {
            Method targetMethod = method;
            if (AopUtils.isJdkDynamicProxy((Object)bean)) {
                try {
                    targetMethod = bean.getClass().getMethod(method.getName(), method.getParameterTypes());
                }
                catch (NoSuchMethodException e) {
                    throw new IllegalArgumentException("Service methods must be extracted to the service interface for JdkDynamicProxy. The affected bean is: '" + beanName + "' and its method: '" + method + "'", e);
                }
            }
            if (this.initialized) {
                this.postProcessMethodAndRegisterEndpointIfAny(bean, beanName, method, annotationType, annotations, postProcessor, targetMethod);
            } else {
                Method methodToPostProcess = targetMethod;
                this.methodsToPostProcessAfterContextInitialization.add(() -> this.postProcessMethodAndRegisterEndpointIfAny(bean, beanName, method, annotationType, annotations, postProcessor, methodToPostProcess));
            }
        }
    }

    private void postProcessMethodAndRegisterEndpointIfAny(Object bean, String beanName, Method method, Class<? extends Annotation> annotationType, List<Annotation> annotations, MethodAnnotationPostProcessor<?> postProcessor, Method targetMethod) {
        Object result = postProcessor.postProcess(bean, beanName, targetMethod, annotations);
        ConfigurableListableBeanFactory beanFactory = this.getBeanFactory();
        BeanDefinitionRegistry definitionRegistry = (BeanDefinitionRegistry)beanFactory;
        if (result instanceof AbstractEndpoint) {
            Role role;
            String phase;
            AbstractEndpoint endpoint = (AbstractEndpoint)result;
            String autoStartup = MessagingAnnotationUtils.resolveAttribute(annotations, "autoStartup", String.class);
            if (StringUtils.hasText((String)autoStartup) && StringUtils.hasText((String)(autoStartup = beanFactory.resolveEmbeddedValue(autoStartup)))) {
                endpoint.setAutoStartup(Boolean.parseBoolean(autoStartup));
            }
            if (StringUtils.hasText((String)(phase = MessagingAnnotationUtils.resolveAttribute(annotations, "phase", String.class))) && StringUtils.hasText((String)(phase = beanFactory.resolveEmbeddedValue(phase)))) {
                endpoint.setPhase(Integer.parseInt(phase));
            }
            if ((role = (Role)AnnotationUtils.findAnnotation((Method)method, Role.class)) != null) {
                endpoint.setRole(role.value());
            }
            String endpointBeanName = this.generateBeanName(beanName, method, annotationType);
            endpoint.setBeanName(endpointBeanName);
            definitionRegistry.registerBeanDefinition(endpointBeanName, (BeanDefinition)new RootBeanDefinition(endpoint.getClass(), () -> endpoint));
            beanFactory.getBean(endpointBeanName);
        }
    }

    protected List<Annotation> getAnnotationChain(Method method, Class<? extends Annotation> annotationType) {
        LinkedList<Annotation> annotationChain = new LinkedList<Annotation>();
        HashSet<Annotation> visited = new HashSet<Annotation>();
        for (MergedAnnotation mergedAnnotation : MergedAnnotations.from((AnnotatedElement)method)) {
            this.recursiveFindAnnotation(annotationType, mergedAnnotation.synthesize(), annotationChain, visited);
            if (annotationChain.size() <= 0) continue;
            Collections.reverse(annotationChain);
            return annotationChain;
        }
        return annotationChain;
    }

    protected boolean recursiveFindAnnotation(Class<? extends Annotation> annotationType, Annotation ann, List<Annotation> annotationChain, Set<Annotation> visited) {
        if (ann.annotationType().equals(annotationType)) {
            annotationChain.add(ann);
            return true;
        }
        for (Annotation metaAnn : ann.annotationType().getAnnotations()) {
            if (ann.equals(metaAnn) || visited.contains(metaAnn) || metaAnn.annotationType().getPackage().getName().startsWith("java.lang")) continue;
            visited.add(metaAnn);
            if (!this.recursiveFindAnnotation(annotationType, metaAnn, annotationChain, visited)) continue;
            annotationChain.add(ann);
            return true;
        }
        return false;
    }

    protected String generateBeanName(String originalBeanName, Method method, Class<? extends Annotation> annotationType) {
        String name = MessagingAnnotationUtils.endpointIdValue(method);
        if (!StringUtils.hasText((String)name)) {
            String baseName;
            name = baseName = originalBeanName + "." + method.getName() + "." + ClassUtils.getShortNameAsProperty(annotationType);
            int count = 1;
            while (this.beanFactory.containsBean(name)) {
                name = baseName + "#" + ++count;
            }
        }
        return name;
    }

    protected Map<Class<? extends Annotation>, MethodAnnotationPostProcessor<?>> getPostProcessors() {
        return this.postProcessors;
    }
}

