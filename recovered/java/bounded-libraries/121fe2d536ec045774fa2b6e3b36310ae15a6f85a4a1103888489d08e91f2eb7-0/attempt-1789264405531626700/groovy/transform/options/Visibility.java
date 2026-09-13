/*
 * Decompiled with CFR 0.152.
 */
package groovy.transform.options;

public enum Visibility {
    PUBLIC(1),
    PROTECTED(4),
    PACKAGE_PRIVATE(0),
    PRIVATE(2),
    UNDEFINED(-1);

    private final int modifier;

    private Visibility(int modifier) {
        this.modifier = modifier;
    }

    public int getModifier() {
        if (this.modifier == -1) {
            throw new UnsupportedOperationException("getModifier() not supported for UNDEFINED");
        }
        return this.modifier;
    }
}

