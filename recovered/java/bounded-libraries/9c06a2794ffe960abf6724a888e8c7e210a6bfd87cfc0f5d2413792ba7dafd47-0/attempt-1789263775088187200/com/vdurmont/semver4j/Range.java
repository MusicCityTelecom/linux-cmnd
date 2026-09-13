/*
 * Decompiled with CFR 0.152.
 */
package com.vdurmont.semver4j;

import com.vdurmont.semver4j.Semver;
import java.util.Objects;

public class Range {
    protected final Semver version;
    protected final RangeOperator op;

    public Range(Semver version, RangeOperator op) {
        this.version = version;
        this.op = op;
    }

    public Range(String version, RangeOperator op) {
        this(new Semver(version, Semver.SemverType.LOOSE), op);
    }

    public boolean isSatisfiedBy(String version) {
        return this.isSatisfiedBy(new Semver(version, this.version.getType()));
    }

    public boolean isSatisfiedBy(Semver version) {
        switch (this.op) {
            case EQ: {
                return version.isEquivalentTo(this.version);
            }
            case LT: {
                return version.isLowerThan(this.version);
            }
            case LTE: {
                return version.isLowerThan(this.version) || version.isEquivalentTo(this.version);
            }
            case GT: {
                return version.isGreaterThan(this.version);
            }
            case GTE: {
                return version.isGreaterThan(this.version) || version.isEquivalentTo(this.version);
            }
        }
        throw new RuntimeException("Code error. Unknown RangeOperator: " + (Object)((Object)this.op));
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Range)) {
            return false;
        }
        Range range = (Range)o;
        return Objects.equals(this.version, range.version) && this.op == range.op;
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.version, this.op});
    }

    public String toString() {
        return this.op.asString() + this.version;
    }

    public static enum RangeOperator {
        EQ("="),
        LT("<"),
        LTE("<="),
        GT(">"),
        GTE(">=");

        private final String s;

        private RangeOperator(String s) {
            this.s = s;
        }

        public String asString() {
            return this.s;
        }
    }
}

