/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.dsl;

import org.springframework.integration.channel.FluxMessageChannel;
import org.springframework.integration.dsl.MessageChannelSpec;

public class FluxMessageChannelSpec
extends MessageChannelSpec<FluxMessageChannelSpec, FluxMessageChannel> {
    protected FluxMessageChannelSpec() {
        this.channel = new FluxMessageChannel();
    }
}

