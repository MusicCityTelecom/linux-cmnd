/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.ConnectionFactory
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Conditional
 *  org.springframework.context.annotation.ConfigurationCondition$ConfigurationPhase
 *  org.springframework.jms.core.JmsTemplate
 *  org.springframework.jndi.JndiLocatorDelegate
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.autoconfigure.jms;

import java.util.Arrays;
import javax.jms.ConnectionFactory;
import javax.naming.NamingException;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.AnyNestedCondition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnJndi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.JmsProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.ConfigurationCondition;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jndi.JndiLocatorDelegate;
import org.springframework.util.StringUtils;

@AutoConfiguration(before={JmsAutoConfiguration.class})
@ConditionalOnClass(value={JmsTemplate.class})
@ConditionalOnMissingBean(value={ConnectionFactory.class})
@Conditional(value={JndiOrPropertyCondition.class})
@EnableConfigurationProperties(value={JmsProperties.class})
public class JndiConnectionFactoryAutoConfiguration {
    private static final String[] JNDI_LOCATIONS = new String[]{"java:/JmsXA", "java:/XAConnectionFactory"};

    @Bean
    public ConnectionFactory jmsConnectionFactory(JmsProperties properties) throws NamingException {
        JndiLocatorDelegate jndiLocatorDelegate = JndiLocatorDelegate.createDefaultResourceRefLocator();
        if (StringUtils.hasLength((String)properties.getJndiName())) {
            return (ConnectionFactory)jndiLocatorDelegate.lookup(properties.getJndiName(), ConnectionFactory.class);
        }
        return this.findJndiConnectionFactory(jndiLocatorDelegate);
    }

    private ConnectionFactory findJndiConnectionFactory(JndiLocatorDelegate jndiLocatorDelegate) {
        for (String name : JNDI_LOCATIONS) {
            try {
                return (ConnectionFactory)jndiLocatorDelegate.lookup(name, ConnectionFactory.class);
            }
            catch (NamingException namingException) {
            }
        }
        throw new IllegalStateException("Unable to find ConnectionFactory in JNDI locations " + Arrays.asList(JNDI_LOCATIONS));
    }

    static class JndiOrPropertyCondition
    extends AnyNestedCondition {
        JndiOrPropertyCondition() {
            super(ConfigurationCondition.ConfigurationPhase.PARSE_CONFIGURATION);
        }

        @ConditionalOnProperty(prefix="spring.jms", name={"jndi-name"})
        static class Property {
            Property() {
            }
        }

        @ConditionalOnJndi(value={"java:/JmsXA", "java:/XAConnectionFactory"})
        static class Jndi {
            Jndi() {
            }
        }
    }
}

