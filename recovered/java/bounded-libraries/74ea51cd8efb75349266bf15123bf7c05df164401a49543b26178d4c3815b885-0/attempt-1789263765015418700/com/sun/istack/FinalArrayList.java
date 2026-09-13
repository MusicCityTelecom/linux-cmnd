/*
 * Decompiled with CFR 0.152.
 */
package com.sun.istack;

import java.util.ArrayList;
import java.util.Collection;

public final class FinalArrayList<T>
extends ArrayList<T> {
    private static final long serialVersionUID = -540534530037816397L;

    public FinalArrayList(int initialCapacity) {
        super(initialCapacity);
    }

    public FinalArrayList() {
    }

    public FinalArrayList(Collection<? extends T> ts) {
        super(ts);
    }
}

