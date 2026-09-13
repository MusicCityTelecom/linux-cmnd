/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.aspectj.lang.JoinPoint
 */
package org.apereo.inspektr.audit.util;

import org.aspectj.lang.JoinPoint;

public class AopUtils {
    private AopUtils() {
    }

    public static JoinPoint unWrapJoinPoint(JoinPoint point) {
        JoinPoint naked = point;
        while (naked.getArgs().length > 0 && naked.getArgs()[0] instanceof JoinPoint) {
            naked = (JoinPoint)naked.getArgs()[0];
        }
        return naked;
    }
}

