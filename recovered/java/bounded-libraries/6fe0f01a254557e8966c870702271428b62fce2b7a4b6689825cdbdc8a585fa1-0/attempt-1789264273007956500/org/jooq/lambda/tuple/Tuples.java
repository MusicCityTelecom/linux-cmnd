/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda.tuple;

final class Tuples {
    Tuples() {
    }

    static <T> int compare(T t1, T t2) {
        return t1 == null && t2 == null ? 0 : (t1 == null ? 1 : (t2 == null ? -1 : ((Comparable)t1).compareTo(t2)));
    }
}

