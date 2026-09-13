/*
 * Decompiled with CFR 0.152.
 */
package com.sun.el.lang;

import com.sun.el.lang.ELSupport;
import com.sun.el.util.MessageFactory;
import java.math.BigDecimal;
import java.math.BigInteger;

public abstract class ELArithmetic {
    public static final BigDecimalDelegate BIGDECIMAL = new BigDecimalDelegate();
    public static final BigIntegerDelegate BIGINTEGER = new BigIntegerDelegate();
    public static final DoubleDelegate DOUBLE = new DoubleDelegate();
    public static final LongDelegate LONG = new LongDelegate();
    private static final Long ZERO = 0L;

    public static final Number add(Object obj0, Object obj1) {
        if (obj0 == null && obj1 == null) {
            return 0L;
        }
        ELArithmetic delegate = BIGDECIMAL.matches(obj0, obj1) ? BIGDECIMAL : (DOUBLE.matches(obj0, obj1) ? DOUBLE : (BIGINTEGER.matches(obj0, obj1) ? BIGINTEGER : LONG));
        Number num0 = delegate.coerce(obj0);
        Number num1 = delegate.coerce(obj1);
        return delegate.add(num0, num1);
    }

    public static final Number mod(Object obj0, Object obj1) {
        if (obj0 == null && obj1 == null) {
            return 0L;
        }
        ELArithmetic delegate = BIGDECIMAL.matches(obj0, obj1) ? BIGDECIMAL : (DOUBLE.matches(obj0, obj1) ? DOUBLE : (BIGINTEGER.matches(obj0, obj1) ? BIGINTEGER : LONG));
        Number num0 = delegate.coerce(obj0);
        Number num1 = delegate.coerce(obj1);
        return delegate.mod(num0, num1);
    }

    public static final Number subtract(Object obj0, Object obj1) {
        if (obj0 == null && obj1 == null) {
            return 0L;
        }
        ELArithmetic delegate = BIGDECIMAL.matches(obj0, obj1) ? BIGDECIMAL : (DOUBLE.matches(obj0, obj1) ? DOUBLE : (BIGINTEGER.matches(obj0, obj1) ? BIGINTEGER : LONG));
        Number num0 = delegate.coerce(obj0);
        Number num1 = delegate.coerce(obj1);
        return delegate.subtract(num0, num1);
    }

    public static final Number divide(Object obj0, Object obj1) {
        if (obj0 == null && obj1 == null) {
            return ZERO;
        }
        ELArithmetic delegate = BIGDECIMAL.matches(obj0, obj1) ? BIGDECIMAL : (BIGINTEGER.matches(obj0, obj1) ? BIGDECIMAL : DOUBLE);
        Number num0 = delegate.coerce(obj0);
        Number num1 = delegate.coerce(obj1);
        return delegate.divide(num0, num1);
    }

    public static final Number multiply(Object obj0, Object obj1) {
        if (obj0 == null && obj1 == null) {
            return 0L;
        }
        ELArithmetic delegate = BIGDECIMAL.matches(obj0, obj1) ? BIGDECIMAL : (DOUBLE.matches(obj0, obj1) ? DOUBLE : (BIGINTEGER.matches(obj0, obj1) ? BIGINTEGER : LONG));
        Number num0 = delegate.coerce(obj0);
        Number num1 = delegate.coerce(obj1);
        return delegate.multiply(num0, num1);
    }

    public static final boolean isNumber(Object obj) {
        return obj != null && ELArithmetic.isNumberType(obj.getClass());
    }

    public static final boolean isNumberType(Class type) {
        return type == Long.class || type == Long.TYPE || type == Double.class || type == Double.TYPE || type == Byte.class || type == Byte.TYPE || type == Short.class || type == Short.TYPE || type == Integer.class || type == Integer.TYPE || type == Float.class || type == Float.TYPE || type == BigInteger.class || type == BigDecimal.class;
    }

    protected ELArithmetic() {
    }

    protected abstract Number add(Number var1, Number var2);

    protected abstract Number multiply(Number var1, Number var2);

    protected abstract Number subtract(Number var1, Number var2);

    protected abstract Number mod(Number var1, Number var2);

    protected abstract Number coerce(Number var1);

    protected final Number coerce(Object obj) {
        if (ELArithmetic.isNumber(obj)) {
            return this.coerce((Number)obj);
        }
        if (obj instanceof String) {
            return this.coerce((String)obj);
        }
        if (obj == null || "".equals(obj)) {
            return this.coerce(ZERO);
        }
        Class<?> objType = obj.getClass();
        if (Character.class.equals(objType) || Character.TYPE == objType) {
            return this.coerce((short)((Character)obj).charValue());
        }
        throw new IllegalArgumentException(MessageFactory.get("el.convert", obj, objType));
    }

    protected abstract Number coerce(String var1);

    protected abstract Number divide(Number var1, Number var2);

    protected abstract boolean matches(Object var1, Object var2);

    public static final class LongDelegate
    extends ELArithmetic {
        protected Number add(Number num0, Number num1) {
            return num0.longValue() + num1.longValue();
        }

        protected Number coerce(Number num) {
            if (num instanceof Long) {
                return num;
            }
            return num.longValue();
        }

        protected Number coerce(String str) {
            return Long.valueOf(str);
        }

        protected Number divide(Number num0, Number num1) {
            return num0.longValue() / num1.longValue();
        }

        protected Number mod(Number num0, Number num1) {
            return num0.longValue() % num1.longValue();
        }

        protected Number subtract(Number num0, Number num1) {
            return num0.longValue() - num1.longValue();
        }

        protected Number multiply(Number num0, Number num1) {
            return num0.longValue() * num1.longValue();
        }

        public boolean matches(Object obj0, Object obj1) {
            return obj0 instanceof Long || obj1 instanceof Long;
        }
    }

    public static final class DoubleDelegate
    extends ELArithmetic {
        protected Number add(Number num0, Number num1) {
            if (num0 instanceof BigDecimal) {
                return ((BigDecimal)num0).add(new BigDecimal(num1.doubleValue()));
            }
            if (num1 instanceof BigDecimal) {
                return new BigDecimal(num0.doubleValue()).add((BigDecimal)num1);
            }
            return num0.doubleValue() + num1.doubleValue();
        }

        protected Number coerce(Number num) {
            if (num instanceof Double) {
                return num;
            }
            if (num instanceof BigInteger) {
                return new BigDecimal((BigInteger)num);
            }
            return num.doubleValue();
        }

        protected Number coerce(String str) {
            return Double.valueOf(str);
        }

        protected Number divide(Number num0, Number num1) {
            return num0.doubleValue() / num1.doubleValue();
        }

        protected Number mod(Number num0, Number num1) {
            return num0.doubleValue() % num1.doubleValue();
        }

        protected Number subtract(Number num0, Number num1) {
            if (num0 instanceof BigDecimal) {
                return ((BigDecimal)num0).subtract(new BigDecimal(num1.doubleValue()));
            }
            if (num1 instanceof BigDecimal) {
                return new BigDecimal(num0.doubleValue()).subtract((BigDecimal)num1);
            }
            return num0.doubleValue() - num1.doubleValue();
        }

        protected Number multiply(Number num0, Number num1) {
            if (num0 instanceof BigDecimal) {
                return ((BigDecimal)num0).multiply(new BigDecimal(num1.doubleValue()));
            }
            if (num1 instanceof BigDecimal) {
                return new BigDecimal(num0.doubleValue()).multiply((BigDecimal)num1);
            }
            return num0.doubleValue() * num1.doubleValue();
        }

        public boolean matches(Object obj0, Object obj1) {
            return obj0 instanceof Double || obj1 instanceof Double || obj0 instanceof Float || obj1 instanceof Float || obj0 != null && (Double.TYPE == obj0.getClass() || Float.TYPE == obj0.getClass()) || obj1 != null && (Double.TYPE == obj1.getClass() || Float.TYPE == obj1.getClass()) || obj0 instanceof String && ELSupport.isStringFloat((String)obj0) || obj1 instanceof String && ELSupport.isStringFloat((String)obj1);
        }
    }

    public static final class BigIntegerDelegate
    extends ELArithmetic {
        protected Number add(Number num0, Number num1) {
            return ((BigInteger)num0).add((BigInteger)num1);
        }

        protected Number coerce(Number num) {
            if (num instanceof BigInteger) {
                return num;
            }
            return new BigInteger(num.toString());
        }

        protected Number coerce(String str) {
            return new BigInteger(str);
        }

        protected Number divide(Number num0, Number num1) {
            return new BigDecimal((BigInteger)num0).divide(new BigDecimal((BigInteger)num1), 4);
        }

        protected Number multiply(Number num0, Number num1) {
            return ((BigInteger)num0).multiply((BigInteger)num1);
        }

        protected Number mod(Number num0, Number num1) {
            return ((BigInteger)num0).mod((BigInteger)num1);
        }

        protected Number subtract(Number num0, Number num1) {
            return ((BigInteger)num0).subtract((BigInteger)num1);
        }

        public boolean matches(Object obj0, Object obj1) {
            return obj0 instanceof BigInteger || obj1 instanceof BigInteger;
        }
    }

    public static final class BigDecimalDelegate
    extends ELArithmetic {
        protected Number add(Number num0, Number num1) {
            return ((BigDecimal)num0).add((BigDecimal)num1);
        }

        protected Number coerce(Number num) {
            if (num instanceof BigDecimal) {
                return num;
            }
            if (num instanceof BigInteger) {
                return new BigDecimal((BigInteger)num);
            }
            return new BigDecimal(num.doubleValue());
        }

        protected Number coerce(String str) {
            return new BigDecimal(str);
        }

        protected Number divide(Number num0, Number num1) {
            return ((BigDecimal)num0).divide((BigDecimal)num1, 4);
        }

        protected Number subtract(Number num0, Number num1) {
            return ((BigDecimal)num0).subtract((BigDecimal)num1);
        }

        protected Number mod(Number num0, Number num1) {
            return num0.doubleValue() % num1.doubleValue();
        }

        protected Number multiply(Number num0, Number num1) {
            return ((BigDecimal)num0).multiply((BigDecimal)num1);
        }

        public boolean matches(Object obj0, Object obj1) {
            return obj0 instanceof BigDecimal || obj1 instanceof BigDecimal;
        }
    }
}

