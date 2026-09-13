/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import org.apache.commons.jexl3.parser.Provider;

public class StreamProvider
implements Provider {
    private Reader m_aReader;

    public StreamProvider(InputStream stream, String charsetName) throws IOException {
        this(new BufferedReader(new InputStreamReader(stream, charsetName)));
    }

    public StreamProvider(InputStream stream, Charset charset) {
        this(new BufferedReader(new InputStreamReader(stream, charset)));
    }

    public StreamProvider(Reader reader) {
        this.m_aReader = reader;
    }

    @Override
    public int read(char[] aDest, int nOfs, int nLen) throws IOException {
        int result = this.m_aReader.read(aDest, nOfs, nLen);
        if (result == 0 && nOfs < aDest.length && nLen > 0) {
            result = -1;
        }
        return result;
    }

    @Override
    public void close() throws IOException {
        if (this.m_aReader != null) {
            this.m_aReader.close();
        }
    }
}

