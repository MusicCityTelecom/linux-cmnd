/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.api;

import org.glassfish.hk2.api.Injectee;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface JustInTimeInjectionResolver {
    public boolean justInTimeResolution(Injectee var1);
}

