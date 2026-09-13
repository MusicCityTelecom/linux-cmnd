/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime;

import groovyjarjarantlr4.v4.runtime.ANTLRInputStream;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;
import groovyjarjarantlr4.v4.runtime.misc.Utils;
import java.io.IOException;

@Deprecated
public class ANTLRFileStream
extends ANTLRInputStream {
    protected String fileName;

    public ANTLRFileStream(@NotNull String fileName) throws IOException {
        this(fileName, null);
    }

    public ANTLRFileStream(@NotNull String fileName, String encoding) throws IOException {
        this.fileName = fileName;
        this.load(fileName, encoding);
    }

    public void load(@NotNull String fileName, @Nullable String encoding) throws IOException {
        this.data = Utils.readFile(fileName, encoding);
        this.n = this.data.length;
    }

    @Override
    public String getSourceName() {
        return this.fileName;
    }
}

