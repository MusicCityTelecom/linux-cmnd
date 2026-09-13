/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.mail.internet.MimeMessage
 *  lombok.Generated
 *  org.apache.commons.lang3.ArrayUtils
 *  org.apache.commons.lang3.ObjectUtils
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.authentication.principal.Principal
 *  org.apereo.cas.util.LoggingUtils
 *  org.apereo.cas.util.RegexUtils
 *  org.apereo.cas.util.spring.SpringExpressionLanguageValueResolver
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.context.HierarchicalMessageSource
 *  org.springframework.mail.javamail.JavaMailSender
 *  org.springframework.mail.javamail.MimeMessageHelper
 */
package org.apereo.cas.notifications;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.internet.MimeMessage;
import lombok.Generated;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.notifications.CommunicationsManager;
import org.apereo.cas.notifications.mail.EmailCommunicationResult;
import org.apereo.cas.notifications.mail.EmailMessageRequest;
import org.apereo.cas.notifications.push.NotificationSender;
import org.apereo.cas.notifications.sms.SmsRequest;
import org.apereo.cas.notifications.sms.SmsSender;
import org.apereo.cas.util.LoggingUtils;
import org.apereo.cas.util.RegexUtils;
import org.apereo.cas.util.spring.SpringExpressionLanguageValueResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.HierarchicalMessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class DefaultCommunicationsManager
implements CommunicationsManager {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultCommunicationsManager.class);
    private final SmsSender smsSender;
    private final JavaMailSender mailSender;
    private final NotificationSender notificationSender;
    private final HierarchicalMessageSource messageSource;

    @Override
    public boolean isMailSenderDefined() {
        return this.mailSender != null;
    }

    @Override
    public boolean isSmsSenderDefined() {
        return this.smsSender != null && this.smsSender.canSend();
    }

    @Override
    public boolean isNotificationSenderDefined() {
        return this.notificationSender != null && this.notificationSender.canSend();
    }

    @Override
    public boolean notify(Principal principal, String title, String body) {
        return this.notificationSender.notify(principal, Map.of("title", title, "message", body));
    }

    @Override
    public EmailCommunicationResult email(EmailMessageRequest emailRequest) {
        List<String> recipients = emailRequest.getRecipients();
        try {
            LOGGER.trace("Attempting to send email [{}] to [{}]", (Object)emailRequest.getBody(), recipients);
            if (!this.isMailSenderDefined() || emailRequest.getEmailProperties().isUndefined() || recipients.isEmpty()) {
                throw new IllegalAccessException("Could not send email; from/to/subject/text or email settings are undefined.");
            }
            MimeMessage message = this.mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setTo(recipients.toArray(ArrayUtils.EMPTY_STRING_ARRAY));
            helper.setText(emailRequest.getBody(), emailRequest.getEmailProperties().isHtml());
            String subject = this.determineEmailSubject(emailRequest);
            helper.setSubject(subject);
            helper.setFrom(emailRequest.getEmailProperties().getFrom());
            if (StringUtils.isNotBlank((CharSequence)emailRequest.getEmailProperties().getReplyTo())) {
                helper.setReplyTo(emailRequest.getEmailProperties().getReplyTo());
            }
            helper.setValidateAddresses(emailRequest.getEmailProperties().isValidateAddresses());
            helper.setPriority(emailRequest.getEmailProperties().getPriority());
            helper.setCc(emailRequest.getEmailProperties().getCc().toArray(ArrayUtils.EMPTY_STRING_ARRAY));
            helper.setBcc(emailRequest.getEmailProperties().getBcc().toArray(ArrayUtils.EMPTY_STRING_ARRAY));
            this.mailSender.send(message);
            return ((EmailCommunicationResult.EmailCommunicationResultBuilder)((EmailCommunicationResult.EmailCommunicationResultBuilder)((EmailCommunicationResult.EmailCommunicationResultBuilder)EmailCommunicationResult.builder().success(true)).to(recipients)).body(emailRequest.getBody())).build();
        }
        catch (Exception ex) {
            LoggingUtils.error((Logger)LOGGER, (Throwable)ex);
            return ((EmailCommunicationResult.EmailCommunicationResultBuilder)((EmailCommunicationResult.EmailCommunicationResultBuilder)((EmailCommunicationResult.EmailCommunicationResultBuilder)EmailCommunicationResult.builder().success(false)).to(recipients)).body(emailRequest.getBody())).build();
        }
    }

    protected String determineEmailSubject(EmailMessageRequest emailRequest) {
        String subject = emailRequest.getEmailProperties().getSubject();
        Pattern pattern = RegexUtils.createPattern((String)"#\\{(.+)\\}");
        Matcher matcher = pattern.matcher(subject);
        if (matcher.find()) {
            ArrayList<String> args = new ArrayList<String>();
            if (emailRequest.getPrincipal() != null) {
                args.add(emailRequest.getPrincipal().getId());
            }
            return this.messageSource.getMessage(matcher.group(1), args.toArray(), "Email Subject", (Locale)ObjectUtils.defaultIfNull((Object)emailRequest.getLocale(), (Object)Locale.getDefault()));
        }
        return SpringExpressionLanguageValueResolver.getInstance().resolve(subject);
    }

    @Override
    public boolean sms(SmsRequest smsRequest) {
        String recipient = smsRequest.getRecipient();
        if (!this.isSmsSenderDefined() || !smsRequest.isSufficient()) {
            LOGGER.warn("Could not send SMS to [{}]; No from/text is found or SMS settings are undefined.", (Object)recipient);
            return false;
        }
        return this.smsSender.send(smsRequest.getFrom(), recipient, smsRequest.getText());
    }

    @Override
    public boolean validate() {
        if (!this.isMailSenderDefined()) {
            LOGGER.info("CAS will not send emails because settings are undefined to account for email servers");
        }
        if (!this.isSmsSenderDefined()) {
            LOGGER.info("CAS will not send sms messages because settings are undefined to account for sms providers");
        }
        if (!this.isNotificationSenderDefined()) {
            LOGGER.info("CAS will not send notifications because providers are undefined to handle messages");
        }
        return this.isMailSenderDefined() || this.isSmsSenderDefined() || this.isNotificationSenderDefined();
    }

    @Generated
    public DefaultCommunicationsManager(SmsSender smsSender, JavaMailSender mailSender, NotificationSender notificationSender, HierarchicalMessageSource messageSource) {
        this.smsSender = smsSender;
        this.mailSender = mailSender;
        this.notificationSender = notificationSender;
        this.messageSource = messageSource;
    }
}

