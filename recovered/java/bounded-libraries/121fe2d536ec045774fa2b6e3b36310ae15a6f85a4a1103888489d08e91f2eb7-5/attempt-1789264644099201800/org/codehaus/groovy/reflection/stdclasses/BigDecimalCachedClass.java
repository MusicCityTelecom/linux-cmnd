/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.reflection.stdclasses;

import java.math.BigDecimal;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.reflection.stdclasses.DoubleCachedClass;
import org.codehaus.groovy.runtime.typehandling.NumberMath;

public class BigDecimalCachedClass
extends DoubleCachedClass {
    public BigDecimalCachedClass(Class klazz, ClassInfo classInfo) {
        super(klazz, classInfo, true);
    }

    @Override
    public boolean isDirectlyAssignable(Object argument) {
        return argument instanceof BigDecimal;
    }

    @Override
    public Object coerceArgument(Object argument) {
        if (argument instanceof Number) {
            return NumberMath.toBigDecimal((Number)argument);
        }
        return argument;
    }
}

