/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.internal;

import java.util.Objects;
import org.apache.commons.jexl3.JexlFeatures;

public final class Source {
    private final int hashCode;
    private final JexlFeatures features;
    private final String str;

    Source(JexlFeatures theFeatures, String theStr) {
        this.features = theFeatures;
        this.str = theStr;
        int hash = 3;
        hash = 37 * hash + this.features.hashCode();
        this.hashCode = hash = 37 * hash + this.str.hashCode();
    }

    int length() {
        return this.str.length();
    }

    public int hashCode() {
        return this.hashCode;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        Source other = (Source)obj;
        if (!Objects.equals(this.features, other.features)) {
            return false;
        }
        return Objects.equals(this.str, other.str);
    }

    public String toString() {
        return this.str;
    }

    public JexlFeatures getFeatures() {
        return this.features;
    }
}

