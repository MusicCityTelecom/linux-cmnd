/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.channel;

import java.util.concurrent.SynchronousQueue;
import org.springframework.integration.channel.QueueChannel;

public class RendezvousChannel
extends QueueChannel {
    public RendezvousChannel() {
        super(new SynchronousQueue());
    }
}

