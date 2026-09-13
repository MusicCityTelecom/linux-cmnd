/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.ObjectCodec
 *  com.fasterxml.jackson.core.io.IOContext
 */
package com.fasterxml.jackson.dataformat.javaprop.impl;

import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsGenerator;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

public class PropertiesBackedGenerator
extends JavaPropsGenerator {
    protected final Map<String, Object> _content;

    public PropertiesBackedGenerator(IOContext ctxt, Map<?, ?> content, int stdFeatures, ObjectCodec codec) {
        super(ctxt, stdFeatures, codec);
        this._content = content;
        this._headerChecked = true;
    }

    @Deprecated
    public PropertiesBackedGenerator(IOContext ctxt, Properties props, int stdFeatures, ObjectCodec codec) {
        super(ctxt, stdFeatures, codec);
        this._content = props;
        this._headerChecked = true;
    }

    public Object getOutputTarget() {
        return this._content;
    }

    public void close() throws IOException {
    }

    public void flush() throws IOException {
    }

    protected void _releaseBuffers() {
    }

    @Override
    protected void _appendFieldName(StringBuilder path, String name) {
        path.append(name);
    }

    @Override
    protected void _writeEscapedEntry(char[] text, int offset, int len) throws IOException {
        this._writeEscapedEntry(new String(text, offset, len));
    }

    @Override
    protected void _writeEscapedEntry(String value) throws IOException {
        this._content.put(this._basePath.toString(), value);
    }

    @Override
    protected void _writeUnescapedEntry(String value) throws IOException {
        this._content.put(this._basePath.toString(), value);
    }

    @Override
    protected void _writeRaw(char c) throws IOException {
    }

    @Override
    protected void _writeRaw(String text) throws IOException {
    }

    @Override
    protected void _writeRaw(StringBuilder text) throws IOException {
    }

    @Override
    protected void _writeRaw(char[] text, int offset, int len) throws IOException {
    }

    protected void _writeRawLong(String text) throws IOException {
    }

    protected void _writeRawLong(StringBuilder text) throws IOException {
    }
}

