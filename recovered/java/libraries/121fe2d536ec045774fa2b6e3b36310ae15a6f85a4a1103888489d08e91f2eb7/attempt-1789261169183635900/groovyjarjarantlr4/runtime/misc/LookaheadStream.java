/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.runtime.misc;

import groovyjarjarantlr4.runtime.misc.FastQueue;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public abstract class LookaheadStream<T>
extends FastQueue<T> {
    public static final int UNINITIALIZED_EOF_ELEMENT_INDEX = Integer.MAX_VALUE;
    protected int currentElementIndex = 0;
    protected T prevElement;
    public T eof = null;
    protected int lastMarker;
    protected int markDepth = 0;

    @Override
    public void reset() {
        super.reset();
        this.currentElementIndex = 0;
        this.p = 0;
        this.prevElement = null;
    }

    public abstract T nextElement();

    public abstract boolean isEOF(T var1);

    @Override
    public T remove() {
        Object o = this.elementAt(0);
        ++this.p;
        if (this.p == this.data.size() && this.markDepth == 0) {
            this.prevElement = o;
            this.clear();
        }
        return o;
    }

    public void consume() {
        this.syncAhead(1);
        this.remove();
        ++this.currentElementIndex;
    }

    protected void syncAhead(int need) {
        int n = this.p + need - 1 - this.data.size() + 1;
        if (n > 0) {
            this.fill(n);
        }
    }

    public void fill(int n) {
        for (int i = 1; i <= n; ++i) {
            T o = this.nextElement();
            if (this.isEOF(o)) {
                this.eof = o;
            }
            this.data.add(o);
        }
    }

    @Override
    public int size() {
        throw new UnsupportedOperationException("streams are of unknown size");
    }

    public T LT(int k) {
        if (k == 0) {
            return null;
        }
        if (k < 0) {
            return this.LB(-k);
        }
        this.syncAhead(k);
        if (this.p + k - 1 > this.data.size()) {
            return this.eof;
        }
        return this.elementAt(k - 1);
    }

    public int index() {
        return this.currentElementIndex;
    }

    public int mark() {
        ++this.markDepth;
        this.lastMarker = this.p;
        return this.lastMarker;
    }

    public void release(int marker) {
    }

    public void rewind(int marker) {
        --this.markDepth;
        int delta = this.p - marker;
        this.currentElementIndex -= delta;
        this.p = marker;
    }

    public void rewind() {
        int delta = this.p - this.lastMarker;
        this.currentElementIndex -= delta;
        this.p = this.lastMarker;
    }

    public void seek(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("can't seek before the beginning of the input");
        }
        int delta = this.currentElementIndex - index;
        if (this.p - delta < 0) {
            throw new UnsupportedOperationException("can't seek before the beginning of this stream's buffer");
        }
        this.p -= delta;
        this.currentElementIndex = index;
    }

    protected T LB(int k) {
        assert (k > 0);
        int index = this.p - k;
        if (index == -1) {
            return this.prevElement;
        }
        if (index >= 0) {
            return (T)this.data.get(index);
        }
        if (index < -1) {
            throw new UnsupportedOperationException("can't look more than one token before the beginning of this stream's buffer");
        }
        throw new UnsupportedOperationException("can't look past the end of this stream's buffer using LB(int)");
    }
}

