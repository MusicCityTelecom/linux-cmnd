/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.utilities;

import org.glassfish.hk2.api.ActiveDescriptor;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface ImmediateErrorHandler {
    public void postConstructFailed(ActiveDescriptor<?> var1, Throwable var2);

    public void preDestroyFailed(ActiveDescriptor<?> var1, Throwable var2);
}

