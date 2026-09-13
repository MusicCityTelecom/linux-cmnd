/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.parser;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public final class NumberParser {
    private Number literal = null;
    private Class<? extends Number> clazz = null;
    static final DecimalFormat BIGDF = new DecimalFormat("0.0b", new DecimalFormatSymbols(Locale.ENGLISH));

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String toString() {
        if (this.literal == null || this.clazz == null || Double.isNaN(this.literal.doubleValue())) {
            return "NaN";
        }
        if (BigDecimal.class.equals(this.clazz)) {
            DecimalFormat decimalFormat = BIGDF;
            synchronized (decimalFormat) {
                return BIGDF.format(this.literal);
            }
        }
        StringBuilder strb = new StringBuilder(this.literal.toString());
        if (Float.class.equals(this.clazz)) {
            strb.append('f');
        } else if (Double.class.equals(this.clazz)) {
            strb.append('d');
        } else if (BigInteger.class.equals(this.clazz)) {
            strb.append('h');
        } else if (Long.class.equals(this.clazz)) {
            strb.append('l');
        }
        return strb.toString();
    }

    Class<? extends Number> getLiteralClass() {
        return this.clazz;
    }

    boolean isInteger() {
        return Integer.class.equals(this.clazz);
    }

    Number getLiteralValue() {
        return this.literal;
    }

    static Number parseInteger(String s) {
        NumberParser np = new NumberParser();
        np.setNatural(s);
        return np.getLiteralValue();
    }

    static Number parseDouble(String s) {
        NumberParser np = new NumberParser();
        np.setReal(s);
        return np.getLiteralValue();
    }

    void setNatural(String s) {
        Number result;
        Class rclass;
        int base;
        if (s.charAt(0) == '0') {
            if (s.length() > 1 && (s.charAt(1) == 'x' || s.charAt(1) == 'X')) {
                base = 16;
                s = s.substring(2);
            } else {
                base = 8;
            }
        } else {
            base = 10;
        }
        int last = s.length() - 1;
        switch (s.charAt(last)) {
            case 'L': 
            case 'l': {
                rclass = Long.class;
                result = Long.valueOf(s.substring(0, last), base);
                break;
            }
            case 'H': 
            case 'h': {
                rclass = BigInteger.class;
                result = new BigInteger(s.substring(0, last), base);
                break;
            }
            default: {
                rclass = Integer.class;
                try {
                    result = Integer.valueOf(s, base);
                    break;
                }
                catch (NumberFormatException take2) {
                    try {
                        result = Long.valueOf(s, base);
                        break;
                    }
                    catch (NumberFormatException take3) {
                        result = new BigInteger(s, base);
                    }
                }
            }
        }
        this.literal = result;
        this.clazz = rclass;
    }

    void setReal(String s) {
        Class rclass;
        Number result;
        if ("#NaN".equals(s) || "NaN".equals(s)) {
            result = Double.NaN;
            rclass = Double.class;
        } else {
            int last = s.length() - 1;
            switch (s.charAt(last)) {
                case 'B': 
                case 'b': {
                    rclass = BigDecimal.class;
                    result = new BigDecimal(s.substring(0, last));
                    break;
                }
                case 'F': 
                case 'f': {
                    rclass = Float.class;
                    result = Float.valueOf(s.substring(0, last));
                    break;
                }
                case 'D': 
                case 'd': {
                    rclass = Double.class;
                    result = Double.valueOf(s.substring(0, last));
                    break;
                }
                default: {
                    rclass = Double.class;
                    try {
                        result = Double.valueOf(s);
                        break;
                    }
                    catch (NumberFormatException take3) {
                        result = new BigDecimal(s);
                    }
                }
            }
        }
        this.literal = result;
        this.clazz = rclass;
    }
}

