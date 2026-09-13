/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.utilities.general;

import org.glassfish.hk2.utilities.general.GeneralUtilities;

public class ThreadSpecificObject<T> {
    private final T incoming;
    private final long tid;
    private final int hash;

    public ThreadSpecificObject(T incoming) {
        this.incoming = incoming;
        this.tid = Thread.currentThread().getId();
        int hash = incoming == null ? 0 : incoming.hashCode();
        this.hash = hash ^= Long.valueOf(this.tid).hashCode();
    }

    public long getThreadIdentifier() {
        return this.tid;
    }

    public T getIncomingObject() {
        return this.incoming;
    }

    public int hashCode() {
        return this.hash;
    }

    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof ThreadSpecificObject)) {
            return false;
        }
        ThreadSpecificObject other = (ThreadSpecificObject)o;
        if (this.tid != other.tid) {
            return false;
        }
        return GeneralUtilities.safeEquals(this.incoming, other.incoming);
    }
}

