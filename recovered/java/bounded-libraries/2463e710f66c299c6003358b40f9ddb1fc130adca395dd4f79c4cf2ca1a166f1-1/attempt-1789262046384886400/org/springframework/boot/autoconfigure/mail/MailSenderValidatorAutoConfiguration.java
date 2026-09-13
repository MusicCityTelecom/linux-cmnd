/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.mail.MessagingException
 *  org.springframework.mail.javamail.JavaMailSenderImpl
 */
package org.springframework.boot.autoconfigure.mail;

import javax.mail.MessagingException;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@AutoConfiguration(after={MailSenderAutoConfiguration.class})
@ConditionalOnProperty(prefix="spring.mail", value={"test-connection"})
@ConditionalOnSingleCandidate(value=JavaMailSenderImpl.class)
public class MailSenderValidatorAutoConfiguration {
    private final JavaMailSenderImpl mailSender;

    public MailSenderValidatorAutoConfiguration(JavaMailSenderImpl mailSender) {
        this.mailSender = mailSender;
        this.validateConnection();
    }

    public void validateConnection() {
        try {
            this.mailSender.testConnection();
        }
        catch (MessagingException ex) {
            throw new IllegalStateException("Mail server is not available", ex);
        }
    }
}

