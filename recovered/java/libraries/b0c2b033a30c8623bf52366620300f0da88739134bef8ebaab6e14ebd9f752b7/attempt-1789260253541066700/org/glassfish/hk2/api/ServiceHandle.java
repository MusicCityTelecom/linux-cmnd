/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.api;

import java.io.Closeable;
import java.util.List;
import org.glassfish.hk2.api.ActiveDescriptor;

public interface ServiceHandle<T>
extends Closeable {
    public T getService();

    public ActiveDescriptor<T> getActiveDescriptor();

    public boolean isActive();

    @Deprecated
    default public void destroy() {
        this.close();
    }

    @Override
    default public void close() {
        this.destroy();
    }

    public void setServiceData(Object var1);

    public Object getServiceData();

    public List<ServiceHandle<?>> getSubHandles();
}

