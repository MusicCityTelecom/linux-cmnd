/*
 * Decompiled with CFR 0.152.
 */
package groovy.lang;

import groovy.lang.Closure;
import groovy.lang.EmptyRange;
import groovy.lang.GroovyRuntimeException;
import groovy.lang.IntRange;
import groovy.lang.Range;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import org.codehaus.groovy.runtime.FormatHelper;
import org.codehaus.groovy.runtime.IteratorClosureAdapter;
import org.codehaus.groovy.runtime.RangeInfo;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.dgmimpl.NumberNumberMinus;
import org.codehaus.groovy.runtime.dgmimpl.NumberNumberMultiply;
import org.codehaus.groovy.runtime.dgmimpl.NumberNumberPlus;
import org.codehaus.groovy.runtime.typehandling.NumberMath;

public class NumberRange
extends AbstractList<Comparable>
implements Range<Comparable>,
Serializable {
    private static final long serialVersionUID = 5107424833653948484L;
    private final Comparable from;
    private final Comparable to;
    private final Number stepSize;
    private int size = -1;
    private Integer hashCodeCache = null;
    private final boolean reverse;
    private final boolean inclusiveLeft;
    private final boolean inclusiveRight;

    public <T extends Number, U extends Number> NumberRange(T from, U to) {
        this(from, to, null, true, true);
    }

    public <T extends Number, U extends Number> NumberRange(T from, U to, boolean inclusive) {
        this(from, to, null, true, inclusive);
    }

    public <T extends Number, U extends Number, V extends Number> NumberRange(T from, U to, V stepSize) {
        this(from, to, stepSize, true, true);
    }

    public <T extends Number, U extends Number, V extends Number> NumberRange(T from, U to, V stepSize, boolean inclusive) {
        this(from, to, stepSize, true, inclusive);
    }

    public <T extends Number, U extends Number, V extends Number> NumberRange(T from, U to, boolean inclusiveLeft, boolean inclusiveRight) {
        this(from, to, null, inclusiveLeft, inclusiveRight);
    }

    public <T extends Number, U extends Number, V extends Number> NumberRange(T from, U to, V stepSize, boolean inclusiveLeft, boolean inclusiveRight) {
        Object tempTo;
        Object tempFrom;
        if (from == null) {
            throw new IllegalArgumentException("Must specify a non-null value for the 'from' index in a Range");
        }
        if (to == null) {
            throw new IllegalArgumentException("Must specify a non-null value for the 'to' index in a Range");
        }
        this.reverse = NumberRange.areReversed(from, to);
        if (this.reverse) {
            tempFrom = to;
            tempTo = from;
        } else {
            tempFrom = from;
            tempTo = to;
        }
        if (tempFrom instanceof Short) {
            tempFrom = ((Number)tempFrom).intValue();
        } else if (tempFrom instanceof Float) {
            tempFrom = ((Number)tempFrom).doubleValue();
        }
        if (tempTo instanceof Short) {
            tempTo = ((Number)tempTo).intValue();
        } else if (tempTo instanceof Float) {
            tempTo = ((Number)tempTo).doubleValue();
        }
        if (tempFrom instanceof Integer && tempTo instanceof Long) {
            tempFrom = ((Number)tempFrom).longValue();
        } else if (tempTo instanceof Integer && tempFrom instanceof Long) {
            tempTo = ((Number)tempTo).longValue();
        }
        this.from = (Comparable)tempFrom;
        this.to = (Comparable)tempTo;
        this.stepSize = stepSize == null ? Integer.valueOf(1) : stepSize;
        this.inclusiveLeft = inclusiveLeft;
        this.inclusiveRight = inclusiveRight;
    }

    public RangeInfo subListBorders(int size) {
        if (this.stepSize.intValue() != 1) {
            throw new IllegalStateException("Step must be 1 when used by subList!");
        }
        return IntRange.subListBorders(((Number)((Object)this.from)).intValue(), ((Number)((Object)this.to)).intValue(), this.inclusiveLeft, this.inclusiveRight, size);
    }

    public <T extends Number> NumberRange by(T stepSize) {
        if (!Integer.valueOf(1).equals(this.stepSize)) {
            throw new IllegalStateException("by only allowed on ranges with original stepSize = 1 but found " + this.stepSize);
        }
        return new NumberRange(NumberRange.comparableNumber(this.from), NumberRange.comparableNumber(this.to), stepSize, this.inclusiveLeft, this.inclusiveRight);
    }

    static <T extends Number> T comparableNumber(Comparable c) {
        return (T)((Number)((Object)c));
    }

    static <T extends Number> T comparableNumber(Number n) {
        return (T)n;
    }

    private static boolean areReversed(Number from, Number to) {
        try {
            return ScriptBytecodeAdapter.compareGreaterThan(from, to);
        }
        catch (ClassCastException cce) {
            throw new IllegalArgumentException("Unable to create range due to incompatible types: " + from.getClass().getSimpleName() + ".." + to.getClass().getSimpleName() + " (possible missing brackets around range?)", cce);
        }
    }

    @Override
    public boolean equals(Object that) {
        return super.equals(that);
    }

    @Override
    public int hashCode() {
        if (this.hashCodeCache == null) {
            this.hashCodeCache = super.hashCode();
        }
        return this.hashCodeCache;
    }

    public boolean fastEquals(NumberRange that) {
        return that != null && this.reverse == that.reverse && this.inclusiveLeft == that.inclusiveLeft && this.inclusiveRight == that.inclusiveRight && ScriptBytecodeAdapter.compareEqual(this.from, that.from) && ScriptBytecodeAdapter.compareEqual(this.to, that.to) && ScriptBytecodeAdapter.compareEqual(this.stepSize, that.stepSize);
    }

    @Override
    public Comparable getFrom() {
        return this.from;
    }

    @Override
    public Comparable getTo() {
        return this.to;
    }

    public Comparable getStepSize() {
        return (Comparable)((Object)this.stepSize);
    }

    @Override
    public boolean isReverse() {
        return this.reverse;
    }

    @Override
    public Comparable get(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + " should not be negative");
        }
        StepIterator iter = new StepIterator(this, this.stepSize);
        Comparable value = (Comparable)iter.next();
        for (int i = 0; i < index; ++i) {
            if (!iter.hasNext()) {
                throw new IndexOutOfBoundsException("Index: " + index + " is too big for range: " + this);
            }
            value = (Comparable)iter.next();
        }
        return value;
    }

    @Override
    public boolean containsWithinBounds(Object value) {
        int result = ScriptBytecodeAdapter.compareTo(this.from, value);
        if ((this.reverse ? this.inclusiveRight : this.inclusiveLeft) && result == 0) {
            result = -1;
        }
        if (result >= 0) {
            return false;
        }
        result = ScriptBytecodeAdapter.compareTo(this.to, value);
        if ((this.reverse ? this.inclusiveLeft : this.inclusiveRight) && result == 0) {
            result = 1;
        }
        return result > 0;
    }

    private void setSize(int size) {
        throw new UnsupportedOperationException("size must not be changed");
    }

    @Override
    public int size() {
        if (this.size == -1) {
            this.calcSize(this.from, this.to, this.stepSize);
        }
        return this.size;
    }

    void calcSize(Comparable from, Comparable to, Number stepSize) {
        if (from == to && !this.inclusiveLeft && !this.inclusiveRight) {
            this.size = 0;
            return;
        }
        int tempsize = 0;
        boolean shortcut = false;
        if (this.isIntegral(stepSize)) {
            BigInteger sizeNum;
            Number toNum;
            Number toTemp;
            Number fromNum;
            Number fromTemp;
            if ((from instanceof Integer || from instanceof Long) && (to instanceof Integer || to instanceof Long)) {
                fromTemp = new BigInteger(from.toString());
                fromNum = this.inclusiveLeft ? fromTemp : ((BigInteger)fromTemp).add(BigInteger.ONE);
                toTemp = new BigInteger(to.toString());
                toNum = this.inclusiveRight ? toTemp : ((BigInteger)toTemp).subtract(BigInteger.ONE);
                sizeNum = new BigDecimal(((BigInteger)toNum).subtract((BigInteger)fromNum)).divide(BigDecimal.valueOf(stepSize.longValue()), RoundingMode.DOWN).toBigInteger().add(BigInteger.ONE);
                tempsize = sizeNum.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) < 0 ? sizeNum.intValue() : Integer.MAX_VALUE;
                shortcut = true;
            } else if ((from instanceof BigDecimal || from instanceof BigInteger) && to instanceof Number || (to instanceof BigDecimal || to instanceof BigInteger) && from instanceof Number) {
                fromTemp = NumberMath.toBigDecimal((Number)((Object)from));
                fromNum = this.inclusiveLeft ? fromTemp : ((BigDecimal)fromTemp).add(BigDecimal.ONE);
                toTemp = NumberMath.toBigDecimal((Number)((Object)to));
                toNum = this.inclusiveRight ? toTemp : ((BigDecimal)toTemp).subtract(BigDecimal.ONE);
                sizeNum = ((BigDecimal)toNum).subtract((BigDecimal)fromNum).divide(BigDecimal.valueOf(stepSize.longValue()), RoundingMode.DOWN).toBigInteger().add(BigInteger.ONE);
                tempsize = sizeNum.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) < 0 ? sizeNum.intValue() : Integer.MAX_VALUE;
                shortcut = true;
            }
        }
        if (!shortcut) {
            StepIterator iter = new StepIterator(this, stepSize);
            while (iter.hasNext() && ++tempsize >= 0) {
                iter.next();
            }
            if (tempsize < 0) {
                tempsize = Integer.MAX_VALUE;
            }
        }
        this.size = tempsize;
    }

    private boolean isIntegral(Number stepSize) {
        BigDecimal tempStepSize = NumberMath.toBigDecimal(stepSize);
        return tempStepSize.equals(new BigDecimal(tempStepSize.toBigInteger()));
    }

    @Override
    public List<Comparable> subList(int fromIndex, int toIndex) {
        int i;
        if (fromIndex < 0) {
            throw new IndexOutOfBoundsException("fromIndex = " + fromIndex);
        }
        if (fromIndex > toIndex) {
            throw new IllegalArgumentException("fromIndex(" + fromIndex + ") > toIndex(" + toIndex + ")");
        }
        if (fromIndex == toIndex) {
            return new EmptyRange<Comparable>(this.from);
        }
        StepIterator iter = new StepIterator(this, this.stepSize);
        Comparable value = (Comparable)iter.next();
        for (i = 0; i < fromIndex; ++i) {
            if (!iter.hasNext()) {
                throw new IndexOutOfBoundsException("Index: " + i + " is too big for range: " + this);
            }
            value = (Comparable)iter.next();
        }
        Comparable fromValue = value;
        while (i < toIndex - 1) {
            if (!iter.hasNext()) {
                throw new IndexOutOfBoundsException("Index: " + i + " is too big for range: " + this);
            }
            value = (Comparable)iter.next();
            ++i;
        }
        Comparable toValue = value;
        return new NumberRange(NumberRange.comparableNumber(fromValue), NumberRange.comparableNumber(toValue), NumberRange.comparableNumber(this.stepSize), true);
    }

    @Override
    public String toString() {
        return this.getToString(this.to.toString(), this.from.toString());
    }

    @Override
    public String inspect() {
        return this.getToString(FormatHelper.inspect(this.to), FormatHelper.inspect(this.from));
    }

    private String getToString(String toText, String fromText) {
        String sepLeft = this.inclusiveLeft ? ".." : "<..";
        String sep = this.inclusiveRight ? sepLeft : sepLeft + "<";
        String base = this.reverse ? "" + toText + sep + fromText : "" + fromText + sep + toText;
        return Integer.valueOf(1).equals(this.stepSize) ? base : base + ".by(" + this.stepSize + ")";
    }

    @Override
    public boolean contains(Object value) {
        if (value == null) {
            return false;
        }
        StepIterator it = new StepIterator(this, this.stepSize);
        while (it.hasNext()) {
            if (!ScriptBytecodeAdapter.compareEqual(value, it.next())) continue;
            return true;
        }
        return false;
    }

    @Override
    public void step(int numSteps, Closure closure) {
        if (numSteps == 0 && ScriptBytecodeAdapter.compareTo(this.from, this.to) == 0) {
            return;
        }
        StepIterator iter = new StepIterator(this, NumberNumberMultiply.multiply(numSteps, this.stepSize));
        while (iter.hasNext()) {
            closure.call((Object)iter.next());
        }
    }

    @Override
    public Iterator<Comparable> iterator() {
        return new StepIterator(this, this.stepSize);
    }

    @Override
    public List<Comparable> step(int numSteps) {
        IteratorClosureAdapter adapter = new IteratorClosureAdapter(this);
        this.step(numSteps, adapter);
        return adapter.asList();
    }

    private Comparable increment(Object value, Number step) {
        return (Comparable)((Object)NumberNumberPlus.plus((Number)value, step));
    }

    private Comparable decrement(Object value, Number step) {
        return (Comparable)((Object)NumberNumberMinus.minus((Number)value, step));
    }

    private class StepIterator
    implements Iterator<Comparable> {
        private final NumberRange range;
        private final Number step;
        private final boolean isAscending;
        private boolean isNextFetched = false;
        private Comparable next = null;

        StepIterator(NumberRange range, Number step) {
            if (ScriptBytecodeAdapter.compareEqual(step, 0) && ScriptBytecodeAdapter.compareNotEqual(range.getFrom(), range.getTo())) {
                throw new GroovyRuntimeException("Infinite loop detected due to step size of 0");
            }
            this.range = range;
            if (ScriptBytecodeAdapter.compareLessThan(step, 0)) {
                this.step = NumberNumberMultiply.multiply(step, -1);
                this.isAscending = range.isReverse();
            } else {
                this.step = step;
                this.isAscending = !range.isReverse();
            }
        }

        @Override
        public boolean hasNext() {
            this.fetchNextIfNeeded();
            if (this.next == null) {
                return false;
            }
            if (this.isAscending) {
                return this.range.inclusiveRight ? ScriptBytecodeAdapter.compareLessThanEqual(this.next, this.range.getTo()) : ScriptBytecodeAdapter.compareLessThan(this.next, this.range.getTo());
            }
            return this.range.inclusiveRight ? ScriptBytecodeAdapter.compareGreaterThanEqual(this.next, this.range.getFrom()) : ScriptBytecodeAdapter.compareGreaterThan(this.next, this.range.getFrom());
        }

        @Override
        public Comparable next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.fetchNextIfNeeded();
            this.isNextFetched = false;
            return this.next;
        }

        private void fetchNextIfNeeded() {
            if (this.isNextFetched) {
                return;
            }
            this.isNextFetched = true;
            if (this.next == null) {
                Comparable comparable = this.next = this.isAscending ? this.range.getFrom() : this.range.getTo();
                if (!this.range.inclusiveLeft) {
                    this.next = this.isAscending ? NumberRange.this.increment(this.next, this.step) : NumberRange.this.decrement(this.next, this.step);
                }
            } else {
                this.next = this.isAscending ? NumberRange.this.increment(this.next, this.step) : NumberRange.this.decrement(this.next, this.step);
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}

