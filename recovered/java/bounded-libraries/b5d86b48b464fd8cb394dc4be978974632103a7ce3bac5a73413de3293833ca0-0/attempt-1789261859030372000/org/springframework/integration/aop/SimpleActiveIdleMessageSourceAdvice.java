/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 */
package org.springframework.integration.aop;

import java.time.Duration;
import org.springframework.integration.aop.AbstractMessageSourceAdvice;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.util.DynamicPeriodicTrigger;
import org.springframework.messaging.Message;

@Deprecated
public class SimpleActiveIdleMessageSourceAdvice
extends AbstractMessageSourceAdvice {
    private final DynamicPeriodicTrigger trigger;
    private volatile Duration idlePollPeriod;
    private volatile Duration activePollPeriod;

    public SimpleActiveIdleMessageSourceAdvice(DynamicPeriodicTrigger trigger2) {
        this.trigger = trigger2;
        this.idlePollPeriod = trigger2.getDuration();
        this.activePollPeriod = trigger2.getDuration();
    }

    public void setIdlePollPeriod(long idlePollPeriod) {
        this.idlePollPeriod = Duration.ofMillis(idlePollPeriod);
    }

    public void setActivePollPeriod(long activePollPeriod) {
        this.activePollPeriod = Duration.ofMillis(activePollPeriod);
    }

    @Override
    public Message<?> afterReceive(Message<?> result, MessageSource<?> source) {
        if (result == null) {
            this.trigger.setDuration(this.idlePollPeriod);
        } else {
            this.trigger.setDuration(this.activePollPeriod);
        }
        return result;
    }
}

