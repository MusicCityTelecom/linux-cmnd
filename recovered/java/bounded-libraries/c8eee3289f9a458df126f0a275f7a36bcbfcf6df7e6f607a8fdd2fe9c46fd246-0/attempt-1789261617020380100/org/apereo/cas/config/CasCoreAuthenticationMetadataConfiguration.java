/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.AuthenticationEventExecutionPlan
 *  org.apereo.cas.authentication.AuthenticationEventExecutionPlanConfigurer
 *  org.apereo.cas.authentication.AuthenticationMetaDataPopulator
 *  org.apereo.cas.authentication.metadata.AuthenticationCredentialTypeMetaDataPopulator
 *  org.apereo.cas.authentication.metadata.AuthenticationDateAttributeMetaDataPopulator
 *  org.apereo.cas.authentication.metadata.CacheCredentialsCipherExecutor
 *  org.apereo.cas.authentication.metadata.CacheCredentialsMetaDataPopulator
 *  org.apereo.cas.authentication.metadata.ClientInfoAuthenticationMetaDataPopulator
 *  org.apereo.cas.authentication.metadata.CredentialCustomFieldsAttributeMetaDataPopulator
 *  org.apereo.cas.authentication.metadata.RememberMeAuthenticationMetaDataPopulator
 *  org.apereo.cas.authentication.metadata.SuccessfulHandlerMetaDataPopulator
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.cas.configuration.features.CasFeatureModule$FeatureCatalog
 *  org.apereo.cas.configuration.model.core.util.EncryptionJwtSigningJwtCryptographyProperties
 *  org.apereo.cas.util.cipher.CipherExecutorUtils
 *  org.apereo.cas.util.crypto.CipherExecutor
 *  org.apereo.cas.util.spring.beans.BeanCondition
 *  org.apereo.cas.util.spring.beans.BeanSupplier
 *  org.apereo.cas.util.spring.boot.ConditionalOnFeatureEnabled
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.beans.factory.annotation.Qualifier
 *  org.springframework.boot.autoconfigure.AutoConfiguration
 *  org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.context.ConfigurableApplicationContext
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.context.annotation.ScopedProxyMode
 *  org.springframework.core.env.PropertyResolver
 */
package org.apereo.cas.config;

import lombok.Generated;
import org.apereo.cas.authentication.AuthenticationEventExecutionPlan;
import org.apereo.cas.authentication.AuthenticationEventExecutionPlanConfigurer;
import org.apereo.cas.authentication.AuthenticationMetaDataPopulator;
import org.apereo.cas.authentication.metadata.AuthenticationCredentialTypeMetaDataPopulator;
import org.apereo.cas.authentication.metadata.AuthenticationDateAttributeMetaDataPopulator;
import org.apereo.cas.authentication.metadata.CacheCredentialsCipherExecutor;
import org.apereo.cas.authentication.metadata.CacheCredentialsMetaDataPopulator;
import org.apereo.cas.authentication.metadata.ClientInfoAuthenticationMetaDataPopulator;
import org.apereo.cas.authentication.metadata.CredentialCustomFieldsAttributeMetaDataPopulator;
import org.apereo.cas.authentication.metadata.RememberMeAuthenticationMetaDataPopulator;
import org.apereo.cas.authentication.metadata.SuccessfulHandlerMetaDataPopulator;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.configuration.features.CasFeatureModule;
import org.apereo.cas.configuration.model.core.util.EncryptionJwtSigningJwtCryptographyProperties;
import org.apereo.cas.util.cipher.CipherExecutorUtils;
import org.apereo.cas.util.crypto.CipherExecutor;
import org.apereo.cas.util.spring.beans.BeanCondition;
import org.apereo.cas.util.spring.beans.BeanSupplier;
import org.apereo.cas.util.spring.boot.ConditionalOnFeatureEnabled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.PropertyResolver;

@EnableConfigurationProperties(value={CasConfigurationProperties.class})
@ConditionalOnFeatureEnabled(feature=CasFeatureModule.FeatureCatalog.Authentication)
@AutoConfiguration
public class CasCoreAuthenticationMetadataConfiguration {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(CasCoreAuthenticationMetadataConfiguration.class);
    private static final BeanCondition CONDITION_CLEARPASS = BeanCondition.on((String)"cas.clearpass.cache-credential").isTrue();

    @Configuration(value="CasCoreAuthenticationMetadataExecutionPlanConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasCoreAuthenticationMetadataExecutionPlanConfiguration {
        @ConditionalOnMissingBean(name={"casCoreAuthenticationMetadataAuthenticationEventExecutionPlanConfigurer"})
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public AuthenticationEventExecutionPlanConfigurer casCoreAuthenticationMetadataAuthenticationEventExecutionPlanConfigurer(@Qualifier(value="authenticationCredentialTypeMetaDataPopulator") AuthenticationMetaDataPopulator authenticationCredentialTypeMetaDataPopulator, @Qualifier(value="credentialCustomFieldsAttributeMetaDataPopulator") AuthenticationMetaDataPopulator credentialCustomFieldsAttributeMetaDataPopulator, @Qualifier(value="authenticationDateMetaDataPopulator") AuthenticationMetaDataPopulator authenticationDateMetaDataPopulator, @Qualifier(value="clientInfoAuthenticationMetaDataPopulator") AuthenticationMetaDataPopulator clientInfoAuthenticationMetaDataPopulator, @Qualifier(value="rememberMeAuthenticationMetaDataPopulator") AuthenticationMetaDataPopulator rememberMeAuthenticationMetaDataPopulator, @Qualifier(value="successfulHandlerMetaDataPopulator") AuthenticationMetaDataPopulator successfulHandlerMetaDataPopulator, @Qualifier(value="cacheCredentialsMetaDataPopulator") ObjectProvider<AuthenticationMetaDataPopulator> cacheCredentialsMetaDataPopulator) {
            return plan -> {
                plan.registerAuthenticationMetadataPopulator(successfulHandlerMetaDataPopulator);
                plan.registerAuthenticationMetadataPopulator(rememberMeAuthenticationMetaDataPopulator);
                plan.registerAuthenticationMetadataPopulator(authenticationCredentialTypeMetaDataPopulator);
                plan.registerAuthenticationMetadataPopulator(authenticationDateMetaDataPopulator);
                plan.registerAuthenticationMetadataPopulator(credentialCustomFieldsAttributeMetaDataPopulator);
                plan.registerAuthenticationMetadataPopulator(clientInfoAuthenticationMetaDataPopulator);
                cacheCredentialsMetaDataPopulator.ifAvailable(arg_0 -> ((AuthenticationEventExecutionPlan)plan).registerAuthenticationMetadataPopulator(arg_0));
            };
        }
    }

    @Configuration(value="CasCoreAuthenticationMetadataClearPassConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasCoreAuthenticationMetadataClearPassConfiguration {
        @ConditionalOnMissingBean(name={"cacheCredentialsMetaDataPopulator"})
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public AuthenticationMetaDataPopulator cacheCredentialsMetaDataPopulator(ConfigurableApplicationContext applicationContext, @Qualifier(value="cacheCredentialsCipherExecutor") CipherExecutor cacheCredentialsCipherExecutor) throws Exception {
            return (AuthenticationMetaDataPopulator)BeanSupplier.of(AuthenticationMetaDataPopulator.class).when(CONDITION_CLEARPASS.given((PropertyResolver)applicationContext.getEnvironment())).supply(() -> {
                LOGGER.warn("CAS is configured to capture and cache credentials via Clearpass. Sharing the user credential with other applications is generally NOT recommended, may lead to security vulnerabilities and MUST only be used as a last resort.");
                return new CacheCredentialsMetaDataPopulator(cacheCredentialsCipherExecutor);
            }).otherwiseProxy().get();
        }
    }

    @Configuration(value="CasCoreAuthenticationMetadataPopulatorConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasCoreAuthenticationMetadataPopulatorConfiguration {
        @ConditionalOnMissingBean(name={"authenticationCredentialTypeMetaDataPopulator"})
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public AuthenticationMetaDataPopulator authenticationCredentialTypeMetaDataPopulator() {
            return new AuthenticationCredentialTypeMetaDataPopulator();
        }

        @ConditionalOnMissingBean(name={"credentialCustomFieldsAttributeMetaDataPopulator"})
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public AuthenticationMetaDataPopulator credentialCustomFieldsAttributeMetaDataPopulator() {
            return new CredentialCustomFieldsAttributeMetaDataPopulator();
        }

        @ConditionalOnMissingBean(name={"authenticationDateMetaDataPopulator"})
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public AuthenticationMetaDataPopulator authenticationDateMetaDataPopulator() {
            return new AuthenticationDateAttributeMetaDataPopulator();
        }

        @ConditionalOnMissingBean(name={"clientInfoAuthenticationMetaDataPopulator"})
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public AuthenticationMetaDataPopulator clientInfoAuthenticationMetaDataPopulator() {
            return new ClientInfoAuthenticationMetaDataPopulator();
        }

        @ConditionalOnMissingBean(name={"successfulHandlerMetaDataPopulator"})
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public AuthenticationMetaDataPopulator successfulHandlerMetaDataPopulator() {
            return new SuccessfulHandlerMetaDataPopulator();
        }

        @ConditionalOnMissingBean(name={"rememberMeAuthenticationMetaDataPopulator"})
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public AuthenticationMetaDataPopulator rememberMeAuthenticationMetaDataPopulator(CasConfigurationProperties casProperties) {
            return new RememberMeAuthenticationMetaDataPopulator(casProperties.getTicket().getTgt().getRememberMe());
        }
    }

    @Configuration(value="CasCoreAuthenticationMetadataCipherConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasCoreAuthenticationMetadataCipherConfiguration {
        @ConditionalOnMissingBean(name={"cacheCredentialsCipherExecutor"})
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public CipherExecutor cacheCredentialsCipherExecutor(ConfigurableApplicationContext applicationContext, CasConfigurationProperties casProperties) throws Exception {
            return (CipherExecutor)BeanSupplier.of(CipherExecutor.class).when(CONDITION_CLEARPASS.given((PropertyResolver)applicationContext.getEnvironment())).and(BeanCondition.on((String)"cas.clearpass.crypto.enabled").isTrue().given((PropertyResolver)applicationContext.getEnvironment())).supply(() -> {
                LOGGER.warn("CAS is configured to capture and cache credentials via Clearpass yet crypto operations for the cached password are turned off. Consider enabling the crypto configuration in CAS settings that allow the system to sign & encrypt the captured credential.");
                return CipherExecutorUtils.newStringCipherExecutor((EncryptionJwtSigningJwtCryptographyProperties)casProperties.getClearpass().getCrypto(), CacheCredentialsCipherExecutor.class);
            }).otherwise(CipherExecutor::noOp).get();
        }
    }
}

