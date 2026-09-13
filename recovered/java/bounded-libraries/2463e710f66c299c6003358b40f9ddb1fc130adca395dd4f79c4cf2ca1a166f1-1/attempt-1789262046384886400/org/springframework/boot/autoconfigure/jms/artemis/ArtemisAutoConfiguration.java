/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.ConnectionFactory
 *  org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.context.annotation.Import
 */
package org.springframework.boot.autoconfigure.jms.artemis;

import javax.jms.ConnectionFactory;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.JmsProperties;
import org.springframework.boot.autoconfigure.jms.JndiConnectionFactoryAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisConnectionFactoryConfiguration;
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisEmbeddedServerConfiguration;
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisProperties;
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisXAConnectionFactoryConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

@AutoConfiguration(before={JmsAutoConfiguration.class}, after={JndiConnectionFactoryAutoConfiguration.class})
@ConditionalOnClass(value={ConnectionFactory.class, ActiveMQConnectionFactory.class})
@ConditionalOnMissingBean(value={ConnectionFactory.class})
@EnableConfigurationProperties(value={ArtemisProperties.class, JmsProperties.class})
@Import(value={ArtemisEmbeddedServerConfiguration.class, ArtemisXAConnectionFactoryConfiguration.class, ArtemisConnectionFactoryConfiguration.class})
public class ArtemisAutoConfiguration {
}

