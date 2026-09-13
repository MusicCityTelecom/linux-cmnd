/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.dsl;

import org.springframework.integration.channel.RendezvousChannel;
import org.springframework.integration.dsl.MessageChannelSpec;

public class RendezvousChannelSpec
extends MessageChannelSpec<RendezvousChannelSpec, RendezvousChannel> {
    protected RendezvousChannelSpec() {
        this.channel = new RendezvousChannel();
    }
}

