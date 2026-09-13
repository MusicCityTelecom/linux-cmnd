/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.el.ELException
 */
package com.sun.el.parser;

import com.sun.el.lang.EvaluationContext;
import com.sun.el.parser.SimpleNode;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.el.ELException;

public final class AstNegative
extends SimpleNode {
    public AstNegative(int id) {
        super(id);
    }

    public Class getType(EvaluationContext ctx) throws ELException {
        return Number.class;
    }

    public Object getValue(EvaluationContext ctx) throws ELException {
        Object obj = this.children[0].getValue(ctx);
        if (obj == null) {
            return 0L;
        }
        if (obj instanceof BigDecimal) {
            return ((BigDecimal)obj).negate();
        }
        if (obj instanceof BigInteger) {
            return ((BigInteger)obj).negate();
        }
        if (obj instanceof String) {
            if (AstNegative.isStringFloat((String)obj)) {
                return -Double.parseDouble((String)obj);
            }
            return -Long.parseLong((String)obj);
        }
        Class<?> type = obj.getClass();
        if (obj instanceof Long || Long.TYPE == type) {
            return -((Long)obj).longValue();
        }
        if (obj instanceof Double || Double.TYPE == type) {
            return -((Double)obj).doubleValue();
        }
        if (obj instanceof Integer || Integer.TYPE == type) {
            return -((Integer)obj).intValue();
        }
        if (obj instanceof Float || Float.TYPE == type) {
            return Float.valueOf(-((Float)obj).floatValue());
        }
        if (obj instanceof Short || Short.TYPE == type) {
            return -((Short)obj).shortValue();
        }
        if (obj instanceof Byte || Byte.TYPE == type) {
            return -((Byte)obj).byteValue();
        }
        Long num = (Long)AstNegative.coerceToNumber(obj, Long.class);
        return -num.longValue();
    }
}

