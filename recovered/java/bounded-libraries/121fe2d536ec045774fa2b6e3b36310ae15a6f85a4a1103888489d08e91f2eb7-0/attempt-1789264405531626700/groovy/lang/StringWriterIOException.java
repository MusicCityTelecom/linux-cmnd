/*
 * Decompiled with CFR 0.152.
 */
package groovy.lang;

import java.io.IOException;

public class StringWriterIOException
extends RuntimeException {
    private static final long serialVersionUID = -504499949457372681L;

    public StringWriterIOException(IOException e) {
        super(e);
    }

    public IOException getIOException() {
        return (IOException)this.getCause();
    }
}

