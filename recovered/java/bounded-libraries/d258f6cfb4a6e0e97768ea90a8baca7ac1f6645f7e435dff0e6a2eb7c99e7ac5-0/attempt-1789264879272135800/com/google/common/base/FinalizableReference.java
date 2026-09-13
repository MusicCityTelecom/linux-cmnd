/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.errorprone.annotations.DoNotMock
 */
package com.google.common.base;

import com.google.common.annotations.GwtIncompatible;
import com.google.errorprone.annotations.DoNotMock;

@DoNotMock(value="Use an instance of one of the Finalizable*Reference classes")
@GwtIncompatible
public interface FinalizableReference {
    public void finalizeReferent();
}

