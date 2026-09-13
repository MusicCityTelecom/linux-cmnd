/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.internal;

import java.text.ParseException;
import org.glassfish.jersey.message.internal.HttpHeaderReader;

class HttpHeaderListAdapter
extends HttpHeaderReader {
    private final HttpHeaderReader reader;
    private boolean isTerminated;

    public HttpHeaderListAdapter(HttpHeaderReader reader) {
        this.reader = reader;
    }

    public void reset() {
        this.isTerminated = false;
    }

    @Override
    public boolean hasNext() {
        if (this.isTerminated) {
            return false;
        }
        if (this.reader.hasNext()) {
            if (this.reader.hasNextSeparator(',', true)) {
                this.isTerminated = true;
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean hasNextSeparator(char separator, boolean skipWhiteSpace) {
        if (this.isTerminated) {
            return false;
        }
        if (this.reader.hasNextSeparator(',', skipWhiteSpace)) {
            this.isTerminated = true;
            return false;
        }
        return this.reader.hasNextSeparator(separator, skipWhiteSpace);
    }

    @Override
    public HttpHeaderReader.Event next() throws ParseException {
        return this.next(true);
    }

    @Override
    public HttpHeaderReader.Event next(boolean skipWhiteSpace) throws ParseException {
        return this.next(skipWhiteSpace, false);
    }

    @Override
    public HttpHeaderReader.Event next(boolean skipWhiteSpace, boolean preserveBackslash) throws ParseException {
        if (this.isTerminated) {
            throw new ParseException("End of header", this.getIndex());
        }
        if (this.reader.hasNextSeparator(',', skipWhiteSpace)) {
            this.isTerminated = true;
            throw new ParseException("End of header", this.getIndex());
        }
        return this.reader.next(skipWhiteSpace, preserveBackslash);
    }

    @Override
    public CharSequence nextSeparatedString(char startSeparator, char endSeparator) throws ParseException {
        if (this.isTerminated) {
            throw new ParseException("End of header", this.getIndex());
        }
        if (this.reader.hasNextSeparator(',', true)) {
            this.isTerminated = true;
            throw new ParseException("End of header", this.getIndex());
        }
        return this.reader.nextSeparatedString(startSeparator, endSeparator);
    }

    @Override
    public HttpHeaderReader.Event getEvent() {
        return this.reader.getEvent();
    }

    @Override
    public CharSequence getEventValue() {
        return this.reader.getEventValue();
    }

    @Override
    public CharSequence getRemainder() {
        return this.reader.getRemainder();
    }

    @Override
    public int getIndex() {
        return this.reader.getIndex();
    }
}

