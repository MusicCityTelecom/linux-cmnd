/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.api;

import org.glassfish.hk2.api.ActiveDescriptor;

public interface AOPProxyCtl {
    public static final String UNDERLYING_METHOD_NAME = "__getUnderlyingDescriptor";

    public ActiveDescriptor<?> __getUnderlyingDescriptor();
}

