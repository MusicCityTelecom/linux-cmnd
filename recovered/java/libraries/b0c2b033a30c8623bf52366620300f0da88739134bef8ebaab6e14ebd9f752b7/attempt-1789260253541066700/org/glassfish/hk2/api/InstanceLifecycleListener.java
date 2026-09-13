/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.api;

import org.glassfish.hk2.api.Filter;
import org.glassfish.hk2.api.InstanceLifecycleEvent;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface InstanceLifecycleListener {
    public Filter getFilter();

    public void lifecycleEvent(InstanceLifecycleEvent var1);
}

