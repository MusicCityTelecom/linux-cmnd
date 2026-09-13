/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.messaging.MessageChannel
 */
package org.springframework.integration.dsl.context;

import java.util.Map;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.messaging.MessageChannel;

public interface IntegrationFlowContext {
    public IntegrationFlowRegistrationBuilder registration(IntegrationFlow var1);

    public IntegrationFlowRegistration getRegistrationById(String var1);

    public void remove(String var1);

    public MessagingTemplate messagingTemplateFor(String var1);

    public Map<String, IntegrationFlowRegistration> getRegistry();

    default public boolean isUseIdAsPrefix(String flowId) {
        return false;
    }

    public static interface IntegrationFlowRegistrationBuilder {
        public IntegrationFlowRegistrationBuilder id(String var1);

        public IntegrationFlowRegistrationBuilder autoStartup(boolean var1);

        public IntegrationFlowRegistrationBuilder addBean(Object var1);

        public IntegrationFlowRegistrationBuilder addBean(String var1, Object var2);

        public IntegrationFlowRegistrationBuilder setSource(Object var1);

        default public IntegrationFlowRegistrationBuilder useFlowIdAsPrefix() {
            return this;
        }

        public IntegrationFlowRegistration register();
    }

    public static interface IntegrationFlowRegistration
    extends BeanFactoryAware {
        public String getId();

        public IntegrationFlow getIntegrationFlow();

        public MessageChannel getInputChannel();

        public MessagingTemplate getMessagingTemplate();

        public void start();

        public void stop();

        public void destroy();
    }
}

