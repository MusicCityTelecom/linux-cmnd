/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 *  org.springframework.scheduling.Trigger
 *  org.springframework.util.Assert
 */
package org.springframework.integration.aop;

import org.springframework.integration.aop.MessageSourceMutator;
import org.springframework.integration.aop.ReceiveMessageAdvice;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.util.CompoundTrigger;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.scheduling.Trigger;
import org.springframework.util.Assert;

public class CompoundTriggerAdvice
implements MessageSourceMutator,
ReceiveMessageAdvice {
    private final CompoundTrigger compoundTrigger;
    private final Trigger override;

    public CompoundTriggerAdvice(CompoundTrigger compoundTrigger, Trigger overrideTrigger) {
        Assert.notNull((Object)compoundTrigger, (String)"'compoundTrigger' cannot be null");
        this.compoundTrigger = compoundTrigger;
        this.override = overrideTrigger;
    }

    @Override
    @Deprecated
    public Message<?> afterReceive(Message<?> result, MessageSource<?> source) {
        return this.afterReceive(result, (Object)source);
    }

    @Override
    @Nullable
    public Message<?> afterReceive(@Nullable Message<?> result, Object source) {
        if (result == null) {
            this.compoundTrigger.setOverride(this.override);
        } else {
            this.compoundTrigger.setOverride(null);
        }
        return result;
    }
}

