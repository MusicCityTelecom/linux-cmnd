/*
 * Decompiled with CFR 0.152.
 */
package com.android.io;

import com.android.io.IAbstractFile;

public class StreamException
extends Exception {
    private static final long serialVersionUID = 1L;
    private final Error mError;
    private final IAbstractFile mFile;

    public StreamException(Exception e2, IAbstractFile file) {
        this(e2, file, Error.DEFAULT);
    }

    public StreamException(Exception e2, IAbstractFile file, Error error) {
        super(e2);
        this.mFile = file;
        this.mError = error;
    }

    public Error getError() {
        return this.mError;
    }

    public IAbstractFile getFile() {
        return this.mFile;
    }

    public static enum Error {
        DEFAULT,
        OUTOFSYNC,
        FILENOTFOUND;

    }
}

