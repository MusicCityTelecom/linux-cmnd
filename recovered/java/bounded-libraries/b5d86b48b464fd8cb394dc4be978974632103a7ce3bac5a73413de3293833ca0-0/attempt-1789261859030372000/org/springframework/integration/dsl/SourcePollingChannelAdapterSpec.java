/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.dsl;

import org.springframework.integration.config.SourcePollingChannelAdapterFactoryBean;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.EndpointSpec;
import org.springframework.integration.scheduling.PollerMetadata;

public class SourcePollingChannelAdapterSpec
extends EndpointSpec<SourcePollingChannelAdapterSpec, SourcePollingChannelAdapterFactoryBean, MessageSource<?>> {
    protected SourcePollingChannelAdapterSpec(MessageSource<?> messageSource) {
        super(messageSource, new SourcePollingChannelAdapterFactoryBean());
        ((SourcePollingChannelAdapterFactoryBean)this.endpointFactoryBean).setSource(messageSource);
    }

    @Override
    public SourcePollingChannelAdapterSpec phase(int phase) {
        ((SourcePollingChannelAdapterFactoryBean)this.endpointFactoryBean).setPhase(phase);
        return (SourcePollingChannelAdapterSpec)this._this();
    }

    @Override
    public SourcePollingChannelAdapterSpec autoStartup(boolean autoStartup) {
        ((SourcePollingChannelAdapterFactoryBean)this.endpointFactoryBean).setAutoStartup(autoStartup);
        return (SourcePollingChannelAdapterSpec)this._this();
    }

    @Override
    public SourcePollingChannelAdapterSpec poller(PollerMetadata pollerMetadata) {
        if (pollerMetadata != null) {
            if (Integer.MIN_VALUE == pollerMetadata.getMaxMessagesPerPoll()) {
                pollerMetadata.setMaxMessagesPerPoll(1L);
            }
            ((SourcePollingChannelAdapterFactoryBean)this.endpointFactoryBean).setPollerMetadata(pollerMetadata);
        }
        return (SourcePollingChannelAdapterSpec)this._this();
    }

    @Override
    public SourcePollingChannelAdapterSpec role(String role) {
        ((SourcePollingChannelAdapterFactoryBean)this.endpointFactoryBean).setRole(role);
        return this;
    }
}

