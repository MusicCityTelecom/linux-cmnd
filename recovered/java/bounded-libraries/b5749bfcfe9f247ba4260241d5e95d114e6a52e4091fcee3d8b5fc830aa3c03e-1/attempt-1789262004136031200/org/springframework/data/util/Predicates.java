/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 */
package org.springframework.data.util;

import java.util.function.Predicate;
import org.springframework.util.Assert;

public interface Predicates {
    public static <T> Predicate<T> isTrue() {
        return t -> true;
    }

    public static <T> Predicate<T> isFalse() {
        return t -> false;
    }

    public static <T> Predicate<T> negate(Predicate<T> predicate) {
        Assert.notNull(predicate, (String)"Predicate must not be null");
        return predicate.negate();
    }
}

