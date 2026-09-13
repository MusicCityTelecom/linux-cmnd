/*
 * Decompiled with CFR 0.152.
 */
package groovy.lang;

import groovy.lang.Closure;
import groovy.lang.Range;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.codehaus.groovy.runtime.FormatHelper;

public class EmptyRange<T extends Comparable>
extends AbstractList<T>
implements Range<T> {
    protected T at;

    public EmptyRange(T at) {
        this.at = at;
    }

    @Override
    public T getFrom() {
        return this.at;
    }

    @Override
    public T getTo() {
        return this.at;
    }

    @Override
    public boolean isReverse() {
        return false;
    }

    @Override
    public boolean containsWithinBounds(Object o) {
        return false;
    }

    @Override
    public String inspect() {
        return FormatHelper.inspect(this.at) + "..<" + FormatHelper.inspect(this.at);
    }

    @Override
    public String toString() {
        return null == this.at ? "null..<null" : this.at + "..<" + this.at;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public T get(int index) {
        throw new IndexOutOfBoundsException("can't get values from Empty Ranges");
    }

    @Override
    public boolean add(T o) {
        throw new UnsupportedOperationException("cannot add to Empty Ranges");
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException("cannot add to Empty Ranges");
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException("cannot add to Empty Ranges");
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("cannot remove from Empty Ranges");
    }

    @Override
    public T remove(int index) {
        throw new UnsupportedOperationException("cannot remove from Empty Ranges");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("cannot remove from Empty Ranges");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("cannot retainAll in Empty Ranges");
    }

    @Override
    public T set(int index, T element) {
        throw new UnsupportedOperationException("cannot set in Empty Ranges");
    }

    @Override
    public void step(int step, Closure closure) {
    }

    @Override
    public List<T> step(int step) {
        return new ArrayList();
    }
}

