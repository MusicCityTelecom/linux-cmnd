/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerator$Feature
 *  com.fasterxml.jackson.core.ObjectCodec
 *  com.fasterxml.jackson.core.io.IOContext
 */
package com.fasterxml.jackson.dataformat.javaprop.impl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsGenerator;
import com.fasterxml.jackson.dataformat.javaprop.io.JPropEscapes;
import java.io.IOException;
import java.io.Writer;

public class WriterBackedGenerator
extends JavaPropsGenerator {
    protected final Writer _out;
    protected char[] _outputBuffer;
    protected int _outputTail = 0;
    protected final int _outputEnd;

    public WriterBackedGenerator(IOContext ctxt, Writer out, int stdFeatures, ObjectCodec codec) {
        super(ctxt, stdFeatures, codec);
        this._out = out;
        this._outputBuffer = ctxt.allocConcatBuffer();
        this._outputEnd = this._outputBuffer.length;
    }

    public Object getOutputTarget() {
        return this._out;
    }

    public void close() throws IOException {
        super.close();
        this._flushBuffer();
        this._outputTail = 0;
        if (this._out != null) {
            if (this._ioContext.isResourceManaged() || this.isEnabled(JsonGenerator.Feature.AUTO_CLOSE_TARGET)) {
                this._out.close();
            } else if (this.isEnabled(JsonGenerator.Feature.FLUSH_PASSED_TO_STREAM)) {
                this._out.flush();
            }
        }
        this._releaseBuffers();
    }

    public void flush() throws IOException {
        this._flushBuffer();
        if (this._out != null && this.isEnabled(JsonGenerator.Feature.FLUSH_PASSED_TO_STREAM)) {
            this._out.flush();
        }
    }

    protected void _releaseBuffers() {
        char[] buf = this._outputBuffer;
        if (buf != null) {
            this._outputBuffer = null;
            this._ioContext.releaseConcatBuffer(buf);
        }
    }

    protected void _flushBuffer() throws IOException {
        if (this._outputTail > 0) {
            this._out.write(this._outputBuffer, 0, this._outputTail);
            this._outputTail = 0;
        }
    }

    @Override
    protected void _appendFieldName(StringBuilder path, String name) {
        JPropEscapes.appendKey(this._basePath, name);
    }

    @Override
    protected void _writeEscapedEntry(String value) throws IOException {
        this._writeRaw(this._basePath);
        this._writeRaw(this._schema.keyValueSeparator());
        this._writeEscaped(value);
        this._writeLinefeed();
    }

    @Override
    protected void _writeEscapedEntry(char[] text, int offset, int len) throws IOException {
        this._writeRaw(this._basePath);
        this._writeRaw(this._schema.keyValueSeparator());
        this._writeEscaped(text, offset, len);
        this._writeLinefeed();
    }

    @Override
    protected void _writeUnescapedEntry(String value) throws IOException {
        this._writeRaw(this._basePath);
        this._writeRaw(this._schema.keyValueSeparator());
        this._writeRaw(value);
        this._writeLinefeed();
    }

    protected void _writeEscaped(String value) throws IOException {
        StringBuilder sb = JPropEscapes.appendValue(value);
        if (sb == null) {
            this._writeRaw(value);
        } else {
            this._writeRaw(sb);
        }
    }

    protected void _writeEscaped(char[] text, int offset, int len) throws IOException {
        this._writeEscaped(new String(text, offset, len));
    }

    protected void _writeLinefeed() throws IOException {
        this._writeRaw(this._schema.lineEnding());
    }

    @Override
    protected void _writeRaw(char c) throws IOException {
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = c;
    }

    @Override
    protected void _writeRaw(String text) throws IOException {
        int len = text.length();
        int room = this._outputEnd - this._outputTail;
        if (room == 0) {
            this._flushBuffer();
            room = this._outputEnd - this._outputTail;
        }
        if (room >= len) {
            text.getChars(0, len, this._outputBuffer, this._outputTail);
            this._outputTail += len;
        } else {
            this._writeRawLong(text);
        }
    }

    @Override
    protected void _writeRaw(StringBuilder text) throws IOException {
        int len = text.length();
        int room = this._outputEnd - this._outputTail;
        if (room == 0) {
            this._flushBuffer();
            room = this._outputEnd - this._outputTail;
        }
        if (room >= len) {
            text.getChars(0, len, this._outputBuffer, this._outputTail);
            this._outputTail += len;
        } else {
            this._writeRawLong(text);
        }
    }

    @Override
    protected void _writeRaw(char[] text, int offset, int len) throws IOException {
        if (len < 100) {
            int room = this._outputEnd - this._outputTail;
            if (len > room) {
                this._flushBuffer();
            }
            System.arraycopy(text, offset, this._outputBuffer, this._outputTail, len);
            this._outputTail += len;
            return;
        }
        this._flushBuffer();
        this._out.write(text, offset, len);
    }

    protected void _writeRawLong(String text) throws IOException {
        int len;
        int amount;
        int room = this._outputEnd - this._outputTail;
        text.getChars(0, room, this._outputBuffer, this._outputTail);
        this._outputTail += room;
        this._flushBuffer();
        int offset = room;
        for (len = text.length() - room; len > this._outputEnd; len -= amount) {
            amount = this._outputEnd;
            text.getChars(offset, offset + amount, this._outputBuffer, 0);
            this._outputTail = amount;
            this._flushBuffer();
            offset += amount;
        }
        text.getChars(offset, offset + len, this._outputBuffer, 0);
        this._outputTail = len;
    }

    protected void _writeRawLong(StringBuilder text) throws IOException {
        int len;
        int amount;
        int room = this._outputEnd - this._outputTail;
        text.getChars(0, room, this._outputBuffer, this._outputTail);
        this._outputTail += room;
        this._flushBuffer();
        int offset = room;
        for (len = text.length() - room; len > this._outputEnd; len -= amount) {
            amount = this._outputEnd;
            text.getChars(offset, offset + amount, this._outputBuffer, 0);
            this._outputTail = amount;
            this._flushBuffer();
            offset += amount;
        }
        text.getChars(offset, offset + len, this._outputBuffer, 0);
        this._outputTail = len;
    }
}

