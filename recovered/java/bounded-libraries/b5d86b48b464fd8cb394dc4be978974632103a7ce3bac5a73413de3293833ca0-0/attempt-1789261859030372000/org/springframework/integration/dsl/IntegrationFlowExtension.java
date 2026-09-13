/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.dsl;

import java.util.Map;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlowDefinition;
import org.springframework.integration.dsl.StandardIntegrationFlow;

public abstract class IntegrationFlowExtension<B extends IntegrationFlowExtension<B>>
extends IntegrationFlowDefinition<B> {
    private final DirectChannel inputChannel = new DirectChannel();

    protected IntegrationFlowExtension() {
        this.channel(this.inputChannel);
    }

    @Override
    public StandardIntegrationFlow get() {
        StandardIntegrationFlow targetIntegrationFlow = super.get();
        return new StandardIntegrationFlowExtension(targetIntegrationFlow.getIntegrationComponents(), this.inputChannel);
    }

    private static class StandardIntegrationFlowExtension
    extends StandardIntegrationFlow {
        private final DirectChannel inputChannel;

        StandardIntegrationFlowExtension(Map<Object, String> integrationComponents, DirectChannel inputChannel) {
            super(integrationComponents);
            this.inputChannel = inputChannel;
        }

        @Override
        public void setBeanName(String name) {
            super.setBeanName(name);
            this.inputChannel.setBeanName(name + ".input");
        }
    }
}

