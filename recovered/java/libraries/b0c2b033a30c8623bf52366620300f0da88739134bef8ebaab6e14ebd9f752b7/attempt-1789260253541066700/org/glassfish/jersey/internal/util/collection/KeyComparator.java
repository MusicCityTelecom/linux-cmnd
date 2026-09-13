/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.util.collection;

import java.io.Serializable;

public interface KeyComparator<K>
extends Serializable {
    public boolean equals(K var1, K var2);

    public int hash(K var1);
}

