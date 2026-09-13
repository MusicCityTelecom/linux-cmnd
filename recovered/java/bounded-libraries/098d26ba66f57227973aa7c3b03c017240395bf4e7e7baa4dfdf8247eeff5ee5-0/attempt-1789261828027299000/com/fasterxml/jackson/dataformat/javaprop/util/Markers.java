/*
 * Decompiled with CFR 0.152.
 */
package com.fasterxml.jackson.dataformat.javaprop.util;

public class Markers {
    protected final String _start;
    protected final String _end;

    protected Markers(String start, String end) {
        if (start == null || start.isEmpty()) {
            throw new IllegalArgumentException("Missing 'start' value");
        }
        if (end == null || end.isEmpty()) {
            throw new IllegalArgumentException("Missing 'end' value");
        }
        this._start = start;
        this._end = end;
    }

    public static Markers create(String start, String end) {
        return new Markers(start, end);
    }

    public String getStart() {
        return this._start;
    }

    public String getEnd() {
        return this._end;
    }
}

