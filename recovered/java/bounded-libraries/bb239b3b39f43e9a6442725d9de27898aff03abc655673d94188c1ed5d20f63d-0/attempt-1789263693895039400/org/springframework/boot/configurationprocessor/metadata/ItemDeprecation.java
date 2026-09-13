/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.configurationprocessor.metadata;

public class ItemDeprecation {
    private String reason;
    private String replacement;
    private String level;

    public ItemDeprecation() {
        this(null, null);
    }

    public ItemDeprecation(String reason, String replacement) {
        this(reason, replacement, null);
    }

    public ItemDeprecation(String reason, String replacement, String level) {
        this.reason = reason;
        this.replacement = replacement;
        this.level = level;
    }

    public String getReason() {
        return this.reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReplacement() {
        return this.replacement;
    }

    public void setReplacement(String replacement) {
        this.replacement = replacement;
    }

    public String getLevel() {
        return this.level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        ItemDeprecation other = (ItemDeprecation)o;
        return this.nullSafeEquals(this.reason, other.reason) && this.nullSafeEquals(this.replacement, other.replacement) && this.nullSafeEquals(this.level, other.level);
    }

    public int hashCode() {
        int result = this.nullSafeHashCode(this.reason);
        result = 31 * result + this.nullSafeHashCode(this.replacement);
        result = 31 * result + this.nullSafeHashCode(this.level);
        return result;
    }

    public String toString() {
        return "ItemDeprecation{reason='" + this.reason + '\'' + ", replacement='" + this.replacement + '\'' + ", level='" + this.level + '\'' + '}';
    }

    private boolean nullSafeEquals(Object o1, Object o2) {
        if (o1 == o2) {
            return true;
        }
        if (o1 == null || o2 == null) {
            return false;
        }
        return o1.equals(o2);
    }

    private int nullSafeHashCode(Object o) {
        return o != null ? o.hashCode() : 0;
    }
}

