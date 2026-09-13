/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.tool;

public enum ErrorSeverity {
    INFO("info"),
    WARNING("warning"),
    WARNING_ONE_OFF("warning"),
    ERROR("error"),
    ERROR_ONE_OFF("error"),
    FATAL("fatal");

    private final String text;

    public String getText() {
        return this.text;
    }

    private ErrorSeverity(String text) {
        this.text = text;
    }
}

