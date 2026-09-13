/*
 * Decompiled with CFR 0.152.
 */
package org.aspectj.weaver;

import org.aspectj.weaver.AnnotationAJ;
import org.aspectj.weaver.UnresolvedType;

public class Utils {
    public static boolean isSuppressing(AnnotationAJ[] anns, String lintkey) {
        if (anns == null) {
            return false;
        }
        for (AnnotationAJ ann : anns) {
            String value;
            if (!UnresolvedType.SUPPRESS_AJ_WARNINGS.getSignature().equals(ann.getTypeSignature()) || (value = ann.getStringFormOfValue("value")) != null && !value.contains(lintkey)) continue;
            return true;
        }
        return false;
    }
}

