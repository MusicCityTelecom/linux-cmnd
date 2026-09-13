/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlOperator;
import org.apache.commons.jexl3.JexlOptions;
import org.apache.commons.jexl3.internal.IntegerRange;
import org.apache.commons.jexl3.internal.LongRange;
import org.apache.commons.jexl3.introspection.JexlMethod;

public class JexlArithmetic {
    protected static final BigDecimal BIGD_DOUBLE_MAX_VALUE = BigDecimal.valueOf(Double.MAX_VALUE);
    protected static final BigDecimal BIGD_DOUBLE_MIN_VALUE = BigDecimal.valueOf(Double.MIN_VALUE);
    protected static final BigInteger BIGI_LONG_MAX_VALUE = BigInteger.valueOf(Long.MAX_VALUE);
    protected static final BigInteger BIGI_LONG_MIN_VALUE = BigInteger.valueOf(Long.MIN_VALUE);
    protected static final int BIGD_SCALE = -1;
    private final boolean strict;
    private final MathContext mathContext;
    private final int mathScale;
    private final Constructor<? extends JexlArithmetic> ctor;
    public static final Pattern FLOAT_PATTERN = Pattern.compile("^[+-]?\\d*(\\.\\d*)?([eE][+-]?\\d+)?$");

    public JexlArithmetic(boolean astrict) {
        this(astrict, null, Integer.MIN_VALUE);
    }

    public JexlArithmetic(boolean astrict, MathContext bigdContext, int bigdScale) {
        this.strict = astrict;
        this.mathContext = bigdContext == null ? MathContext.DECIMAL128 : bigdContext;
        this.mathScale = bigdScale == Integer.MIN_VALUE ? -1 : bigdScale;
        Constructor<?> actor = null;
        try {
            actor = this.getClass().getConstructor(Boolean.TYPE, MathContext.class, Integer.TYPE);
        }
        catch (Exception exception) {
            // empty catch block
        }
        this.ctor = actor;
    }

    public JexlArithmetic options(JexlOptions options) {
        if (options != null) {
            int bigdScale;
            boolean ostrict = options.isStrictArithmetic();
            MathContext bigdContext = options.getMathContext();
            if (bigdContext == null) {
                bigdContext = this.getMathContext();
            }
            if ((bigdScale = options.getMathScale()) == Integer.MIN_VALUE) {
                bigdScale = this.getMathScale();
            }
            if (ostrict != this.isStrict() || bigdScale != this.getMathScale() || bigdContext != this.getMathContext()) {
                return this.createWithOptions(ostrict, bigdContext, bigdScale);
            }
        }
        return this;
    }

    @Deprecated
    public JexlArithmetic options(JexlEngine.Options options) {
        if (options != null) {
            int bigdScale;
            MathContext bigdContext;
            Boolean ostrict = options.isStrictArithmetic();
            if (ostrict == null) {
                ostrict = this.isStrict();
            }
            if ((bigdContext = options.getArithmeticMathContext()) == null) {
                bigdContext = this.getMathContext();
            }
            if ((bigdScale = options.getArithmeticMathScale()) == Integer.MIN_VALUE) {
                bigdScale = this.getMathScale();
            }
            if (ostrict.booleanValue() != this.isStrict() || bigdScale != this.getMathScale() || bigdContext != this.getMathContext()) {
                return this.createWithOptions(ostrict, bigdContext, bigdScale);
            }
        }
        return this;
    }

    public JexlArithmetic options(JexlContext context) {
        if (context instanceof JexlContext.OptionsHandle) {
            return this.options(((JexlContext.OptionsHandle)((Object)context)).getEngineOptions());
        }
        if (context instanceof JexlEngine.Options) {
            return this.options((JexlEngine.Options)((Object)context));
        }
        return this;
    }

    protected JexlArithmetic createWithOptions(boolean astrict, MathContext bigdContext, int bigdScale) {
        if (this.ctor != null) {
            try {
                return this.ctor.newInstance(astrict, bigdContext, bigdScale);
            }
            catch (IllegalAccessException | IllegalArgumentException | InstantiationException | InvocationTargetException exception) {
                // empty catch block
            }
        }
        return new JexlArithmetic(astrict, bigdContext, bigdScale);
    }

    public ArrayBuilder arrayBuilder(int size) {
        return new org.apache.commons.jexl3.internal.ArrayBuilder(size);
    }

    public SetBuilder setBuilder(int size) {
        return new org.apache.commons.jexl3.internal.SetBuilder(size);
    }

    public MapBuilder mapBuilder(int size) {
        return new org.apache.commons.jexl3.internal.MapBuilder(size);
    }

    public Iterable<?> createRange(Object from, Object to) throws ArithmeticException {
        long lfrom = this.toLong(from);
        long lto = this.toLong(to);
        if (lfrom >= Integer.MIN_VALUE && lfrom <= Integer.MAX_VALUE && lto >= Integer.MIN_VALUE && lto <= Integer.MAX_VALUE) {
            return IntegerRange.create((int)lfrom, (int)lto);
        }
        return LongRange.create(lfrom, lto);
    }

    public boolean isStrict() {
        return this.strict;
    }

    public MathContext getMathContext() {
        return this.mathContext;
    }

    public int getMathScale() {
        return this.mathScale;
    }

    protected BigDecimal roundBigDecimal(BigDecimal number) {
        int mscale = this.getMathScale();
        if (mscale >= 0) {
            return number.setScale(mscale, this.getMathContext().getRoundingMode());
        }
        return number;
    }

    protected Object controlNullNullOperands() {
        if (this.isStrict()) {
            throw new NullOperand();
        }
        return 0;
    }

    protected void controlNullOperand() {
        if (this.isStrict()) {
            throw new NullOperand();
        }
    }

    protected boolean isFloatingPointNumber(Object val) {
        if (val instanceof Float || val instanceof Double) {
            return true;
        }
        if (val instanceof CharSequence) {
            Matcher m = FLOAT_PATTERN.matcher((CharSequence)val);
            return m.matches() && (m.start(1) >= 0 || m.start(2) >= 0);
        }
        return false;
    }

    protected boolean isFloatingPoint(Object o) {
        return o instanceof Float || o instanceof Double;
    }

    protected boolean isNumberable(Object o) {
        return o instanceof Integer || o instanceof Long || o instanceof Byte || o instanceof Short || o instanceof Character;
    }

    public Number narrow(Number original) {
        return this.narrowNumber(original, null);
    }

    protected boolean narrowAccept(Class<?> narrow, Class<?> source) {
        return narrow == null || narrow.equals(source);
    }

    public Number narrowNumber(Number original, Class<?> narrow) {
        if (original == null) {
            return null;
        }
        Number result = original;
        if (original instanceof BigDecimal) {
            BigDecimal bigd = (BigDecimal)original;
            if (bigd.compareTo(BIGD_DOUBLE_MAX_VALUE) > 0 || bigd.compareTo(BIGD_DOUBLE_MIN_VALUE) < 0) {
                return original;
            }
            try {
                long l = bigd.longValueExact();
                if (this.narrowAccept(narrow, Integer.class) && l <= Integer.MAX_VALUE && l >= Integer.MIN_VALUE) {
                    return (int)l;
                }
                if (this.narrowAccept(narrow, Long.class)) {
                    return l;
                }
            }
            catch (ArithmeticException arithmeticException) {
                // empty catch block
            }
        }
        if (original instanceof Double || original instanceof Float) {
            double value = original.doubleValue();
            if (this.narrowAccept(narrow, Float.class) && value <= 3.4028234663852886E38 && value >= (double)1.4E-45f) {
                result = Float.valueOf(result.floatValue());
            }
        } else {
            BigInteger bigi;
            if (original instanceof BigInteger && ((bigi = (BigInteger)original).compareTo(BIGI_LONG_MAX_VALUE) > 0 || bigi.compareTo(BIGI_LONG_MIN_VALUE) < 0)) {
                return original;
            }
            long value = original.longValue();
            if (this.narrowAccept(narrow, Byte.class) && value <= 127L && value >= -128L) {
                result = (byte)value;
            } else if (this.narrowAccept(narrow, Short.class) && value <= 32767L && value >= -32768L) {
                result = (short)value;
            } else if (this.narrowAccept(narrow, Integer.class) && value <= Integer.MAX_VALUE && value >= Integer.MIN_VALUE) {
                result = (int)value;
            }
        }
        return result;
    }

    protected Number narrowBigInteger(Object lhs, Object rhs, BigInteger bigi) {
        if (!(lhs instanceof BigInteger) && !(rhs instanceof BigInteger) && bigi.compareTo(BIGI_LONG_MAX_VALUE) <= 0 && bigi.compareTo(BIGI_LONG_MIN_VALUE) >= 0) {
            long l = bigi.longValue();
            if (!(lhs instanceof Long) && !(rhs instanceof Long) && l <= Integer.MAX_VALUE && l >= Integer.MIN_VALUE) {
                return (int)l;
            }
            return l;
        }
        return bigi;
    }

    protected Number narrowBigDecimal(Object lhs, Object rhs, BigDecimal bigd) {
        if (this.isNumberable(lhs) || this.isNumberable(rhs)) {
            try {
                long l = bigd.longValueExact();
                if (l <= Integer.MAX_VALUE && l >= Integer.MIN_VALUE) {
                    return (int)l;
                }
                return l;
            }
            catch (ArithmeticException arithmeticException) {
                // empty catch block
            }
        }
        return bigd;
    }

    public boolean narrowArguments(Object[] args) {
        boolean narrowed = false;
        if (args != null) {
            for (int a = 0; a < args.length; ++a) {
                Number narrow;
                Number narg;
                Object arg = args[a];
                if (!(arg instanceof Number) || (narg = (Number)arg).equals(narrow = this.narrow(narg))) continue;
                args[a] = narrow;
                narrowed = true;
            }
        }
        return narrowed;
    }

    protected Number narrowLong(Object lhs, Object rhs, long r) {
        if (!(lhs instanceof Long) && !(rhs instanceof Long) && (long)((int)r) == r) {
            return (int)r;
        }
        return r;
    }

    protected Number asLongNumber(Object value) {
        return value instanceof Long || value instanceof Integer || value instanceof Short || value instanceof Byte ? (Number)value : null;
    }

    public Object add(Object left, Object right) {
        block8: {
            boolean strconcat;
            if (left == null && right == null) {
                return this.controlNullNullOperands();
            }
            boolean bl = this.strict ? left instanceof String || right instanceof String : (strconcat = left instanceof String && right instanceof String);
            if (!strconcat) {
                try {
                    Number ln = this.asLongNumber(left);
                    Number rn = this.asLongNumber(right);
                    if (ln != null && rn != null) {
                        long y;
                        long result;
                        long x = ln.longValue();
                        if (((x ^ (result = x + (y = rn.longValue()))) & (y ^ result)) < 0L) {
                            return BigInteger.valueOf(x).add(BigInteger.valueOf(y));
                        }
                        return this.narrowLong(left, right, result);
                    }
                    if (left instanceof BigDecimal || right instanceof BigDecimal) {
                        BigDecimal l = this.toBigDecimal(left);
                        BigDecimal r = this.toBigDecimal(right);
                        BigDecimal result = l.add(r, this.getMathContext());
                        return this.narrowBigDecimal(left, right, result);
                    }
                    if (this.isFloatingPointNumber(left) || this.isFloatingPointNumber(right)) {
                        double l = this.toDouble(left);
                        double r = this.toDouble(right);
                        return l + r;
                    }
                    BigInteger l = this.toBigInteger(left);
                    BigInteger r = this.toBigInteger(right);
                    BigInteger result = l.add(r);
                    return this.narrowBigInteger(left, right, result);
                }
                catch (NumberFormatException nfe) {
                    if (left != null && right != null) break block8;
                    this.controlNullOperand();
                }
            }
        }
        return this.toString(left).concat(this.toString(right));
    }

    public Object divide(Object left, Object right) {
        if (left == null && right == null) {
            return this.controlNullNullOperands();
        }
        Number ln = this.asLongNumber(left);
        Number rn = this.asLongNumber(right);
        if (ln != null && rn != null) {
            long x = ln.longValue();
            long y = rn.longValue();
            if (y == 0L) {
                throw new ArithmeticException("/");
            }
            long result = x / y;
            return this.narrowLong(left, right, result);
        }
        if (left instanceof BigDecimal || right instanceof BigDecimal) {
            BigDecimal l = this.toBigDecimal(left);
            BigDecimal r = this.toBigDecimal(right);
            if (BigDecimal.ZERO.equals(r)) {
                throw new ArithmeticException("/");
            }
            BigDecimal result = l.divide(r, this.getMathContext());
            return this.narrowBigDecimal(left, right, result);
        }
        if (this.isFloatingPointNumber(left) || this.isFloatingPointNumber(right)) {
            double l = this.toDouble(left);
            double r = this.toDouble(right);
            if (r == 0.0) {
                throw new ArithmeticException("/");
            }
            return l / r;
        }
        BigInteger l = this.toBigInteger(left);
        BigInteger r = this.toBigInteger(right);
        if (BigInteger.ZERO.equals(r)) {
            throw new ArithmeticException("/");
        }
        BigInteger result = l.divide(r);
        return this.narrowBigInteger(left, right, result);
    }

    public Object mod(Object left, Object right) {
        if (left == null && right == null) {
            return this.controlNullNullOperands();
        }
        Number ln = this.asLongNumber(left);
        Number rn = this.asLongNumber(right);
        if (ln != null && rn != null) {
            long x = ln.longValue();
            long y = rn.longValue();
            if (y == 0L) {
                throw new ArithmeticException("%");
            }
            long result = x % y;
            return this.narrowLong(left, right, result);
        }
        if (left instanceof BigDecimal || right instanceof BigDecimal) {
            BigDecimal l = this.toBigDecimal(left);
            BigDecimal r = this.toBigDecimal(right);
            if (BigDecimal.ZERO.equals(r)) {
                throw new ArithmeticException("%");
            }
            BigDecimal remainder = l.remainder(r, this.getMathContext());
            return this.narrowBigDecimal(left, right, remainder);
        }
        if (this.isFloatingPointNumber(left) || this.isFloatingPointNumber(right)) {
            double l = this.toDouble(left);
            double r = this.toDouble(right);
            if (r == 0.0) {
                throw new ArithmeticException("%");
            }
            return l % r;
        }
        BigInteger l = this.toBigInteger(left);
        BigInteger r = this.toBigInteger(right);
        if (BigInteger.ZERO.equals(r)) {
            throw new ArithmeticException("%");
        }
        BigInteger result = l.mod(r);
        return this.narrowBigInteger(left, right, result);
    }

    protected static boolean isMultiplyExact(long x, long y, long r) {
        long ay;
        long ax = Math.abs(x);
        return (ax | (ay = Math.abs(y))) >>> 31 == 0L || (y == 0L || r / y == x) && (x != Long.MIN_VALUE || y != -1L);
    }

    public Object multiply(Object left, Object right) {
        if (left == null && right == null) {
            return this.controlNullNullOperands();
        }
        Number ln = this.asLongNumber(left);
        Number rn = this.asLongNumber(right);
        if (ln != null && rn != null) {
            long result;
            long y;
            long x = ln.longValue();
            if (!JexlArithmetic.isMultiplyExact(x, y = rn.longValue(), result = x * y)) {
                return BigInteger.valueOf(x).multiply(BigInteger.valueOf(y));
            }
            return this.narrowLong(left, right, result);
        }
        if (left instanceof BigDecimal || right instanceof BigDecimal) {
            BigDecimal l = this.toBigDecimal(left);
            BigDecimal r = this.toBigDecimal(right);
            BigDecimal result = l.multiply(r, this.getMathContext());
            return this.narrowBigDecimal(left, right, result);
        }
        if (this.isFloatingPointNumber(left) || this.isFloatingPointNumber(right)) {
            double l = this.toDouble(left);
            double r = this.toDouble(right);
            return l * r;
        }
        BigInteger l = this.toBigInteger(left);
        BigInteger r = this.toBigInteger(right);
        BigInteger result = l.multiply(r);
        return this.narrowBigInteger(left, right, result);
    }

    public Object subtract(Object left, Object right) {
        if (left == null && right == null) {
            return this.controlNullNullOperands();
        }
        Number ln = this.asLongNumber(left);
        Number rn = this.asLongNumber(right);
        if (ln != null && rn != null) {
            long result;
            long y;
            long x = ln.longValue();
            if (((x ^ (y = rn.longValue())) & (x ^ (result = x - y))) < 0L) {
                return BigInteger.valueOf(x).subtract(BigInteger.valueOf(y));
            }
            return this.narrowLong(left, right, result);
        }
        if (left instanceof BigDecimal || right instanceof BigDecimal) {
            BigDecimal l = this.toBigDecimal(left);
            BigDecimal r = this.toBigDecimal(right);
            BigDecimal result = l.subtract(r, this.getMathContext());
            return this.narrowBigDecimal(left, right, result);
        }
        if (this.isFloatingPointNumber(left) || this.isFloatingPointNumber(right)) {
            double l = this.toDouble(left);
            double r = this.toDouble(right);
            return l - r;
        }
        BigInteger l = this.toBigInteger(left);
        BigInteger r = this.toBigInteger(right);
        BigInteger result = l.subtract(r);
        return this.narrowBigInteger(left, right, result);
    }

    public Object negate(Object val) {
        if (val == null) {
            this.controlNullOperand();
            return null;
        }
        if (val instanceof Integer) {
            return -((Integer)val).intValue();
        }
        if (val instanceof Double) {
            return -((Double)val).doubleValue();
        }
        if (val instanceof Long) {
            return -((Long)val).longValue();
        }
        if (val instanceof BigDecimal) {
            return ((BigDecimal)val).negate();
        }
        if (val instanceof BigInteger) {
            return ((BigInteger)val).negate();
        }
        if (val instanceof Float) {
            return Float.valueOf(-((Float)val).floatValue());
        }
        if (val instanceof Short) {
            return -((Short)val).shortValue();
        }
        if (val instanceof Byte) {
            return -((Byte)val).byteValue();
        }
        if (val instanceof Boolean) {
            return (Boolean)val == false;
        }
        if (val instanceof AtomicBoolean) {
            return !((AtomicBoolean)val).get();
        }
        throw new ArithmeticException("Object negate:(" + val + ")");
    }

    public boolean isNegateStable() {
        return true;
    }

    public Object positivize(Object val) {
        if (val == null) {
            this.controlNullOperand();
            return null;
        }
        if (val instanceof Short) {
            return ((Short)val).intValue();
        }
        if (val instanceof Byte) {
            return ((Byte)val).intValue();
        }
        if (val instanceof Number) {
            return val;
        }
        if (val instanceof Character) {
            return (int)((Character)val).charValue();
        }
        if (val instanceof Boolean) {
            return val;
        }
        if (val instanceof AtomicBoolean) {
            return ((AtomicBoolean)val).get();
        }
        throw new ArithmeticException("Object positivize:(" + val + ")");
    }

    public boolean isPositivizeStable() {
        return true;
    }

    public Boolean contains(Object container, Object value) {
        if (value == null && container == null) {
            return true;
        }
        if (value == null || container == null) {
            return false;
        }
        if (container instanceof Pattern) {
            return ((Pattern)container).matcher(value.toString()).matches();
        }
        if (container instanceof CharSequence) {
            return value.toString().matches(container.toString());
        }
        if (container instanceof Map) {
            if (value instanceof Map) {
                return ((Map)container).keySet().containsAll(((Map)value).keySet());
            }
            return ((Map)container).containsKey(value);
        }
        if (container instanceof Collection) {
            if (value instanceof Collection) {
                return ((Collection)container).containsAll((Collection)value);
            }
            return ((Collection)container).contains(value);
        }
        return null;
    }

    public Boolean endsWith(Object left, Object right) {
        if (left == null && right == null) {
            return true;
        }
        if (left == null || right == null) {
            return false;
        }
        if (left instanceof CharSequence) {
            return this.toString(left).endsWith(this.toString(right));
        }
        return null;
    }

    public Boolean startsWith(Object left, Object right) {
        if (left == null && right == null) {
            return true;
        }
        if (left == null || right == null) {
            return false;
        }
        if (left instanceof CharSequence) {
            return this.toString(left).startsWith(this.toString(right));
        }
        return null;
    }

    public Boolean empty(Object object) {
        return object == null || this.isEmpty(object, false) != false;
    }

    public Boolean isEmpty(Object object) {
        return this.isEmpty(object, object == null);
    }

    public Boolean isEmpty(Object object, Boolean def) {
        if (object instanceof Number) {
            double d = ((Number)object).doubleValue();
            return Double.isNaN(d) || d == 0.0;
        }
        if (object instanceof CharSequence) {
            return ((CharSequence)object).length() == 0;
        }
        if (object.getClass().isArray()) {
            return Array.getLength(object) == 0;
        }
        if (object instanceof Collection) {
            return ((Collection)object).isEmpty();
        }
        if (object instanceof Map) {
            return ((Map)object).isEmpty();
        }
        return def;
    }

    public Integer size(Object object) {
        return this.size(object, object == null ? 0 : 1);
    }

    public Integer size(Object object, Integer def) {
        if (object instanceof CharSequence) {
            return ((CharSequence)object).length();
        }
        if (object.getClass().isArray()) {
            return Array.getLength(object);
        }
        if (object instanceof Collection) {
            return ((Collection)object).size();
        }
        if (object instanceof Map) {
            return ((Map)object).size();
        }
        return def;
    }

    public Object and(Object left, Object right) {
        long l = this.toLong(left);
        long r = this.toLong(right);
        return l & r;
    }

    public Object or(Object left, Object right) {
        long l = this.toLong(left);
        long r = this.toLong(right);
        return l | r;
    }

    public Object xor(Object left, Object right) {
        long l = this.toLong(left);
        long r = this.toLong(right);
        return l ^ r;
    }

    public Object complement(Object val) {
        long l = this.toLong(val);
        return l ^ 0xFFFFFFFFFFFFFFFFL;
    }

    public Object not(Object val) {
        return !this.toBoolean(val);
    }

    protected int compare(Object left, Object right, String operator) {
        if (left != null && right != null) {
            if (left instanceof BigDecimal || right instanceof BigDecimal) {
                BigDecimal l = this.toBigDecimal(left);
                BigDecimal r = this.toBigDecimal(right);
                return l.compareTo(r);
            }
            if (left instanceof BigInteger || right instanceof BigInteger) {
                BigInteger l = this.toBigInteger(left);
                BigInteger r = this.toBigInteger(right);
                return l.compareTo(r);
            }
            if (this.isFloatingPoint(left) || this.isFloatingPoint(right)) {
                double lhs = this.toDouble(left);
                double rhs = this.toDouble(right);
                if (Double.isNaN(lhs)) {
                    if (Double.isNaN(rhs)) {
                        return 0;
                    }
                    return -1;
                }
                if (Double.isNaN(rhs)) {
                    return 1;
                }
                if (lhs < rhs) {
                    return -1;
                }
                if (lhs > rhs) {
                    return 1;
                }
                return 0;
            }
            if (this.isNumberable(left) || this.isNumberable(right)) {
                long rhs;
                long lhs = this.toLong(left);
                if (lhs < (rhs = this.toLong(right))) {
                    return -1;
                }
                if (lhs > rhs) {
                    return 1;
                }
                return 0;
            }
            if (left instanceof String || right instanceof String) {
                return this.toString(left).compareTo(this.toString(right));
            }
            if ("==".equals(operator)) {
                return left.equals(right) ? 0 : -1;
            }
            if (left instanceof Comparable) {
                Comparable comparable = (Comparable)left;
                return comparable.compareTo(right);
            }
            if (right instanceof Comparable) {
                Comparable comparable = (Comparable)right;
                return comparable.compareTo(left);
            }
        }
        throw new ArithmeticException("Object comparison:(" + left + " " + operator + " " + right + ")");
    }

    public boolean equals(Object left, Object right) {
        if (left == right) {
            return true;
        }
        if (left == null || right == null) {
            return false;
        }
        if (left instanceof Boolean || right instanceof Boolean) {
            return this.toBoolean(left) == this.toBoolean(right);
        }
        return this.compare(left, right, "==") == 0;
    }

    public boolean lessThan(Object left, Object right) {
        if (left == right || left == null || right == null) {
            return false;
        }
        return this.compare(left, right, "<") < 0;
    }

    public boolean greaterThan(Object left, Object right) {
        if (left == right || left == null || right == null) {
            return false;
        }
        return this.compare(left, right, ">") > 0;
    }

    public boolean lessThanOrEqual(Object left, Object right) {
        if (left == right) {
            return true;
        }
        if (left == null || right == null) {
            return false;
        }
        return this.compare(left, right, "<=") <= 0;
    }

    public boolean greaterThanOrEqual(Object left, Object right) {
        if (left == right) {
            return true;
        }
        if (left == null || right == null) {
            return false;
        }
        return this.compare(left, right, ">=") >= 0;
    }

    public boolean toBoolean(Object val) {
        if (val == null) {
            this.controlNullOperand();
            return false;
        }
        if (val instanceof Boolean) {
            return (Boolean)val;
        }
        if (val instanceof Number) {
            double number = this.toDouble(val);
            return !Double.isNaN(number) && number != 0.0;
        }
        if (val instanceof AtomicBoolean) {
            return ((AtomicBoolean)val).get();
        }
        if (val instanceof String) {
            String strval = val.toString();
            return !strval.isEmpty() && !"false".equals(strval);
        }
        return true;
    }

    public int toInteger(Object val) {
        if (val == null) {
            this.controlNullOperand();
            return 0;
        }
        if (val instanceof Double) {
            Double dval = (Double)val;
            if (Double.isNaN(dval)) {
                return 0;
            }
            return dval.intValue();
        }
        if (val instanceof Number) {
            return ((Number)val).intValue();
        }
        if (val instanceof String) {
            if ("".equals(val)) {
                return 0;
            }
            return Integer.parseInt((String)val);
        }
        if (val instanceof Boolean) {
            return (Boolean)val != false ? 1 : 0;
        }
        if (val instanceof AtomicBoolean) {
            return ((AtomicBoolean)val).get() ? 1 : 0;
        }
        if (val instanceof Character) {
            return ((Character)val).charValue();
        }
        throw new ArithmeticException("Integer coercion: " + val.getClass().getName() + ":(" + val + ")");
    }

    public long toLong(Object val) {
        if (val == null) {
            this.controlNullOperand();
            return 0L;
        }
        if (val instanceof Double) {
            Double dval = (Double)val;
            if (Double.isNaN(dval)) {
                return 0L;
            }
            return dval.longValue();
        }
        if (val instanceof Number) {
            return ((Number)val).longValue();
        }
        if (val instanceof String) {
            if ("".equals(val)) {
                return 0L;
            }
            return Long.parseLong((String)val);
        }
        if (val instanceof Boolean) {
            return (Boolean)val != false ? 1L : 0L;
        }
        if (val instanceof AtomicBoolean) {
            return ((AtomicBoolean)val).get() ? 1L : 0L;
        }
        if (val instanceof Character) {
            return ((Character)val).charValue();
        }
        throw new ArithmeticException("Long coercion: " + val.getClass().getName() + ":(" + val + ")");
    }

    public BigInteger toBigInteger(Object val) {
        if (val == null) {
            this.controlNullOperand();
            return BigInteger.ZERO;
        }
        if (val instanceof BigInteger) {
            return (BigInteger)val;
        }
        if (val instanceof Double) {
            Double dval = (Double)val;
            if (Double.isNaN(dval)) {
                return BigInteger.ZERO;
            }
            return BigInteger.valueOf(dval.longValue());
        }
        if (val instanceof BigDecimal) {
            return ((BigDecimal)val).toBigInteger();
        }
        if (val instanceof Number) {
            return BigInteger.valueOf(((Number)val).longValue());
        }
        if (val instanceof Boolean) {
            return BigInteger.valueOf((Boolean)val != false ? 1L : 0L);
        }
        if (val instanceof AtomicBoolean) {
            return BigInteger.valueOf(((AtomicBoolean)val).get() ? 1L : 0L);
        }
        if (val instanceof String) {
            String string = (String)val;
            if ("".equals(string)) {
                return BigInteger.ZERO;
            }
            return new BigInteger(string);
        }
        if (val instanceof Character) {
            char i = ((Character)val).charValue();
            return BigInteger.valueOf(i);
        }
        throw new ArithmeticException("BigInteger coercion: " + val.getClass().getName() + ":(" + val + ")");
    }

    public BigDecimal toBigDecimal(Object val) {
        if (val instanceof BigDecimal) {
            return this.roundBigDecimal((BigDecimal)val);
        }
        if (val == null) {
            this.controlNullOperand();
            return BigDecimal.ZERO;
        }
        if (val instanceof Double) {
            if (Double.isNaN((Double)val)) {
                return BigDecimal.ZERO;
            }
            return this.roundBigDecimal(new BigDecimal(val.toString(), this.getMathContext()));
        }
        if (val instanceof Number) {
            return this.roundBigDecimal(new BigDecimal(val.toString(), this.getMathContext()));
        }
        if (val instanceof Boolean) {
            return BigDecimal.valueOf((Boolean)val != false ? 1.0 : 0.0);
        }
        if (val instanceof AtomicBoolean) {
            return BigDecimal.valueOf(((AtomicBoolean)val).get() ? 1L : 0L);
        }
        if (val instanceof String) {
            String string = (String)val;
            if ("".equals(string)) {
                return BigDecimal.ZERO;
            }
            return this.roundBigDecimal(new BigDecimal(string, this.getMathContext()));
        }
        if (val instanceof Character) {
            char i = ((Character)val).charValue();
            return new BigDecimal(i);
        }
        throw new ArithmeticException("BigDecimal coercion: " + val.getClass().getName() + ":(" + val + ")");
    }

    public double toDouble(Object val) {
        if (val == null) {
            this.controlNullOperand();
            return 0.0;
        }
        if (val instanceof Double) {
            return (Double)val;
        }
        if (val instanceof Number) {
            return Double.parseDouble(String.valueOf(val));
        }
        if (val instanceof Boolean) {
            return (Boolean)val != false ? 1.0 : 0.0;
        }
        if (val instanceof AtomicBoolean) {
            return ((AtomicBoolean)val).get() ? 1.0 : 0.0;
        }
        if (val instanceof String) {
            String string = (String)val;
            if ("".equals(string)) {
                return Double.NaN;
            }
            return Double.parseDouble(string);
        }
        if (val instanceof Character) {
            char i = ((Character)val).charValue();
            return i;
        }
        throw new ArithmeticException("Double coercion: " + val.getClass().getName() + ":(" + val + ")");
    }

    public String toString(Object val) {
        if (val == null) {
            this.controlNullOperand();
            return "";
        }
        if (!(val instanceof Double)) {
            return val.toString();
        }
        Double dval = (Double)val;
        if (Double.isNaN(dval)) {
            return "";
        }
        return dval.toString();
    }

    @Deprecated
    public final Object bitwiseAnd(Object lhs, Object rhs) {
        return this.and(lhs, rhs);
    }

    @Deprecated
    public final Object bitwiseOr(Object lhs, Object rhs) {
        return this.or(lhs, rhs);
    }

    @Deprecated
    public final Object bitwiseXor(Object lhs, Object rhs) {
        return this.xor(lhs, rhs);
    }

    @Deprecated
    public final Object logicalNot(Object arg) {
        return this.not(arg);
    }

    @Deprecated
    public final Object matches(Object lhs, Object rhs) {
        return this.contains(rhs, lhs);
    }

    public static interface MapBuilder {
        public void put(Object var1, Object var2);

        public Object create();
    }

    public static interface SetBuilder {
        public void add(Object var1);

        public Object create();
    }

    public static interface ArrayBuilder {
        public void add(Object var1);

        public Object create(boolean var1);
    }

    public static interface Uberspect {
        public boolean overloads(JexlOperator var1);

        public JexlMethod getOperator(JexlOperator var1, Object ... var2);
    }

    public static class NullOperand
    extends ArithmeticException {
    }
}

