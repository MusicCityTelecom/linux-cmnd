/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.annotation.ConditionContext
 *  org.springframework.core.annotation.AnnotationAttributes
 *  org.springframework.core.annotation.Order
 *  org.springframework.core.type.AnnotatedTypeMetadata
 *  org.springframework.jndi.JndiLocatorDelegate
 *  org.springframework.jndi.JndiLocatorSupport
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.autoconfigure.condition;

import java.util.Map;
import javax.naming.NamingException;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.ConditionalOnJndi;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.Order;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.jndi.JndiLocatorDelegate;
import org.springframework.jndi.JndiLocatorSupport;
import org.springframework.util.StringUtils;

@Order(value=2147483627)
class OnJndiCondition
extends SpringBootCondition {
    OnJndiCondition() {
    }

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        AnnotationAttributes annotationAttributes = AnnotationAttributes.fromMap((Map)metadata.getAnnotationAttributes(ConditionalOnJndi.class.getName()));
        String[] locations = annotationAttributes.getStringArray("value");
        try {
            return this.getMatchOutcome(locations);
        }
        catch (NoClassDefFoundError ex) {
            return ConditionOutcome.noMatch(ConditionMessage.forCondition(ConditionalOnJndi.class, new Object[0]).because("JNDI class not found"));
        }
    }

    private ConditionOutcome getMatchOutcome(String[] locations) {
        if (!this.isJndiAvailable()) {
            return ConditionOutcome.noMatch(ConditionMessage.forCondition(ConditionalOnJndi.class, new Object[0]).notAvailable("JNDI environment"));
        }
        if (locations.length == 0) {
            return ConditionOutcome.match(ConditionMessage.forCondition(ConditionalOnJndi.class, new Object[0]).available("JNDI environment"));
        }
        JndiLocator locator = this.getJndiLocator(locations);
        String location = locator.lookupFirstLocation();
        String details = "(" + StringUtils.arrayToCommaDelimitedString((Object[])locations) + ")";
        if (location != null) {
            return ConditionOutcome.match(ConditionMessage.forCondition(ConditionalOnJndi.class, details).foundExactly("\"" + location + "\""));
        }
        return ConditionOutcome.noMatch(ConditionMessage.forCondition(ConditionalOnJndi.class, details).didNotFind("any matching JNDI location").atAll());
    }

    protected boolean isJndiAvailable() {
        return JndiLocatorDelegate.isDefaultJndiEnvironmentAvailable();
    }

    protected JndiLocator getJndiLocator(String[] locations) {
        return new JndiLocator(locations);
    }

    protected static class JndiLocator
    extends JndiLocatorSupport {
        private String[] locations;

        public JndiLocator(String[] locations) {
            this.locations = locations;
        }

        public String lookupFirstLocation() {
            for (String location : this.locations) {
                try {
                    this.lookup(location);
                    return location;
                }
                catch (NamingException namingException) {
                }
            }
            return null;
        }
    }
}

