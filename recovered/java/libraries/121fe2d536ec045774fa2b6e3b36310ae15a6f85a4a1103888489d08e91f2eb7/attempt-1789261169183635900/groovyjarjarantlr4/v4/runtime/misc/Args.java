/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.misc;

public final class Args {
    public static void notNull(String parameterName, Object value) {
        if (value == null) {
            throw new NullPointerException(parameterName + " cannot be null.");
        }
    }

    private Args() {
    }
}

