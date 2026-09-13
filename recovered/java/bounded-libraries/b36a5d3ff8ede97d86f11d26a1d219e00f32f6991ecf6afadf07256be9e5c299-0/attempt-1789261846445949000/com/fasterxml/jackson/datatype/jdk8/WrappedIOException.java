/*
 * Decompiled with CFR 0.152.
 */
package com.fasterxml.jackson.datatype.jdk8;

import java.io.IOException;

public class WrappedIOException
extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public WrappedIOException(IOException cause) {
        super(cause);
    }

    @Override
    public IOException getCause() {
        return (IOException)super.getCause();
    }
}

