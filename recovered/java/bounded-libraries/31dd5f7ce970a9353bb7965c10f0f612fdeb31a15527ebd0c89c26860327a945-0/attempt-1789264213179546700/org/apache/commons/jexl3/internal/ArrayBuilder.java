/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.internal;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.IdentityHashMap;
import java.util.Map;
import org.apache.commons.jexl3.JexlArithmetic;

public class ArrayBuilder
implements JexlArithmetic.ArrayBuilder {
    private static final int PRIMITIVE_SIZE = 8;
    private static final Map<Class<?>, Class<?>> BOXING_CLASSES = new IdentityHashMap(8);
    protected Class<?> commonClass = null;
    protected boolean isNumber = true;
    protected boolean unboxing = true;
    protected final Object[] untyped;
    protected int added = 0;

    protected static Class<?> unboxingClass(Class<?> parm) {
        Class<?> prim = BOXING_CLASSES.get(parm);
        return prim == null ? parm : prim;
    }

    public ArrayBuilder(int size) {
        this.untyped = new Object[size];
    }

    @Override
    public void add(Object value) {
        if (!Object.class.equals(this.commonClass)) {
            if (value == null) {
                this.isNumber = false;
                this.unboxing = false;
            } else {
                Class<?> eclass = value.getClass();
                if (this.commonClass == null) {
                    this.commonClass = eclass;
                    this.isNumber = this.isNumber && Number.class.isAssignableFrom(this.commonClass);
                } else if (!this.commonClass.equals(eclass)) {
                    if (this.isNumber && Number.class.isAssignableFrom(eclass)) {
                        this.commonClass = Number.class;
                    } else {
                        do {
                            if ((eclass = eclass.getSuperclass()) != null) continue;
                            this.commonClass = Object.class;
                            break;
                        } while (!this.commonClass.isAssignableFrom(eclass));
                    }
                }
            }
        }
        if (this.added >= this.untyped.length) {
            throw new IllegalArgumentException("add() over size");
        }
        this.untyped[this.added++] = value;
    }

    @Override
    public Object create(boolean extended) {
        if (this.untyped == null) {
            return new Object[0];
        }
        if (extended) {
            ArrayList<Object> list = new ArrayList<Object>(this.added);
            list.addAll(Arrays.asList(this.untyped).subList(0, this.added));
            return list;
        }
        if (this.commonClass == null || Object.class.equals(this.commonClass)) {
            return this.untyped.clone();
        }
        int size = this.added;
        if (this.unboxing) {
            this.commonClass = ArrayBuilder.unboxingClass(this.commonClass);
        }
        Object typed = Array.newInstance(this.commonClass, size);
        for (int i = 0; i < size; ++i) {
            Array.set(typed, i, this.untyped[i]);
        }
        return typed;
    }

    static {
        BOXING_CLASSES.put(Boolean.class, Boolean.TYPE);
        BOXING_CLASSES.put(Byte.class, Byte.TYPE);
        BOXING_CLASSES.put(Character.class, Character.TYPE);
        BOXING_CLASSES.put(Double.class, Double.TYPE);
        BOXING_CLASSES.put(Float.class, Float.TYPE);
        BOXING_CLASSES.put(Integer.class, Integer.TYPE);
        BOXING_CLASSES.put(Long.class, Long.TYPE);
        BOXING_CLASSES.put(Short.class, Short.TYPE);
    }
}

