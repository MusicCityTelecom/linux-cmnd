/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.atn;

import groovyjarjarantlr4.v4.runtime.misc.Utils;
import java.util.BitSet;

public class ConflictInfo {
    private final BitSet conflictedAlts;
    private final boolean exact;

    public ConflictInfo(BitSet conflictedAlts, boolean exact) {
        this.conflictedAlts = conflictedAlts;
        this.exact = exact;
    }

    public final BitSet getConflictedAlts() {
        return this.conflictedAlts;
    }

    public final boolean isExact() {
        return this.exact;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ConflictInfo)) {
            return false;
        }
        ConflictInfo other = (ConflictInfo)obj;
        return this.isExact() == other.isExact() && Utils.equals(this.getConflictedAlts(), other.getConflictedAlts());
    }

    public int hashCode() {
        return this.getConflictedAlts().hashCode();
    }
}

