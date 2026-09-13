/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.client;

import javax.ws.rs.ConstrainedTo;
import javax.ws.rs.RuntimeType;
import org.glassfish.jersey.spi.Contract;

@Contract
@ConstrainedTo(value=RuntimeType.CLIENT)
public interface ClientLifecycleListener {
    public void onInit();

    public void onClose();
}

