/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.authentication.MultifactorAuthenticationPrincipalResolver
 *  org.apereo.cas.authentication.attribute.AttributeDefinitionStore
 *  org.apereo.cas.authentication.principal.PrincipalAttributesRepositoryCache
 *  org.apereo.cas.authentication.principal.RegisteredServicePrincipalAttributesRepository
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.services.persondir.IPersonAttributeDao
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor
 *  org.springframework.beans.factory.config.ConfigurableListableBeanFactory
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ApplicationContextAware
 *  org.springframework.context.ConfigurableApplicationContext
 *  org.springframework.core.annotation.AnnotationAwareOrderComparator
 */
package org.apereo.cas.util.spring;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apereo.cas.authentication.MultifactorAuthenticationPrincipalResolver;
import org.apereo.cas.authentication.attribute.AttributeDefinitionStore;
import org.apereo.cas.authentication.principal.PrincipalAttributesRepositoryCache;
import org.apereo.cas.authentication.principal.RegisteredServicePrincipalAttributesRepository;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.util.scripting.ExecutableCompiledGroovyScript;
import org.apereo.cas.util.scripting.ScriptResourceCacheManager;
import org.apereo.cas.util.text.MessageSanitizer;
import org.apereo.services.persondir.IPersonAttributeDao;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

public class ApplicationContextProvider
implements ApplicationContextAware {
    private static ApplicationContext CONTEXT;

    public static ApplicationContext getApplicationContext() {
        return CONTEXT;
    }

    public static void processBeanInjections(Object bean) {
        ConfigurableApplicationContext ac = ApplicationContextProvider.getConfigurableApplicationContext();
        if (ac != null) {
            AutowiredAnnotationBeanPostProcessor bpp = new AutowiredAnnotationBeanPostProcessor();
            bpp.setBeanFactory((BeanFactory)ac.getAutowireCapableBeanFactory());
            bpp.processInjection(bean);
        }
    }

    public void setApplicationContext(ApplicationContext context) {
        CONTEXT = context;
    }

    public static List<MultifactorAuthenticationPrincipalResolver> getMultifactorAuthenticationPrincipalResolvers() {
        ArrayList<MultifactorAuthenticationPrincipalResolver> resolvers = new ArrayList<MultifactorAuthenticationPrincipalResolver>(CONTEXT.getBeansOfType(MultifactorAuthenticationPrincipalResolver.class).values());
        AnnotationAwareOrderComparator.sort(resolvers);
        return resolvers;
    }

    public static void holdApplicationContext(ApplicationContext ctx) {
        CONTEXT = ctx;
    }

    public static <T> T registerBeanIntoApplicationContext(ConfigurableApplicationContext applicationContext, Class<T> beanClazz, String beanId) {
        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        Object provider = beanFactory.createBean(beanClazz);
        beanFactory.initializeBean(provider, beanId);
        beanFactory.autowireBean(provider);
        beanFactory.registerSingleton(beanId, provider);
        return (T)provider;
    }

    public static <T> T registerBeanIntoApplicationContext(ConfigurableApplicationContext applicationContext, T beanInstance, String beanId) {
        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        if (beanFactory.containsBean(beanId)) {
            return (T)applicationContext.getBean(beanId, beanInstance.getClass());
        }
        beanFactory.initializeBean(beanInstance, beanId);
        beanFactory.autowireBean(beanInstance);
        beanFactory.registerSingleton(beanId, beanInstance);
        return beanInstance;
    }

    public static Optional<CasConfigurationProperties> getCasConfigurationProperties() {
        if (CONTEXT != null) {
            return Optional.of((CasConfigurationProperties)CONTEXT.getBean(CasConfigurationProperties.class));
        }
        return Optional.empty();
    }

    public static Optional<IPersonAttributeDao> getAttributeRepository() {
        if (CONTEXT != null) {
            return Optional.of((IPersonAttributeDao)CONTEXT.getBean("attributeRepository", IPersonAttributeDao.class));
        }
        return Optional.empty();
    }

    public static ConfigurableApplicationContext getConfigurableApplicationContext() {
        return (ConfigurableApplicationContext)CONTEXT;
    }

    public static Optional<ScriptResourceCacheManager<String, ExecutableCompiledGroovyScript>> getScriptResourceCacheManager() {
        if (CONTEXT != null && CONTEXT.containsBean("scriptResourceCacheManager")) {
            return Optional.of((ScriptResourceCacheManager)CONTEXT.getBean("scriptResourceCacheManager", ScriptResourceCacheManager.class));
        }
        return Optional.empty();
    }

    public static Optional<AttributeDefinitionStore> getAttributeDefinitionStore() {
        if (CONTEXT != null && CONTEXT.containsBean("attributeDefinitionStore")) {
            return Optional.of((AttributeDefinitionStore)CONTEXT.getBean("attributeDefinitionStore", AttributeDefinitionStore.class));
        }
        return Optional.empty();
    }

    public static Optional<RegisteredServicePrincipalAttributesRepository> getPrincipalAttributesRepository() {
        if (CONTEXT != null && CONTEXT.containsBean("globalPrincipalAttributeRepository")) {
            return Optional.of((RegisteredServicePrincipalAttributesRepository)CONTEXT.getBean("globalPrincipalAttributeRepository", RegisteredServicePrincipalAttributesRepository.class));
        }
        return Optional.empty();
    }

    public static Optional<PrincipalAttributesRepositoryCache> getPrincipalAttributesRepositoryCache() {
        if (CONTEXT != null && CONTEXT.containsBean("principalAttributesRepositoryCache")) {
            return Optional.of((PrincipalAttributesRepositoryCache)CONTEXT.getBean("principalAttributesRepositoryCache", PrincipalAttributesRepositoryCache.class));
        }
        return Optional.empty();
    }

    public static Optional<MessageSanitizer> getMessagSanitizer() {
        if (CONTEXT != null && CONTEXT.containsBean("messageSanitizer")) {
            return Optional.of((MessageSanitizer)CONTEXT.getBean("messageSanitizer", MessageSanitizer.class));
        }
        return Optional.empty();
    }
}

