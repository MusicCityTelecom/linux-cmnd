/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.runtime;

import groovyjarjarantlr4.runtime.ANTLRStringStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ANTLRFileStream
extends ANTLRStringStream {
    protected String fileName;

    public ANTLRFileStream(String fileName) throws IOException {
        this(fileName, null);
    }

    public ANTLRFileStream(String fileName, String encoding) throws IOException {
        this.fileName = fileName;
        this.load(fileName, encoding);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void load(String fileName, String encoding) throws IOException {
        if (fileName == null) {
            return;
        }
        File f = new File(fileName);
        int size = (int)f.length();
        FileInputStream fis = new FileInputStream(fileName);
        InputStreamReader isr = encoding != null ? new InputStreamReader((InputStream)fis, encoding) : new InputStreamReader(fis);
        try {
            this.data = new char[size];
            this.n = isr.read(this.data);
        }
        finally {
            isr.close();
        }
    }

    public String getSourceName() {
        return this.fileName;
    }
}

