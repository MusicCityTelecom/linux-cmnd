/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.util.collection;

import org.glassfish.jersey.internal.util.collection.KeyComparator;

public class StringIgnoreCaseKeyComparator
implements KeyComparator<String> {
    private static final long serialVersionUID = 9106900325469360723L;
    public static final StringIgnoreCaseKeyComparator SINGLETON = new StringIgnoreCaseKeyComparator();

    @Override
    public int hash(String k) {
        return k.toLowerCase().hashCode();
    }

    @Override
    public boolean equals(String x, String y) {
        return x.equalsIgnoreCase(y);
    }
}

