/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Multimap
 *  lombok.Generated
 *  org.apereo.cas.adaptors.jdbc.AbstractJdbcUsernamePasswordAuthenticationHandler
 *  org.apereo.cas.adaptors.jdbc.BindModeSearchDatabaseAuthenticationHandler
 *  org.apereo.cas.adaptors.jdbc.JdbcAuthenticationUtils
 *  org.apereo.cas.adaptors.jdbc.QueryAndEncodeDatabaseAuthenticationHandler
 *  org.apereo.cas.adaptors.jdbc.QueryDatabaseAuthenticationHandler
 *  org.apereo.cas.adaptors.jdbc.SearchModeSearchDatabaseAuthenticationHandler
 *  org.apereo.cas.authentication.AuthenticationEventExecutionPlanConfigurer
 *  org.apereo.cas.authentication.AuthenticationHandler
 *  org.apereo.cas.authentication.CoreAuthenticationUtils
 *  org.apereo.cas.authentication.principal.PrincipalFactory
 *  org.apereo.cas.authentication.principal.PrincipalFactoryUtils
 *  org.apereo.cas.authentication.principal.PrincipalResolver
 *  org.apereo.cas.authentication.support.password.PasswordPolicyContext
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.cas.configuration.features.CasFeatureModule$FeatureCatalog
 *  org.apereo.cas.configuration.model.support.jdbc.JdbcAuthenticationProperties
 *  org.apereo.cas.configuration.model.support.jdbc.authn.BaseJdbcAuthenticationProperties
 *  org.apereo.cas.configuration.model.support.jpa.AbstractJpaProperties
 *  org.apereo.cas.configuration.support.JpaBeans
 *  org.apereo.cas.services.ServicesManager
 *  org.apereo.cas.util.CollectionUtils
 *  org.apereo.cas.util.spring.boot.ConditionalOnFeatureEnabled
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Qualifier
 *  org.springframework.boot.autoconfigure.AutoConfiguration
 *  org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.context.ConfigurableApplicationContext
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.ScopedProxyMode
 */
package org.apereo.cas.adaptors.jdbc.config;

import com.google.common.collect.Multimap;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import javax.sql.DataSource;
import lombok.Generated;
import org.apereo.cas.adaptors.jdbc.AbstractJdbcUsernamePasswordAuthenticationHandler;
import org.apereo.cas.adaptors.jdbc.BindModeSearchDatabaseAuthenticationHandler;
import org.apereo.cas.adaptors.jdbc.JdbcAuthenticationUtils;
import org.apereo.cas.adaptors.jdbc.QueryAndEncodeDatabaseAuthenticationHandler;
import org.apereo.cas.adaptors.jdbc.QueryDatabaseAuthenticationHandler;
import org.apereo.cas.adaptors.jdbc.SearchModeSearchDatabaseAuthenticationHandler;
import org.apereo.cas.authentication.AuthenticationEventExecutionPlanConfigurer;
import org.apereo.cas.authentication.AuthenticationHandler;
import org.apereo.cas.authentication.CoreAuthenticationUtils;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.authentication.principal.PrincipalFactoryUtils;
import org.apereo.cas.authentication.principal.PrincipalResolver;
import org.apereo.cas.authentication.support.password.PasswordPolicyContext;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.configuration.features.CasFeatureModule;
import org.apereo.cas.configuration.model.support.jdbc.JdbcAuthenticationProperties;
import org.apereo.cas.configuration.model.support.jdbc.authn.BaseJdbcAuthenticationProperties;
import org.apereo.cas.configuration.model.support.jpa.AbstractJpaProperties;
import org.apereo.cas.configuration.support.JpaBeans;
import org.apereo.cas.services.ServicesManager;
import org.apereo.cas.util.CollectionUtils;
import org.apereo.cas.util.spring.boot.ConditionalOnFeatureEnabled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ScopedProxyMode;

@EnableConfigurationProperties(value={CasConfigurationProperties.class})
@ConditionalOnFeatureEnabled(feature=CasFeatureModule.FeatureCatalog.Authentication, module="jdbc")
@AutoConfiguration
public class CasJdbcAuthenticationConfiguration {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(CasJdbcAuthenticationConfiguration.class);

    @ConditionalOnMissingBean(name={"queryAndEncodeDatabaseAuthenticationHandlers"})
    @Bean
    @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
    public Collection<AuthenticationHandler> queryAndEncodeDatabaseAuthenticationHandlers(@Qualifier(value="queryPasswordPolicyConfiguration") PasswordPolicyContext queryPasswordPolicyConfiguration, ConfigurableApplicationContext applicationContext, @Qualifier(value="servicesManager") ServicesManager servicesManager, @Qualifier(value="jdbcPrincipalFactory") PrincipalFactory jdbcPrincipalFactory, CasConfigurationProperties casProperties) {
        HashSet<AuthenticationHandler> handlers = new HashSet<AuthenticationHandler>();
        JdbcAuthenticationProperties jdbc = casProperties.getAuthn().getJdbc();
        jdbc.getEncode().forEach(b -> {
            QueryAndEncodeDatabaseAuthenticationHandler h = new QueryAndEncodeDatabaseAuthenticationHandler(b, servicesManager, jdbcPrincipalFactory, (DataSource)JpaBeans.newDataSource((AbstractJpaProperties)b));
            JdbcAuthenticationUtils.configureJdbcAuthenticationHandler((AbstractJdbcUsernamePasswordAuthenticationHandler)h, (PasswordPolicyContext)queryPasswordPolicyConfiguration, (BaseJdbcAuthenticationProperties)b, (ConfigurableApplicationContext)applicationContext);
            handlers.add((AuthenticationHandler)h);
        });
        return handlers;
    }

    @ConditionalOnMissingBean(name={"bindModeSearchDatabaseAuthenticationHandlers"})
    @Bean
    @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
    public Collection<AuthenticationHandler> bindModeSearchDatabaseAuthenticationHandlers(@Qualifier(value="bindSearchPasswordPolicyConfiguration") PasswordPolicyContext bindSearchPasswordPolicyConfiguration, ConfigurableApplicationContext applicationContext, @Qualifier(value="servicesManager") ServicesManager servicesManager, @Qualifier(value="jdbcPrincipalFactory") PrincipalFactory jdbcPrincipalFactory, CasConfigurationProperties casProperties) {
        HashSet<AuthenticationHandler> handlers = new HashSet<AuthenticationHandler>();
        JdbcAuthenticationProperties jdbc = casProperties.getAuthn().getJdbc();
        jdbc.getBind().forEach(b -> {
            BindModeSearchDatabaseAuthenticationHandler h = new BindModeSearchDatabaseAuthenticationHandler(b.getName(), servicesManager, jdbcPrincipalFactory, Integer.valueOf(b.getOrder()), (DataSource)JpaBeans.newDataSource((AbstractJpaProperties)b));
            JdbcAuthenticationUtils.configureJdbcAuthenticationHandler((AbstractJdbcUsernamePasswordAuthenticationHandler)h, (PasswordPolicyContext)bindSearchPasswordPolicyConfiguration, (BaseJdbcAuthenticationProperties)b, (ConfigurableApplicationContext)applicationContext);
            handlers.add((AuthenticationHandler)h);
        });
        return handlers;
    }

    @ConditionalOnMissingBean(name={"queryDatabaseAuthenticationHandlers"})
    @Bean
    @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
    public Collection<AuthenticationHandler> queryDatabaseAuthenticationHandlers(@Qualifier(value="queryPasswordPolicyConfiguration") PasswordPolicyContext queryPasswordPolicyConfiguration, ConfigurableApplicationContext applicationContext, @Qualifier(value="servicesManager") ServicesManager servicesManager, @Qualifier(value="jdbcPrincipalFactory") PrincipalFactory jdbcPrincipalFactory, CasConfigurationProperties casProperties) {
        HashSet<AuthenticationHandler> handlers = new HashSet<AuthenticationHandler>();
        JdbcAuthenticationProperties jdbc = casProperties.getAuthn().getJdbc();
        jdbc.getQuery().forEach(b -> {
            Multimap attributes = CoreAuthenticationUtils.transformPrincipalAttributesListIntoMultiMap((List)b.getPrincipalAttributeList());
            LOGGER.trace("Created and mapped principal attributes [{}] for [{}]...", (Object)attributes, (Object)b.getName());
            QueryDatabaseAuthenticationHandler h = new QueryDatabaseAuthenticationHandler(b, servicesManager, jdbcPrincipalFactory, (DataSource)JpaBeans.newDataSource((AbstractJpaProperties)b), CollectionUtils.wrap((Multimap)attributes));
            JdbcAuthenticationUtils.configureJdbcAuthenticationHandler((AbstractJdbcUsernamePasswordAuthenticationHandler)h, (PasswordPolicyContext)queryPasswordPolicyConfiguration, (BaseJdbcAuthenticationProperties)b, (ConfigurableApplicationContext)applicationContext);
            handlers.add((AuthenticationHandler)h);
        });
        return handlers;
    }

    @ConditionalOnMissingBean(name={"searchModeSearchDatabaseAuthenticationHandlers"})
    @Bean
    @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
    public Collection<AuthenticationHandler> searchModeSearchDatabaseAuthenticationHandlers(@Qualifier(value="searchModePasswordPolicyConfiguration") PasswordPolicyContext searchModePasswordPolicyConfiguration, ConfigurableApplicationContext applicationContext, @Qualifier(value="servicesManager") ServicesManager servicesManager, @Qualifier(value="jdbcPrincipalFactory") PrincipalFactory jdbcPrincipalFactory, CasConfigurationProperties casProperties) {
        HashSet<AuthenticationHandler> handlers = new HashSet<AuthenticationHandler>();
        JdbcAuthenticationProperties jdbc = casProperties.getAuthn().getJdbc();
        jdbc.getSearch().forEach(b -> {
            SearchModeSearchDatabaseAuthenticationHandler h = new SearchModeSearchDatabaseAuthenticationHandler(b, servicesManager, jdbcPrincipalFactory, (DataSource)JpaBeans.newDataSource((AbstractJpaProperties)b));
            JdbcAuthenticationUtils.configureJdbcAuthenticationHandler((AbstractJdbcUsernamePasswordAuthenticationHandler)h, (PasswordPolicyContext)searchModePasswordPolicyConfiguration, (BaseJdbcAuthenticationProperties)b, (ConfigurableApplicationContext)applicationContext);
            handlers.add((AuthenticationHandler)h);
        });
        return handlers;
    }

    @ConditionalOnMissingBean(name={"jdbcAuthenticationHandlers"})
    @Bean
    @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
    public Collection<AuthenticationHandler> jdbcAuthenticationHandlers(@Qualifier(value="queryAndEncodeDatabaseAuthenticationHandlers") Collection<AuthenticationHandler> queryAndEncodeDatabaseAuthenticationHandlers, @Qualifier(value="bindModeSearchDatabaseAuthenticationHandlers") Collection<AuthenticationHandler> bindModeSearchDatabaseAuthenticationHandlers, @Qualifier(value="queryDatabaseAuthenticationHandlers") Collection<AuthenticationHandler> queryDatabaseAuthenticationHandlers, @Qualifier(value="searchModeSearchDatabaseAuthenticationHandlers") Collection<AuthenticationHandler> searchModeSearchDatabaseAuthenticationHandlers) {
        HashSet<AuthenticationHandler> handlers = new HashSet<AuthenticationHandler>();
        handlers.addAll(bindModeSearchDatabaseAuthenticationHandlers);
        handlers.addAll(queryAndEncodeDatabaseAuthenticationHandlers);
        handlers.addAll(queryDatabaseAuthenticationHandlers);
        handlers.addAll(searchModeSearchDatabaseAuthenticationHandlers);
        return handlers;
    }

    @ConditionalOnMissingBean(name={"jdbcPrincipalFactory"})
    @Bean
    @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
    public PrincipalFactory jdbcPrincipalFactory() {
        return PrincipalFactoryUtils.newPrincipalFactory();
    }

    @ConditionalOnMissingBean(name={"queryAndEncodePasswordPolicyConfiguration"})
    @Bean
    @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
    public PasswordPolicyContext queryAndEncodePasswordPolicyConfiguration() {
        return new PasswordPolicyContext();
    }

    @ConditionalOnMissingBean(name={"searchModePasswordPolicyConfiguration"})
    @Bean
    @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
    public PasswordPolicyContext searchModePasswordPolicyConfiguration() {
        return new PasswordPolicyContext();
    }

    @ConditionalOnMissingBean(name={"queryPasswordPolicyConfiguration"})
    @Bean
    @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
    public PasswordPolicyContext queryPasswordPolicyConfiguration() {
        return new PasswordPolicyContext();
    }

    @ConditionalOnMissingBean(name={"bindSearchPasswordPolicyConfiguration"})
    @Bean
    @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
    public PasswordPolicyContext bindSearchPasswordPolicyConfiguration() {
        return new PasswordPolicyContext();
    }

    @ConditionalOnMissingBean(name={"jdbcAuthenticationEventExecutionPlanConfigurer"})
    @Bean
    @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
    public AuthenticationEventExecutionPlanConfigurer jdbcAuthenticationEventExecutionPlanConfigurer(@Qualifier(value="jdbcAuthenticationHandlers") Collection<AuthenticationHandler> jdbcAuthenticationHandlers, @Qualifier(value="defaultPrincipalResolver") PrincipalResolver defaultPrincipalResolver) {
        return plan -> jdbcAuthenticationHandlers.forEach(h -> plan.registerAuthenticationHandlerWithPrincipalResolver(h, defaultPrincipalResolver));
    }
}

