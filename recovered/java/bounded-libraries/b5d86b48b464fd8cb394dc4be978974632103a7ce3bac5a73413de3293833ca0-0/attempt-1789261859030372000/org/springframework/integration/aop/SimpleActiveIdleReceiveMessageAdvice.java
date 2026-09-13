/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 *  org.springframework.util.Assert
 */
package org.springframework.integration.aop;

import java.time.Duration;
import org.springframework.integration.aop.ReceiveMessageAdvice;
import org.springframework.integration.util.DynamicPeriodicTrigger;
import org.springframework.messaging.Message;
import org.springframework.util.Assert;

public class SimpleActiveIdleReceiveMessageAdvice
implements ReceiveMessageAdvice {
    private final DynamicPeriodicTrigger trigger;
    private volatile Duration idlePollPeriod;
    private volatile Duration activePollPeriod;

    public SimpleActiveIdleReceiveMessageAdvice(DynamicPeriodicTrigger trigger2) {
        Assert.notNull((Object)trigger2, (String)"'trigger' must not be null");
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
    public Message<?> afterReceive(Message<?> result, Object source) {
        if (result == null) {
            this.trigger.setDuration(this.idlePollPeriod);
        } else {
            this.trigger.setDuration(this.activePollPeriod);
        }
        return result;
    }
}

