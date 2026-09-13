/*
 * Decompiled with CFR 0.152.
 */
package com.android.io;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class NonClosingInputStream
extends FilterInputStream {
    private final InputStream mInputStream;
    private CloseBehavior mCloseBehavior = CloseBehavior.CLOSE;

    public NonClosingInputStream(InputStream in) {
        super(in);
        this.mInputStream = in;
    }

    public CloseBehavior getCloseBehavior() {
        return this.mCloseBehavior;
    }

    public NonClosingInputStream setCloseBehavior(CloseBehavior closeBehavior) {
        this.mCloseBehavior = closeBehavior;
        return this;
    }

    @Override
    public void close() throws IOException {
        switch (this.mCloseBehavior) {
            case IGNORE: {
                break;
            }
            case RESET: {
                this.mInputStream.reset();
                break;
            }
            case CLOSE: {
                this.mInputStream.close();
            }
        }
    }

    public static enum CloseBehavior {
        CLOSE,
        IGNORE,
        RESET;

    }
}

