/*
 * Decompiled with CFR 0.152.
 */
package org.aopalliance.intercept;

import org.aopalliance.intercept.FieldAccess;
import org.aopalliance.intercept.Interceptor;

public interface FieldInterceptor
extends Interceptor {
    public Object get(FieldAccess var1) throws Throwable;

    public Object set(FieldAccess var1) throws Throwable;
}

