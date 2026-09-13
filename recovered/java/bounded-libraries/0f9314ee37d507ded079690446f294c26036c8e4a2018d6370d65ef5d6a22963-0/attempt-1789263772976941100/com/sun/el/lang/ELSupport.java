/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.el.ELException
 *  javax.el.PropertyNotFoundException
 */
package com.sun.el.lang;

import com.sun.el.lang.ELArithmetic;
import com.sun.el.util.MessageFactory;
import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.el.ELException;
import javax.el.PropertyNotFoundException;

public class ELSupport {
    private static final Long ZERO = 0L;

    public static final void throwUnhandled(Object base, Object property) throws ELException {
        if (base == null) {
            throw new PropertyNotFoundException(MessageFactory.get("error.resolver.unhandled.null", property));
        }
        throw new PropertyNotFoundException(MessageFactory.get("error.resolver.unhandled", base.getClass(), property));
    }

    public static final int compare(Object obj0, Object obj1) throws ELException {
        if (obj0 == obj1 || ELSupport.equals(obj0, obj1)) {
            return 0;
        }
        if (ELSupport.isBigDecimalOp(obj0, obj1)) {
            BigDecimal bd0 = (BigDecimal)ELSupport.coerceToNumber(obj0, BigDecimal.class);
            BigDecimal bd1 = (BigDecimal)ELSupport.coerceToNumber(obj1, BigDecimal.class);
            return bd0.compareTo(bd1);
        }
        if (ELSupport.isDoubleOp(obj0, obj1)) {
            Double d0 = (Double)ELSupport.coerceToNumber(obj0, Double.class);
            Double d1 = (Double)ELSupport.coerceToNumber(obj1, Double.class);
            return d0.compareTo(d1);
        }
        if (ELSupport.isBigIntegerOp(obj0, obj1)) {
            BigInteger bi0 = (BigInteger)ELSupport.coerceToNumber(obj0, BigInteger.class);
            BigInteger bi1 = (BigInteger)ELSupport.coerceToNumber(obj1, BigInteger.class);
            return bi0.compareTo(bi1);
        }
        if (ELSupport.isLongOp(obj0, obj1)) {
            Long l0 = (Long)ELSupport.coerceToNumber(obj0, Long.class);
            Long l1 = (Long)ELSupport.coerceToNumber(obj1, Long.class);
            return l0.compareTo(l1);
        }
        if (obj0 instanceof String || obj1 instanceof String) {
            return ELSupport.coerceToString(obj0).compareTo(ELSupport.coerceToString(obj1));
        }
        if (obj0 instanceof Comparable) {
            return obj1 != null ? ((Comparable)obj0).compareTo(obj1) : 1;
        }
        if (obj1 instanceof Comparable) {
            return obj0 != null ? -((Comparable)obj1).compareTo(obj0) : -1;
        }
        throw new ELException(MessageFactory.get("error.compare", obj0, obj1));
    }

    public static final boolean equals(Object obj0, Object obj1) throws ELException {
        if (obj0 == obj1) {
            return true;
        }
        if (obj0 == null || obj1 == null) {
            return false;
        }
        if (obj0 instanceof Boolean || obj1 instanceof Boolean) {
            return ELSupport.coerceToBoolean(obj0).equals(ELSupport.coerceToBoolean(obj1));
        }
        if (obj0.getClass().isEnum()) {
            return obj0.equals(ELSupport.coerceToEnum(obj1, obj0.getClass()));
        }
        if (obj1.getClass().isEnum()) {
            return obj1.equals(ELSupport.coerceToEnum(obj0, obj1.getClass()));
        }
        if (obj0 instanceof String || obj1 instanceof String) {
            return ELSupport.coerceToString(obj0).equals(ELSupport.coerceToString(obj1));
        }
        if (ELSupport.isBigDecimalOp(obj0, obj1)) {
            BigDecimal bd0 = (BigDecimal)ELSupport.coerceToNumber(obj0, BigDecimal.class);
            BigDecimal bd1 = (BigDecimal)ELSupport.coerceToNumber(obj1, BigDecimal.class);
            return bd0.equals(bd1);
        }
        if (ELSupport.isDoubleOp(obj0, obj1)) {
            Double d0 = (Double)ELSupport.coerceToNumber(obj0, Double.class);
            Double d1 = (Double)ELSupport.coerceToNumber(obj1, Double.class);
            return d0.equals(d1);
        }
        if (ELSupport.isBigIntegerOp(obj0, obj1)) {
            BigInteger bi0 = (BigInteger)ELSupport.coerceToNumber(obj0, BigInteger.class);
            BigInteger bi1 = (BigInteger)ELSupport.coerceToNumber(obj1, BigInteger.class);
            return bi0.equals(bi1);
        }
        if (ELSupport.isLongOp(obj0, obj1)) {
            Long l0 = (Long)ELSupport.coerceToNumber(obj0, Long.class);
            Long l1 = (Long)ELSupport.coerceToNumber(obj1, Long.class);
            return l0.equals(l1);
        }
        return obj0.equals(obj1);
    }

    public static final Boolean coerceToBoolean(Object obj) throws IllegalArgumentException {
        if (obj == null || "".equals(obj)) {
            return Boolean.FALSE;
        }
        if (obj instanceof Boolean) {
            return (Boolean)obj;
        }
        if (obj instanceof String) {
            return Boolean.valueOf((String)obj);
        }
        throw new IllegalArgumentException(MessageFactory.get("error.convert", obj, obj.getClass(), Boolean.class));
    }

    public static final Enum coerceToEnum(Object obj, Class type) {
        if (obj == null || "".equals(obj)) {
            return null;
        }
        if (obj.getClass().isEnum()) {
            return (Enum)obj;
        }
        return Enum.valueOf(type, obj.toString());
    }

    public static final Character coerceToCharacter(Object obj) throws IllegalArgumentException {
        if (obj == null || "".equals(obj)) {
            return Character.valueOf('\u0000');
        }
        if (obj instanceof String) {
            return Character.valueOf(((String)obj).charAt(0));
        }
        if (ELArithmetic.isNumber(obj)) {
            return Character.valueOf((char)((Number)obj).shortValue());
        }
        Class<?> objType = obj.getClass();
        if (obj instanceof Character) {
            return (Character)obj;
        }
        throw new IllegalArgumentException(MessageFactory.get("error.convert", obj, objType, Character.class));
    }

    public static final Number coerceToNumber(Object obj) {
        if (obj == null) {
            return ZERO;
        }
        if (obj instanceof Number) {
            return (Number)obj;
        }
        String str = ELSupport.coerceToString(obj);
        if (ELSupport.isStringFloat(str)) {
            return ELSupport.toFloat(str);
        }
        return ELSupport.toNumber(str);
    }

    protected static final Number coerceToNumber(Number number, Class type) throws IllegalArgumentException {
        if (Long.TYPE == type || Long.class.equals((Object)type)) {
            return number.longValue();
        }
        if (Double.TYPE == type || Double.class.equals((Object)type)) {
            return number.doubleValue();
        }
        if (Integer.TYPE == type || Integer.class.equals((Object)type)) {
            return number.intValue();
        }
        if (BigInteger.class.equals((Object)type)) {
            if (number instanceof BigDecimal) {
                return ((BigDecimal)number).toBigInteger();
            }
            if (number instanceof BigInteger) {
                return number;
            }
            return BigInteger.valueOf(number.longValue());
        }
        if (BigDecimal.class.equals((Object)type)) {
            if (number instanceof BigDecimal) {
                return number;
            }
            if (number instanceof BigInteger) {
                return new BigDecimal((BigInteger)number);
            }
            if (number instanceof Long) {
                return new BigDecimal((Long)number);
            }
            return new BigDecimal(number.doubleValue());
        }
        if (Byte.TYPE == type || Byte.class.equals((Object)type)) {
            return number.byteValue();
        }
        if (Short.TYPE == type || Short.class.equals((Object)type)) {
            return number.shortValue();
        }
        if (Float.TYPE == type || Float.class.equals((Object)type)) {
            return Float.valueOf(number.floatValue());
        }
        throw new IllegalArgumentException(MessageFactory.get("error.convert", number, number.getClass(), type));
    }

    public static final Number coerceToNumber(Object obj, Class type) throws IllegalArgumentException {
        if (obj == null || "".equals(obj)) {
            return ELSupport.coerceToNumber(ZERO, type);
        }
        if (obj instanceof String) {
            return ELSupport.coerceToNumber((String)obj, type);
        }
        if (ELArithmetic.isNumber(obj)) {
            if (obj.getClass().equals(type)) {
                return (Number)obj;
            }
            return ELSupport.coerceToNumber((Number)obj, type);
        }
        if (obj instanceof Character) {
            return ELSupport.coerceToNumber((short)((Character)obj).charValue(), type);
        }
        throw new IllegalArgumentException(MessageFactory.get("error.convert", obj, obj.getClass(), type));
    }

    protected static final Number coerceToNumber(String val, Class type) throws IllegalArgumentException {
        if (Long.TYPE == type || Long.class.equals((Object)type)) {
            return Long.valueOf(val);
        }
        if (Integer.TYPE == type || Integer.class.equals((Object)type)) {
            return Integer.valueOf(val);
        }
        if (Double.TYPE == type || Double.class.equals((Object)type)) {
            return Double.valueOf(val);
        }
        if (BigInteger.class.equals((Object)type)) {
            return new BigInteger(val);
        }
        if (BigDecimal.class.equals((Object)type)) {
            return new BigDecimal(val);
        }
        if (Byte.TYPE == type || Byte.class.equals((Object)type)) {
            return Byte.valueOf(val);
        }
        if (Short.TYPE == type || Short.class.equals((Object)type)) {
            return Short.valueOf(val);
        }
        if (Float.TYPE == type || Float.class.equals((Object)type)) {
            return Float.valueOf(val);
        }
        throw new IllegalArgumentException(MessageFactory.get("error.convert", val, String.class, type));
    }

    public static final String coerceToString(Object obj) {
        if (obj == null) {
            return "";
        }
        if (obj instanceof String) {
            return (String)obj;
        }
        if (obj instanceof Enum) {
            return ((Enum)obj).name();
        }
        return obj.toString();
    }

    public static final void checkType(Object obj, Class type) throws IllegalArgumentException {
        if (String.class.equals((Object)type)) {
            ELSupport.coerceToString(obj);
        }
        if (ELArithmetic.isNumberType(type)) {
            ELSupport.coerceToNumber(obj, type);
        }
        if (Character.class.equals((Object)type) || Character.TYPE == type) {
            ELSupport.coerceToCharacter(obj);
        }
        if (Boolean.class.equals((Object)type) || Boolean.TYPE == type) {
            ELSupport.coerceToBoolean(obj);
        }
        if (type.isEnum()) {
            ELSupport.coerceToEnum(obj, type);
        }
    }

    public static final Object coerceToType(Object obj, Class type) throws IllegalArgumentException {
        if (type == null || Object.class.equals((Object)type) || obj != null && type.isAssignableFrom(obj.getClass())) {
            return obj;
        }
        if (String.class.equals((Object)type)) {
            return ELSupport.coerceToString(obj);
        }
        if (ELArithmetic.isNumberType(type)) {
            return ELSupport.coerceToNumber(obj, type);
        }
        if (Character.class.equals((Object)type) || Character.TYPE == type) {
            return ELSupport.coerceToCharacter(obj);
        }
        if (Boolean.class.equals((Object)type) || Boolean.TYPE == type) {
            return ELSupport.coerceToBoolean(obj);
        }
        if (type.isEnum()) {
            return ELSupport.coerceToEnum(obj, type);
        }
        if (obj == null) {
            return null;
        }
        if (obj instanceof String) {
            if ("".equals(obj)) {
                return null;
            }
            PropertyEditor editor = PropertyEditorManager.findEditor(type);
            if (editor != null) {
                editor.setAsText((String)obj);
                return editor.getValue();
            }
        }
        throw new IllegalArgumentException(MessageFactory.get("error.convert", obj, obj.getClass(), type));
    }

    public static final boolean containsNulls(Object[] obj) {
        for (int i = 0; i < obj.length; ++i) {
            if (obj[0] != null) continue;
            return true;
        }
        return false;
    }

    public static final boolean isBigDecimalOp(Object obj0, Object obj1) {
        return obj0 instanceof BigDecimal || obj1 instanceof BigDecimal;
    }

    public static final boolean isBigIntegerOp(Object obj0, Object obj1) {
        return obj0 instanceof BigInteger || obj1 instanceof BigInteger;
    }

    public static final boolean isDoubleOp(Object obj0, Object obj1) {
        return obj0 instanceof Double || obj1 instanceof Double || obj0 instanceof Float || obj1 instanceof Float;
    }

    public static final boolean isDoubleStringOp(Object obj0, Object obj1) {
        return ELSupport.isDoubleOp(obj0, obj1) || obj0 instanceof String && ELSupport.isStringFloat((String)obj0) || obj1 instanceof String && ELSupport.isStringFloat((String)obj1);
    }

    public static final boolean isLongOp(Object obj0, Object obj1) {
        return obj0 instanceof Long || obj1 instanceof Long || obj0 instanceof Integer || obj1 instanceof Integer || obj0 instanceof Character || obj1 instanceof Character || obj0 instanceof Short || obj1 instanceof Short || obj0 instanceof Byte || obj1 instanceof Byte;
    }

    public static final boolean isStringFloat(String str) {
        int len = str.length();
        if (len > 1) {
            for (int i = 0; i < len; ++i) {
                switch (str.charAt(i)) {
                    case 'E': {
                        return true;
                    }
                    case 'e': {
                        return true;
                    }
                    case '.': {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static final Number toFloat(String value) {
        try {
            if (Double.parseDouble(value) > Double.MAX_VALUE) {
                return new BigDecimal(value);
            }
            return Double.valueOf(value);
        }
        catch (NumberFormatException e0) {
            return new BigDecimal(value);
        }
    }

    public static final Number toNumber(String value) {
        try {
            return Integer.parseInt(value);
        }
        catch (NumberFormatException e0) {
            try {
                return Long.parseLong(value);
            }
            catch (NumberFormatException e1) {
                return new BigInteger(value);
            }
        }
    }
}

