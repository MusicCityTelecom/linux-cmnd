/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

abstract class Sum<N> {
    long count;

    Sum() {
    }

    void add(Sum<N> sum) {
        this.add0(sum.result());
        this.count += sum.count;
    }

    void add(N value) {
        this.add0(value);
        ++this.count;
    }

    void and(Sum<N> sum) {
        this.and0(sum.result());
    }

    void and(N value) {
        this.and0(value);
    }

    void or(Sum<N> sum) {
        this.or0(sum.result());
    }

    void or(N value) {
        this.or0(value);
    }

    abstract void add0(N var1);

    abstract void and0(N var1);

    abstract void or0(N var1);

    abstract N result();

    abstract N avg();

    static <N> Sum<N> create(N value) {
        Sum result;
        if (value instanceof Byte) {
            result = new OfByte();
        } else if (value instanceof Short) {
            result = new OfShort();
        } else if (value instanceof Integer) {
            result = new OfInt();
        } else if (value instanceof Long) {
            result = new OfLong();
        } else if (value instanceof Float) {
            result = new OfFloat();
        } else if (value instanceof Double) {
            result = new OfDouble();
        } else if (value instanceof BigInteger) {
            result = new OfBigInteger();
        } else if (value instanceof BigDecimal) {
            result = new OfBigDecimal();
        } else {
            throw new IllegalArgumentException("Cannot calculate sums for value : " + value);
        }
        result.add(value);
        return result;
    }

    static class OfBigDecimal
    extends Sum<BigDecimal> {
        BigDecimal sum = BigDecimal.ZERO;

        OfBigDecimal() {
        }

        @Override
        void add0(BigDecimal value) {
            this.sum = this.sum.add(value);
        }

        @Override
        void and0(BigDecimal value) {
            throw new UnsupportedOperationException();
        }

        @Override
        void or0(BigDecimal value) {
            throw new UnsupportedOperationException();
        }

        @Override
        BigDecimal result() {
            return this.sum;
        }

        @Override
        BigDecimal avg() {
            return this.sum.divide(BigDecimal.valueOf(this.count), RoundingMode.HALF_EVEN);
        }
    }

    static class OfBigInteger
    extends Sum<BigInteger> {
        BigInteger sum = BigInteger.ZERO;

        OfBigInteger() {
        }

        @Override
        void add0(BigInteger value) {
            this.sum = this.sum.add(value);
        }

        @Override
        void and0(BigInteger value) {
            throw new UnsupportedOperationException();
        }

        @Override
        void or0(BigInteger value) {
            throw new UnsupportedOperationException();
        }

        @Override
        BigInteger result() {
            return this.sum;
        }

        @Override
        BigInteger avg() {
            return this.sum.divide(BigInteger.valueOf(this.count));
        }
    }

    static class OfDouble
    extends Sum<Double> {
        double sum;

        OfDouble() {
        }

        @Override
        void add0(Double value) {
            this.sum += value.doubleValue();
        }

        @Override
        void and0(Double value) {
            throw new UnsupportedOperationException();
        }

        @Override
        void or0(Double value) {
            throw new UnsupportedOperationException();
        }

        @Override
        Double result() {
            return this.sum;
        }

        @Override
        Double avg() {
            return this.sum / (double)this.count;
        }
    }

    static class OfFloat
    extends Sum<Float> {
        float sum;

        OfFloat() {
        }

        @Override
        void add0(Float value) {
            this.sum += value.floatValue();
        }

        @Override
        void and0(Float value) {
            throw new UnsupportedOperationException();
        }

        @Override
        void or0(Float value) {
            throw new UnsupportedOperationException();
        }

        @Override
        Float result() {
            return Float.valueOf(this.sum);
        }

        @Override
        Float avg() {
            return Float.valueOf(this.sum / (float)this.count);
        }
    }

    static class OfLong
    extends Sum<Long> {
        long sum;

        OfLong() {
        }

        @Override
        void add0(Long value) {
            this.sum += value.longValue();
        }

        @Override
        void and0(Long value) {
            this.sum &= value.longValue();
        }

        @Override
        void or0(Long value) {
            this.sum |= value.longValue();
        }

        @Override
        Long result() {
            return this.sum;
        }

        @Override
        Long avg() {
            return this.sum / this.count;
        }
    }

    static class OfInt
    extends Sum<Integer> {
        int sum;

        OfInt() {
        }

        @Override
        void add0(Integer value) {
            this.sum += value.intValue();
        }

        @Override
        void and0(Integer value) {
            this.sum &= value.intValue();
        }

        @Override
        void or0(Integer value) {
            this.sum |= value.intValue();
        }

        @Override
        Integer result() {
            return this.sum;
        }

        @Override
        Integer avg() {
            return (int)((long)this.sum / this.count);
        }
    }

    static class OfShort
    extends Sum<Short> {
        short sum;

        OfShort() {
        }

        @Override
        void add0(Short value) {
            this.sum = (short)(this.sum + value);
        }

        @Override
        void and0(Short value) {
            this.sum = (short)(this.sum & value);
        }

        @Override
        void or0(Short value) {
            this.sum = (short)(this.sum | value);
        }

        @Override
        Short result() {
            return this.sum;
        }

        @Override
        Short avg() {
            return (short)((long)this.sum / this.count);
        }
    }

    static class OfByte
    extends Sum<Byte> {
        byte result;

        OfByte() {
        }

        @Override
        void add0(Byte value) {
            this.result = (byte)(this.result + value);
        }

        @Override
        Byte result() {
            return this.result;
        }

        @Override
        void and0(Byte value) {
            this.result = (byte)(this.result & value);
        }

        @Override
        void or0(Byte value) {
            this.result = (byte)(this.result | value);
        }

        @Override
        Byte avg() {
            return (byte)((long)this.result / this.count);
        }
    }
}

