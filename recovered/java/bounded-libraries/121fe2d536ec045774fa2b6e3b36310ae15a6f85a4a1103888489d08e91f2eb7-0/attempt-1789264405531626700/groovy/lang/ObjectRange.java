/*
 * Decompiled with CFR 0.152.
 */
package groovy.lang;

import groovy.lang.Closure;
import groovy.lang.EmptyRange;
import groovy.lang.GroovyRuntimeException;
import groovy.lang.Range;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.codehaus.groovy.runtime.FormatHelper;
import org.codehaus.groovy.runtime.InvokerHelper;
import org.codehaus.groovy.runtime.IteratorClosureAdapter;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;
import org.codehaus.groovy.runtime.typehandling.NumberMath;

public class ObjectRange
extends AbstractList<Comparable>
implements Range<Comparable> {
    private final Comparable from;
    private final Comparable to;
    private int size = -1;
    private final boolean reverse;

    public ObjectRange(Comparable from, Comparable to) {
        this(from, to, null);
    }

    public ObjectRange(Comparable smaller, Comparable larger, boolean reverse) {
        this(smaller, larger, (Boolean)reverse);
    }

    private ObjectRange(Comparable smaller, Comparable larger, Boolean reverse) {
        if (smaller == null) {
            throw new IllegalArgumentException("Must specify a non-null value for the 'from' index in a Range");
        }
        if (larger == null) {
            throw new IllegalArgumentException("Must specify a non-null value for the 'to' index in a Range");
        }
        if (reverse == null) {
            boolean computedReverse = ObjectRange.areReversed(smaller, larger);
            if (computedReverse) {
                Comparable temp = larger;
                larger = smaller;
                smaller = temp;
            }
            this.reverse = computedReverse;
        } else {
            this.reverse = reverse;
        }
        if (smaller instanceof Short) {
            smaller = Integer.valueOf(((Short)smaller).intValue());
        } else if (smaller instanceof Float) {
            smaller = Double.valueOf(((Float)smaller).doubleValue());
        }
        if (larger instanceof Short) {
            larger = Integer.valueOf(((Short)larger).intValue());
        } else if (larger instanceof Float) {
            larger = Double.valueOf(((Float)larger).doubleValue());
        }
        if (smaller instanceof Integer && larger instanceof Long) {
            smaller = Long.valueOf(((Integer)smaller).longValue());
        } else if (larger instanceof Integer && smaller instanceof Long) {
            larger = Long.valueOf(((Integer)larger).longValue());
        }
        if (smaller.getClass() == larger.getClass() || smaller instanceof Number && larger instanceof Number) {
            this.from = smaller;
            this.to = larger;
        } else {
            Comparable tempfrom = ObjectRange.normaliseStringType(smaller);
            Comparable tempto = ObjectRange.normaliseStringType(larger);
            if (tempfrom instanceof Number && tempto instanceof Number) {
                this.from = tempfrom;
                this.to = tempto;
            } else {
                Comparable start;
                Comparable comparable = start = this.reverse ? larger : smaller;
                if (start instanceof String || start instanceof Number) {
                    throw new IllegalArgumentException("Incompatible Argument classes for ObjectRange " + smaller.getClass() + ", " + larger.getClass());
                }
                this.from = smaller;
                this.to = larger;
            }
        }
        this.checkBoundaryCompatibility();
    }

    protected void checkBoundaryCompatibility() {
        if (this.from instanceof String && this.to instanceof String) {
            int i;
            String start = this.from.toString();
            String end = this.to.toString();
            if (start.length() != end.length()) {
                throw new IllegalArgumentException("Incompatible Strings for Range: different length");
            }
            int length = start.length();
            for (i = 0; i < length && start.charAt(i) == end.charAt(i); ++i) {
            }
            if (i < length - 1) {
                throw new IllegalArgumentException("Incompatible Strings for Range: String#next() will not reach the expected value");
            }
        }
    }

    private static boolean areReversed(Comparable from, Comparable to) {
        try {
            return ScriptBytecodeAdapter.compareGreaterThan(from, to);
        }
        catch (IllegalArgumentException iae) {
            throw new IllegalArgumentException("Unable to create range due to incompatible types: " + from.getClass().getSimpleName() + ".." + to.getClass().getSimpleName() + " (possible missing brackets around range?)", iae);
        }
    }

    @Override
    public boolean equals(Object that) {
        return that instanceof ObjectRange ? this.equals((ObjectRange)that) : super.equals(that);
    }

    public boolean equals(ObjectRange that) {
        return that != null && this.reverse == that.reverse && DefaultTypeTransformation.compareEqual(this.from, that.from) && DefaultTypeTransformation.compareEqual(this.to, that.to);
    }

    @Override
    public Comparable getFrom() {
        return this.from;
    }

    @Override
    public Comparable getTo() {
        return this.to;
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
        StepIterator iter = new StepIterator(this, 1);
        Comparable value = iter.next();
        for (int i = 0; i < index; ++i) {
            if (!iter.hasNext()) {
                throw new IndexOutOfBoundsException("Index: " + index + " is too big for range: " + this);
            }
            value = iter.next();
        }
        return value;
    }

    @Override
    public boolean containsWithinBounds(Object value) {
        if (value instanceof Comparable) {
            int result = this.compareTo(this.from, (Comparable)value);
            return result == 0 || result < 0 && this.compareTo(this.to, (Comparable)value) >= 0;
        }
        return this.contains(value);
    }

    protected int compareTo(Comparable first, Comparable second) {
        return DefaultGroovyMethods.numberAwareCompareTo(first, second);
    }

    private void setSize(int size) {
        throw new UnsupportedOperationException("size must not be changed");
    }

    @Override
    public int size() {
        if (this.size == -1) {
            int tempsize = 0;
            if ((this.from instanceof Integer || this.from instanceof Long) && (this.to instanceof Integer || this.to instanceof Long)) {
                BigInteger fromNum = new BigInteger(this.from.toString());
                BigInteger toNum = new BigInteger(this.to.toString());
                BigInteger sizeNum = toNum.subtract(fromNum).add(new BigInteger("1"));
                tempsize = sizeNum.intValue();
                if (!BigInteger.valueOf(tempsize).equals(sizeNum)) {
                    tempsize = Integer.MAX_VALUE;
                }
            } else if (this.from instanceof Character && this.to instanceof Character) {
                char fromNum = ((Character)this.from).charValue();
                char toNum = ((Character)this.to).charValue();
                tempsize = toNum - fromNum + 1;
            } else if ((this.from instanceof BigDecimal || this.from instanceof BigInteger) && this.to instanceof Number || (this.to instanceof BigDecimal || this.to instanceof BigInteger) && this.from instanceof Number) {
                BigDecimal fromNum = NumberMath.toBigDecimal((Number)((Object)this.from));
                BigDecimal toNum = NumberMath.toBigDecimal((Number)((Object)this.to));
                BigInteger sizeNum = toNum.subtract(fromNum).add(BigDecimal.ONE).toBigInteger();
                tempsize = sizeNum.intValue();
                if (!BigInteger.valueOf(tempsize).equals(sizeNum)) {
                    tempsize = Integer.MAX_VALUE;
                }
            } else {
                StepIterator iter = new StepIterator(this, 1);
                while (iter.hasNext() && ++tempsize >= 0) {
                    iter.next();
                }
            }
            if (tempsize < 0) {
                tempsize = Integer.MAX_VALUE;
            }
            this.size = tempsize;
        }
        return this.size;
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
        StepIterator iter = new StepIterator(this, 1);
        Comparable toValue = (Comparable)iter.next();
        for (i = 0; i < fromIndex; ++i) {
            if (!iter.hasNext()) {
                throw new IndexOutOfBoundsException("Index: " + i + " is too big for range: " + this);
            }
            toValue = (Comparable)iter.next();
        }
        Comparable fromValue = toValue;
        while (i < toIndex - 1) {
            if (!iter.hasNext()) {
                throw new IndexOutOfBoundsException("Index: " + i + " is too big for range: " + this);
            }
            toValue = (Comparable)iter.next();
            ++i;
        }
        return new ObjectRange(fromValue, toValue, this.reverse);
    }

    @Override
    public String toString() {
        return this.reverse ? "" + this.to + ".." + this.from : "" + this.from + ".." + this.to;
    }

    @Override
    public String inspect() {
        String toText = FormatHelper.inspect(this.to);
        String fromText = FormatHelper.inspect(this.from);
        return this.reverse ? "" + toText + ".." + fromText : "" + fromText + ".." + toText;
    }

    @Override
    public boolean contains(Object value) {
        StepIterator iter = new StepIterator(this, 1);
        if (value == null) {
            return false;
        }
        while (iter.hasNext()) {
            if (!DefaultTypeTransformation.compareEqual(value, iter.next())) continue;
            return true;
        }
        return false;
    }

    @Override
    public void step(int step, Closure closure) {
        if (step == 0 && this.compareTo(this.from, this.to) == 0) {
            return;
        }
        StepIterator iter = new StepIterator(this, step);
        while (iter.hasNext()) {
            closure.call(iter.next());
        }
    }

    @Override
    public Iterator<Comparable> iterator() {
        return new StepIterator(this, 1);
    }

    @Override
    public List<Comparable> step(int step) {
        IteratorClosureAdapter adapter = new IteratorClosureAdapter(this);
        this.step(step, adapter);
        return adapter.asList();
    }

    protected Object increment(Object value) {
        return InvokerHelper.invokeMethod(value, "next", null);
    }

    protected Object decrement(Object value) {
        return InvokerHelper.invokeMethod(value, "previous", null);
    }

    private static Comparable normaliseStringType(Comparable operand) {
        if (operand instanceof Character) {
            return Integer.valueOf(((Character)operand).charValue());
        }
        if (operand instanceof String) {
            String string = (String)((Object)operand);
            if (string.length() == 1) {
                return Integer.valueOf(string.charAt(0));
            }
            return string;
        }
        return operand;
    }

    private static final class StepIterator
    implements Iterator<Comparable> {
        private final int step;
        private final ObjectRange range;
        private int index = -1;
        private Comparable value;
        private boolean nextFetched = true;

        private StepIterator(ObjectRange range, int desiredStep) {
            if (desiredStep == 0 && range.compareTo(range.getFrom(), range.getTo()) != 0) {
                throw new GroovyRuntimeException("Infinite loop detected due to step size of 0");
            }
            this.range = range;
            this.step = range.isReverse() ? -desiredStep : desiredStep;
            this.value = this.step > 0 ? range.getFrom() : range.getTo();
        }

        @Override
        public void remove() {
            this.range.remove(this.index);
        }

        @Override
        public Comparable next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.nextFetched = false;
            ++this.index;
            return this.value;
        }

        @Override
        public boolean hasNext() {
            if (!this.nextFetched) {
                this.value = this.peek();
                this.nextFetched = true;
            }
            return this.value != null;
        }

        private Comparable peek() {
            if (this.step > 0) {
                Comparable peekValue = this.value;
                for (int i = 0; i < this.step; ++i) {
                    if (this.range.compareTo(peekValue = (Comparable)this.range.increment(peekValue), this.range.from) > 0) continue;
                    return null;
                }
                if (this.range.compareTo(peekValue, this.range.to) <= 0) {
                    return peekValue;
                }
            } else {
                int positiveStep = -this.step;
                Comparable peekValue = this.value;
                for (int i = 0; i < positiveStep; ++i) {
                    if (this.range.compareTo(peekValue = (Comparable)this.range.decrement(peekValue), this.range.to) < 0) continue;
                    return null;
                }
                if (this.range.compareTo(peekValue, this.range.from) >= 0) {
                    return peekValue;
                }
            }
            return null;
        }
    }
}

