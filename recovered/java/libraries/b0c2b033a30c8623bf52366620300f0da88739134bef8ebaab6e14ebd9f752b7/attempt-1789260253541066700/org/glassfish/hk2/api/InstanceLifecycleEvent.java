/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.api;

import java.util.Map;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.Injectee;
import org.glassfish.hk2.api.InstanceLifecycleEventType;

public interface InstanceLifecycleEvent {
    public InstanceLifecycleEventType getEventType();

    public ActiveDescriptor<?> getActiveDescriptor();

    public Object getLifecycleObject();

    public Map<Injectee, Object> getKnownInjectees();
}

