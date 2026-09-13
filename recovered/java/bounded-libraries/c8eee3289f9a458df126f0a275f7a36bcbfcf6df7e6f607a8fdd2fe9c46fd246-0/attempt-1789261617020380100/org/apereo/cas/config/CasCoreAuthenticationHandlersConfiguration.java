/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.authentication.AcceptUsersAuthenticationHandler
 *  org.apereo.cas.authentication.AuthenticationEventExecutionPlanConfigurer
 *  org.apereo.cas.authentication.AuthenticationHandler
 *  org.apereo.cas.authentication.CoreAuthenticationUtils
 *  org.apereo.cas.authentication.handler.support.HttpBasedServiceCredentialsAuthenticationHandler
 *  org.apereo.cas.authentication.handler.support.jaas.JaasAuthenticationHandler
 *  org.apereo.cas.authentication.principal.PrincipalFactory
 *  org.apereo.cas.authentication.principal.PrincipalFactoryUtils
 *  org.apereo.cas.authentication.principal.PrincipalNameTransformerUtils
 *  org.apereo.cas.authentication.principal.PrincipalResolver
 *  org.apereo.cas.authentication.principal.resolvers.ProxyingPrincipalResolver
 *  org.apereo.cas.authentication.support.password.PasswordEncoderUtils
 *  org.apereo.cas.authentication.support.password.PasswordPolicyContext
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.cas.configuration.features.CasFeatureModule$FeatureCatalog
 *  org.apereo.cas.configuration.model.core.authentication.PasswordEncoderProperties
 *  org.apereo.cas.configuration.model.core.authentication.PasswordPolicyProperties
 *  org.apereo.cas.configuration.model.core.authentication.PersonDirectoryPrincipalResolverProperties
 *  org.apereo.cas.configuration.model.core.authentication.PrincipalAttributesCoreProperties$MergingStrategyTypes
 *  org.apereo.cas.configuration.model.core.authentication.PrincipalTransformationProperties
 *  org.apereo.cas.configuration.model.support.generic.AcceptAuthenticationProperties
 *  org.apereo.cas.services.ServicesManager
 *  org.apereo.cas.util.http.HttpClient
 *  org.apereo.cas.util.spring.beans.BeanCondition
 *  org.apereo.cas.util.spring.beans.BeanContainer
 *  org.apereo.cas.util.spring.beans.BeanSupplier
 *  org.apereo.cas.util.spring.boot.ConditionalOnFeatureEnabled
 *  org.apereo.services.persondir.IPersonAttributeDao
 *  org.apereo.services.persondir.support.merger.IAttributeMerger
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Qualifier
 *  org.springframework.boot.autoconfigure.AutoConfiguration
 *  org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ConfigurableApplicationContext
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.context.annotation.ScopedProxyMode
 *  org.springframework.core.env.PropertyResolver
 */
package org.apereo.cas.config;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.authentication.AcceptUsersAuthenticationHandler;
import org.apereo.cas.authentication.AuthenticationEventExecutionPlanConfigurer;
import org.apereo.cas.authentication.AuthenticationHandler;
import org.apereo.cas.authentication.CoreAuthenticationUtils;
import org.apereo.cas.authentication.handler.support.HttpBasedServiceCredentialsAuthenticationHandler;
import org.apereo.cas.authentication.handler.support.jaas.JaasAuthenticationHandler;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.authentication.principal.PrincipalFactoryUtils;
import org.apereo.cas.authentication.principal.PrincipalNameTransformerUtils;
import org.apereo.cas.authentication.principal.PrincipalResolver;
import org.apereo.cas.authentication.principal.resolvers.ProxyingPrincipalResolver;
import org.apereo.cas.authentication.support.password.PasswordEncoderUtils;
import org.apereo.cas.authentication.support.password.PasswordPolicyContext;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.configuration.features.CasFeatureModule;
import org.apereo.cas.configuration.model.core.authentication.PasswordEncoderProperties;
import org.apereo.cas.configuration.model.core.authentication.PasswordPolicyProperties;
import org.apereo.cas.configuration.model.core.authentication.PersonDirectoryPrincipalResolverProperties;
import org.apereo.cas.configuration.model.core.authentication.PrincipalAttributesCoreProperties;
import org.apereo.cas.configuration.model.core.authentication.PrincipalTransformationProperties;
import org.apereo.cas.configuration.model.support.generic.AcceptAuthenticationProperties;
import org.apereo.cas.services.ServicesManager;
import org.apereo.cas.util.http.HttpClient;
import org.apereo.cas.util.spring.beans.BeanCondition;
import org.apereo.cas.util.spring.beans.BeanContainer;
import org.apereo.cas.util.spring.beans.BeanSupplier;
import org.apereo.cas.util.spring.boot.ConditionalOnFeatureEnabled;
import org.apereo.services.persondir.IPersonAttributeDao;
import org.apereo.services.persondir.support.merger.IAttributeMerger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.PropertyResolver;

@EnableConfigurationProperties(value={CasConfigurationProperties.class})
@ConditionalOnFeatureEnabled(feature=CasFeatureModule.FeatureCatalog.Authentication)
@AutoConfiguration
public class CasCoreAuthenticationHandlersConfiguration {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(CasCoreAuthenticationHandlersConfiguration.class);

    @Configuration(value="CasCoreAuthenticationHandlersJaasConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasCoreAuthenticationHandlersJaasConfiguration {
        @ConditionalOnMissingBean(name={"jaasPasswordPolicyConfiguration"})
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public PasswordPolicyContext jaasPasswordPolicyConfiguration() {
            return new PasswordPolicyContext();
        }

        @ConditionalOnMissingBean(name={"jaasPrincipalFactory"})
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public PrincipalFactory jaasPrincipalFactory() {
            return PrincipalFactoryUtils.newPrincipalFactory();
        }

        @Bean
        @ConditionalOnMissingBean(name={"jaasPersonDirectoryPrincipalResolvers"})
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public BeanContainer<PrincipalResolver> jaasPersonDirectoryPrincipalResolvers(CasConfigurationProperties casProperties, @Qualifier(value="attributeRepository") IPersonAttributeDao attributeRepository, @Qualifier(value="jaasPrincipalFactory") PrincipalFactory jaasPrincipalFactory) {
            PersonDirectoryPrincipalResolverProperties personDirectory = casProperties.getPersonDirectory();
            return BeanContainer.of(casProperties.getAuthn().getJaas().stream().filter(jaas -> StringUtils.isNotBlank((CharSequence)jaas.getRealm())).map(jaas -> {
                PersonDirectoryPrincipalResolverProperties jaasPrincipal = jaas.getPrincipal();
                IAttributeMerger attributeMerger = CoreAuthenticationUtils.getAttributeMerger((PrincipalAttributesCoreProperties.MergingStrategyTypes)casProperties.getAuthn().getAttributeRepository().getCore().getMerger());
                return CoreAuthenticationUtils.newPersonDirectoryPrincipalResolver((PrincipalFactory)jaasPrincipalFactory, (IPersonAttributeDao)attributeRepository, (IAttributeMerger)attributeMerger, (PersonDirectoryPrincipalResolverProperties[])new PersonDirectoryPrincipalResolverProperties[]{jaasPrincipal, personDirectory});
            }).collect(Collectors.toList()));
        }

        @ConditionalOnMissingBean(name={"jaasAuthenticationHandlers"})
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        @Bean
        public BeanContainer<AuthenticationHandler> jaasAuthenticationHandlers(CasConfigurationProperties casProperties, ConfigurableApplicationContext applicationContext, @Qualifier(value="servicesManager") ServicesManager servicesManager, @Qualifier(value="jaasPrincipalFactory") PrincipalFactory jaasPrincipalFactory) {
            return BeanContainer.of(casProperties.getAuthn().getJaas().stream().filter(jaas -> StringUtils.isNotBlank((CharSequence)jaas.getRealm())).map(jaas -> {
                JaasAuthenticationHandler h = new JaasAuthenticationHandler(jaas.getName(), servicesManager, jaasPrincipalFactory, Integer.valueOf(jaas.getOrder()));
                h.setState(jaas.getState());
                h.setKerberosKdcSystemProperty(jaas.getKerberosKdcSystemProperty());
                h.setKerberosRealmSystemProperty(jaas.getKerberosRealmSystemProperty());
                h.setRealm(jaas.getRealm());
                h.setPasswordEncoder(PasswordEncoderUtils.newPasswordEncoder((PasswordEncoderProperties)jaas.getPasswordEncoder(), (ApplicationContext)applicationContext));
                if (StringUtils.isNotBlank((CharSequence)jaas.getLoginConfigType())) {
                    h.setLoginConfigType(jaas.getLoginConfigType());
                }
                if (StringUtils.isNotBlank((CharSequence)jaas.getLoginConfigurationFile())) {
                    h.setLoginConfigurationFile(new File(jaas.getLoginConfigurationFile()));
                }
                PasswordPolicyProperties passwordPolicy = jaas.getPasswordPolicy();
                h.setPasswordPolicyHandlingStrategy(CoreAuthenticationUtils.newPasswordPolicyHandlingStrategy((PasswordPolicyProperties)passwordPolicy, (ApplicationContext)applicationContext));
                if (passwordPolicy.isEnabled()) {
                    LOGGER.debug("Password policy is enabled for JAAS. Constructing password policy configuration for [{}]", (Object)jaas.getRealm());
                    PasswordPolicyContext cfg = new PasswordPolicyContext(passwordPolicy);
                    if (passwordPolicy.isAccountStateHandlingEnabled()) {
                        cfg.setAccountStateHandler((response, configuration) -> new ArrayList(0));
                    } else {
                        LOGGER.debug("Handling account states is disabled via CAS configuration");
                    }
                    h.setPasswordPolicyConfiguration(cfg);
                }
                h.setPrincipalNameTransformer(PrincipalNameTransformerUtils.newPrincipalNameTransformer((PrincipalTransformationProperties)jaas.getPrincipalTransformation()));
                h.setCredentialSelectionPredicate(CoreAuthenticationUtils.newCredentialSelectionPredicate((String)jaas.getCredentialCriteria()));
                return h;
            }).collect(Collectors.toList()));
        }

        @ConditionalOnMissingBean(name={"jaasAuthenticationEventExecutionPlanConfigurer"})
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public AuthenticationEventExecutionPlanConfigurer jaasAuthenticationEventExecutionPlanConfigurer(@Qualifier(value="jaasAuthenticationHandlers") BeanContainer<AuthenticationHandler> jaasAuthenticationHandlers, @Qualifier(value="jaasPersonDirectoryPrincipalResolvers") BeanContainer<PrincipalResolver> jaasPersonDirectoryPrincipalResolvers) {
            return plan -> plan.registerAuthenticationHandlerWithPrincipalResolvers(jaasAuthenticationHandlers.toList(), jaasPersonDirectoryPrincipalResolvers.toList());
        }
    }

    @Configuration(value="CasCoreAuthenticationHandlersAcceptConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasCoreAuthenticationHandlersAcceptConfiguration {
        private static Map<String, String> getParsedUsers(CasConfigurationProperties casProperties) {
            AcceptAuthenticationProperties accept = casProperties.getAuthn().getAccept();
            String usersProperty = accept.getUsers();
            if (accept.isEnabled() && StringUtils.isNotBlank((CharSequence)usersProperty) && usersProperty.contains("::")) {
                Pattern pattern = Pattern.compile("::");
                return Stream.of(usersProperty.split(",")).map(pattern::split).collect(Collectors.toMap(userAndPassword -> userAndPassword[0], userAndPassword -> userAndPassword[1]));
            }
            return new HashMap<String, String>(0);
        }

        @ConditionalOnMissingBean(name={"acceptPasswordPolicyConfiguration"})
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public PasswordPolicyContext acceptPasswordPolicyConfiguration() {
            return new PasswordPolicyContext();
        }

        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        @Bean
        @ConditionalOnMissingBean(name={"acceptUsersAuthenticationHandler"})
        public AuthenticationHandler acceptUsersAuthenticationHandler(CasConfigurationProperties casProperties, ConfigurableApplicationContext applicationContext, @Qualifier(value="servicesManager") ServicesManager servicesManager, @Qualifier(value="acceptUsersPrincipalFactory") PrincipalFactory acceptUsersPrincipalFactory, @Qualifier(value="acceptPasswordPolicyConfiguration") PasswordPolicyContext acceptPasswordPolicyConfiguration) {
            AcceptAuthenticationProperties props = casProperties.getAuthn().getAccept();
            AcceptUsersAuthenticationHandler h = new AcceptUsersAuthenticationHandler(props.getName(), servicesManager, acceptUsersPrincipalFactory, Integer.valueOf(props.getOrder()), CasCoreAuthenticationHandlersAcceptConfiguration.getParsedUsers(casProperties));
            h.setState(props.getState());
            h.setPasswordEncoder(PasswordEncoderUtils.newPasswordEncoder((PasswordEncoderProperties)props.getPasswordEncoder(), (ApplicationContext)applicationContext));
            h.setPasswordPolicyConfiguration(acceptPasswordPolicyConfiguration);
            h.setCredentialSelectionPredicate(CoreAuthenticationUtils.newCredentialSelectionPredicate((String)props.getCredentialCriteria()));
            h.setPrincipalNameTransformer(PrincipalNameTransformerUtils.newPrincipalNameTransformer((PrincipalTransformationProperties)props.getPrincipalTransformation()));
            PasswordPolicyProperties passwordPolicy = props.getPasswordPolicy();
            h.setPasswordPolicyHandlingStrategy(CoreAuthenticationUtils.newPasswordPolicyHandlingStrategy((PasswordPolicyProperties)passwordPolicy, (ApplicationContext)applicationContext));
            if (passwordPolicy.isEnabled()) {
                PasswordPolicyContext cfg = new PasswordPolicyContext(passwordPolicy);
                if (passwordPolicy.isAccountStateHandlingEnabled()) {
                    cfg.setAccountStateHandler((response, configuration) -> new ArrayList(0));
                } else {
                    LOGGER.debug("Handling account states is disabled via CAS configuration");
                }
                h.setPasswordPolicyConfiguration(cfg);
            }
            return h;
        }

        @ConditionalOnMissingBean(name={"acceptUsersPrincipalFactory"})
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public PrincipalFactory acceptUsersPrincipalFactory() {
            return PrincipalFactoryUtils.newPrincipalFactory();
        }
    }

    @Configuration(value="CasCoreAuthenticationHandlersProxyConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasCoreAuthenticationHandlersProxyConfiguration {
        private static final BeanCondition CONDITION = BeanCondition.on((String)"cas.sso.proxy-authn-enabled").isTrue().evenIfMissing();

        @Bean
        @ConditionalOnMissingBean(name={"proxyAuthenticationHandler"})
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public AuthenticationHandler proxyAuthenticationHandler(ConfigurableApplicationContext applicationContext, @Qualifier(value="servicesManager") ServicesManager servicesManager, @Qualifier(value="proxyPrincipalFactory") PrincipalFactory proxyPrincipalFactory, @Qualifier(value="supportsTrustStoreSslSocketFactoryHttpClient") HttpClient supportsTrustStoreSslSocketFactoryHttpClient) throws Exception {
            return (AuthenticationHandler)BeanSupplier.of(AuthenticationHandler.class).when(CONDITION.given((PropertyResolver)applicationContext.getEnvironment())).supply(() -> new HttpBasedServiceCredentialsAuthenticationHandler(null, servicesManager, proxyPrincipalFactory, Integer.valueOf(Integer.MIN_VALUE), supportsTrustStoreSslSocketFactoryHttpClient)).otherwiseProxy().get();
        }

        @ConditionalOnMissingBean(name={"proxyPrincipalFactory"})
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public PrincipalFactory proxyPrincipalFactory(ConfigurableApplicationContext applicationContext) throws Exception {
            return (PrincipalFactory)BeanSupplier.of(PrincipalFactory.class).when(CONDITION.given((PropertyResolver)applicationContext.getEnvironment())).supply(PrincipalFactoryUtils::newPrincipalFactory).otherwiseProxy().get();
        }

        @ConditionalOnMissingBean(name={"proxyPrincipalResolver"})
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public PrincipalResolver proxyPrincipalResolver(ConfigurableApplicationContext applicationContext, @Qualifier(value="proxyPrincipalFactory") PrincipalFactory proxyPrincipalFactory) throws Exception {
            return (PrincipalResolver)BeanSupplier.of(PrincipalResolver.class).when(CONDITION.given((PropertyResolver)applicationContext.getEnvironment())).supply(() -> new ProxyingPrincipalResolver(proxyPrincipalFactory)).otherwiseProxy().get();
        }

        @ConditionalOnMissingBean(name={"proxyAuthenticationEventExecutionPlanConfigurer"})
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public AuthenticationEventExecutionPlanConfigurer proxyAuthenticationEventExecutionPlanConfigurer(ConfigurableApplicationContext applicationContext, @Qualifier(value="proxyAuthenticationHandler") AuthenticationHandler proxyAuthenticationHandler, @Qualifier(value="proxyPrincipalResolver") PrincipalResolver proxyPrincipalResolver) throws Exception {
            return (AuthenticationEventExecutionPlanConfigurer)BeanSupplier.of(AuthenticationEventExecutionPlanConfigurer.class).when(CONDITION.given((PropertyResolver)applicationContext.getEnvironment())).supply(() -> plan -> plan.registerAuthenticationHandlerWithPrincipalResolver(proxyAuthenticationHandler, proxyPrincipalResolver)).otherwiseProxy().get();
        }
    }
}

