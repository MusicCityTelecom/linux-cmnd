/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.api;

import java.util.Set;
import org.glassfish.hk2.api.ServiceLocator;

public interface ServiceLocatorListener {
    public void initialize(Set<ServiceLocator> var1);

    public void locatorAdded(ServiceLocator var1);

    public void locatorDestroyed(ServiceLocator var1);
}

