/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.audit.AuditTrailExecutionPlan
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.cas.configuration.features.CasFeatureModule$FeatureCatalog
 *  org.apereo.cas.configuration.model.support.throttle.ThrottleProperties
 *  org.apereo.cas.throttle.AuthenticationThrottlingExecutionPlan
 *  org.apereo.cas.throttle.AuthenticationThrottlingExecutionPlanConfigurer
 *  org.apereo.cas.throttle.ConcurrentThrottledSubmissionsStore
 *  org.apereo.cas.throttle.DefaultAuthenticationThrottlingExecutionPlan
 *  org.apereo.cas.throttle.DefaultThrottledRequestResponseHandler
 *  org.apereo.cas.throttle.ThrottledRequestExecutor
 *  org.apereo.cas.throttle.ThrottledRequestFilter
 *  org.apereo.cas.throttle.ThrottledRequestResponseHandler
 *  org.apereo.cas.util.spring.boot.ConditionalOnFeatureEnabled
 *  org.apereo.cas.web.support.InMemoryThrottledSubmissionByIpAddressAndUsernameHandlerInterceptorAdapter
 *  org.apereo.cas.web.support.InMemoryThrottledSubmissionByIpAddressHandlerInterceptorAdapter
 *  org.apereo.cas.web.support.InMemoryThrottledSubmissionCleaner
 *  org.apereo.cas.web.support.ThrottledSubmissionHandlerConfigurationContext
 *  org.apereo.cas.web.support.ThrottledSubmissionHandlerEndpoint
 *  org.apereo.cas.web.support.ThrottledSubmissionHandlerInterceptor
 *  org.apereo.cas.web.support.ThrottledSubmissionsStore
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.beans.factory.annotation.Qualifier
 *  org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnAvailableEndpoint
 *  org.springframework.boot.autoconfigure.AutoConfiguration
 *  org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.context.ConfigurableApplicationContext
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.context.annotation.ScopedProxyMode
 *  org.springframework.web.servlet.HandlerInterceptor
 */
package org.apereo.cas.config;

import java.util.List;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.audit.AuditTrailExecutionPlan;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.configuration.features.CasFeatureModule;
import org.apereo.cas.configuration.model.support.throttle.ThrottleProperties;
import org.apereo.cas.throttle.AuthenticationThrottlingExecutionPlan;
import org.apereo.cas.throttle.AuthenticationThrottlingExecutionPlanConfigurer;
import org.apereo.cas.throttle.ConcurrentThrottledSubmissionsStore;
import org.apereo.cas.throttle.DefaultAuthenticationThrottlingExecutionPlan;
import org.apereo.cas.throttle.DefaultThrottledRequestResponseHandler;
import org.apereo.cas.throttle.ThrottledRequestExecutor;
import org.apereo.cas.throttle.ThrottledRequestFilter;
import org.apereo.cas.throttle.ThrottledRequestResponseHandler;
import org.apereo.cas.util.spring.boot.ConditionalOnFeatureEnabled;
import org.apereo.cas.web.support.InMemoryThrottledSubmissionByIpAddressAndUsernameHandlerInterceptorAdapter;
import org.apereo.cas.web.support.InMemoryThrottledSubmissionByIpAddressHandlerInterceptorAdapter;
import org.apereo.cas.web.support.InMemoryThrottledSubmissionCleaner;
import org.apereo.cas.web.support.ThrottledSubmissionHandlerConfigurationContext;
import org.apereo.cas.web.support.ThrottledSubmissionHandlerEndpoint;
import org.apereo.cas.web.support.ThrottledSubmissionHandlerInterceptor;
import org.apereo.cas.web.support.ThrottledSubmissionsStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnAvailableEndpoint;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.servlet.HandlerInterceptor;

@EnableConfigurationProperties(value={CasConfigurationProperties.class})
@ConditionalOnFeatureEnabled(feature=CasFeatureModule.FeatureCatalog.Throttling)
@AutoConfiguration
public class CasThrottlingConfiguration {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(CasThrottlingConfiguration.class);

    @Configuration(value="CasThrottlingSchedulerConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasThrottlingSchedulerConfiguration {
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public Runnable throttleSubmissionCleaner(@Qualifier(value="authenticationThrottlingExecutionPlan") AuthenticationThrottlingExecutionPlan plan) {
            return new InMemoryThrottledSubmissionCleaner(plan);
        }
    }

    @Configuration(value="CasThrottlingWebConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasThrottlingWebConfiguration {
        @Bean
        @ConditionalOnAvailableEndpoint
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public ThrottledSubmissionHandlerEndpoint throttledSubmissionHandlerEndpoint(@Qualifier(value="authenticationThrottlingExecutionPlan") ObjectProvider<AuthenticationThrottlingExecutionPlan> plan, CasConfigurationProperties casProperties) {
            return new ThrottledSubmissionHandlerEndpoint(casProperties, plan);
        }
    }

    @Configuration(value="CasThrottlingPlanConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasThrottlingPlanConfiguration {
        @ConditionalOnMissingBean(name={"authenticationThrottlingExecutionPlanConfigurer"})
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public AuthenticationThrottlingExecutionPlanConfigurer authenticationThrottlingExecutionPlanConfigurer(@Qualifier(value="httpPostMethodThrottlingRequestFilter") ThrottledRequestFilter httpPostMethodThrottlingRequestFilter, @Qualifier(value="authenticationThrottle") ThrottledSubmissionHandlerInterceptor authenticationThrottle) {
            return plan -> {
                plan.registerAuthenticationThrottleFilter(httpPostMethodThrottlingRequestFilter);
                plan.registerAuthenticationThrottleInterceptor((HandlerInterceptor)authenticationThrottle);
            };
        }
    }

    @Configuration(value="CasThrottlingPlanExecutionConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasThrottlingPlanExecutionConfiguration {
        @ConditionalOnMissingBean(name={"authenticationThrottlingExecutionPlan"})
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public AuthenticationThrottlingExecutionPlan authenticationThrottlingExecutionPlan(List<AuthenticationThrottlingExecutionPlanConfigurer> configurers) {
            DefaultAuthenticationThrottlingExecutionPlan plan = new DefaultAuthenticationThrottlingExecutionPlan();
            configurers.forEach(c -> {
                LOGGER.trace("Registering authentication throttler [{}]", (Object)c.getName());
                c.configureAuthenticationThrottlingExecutionPlan((AuthenticationThrottlingExecutionPlan)plan);
            });
            return plan;
        }
    }

    @Configuration(value="CasThrottlingCoreConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasThrottlingCoreConfiguration {
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        @Bean
        @ConditionalOnMissingBean(name={"throttledRequestResponseHandler"})
        public ThrottledRequestResponseHandler throttledRequestResponseHandler(CasConfigurationProperties casProperties) {
            ThrottleProperties throttle = casProperties.getAuthn().getThrottle();
            return new DefaultThrottledRequestResponseHandler(throttle.getCore().getUsernameParameter());
        }

        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        @Bean
        @ConditionalOnMissingBean(name={"throttledRequestExecutor"})
        public ThrottledRequestExecutor throttledRequestExecutor() {
            return ThrottledRequestExecutor.noOp();
        }

        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        @ConditionalOnMissingBean(name={"throttleSubmissionMap"})
        @Bean
        public ThrottledSubmissionsStore throttleSubmissionMap(CasConfigurationProperties casProperties) {
            return new ConcurrentThrottledSubmissionsStore(casProperties);
        }

        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        @ConditionalOnMissingBean(name={"httpPostMethodThrottlingRequestFilter"})
        public ThrottledRequestFilter httpPostMethodThrottlingRequestFilter() {
            return ThrottledRequestFilter.httpPost();
        }
    }

    @Configuration(value="CasThrottlingContextConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasThrottlingContextConfiguration {
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        @ConditionalOnMissingBean(name={"authenticationThrottlingConfigurationContext"})
        public ThrottledSubmissionHandlerConfigurationContext authenticationThrottlingConfigurationContext(ConfigurableApplicationContext applicationContext, @Qualifier(value="auditTrailExecutionPlan") AuditTrailExecutionPlan auditTrailExecutionPlan, CasConfigurationProperties casProperties, @Qualifier(value="throttledRequestResponseHandler") ThrottledRequestResponseHandler throttledRequestResponseHandler, @Qualifier(value="throttledRequestExecutor") ThrottledRequestExecutor throttledRequestExecutor, @Qualifier(value="throttleSubmissionMap") ThrottledSubmissionsStore throttledSubmissionStore) {
            return ThrottledSubmissionHandlerConfigurationContext.builder().casProperties(casProperties).throttledSubmissionStore(throttledSubmissionStore).auditTrailExecutionPlan(auditTrailExecutionPlan).throttledRequestResponseHandler(throttledRequestResponseHandler).throttledRequestExecutor(throttledRequestExecutor).applicationContext(applicationContext).build();
        }
    }

    @Configuration(value="CasThrottlingInterceptorConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasThrottlingInterceptorConfiguration {
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        @ConditionalOnMissingBean(name={"authenticationThrottle"})
        @Bean
        public ThrottledSubmissionHandlerInterceptor authenticationThrottle(CasConfigurationProperties casProperties, @Qualifier(value="authenticationThrottlingConfigurationContext") ThrottledSubmissionHandlerConfigurationContext authenticationThrottlingConfigurationContext) {
            ThrottleProperties throttle = casProperties.getAuthn().getThrottle();
            if (throttle.getFailure().getRangeSeconds() <= 0 && throttle.getFailure().getThreshold() <= 0) {
                LOGGER.trace("Authentication throttling is disabled since no range-seconds or failure-threshold is defined");
                return ThrottledSubmissionHandlerInterceptor.noOp();
            }
            if (StringUtils.isNotBlank((CharSequence)throttle.getCore().getUsernameParameter())) {
                LOGGER.trace("Activating authentication throttling based on IP address and username...");
                return new InMemoryThrottledSubmissionByIpAddressAndUsernameHandlerInterceptorAdapter(authenticationThrottlingConfigurationContext);
            }
            LOGGER.trace("Activating authentication throttling based on IP address...");
            return new InMemoryThrottledSubmissionByIpAddressHandlerInterceptorAdapter(authenticationThrottlingConfigurationContext);
        }
    }
}

