/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.internal;

import java.util.HashSet;
import java.util.Set;
import org.apache.commons.jexl3.JexlArithmetic;

public class SetBuilder
implements JexlArithmetic.SetBuilder {
    protected final Set<Object> set;

    public SetBuilder(int size) {
        this.set = new HashSet<Object>(size);
    }

    @Override
    public void add(Object value) {
        this.set.add(value);
    }

    @Override
    public Object create() {
        return this.set;
    }
}

