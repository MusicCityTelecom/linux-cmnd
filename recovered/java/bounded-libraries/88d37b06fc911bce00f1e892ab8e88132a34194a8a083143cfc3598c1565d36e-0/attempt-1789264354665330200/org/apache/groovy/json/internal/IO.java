/*
 * Decompiled with CFR 0.152.
 */
package org.apache.groovy.json.internal;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import org.apache.groovy.json.internal.CharBuf;
import org.apache.groovy.json.internal.Exceptions;

public class IO {
    private static final int EOF = -1;
    private static final int DEFAULT_BUFFER_SIZE = 4096;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static CharBuf read(Reader input, CharBuf charBuf, int bufSize) {
        if (charBuf == null) {
            charBuf = CharBuf.create(bufSize);
        } else {
            charBuf.readForRecycle();
        }
        try {
            char[] buffer = charBuf.toCharArray();
            int size = input.read(buffer);
            if (size != -1) {
                charBuf._len(size);
            }
            if (size < 0) {
                CharBuf charBuf2 = charBuf;
                return charBuf2;
            }
            IO.copy(input, charBuf);
        }
        catch (IOException e) {
            Exceptions.handle(e);
        }
        finally {
            try {
                input.close();
            }
            catch (IOException e) {
                Exceptions.handle(e);
            }
        }
        return charBuf;
    }

    public static int copy(Reader input, Writer output) {
        long count = IO.copyLarge(input, output);
        if (count > Integer.MAX_VALUE) {
            return -1;
        }
        return (int)count;
    }

    public static long copyLarge(Reader reader, Writer writer) {
        return IO.copyLarge(reader, writer, new char[4096]);
    }

    public static long copyLarge(Reader reader, Writer writer, char[] buffer) {
        long count = 0L;
        try {
            int n;
            while (-1 != (n = reader.read(buffer))) {
                writer.write(buffer, 0, n);
                count += (long)n;
            }
        }
        catch (IOException e) {
            Exceptions.handle(e);
        }
        return count;
    }
}

