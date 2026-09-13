/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.boot.context.properties.PropertyMapper
 *  org.springframework.context.annotation.Bean
 *  org.springframework.core.env.Environment
 *  org.springframework.ldap.core.ContextSource
 *  org.springframework.ldap.core.LdapOperations
 *  org.springframework.ldap.core.LdapTemplate
 *  org.springframework.ldap.core.support.DirContextAuthenticationStrategy
 *  org.springframework.ldap.core.support.LdapContextSource
 */
package org.springframework.boot.autoconfigure.ldap;

import java.util.Collections;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.ldap.LdapProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.DirContextAuthenticationStrategy;
import org.springframework.ldap.core.support.LdapContextSource;

@AutoConfiguration
@ConditionalOnClass(value={ContextSource.class})
@EnableConfigurationProperties(value={LdapProperties.class})
public class LdapAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public LdapContextSource ldapContextSource(LdapProperties properties, Environment environment, ObjectProvider<DirContextAuthenticationStrategy> dirContextAuthenticationStrategy) {
        LdapContextSource source = new LdapContextSource();
        dirContextAuthenticationStrategy.ifUnique(arg_0 -> ((LdapContextSource)source).setAuthenticationStrategy(arg_0));
        PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();
        propertyMapper.from((Object)properties.getUsername()).to(arg_0 -> ((LdapContextSource)source).setUserDn(arg_0));
        propertyMapper.from((Object)properties.getPassword()).to(arg_0 -> ((LdapContextSource)source).setPassword(arg_0));
        propertyMapper.from((Object)properties.getAnonymousReadOnly()).to(arg_0 -> ((LdapContextSource)source).setAnonymousReadOnly(arg_0));
        propertyMapper.from((Object)properties.getBase()).to(arg_0 -> ((LdapContextSource)source).setBase(arg_0));
        propertyMapper.from((Object)properties.determineUrls(environment)).to(arg_0 -> ((LdapContextSource)source).setUrls(arg_0));
        propertyMapper.from(properties.getBaseEnvironment()).to(baseEnvironment -> source.setBaseEnvironmentProperties(Collections.unmodifiableMap(baseEnvironment)));
        return source;
    }

    @Bean
    @ConditionalOnMissingBean(value={LdapOperations.class})
    public LdapTemplate ldapTemplate(LdapProperties properties, ContextSource contextSource) {
        LdapProperties.Template template = properties.getTemplate();
        PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();
        LdapTemplate ldapTemplate = new LdapTemplate(contextSource);
        propertyMapper.from((Object)template.isIgnorePartialResultException()).to(arg_0 -> ((LdapTemplate)ldapTemplate).setIgnorePartialResultException(arg_0));
        propertyMapper.from((Object)template.isIgnoreNameNotFoundException()).to(arg_0 -> ((LdapTemplate)ldapTemplate).setIgnoreNameNotFoundException(arg_0));
        propertyMapper.from((Object)template.isIgnoreSizeLimitExceededException()).to(arg_0 -> ((LdapTemplate)ldapTemplate).setIgnoreSizeLimitExceededException(arg_0));
        return ldapTemplate;
    }
}

