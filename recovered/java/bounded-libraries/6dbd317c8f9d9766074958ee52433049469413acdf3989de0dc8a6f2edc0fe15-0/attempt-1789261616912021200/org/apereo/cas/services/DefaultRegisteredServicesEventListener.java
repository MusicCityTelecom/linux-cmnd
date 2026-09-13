/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.cas.configuration.model.core.services.ServiceRegistryProperties
 *  org.apereo.cas.configuration.model.support.email.EmailProperties
 *  org.apereo.cas.configuration.model.support.sms.SmsProperties
 *  org.apereo.cas.notifications.CommunicationsManager
 *  org.apereo.cas.notifications.mail.EmailMessageBodyBuilder
 *  org.apereo.cas.notifications.mail.EmailMessageRequest
 *  org.apereo.cas.notifications.sms.SmsBodyBuilder
 *  org.apereo.cas.notifications.sms.SmsRequest
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.services.ServicesManager
 *  org.apereo.cas.support.events.service.CasRegisteredServiceExpiredEvent
 *  org.apereo.cas.support.events.service.CasRegisteredServicesRefreshEvent
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.cloud.context.environment.EnvironmentChangeEvent
 */
package org.apereo.cas.services;

import java.util.List;
import java.util.Map;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.configuration.model.core.services.ServiceRegistryProperties;
import org.apereo.cas.configuration.model.support.email.EmailProperties;
import org.apereo.cas.configuration.model.support.sms.SmsProperties;
import org.apereo.cas.notifications.CommunicationsManager;
import org.apereo.cas.notifications.mail.EmailMessageBodyBuilder;
import org.apereo.cas.notifications.mail.EmailMessageRequest;
import org.apereo.cas.notifications.sms.SmsBodyBuilder;
import org.apereo.cas.notifications.sms.SmsRequest;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.RegisteredServicesEventListener;
import org.apereo.cas.services.ServicesManager;
import org.apereo.cas.support.events.service.CasRegisteredServiceExpiredEvent;
import org.apereo.cas.support.events.service.CasRegisteredServicesRefreshEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;

public class DefaultRegisteredServicesEventListener
implements RegisteredServicesEventListener {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultRegisteredServicesEventListener.class);
    private final ServicesManager servicesManager;
    private final CasConfigurationProperties casProperties;
    private final CommunicationsManager communicationsManager;

    @Override
    public void handleRefreshEvent(CasRegisteredServicesRefreshEvent event) {
        this.servicesManager.load();
    }

    @Override
    public void handleEnvironmentChangeEvent(EnvironmentChangeEvent event) {
        this.servicesManager.load();
    }

    @Override
    public void handleRegisteredServiceExpiredEvent(CasRegisteredServiceExpiredEvent event) {
        RegisteredService registeredService = event.getRegisteredService();
        List contacts = registeredService.getContacts();
        ServiceRegistryProperties serviceRegistry = this.casProperties.getServiceRegistry();
        String serviceName = (String)StringUtils.defaultIfBlank((CharSequence)registeredService.getName(), (CharSequence)registeredService.getServiceId());
        if (contacts == null || contacts.isEmpty()) {
            LOGGER.debug("No contacts are defined to be notified for policy changes to service [{}]", (Object)serviceName);
            return;
        }
        String logMessage = String.format("Sending notification to [{}] as service [{}] is %s from registry", event.isDeleted() ? "deleted" : "expired");
        LOGGER.info(logMessage, (Object)contacts, (Object)serviceName);
        this.communicationsManager.validate();
        if (this.communicationsManager.isMailSenderDefined()) {
            EmailProperties mail = serviceRegistry.getMail();
            String body = EmailMessageBodyBuilder.builder().properties(mail).parameters(Map.of("service", serviceName)).build().get();
            contacts.stream().filter(contact -> StringUtils.isNotBlank((CharSequence)contact.getEmail())).forEach(contact -> {
                EmailMessageRequest emailRequest = EmailMessageRequest.builder().emailProperties(mail).to(List.of(contact.getEmail())).body(body).build();
                this.communicationsManager.email(emailRequest);
            });
        }
        if (this.communicationsManager.isSmsSenderDefined()) {
            SmsProperties sms = serviceRegistry.getSms();
            String message = SmsBodyBuilder.builder().properties(sms).parameters(Map.of("service", serviceName)).build().get();
            contacts.stream().filter(contact -> StringUtils.isNotBlank((CharSequence)contact.getPhone())).forEach(contact -> {
                SmsRequest smsRequest = SmsRequest.builder().from(sms.getFrom()).to(contact.getPhone()).text(message).build();
                this.communicationsManager.sms(smsRequest);
            });
        }
    }

    @Generated
    public DefaultRegisteredServicesEventListener(ServicesManager servicesManager, CasConfigurationProperties casProperties, CommunicationsManager communicationsManager) {
        this.servicesManager = servicesManager;
        this.casProperties = casProperties;
        this.communicationsManager = communicationsManager;
    }
}

