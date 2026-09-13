/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.CentralAuthenticationService
 *  org.apereo.cas.audit.AuditTrailRecordResolutionPlanConfigurer
 *  org.apereo.cas.authentication.AuthenticationSystemSupport
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.cas.configuration.features.CasFeatureModule$FeatureCatalog
 *  org.apereo.cas.logout.slo.SingleLogoutRequestExecutor
 *  org.apereo.cas.rest.audit.RestResponseEntityAuditResourceResolver
 *  org.apereo.cas.rest.authentication.RestAuthenticationService
 *  org.apereo.cas.rest.factory.CasProtocolServiceTicketResourceEntityResponseFactory
 *  org.apereo.cas.rest.factory.CompositeServiceTicketResourceEntityResponseFactory
 *  org.apereo.cas.rest.factory.DefaultTicketGrantingTicketResourceEntityResponseFactory
 *  org.apereo.cas.rest.factory.DefaultUserAuthenticationResourceEntityResponseFactory
 *  org.apereo.cas.rest.factory.RestHttpRequestCredentialFactory
 *  org.apereo.cas.rest.factory.ServiceTicketResourceEntityResponseFactory
 *  org.apereo.cas.rest.factory.TicketGrantingTicketResourceEntityResponseFactory
 *  org.apereo.cas.rest.factory.UserAuthenticationResourceEntityResponseFactory
 *  org.apereo.cas.rest.plan.DefaultServiceTicketResourceEntityResponseFactoryPlan
 *  org.apereo.cas.rest.plan.ServiceTicketResourceEntityResponseFactoryConfigurer
 *  org.apereo.cas.rest.plan.ServiceTicketResourceEntityResponseFactoryPlan
 *  org.apereo.cas.support.rest.resources.ServiceTicketResource
 *  org.apereo.cas.support.rest.resources.TicketGrantingTicketResource
 *  org.apereo.cas.support.rest.resources.TicketStatusResource
 *  org.apereo.cas.support.rest.resources.UserAuthenticationResource
 *  org.apereo.cas.throttle.AuthenticationThrottlingExecutionPlan
 *  org.apereo.cas.ticket.registry.TicketRegistry
 *  org.apereo.cas.ticket.registry.TicketRegistrySupport
 *  org.apereo.cas.util.spring.RefreshableHandlerInterceptor
 *  org.apereo.cas.util.spring.boot.ConditionalOnFeatureEnabled
 *  org.apereo.cas.web.ProtocolEndpointWebSecurityConfigurer
 *  org.apereo.cas.web.support.ArgumentExtractor
 *  org.apereo.inspektr.audit.spi.AuditActionResolver
 *  org.apereo.inspektr.audit.spi.AuditResourceResolver
 *  org.apereo.inspektr.audit.spi.support.DefaultAuditActionResolver
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.beans.factory.annotation.Qualifier
 *  org.springframework.boot.autoconfigure.AutoConfiguration
 *  org.springframework.boot.autoconfigure.AutoConfigureOrder
 *  org.springframework.boot.autoconfigure.condition.ConditionalOnBean
 *  org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ConfigurableApplicationContext
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.context.annotation.ScopedProxyMode
 *  org.springframework.web.servlet.HandlerInterceptor
 *  org.springframework.web.servlet.config.annotation.InterceptorRegistry
 *  org.springframework.web.servlet.config.annotation.WebMvcConfigurer
 */
package org.apereo.cas.config;

import java.util.List;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.CentralAuthenticationService;
import org.apereo.cas.audit.AuditTrailRecordResolutionPlanConfigurer;
import org.apereo.cas.authentication.AuthenticationSystemSupport;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.configuration.features.CasFeatureModule;
import org.apereo.cas.logout.slo.SingleLogoutRequestExecutor;
import org.apereo.cas.rest.audit.RestResponseEntityAuditResourceResolver;
import org.apereo.cas.rest.authentication.RestAuthenticationService;
import org.apereo.cas.rest.factory.CasProtocolServiceTicketResourceEntityResponseFactory;
import org.apereo.cas.rest.factory.CompositeServiceTicketResourceEntityResponseFactory;
import org.apereo.cas.rest.factory.DefaultTicketGrantingTicketResourceEntityResponseFactory;
import org.apereo.cas.rest.factory.DefaultUserAuthenticationResourceEntityResponseFactory;
import org.apereo.cas.rest.factory.RestHttpRequestCredentialFactory;
import org.apereo.cas.rest.factory.ServiceTicketResourceEntityResponseFactory;
import org.apereo.cas.rest.factory.TicketGrantingTicketResourceEntityResponseFactory;
import org.apereo.cas.rest.factory.UserAuthenticationResourceEntityResponseFactory;
import org.apereo.cas.rest.plan.DefaultServiceTicketResourceEntityResponseFactoryPlan;
import org.apereo.cas.rest.plan.ServiceTicketResourceEntityResponseFactoryConfigurer;
import org.apereo.cas.rest.plan.ServiceTicketResourceEntityResponseFactoryPlan;
import org.apereo.cas.support.rest.resources.ServiceTicketResource;
import org.apereo.cas.support.rest.resources.TicketGrantingTicketResource;
import org.apereo.cas.support.rest.resources.TicketStatusResource;
import org.apereo.cas.support.rest.resources.UserAuthenticationResource;
import org.apereo.cas.throttle.AuthenticationThrottlingExecutionPlan;
import org.apereo.cas.ticket.registry.TicketRegistry;
import org.apereo.cas.ticket.registry.TicketRegistrySupport;
import org.apereo.cas.util.spring.RefreshableHandlerInterceptor;
import org.apereo.cas.util.spring.boot.ConditionalOnFeatureEnabled;
import org.apereo.cas.web.ProtocolEndpointWebSecurityConfigurer;
import org.apereo.cas.web.support.ArgumentExtractor;
import org.apereo.inspektr.audit.spi.AuditActionResolver;
import org.apereo.inspektr.audit.spi.AuditResourceResolver;
import org.apereo.inspektr.audit.spi.support.DefaultAuditActionResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableConfigurationProperties(value={CasConfigurationProperties.class})
@ConditionalOnFeatureEnabled(feature=CasFeatureModule.FeatureCatalog.RestProtocol)
@AutoConfiguration
public class CasRestConfiguration {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(CasRestConfiguration.class);

    @Configuration(value="CasRestAuditConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasRestAuditConfiguration {
        @Bean
        @ConditionalOnMissingBean(name={"restAuditTrailRecordResolutionPlanConfigurer"})
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public AuditTrailRecordResolutionPlanConfigurer restAuditTrailRecordResolutionPlanConfigurer() {
            return plan -> {
                plan.registerAuditActionResolver("REST_API_TICKET_GRANTING_TICKET_ACTION_RESOLVER", (AuditActionResolver)new DefaultAuditActionResolver("_CREATED", "_FAILED"));
                plan.registerAuditResourceResolver("REST_API_TICKET_GRANTING_TICKET_RESOURCE_RESOLVER", (AuditResourceResolver)new RestResponseEntityAuditResourceResolver(false));
                plan.registerAuditActionResolver("REST_API_SERVICE_TICKET_ACTION_RESOLVER", (AuditActionResolver)new DefaultAuditActionResolver("_CREATED", "_FAILED"));
                plan.registerAuditResourceResolver("REST_API_SERVICE_TICKET_RESOURCE_RESOLVER", (AuditResourceResolver)new RestResponseEntityAuditResourceResolver(true));
            };
        }
    }

    @Configuration(value="CasRestControllerResourcesConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    @AutoConfigureOrder(value=0x7FFFFFFF)
    public static class CasRestControllerResourcesConfiguration {
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public TicketStatusResource ticketStatusResource(@Qualifier(value="ticketRegistry") TicketRegistry ticketRegistry) {
            return new TicketStatusResource(ticketRegistry);
        }

        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public ServiceTicketResource serviceTicketResource(@Qualifier(value="serviceTicketResourceEntityResponseFactory") ServiceTicketResourceEntityResponseFactory serviceTicketResourceEntityResponseFactory, @Qualifier(value="restHttpRequestCredentialFactory") RestHttpRequestCredentialFactory restHttpRequestCredentialFactory, @Qualifier(value="defaultTicketRegistrySupport") TicketRegistrySupport ticketRegistrySupport, @Qualifier(value="defaultAuthenticationSystemSupport") AuthenticationSystemSupport authenticationSystemSupport, ConfigurableApplicationContext applicationContext, @Qualifier(value="argumentExtractor") ArgumentExtractor argumentExtractor) {
            return new ServiceTicketResource(authenticationSystemSupport, ticketRegistrySupport, argumentExtractor, serviceTicketResourceEntityResponseFactory, restHttpRequestCredentialFactory, (ApplicationContext)applicationContext);
        }

        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public TicketGrantingTicketResource ticketGrantingTicketResource(@Qualifier(value="ticketGrantingTicketResourceEntityResponseFactory") TicketGrantingTicketResourceEntityResponseFactory ticketGrantingTicketResourceEntityResponseFactory, ConfigurableApplicationContext applicationContext, @Qualifier(value="centralAuthenticationService") CentralAuthenticationService centralAuthenticationService, @Qualifier(value="restAuthenticationService") RestAuthenticationService restAuthenticationService, @Qualifier(value="defaultSingleLogoutRequestExecutor") SingleLogoutRequestExecutor defaultSingleLogoutRequestExecutor) {
            return new TicketGrantingTicketResource(restAuthenticationService, centralAuthenticationService, ticketGrantingTicketResourceEntityResponseFactory, (ApplicationContext)applicationContext, defaultSingleLogoutRequestExecutor);
        }

        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public UserAuthenticationResource userAuthenticationRestController(@Qualifier(value="userAuthenticationResourceEntityResponseFactory") UserAuthenticationResourceEntityResponseFactory userAuthenticationResourceEntityResponseFactory, ConfigurableApplicationContext applicationContext, @Qualifier(value="restAuthenticationService") RestAuthenticationService restAuthenticationService) {
            return new UserAuthenticationResource(restAuthenticationService, userAuthenticationResourceEntityResponseFactory, (ApplicationContext)applicationContext);
        }
    }

    @Configuration(value="CasRestThrottleConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    @ConditionalOnBean(value={AuthenticationThrottlingExecutionPlan.class})
    public static class CasRestThrottleConfiguration {
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        @ConditionalOnMissingBean(name={"restAuthenticationThrottle"})
        public WebMvcConfigurer casRestThrottlingWebMvcConfigurer(final @Qualifier(value="authenticationThrottlingExecutionPlan") ObjectProvider<AuthenticationThrottlingExecutionPlan> authenticationThrottlingExecutionPlan) {
            return new WebMvcConfigurer(){

                public void addInterceptors(InterceptorRegistry registry) {
                    authenticationThrottlingExecutionPlan.ifAvailable(plan -> {
                        RefreshableHandlerInterceptor handler = new RefreshableHandlerInterceptor(() -> ((AuthenticationThrottlingExecutionPlan)plan).getAuthenticationThrottleInterceptors());
                        LOGGER.debug("Activating authentication throttling for REST endpoints...");
                        registry.addInterceptor((HandlerInterceptor)handler).order(0).addPathPatterns(new String[]{"/v1/**"});
                    });
                }
            };
        }
    }

    @Configuration(value="CasRestWebConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasRestWebConfiguration {
        @Bean
        @ConditionalOnMissingBean(name={"restProtocolEndpointConfigurer"})
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public ProtocolEndpointWebSecurityConfigurer<Void> restProtocolEndpointConfigurer() {
            return new ProtocolEndpointWebSecurityConfigurer<Void>(){

                public List<String> getIgnoredEndpoints() {
                    return List.of(StringUtils.prependIfMissing((String)"/v1", (CharSequence)"/", (CharSequence[])new CharSequence[0]));
                }
            };
        }
    }

    @Configuration(value="CasRestResponseFactoryConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasRestResponseFactoryConfiguration {
        @Bean
        @ConditionalOnMissingBean(name={"serviceTicketResourceEntityResponseFactory"})
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public ServiceTicketResourceEntityResponseFactory serviceTicketResourceEntityResponseFactory(List<ServiceTicketResourceEntityResponseFactoryConfigurer> configurers) {
            DefaultServiceTicketResourceEntityResponseFactoryPlan plan = new DefaultServiceTicketResourceEntityResponseFactoryPlan();
            configurers.forEach(c -> c.configureEntityResponseFactory((ServiceTicketResourceEntityResponseFactoryPlan)plan));
            return new CompositeServiceTicketResourceEntityResponseFactory(plan.getFactories());
        }

        @Bean
        @ConditionalOnMissingBean(name={"ticketGrantingTicketResourceEntityResponseFactory"})
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public TicketGrantingTicketResourceEntityResponseFactory ticketGrantingTicketResourceEntityResponseFactory() {
            return new DefaultTicketGrantingTicketResourceEntityResponseFactory();
        }

        @Bean
        @ConditionalOnMissingBean(name={"userAuthenticationResourceEntityResponseFactory"})
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public UserAuthenticationResourceEntityResponseFactory userAuthenticationResourceEntityResponseFactory() {
            return new DefaultUserAuthenticationResourceEntityResponseFactory();
        }
    }

    @Configuration(value="CasRestResponseFactoryPlanConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasRestResponseFactoryPlanConfiguration {
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        @ConditionalOnMissingBean(name={"restServiceTicketResourceEntityResponseFactoryConfigurer"})
        public ServiceTicketResourceEntityResponseFactoryConfigurer restServiceTicketResourceEntityResponseFactoryConfigurer(@Qualifier(value="centralAuthenticationService") CentralAuthenticationService centralAuthenticationService) {
            return plan -> plan.registerFactory((ServiceTicketResourceEntityResponseFactory)new CasProtocolServiceTicketResourceEntityResponseFactory(centralAuthenticationService));
        }
    }
}

