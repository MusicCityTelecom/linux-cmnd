/*
 * Decompiled with CFR 0.152.
 */
package com.android.support;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 1, 9}, bv={1, 0, 2}, k=1, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00060\u0001j\u0002`\u0002B\u0011\u0012\n\b\u0002\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u00a2\u0006\u0002\u0010\u0005\u00a8\u0006\u0006"}, d2={"Lcom/android/support/InvalidDataException;", "Ljava/lang/RuntimeException;", "Lkotlin/RuntimeException;", "message", "", "(Ljava/lang/String;)V", "common"})
public final class InvalidDataException
extends RuntimeException {
    public InvalidDataException(@Nullable String message) {
        super(message);
    }

    public /* synthetic */ InvalidDataException(String string, int n2, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n2 & 1) != 0) {
            string = null;
        }
        this(string);
    }

    public InvalidDataException() {
        this(null, 1, null);
    }
}

