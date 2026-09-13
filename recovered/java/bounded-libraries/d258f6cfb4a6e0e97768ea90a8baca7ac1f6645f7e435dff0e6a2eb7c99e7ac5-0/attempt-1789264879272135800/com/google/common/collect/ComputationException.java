/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.checkerframework.checker.nullness.qual.Nullable
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import org.checkerframework.checker.nullness.qual.Nullable;

@Deprecated
@GwtCompatible
public class ComputationException
extends RuntimeException {
    private static final long serialVersionUID = 0L;

    public ComputationException(@Nullable Throwable cause) {
        super(cause);
    }
}

