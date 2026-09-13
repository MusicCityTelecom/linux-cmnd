/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.activation.MimeType
 *  javax.mail.internet.MimeMessage
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.context.annotation.Conditional
 *  org.springframework.context.annotation.ConfigurationCondition$ConfigurationPhase
 *  org.springframework.context.annotation.Import
 *  org.springframework.mail.MailSender
 */
package org.springframework.boot.autoconfigure.mail;

import javax.activation.MimeType;
import javax.mail.internet.MimeMessage;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.AnyNestedCondition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.autoconfigure.mail.MailSenderJndiConfiguration;
import org.springframework.boot.autoconfigure.mail.MailSenderPropertiesConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.ConfigurationCondition;
import org.springframework.context.annotation.Import;
import org.springframework.mail.MailSender;

@AutoConfiguration
@ConditionalOnClass(value={MimeMessage.class, MimeType.class, MailSender.class})
@ConditionalOnMissingBean(value={MailSender.class})
@Conditional(value={MailSenderCondition.class})
@EnableConfigurationProperties(value={MailProperties.class})
@Import(value={MailSenderJndiConfiguration.class, MailSenderPropertiesConfiguration.class})
public class MailSenderAutoConfiguration {

    static class MailSenderCondition
    extends AnyNestedCondition {
        MailSenderCondition() {
            super(ConfigurationCondition.ConfigurationPhase.PARSE_CONFIGURATION);
        }

        @ConditionalOnProperty(prefix="spring.mail", name={"jndi-name"})
        static class JndiNameProperty {
            JndiNameProperty() {
            }
        }

        @ConditionalOnProperty(prefix="spring.mail", name={"host"})
        static class HostProperty {
            HostProperty() {
            }
        }
    }
}

