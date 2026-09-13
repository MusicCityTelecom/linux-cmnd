/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.FormatSchema
 */
package com.fasterxml.jackson.dataformat.javaprop;

import com.fasterxml.jackson.core.FormatSchema;
import com.fasterxml.jackson.dataformat.javaprop.util.JPropPathSplitter;
import com.fasterxml.jackson.dataformat.javaprop.util.Markers;
import java.io.Serializable;

public class JavaPropsSchema
implements FormatSchema,
Serializable {
    private static final long serialVersionUID = 1L;
    protected static final Markers DEFAULT_INDEX_MARKER = Markers.create("[", "]");
    protected static final JavaPropsSchema EMPTY = new JavaPropsSchema();
    protected transient JPropPathSplitter _splitter;
    protected int _firstArrayOffset = 1;
    protected String _pathSeparator = ".";
    protected Markers _indexMarker = DEFAULT_INDEX_MARKER;
    protected boolean _parseSimpleIndexes = true;
    protected boolean _writeIndexUsingMarkers;
    protected String _lineIndentation = "";
    protected String _keyValueSeparator = "=";
    protected String _lineEnding = "\n";
    protected String _header = "";
    protected String _prefix;

    public JavaPropsSchema() {
    }

    public JavaPropsSchema(JavaPropsSchema base) {
        this._firstArrayOffset = base._firstArrayOffset;
        this._pathSeparator = base._pathSeparator;
        this._indexMarker = base._indexMarker;
        this._parseSimpleIndexes = base._parseSimpleIndexes;
        this._writeIndexUsingMarkers = base._writeIndexUsingMarkers;
        this._lineIndentation = base._lineIndentation;
        this._keyValueSeparator = base._keyValueSeparator;
        this._lineEnding = base._lineEnding;
        this._header = base._header;
        this._prefix = base._prefix;
    }

    public JPropPathSplitter pathSplitter() {
        JPropPathSplitter splitter = this._splitter;
        if (splitter == null) {
            this._splitter = splitter = JPropPathSplitter.create(this);
        }
        return splitter;
    }

    public JavaPropsSchema withFirstArrayOffset(int v) {
        if (v == this._firstArrayOffset) {
            return this;
        }
        JavaPropsSchema s = new JavaPropsSchema(this);
        s._firstArrayOffset = v;
        return s;
    }

    public JavaPropsSchema withPathSeparator(String v) {
        if (v == null) {
            v = "";
        }
        if (this._equals(v, this._pathSeparator)) {
            return this;
        }
        JavaPropsSchema s = new JavaPropsSchema(this);
        s._pathSeparator = v;
        return s;
    }

    public JavaPropsSchema withoutPathSeparator() {
        if ("".equals(this._pathSeparator)) {
            return this;
        }
        JavaPropsSchema s = new JavaPropsSchema(this);
        s._pathSeparator = "";
        return s;
    }

    public JavaPropsSchema withIndexMarker(Markers v) {
        if (this._equals(v, this._indexMarker)) {
            return this;
        }
        JavaPropsSchema s = new JavaPropsSchema(this);
        s._indexMarker = v;
        return s;
    }

    public JavaPropsSchema withoutIndexMarker() {
        if (this._indexMarker == null) {
            return this;
        }
        JavaPropsSchema s = new JavaPropsSchema(this);
        s._indexMarker = null;
        return s;
    }

    public JavaPropsSchema withParseSimpleIndexes(boolean v) {
        if (v == this._parseSimpleIndexes) {
            return this;
        }
        JavaPropsSchema s = new JavaPropsSchema(this);
        s._parseSimpleIndexes = v;
        return s;
    }

    public JavaPropsSchema withWriteIndexUsingMarkers(boolean v) {
        if (v == this._writeIndexUsingMarkers) {
            return this;
        }
        JavaPropsSchema s = new JavaPropsSchema(this);
        s._writeIndexUsingMarkers = v;
        return s;
    }

    public JavaPropsSchema withLineIndentation(String v) {
        if (this._equals(v, this._lineIndentation)) {
            return this;
        }
        JavaPropsSchema s = new JavaPropsSchema(this);
        s._lineIndentation = v;
        return s;
    }

    public JavaPropsSchema withoutLineIndentation() {
        return this.withLineIndentation("");
    }

    public JavaPropsSchema withKeyValueSeparator(String v) {
        if (this._equals(v, this._keyValueSeparator)) {
            return this;
        }
        JavaPropsSchema s = new JavaPropsSchema(this);
        s._keyValueSeparator = v;
        return s;
    }

    public JavaPropsSchema withLineEnding(String v) {
        if (this._equals(v, this._lineEnding)) {
            return this;
        }
        JavaPropsSchema s = new JavaPropsSchema(this);
        s._lineEnding = v;
        return s;
    }

    public JavaPropsSchema withPrefix(String v) {
        if (this._equals(v, this._prefix)) {
            return this;
        }
        JavaPropsSchema s = new JavaPropsSchema(this);
        s._prefix = v;
        return s;
    }

    public JavaPropsSchema withHeader(String v) {
        if (this._equals(v, this._header)) {
            return this;
        }
        JavaPropsSchema s = new JavaPropsSchema(this);
        s._header = v;
        return s;
    }

    public JavaPropsSchema withoutHeader() {
        return this.withHeader("");
    }

    public String getSchemaType() {
        return "JavaProps";
    }

    public static JavaPropsSchema emptySchema() {
        return EMPTY;
    }

    public int firstArrayOffset() {
        return this._firstArrayOffset;
    }

    public String header() {
        return this._header;
    }

    public Markers indexMarker() {
        return this._indexMarker;
    }

    public String lineEnding() {
        return this._lineEnding;
    }

    public String lineIndentation() {
        return this._lineIndentation;
    }

    public String keyValueSeparator() {
        return this._keyValueSeparator;
    }

    public boolean parseSimpleIndexes() {
        return this._parseSimpleIndexes;
    }

    public String pathSeparator() {
        return this._pathSeparator;
    }

    public String prefix() {
        return this._prefix;
    }

    public boolean writeIndexUsingMarkers() {
        return this._writeIndexUsingMarkers && this._indexMarker != null;
    }

    private <V> boolean _equals(V a, V b) {
        if (a == null) {
            return b == null;
        }
        return b != null && a.equals(b);
    }
}

