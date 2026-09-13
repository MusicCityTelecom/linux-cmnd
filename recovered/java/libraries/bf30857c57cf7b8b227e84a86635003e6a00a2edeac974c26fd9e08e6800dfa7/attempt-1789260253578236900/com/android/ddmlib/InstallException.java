/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib;

import com.android.ddmlib.CanceledException;
import com.android.ddmlib.SyncException;

public class InstallException
extends CanceledException {
    private static final long serialVersionUID = 1L;

    public InstallException(Throwable cause) {
        super(cause.getMessage(), cause);
    }

    public InstallException(String message) {
        super(message);
    }

    public InstallException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public boolean wasCanceled() {
        Throwable cause = this.getCause();
        return cause instanceof SyncException && ((SyncException)cause).wasCanceled();
    }
}

