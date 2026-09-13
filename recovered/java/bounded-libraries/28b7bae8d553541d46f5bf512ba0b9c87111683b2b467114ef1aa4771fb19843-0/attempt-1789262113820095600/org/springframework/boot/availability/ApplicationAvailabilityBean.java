/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.context.ApplicationEventPublisher
 *  org.springframework.context.ApplicationListener
 *  org.springframework.util.Assert
 */
package org.springframework.boot.availability;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.availability.ApplicationAvailability;
import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.AvailabilityState;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.util.Assert;

public class ApplicationAvailabilityBean
implements ApplicationAvailability,
ApplicationListener<AvailabilityChangeEvent<?>> {
    private final Map<Class<? extends AvailabilityState>, AvailabilityChangeEvent<?>> events = new ConcurrentHashMap();
    private final Log logger;

    public ApplicationAvailabilityBean() {
        this(LogFactory.getLog(ApplicationAvailabilityBean.class));
    }

    ApplicationAvailabilityBean(Log logger) {
        this.logger = logger;
    }

    @Override
    public <S extends AvailabilityState> S getState(Class<S> stateType, S defaultState) {
        Assert.notNull(stateType, (String)"StateType must not be null");
        Assert.notNull(defaultState, (String)"DefaultState must not be null");
        S state = this.getState(stateType);
        return state != null ? state : defaultState;
    }

    @Override
    public <S extends AvailabilityState> S getState(Class<S> stateType) {
        AvailabilityChangeEvent<S> event = this.getLastChangeEvent(stateType);
        return event != null ? (S)event.getState() : null;
    }

    @Override
    public <S extends AvailabilityState> AvailabilityChangeEvent<S> getLastChangeEvent(Class<S> stateType) {
        return this.events.get(stateType);
    }

    public void onApplicationEvent(AvailabilityChangeEvent<?> event) {
        Class<? extends AvailabilityState> type = this.getStateType((AvailabilityState)event.getState());
        if (this.logger.isDebugEnabled()) {
            this.logger.debug(this.getLogMessage(type, event));
        }
        this.events.put(type, event);
    }

    private <S extends AvailabilityState> Object getLogMessage(Class<S> type, AvailabilityChangeEvent<?> event) {
        AvailabilityChangeEvent<S> lastChangeEvent = this.getLastChangeEvent(type);
        StringBuilder message = new StringBuilder("Application availability state " + type.getSimpleName() + " changed");
        message.append(lastChangeEvent != null ? " from " + lastChangeEvent.getState() : "");
        message.append(" to " + event.getState());
        message.append(this.getSourceDescription(event.getSource()));
        return message;
    }

    private String getSourceDescription(Object source) {
        if (source == null || source instanceof ApplicationEventPublisher) {
            return "";
        }
        return ": " + (source instanceof Throwable ? source : source.getClass().getName());
    }

    private Class<? extends AvailabilityState> getStateType(AvailabilityState state) {
        Class<Object> type = state instanceof Enum ? ((Enum)((Object)state)).getDeclaringClass() : state.getClass();
        return type;
    }
}

