/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.mail.javamail.JavaMailSenderImpl
 */
package org.springframework.boot.actuate.mail;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class MailHealthIndicator
extends AbstractHealthIndicator {
    private final JavaMailSenderImpl mailSender;

    public MailHealthIndicator(JavaMailSenderImpl mailSender) {
        super("Mail health check failed");
        this.mailSender = mailSender;
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        builder.withDetail("location", this.mailSender.getHost() + ":" + this.mailSender.getPort());
        this.mailSender.testConnection();
        builder.up();
    }
}

