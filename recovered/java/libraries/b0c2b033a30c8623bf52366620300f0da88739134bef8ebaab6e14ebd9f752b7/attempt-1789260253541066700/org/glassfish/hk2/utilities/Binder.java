/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.utilities;

import org.glassfish.hk2.api.DynamicConfiguration;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface Binder {
    public void bind(DynamicConfiguration var1);
}

