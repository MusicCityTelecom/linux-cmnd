/*
 * Decompiled with CFR 0.152.
 */
package groovy.lang;

import groovy.lang.Closure;
import groovy.lang.EmptyRange;
import groovy.lang.GroovyRuntimeException;
import groovy.lang.NumberRange;
import groovy.lang.Range;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.AbstractList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import org.codehaus.groovy.runtime.IteratorClosureAdapter;
import org.codehaus.groovy.runtime.RangeInfo;

public class IntRange
extends AbstractList<Integer>
implements Range<Integer>,
Serializable {
    private static final long serialVersionUID = -7827097587793510780L;
    private final int from;
    private final int to;
    private final boolean reverse;
    private final Boolean inclusiveRight;
    private final Boolean inclusiveLeft;

    public IntRange(int from, int to) {
        this.inclusiveRight = null;
        this.inclusiveLeft = null;
        if (from > to) {
            this.from = to;
            this.to = from;
            this.reverse = true;
        } else {
            this.from = from;
            this.to = to;
            this.reverse = false;
        }
        this.checkSize();
    }

    protected IntRange(int from, int to, boolean reverse) {
        this.inclusiveRight = null;
        this.inclusiveLeft = null;
        if (from > to) {
            throw new IllegalArgumentException("'from' must be less than or equal to 'to'");
        }
        this.from = from;
        this.to = to;
        this.reverse = reverse;
        this.checkSize();
    }

    public IntRange(boolean inclusiveRight, int from, int to) {
        this(true, inclusiveRight, from, to);
    }

    public IntRange(boolean inclusiveLeft, boolean inclusiveRight, int from, int to) {
        this.from = from;
        this.to = to;
        this.inclusiveRight = inclusiveRight;
        this.inclusiveLeft = inclusiveLeft;
        this.reverse = false;
        this.checkSize();
    }

    public <T extends Number> NumberRange by(T stepSize) {
        return new NumberRange(this.getFrom(), this.getTo(), stepSize, true, true);
    }

    private void checkSize() {
        long size = (long)this.to - (long)this.from + 1L;
        if (size > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("A range must have no more than 2147483647 elements but attempted " + size + " elements");
        }
    }

    public RangeInfo subListBorders(int size) {
        if (this.inclusiveRight == null || this.inclusiveLeft == null) {
            throw new IllegalStateException("Should not call subListBorders on a non-inclusive aware IntRange");
        }
        return IntRange.subListBorders(this.from, this.to, this.inclusiveLeft, this.inclusiveRight, size);
    }

    static RangeInfo subListBorders(int from, int to, boolean inclusiveRight, int size) {
        return IntRange.subListBorders(from, to, true, inclusiveRight, size);
    }

    static RangeInfo subListBorders(int from, int to, boolean inclusiveLeft, boolean inclusiveRight, int size) {
        int tempTo;
        int tempFrom = from;
        if (tempFrom < 0) {
            tempFrom += size;
        }
        if ((tempTo = to) < 0) {
            tempTo += size;
        }
        if (tempFrom > tempTo) {
            return new RangeInfo(inclusiveRight ? tempTo : tempTo + 1, inclusiveLeft ? tempFrom + 1 : tempFrom, true);
        }
        return new RangeInfo(inclusiveLeft ? tempFrom : tempFrom + 1, inclusiveRight ? tempTo + 1 : tempTo, false);
    }

    @Override
    public boolean equals(Object that) {
        return that instanceof IntRange ? this.equals((IntRange)that) : super.equals(that);
    }

    public boolean equals(IntRange that) {
        return that != null && this.from == that.from && this.to == that.to && (this.inclusiveRight == null ? this.reverse == that.reverse : Objects.equals(this.inclusiveLeft, that.inclusiveLeft) && Objects.equals(this.inclusiveRight, that.inclusiveRight));
    }

    @Override
    public Integer getFrom() {
        if (this.from <= this.to) {
            return this.inclusiveLeft == null || this.inclusiveLeft != false ? this.from : this.from + 1;
        }
        return this.inclusiveRight == null || this.inclusiveRight != false ? this.to : this.to + 1;
    }

    @Override
    public Integer getTo() {
        if (this.from <= this.to) {
            return this.inclusiveRight == null || this.inclusiveRight != false ? this.to : this.to - 1;
        }
        return this.inclusiveLeft == null || this.inclusiveLeft != false ? this.from : this.from - 1;
    }

    public Boolean getInclusive() {
        return this.getInclusiveRight();
    }

    public Boolean getInclusiveRight() {
        return this.inclusiveRight;
    }

    public Boolean getInclusiveLeft() {
        return this.inclusiveLeft;
    }

    public int getFromInt() {
        return this.getFrom();
    }

    public int getToInt() {
        return this.getTo();
    }

    @Override
    public boolean isReverse() {
        return this.inclusiveRight == null && this.inclusiveLeft == null ? this.reverse : this.from > this.to;
    }

    @Override
    public boolean containsWithinBounds(Object o) {
        return this.contains(o);
    }

    @Override
    public Integer get(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + " should not be negative");
        }
        if (index >= this.size()) {
            throw new IndexOutOfBoundsException("Index: " + index + " too big for range: " + this);
        }
        return this.isReverse() ? this.getTo() - index : index + this.getFrom();
    }

    @Override
    public int size() {
        return Math.max(this.getTo() - this.getFrom() + 1, 0);
    }

    @Override
    public Iterator<Integer> iterator() {
        return new IntRangeIterator();
    }

    @Override
    public List<Integer> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0) {
            throw new IndexOutOfBoundsException("fromIndex = " + fromIndex);
        }
        if (toIndex > this.size()) {
            throw new IndexOutOfBoundsException("toIndex = " + toIndex);
        }
        if (fromIndex > toIndex) {
            throw new IllegalArgumentException("fromIndex(" + fromIndex + ") > toIndex(" + toIndex + ")");
        }
        if (fromIndex == toIndex) {
            return new EmptyRange<Integer>(this.getFrom());
        }
        return new IntRange(fromIndex + this.getFrom(), toIndex + this.getFrom() - 1, this.isReverse());
    }

    @Override
    public String toString() {
        if (this.inclusiveRight == null && this.inclusiveLeft == null) {
            return this.reverse ? "" + this.to + ".." + this.from : "" + this.from + ".." + this.to;
        }
        return "" + this.from + (this.inclusiveLeft != false ? "" : "<") + ".." + (this.inclusiveRight != false ? "" : "<") + this.to;
    }

    @Override
    public String inspect() {
        return this.toString();
    }

    @Override
    public boolean contains(Object value) {
        if (value instanceof Integer) {
            return (Integer)value >= this.getFrom() && (Integer)value <= this.getTo();
        }
        if (value instanceof BigInteger) {
            BigInteger bigint = (BigInteger)value;
            return bigint.compareTo(BigInteger.valueOf(this.getFrom().intValue())) >= 0 && bigint.compareTo(BigInteger.valueOf(this.getTo().intValue())) <= 0;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection other) {
        if (other instanceof IntRange) {
            IntRange range = (IntRange)other;
            return this.getFrom() <= range.getFrom() && range.getTo() <= this.getTo();
        }
        return super.containsAll(other);
    }

    @Override
    public void step(int step, Closure closure) {
        if (step == 0) {
            if (!this.getFrom().equals(this.getTo())) {
                throw new GroovyRuntimeException("Infinite loop detected due to step size of 0");
            }
            return;
        }
        if (this.isReverse()) {
            step = -step;
        }
        if (step > 0) {
            for (int value = this.getFrom().intValue(); value <= this.getTo(); value += step) {
                closure.call((Object)value);
                if ((long)value + (long)step < Integer.MAX_VALUE) {
                    continue;
                }
                break;
            }
        } else {
            for (int value = this.getTo().intValue(); value >= this.getFrom(); value += step) {
                closure.call((Object)value);
                if ((long)value + (long)step > Integer.MIN_VALUE) {
                    continue;
                }
                break;
            }
        }
    }

    @Override
    public List<Integer> step(int step) {
        IteratorClosureAdapter adapter = new IteratorClosureAdapter(this);
        this.step(step, adapter);
        return adapter.asList();
    }

    @Override
    public int hashCode() {
        int from = this.getFrom();
        int to = this.getTo();
        int hashCode = (from + to + 1) * (from + to) / 2 + to;
        return hashCode;
    }

    private class IntRangeIterator
    implements Iterator<Integer> {
        private int index;
        private int size;
        private int value;

        private IntRangeIterator() {
            this.size = IntRange.this.size();
            this.value = IntRange.this.isReverse() ? IntRange.this.getTo() : IntRange.this.getFrom();
        }

        @Override
        public boolean hasNext() {
            return this.index < this.size;
        }

        @Override
        public Integer next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            if (this.index++ > 0) {
                this.value = IntRange.this.isReverse() ? --this.value : ++this.value;
            }
            return this.value;
        }

        @Override
        public void remove() {
            IntRange.this.remove(this.index);
        }
    }
}

