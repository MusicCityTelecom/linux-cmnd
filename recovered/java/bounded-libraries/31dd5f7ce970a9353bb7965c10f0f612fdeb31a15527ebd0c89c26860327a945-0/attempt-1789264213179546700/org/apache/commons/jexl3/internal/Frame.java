/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.internal;

import java.util.Arrays;
import org.apache.commons.jexl3.internal.Scope;

public final class Frame {
    private final Scope scope;
    private final Object[] stack;
    private final int curried;

    Frame(Scope s, Object[] r, int c) {
        this.scope = s;
        this.stack = r;
        this.curried = c;
    }

    public String[] getUnboundParameters() {
        return this.scope.getParameters(this.curried);
    }

    public Scope getScope() {
        return this.scope;
    }

    public int hashCode() {
        return Arrays.deepHashCode(this.stack);
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        Frame other = (Frame)obj;
        return Arrays.deepEquals(this.stack, other.stack);
    }

    Object get(int s) {
        return this.stack[s];
    }

    boolean has(int s) {
        return s >= 0 && s < this.stack.length && this.stack[s] != Scope.UNDECLARED;
    }

    void set(int r, Object value) {
        this.stack[r] = value;
    }

    Frame assign(Object ... values) {
        if (this.stack != null) {
            int nparm = this.scope.getArgCount();
            Object[] copy = (Object[])this.stack.clone();
            int ncopy = 0;
            if (values != null && values.length > 0) {
                ncopy = Math.min(nparm - this.curried, Math.min(nparm, values.length));
                System.arraycopy(values, 0, copy, this.curried, ncopy);
            }
            Arrays.fill(copy, this.curried + ncopy, nparm, null);
            return new Frame(this.scope, copy, this.curried + ncopy);
        }
        return this;
    }
}

