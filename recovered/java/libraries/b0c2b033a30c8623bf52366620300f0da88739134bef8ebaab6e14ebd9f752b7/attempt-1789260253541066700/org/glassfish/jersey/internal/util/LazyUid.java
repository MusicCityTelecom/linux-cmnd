/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.util;

import java.io.Serializable;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class LazyUid
implements Serializable {
    private static final long serialVersionUID = 4618609413877136867L;
    private final AtomicReference<String> uid = new AtomicReference();

    public String value() {
        if (this.uid.get() == null) {
            this.uid.compareAndSet(null, UUID.randomUUID().toString());
        }
        return this.uid.get();
    }

    public boolean equals(Object that) {
        if (that == null) {
            return false;
        }
        if (this.getClass() != that.getClass()) {
            return false;
        }
        LazyUid other = (LazyUid)that;
        return this.value().equals(other.value());
    }

    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + this.value().hashCode();
        return hash;
    }

    public String toString() {
        return this.value();
    }
}

