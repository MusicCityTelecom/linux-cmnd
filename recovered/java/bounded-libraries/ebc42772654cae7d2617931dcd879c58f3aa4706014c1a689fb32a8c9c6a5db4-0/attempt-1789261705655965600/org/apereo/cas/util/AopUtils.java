/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.aspectj.lang.JoinPoint
 */
package org.apereo.cas.util;

import lombok.Generated;
import org.aspectj.lang.JoinPoint;

public final class AopUtils {
    public static JoinPoint unWrapJoinPoint(JoinPoint point) {
        JoinPoint naked = point;
        while (naked.getArgs() != null && naked.getArgs().length > 0 && naked.getArgs()[0] instanceof JoinPoint) {
            naked = (JoinPoint)naked.getArgs()[0];
        }
        return naked;
    }

    @Generated
    private AopUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

