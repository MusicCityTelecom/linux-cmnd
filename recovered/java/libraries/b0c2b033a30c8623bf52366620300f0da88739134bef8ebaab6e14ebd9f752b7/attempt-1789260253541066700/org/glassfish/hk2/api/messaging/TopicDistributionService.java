/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.api.messaging;

import org.glassfish.hk2.api.messaging.Topic;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface TopicDistributionService {
    public static final String HK2_DEFAULT_TOPIC_DISTRIBUTOR = "HK2TopicDistributionService";

    public void distributeMessage(Topic<?> var1, Object var2);
}

