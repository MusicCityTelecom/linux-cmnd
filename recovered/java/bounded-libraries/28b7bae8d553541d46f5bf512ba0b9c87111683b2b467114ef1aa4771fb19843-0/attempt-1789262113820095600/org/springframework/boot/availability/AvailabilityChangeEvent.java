/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ApplicationEventPublisher
 *  org.springframework.context.PayloadApplicationEvent
 *  org.springframework.core.ResolvableType
 *  org.springframework.util.Assert
 */
package org.springframework.boot.availability;

import org.springframework.boot.availability.AvailabilityState;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.core.ResolvableType;
import org.springframework.util.Assert;

public class AvailabilityChangeEvent<S extends AvailabilityState>
extends PayloadApplicationEvent<S> {
    public AvailabilityChangeEvent(Object source, S state) {
        super(source, state);
    }

    public S getState() {
        return (S)((AvailabilityState)this.getPayload());
    }

    public ResolvableType getResolvableType() {
        return ResolvableType.forClassWithGenerics(((Object)((Object)this)).getClass(), (Class[])new Class[]{this.getStateType()});
    }

    private Class<?> getStateType() {
        S state = this.getState();
        if (state instanceof Enum) {
            return ((Enum)state).getDeclaringClass();
        }
        return state.getClass();
    }

    public static <S extends AvailabilityState> void publish(ApplicationContext context, S state) {
        Assert.notNull((Object)context, (String)"Context must not be null");
        AvailabilityChangeEvent.publish((ApplicationEventPublisher)context, context, state);
    }

    public static <S extends AvailabilityState> void publish(ApplicationEventPublisher publisher, Object source, S state) {
        Assert.notNull((Object)publisher, (String)"Publisher must not be null");
        publisher.publishEvent(new AvailabilityChangeEvent<S>(source, state));
    }
}

