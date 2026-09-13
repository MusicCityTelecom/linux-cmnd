/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.runtime;

import groovyjarjarantlr4.runtime.ANTLRReaderStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ANTLRInputStream
extends ANTLRReaderStream {
    public ANTLRInputStream() {
    }

    public ANTLRInputStream(InputStream input) throws IOException {
        this(input, null);
    }

    public ANTLRInputStream(InputStream input, int size) throws IOException {
        this(input, size, null);
    }

    public ANTLRInputStream(InputStream input, String encoding) throws IOException {
        this(input, 1024, encoding);
    }

    public ANTLRInputStream(InputStream input, int size, String encoding) throws IOException {
        this(input, size, 1024, encoding);
    }

    public ANTLRInputStream(InputStream input, int size, int readBufferSize, String encoding) throws IOException {
        InputStreamReader isr = encoding != null ? new InputStreamReader(input, encoding) : new InputStreamReader(input);
        this.load(isr, size, readBufferSize);
    }
}

