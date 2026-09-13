/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.io.IOContext
 */
package com.fasterxml.jackson.dataformat.javaprop.io;

import com.fasterxml.jackson.core.io.IOContext;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

public final class Latin1Reader
extends Reader {
    private final IOContext _ioContext;
    private InputStream _inputSource;
    private byte[] _inputBuffer;
    private int _inputPtr;
    private int _inputEnd;
    private int _charCount = 0;
    private char[] _tmpBuffer = null;

    public Latin1Reader(byte[] buf, int ptr, int len) {
        super(new Object());
        this._ioContext = null;
        this._inputSource = null;
        this._inputBuffer = buf;
        this._inputPtr = ptr;
        this._inputEnd = ptr + len;
    }

    public Latin1Reader(IOContext ctxt, InputStream in) {
        super(in);
        this._ioContext = ctxt;
        this._inputSource = in;
        this._inputBuffer = ctxt.allocReadIOBuffer();
        this._inputPtr = 0;
        this._inputEnd = 0;
    }

    public int getReadCharsCount() {
        return this._charCount;
    }

    @Override
    public void close() throws IOException {
        InputStream in = this._inputSource;
        if (in != null) {
            this._inputSource = null;
            this.freeBuffers();
            in.close();
        }
    }

    @Override
    public int read() throws IOException {
        if (this._tmpBuffer == null) {
            this._tmpBuffer = new char[1];
        }
        if (this.read(this._tmpBuffer, 0, 1) < 1) {
            return -1;
        }
        return this._tmpBuffer[0];
    }

    @Override
    public int read(char[] cbuf) throws IOException {
        return this.read(cbuf, 0, cbuf.length);
    }

    @Override
    public int read(char[] cbuf, int start, int len) throws IOException {
        if (this._inputBuffer == null) {
            return -1;
        }
        if (len < 1) {
            return len;
        }
        int left = this._inputEnd - this._inputPtr;
        if (left < 1) {
            if (!this.loadMore()) {
                return -1;
            }
            left = this._inputEnd - this._inputPtr;
        }
        if (left > len) {
            left = len;
        }
        byte[] inBuf = this._inputBuffer;
        int inPtr = this._inputPtr;
        int outPtr = start;
        int inEnd = inPtr + left;
        do {
            cbuf[outPtr++] = (char)inBuf[inPtr++];
        } while (inPtr < inEnd);
        this._inputPtr = inPtr;
        return left;
    }

    private boolean loadMore() throws IOException {
        this._charCount += this._inputEnd;
        this._inputPtr = 0;
        this._inputEnd = 0;
        if (this._inputSource == null) {
            this.freeBuffers();
            return false;
        }
        int count = this._inputSource.read(this._inputBuffer, 0, this._inputBuffer.length);
        if (count < 1) {
            this.freeBuffers();
            if (count < 0) {
                return false;
            }
            throw new IOException("Strange I/O stream, returned 0 bytes on read");
        }
        this._inputEnd = count;
        return true;
    }

    private final void freeBuffers() {
        byte[] buf;
        if (this._ioContext != null && (buf = this._inputBuffer) != null) {
            this._inputBuffer = null;
            this._ioContext.releaseReadIOBuffer(buf);
        }
    }
}

