/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.cas.configuration.features.CasFeatureModule$FeatureCatalog
 *  org.apereo.cas.configuration.model.support.sms.GroovySmsProperties
 *  org.apereo.cas.configuration.model.support.sms.RestfulSmsProperties
 *  org.apereo.cas.util.spring.boot.ConditionalOnFeatureEnabled
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.beans.factory.annotation.Qualifier
 *  org.springframework.boot.autoconfigure.AutoConfiguration
 *  org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.context.HierarchicalMessageSource
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.ScopedProxyMode
 *  org.springframework.mail.javamail.JavaMailSender
 *  org.springframework.scheduling.annotation.EnableScheduling
 */
package org.apereo.cas.config;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.configuration.features.CasFeatureModule;
import org.apereo.cas.configuration.model.support.sms.GroovySmsProperties;
import org.apereo.cas.configuration.model.support.sms.RestfulSmsProperties;
import org.apereo.cas.notifications.CommunicationsManager;
import org.apereo.cas.notifications.DefaultCommunicationsManager;
import org.apereo.cas.notifications.push.DefaultNotificationSender;
import org.apereo.cas.notifications.push.NotificationSender;
import org.apereo.cas.notifications.push.NotificationSenderExecutionPlanConfigurer;
import org.apereo.cas.notifications.sms.GroovySmsSender;
import org.apereo.cas.notifications.sms.RestfulSmsSender;
import org.apereo.cas.notifications.sms.SmsSender;
import org.apereo.cas.util.spring.boot.ConditionalOnFeatureEnabled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.HierarchicalMessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableConfigurationProperties(value={CasConfigurationProperties.class})
@ConditionalOnFeatureEnabled(feature=CasFeatureModule.FeatureCatalog.Notifications)
@AutoConfiguration
public class CasCoreNotificationsConfiguration {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(CasCoreNotificationsConfiguration.class);

    @Bean
    @ConditionalOnMissingBean(name={"communicationsManager"})
    @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
    public CommunicationsManager communicationsManager(@Qualifier(value="messageSource") HierarchicalMessageSource messageSource, @Qualifier(value="mailSender") ObjectProvider<JavaMailSender> mailSender, @Qualifier(value="smsSender") SmsSender smsSender, @Qualifier(value="notificationSender") NotificationSender notificationSender) {
        return new DefaultCommunicationsManager(smsSender, (JavaMailSender)mailSender.getIfAvailable(), notificationSender, messageSource);
    }

    @Bean
    @ConditionalOnMissingBean(name={"smsSender"})
    @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
    public SmsSender smsSender(CasConfigurationProperties casProperties) {
        GroovySmsProperties groovy = casProperties.getSmsProvider().getGroovy();
        if (groovy.getLocation() != null) {
            return new GroovySmsSender(groovy.getLocation());
        }
        RestfulSmsProperties rest = casProperties.getSmsProvider().getRest();
        if (StringUtils.isNotBlank((CharSequence)rest.getUrl())) {
            return new RestfulSmsSender(rest);
        }
        return SmsSender.noOp();
    }

    @Bean
    @ConditionalOnMissingBean(name={"notificationSender"})
    @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
    public NotificationSender notificationSender(ObjectProvider<List<NotificationSenderExecutionPlanConfigurer>> configurerProviders) {
        List configurers = Optional.ofNullable((List)configurerProviders.getIfAvailable()).orElseGet(ArrayList::new);
        List<NotificationSender> results = configurers.stream().map(cfg -> {
            LOGGER.trace("Configuring notification sender [{}]", (Object)cfg.getName());
            return cfg.configureNotificationSender();
        }).sorted(Comparator.comparing(NotificationSender::getOrder)).collect(Collectors.toList());
        return new DefaultNotificationSender(results);
    }
}

