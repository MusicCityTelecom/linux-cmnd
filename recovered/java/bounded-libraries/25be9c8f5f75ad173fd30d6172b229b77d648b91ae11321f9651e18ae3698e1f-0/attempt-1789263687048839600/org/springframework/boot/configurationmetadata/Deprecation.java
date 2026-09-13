/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.configurationmetadata;

import java.io.Serializable;

public class Deprecation
implements Serializable {
    private Level level = Level.WARNING;
    private String reason;
    private String shortReason;
    private String replacement;

    public Level getLevel() {
        return this.level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public String getReason() {
        return this.reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getShortReason() {
        return this.shortReason;
    }

    public void setShortReason(String shortReason) {
        this.shortReason = shortReason;
    }

    public String getReplacement() {
        return this.replacement;
    }

    public void setReplacement(String replacement) {
        this.replacement = replacement;
    }

    public String toString() {
        return "Deprecation{level='" + (Object)((Object)this.level) + '\'' + ", reason='" + this.reason + '\'' + ", replacement='" + this.replacement + '\'' + '}';
    }

    public static enum Level {
        WARNING,
        ERROR;

    }
}

